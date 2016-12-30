package com.kaishengit.service;

import com.kaishengit.dao.NodeDao;
import com.kaishengit.entity.Node;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.DbHelp;
import com.kaishengit.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by sunny on 2016/12/30.
 */
public class NodeService {
    private Logger logger = LoggerFactory.getLogger(NodeService.class);

    private NodeDao nodeDao = new NodeDao();

    /**
     * 查找所有论坛版块
     *
     * @return 论坛版块清单，nodeList
     */
    public List<Node> findAllNode() {
        logger.debug("查询了所有板块信息");
        return nodeDao.findAll();
    }

    public void delNodeById(String nodeId) {
        if (StringUtils.isNumeric(nodeId)) {
            Node node = nodeDao.findById(Integer.valueOf(nodeId));
            if (node.getTopicNum() > 0) {
                throw new ServiceException("该节点下已有主题,不可删除");
            } else {
                nodeDao.delById(nodeId);
                logger.info("节点{}被删除", node.getNodeName());
            }
        } else {
            throw new ServiceException("参数错误");
        }
    }

    public void addNode(String nodeName) {
        if (nodeDao.findByNodeName(nodeName) == null) {
            Node node = new Node();
            node.setNodeName(nodeName);
            nodeDao.addNode(node);
            logger.info("添加了新节点{}", nodeName);
        } else {
            throw new ServiceException("节点已存在，不能重复创建！");
        }
    }
}

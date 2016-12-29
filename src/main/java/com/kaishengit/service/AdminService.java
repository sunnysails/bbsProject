package com.kaishengit.service;

import com.kaishengit.dao.AdminDao;
import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.ReplyDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.entity.Admin;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Config;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sunny on 2016/12/29.
 */
public class AdminService {
    Logger logger = LoggerFactory.getLogger(AdminService.class);
    private AdminDao adminDao = new AdminDao();
    private ReplyDao replyDao = new ReplyDao();
    private TopicDao topicDao = new TopicDao();
    private NodeDao nodeDao = new NodeDao();

    public Admin login(String adminName, String passWord, String ip) {
        Admin admin = adminDao.findByName(adminName);
        if (admin != null && admin.getPassWord().equals(DigestUtils.md5Hex(Config.get("user.password.salt") + passWord))) {
            logger.info("管理员“{}”，在Ip“{}”登录了后台管理系统", adminName, ip);
            return admin;
        } else {
            throw new ServiceException("账号或密码错误");
        }
    }

    /**
     * 根据Id 删除帖子，首先删除该帖子下属的回复，根据Id 获取Topic，删出该主题
     *
     * @param id
     */
    public void deleteTopicById(String id) {
        replyDao.delByTopicId(id);
        Topic topic = topicDao.findById(Integer.valueOf(id));
        if (topic != null) {
            Node node = nodeDao.findById(topic.getNodeId());
            node.setTopicNum(node.getTopicNum() - 1);
            nodeDao.update(node);
            topicDao.delById(id);
            logger.info("删除了Id为“{}”，主题为“{}”的帖子", id, topic.getTitle());
        } else {
            logger.error("删除Id为“{}”，主题为“{}”的帖子出错。", id, topic.getTitle());
            throw new ServiceException("该主题不存在或已删除");
        }
    }
}

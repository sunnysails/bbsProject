package com.kaishengit.service;

import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by sunny on 2016/12/21.
 */
public class TopicService {
    private Logger logger = LoggerFactory.getLogger(TopicService.class);

    private TopicDao topicDao = new TopicDao();
    private UserDao userDao = new UserDao();
    private NodeDao nodeDao = new NodeDao();

    /**
     * 查找所有论坛版块
     *
     * @return 论坛版块清单，nodeList
     */
    public List<Node> findAllNode() {

        logger.debug("查询了所有板块信息");
        return nodeDao.findAllNode();
    }

    /**
     * 存储新帖子并返回该 Topic 对象
     *
     * @param title   主题
     * @param content 内容
     * @param nodeId  关联板块ID
     * @param userId  发帖人ID
     * @return Topic对象
     */
    public Topic addNewTopic(String title, String content, Integer nodeId, Integer userId) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setNodeId(nodeId);
        topic.setUserId(userId);
        Integer topicId = topicDao.save(topic);
        topic.setId(topicId);
        return topic;
    }
}
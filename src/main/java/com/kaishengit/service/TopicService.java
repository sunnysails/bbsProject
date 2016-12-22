package com.kaishengit.service;

import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Config;
import com.kaishengit.util.StringUtils;
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
        return nodeDao.findAll();
    }

    /**
     * 查询所有帖子信息，并返回完整的topic对象集合（包含user，node信息）。
     *
     * @return topic 集合
     */
    public List<Topic> findAllTopic() {

        List<Topic> topicList = topicDao.findAll();

        logger.debug("查询所有帖子信息");
        return topicList;
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
        //封装对象topic
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setNodeId(nodeId);
        topic.setUserId(userId);
        Integer topicId = topicDao.save(topic);
        topic.setId(topicId);

        //更新node表的topicnum
        Node node = nodeDao.findById(nodeId);
        if (node != null) {
            node.setTopicNum(node.getTopicNum() + 1);
            nodeDao.save(node);
        } else {
            throw new ServiceException("论坛板块不存在");
        }

        logger.info("Id为{}在{}板块ID：{}发表了主题为：{}帖子", userId, topic.getCreateTime(), nodeId, title);
        return topic;
    }

    /**
     * 根据Id 查询帖子,判断参数是否正确，判断帖子是否存在。
     *
     * @param topicId
     * @return
     */
    public Topic findTopicById(String topicId) {
        if (StringUtils.isNumeric(topicId)) {
            Topic topic = topicDao.findById(topicId);
            if (topic != null) {
                User user = userDao.findById(topic.getUserId());
                Node node = nodeDao.findById(topic.getNodeId());
                user.setAvatar(Config.get("qiniu.domain") + user.getAvatar());
                topic.setUser(user);
                topic.setNode(node);

                logger.debug("{}查看了帖子，时间为{}，主题为：{}", topic.getUser().getUserName(), topic.getCreateTime(), topic.getTitle());
                //TODO  bug 待调整，logger错误。
                return topic;
            } else {
                throw new ServiceException("该帖子不存在，或已被删除");
            }
        } else {
            throw new ServiceException("参数错误！");
        }
    }
}
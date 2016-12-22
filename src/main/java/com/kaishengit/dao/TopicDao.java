package com.kaishengit.dao;

import com.kaishengit.entity.Topic;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by sunny on 2016/12/21.
 */
public class TopicDao {
    public Integer save(Topic topic) {
        String sql = "INSERT INTO t_topic (title, content, userid, nodeid) VALUES(?,?,?,?)";
        return DbHelp.insert(sql, topic.getTitle(), topic.getContent(), topic.getUserId(), topic.getNodeId());
        //TODO node数量加！！
    }

    public Topic findById(String topicId) {
        String sql = "SELECT * FROM t_topic WHERE id = ?";
        return DbHelp.query(sql, new BeanHandler<>(Topic.class), topicId);
    }

    public List<Topic> findAll() {
        String sql = "SELECT * FROM t_topic";
        return DbHelp.query(sql, new BeanListHandler<Topic>(Topic.class));
    }
}

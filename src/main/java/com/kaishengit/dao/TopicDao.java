package com.kaishengit.dao;

import com.kaishengit.entity.Topic;
import com.kaishengit.util.DbHelp;

/**
 * Created by sunny on 2016/12/21.
 */
public class TopicDao {
    public Integer save(Topic topic) {
        String sql = "INSERT INTO t_topic (title, content, userid, nodeid) VALUES(?,?,?,?)";
        return DbHelp.insert(sql, topic.getTitle(), topic.getContent(), topic.getUserId(), topic.getNodeId());
        //TODO node数量加！！
    }
}

package com.kaishengit.dao;

import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.util.Config;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by sunny on 2016/12/21.
 */
public class TopicDao {
    private AbstractListHandler<Topic> abr = new AbstractListHandler<Topic>() {
        @Override
        protected Topic handleRow(ResultSet rs) throws SQLException {
            Topic topic = new BasicRowProcessor().toBean(rs, Topic.class);

            User user = new User();
            user.setAvatar(rs.getString("avatar"));
            user.setUserName(rs.getString("username"));
            topic.setUser(user);
            return topic;
        }
    };

    public Integer save(Topic topic) {
        String sql = "INSERT INTO t_topic (title, content, lastreplytime, userid, nodeid) VALUES(?,?,?,?,?)";
        return DbHelp.insert(sql, topic.getTitle(), topic.getContent(), topic.getLastReplyTime(), topic.getUserId(), topic.getNodeId());
        //TODO node数量加！！
    }

    public Topic findById(Integer topicId) {
        String sql = "SELECT * FROM t_topic WHERE id = ?";
        return DbHelp.query(sql, new BeanHandler<>(Topic.class), topicId);
    }

    /**
     * 查询所有与首页相关的信息，并封装成一个topic集合
     *
     * @return topic 集合
     */
    public List<Topic> findAll() {
        String sql = "SELECT * FROM t_topic tt ,t_user tu ,t_node tn WHERE tt.userid = tu.id AND tt.nodeid = tn.id ORDER BY lastreplytime";
        return DbHelp.query(sql, new AbstractListHandler<Topic>() {
            @Override
            protected Topic handleRow(ResultSet rs) throws SQLException {
                Topic topic = new BasicRowProcessor().toBean(rs, Topic.class);
                topic.setId(rs.getInt("id"));

                User user = new User();
                user.setAvatar(Config.get("qiniu.domain") + rs.getString("avatar"));
                user.setUserName(rs.getString("username"));
                topic.setUser(user);

                Node node = new Node();
                node.setTopicNum(rs.getInt("topicnum"));
                node.setNodeName(rs.getString("nodename"));
                topic.setNode(node);

                return topic;
            }
        });
    }

    public void update(Topic topic) {
        String sql = "UPDATE t_topic SET title = ?, content = ?, createtime = ?, clicknum = ?, favnum= ?, thankyounum = ?, replynum = ?, lastreplytime = ?, nodeid = ?,userid = ? where id = ?";
        DbHelp.update(sql, topic.getTitle(), topic.getContent(), topic.getCreateTime(), topic.getClickNum(), topic.getFavNum(), topic.getThankNum(), topic.getReplyNum(), topic.getLastReplyTime(), topic.getNodeId(), topic.getUserId(), topic.getId());
    }

    public Long count() {
        String sql = "SELECT count(*) FROM t_topic";
        return DbHelp.query(sql, new ScalarHandler<Long>());
    }

    public List<Topic> findAllByPage(Integer page, Integer p) {
        String sql = "SELECT tu.username,tu.avatar,tt.* FROM t_topic tt ,t_user tu WHERE tt.userid = tu.id ORDER BY lastreplytime DESC LIMIT ?,?";
        return DbHelp.query(sql, abr, page, p);
    }

    public List<Topic> findAllByNodeIdAndPage(String nodeId, Integer page, Integer p) {
        String sql = "SELECT tu.username,tu.avatar,tt.* FROM t_topic tt ,t_user tu WHERE tt.userid = tu.id AND nodeid=? ORDER BY lastreplytime DESC LIMIT ?,?";
        return DbHelp.query(sql, abr, nodeId, page, p);
    }
}
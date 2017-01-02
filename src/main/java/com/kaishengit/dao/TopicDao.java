package com.kaishengit.dao;

import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.util.DbHelp;
import com.kaishengit.vo.TopicReplyCountVo;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
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
            user.setAvatar(User.QINIUDOMAIN + rs.getString("avatar"));
            user.setUserName(rs.getString("username"));
            topic.setUser(user);
            return topic;
        }
    };

    public Integer save(Topic topic) {
        String sql = "INSERT INTO t_topic (title, content, lastreplytime, userid, nodeid) VALUES (?, ?, ?, ?, ?)";
        return DbHelp.insert(sql, topic.getTitle(), topic.getContent(), topic.getLastReplyTime(), topic.getUserId(), topic.getNodeId());
    }

    public Topic findById(Integer topicId) {
        String sql = "SELECT * FROM t_topic WHERE id = ?";
        return DbHelp.query(sql, new BeanHandler<>(Topic.class), topicId);
    }

    public void update(Topic topic) {
        String sql = "UPDATE t_topic\n" +
                "SET title = ?, content = ?, createtime = ?, clicknum = ?, favnum = ?, thankyounum = ?, replynum = ?, lastreplytime = ?,\n" +
                "  nodeid  = ?, userid = ?\n" +
                "WHERE id = ?";
        DbHelp.update(sql, topic.getTitle(), topic.getContent(), topic.getCreateTime(), topic.getClickNum(), topic.getFavNum(), topic.getThankNum(), topic.getReplyNum(), topic.getLastReplyTime(), topic.getNodeId(), topic.getUserId(), topic.getId());
    }

    public Long count() {
        String sql = "SELECT count(*) FROM t_topic";
        return DbHelp.query(sql, new ScalarHandler<Long>());
    }

    public List<Topic> findAllByPage(Integer page, Integer p) {
        String sql = "SELECT\n" +
                "  tu.username,\n" +
                "  tu.avatar,\n" +
                "  tt.*\n" +
                "FROM t_topic tt, t_user tu\n" +
                "WHERE tt.userid = tu.id\n" +
                "ORDER BY lastreplytime DESC\n" +
                "LIMIT ?, ?";
        return DbHelp.query(sql, abr, page, p);
    }

    public List<Topic> findAllByNodeIdAndPage(String nodeId, Integer page, Integer p) {
        String sql = "SELECT\n" +
                "  tu.username,\n" +
                "  tu.avatar,\n" +
                "  tt.*\n" +
                "FROM t_topic tt, t_user tu\n" +
                "WHERE tt.userid = tu.id AND nodeid = ?\n" +
                "ORDER BY lastreplytime DESC\n" +
                "LIMIT ?, ?";
        return DbHelp.query(sql, abr, nodeId, page, p);
    }

    public void delById(String topicId) {
        String sql = "DELETE FROM t_topic WHERE id = ?";
        DbHelp.update(sql, topicId);
    }

    public int countTopicByDay() {
        String sql = "SELECT count(*)\n" +
                "FROM (SELECT count(*)\n" +
                "      FROM t_topic\n" +
                "      GROUP BY DATE_FORMAT(createtime, '%y-%m-%d')) AS topicCount";
        return DbHelp.query(sql, new ScalarHandler<Long>()).intValue();
    }

    public List<TopicReplyCountVo> getTopicAndReplyNumList(int start, Integer page) {
        String sql = "SELECT\n" +
                "  COUNT(*)                                                                                  topicnum,\n" +
                "  DATE_FORMAT(createtime, '%y-%m-%d')                                                       'time',\n" +
                "  (SELECT COUNT(*)\n" +
                "   FROM t_reply\n" +
                "   WHERE DATE_FORMAT(createtime, '%y-%m-%d') = DATE_FORMAT(t_topic.createtime, '%y-%m-%d')) 'replynum'\n" +
                "FROM t_topic\n" +
                "GROUP BY (DATE_FORMAT(createtime, '%y-%m-%d'))\n" +
                "ORDER BY (DATE_FORMAT(createtime, '%y-%m-%d')) DESC\n" +
                "LIMIT ?, ?";

        return DbHelp.query(sql, new BeanListHandler<TopicReplyCountVo>(TopicReplyCountVo.class), start, page);
    }
}
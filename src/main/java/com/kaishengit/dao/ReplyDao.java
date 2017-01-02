package com.kaishengit.dao;

import com.kaishengit.entity.Reply;
import com.kaishengit.entity.User;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by sunny on 2016/12/23.
 */
public class ReplyDao {

    public void addReply(Reply reply) {
        String sql = "INSERT INTO t_reply (content, userid, topicid) VALUES(?,?,?)";
        DbHelp.insert(sql, reply.getContent(), reply.getUserId(), reply.getTopicId());
    }

    public void delByTopicId(String topicId) {
        String sql = "DELETE FROM t_reply WHERE topicid = ?";
        DbHelp.update(sql, topicId);
    }

    /**
     * 根据topicId 和page查询新的回复页面
     *
     * @param topicId 帖子Id
     * @param start
     * @param page
     * @return 结果页面信息
     */
    public List<Reply> findReplyListByATiP(String topicId, int start, Integer page) {
        String sql = "SELECT\n" +
                "  tu.id,\n" +
                "  tu.avatar,\n" +
                "  tu.username,\n" +
                "  tr.*\n" +
                "FROM t_reply tr, t_user tu\n" +
                "WHERE tr.userid = tu.id AND topicid = ?\n" +
                "LIMIT ?, ?";
        return DbHelp.query(sql, new AbstractListHandler<Reply>() {
            @Override
            protected Reply handleRow(ResultSet rs) throws SQLException {
                Reply reply = new BasicRowProcessor().toBean(rs, Reply.class);

                User user = new User();
                user.setAvatar(User.QINIUDOMAIN + rs.getString("avatar"));
                user.setUserName(rs.getString("username"));
                user.setId(rs.getInt("id"));
                reply.setUser(user);
                return reply;
            }
        }, topicId, start, page);
    }
}

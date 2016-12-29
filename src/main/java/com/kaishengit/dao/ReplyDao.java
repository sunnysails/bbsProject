package com.kaishengit.dao;

import com.kaishengit.entity.Reply;
import com.kaishengit.entity.User;
import com.kaishengit.util.Config;
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
    /**
     * 通过topicId查找回复列表，及回复用户简单信息
     *
     * @param topicId 帖子Id
     * @return 回复列表集
     */
    public List<Reply> findListByTopicId(String topicId) {
        String sql = "SELECT tu.id,tu.avatar,tu.username,tr.* FROM t_reply tr ,t_user tu WHERE tr.userid = tu.id AND topicid = ?";
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
        }, topicId);
    }

    public void addReply(Reply reply) {
        String sql = "INSERT INTO t_reply (content, userid, topicid) VALUES(?,?,?)";
        DbHelp.insert(sql, reply.getContent(), reply.getUserId(), reply.getTopicId());
    }

    public void delByTopicId(String topicId) {
        String sql = "DELETE FROM t_reply WHERE topicid = ?";
        DbHelp.update(sql,topicId);
    }
}

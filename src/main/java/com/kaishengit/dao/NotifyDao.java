package com.kaishengit.dao;

import com.kaishengit.entity.Notify;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by sunny on 2016/12/27.
 */
public class NotifyDao {
    public List<Notify> findByUserId(Integer userId) {
        String sql = "SELECT *\n" +
                "FROM t_notify\n" +
                "WHERE userid = ?\n" +
                "ORDER BY readtime, createtime";
        return DbHelp.query(sql, new BeanListHandler<Notify>(Notify.class), userId);
    }

    public Notify findById(Integer notifyId) {
        String sql = "SELECT *\n" +
                "FROM t_notify\n" +
                "WHERE id = ?";
        return DbHelp.query(sql, new BeanHandler<Notify>(Notify.class), notifyId);
    }

    public void update(Notify notify) {
        String sql = "UPDATE t_notify\n" +
                "SET state = ?, readtime = ?\n" +
                "WHERE id = ?";
        DbHelp.update(sql, notify.getState(), notify.getReadTime(), notify.getId());
    }

    public void save(Notify notify) {
        String sql = "INSERT INTO t_notify (userid, replyid, content, state) VALUES (?, ?, ?, ?)";
        DbHelp.update(sql, notify.getUserId(), notify.getReplyId(), notify.getContent(), notify.getState());
    }
}

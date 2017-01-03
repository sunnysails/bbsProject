package com.kaishengit.dao;

import com.kaishengit.entity.User;
import com.kaishengit.util.DbHelp;
import com.kaishengit.vo.UserVo;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;

/**
 * Created by sunny on 2016/12/15.
 */
public class UserDao {
    //存储user
    public void save(User user) {
        String sql = "INSERT INTO t_user (username, password, email, phone, state, avatar) VALUES (?, ?, ?, ?, ?, ?)";
        DbHelp.update(sql, user.getUserName(), user.getPassWord(), user.getEmail(), user.getPhone(), user.getState(), user.getAvatar());
    }

    //通过用户名来查找用户
    public User findByUserName(String userName) {
        String sql = "SELECT * FROM t_user WHERE username = ?";
        return DbHelp.query(sql, new BeanHandler<>(User.class), userName);
    }

    //通过ID查找用户
    public User findById(Integer id) {
        String sql = "SELECT * FROM t_user WHERE id = ?";
        return DbHelp.query(sql, new BeanHandler<>(User.class), id);
    }

    //通过电子邮件查询用户
    public User findByEmail(String email) {
        String sql = "SELECT * FROM t_user WHERE email = ?";
        return DbHelp.query(sql, new BeanHandler<User>(User.class), email);
    }

    //更改账户信息
    public void update(User user) {
        String sql = "UPDATE t_user\n" +
                "SET password = ?, email = ?, phone = ?, state = ?, avatar = ?\n" +
                "WHERE id = ?";
        DbHelp.update(sql, user.getPassWord(), user.getEmail(), user.getPhone(), user.getState(), user.getAvatar(), user.getId());
    }

    public Integer count() {
        String sql = "SELECT count(*)\n" +
                "FROM t_user\n" +
                "WHERE state != 0\n" +
                "ORDER BY id";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    public List<UserVo> findUserVoByPageNo(int start, Integer page) {
        String sql = "SELECT\n" +
                "  tu.id,\n" +
                "  tu.username,\n" +
                "  tu.email,\n" +
                "  tu.phone,\n" +
                "  tu.state,\n" +
                "  tu.createtime,\n" +
                "  tu.avatar,\n" +
                "  a.logintime,\n" +
                "  a.ip\n" +
                "FROM t_user tu, (SELECT\n" +
                "                   tll.logintime,\n" +
                "                   tll.ip,\n" +
                "                   tll.userid\n" +
                "                 FROM t_login_log tll\n" +
                "                 ORDER BY logintime DESC) a\n" +
                "GROUP BY tu.id\n" +
                "LIMIT ?, ?";
        return DbHelp.query(sql,new BeanListHandler<UserVo>(UserVo.class),start,page);
    }
}

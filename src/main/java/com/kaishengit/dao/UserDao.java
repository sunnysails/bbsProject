package com.kaishengit.dao;

import com.kaishengit.entity.User;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by sunny on 2016/12/15.
 */
public class UserDao {
    //存储user
    public void save(User user) {
        String sql = "INSERT INTO t_user(username, password, email, phone, avatar) VALUES(?,?,?,?,?)";
        DbHelp.update(sql, user.getUserName(), user.getPassWord(), user.getEmail(), user.getPhone(), user.getAvatar());
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
}

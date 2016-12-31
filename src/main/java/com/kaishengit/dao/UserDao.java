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
        String sql = "INSERT INTO t_user(username, password, email, phone, state, avatar) VALUES (?,?,?,?,?,?)";
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
        String sql = "update t_user set password=?,email=?,phone=?,state=?,avatar=? where id = ?";
        DbHelp.update(sql, user.getPassWord(), user.getEmail(), user.getPhone(), user.getState(), user.getAvatar(), user.getId());
    }

    public Integer count() {
        String sql = "select count(*) from t_user where state != 0 order by id";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    public List<UserVo> findUserVoByPageNo(int start, Integer page) {
        String sql = "SELECT tu.id,tu.username,tu.email,tu.phone,tu.state,tu.createtime,tu.avatar,tll.logintime,tll.ip,max(tll.logintime) FROM t_login_log tll ,t_user tu GROUP BY tu.id LIMIT ?,?";
        return DbHelp.query(sql,new BeanListHandler<UserVo>(UserVo.class),start,page);
    }
}

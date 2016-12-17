package com.kaishengit.dao;

import com.kaishengit.entity.LoginLog;
import com.kaishengit.util.DbHelp;

/**
 * Created by sunny on 2016/12/17.
 */
public class LoginLogDao {
    //存储用户操作log
    public void save(LoginLog log) {
        String sql = "INSERT INTO t_login_log(ip, userid) VALUES (?,?)";
        DbHelp.update(sql,log.getIp(), log.getUserId());
    }
}

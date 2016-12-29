package com.kaishengit.dao;

import com.kaishengit.entity.Admin;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by sunny on 2016/12/29.
 */
public class AdminDao {
    public Admin findByName(String adminName) {
        String sql = "SELECT * FROM t_admin WHERE adminname = ?";
        return DbHelp.query(sql, new BeanHandler<Admin>(Admin.class), adminName);
    }
}

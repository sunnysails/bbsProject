package com.kaishengit.dao;

import com.kaishengit.entity.Node;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by sunny on 2016/12/21.
 */
public class NodeDao {
    public List<Node> findAll() {
        String sql = "SELECT * FROM t_node";
        return DbHelp.query(sql, new BeanListHandler<Node>(Node.class));
    }

    public Node findById(Integer id) {
        String sql = "SELECT * FROM t_node WHERE id = ?";
        return DbHelp.query(sql, new BeanHandler<Node>(Node.class), id);
    }
}

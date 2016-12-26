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

    public Node findById(Integer nodeId) {
        String sql = "SELECT * FROM t_node WHERE id = ?";
        return DbHelp.query(sql, new BeanHandler<Node>(Node.class), nodeId);
    }

    public void save(Node node) {
        String sql = "UPDATE t_node SET nodename = ?, topicnum = ? WHERE id = ?";
        DbHelp.update(sql, node.getNodeName(), node.getTopicNum(), node.getId());
    }

    public void update(Node node) {
        String sql = "update t_node set nodename = ?, topicnum = ? where id = ?";
        DbHelp.update(sql, node.getNodeName(), node.getTopicNum(), node.getId());
    }
}

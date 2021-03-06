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
        String sql = "UPDATE t_node\n" +
                "SET nodename = ?, topicnum = ?\n" +
                "WHERE id = ?";
        DbHelp.update(sql, node.getNodeName(), node.getTopicNum(), node.getId());
    }

    public void delById(String nodeId) {
        String sql = "DELETE FROM t_node\n" +
                "WHERE id = ?";
        DbHelp.update(sql,nodeId);
    }

    public Node findByNodeName(String nodeName) {
        String sql = "SELECT * FROM t_node WHERE nodename = ?";
        return DbHelp.query(sql,new BeanHandler<Node>(Node.class),nodeName);
    }

    public void addNode(Node node) {
        String sql ="INSERT INTO t_node (nodename) VALUE (?)";
        DbHelp.update(sql,node.getNodeName());
    }
}

package com.kaishengit.entity;

import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Config;
import org.joda.time.DateTime;

import java.sql.Timestamp;

/**
 * Created by sunny on 2016/12/21.
 */
public class Topic {
    private static final Integer UNEDITTIME = Integer.valueOf(Config.get("topic.unedit.time"));

    private Integer id;
    private String title;
    private String content;
    private Timestamp createTime;
    private Integer clickNum;
    private Integer favNum;
    private Integer thankNum;
    private Integer replyNum;
    private Timestamp lastReplyTime;
    private Integer userId;
    private Integer nodeId;

    private User user;
    private Node node;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public Integer getFavNum() {
        return favNum;
    }

    public void setFavNum(Integer favNum) {
        this.favNum = favNum;
    }

    public Integer getThankNum() {
        return thankNum;
    }

    public void setThankNum(Integer thankNum) {
        this.thankNum = thankNum;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public Timestamp getLastReplyTime() {
        return lastReplyTime;
    }

    public void setLastReplyTime(Timestamp lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public boolean isEdit() {
        DateTime dateTime = new DateTime(getCreateTime());
        if (dateTime.plusMinutes(UNEDITTIME).isAfterNow()) {
            if (getReplyNum() == 0){
                return true;
            }else {
                throw new ServiceException("已有人回复该帖，不能删除该帖子");
            }
        } else {
            throw new ServiceException("该帖发布已过允许时间，禁止进行编辑，请联系管理员");
        }
    }
}

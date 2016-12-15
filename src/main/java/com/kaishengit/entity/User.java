package com.kaishengit.entity;

import java.sql.Timestamp;

/**
 * Created by sunny on 2016/12/15.
 */
public class User {
    private Integer id;//用户ID
    private String userName;//用户帐号，用于登录
    private String passWord;//用户密码，md5保存
    private String email;//用户电子邮件地址
    private Integer phone;//用户电话号码，数字
    private Integer state;//用户状态，0、未激活；1、正在使用；2、被禁止
    private Timestamp createTime;//用户创建时间。
    private String avatar;//用户头像URL

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

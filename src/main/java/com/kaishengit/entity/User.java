package com.kaishengit.entity;

import com.kaishengit.util.Config;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by sunny on 2016/12/15.
 */
public class User implements Serializable {

    /**
     * 新用户默认头像key
     */
    public static final String DEFAULT_AVATAR_NAME = "default-avatar.jpg";
    /**
     * 用户状态:未激活
     */
    public static final Integer USERSTATE_UNACTIVE = 0;
    /**
     * 用户状态:已激活（正常）
     */
    public static final Integer USERSTATE_ACTIVE = 1;
    /**
     * 用户状态:禁用
     */
    public static final Integer USERSTATE_DISABLED = 2;

    public static final String QINIUDOMAIN = Config.get("qiniu.domain");

    private Integer id;//用户ID
    private String userName;//用户帐号，用于登录
    private String passWord;//用户密码，md5保存
    private String email;//用户电子邮件地址
    private String phone;//用户电话号码，数字
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
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
        return QINIUDOMAIN + avatar;
    }

    public String getAvatarFileName() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

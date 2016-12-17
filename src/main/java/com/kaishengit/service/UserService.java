package com.kaishengit.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Config;
import com.kaishengit.util.EmailUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.sun.corba.se.impl.util.RepositoryId.cache;

/**
 * Created by sunny on 2016/12/15.
 */
public class UserService {
    private UserDao userDao = new UserDao();

    //发送激活邮件的TOKEN缓存
    private static Cache<String, String > cache = CacheBuilder.newBuilder()
            .expireAfterWrite(6, TimeUnit.HOURS)
            .build();

    /**
     *
     * @param token 根据激活邮件种URL 的token 值激活对应的用户
     * @return
     */
    public void activeUser(String token){
        String userName = cache.getIfPresent(token);
        if (userName == null){
            throw new ServiceException("token无效或链接已过期");
        }else{
            User user = userDao.findByUserName(userName);
            if (user == null) {
                throw new ServiceException("无法找到对应账号");
            }else{
                user.setState(User.USERSTATE_ACTIVE);
                userDao.update(user);
                //将缓存中的键值对删除
                cache.invalidate(token);
            }
        }
    }

    /**
     * 校检用户名是否被占用
     *
     * @param userName
     * @return
     */
    public boolean validateUserName(String userName) {
        //检测保留名冲突
        String name = Config.get("no.signup.usernames");
        List<String> nameList = Arrays.asList(name.split(","));
        if (nameList.contains(userName)) {
            return false;
        }
        return userDao.findByUserName(userName) == null;
    }

    /**
     * 校检电子邮件唯一性
     *
     * @param email
     * @return
     */
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    /**
     * 新用户注册
     *
     * @param userName
     * @param passWord
     * @param email
     * @param phone
     */
    public void saveUser(String userName, String passWord, String email, String phone) {
        User user = new User();
        user.setUserName(userName);
        user.setPassWord(DigestUtils.md5Hex(Config.get("user.password.salt") + passWord));
        user.setAvatar(User.DEFAULT_AVATAR_NAME);
        user.setEmail(email);
        user.setPhone(phone);
        user.setState(User.USERSTATE_UNACTIVE);

        userDao.save(user);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //多线程后台给用户发送电子邮件
                String uuid = UUID.randomUUID().toString();
                String url = "http://www.aaa.com/user/active?_="+uuid;
                //放入缓存并等待6小时
                cache.put(uuid, userName);
                String html = "<h3>Dear "+userName+":</h3>请点击<a href='"+url+"'>该链接</a>去激活你的账号. <br> sun";

                EmailUtil.sendHtmlEmail(email,"用户激活邮件",html);
            }
        });
        thread.start();
    }
}

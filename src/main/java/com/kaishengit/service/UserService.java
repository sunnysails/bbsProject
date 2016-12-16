package com.kaishengit.service;

import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.User;
import com.kaishengit.util.Config;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sunny on 2016/12/15.
 */
public class UserService {
    private UserDao userDao = new UserDao();

    /**
     * 校检用户名是否被占用
     *
     * @param userName
     * @return
     */
    public boolean validateUserName(String userName) {
        //检测保留名
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
        user.setPassWord(passWord);
        user.setEmail(email);
        user.setPhone(phone);

        userDao.save(user);
    }
}

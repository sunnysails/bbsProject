package com.kaishengit.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kaishengit.dao.LoginLogDao;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.LoginLog;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Config;
import com.kaishengit.util.EmailUtil;
import com.kaishengit.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.sun.corba.se.impl.util.RepositoryId.cache;

/**
 * Created by sunny on 2016/12/15.
 */
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    private LoginLogDao loginLogDao = new LoginLogDao();

    private UserDao userDao = new UserDao();

    //发送激活邮件的TOKEN缓存
    private static Cache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(6, TimeUnit.HOURS)
            .build();
    //发送找回密码的TOKEN缓存
    private static Cache<String, String> fondCache = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();
    //限制操作频率的缓存
    private static Cache<String, String> activeCache = CacheBuilder.newBuilder()
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .build();

    /**
     * @param token 根据激活邮件种URL 的token 值激活对应的用户
     * @return
     */
    public void activeUser(String token) {
        String userName = cache.getIfPresent(token);
        if (userName == null) {
            throw new ServiceException("token无效或链接已过期");
        } else {
            User user = userDao.findByUserName(userName);
            if (user == null) {
                throw new ServiceException("无法找到对应账号");
            } else {
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
                String url = "http://www.aaa.com/user/active?_=" + uuid;
                //放入缓存并等待6小时
                cache.put(uuid, userName);
                String html = "<h3>Dear " + userName + ":</h3>请点击<a href='" + url + "'>该链接</a>去激活你的账号. <br> sun";

                EmailUtil.sendHtmlEmail(email, "用户激活邮件", html);
            }
        });
        thread.start();
    }

    /**
     * 用户登录
     *
     * @param userName 客户端输入账户
     * @param passWord 客户端输入密码
     * @param ip       客户端IP
     * @return
     */
    public User login(String userName, String passWord, String ip) {
        User user = userDao.findByUserName(userName);
        //查找该用户是否存在及密码验证
        if (user != null && DigestUtils.md5Hex(Config.get("user.password.salt") + passWord).equals(user.getPassWord())) {
            //判断用户的账户状态
            if (user.getState().equals(User.USERSTATE_ACTIVE)) {
                //记录登录日志
                LoginLog log = new LoginLog();
                log.setIp(ip);
                log.setUserId(user.getId());
                loginLogDao.save(log);

                logger.info("{}登录了系统,IP:{}", userName, ip);
                return user;
            } else if (User.USERSTATE_UNACTIVE.equals(user.getState())) {
                throw new ServiceException("帐号未被激活，请到邮箱中激活");
            } else {
                throw new ServiceException("帐号被禁用");
            }
        } else {
            throw new ServiceException("账号或密码错误");
        }
    }

    /**
     * 用户找回密码发送邮件链接操作
     *
     * @param sessionId 客户端sessionId，限制客户端的操作频率
     * @param type      找回密码的方式   email ||  phone
     * @param value     电子邮件地址 | 手机号码
     */
    public void foundPassWord(String sessionId, String type, String value) {
        //判断用户操作间隔是否正常
        if (activeCache.getIfPresent(sessionId) == null) {
            //判断用户找回密码的方式 email | phone
            if ("phone".equals(type)) {
                //TODO 发送验证码
            } else if ("email".equals(type)) {
                //根据邮件地址查找该邮件对应的用户信息
                User user = userDao.findByEmail(value);
                if (user != null) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String uuid = UUID.randomUUID().toString();
                            String url = "http://www.aaa.com/user/restpassword?token=" + uuid;

                            fondCache.put(uuid, user.getUserName());
                            String html = user.getUserName() + "<br>请点击该<a href='" + url + "'>链接</a>进行找回密码操作，链接在30分钟内有效";
                            EmailUtil.sendHtmlEmail(value, "密码找回邮件", html);
                        }
                    });
                    thread.start();
                } else {
                    throw new ServiceException("用户不存在");
                }
            }
            activeCache.put(sessionId, "占位，无意义");
        } else {
            throw new ServiceException("操作频率过快");
        }
    }

    /**
     * 根据找回密码的链接获取找回密码的用户
     *
     * @param token
     * @return
     */
    public User foundPasswordGetUserByToken(String token) {
        String userName = fondCache.getIfPresent(token);
        if (StringUtils.isEmpty(userName)) {
            throw new ServiceException("token过期或不存在");
        } else {
            User user = userDao.findByUserName(userName);
            if (user == null) {
                throw new ServiceException("未找到对应的账户，请检查后重试");
            } else {
                return user;
            }
        }
    }

    /**
     * 用户密码重置
     *
     * @param id       用户ID
     * @param token    找回密码的TOKEN值
     * @param passWord 用户设置的新密码
     */
    public void restPassWord(String id, String token, String passWord) {
        if (fondCache.getIfPresent(token) == null) {
            throw new ServiceException("token已过期或链接已失效");
        } else {
            User user = userDao.findById(Integer.valueOf(id));
            user.setPassWord(DigestUtils.md5Hex(Config.get("user.password.salt") + passWord));
            userDao.update(user);

            logger.info("{}重置了密码", user.getUserName());
        }
    }
}

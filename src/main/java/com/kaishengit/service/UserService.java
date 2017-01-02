package com.kaishengit.service;

import com.google.common.base.Predicate;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.kaishengit.dao.LoginLogDao;
import com.kaishengit.dao.NotifyDao;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.LoginLog;
import com.kaishengit.entity.Notify;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.*;
import com.kaishengit.vo.UserVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by sunny on 2016/12/15.
 */
public class UserService {
    private String emailUrl=Config.get("email.url");

    private Logger logger = LoggerFactory.getLogger(UserService.class);
    private LoginLogDao loginLogDao = new LoginLogDao();
    private NotifyDao notifyDao = new NotifyDao();
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
     * 根据激活邮件种URL 的token 值激活对应的用户
     *
     * @param token
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
                logger.info("{}激活了账号", user.getUserName());
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
                String url = emailUrl + uuid;
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
                //拼装avatar的完整路径
                user.setAvatar(User.QINIUDOMAIN + user.getAvatar());
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
                            String url = emailUrl + uuid;

                            fondCache.put(uuid, user.getUserName());

                            String html = user.getUserName() + "<br>请点击该<a href='" + url + "'>链接</a>进行找回密码操作，链接在30分钟内有效";
                            EmailUtil.sendHtmlEmail(value, "密码找回邮件", html);

                            logger.info("{}使用了通过邮件找回密码功能", user.getUserName());
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

            //删除作为验证的TOKEN值。
            fondCache.invalidate(token);
            logger.info("{}重置了密码", user.getUserName());
        }
    }

    /**
     * 更改已登录用户email
     *
     * @param user  更改邮箱的用户
     * @param email 新邮箱
     */
    public void updateEmail(User user, String email) {
        user.setEmail(email);
        userDao.update(user);

        logger.info("用户：{}，更改了新邮箱", user.getUserName());
    }

    /**
     * 更改已登录用户密码
     *
     * @param user        用户
     * @param oldPassWord 旧密码
     * @param newPassWord 新密码
     */
    public void updatePassWord(User user, String oldPassWord, String newPassWord) {
        if (DigestUtils.md5Hex(Config.get("user.password.salt") + oldPassWord).equals(user.getPassWord())) {
            newPassWord = DigestUtils.md5Hex(Config.get("user.password.salt") + newPassWord);
            user.setPassWord(newPassWord);
            userDao.update(user);

            logger.info("用户：{}，更改了密码", user.getUserName());
        } else {
            throw new ServiceException("原始密码错误,操作失败，请检查后重试");
        }
    }

    /**
     * 更改头像,同时删除旧头像
     *
     * @param user    用户
     * @param fileKey 文件名
     */
    public void updateAvatar(User user, String fileKey) {
        if (user.getAvatar().equals("default-avatar")) {
            QiNiuService qiNiuService = new QiNiuService();
            qiNiuService.delAfterUpdate(user);
        }

        user.setAvatar(fileKey);
        userDao.update(user);

        logger.info("用户{}更改了头像", user.getUserName());
    }

    public List<Notify> findNotifyListByUser(User user) {
        logger.debug("查询通知列表");
        return notifyDao.findByUserId(user.getId());
    }

    /**
     * 根据user 查出notifyList,同时进行过滤，选择出unreadNotifyList
     *
     * @param user user对象
     * @return unReadNotifyList，未读通知列表
     */
    public List<Notify> findUnreadNotifyListByUser(User user) {
        logger.debug("查询未读通知列表");
        return Lists.newArrayList(Collections2.filter(notifyDao.findByUserId(user.getId()), new Predicate<Notify>() {
            @Override
            public boolean apply(Notify notify) {
                return notify.getState() == 0;
            }
        }));
    }

    public void updateNotifyStateByIds(String ids) {
        String idArray[] = ids.split(",");
        for (int i = 0; i < idArray.length; i++) {
            Notify notify = notifyDao.findById(Integer.valueOf(idArray[i]));
            notify.setState(Notify.NOTIFY_STATE_READ);
            notify.setReadTime(new Timestamp(DateTime.now().getMillis()));
            notifyDao.update(notify);
        }
    }

    public Page<UserVo> findUserList(String p) {
        Integer pageNo = StringUtils.isNumeric(p) ? Integer.parseInt(p) : 1;
        Integer count = userDao.count();
        Page<UserVo> page = new Page<>(count, pageNo);
        List<UserVo> userVoList = userDao.findUserVoByPageNo(page.getStart(), Page.PAGE);
        page.setItems(userVoList);
        return page;
    }

    public void updateState(String id, String state) {
        if (StringUtils.isNumeric(id) && StringUtils.isNumeric(state)) {
            Integer s = Integer.valueOf(state) == 1 ? 2 : 1;
            User user = userDao.findById(Integer.valueOf(id));
            user.setState(s);
            userDao.update(user);
            logger.info("更改了用户：{}，的状态码为{}",user.getUserName(),state);
        } else {
            throw new ServiceException("参数错误！");
        }
    }
}
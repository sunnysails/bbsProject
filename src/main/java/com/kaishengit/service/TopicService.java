package com.kaishengit.service;

import com.kaishengit.dao.*;
import com.kaishengit.entity.*;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Page;
import com.kaishengit.util.StringUtils;
import com.kaishengit.vo.TopicReplyCountVo;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sunny on 2016/12/21.
 */
public class TopicService {
    private Logger logger = LoggerFactory.getLogger(TopicService.class);

    private TopicDao topicDao = new TopicDao();
    private UserDao userDao = new UserDao();
    private NodeDao nodeDao = new NodeDao();
    private ReplyDao replyDao = new ReplyDao();
    private FavDao favDao = new FavDao();
    private NotifyDao notifyDao = new NotifyDao();

    /**
     * 存储新帖子并返回该 Topic 对象
     *
     * @param title   主题
     * @param content 内容
     * @param nodeId  关联板块ID
     * @param userId  发帖人ID
     * @return Topic对象
     */
    public Topic addNewTopic(String title, String content, String nodeId, Integer userId) {
        //封装对象topic
        if (StringUtils.isNumeric(nodeId)) {
            Topic topic = new Topic();
            topic.setTitle(title);
            topic.setContent(content);
            topic.setNodeId(Integer.valueOf(nodeId));
            topic.setUserId(userId);
            //暂时设置新帖最后回复时间与发布时间一致
            topic.setLastReplyTime(new Timestamp(new DateTime().getMillis()));
            Integer topicId = topicDao.save(topic);
            topic.setId(topicId);

            //更新node表的topicnum
            Node node = nodeDao.findById(Integer.valueOf(nodeId));
            if (node != null) {
                node.setTopicNum(node.getTopicNum() + 1);
                nodeDao.save(node);
            } else {
                throw new ServiceException("论坛板块不存在");
            }

            logger.info("Id为{}在{}板块ID：{}发表了主题为：{}帖子", userId, topic.getCreateTime(), nodeId, title);
            return topic;
        } else {
            throw new ServiceException("参数错误！");
        }
    }

    /**
     * 根据Id 查询帖子,判断参数是否正确，判断帖子是否存在。
     *
     * @param topicId 需要查询的帖子的ID
     * @return topic对象
     */
    public Topic findTopicById(String topicId) {
        if (StringUtils.isNumeric(topicId)) {
            Topic topic = topicDao.findById(Integer.valueOf(topicId));
            if (topic != null) {
                User user = userDao.findById(topic.getUserId());
                Node node = nodeDao.findById(topic.getNodeId());
                user.setAvatar(User.QINIUDOMAIN + user.getAvatar());
                topic.setUser(user);
                topic.setNode(node);

                logger.debug("{}查看了帖子，时间为{}，主题为：{}", topic.getUser().getUserName(), topic.getCreateTime(), topic.getTitle());
                //TODO  bug 待调整，logger错误。
                return topic;
            } else {
                throw new ServiceException("该帖子不存在，或已被删除");
            }
        } else {
            throw new ServiceException("参数错误！");
        }
    }

    /**
     * 返回一个reply集合
     *
     * @param topicId 需要查询的帖子的ID
     * @return replyList
     */
    public List<Reply> findReplyListByTopicId(String topicId) {
        return replyDao.findListByTopicId(topicId);
    }

    /**
     * 添加新回复
     *
     * @param topicId 回复帖子ID
     * @param content 回复内容
     * @param user    回复用户对象
     */
    public void addTopicReply(String topicId, String content, User user) {
        if (StringUtils.isNotEmpty(content)) {
            if (StringUtils.isNumeric(topicId)) {
                //新增回复到t_reply表
                Reply reply = new Reply();
                reply.setContent(content);
                reply.setUserId(user.getId());
                reply.setTopicId(Integer.valueOf(topicId));
                replyDao.addReply(reply);
                //更新t_topic表中的replynum 和 lastreplytime字段
                Topic topic = topicDao.findById(Integer.valueOf(topicId));
                if (topic != null) {
                    topic.setReplyNum(topic.getReplyNum() + 1);
                    Timestamp now = new Timestamp(DateTime.now().getMillis());
                    topic.setLastReplyTime(now);
                    topicDao.update(topic);

                    logger.info("{}在“{}”时间回复了主题为“{}”的帖子", user.getUserName(), now, topic.getTitle());
                } else {

                    logger.info("ID“{}”帖子查询出错", topicId);
                    throw new ServiceException("回复的帖子不存在或已被删除");
                }
                //新增回复通知
                if (!user.getId().equals(topic.getUserId())) {
                    Notify notify = new Notify();
                    notify.setUserId(topic.getUserId());
                    notify.setContent("您的主题 <a href=\"/topicdetail?topicId=" + topic.getId() + "\">[" + topic.getTitle() + "] </a> 有了新的回复,请查看.");
                    notify.setState(Notify.NOTIFY_STATE_UNREAD);
                    notifyDao.save(notify);
                }
            } else {
                logger.debug("{}", topicId);
                throw new ServiceException("参数错误");
            }
        } else {
            throw new ServiceException("回复不能为空");
        }
    }

    /**
     * 通过板块Id和页码查询数据库信息
     *
     * @param nodeId 板块Id
     * @param pageNo 当前页码
     * @return 一个页面内的topic对象集合
     */
    public Page<Topic> findAllTopicBPANi(String nodeId, String pageNo) {
        Integer p = StringUtils.isNumeric(pageNo) ? Integer.valueOf(pageNo) : 1;
        int count = 0;
        if (StringUtils.isNumeric(nodeId)) {
            count = nodeDao.findById(Integer.valueOf(nodeId)).getTopicNum();

            logger.debug("当前版块总数量{}", count);
        } else {
            count = topicDao.count().intValue();

            logger.debug("当前所有帖子总数量{}...", count);
        }
        count = count == 0 ? 1 : count;

        List<Topic> topicList = null;
        Page<Topic> topicPageList = new Page<>(count, p);

        if (StringUtils.isNumeric(nodeId)) {
            topicList = topicDao.findAllByNodeIdAndPage(nodeId, topicPageList.getStart(), Page.PAGE);
        } else {
            topicList = topicDao.findAllByPage(topicPageList.getStart(), Page.PAGE);
        }
        topicPageList.setItems(topicList);

        logger.debug("查询了“{}”第{}页数据,总共{}页", topicPageList.getStart(), p, Page.PAGE);
        return topicPageList;
    }

    public void updateTopic(Topic topic) {
        topicDao.update(topic);
    }

    /**
     * 统计帖子点击量
     *
     * @param topicId 需要统计的帖子Id
     * @param user
     * @return
     */
    public Fav findFavByUserIdAndTopicId(String topicId, User user) {
        if (StringUtils.isNumeric(topicId)) {

            logger.debug("{}点击Id为：{}的帖子", user.getUserName(), topicId);
            return favDao.findById(user.getId(), Integer.valueOf(topicId));
        } else {
//            logger.debug("fav查询错误，userId：{}，topicId{}", user.getId(), topicId);
            throw new ServiceException("参数错误！");
        }
    }

    /**
     * 根据FAV 或者 UNFAN 进行决定收藏或取消收藏。
     *
     * @param action  当前状态fav
     * @param user
     * @param topicId
     */
    public void favOrUNTopic(String action, User user, String topicId) {
        if (user != null && StringUtils.isNumeric(topicId)) {
            Fav fav = new Fav();
            Topic topic = topicDao.findById(Integer.valueOf(topicId));
            if (action.equals("fav")) {
                fav.setTopicId(Integer.valueOf(topicId));
                fav.setUserId(user.getId());
                favDao.addFav(fav);

                logger.debug("{}fav了", user.getUserName());
                topic.setFavNum(topic.getFavNum() + 1);
            } else if (action.equals("unfav")) {
                favDao.deleteFavById(user.getId(), Integer.valueOf(topicId));

                logger.debug("{}取消了fav", user.getUserName());
                topic.setFavNum(topic.getFavNum() - 1);
            } else {
                throw new ServiceException("参数错误！");
            }

            topicDao.update(topic);
            logger.debug("{}fav数量", topic.getFavNum());
        } else {
            logger.debug("action={},userId={},topicId{},错误1", action, user.getId(), topicId);
            throw new ServiceException("参数错误！");
            //TODO,
        }
    }

    /**
     * 更新帖子
     *
     * @param title
     * @param content
     * @param nodeId
     * @param topicId
     */
    public void updateTopicById(String title, String content, String nodeId, String topicId) {
        if (StringUtils.isNumeric(nodeId) && StringUtils.isNumeric(topicId)) {
            Integer nId = Integer.valueOf(nodeId);
            Integer tId = Integer.valueOf(topicId);

            Topic topic = topicDao.findById(tId);
            Integer oldNodeId = topic.getNodeId();
            if (topic.isEdit()) {
                topic.setTitle(title);
                topic.setContent(content);
                topic.setNodeId(nId);
                topicDao.update(topic);
                logger.info("Id“{}”用户修改了：“{}”帖子", topic.getUserId(), topic.getTitle());
                if (oldNodeId != nId) {
                    //更新node表，使得原來的node的topicnum -1
                    Node oldNode = nodeDao.findById(nId);
                    oldNode.setTopicNum(oldNode.getTopicNum() - 1);
                    nodeDao.update(oldNode);
                    //更新node表，使得新的node的topicnum + 1
                    Node newNode = nodeDao.findById(nId);
                    newNode.setTopicNum(newNode.getTopicNum() + 1);
                    nodeDao.update(newNode);
                    return;
                }
            } else {
                logger.debug("删除");
                throw new ServiceException("该帖子正在编辑或已被删除请稍候再试或者联系管理员。");
            }
        } else {

            logger.error("错误");
            throw new ServiceException("参数错误！");
        }
    }

    public Page<TopicReplyCountVo> getTopicAndReplyNumByDayList(String p) {
        Integer pageNo = StringUtils.isNumeric(p) ? Integer.valueOf(p) : 1;
        int count = topicDao.countTopicByDay();
        Page<TopicReplyCountVo> page = new Page<>(count, pageNo);
        List<TopicReplyCountVo> topicReplyCountVoList = topicDao.getTopicAndReplyNumList(page.getStart(), Page.PAGE);
        page.setItems(topicReplyCountVoList);
        return page;
    }
}
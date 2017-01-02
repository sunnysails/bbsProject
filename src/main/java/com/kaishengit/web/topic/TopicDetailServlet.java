package com.kaishengit.web.topic;

import com.kaishengit.entity.Fav;
import com.kaishengit.entity.Reply;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by sunny on 2016/12/21.
 */
@WebServlet("/topicdetail")
public class TopicDetailServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("topicId");
        TopicService topicService = new TopicService();
        try {
            Topic topic = topicService.findTopicById(topicId);
            List<Reply> replyList = topicService.findReplyListByTopicId(topicId);

            topic.setClickNum(topic.getClickNum() + 1);
            topicService.updateTopic(topic);

            req.setAttribute("topic", topic);
            req.setAttribute("replyList", replyList);

            User user = getCurrentUser(req);
            if (user != null) {
                Fav fav = topicService.findFavByUserIdAndTopicId(topicId, user);
                req.setAttribute("fav", fav);
            }

            forWard("topic/topicdetail", req, resp);
        } catch (ServiceException e) {
            resp.sendError(404, e.getMessage());
        }
    }
}

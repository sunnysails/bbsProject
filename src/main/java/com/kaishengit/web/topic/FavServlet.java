package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
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

/**
 * Created by sunny on 2016/12/25.
 */
@WebServlet("/topicfav")
public class FavServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String topicId = req.getParameter("topicId");
        User user = (User) req.getSession().getAttribute("curr_user");
        TopicService topicService = new TopicService();
        JsonResult result = new JsonResult();
        try {
            topicService.favOrUNTopic(action, user, topicId);
            Topic topic = topicService.findTopicById(topicId);
            result.setState(JsonResult.SUCCESS);
            result.setData(topic.getFavNum());
        } catch (ServiceException e) {
            result.setMessage(e.getMessage());
        }
        renderJSON(result,resp);
    }
}

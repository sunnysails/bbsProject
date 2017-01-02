package com.kaishengit.web.topic;

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
 * Created by sunny on 2016/12/23.
 */
@WebServlet("/newreply")
public class NewReplyServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("topicId");
        String content = req.getParameter("content");
        User user = getCurrentUser(req);
        TopicService topicService = new TopicService();
        try {
            topicService.addTopicReply(topicId, content, user);
        } catch (ServiceException e) {
            resp.sendError(501,e.getMessage());
            return;
        }
        resp.sendRedirect("/topicdetail?topicId="+topicId);
    }
}

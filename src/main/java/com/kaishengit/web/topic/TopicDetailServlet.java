package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Reply;
import com.kaishengit.entity.Topic;
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
            req.setAttribute("topic", topic);
            req.setAttribute("replyList",replyList);
            forWord("topic/topicdetail", req, resp);
        } catch (ServiceException e) {
            resp.sendError(404,e.getMessage());
            System.out.println(e.getMessage());
            //TODO logger
        }
    }
}

package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
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
@WebServlet("/newtopic")
public class NewTopicServlet extends BaseServlet {
    private TopicService topicService = new TopicService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取nodeList到页面
        List<Node> nodeList = topicService.findAllNode();
        req.setAttribute("nodeList", nodeList);
        forWord("topic/newtopic", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String nodeId = req.getParameter("nodeId");
        User user = (User) req.getSession().getAttribute("curr_user");
        Topic topic = topicService.addNewTopic(title, content, Integer.valueOf(nodeId), user.getId());

        JsonResult result = new JsonResult(topic);
        renderJSON(result, resp);
    }
}

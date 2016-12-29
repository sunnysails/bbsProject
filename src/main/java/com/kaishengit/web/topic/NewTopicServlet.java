package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.QiNiuService;
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
        QiNiuService qiNiuService = new QiNiuService();
        String token = qiNiuService.upload();

        //获取nodeList到页面
        List<Node> nodeList = topicService.findAllNode();
        req.setAttribute("nodeList", nodeList);
        req.setAttribute("token",token);
        forWard("topic/newtopic", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String nodeId = req.getParameter("nodeId");
        User user = getCurrentUser(req);
        JsonResult result = new JsonResult();

        try {
            Topic topic = topicService.addNewTopic(title, content, nodeId, user.getId());
            result.setData(topic);
        } catch (ServiceException e) {
            result.setMessage(e.getMessage());
        }
        renderJSON(result, resp);
    }
}
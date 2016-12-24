package com.kaishengit.web;

import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by sunny on 2016/12/17.
 */
@WebServlet("/home")
public class HomeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取nodeList到页面
        String nodeId = req.getParameter("nodeId");
        String p= req.getParameter("p");

        TopicService topicService = new TopicService();
        Page<Topic> page = topicService.findAllTopicBPANi(nodeId, p);

        List<Node> nodeList = topicService.findAllNode();

        if (page == null) {
            forWord("index", req, resp);
            return;
        }
        List<Topic> topicList = page.getItems();
        req.setAttribute("nodeList", nodeList);
        req.setAttribute("page", page);
        req.setAttribute("topicList", topicList);
        forWord("index", req, resp);
    }
}
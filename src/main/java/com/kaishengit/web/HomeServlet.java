package com.kaishengit.web;

import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.service.TopicService;

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
        TopicService topicService = new TopicService();
        List<Topic> topicList = topicService.findAllTopic();
        List<Node> nodeList = topicService.findAllNode();
        req.setAttribute("nodeList",nodeList);
        req.setAttribute("topicList", topicList);
        forWord("index",req, resp);
    }
}
package com.kaishengit.web.admin;

import com.kaishengit.service.TopicService;
import com.kaishengit.util.Page;
import com.kaishengit.vo.TopicReplyCountVo;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sunny on 2016/12/29.
 */
@WebServlet("/admin/home")
public class AdminHomeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p = req.getParameter("p");
        Page<TopicReplyCountVo> page = new TopicService().getTopicAndReplyNumByDayList(p);
req.setAttribute("page",page);
        forWard("admin/home",req,resp);
    }
}
package com.kaishengit.web.user;

import com.google.common.collect.Maps;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by sunny on 2016/12/17.
 */
@WebServlet("/foundpassword")
public class FoundPassWordServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forWard("user/foundpassword", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String value = req.getParameter("value");
        String type = req.getParameter("type");
        //获取当前客户端的sessionId
        String sessionId = req.getSession().getId();

        Map<String, Object> result = Maps.newHashMap();
        UserService userService = new UserService();
        try {
            userService.foundPassWord(sessionId, type, value);
            result.put("state", "success");
        } catch (ServiceException e) {
            result.put("state", "error");
            result.put("message", e.getMessage());
        }
        renderJSON(result, resp);
    }
}

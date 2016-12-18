package com.kaishengit.web.user;

import com.google.common.collect.Maps;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.util.StringUtils;
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
@WebServlet("/user/restpassword")
public class RestPassWordServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            resp.sendError(404);
        } else {
            //根据TOKEN 确定USERNAME  然后查出该USER
            UserService userService = new UserService();
            try {
                User user = userService.foundPasswordGetUserByToken(token);
                req.setAttribute("token", token);
                req.setAttribute("user", user);
                forWord("user/restpassword", req, resp);
            } catch (ServiceException e) {
                req.setAttribute("message", e.getMessage());
                forWord("user/reset_error", req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String token = req.getParameter("token");
        String passWord = req.getParameter("passWord");

        Map<String ,Object> result = Maps.newHashMap();
        UserService userService = new UserService();
        try {
            userService.restPassWord(id, token, passWord);
            result.put("state", "success");
        }catch (ServiceException e){
            result.put("state","error");
            result.put("message",e.getMessage());
        }
        renderJSON(result,resp);
    }
}

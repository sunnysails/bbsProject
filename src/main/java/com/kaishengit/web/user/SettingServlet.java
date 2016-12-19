package com.kaishengit.web.user;

import com.google.common.collect.Maps;
import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.User;
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
 * Created by sunny on 2016/12/19.
 */
@WebServlet("/setting")
public class SettingServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forWord("user/setting", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("profile".equals(action)) {
            updateProfile(req, resp);
        } else if ("password".equals(action)) {
            updatePassword(req, resp);
        }
    }

    //修改密码
    private void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String oldPassWord = req.getParameter("oldPassWord");
        String newPassWord = req.getParameter("newPassWord");

        User user = getCurrentUser(req);
        try {
            UserService userService = new UserService();
            userService.updatePassWord(user, oldPassWord, newPassWord);

            JsonResult result = new JsonResult();
            result.setState(JsonResult.SUCCESS);

            renderJSON(result, resp);
        } catch (ServiceException e) {
            JsonResult result = new JsonResult(e.getMessage());
            renderJSON(result, resp);
        }
    }

    //修改电子邮件
    private void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        User user = getCurrentUser(req);

        UserService userService = new UserService();
        userService.updateEmail(user, email);

        JsonResult result = new JsonResult();
        result.setState(JsonResult.SUCCESS);
        renderJSON(result, resp);
    }
}
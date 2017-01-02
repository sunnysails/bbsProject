package com.kaishengit.web.user;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.util.RandomValidateCode;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by sunny on 2016/12/15.
 */
@WebServlet("/login")
public class LoginServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forWard("user/login", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String passWord = req.getParameter("passWord");
        String validatecode = req.getParameter("validatecode");
        JsonResult result = new JsonResult();
        if (RandomValidateCode.validateCode != null && validatecode.trim().toUpperCase().equals(RandomValidateCode.validateCode)) {
            System.out.println("验证码正确");
            //获取客户端Ip
            String ip = req.getLocalAddr();
            UserService userService = new UserService();
            try {
                User user = userService.login(userName, passWord, ip);
                //将登录成功的用户放入Session
                HttpSession session = req.getSession();
                session.setAttribute("curr_user", user);
                result.setState(JsonResult.SUCCESS);
            } catch (ServiceException e) {
                result.setMessage(e.getMessage());
            }
        } else {
            result.setMessage("验证码错误！");
        }
        renderJSON(result, resp);
    }
}
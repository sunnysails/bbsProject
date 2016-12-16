package com.kaishengit.web.user;

import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;
import com.kaishengit.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sunny on 2016/12/16.
 */
@WebServlet("/validate/user")
public class ValidateUserServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        //处理URL中的中文问题
        userName = StringUtils.isoToUTF8(userName);

        UserService userService = new UserService();

        Boolean result = userService.validateUserName(userName);

        if (result) {
            renderText("true", resp);
        } else {
            renderText("false", resp);
        }
    }
}

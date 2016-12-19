package com.kaishengit.web.user;

import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.util.StringUtils;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sunny on 2016/12/16.
 */
@WebServlet("/validate/email")
public class ValidateEmailSServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String type = req.getParameter("type");

        //判断TYPE是否存在，若存在且值为“1”则继续
        if (StringUtils.isNotEmpty(type) && "1".equals(type)) {
            User currentUser = getCurrentUser(req);
            //
            if (currentUser != null) {
                //判断email，是否存在。
                if (currentUser.getEmail().equals(email)) {
                    renderText("true", resp);
                    return;
                }
            }
        }

        UserService userService = new UserService();
        User user = userService.findByEmail(email);

        if (user == null) {
            renderText("true", resp);
        } else {
            renderText("false", resp);
        }
    }
}

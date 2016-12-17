package com.kaishengit.web.user;

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
@WebServlet("/user/active")
public class ActiveUserServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("_");
        if (StringUtils.isEmpty(token)){
            resp.sendError(404);
        }else{
            UserService userService = new UserService();
            try{
                userService.activeUser(token);
                forWord("user/active_success",req,resp);
            }catch (ServletException e){
                req.setAttribute("message",e.getMessage());
                forWord("user/active_error", req, resp);
            }
        }
    }
}

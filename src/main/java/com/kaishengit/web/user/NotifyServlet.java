package com.kaishengit.web.user;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Notify;
import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by sunny on 2016/12/27.
 */
@WebServlet("/notify")
public class NotifyServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getCurrentUser(req);
        List<Notify> notifyList = new UserService().findNotifyListByUser(user);
        req.setAttribute("notifyList",notifyList);
        forWard("user/notify",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getCurrentUser(req);
        List<Notify> unreadList = new UserService().findUnreadNotifyListByUser(user);
        JsonResult result = new JsonResult(unreadList.size());
        renderJSON(result,resp);
    }
}
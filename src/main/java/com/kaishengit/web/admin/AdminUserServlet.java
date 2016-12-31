package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.util.Page;
import com.kaishengit.vo.UserVo;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sunny on 2016/12/31.
 */
@WebServlet("/admin/user")
public class AdminUserServlet extends BaseServlet {
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p = req.getParameter("p");
        Page<UserVo> page = userService.findUserList(p);
        req.setAttribute("page", page);
        forWard("admin/user", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stateId = req.getParameter("stateId");
        String[] all = stateId.split(",");
        String id = all[0];
        String state = all[1];
        JsonResult result = new JsonResult();
        try {
            userService.updateState(id, state);
            result.setState(JsonResult.SUCCESS);
        } catch (ServiceException e) {
            result.setMessage(e.getMessage());
        }
        renderJSON(result, resp);
    }
}

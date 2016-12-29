package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Admin;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.AdminService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by sunny on 2016/12/28.
 */
@WebServlet("/admin/login")
public class AdminLoginServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forWard("admin/login", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String adminName = req.getParameter("adminName");
        String passWord = req.getParameter("passWord");
        String ip = req.getLocalAddr();
        JsonResult result = new JsonResult();
        AdminService adminService = new AdminService();

        try {
            Admin admin = adminService.login(adminName, passWord, ip);

            HttpSession session = req.getSession();
            session.setAttribute("curr_admin", admin);
            result.setState(JsonResult.SUCCESS);
        } catch (ServiceException e) {
            result.setMessage(e.getMessage());
        }
        renderJSON(result, resp);
    }
}

package com.kaishengit.web.admin;

import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by sunny on 2016/12/31.
 */
@WebServlet("/admin/loginout")
public class AdminLoginOutServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();

        req.setAttribute("message", "您已安全退出");
        forWard("admin/login", req, resp);
    }
}

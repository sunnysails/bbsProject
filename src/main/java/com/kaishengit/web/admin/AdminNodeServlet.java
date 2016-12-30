package com.kaishengit.web.admin;

import com.kaishengit.entity.Node;
import com.kaishengit.service.NodeService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by sunny on 2016/12/30.
 */
@WebServlet("/admin/node")
public class AdminNodeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Node> nodeList = new NodeService().findAllNode();
        req.setAttribute("nodeList",nodeList);
        forWard("admin/node",req,resp);
    }
}

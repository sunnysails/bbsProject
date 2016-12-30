package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.NodeService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sunny on 2016/12/30.
 */
@WebServlet("/admin/nodedel")
public class NodeDelServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        NodeService nodeService = new NodeService();
        JsonResult result = new JsonResult();
        try {
            nodeService.delNodeById(id);
            result.setState(JsonResult.SUCCESS);
        } catch (ServiceException e) {
            result.setMessage(e.getMessage());
        }
        renderJSON(result, resp);
    }
}

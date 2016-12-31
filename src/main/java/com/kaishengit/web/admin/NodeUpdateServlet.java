package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.NodeService;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sunny on 2016/12/30.
 */
@WebServlet("/admin/nodeupdate")
public class NodeUpdateServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeId = req.getParameter("nodeId");
        try{
            Node node = new NodeService().findNodeById(nodeId);
            req.setAttribute("node",node);
            forWard("admin/nodeupdate",req,resp);
        }catch (ServiceException e){
            resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeId = req.getParameter("nodeId");
        String nodeName = req.getParameter("nodeName");
        JsonResult result = new JsonResult();
        try{
            new NodeService().updateNode(nodeId, nodeName);
            result.setState(JsonResult.SUCCESS);
        }catch (ServiceException e){
            result.setMessage(e.getMessage());
        }
        renderJSON(result,resp);
    }
}

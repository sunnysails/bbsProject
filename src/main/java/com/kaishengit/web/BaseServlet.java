package com.kaishengit.web;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by sunny on 2016/12/15.
 */
public class BaseServlet extends HttpServlet {

    public void forWord(String path, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/" + path + ".jsp").forward(req, resp);
    }

    /**
     * 给客户端响应一个JSON数据
     *
     * @param object   要被转成JSON的对象
     * @param response
     * @throws IOException
     */
    public void renderJSON(Object object, HttpServletResponse response) throws IOException {
        String json = new Gson().toJson(object);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * @param str
     * @param resp
     * @throws IOException
     */
    public void renderText(String str, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain;charset=UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.print(str);
        writer.flush();
        writer.close();
    }
}

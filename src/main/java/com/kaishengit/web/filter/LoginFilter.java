package com.kaishengit.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by sunny on 2016/12/19.
 */
public class LoginFilter extends AbstractFilter {
    private List<String> urlList = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String validateUrl = filterConfig.getInitParameter("validateUrl");
        urlList = Arrays.asList(validateUrl.split(","));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        //获取用户要访问的URL，
        String requestUrl = req.getRequestURI();

        if (urlList != null && urlList.contains(requestUrl)) {
            if (req.getSession().getAttribute("curr_user") == null) {
                Map map = req.getParameterMap();
                Set paramSet = map.entrySet();
                Iterator it = paramSet.iterator();
                if (it.hasNext()){
                    requestUrl += "?";

                    while(it.hasNext()){
                        Map.Entry me = (Map.Entry) it.next();
                        Object key = me.getKey();
                        Object value = me.getValue();
                        String valString[] = (String[]) value;
                        String param = "";
                        for(int i=0;i<valString.length;i++){
                            param = key + "=" + valString[i] +"&";
                            requestUrl += param;
                        }
                    }
                    requestUrl = requestUrl.substring(0,requestUrl.length()-1);
                    System.out.println("requestUrl = " + requestUrl);
                }
                //去登录页面
                resp.sendRedirect("/login?redirect=" + requestUrl);
            } else {
                filterChain.doFilter(req, resp);
            }
        } else {
            filterChain.doFilter(req, resp);
        }
    }
}

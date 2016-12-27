<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2016/12/15
  Time: 21:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="header-bar">
    <div class="container">
        <a href="/home" class="brand">
            <i class="fa fa-flag" title="首页"></i>
        </a>
        <ul class="unstyled inline pull-right">
            <c:choose>
                <c:when test="${not empty sessionScope.curr_user}">
                    <li>
                        <a href="/setting" title="个人中心">
                            <img id="navbar_avatar" src="${sessionScope.curr_user.avatar}?imageView2/1/w/20/h/20"
                                 class="img-circle" alt="">
                        </a>
                    </li>
                    <li><a href="/newtopic"><i class="fa fa-plus" title="发帖"></i></a></li>
                    <li><a href="#"><i class="fa fa-bell" title="通知"></i></a></li>
                    <li><a href="/setting"><i class="fa fa-cog" title="设置"></i></a></li>
                    <li><a href="/logout"><i class="fa fa-sign-out" title="退出"></i></a></li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="/setting" title="个人中心">
                            <img src="http://oi245j9g2.bkt.clouddn.com/default-avatar.jpg?imageView2/1/w/20/h/20"
                                 class="img-circle" alt="">
                        </a>
                    </li>
                    <li><a href="/login" title="登录"><i class="fa fa-sign-in"></i></a></li>
                    <li><a href="/reg" title="注册"><i class="fa fa-user-plus"></i></a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
<!--header-bar end-->
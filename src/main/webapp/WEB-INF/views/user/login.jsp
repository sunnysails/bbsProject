<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2016/12/15
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/dist/sweetalert.css">
</head>
<body>

<%@include file="../include/navbar.jsp" %>

<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 登录</span>
        </div>

        <form id="loginForm" action="" class="form-horizontal">
            <c:if test="${not empty requestScope.message}">
                <div class="alert alert-success">
                        ${requestScope.message}
                </div>
            </c:if>
            <c:if test="${not empty param.redirect}">
                <div class="alert alert-success">
                    请登录后再继续操作
                </div>
            </c:if>
            <div class="control-group">
                <label id="userName" class="control-label">账号</label>
                <div class="controls">
                    <input type="text" name="userName">
                </div>
            </div>
            <div class="control-group">
                <label id="passWord" class="control-label">密码</label>
                <div class="controls">
                    <input type="password" name="passWord">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label"></label>
                <div class="controls">
                    <a href="foundpassword">忘记密码</a>
                </div>
            </div>

            <div class="form-actions">
                <button id="loginBtn" class="btn btn-primary">登录</button>

                <a class="pull-right" href="/reg">注册账号</a>
            </div>
        </form>
    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/dist/sweetalert-dev.js"></script>
<script type="text/javascript" src="/static/js/user/login.js"></script>
</body>
</html>

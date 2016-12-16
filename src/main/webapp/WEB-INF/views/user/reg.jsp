<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2016/12/15
  Time: 21:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>注册用户</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="../include/navbar.jsp" %>
    <div class="container">
        <div class="box">
            <div class="box-header">
                <span class="title"><i class="fa fa-sign-in"></i> 注册账号</span>
            </div>

            <form id="regForm" action="" class="form-horizontal">
                <div class="control-group">
                    <label class="control-label">账号</label>
                    <div class="controls">
                        <input type="text" name="userName">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">密码</label>
                    <div class="controls">
                        <input type="text" name="passWord">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">重复密码</label>
                    <div class="controls">
                        <input type="text" name="dpassWord">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">电子邮件</label>
                    <div class="controls">
                        <input type="text" name="email">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">手机号码</label>
                    <div class="controls">
                        <input type="text" name="phone">
                    </div>
                </div>
                <div class="form-actions">
                    <button id="regBtn" class="btn btn-primary">注册</button>
                    <a class="pull-right" href="/login">登录</a>
                </div>
            </form>
        </div>
        <!--box end-->
    </div>
    <!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/user/reg.js"></script>
</body>
</html>

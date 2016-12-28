<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2016/12/18
  Time: 18:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>重置密码</title>
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
            <span class="title">重置密码</span>
        </div>

        <form id="restForm" action="" class="form-horizontal">
            <input type="hidden" name="id" value="${requestScope.user.id}">
            <input type="hidden" name="token" value="${requestScope.token}">
            <div class="control-group">
                <label class="control-label">请输入新密码</label>
                <div class="controls">
                    <input type="password" name="passWord" id="passWord">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">请重复输入密码</label>
                <div class="controls">
                    <input type="password" name="rePassWord">
                </div>
            </div>

            <div class="form-actions">
                <button id="restBtn" type="button" class="btn btn-primary">确定</button>

                <a class="pull-right" href="/login">取消</a>
            </div>
        </form>
    </div>
</div>
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/dist/sweetalert-dev.js"></script>
<script src="/static/js/user/restpassword.js"></script>
<script src="/static/js/user/notify.js"></script>
</body>
</html>

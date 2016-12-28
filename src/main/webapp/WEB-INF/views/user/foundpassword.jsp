<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2016/12/17
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>找回密码</title>
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
            <span class="title">找回密码</span>
        </div>

        <form id="form" action="" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">选择找回方式</label>
                <div class="controls">
                    <select name="type" id="type">
                        <option value="email">根据电子邮件找回</option>
                        <option value="phone">根据手机号码找回</option>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" id="typename">请输入电子邮件地址</label>
                <div class="controls">
                    <input type="text" name="value">
                </div>
            </div>

            <div class="form-actions">
                <button id="btn" type="button" class="btn btn-primary">提交</button>

                <a class="pull-right" href="/login">取消</a>
            </div>
        </form>
    </div>
</div>
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/dist/sweetalert-dev.js"></script>
<script src="/static/js/user/foundpassword.js"></script>
<script src="/static/js/user/notify.js"></script>
</body>
</html>

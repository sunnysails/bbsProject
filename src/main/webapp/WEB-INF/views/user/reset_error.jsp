<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2016/12/18
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>错误页面</title>
    <link href="/static/css/font-awesome/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/css/bootstrap2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@ include file="../include/navbar.jsp" %>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i>系统提示</span>
        </div>
        <div class="box-padding">
            <h4 style="font-size: 20px">${requestScope.message}</h4>
        </div>
    </div>
    <!--box end-->
</div>
<!--container end-->
</body>
</html>

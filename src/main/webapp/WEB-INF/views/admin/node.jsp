<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2016/12/29
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/js/dist/sweetalert.css" rel="stylesheet">
    <style>
        .mt20 {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<%@include file="../include/adminnavbar.jsp"%>
<!--header-bar end-->
<div class="container-fluid mt20">
    <a href="javascript:;" id="addNode" class="btn btn-success">添加新节点</a>
    <table class="table">
        <thead>
        <tr>
            <th>节点名称</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${nodeList}" var="node">
            <tr>
                <td>${node.nodeName}</td>
                <td>
                    <a href="/admin/nodeUpdate?nodeId=${node.id}">修改</a>
                    <a href="javascript:;" rel="${node.id}" class="delNode">删除</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script src="/static/js/dist/sweetalert.min.js"></script></body>
<script src="/static/js/admin/node.js"></script>
</body>
</html>

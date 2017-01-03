<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2016/12/29
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>主题管理</title>
    <link href="/static/css/font-awesome/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/css/bootstrap2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/js/dist/sweetalert.css">
</head>
<body>
<%@include file="../include/adminnavbar.jsp" %>
<!--header-bar end-->
<div class="container-fluid" style="margin-top:20px">
    <table class="table">
        <thead>
        <tr>
            <th>名称</th>
            <th>发布人</th>
            <th>发布时间</th>
            <th>回复数量</th>
            <th>最后回复时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${page.items}" var="topic">
            <tr>
                <td><a href="/topicdetail?topicId=${topic.id}" target="_blank">${topic.title}</a></td>
                <td>${topic.user.userName}</td>
                <td>${topic.createTime}</td>
                <td>${topic.replyNum}</td>
                <td>${topic.lastReplyTime}</td>
                <td><a href="javascript:;" rel="${topic.id}" class="del">删除</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="pagination pagination-mini pagination-centered">
        <ul id="pagination" style="margin-bottom:20px;"></ul>
    </div>
</div>
<!--container end-->

<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script src="/static/js/dist/sweetalert-dev.js"></script>
<script src="/static/js/admin/topic.js"></script>
<script>
    $(function () {
        $("#pagination").twbsPagination({
            totalPages:${page.totalPage},
            visiblePages: 5,
            first: '首页',
            last: '末页',
            prev: '上一页',
            next: '下一页',
            href: '?p={{number}}&_=${param._}'
        });
    });
</script>
</body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2016/12/15
  Time: 13:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <link href="/static/css/font-awesome/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/css/bootstrap2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="./include/navbar.jsp" %>

<div class="container">
    <div class="box">
        <div class="talk-item">
            <ul class="topic-type unstyled inline" style="margin-bottom:0px;">
                <li class="${empty param.nodeId?'active':''}"><a href="/home">全部</a></li>
                <c:forEach items="${nodeList}" var="node">
                    <li class="${node.id == param.nodeId?'active':''}"><a
                            href="/home?nodeId=${node.id}">${node.nodeName}</a></li>
                </c:forEach>
            </ul>
        </div>
        <c:forEach items="${page.items}" var="topic">
            <div class="talk-item">
                <table class="talk-table">
                    <tr>
                        <td width="50">
                            <img class="avatar"
                                 src="${topic.user.avatar}?imageView2/1/w/40/h/40"
                                 alt="">
                        </td>
                        <td width="80">
                            <a href="">${topic.user.userName}</a>
                        </td>
                        <td width="auto">
                            <a href="/topicdetail?topicId=${topic.id}">${topic.title}</a>
                        </td>
                        <td width="50" align="center">
                            <span class="badge">${topic.replyNum}</span>
                        </td>
                    </tr>
                </table>
            </div>
        </c:forEach>
        <div class="pagination pagination-mini pagination-centered">
            <ul id="pagination" style="margin-bottom:20px;"></ul>
        </div>
    </div>
    <!--box end-->
</div>
<!--container end-->
<div class="footer">
    <div class="container">
        Copyright © 2016 sun
    </div>
</div>
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script src="/static/js/user/notify.js"></script>
<script>
    $(function () {
        $("#pagination").twbsPagination({
            totalPages:${page.totalPage},
            visiblePages: 5,
            first: '首页',
            last: '末页',
            prev: '上一页',
            next: '下一页',
            href: '?p={{number}}&nodeId=${param.nodeId}'
        });
    });
</script>
</body>
</html>

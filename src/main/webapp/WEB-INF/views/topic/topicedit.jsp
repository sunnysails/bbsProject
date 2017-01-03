<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2016/12/26
  Time: 9:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑帖子</title>
    <link href="/static/css/font-awesome/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/css/bootstrap2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/dist/sweetalert.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
</head>
<body>
<%@include file="../include/navbar.jsp" %>

<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-plus"></i> 发布新主题</span>
        </div>

        <form id="topicForm" action="" style="padding: 20px">
            <label class="control-label">主题标题</label>
            <input name="topicId" id="topicId" type="hidden" value="${topic.id}">
            <input name="title" id="title" type="text" style="width: 100%;box-sizing: border-box;height: 30px"
                   value="${topic.title}">
            <label class="control-label">正文</label>
            <textarea name="content" id="editor">${topic.content}</textarea>

            <select name="nodeId" id="nodeId" style="margin-top:15px;">
                <option value="">请选择节点</option>
                <c:forEach items="${nodeList}" var="node">
                    <option value="${node.id}">${node.nodeName}</option>
                </c:forEach>
            </select>
        </form>
        <div class="form-actions" style="text-align: right">
            <button id="sendBtn" class="btn btn-primary">确认修改</button>
        </div>
    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/dist/sweetalert-dev.js"></script>
<script>
    $(function () {
        var editor = new Simditor({
            textarea: $('#editor')
            //optional options
        });
    });
</script>
<script src="/static/js/topic/topicedit.js"></script>
<script src="/static/js/user/notify.js"></script>
</body>
</html>
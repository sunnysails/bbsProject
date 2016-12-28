<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2016/12/21
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>发布新主题</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/dist/sweetalert.css">
    <link rel="stylesheet" type="text/css" href="/static/js/editer/styles/simditor.css">
    <link rel="stylesheet" type="text/css" href="/static/css/simditor-emoji.css">
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
            <input name="title" id="title" type="text" style="width: 100%;box-sizing: border-box;height: 30px"
                   placeholder="请输入主题标题，如果标题能够表达完整内容，则正文可以为空">
            <label class="control-label">正文</label>
            <textarea name="content" id="editor"></textarea>

            <select name="nodeId" id="nodeId" style="margin-top:15px;">
                <option value="">请选择节点</option>
                <c:forEach items="${nodeList}" var="node">
                    <option value="${node.id}">${node.nodeName}</option>
                </c:forEach>
            </select>
        </form>
        <div class="form-actions" style="text-align: right">
            <button id="sendBtn" class="btn btn-primary">发布主题</button>
        </div>
    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="/static/js/editer/scripts/uploader.js"></script>

<script type="text/javascript" src="/static/js/editer/scripts/simditor.min.js"></script>
<script type="text/javascript" src="/static/js/editer/scripts/simditor-emoji.js"></script>
<script src="/static/js/dist/sweetalert-dev.js"></script>
<script>
    $(function () {
        var editor = new Simditor({
            textarea: $('#editor'),
            //optional options
            toolbar: ['title','bold','italic','underline','strikethrough',
                'fontScale','color','ol' ,'ul', 'blockquote', 'code',
                'table', 'image', 'emoji'],
            emoji: {
                imagePath:'/static/img/emoji/'
            },
            upload:{
                url: 'http://up-z1.qiniu.com/',
                params:{"token":"${token}"},
                fileKey:'file'
            },
        });
    });
</script>
<script src="/static/js/topic/newtopic.js"></script>
<script src="/static/js/user/notify.js"></script>
</body>
</html>
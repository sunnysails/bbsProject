<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2016/12/21
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>主题页</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <link rel="stylesheet" href="/static/js/dist/sweetalert.css">
    <style>
        body {
            background-image: url(/static/img/bg.jpg);
        }

        .simditor .simditor-body {
            min-height: 100px;
        }
    </style>
</head>
<body>
<%@include file="../include/navbar.jsp" %>

<div class="container">
    <div class="box">
        <ul class="breadcrumb" style="background-color: #fff;margin-bottom: 0px;">
            <li><a href="/home">首页</a> <span class="divider">/</span></li>
            <li class="active"><a href="/home?nodeId=${topic.nodeId}">${requestScope.topic.node.nodeName}</a></li>
        </ul>
        <div class="topic-head">
            <img class="img-rounded avatar" src="${requestScope.topic.user.avatar}?imageView2/1/w/60/h/60" alt="">
            <h3 class="title">${requestScope.toric.title}</h3>
            <p class="topic-msg muted"><a href="">${requestScope.topic.user.userName}</a>
                · <span id="topicTime">${requestScope.topic.createTime}</span></p>
        </div>
        <div class="topic-body">
            ${requestScope.topic.content}
        </div>
        <div class="topic-toolbar">
            <c:if test="${not empty sessionScope.curr_user}">
                <ul class="unstyled inline pull-left">
                    <c:choose>
                        <c:when test="${not empty fav}">
                            <li><a href="javascript:;" id="favTopic">取消收藏</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="javascript:;" id="favTopic">加入收藏</a></li>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${sessionScope.curr_user.id==topic.userId and topic.edit}">
                        <li><a href="/topicedit?topicId=${param.topicId}">编辑</a></li>
                    </c:if>
                    <li><a href="">感谢</a></li>
                </ul>
            </c:if>
            <ul class="unstyled inline pull-right muted">
                <li>${requestScope.topic.clickNum}次点击</li>
                <li><span id="topicFav">${requestScope.topic.favNum}</span>人收藏</li>
                <li>${requestScope.topic.thankNum}人感谢</li>
            </ul>
        </div>
    </div>
    <!--box end-->

    <div class="box" style="margin-top:20px;">
        <div class="talk-item muted" style="font-size: 12px">
            ${fn:length(replyList)}个回复 | 直到 <span id="lastReplyTime">${requestScope.topic.lastReplyTime}</span>
        </div>
        <c:forEach items="${replyList}" var="reply">
            <div class="talk-item">
                <table class="talk-table">
                    <tr>
                        <td width="50">
                            <img class="avatar" src="${reply.user.avatar}?imageView2/1/w/40/h/40" alt="">
                        </td>
                        <td width="auto">
                            <a href="" style="font-size: 12px">${reply.user.userName}</a> <span style="font-size: 12px"
                                                                                                class="reply">${reply.createTime}</span>
                            <br>
                            <p style="font-size: 14px">${reply.content}</p>
                        </td>
                        <td width="70" align="right" style="font-size: 12px">
                            <a href="" title="回复"><i class="fa fa-reply"></i></a>&nbsp;
                            <span class="badge">1</span>
                        </td>
                    </tr>
                </table>
            </div>
        </c:forEach>
        <c:choose>
            <c:when test="${not empty sessionScope.curr_user}">
                <div class="box" style="margin:20px 0px;">
                    <a name="reply"></a>
                    <div class="talk-item muted" style="font-size: 12px">
                        <i class="fa fa-plus"></i> 添加一条新回复
                    </div>
                    <form id="replyForm" action="/newreply" method="post" style="padding: 15px;margin-bottom:0px;">
                        <input name="topicId" type="hidden" value="${topic.id}">
                        <textarea name="content" id="editor"></textarea>
                    </form>
                    <div class="talk-item muted" style="text-align: right;font-size: 12px">
                        <span class="pull-left">请尽量让自己的回复能够对别人有帮助回复</span>
                        <button id="replyBtn" class="btn btn-primary">发布</button>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="box" style="margin:20px 0px;">
                    <div class="talk-item">
                        请<a href="/login?redirect=topicdetail?topicId=${topic.id}#reply">登录</a>后再回复
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <!--container end-->
</div>
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/dist/sweetalert-dev.js"></script>
<script src="/static/js/highlight.pack.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.10.6/moment.min.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.10.6/locale/zh-cn.js"></script>
<script>
    $(function () {
        <c:if test="${not empty sessionScope.curr_user}">
        var editor = new Simditor({
            textarea: $('#editor'),
            toolbar: false
            //optional options
        });
        </c:if>
        $("#replyBtn").click(function () {
            $("#replyForm").submit();
        });
        $("#favTopic").click(function () {
            var $this = $(this);
            var action = "";

            if ($this.text() == "加入收藏") {
                action = "fav";
            } else {
                action = "unfav"
            }
            $.post("/topicfav", {"topicId":${topic.id}, "action": action}).done(function (json) {
                if (json.state == "success") {
                    if (action == "fav") {
                        swal({
                            title: "取消收藏!",
                            imageUrl: "/static/img/heart-down.png"
                        });
                        $this.text("取消收藏");
                    } else {
                        swal({
                            title: "收藏成功!",
                            imageUrl: "/static/img/heart-up.png"
                        });
                        $this.text("加入收藏");
                    }
                    $("#topicFav").text(json.data);
                } else {
                    swal("操作失败,请稍候再试", "", "error");
                }
            }).error(function () {
                swal("操作失败,请稍候再试", "", "error");
            });
        });

        $("#topicTime").text(moment($("#topicTime").text()).fromNow());
        $("#lastReplyTime").text(moment($("#lastReplyTime").text()).format("YYYY年MM月DD日 HH:mm:ss"));
        $(".reply").text(function () {
            var time = $(this).text();
            return moment(time).fromNow();
        });
    });
</script>
<script src="/static/js/user/notify.js"></script>
</body>
</html>
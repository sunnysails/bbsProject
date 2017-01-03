<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2016/12/31
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="/static/css/font-awesome/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/css/bootstrap2/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/js/dist/sweetalert.css" rel="stylesheet">
</head>
<script>
</script>
<body>
<%@include file="../include/adminnavbar.jsp" %>
<!--header-bar end-->
<div class="container-fluid" style="margin-top:20px">
    <table class="table">
        <thead>
        <tr>
            <th>账号</th>
            <th>注册时间</th>
            <th>最后登录时间</th>
            <th>最后登录IP</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.items}" var="userVo">
            <tr>
                <td>${userVo.userName}</td>
                <td>${userVo.createtime}</td>
                <td>${userVo.logintime}</td>
                <td>${userVo.ip}</td>
                <td>
                    <a href="javascript:;" class="update"
                       rel="${userVo.id},${userVo.state}">${userVo.state == '1'?'禁用':'恢复'}</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagination pagination-mini pagination-centered">
        <ul id="pagination" style="margin-bottom:20px;"></ul>
    </div>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script src="/static/js/dist/sweetalert.min.js"></script>
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

        $(".update").click(function () {
            var state = $(this).attr("rel");
            console.log(state);
            $.ajax({
                url: "/admin/user",
                type: "post",
                data: {"stateId": state},
                success: function (data) {
                    if (data.state == 'success') {
                        swal({
                            title: "修改成功!",
                            type: "success",
                            timer: 2000,
                            showConfirmButton: false,
                            showCancelButton: false,
                            closeOnConfirm: false
                        }, function () {
                            window.history.go(0);
                        });
                    } else {
                        swal("修改失败", data.message, "error");
                    }
                },
                error: function () {
                    swal("服务器异常,删除失败!", "", "error");
                }
            });
        });
    });
</script>
</body>
</html>
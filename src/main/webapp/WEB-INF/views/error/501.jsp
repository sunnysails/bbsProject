<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2017/1/2
  Time: 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>
<h1>回复不能为空<a href="javascript:;" onclick='back()'>点击返回主题</a></h1>
</body>
<script>
    function back () {
        window.history.go(-1);
    }
</script>
</html>
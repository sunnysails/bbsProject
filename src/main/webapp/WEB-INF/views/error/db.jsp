<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Document</title>
</head>
<body>
	<h1>数据库好像不在了...</h1>
	<%
		// String message = exception.getMessage();
		// sendEmail();
		Class exceptionClass = (Class)request.getAttribute("javax.servlet.error.exception_type");
		//out.print("exception_type:" + exceptionClass.getName());
		
		String errorUrl = (String)request.getAttribute("javax.servlet.error.request_uri");
		//out.print("ERROR URL:" + errorUrl);
		
		
	%>
</body>
</html>
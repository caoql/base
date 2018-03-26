<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<b>这个是JSP页面出错后的跳转页面</b>
	<%= exception == null ? "" : "<b>,错误信息如下:</b>" + exception.getMessage()%>
</body>
</html>
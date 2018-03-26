<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true" errorPage="/error.jsp"%>
<%--
	1.errorPage属性的设置值必须使用相对路径，如果以“/”开头，表示相对于当前Web应用程序的根目录(注意不是站点根目录)，否则，表示相对于当前页面。
	2.可以在web.xml文件中使用<error-page>元素为整个Web应用程序设置错误处理页面。
	3.如果设置了某个JSP页面的errorPage属性，那么在web.xml文件中设置的错误处理将不对该页面起作用。
 --%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 3秒钟后自动跳转回首页 -->
<meta http-equiv="refresh"
	content="3;url=<%=path %>/login.jsp">
<title>Insert title here</title>
</head>
<body>
	<div><img src="<%=path %>/img/404.png"/></div>
	<b>您访问的地址不存在！！！</b>
	<br /> 3秒钟后自动跳转回首页，如果没有跳转，请点击
	<a href="${pageContext.request.contextPath}/login.jsp">这里</a>
	<%-- <%
	int i = 1/0;
	%> --%>
</body>
</html>
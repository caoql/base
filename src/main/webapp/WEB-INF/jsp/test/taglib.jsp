<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ taglib uri="/taglib" prefix="c"%>
<%--在Jsp页面中使用防盗链标签 
当用户尝试直接通过URL地址(http://localhost:8080/base/taglib.jsp)访问这个页面时，
防盗链标签的标签处理器内部就会进行处理，将请求重定向到/login.jsp
--%>
<c:referer site="http://localhost:8080/base" page="/login.jsp" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>taglib标签库的测试页面</title>
</head>
<body>
	<p>hello jsp</p>

	模拟if标签：
	<%--if标签的test属性值为true ,标签体的内容会输出--%>
	<c:if test="true">
		<h3>网站内部资料</h3>
	</c:if>
	<%--if标签的test属性值为false ,标签体的内容不会输出--%>
	<c:if test="false">
 		这里的内部不输出
    </c:if>
	<hr />

	模拟choose,when,otherwise标签：
	<br />
	<c:choose>
		<c:when test="${user == null}">
			when标签标签体输出的内容：
            <h3>用户为空</h3>
		</c:when>
		<c:otherwise>
 			用户不为空
        </c:otherwise>
	</c:choose>
	<hr />
	<c:choose>
		<c:when test="${user != null}">
			用户不为空
        </c:when>
		<c:otherwise>
			otherwise标签标签体输出的内容：
            <h3>用户为空</h3>
		</c:otherwise>
	</c:choose>
	<hr />

	模拟foreach标签：
	<br />
	<%
		// 这段数据只是模拟用的，实际上是后台返回的
		// list集合
		List<String> stars = new ArrayList<String>();
		stars.add("乔丹");
		stars.add("科比");
		stars.add("詹姆斯");
		stars.add("库里");

		//对象数组
		Integer intObjArr[] = new Integer[] { 1, 2, 3 };

		//基本数据类型数组
		int intArr[] = new int[] { 4, 5, 6 };

		//map集合
		Map<String, String> mapData = new HashMap<String, String>();
		mapData.put("a", "aaaaaa");
		mapData.put("b", "bbbbbb");

		//将集合存储到pageContext对象中
		pageContext.setAttribute("stars", stars);
		pageContext.setAttribute("intObjArr", intObjArr);
		pageContext.setAttribute("intArr", intArr);
		pageContext.setAttribute("mapData", mapData);
	%>
	<%--迭代存储在pageContext对象中的stars集合 --%>
	<c:foreach items="${stars}" var="star">
		${star}<br />
	</c:foreach>
	<hr />
	<%--迭代存储在pageContext对象中的数组 --%>
	<c:foreach items="${intObjArr}" var="num">
             ${num}<br />
	</c:foreach>
	<hr />
	<%--迭代存储在pageContext对象中的数组 --%>
	<c:foreach items="${intArr}" var="num">
             ${num}<br />
	</c:foreach>
	<hr />
	<%--迭代存储在pageContext对象中的map集合 --%>
	<c:foreach items="${mapData}" var="me">
             ${me}<br />
	</c:foreach>
	<hr />

	转义标签：
	<c:htmlEscape>
		<a href="http://www.cnblogs.com">访问博客园</a>
	</c:htmlEscape>
	<hr>

	模拟out标签：
	<br />
	<%--使用out标签输出content属性的内容 --%>
	<c:out content="<a href='http://www.cnblogs.com'>访问博客园</a>" /><br />
	<%--使用out标签输出 content属性的内容，内容中的html代码会进行转义处理--%>
	<c:out content="<a href='http://www.cnblogs.com'>访问博客园</a>"
		escapeHtml="true" />
	<hr />
</body>
</html>
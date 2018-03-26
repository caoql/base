<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%-- 使用taglib指令引用simple标签库，标签库的前缀(prefix)可以随便设置，如这里设置成 prefix="simple" --%>
<%@taglib uri="/WEB-INF/simple.tld" prefix="simple"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>自定义标签开发的测试页面</title>
</head>
<body>
	<%--在jsp页面中使用自定义标签 demo1标签是带有标签体的，标签体的内容是"小C"这几个字符串--%>
	<simple:demo1>
		小C
    </simple:demo1>
	<hr />

	<%--在jsp页面中使用自定义标签 --%>
	<simple:demo2>
		<h1>小C</h1>
	</simple:demo2>
	<hr />

	<%--在jsp页面中使用自定义标签，标签有一个count属性,如果标签的属性值是8种基本数据类型，那么在JSP页面在传递字符串时，JSP引擎会自动转换成相应的类型，但如果标签的属性值是复合数据类型，那么JSP引擎是无法自动转换的--%>
	<simple:demo5 count="${4}">
		<h1>科比</h1>
	</simple:demo5>
	<hr />

	<%--如果一定要给标签的复合属性赋值，那么可以采用表达式的方式给复合属性赋值，如下所示： --%>
	<simple:demo6 date="<%=new Date()%>" />
	<hr>

	<%--在jsp页面中使用自定义标签,修改内容 --%>
	<simple:demo3>
        andy_xiaoC
    </simple:demo3>
	<hr />

	<%--在jsp页面中使用自定义标签,控制jsp标签后面的内容是否显示 --%>
	<simple:demo4 />
	<!-- 这里的内容位于 <simple:demo4 />标签后面，因此不会输出到页面上 -->
	<h1>天下霸主</h1>
</body>


</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- 使用taglib指令引用cal标签库，标签库的前缀(prefix)可以随便设置，如这里设置成 prefix="cal" --%>
<%@taglib uri="/cal" prefix="cal"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>自定义标签开发的测试页面</title>
</head>
<body>
	你的IP地址是(使用自定义标签获取输出)：
	<%--使用自定义标签viewIP --%>
	<cal:viewIP />
	<hr />

	<%--在jsp页面中使用自定义标签 demo1标签是带有标签体的，标签体的内容是"小C"这几个字符串--%>
	是否显示标签体里面的内容：
	<cal:demo1>
		小C
    </cal:demo1>
	<hr />

	标签体的内容重复执行：
	<%--在jsp页面中使用自定义标签 demo3标签--%>
	<cal:demo3>
		<h3>jsp页面的内容</h3>
	</cal:demo3>
	<hr />

	标签体的内容修改
	<%--在jsp页面中使用自定义标签 demo4标签--%>
	<cal:demo4>
		<h3>cal_jsp</h3>
	</cal:demo4>
	<hr />

	<%--在jsp页面中使用自定义标签 demo2标签是不带标签体的--%>
	标签后的jsp页面是否执行：
	<cal:demo2 />
	<h1>jsp页面的内容2</h1>
	<hr />
</body>
</html>
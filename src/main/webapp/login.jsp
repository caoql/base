<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
	<meta charset="utf-8" />
	<title>后台模板系统</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/login.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/jquery-easyui/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/jquery-easyui/themes/icon.css" />
	<link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>
</head>
<body>
	<jsp:directive.include file="WEB-INF/jsp/common/top.jsp" />
	<div class="main">
		<div class="login">
			<div class="title">系统登陆</div>
			<form>
				<input class="text" type="text" id="username" name="username" placeholder="请输入用户名"/>
				<input class="text" type="password" id="password" name="password" placeholder="请输入密码" />
				<div id="btn_login" class="sure">登录</div>
			</form>
		</div>
	</div>
	<%-- <jsp:directive.include file="WEB-INF/jsp/common/bottom.jsp" /> --%>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
		var _contextPath;
		$(function() {
			// 把java的值和js的值关联起来了
			_contextPath = '<%=path %>';
			$('#btn_login').bind('click',login);
			//为回车键绑定登录事件
			$(window).keydown(function(event) {
				if (event.keyCode == 13) {
					login();
				}
			});
		});
		
		
		//登录
		function login() {
			var username = $('#username').val();
			var password = $('#password').val();
			//校验用户名和密码不能为空
			if(username == null || username == ""){
				$.messager.alert('提示消息', '用户名不能为空', 'info');
				return false;
			}
			if(password == null || password == ""){
				$.messager.alert('提示消息', '密码不能为空', 'info');
				return false;
			}
			//给密码加密
			/* var password = b64_md5($('#password').val()); */
			var password = $('#password').val(); 
			//发送请求
			$.ajax({
				type: "POST",
				url: _contextPath + "/login",
				data: {"username":username,"password":password},
				success: function(data) {
					if ( data != null && data.code == 0){
						 window.location.href = _contextPath + '/index';
					}  else {
						$.messager.alert('提示消息', data.msg, 'info');
					}
				},
				error: function(xhr) {
					$.messager.alert('提示消息', '登录失败', 'info');
				}
			});
		}
	</script>
</body>
</html>

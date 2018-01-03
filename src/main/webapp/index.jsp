<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>登录页</title>
	<link rel="stylesheet" type="text/css" href="ui/js/jquery-easyui/themes/bootstrap/easyui.css" />
	<link rel="stylesheet" type="text/css" href="ui/js/jquery-easyui/themes/icon.css" />
</head>
<body class="easyui-layout">
	<a href="/base/admin/home">首页</a>
    
	<script src="ui/js/jquery-easyui/jquery.min.js"></script>
	<script src="ui/js/jquery-easyui/jquery.easyui.min.js"></script>
	<script src="ui/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
	<script>
		$(function() {
			$('#click').click(function() {
				$.ajax({
					type: 'POST',
					url: '/base/admin/test',
					data: {name: 'caoql', age: 25},
					success: function(data) {
						console.log(data);
						$.messager.alert('提示消息', '操作成功', 'info');
					},
					error: function(xhr) {
						console.log(xhr);
						$.messager.alert('提示消息', '操作失败', 'error');
					}
				});
			});
		});
	</script>
</body>
</html>

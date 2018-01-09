<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../../ui/js/jquery-easyui/themes/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../ui/js/jquery-easyui/themes/icon.css" />
</head>
<body>
<form id="">
	账号：<input type="text" id="account" name="account" class="easyui-validatebox">
	<input id="btn-save" type=submit value="提交">
	<input type="reset" value="重置">
</form>
<script src="../../ui/js/jquery-easyui/jquery.min.js"></script>
<script src="../../ui/js/jquery-easyui/jquery.easyui.min.js"></script>
<script src="../../ui/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script>
	$(function() {
		// 绑定提交
		$('#btn-save').on('click', function() {
			$.ajax({
				type: 'POST',
				url: '/base/admin/user/adduser',
				data: {account: $('#account').val()},
				success: function(data) {
					if (data != null && data.code == 0) {}
					
				}
			});
		});
	});
</script>
</body>
</html>
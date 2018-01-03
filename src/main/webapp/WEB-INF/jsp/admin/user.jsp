<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户界面列表</title>
<link rel="stylesheet" type="text/css" href="../../ui/js/jquery-easyui/themes/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../ui/js/jquery-easyui/themes/icon.css" />
</head>
<body>
	<table id="datagrid" class="easyui-datagrid" title="用户界面" style="width:700px;height:250px"
			data-options="rownumbers:true,url:'/base/admin/user/list',fit:true,singleSelect:true,toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'userId',width:80,align:'center'">用户ID</th>
				<th data-options="field:'account',width:100,align:'center'">账号</th>
				<th data-options="field:'name',width:80,align:'center'">姓名</th>
				<th data-options="field:'phone',width:80,align:'center'">手机号码</th>
				<th data-options="field:'sex',width:80,align:'center'">性别</th>
				<th data-options="field:'isEnabled',width:80,align:'center'">是否启用</th>
				<th data-options="field:'createTime',width:80,align:'center'">创建时间</th>
				<th data-options="field:'creator',width:80,align:'center'">创建人</th>
				<th data-options="field:'updateTime',width:80,align:'center'">修改时间</th>
				<th data-options="field:'updator',width:80,align:'center'">修改人</th>
				<th data-options="field:'remark',width:80,align:'center'">备注</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"></a>
		</div>
		<div>
			Date From: <input class="easyui-datebox" style="width:80px">
			To: <input class="easyui-datebox" style="width:80px">
			Language: 
			<select class="easyui-combobox" panelHeight="auto" style="width:100px">
				<option value="java">Java</option>
				<option value="c">C</option>
				<option value="basic">Basic</option>
				<option value="perl">Perl</option>
				<option value="python">Python</option>
			</select>
			账户: <input class="easyui-validatebox"  id="account" name="account" style="width:80px">
			<a href="#" id="btn-search" class="easyui-linkbutton" iconCls="icon-search">Search</a>
		</div>
	</div>
<script src="../../ui/js/jquery-easyui/jquery.min.js"></script>
<script src="../../ui/js/jquery-easyui/jquery.easyui.min.js"></script>
<script src="../../ui/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script>
	$(function() {
		// 绑定查询
		$('#btn-search').on('click', function() {
			$('#datagrid').datagrid({
				queryParams: {
					account: $('#account').val()
				},
				url: '/base/admin/user/list'
			});
		});
	});
</script>
</body>
</html>
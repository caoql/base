<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/global.jsp"%>
<div data-options="fit:true,border:false">
	<form id="userEditForm" method="post">
		<div>
			<table>
				<tbody>
					<tr>
						<input id="userId" name="userId" type="hidden"
							value="${user.userId}">
						<td align="right">登录账号：</td>
						<td><input name="account" type="text" placeholder="请输入登录账号"
							class="easyui-validatebox" data-options="required:true"
							value="${user.account}"></td>
						<td align="right">姓名：</td>
						<td><input name="name" type="text" placeholder="请输入姓名"
							class="easyui-validatebox" data-options="required:true"
							value="${user.name}"></td>
					</tr>
					<tr>
						<td align="right">性别：</td>
						<td><input type="text" name="sex" class="easyui-combobox"
							data-options="editable:false,panelHeight:'auto',
							valueField:'value', textField:'text',
							data: [{
								value: 'm',
								text: '男'
							},{
								value: 'w',
								text: '女'
							}]"
							value="${user.sex}"></td>
						<td align="right">电话：</td>
						<td><input type="text" name="phone" class="easyui-numberbox"
							value="${user.phone}" /></td>
					</tr>
					<tr>
						<td align="right">是否启用：</td>
						<td><input type="text" name="isEnabled"
							class="easyui-combobox"
							data-options="editable:false,panelHeight:'auto',
							valueField:'value', textField:'text',
							data: [{
								value: '1',
								text: '启用'
							},{
								value: '0',
								text: '停用'
							}]"
							value="${user.isEnabled}"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</div>
<script type="text/javascript">
	$(function() {
		// 绑定提交
		$('#btn-save').on('click', function() {
			// 校验表单默认规则
			if (!$('#userEditForm').form('validate')) {
				return false;
			}
			// 获取数据
			var formdata = $$.serializeToJson('#userEditForm');
			if (!formdata) {
				return false;
			}
			// 发送请求处理
			$.ajax({
				type : 'POST',
				url : '${path}/admin/user/edit',
				data : formdata,
				success : function(data) {
					if (data != null) {
						if (data.code == 0) {
							$('#editForm').dialog('close');
							$$.refreshDatagrid('datagrid');
						} else {
							$.messager.alert('提示信息', data.msg, 'error');
						}
					} else {
						$.messager.alert('提示信息','修改失败','error');
					}
				}
			});
		});
	});
</script>
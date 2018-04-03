<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/global.jsp"%>
<div data-options="fit:true,border:false">
	<form id="roleAddForm">
		<div>
			<table>
				<tbody>
					<tr>
						<td align="right">角色名称：</td>
						<td><input name="name" type="text" placeholder="请输入角色名称" style="200px;"
							class="easyui-validatebox" data-options="required:true">
						</td>
					</tr>
					<tr>
						<td align="right">描述：</td>
						<td><input name="description" type="text" style="200px;"
							placeholder="请输入描述" class="easyui-validatebox"
							data-options="required:false"></td>
					</tr>
					<tr>
						<td align="right">是否启用：</td>
						<td><input type="text" name="isEnabled" class="easyui-combobox" style="200px;"
							data-options="editable:false,panelHeight:'auto',
							valueField:'value', textField:'text',
							data: [{
								value: '1',
								text: '启用'
							},{
								value: '0',
								text: '停用'
							}]" value="1">
						</td>
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
			if (!$('#roleAddForm').form('validate')) {
				return false;
			}
			// 获取数据
			var formdata = $$.serializeToJson('#roleAddForm', true);
			if (!formdata) {
				return false;
			}
			// 发送请求处理
			$.ajax({
				type: 'POST',
				url: '${path }/admin/role/add',
				data: formdata,
				success: function(data) {
					if (data != null) {
						if (data.code == 0) {
							$('#addForm').dialog('close');
							$$.refreshDatagrid('datagrid');
						} else {
							$.messager.alert('提示信息', data.msg, 'error');
						}
					} else {
						$.messager.alert('提示信息','新增失败','error');
					}
				}
			});
		});
	});
</script>
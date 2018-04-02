<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/global.jsp"%>
<script type="text/javascript">
	$(function() {
		// 绑定提交
		$('#btn-save').on('click', function() {
			// 校验表单默认规则
			if (!$('#resourceAddForm').form('validate')) {
				return false;
			}
			// 获取数据
			var formdata = $$.serializeToJson('#resourceAddForm', true);
			if (!formdata) {
				return false;
			}
			// 发送请求处理
			$.ajax({
				type: 'POST',
				url: '${path }/admin/resource/add',
				data: formdata,
				success: function(data) {
					if (data != null) {
						if (data.code == 0) {
							$('#addForm').dialog('close');
							$$.refreshTreegrid('resourceTreeGrid');
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
<div data-options="fit:true,border:false">
	<form id="resourceAddForm" method="post">
		<div>
			<table>
				<tbody>
					<tr>
						<td align="right">资源名称：</td>
						<td><input name="name" type="text" placeholder="请输入角色名称"
							class="easyui-validatebox" data-options="required:true">
						</td>
					</tr>
					<tr>
						<td align="right">资源类型：</td>
						<td><input type="text" name="type" class="easyui-combobox"
							data-options="required:true,editable:false,panelHeight:'auto',
							valueField:'value', textField:'text',
							data: [{
								value: 'F',
								text: '菜单组'
							},{
								value: 'A',
								text: '菜单'
							},{
								value: 'D',
								text: '动作'
							}]">
						</td>
					</tr>
					<tr>
						<td align="right">URL：</td>
						<td><input name="url" type="text" placeholder="请输入URL"
							class="easyui-validatebox" data-options="required:true">
						</td>
					</tr>
					<tr>
						<td align="right">顺序：</td>
						<td><input name="nodeOrder" type="text" placeholder="请输入URL"
							class="easyui-numberbox" data-options="required:false">
						</td>
					</tr>
					<tr>
						<td align="right">父级资源ID：</td>
						<td><input name="pid" type="text" placeholder="请输入URL"
							class="easyui-validatebox" data-options="required:false">
						</td>
					</tr>
					<tr>
						<td align="right">描述：</td>
						<td><input name="description" type="text"
							placeholder="请输入描述" class="easyui-validatebox"
							data-options="required:false"></td>
					</tr>
					<tr>
						<td align="right">是否启用：</td>
						<td><input type="text" name="isEnabled" class="easyui-combobox"
							data-options="editable:false,panelHeight:'auto',
							valueField:'value', textField:'text',
							data: [{
								value: '1',
								text: '启用'
							},{
								value: '0',
								text: '停用'
							}]">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</div>
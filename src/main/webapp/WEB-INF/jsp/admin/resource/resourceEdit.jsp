<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/global.jsp"%>
<script type="text/javascript">
	$(function() {
		pidInit();
		// 绑定提交
		$('#btn-save').on('click', function() {
			// 校验表单默认规则
			if (!$('#resourceEditForm').form('validate')) {
				return false;
			}
			// 获取数据
			var formdata = $$.serializeToJson('#resourceEditForm');
			if (!formdata) {
				return false;
			}
			// 发送请求处理
			$.ajax({
				type: 'POST',
				url: '${path }/admin/resource/edit',
				data: formdata,
				success: function(data) {
					if (data != null) {
						if (data.code == 0) {
							$('#editForm').dialog('close');
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
	
	/**
	 * 初始化上级资源下拉框
	 */
	function pidInit() {
		//上级资源-资源树
		$('#pid').combotree({
			method: 'GET',
	        url: '${path }/admin/resource/pidtree',
	        parentField: 'pid',
	        lines: true,
	        panelHeight: 'auto'
	    });
	}
</script>
<div data-options="fit:true,border:false">
	<form id="resourceEditForm">
		<div>
			<table>
				<tbody>
					<input name="resourceId" type="hidden"  value="${resource.resourceId}">
					<tr>
						<td align="right">资源名称：</td>
						<td><input name="name" type="text" placeholder="请输入资源名称" style="width:200px;"
							class="easyui-validatebox" data-options="required:true" value="${resource.name}">
						</td>
					</tr>
					<tr>
						<td align="right">资源类型：</td>
						<td><input type="text" name="type" class="easyui-combobox" style="width:200px;"
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
							}]" value="${resource.type}">
						</td>
					</tr>
					<tr>
						<td align="right">URL：</td>
						<td><input name="url" type="text" placeholder="请输入URL" style="width:200px;"
							class="easyui-validatebox" data-options="required:false" value="${resource.url}">
						</td>
					</tr>
					<tr>
						<td align="right">顺序：</td>
						<td><input name="nodeOrder" type="text" style="width:200px;"
							class="easyui-numberbox" data-options="required:false" value="${resource.nodeOrder}">
						</td>
					</tr>
					<tr>
						<td align="right">描述：</td>
						<td><input name="description" type="text" style="width:200px;"
							placeholder="请输入描述" class="easyui-validatebox"
							data-options="required:false" value="${resource.description}"></td>
					</tr>
					<tr>
						<td align="right">是否启用：</td>
						<td><input type="text" name="isEnabled" class="easyui-combobox" style="width:200px;"
							data-options="editable:false,panelHeight:'auto',
							valueField:'value', textField:'text',
							data: [{
								value: '1',
								text: '启用'
							},{
								value: '0',
								text: '停用'
							}]" value="${resource.isEnabled}">
						</td>
					</tr>
					<tr>
						<td align="right">父级资源：</td>
						<td><input id="pid" name="pid" type="text" placeholder="请输入父级资源ID" style="width:200px;"
							 data-options="required:false" value="${resource.pid}">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/global.jsp"%>
<script type="text/javascript">
	//回显角色值
	var roleIds = [];
	<c:forEach items='${rolelist}' var='item'>
		roleIds.push('${item.id}');
	</c:forEach>
	
	$(function() {
		$('#roleIds').combobox({
			multiple:true,
			editable:false,
			valueField:'id',
			textField:'text',
			url:'${path}/admin/user/searchrole',
			onLoadSuccess: function(data) {
				$('#roleIds').combobox('setValues', roleIds);
			}
		});
		
		
		// 绑定提交
		$('#btn-save').on('click', function() {
			// 校验表单默认规则
			if (!$('#userBindForm').form('validate')) {
				return false;
			}
			// 获取数据
			var formdata = $$.serializeToJson('#userBindForm', true);
			if (!formdata) {
				return false;
			}
			var roleIds = formdata.roleIds;
			var ids = "";
			if (roleIds) {
				ids = roleIds.toString();
			}
			formdata.roleIds = ids;
			// 发送请求处理
			$.ajax({
				type : 'POST',
				url : '${path}/admin/user/bindrole',
				data : formdata,
				success : function(data) {
					if (data != null) {
						if (data.code == 0) {
							$('#bindForm').dialog('close');
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
<div data-options="fit:true,border:false">
	<form id="userBindForm" method="post">
		<div>
			<table>
				<tbody>
					<tr>
						<input id="userId" name="userId" type="hidden"
							value="${id}">
						<td align="right">请选择角色：</td>
						<td><input id="roleIds" name="roleIds" type="text" data-options="required:true"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#roleEditForm').form({
            url : '${path }/role/edit',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#roleEditForm');
                    parent.$.messager.alert('错误', eval(result.msg), 'error');
                }
            }
        });
        
        $("#roleEditStatus").val('${role.status}');
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <form id="roleEditForm" method="post" class="edit-user-pwd resource-form">
	    <div class="wraper">
	        <table cellpadding="0" cellspacing="0">
	            <tbody>
	                <tr>
	                    <td width="85" align="right">角色名称：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
	                            <input name="id" type="hidden"  value="${role.id}">
	                            <input name="name" type="text" placeholder="请输入角色名称" class="easyui-validatebox" data-options="required:true" value="${role.name}">
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td width="85" align="right">排序：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
	                            <input name="seq"  class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false" value="${role.seq}">
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td width="85" align="right">状态：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
	                            <select id="roleEditStatus" name="status" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
		                            <option value="0">正常</option>
		                            <option value="1">停用</option>
		                        </select>
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td width="85" align="right">备注：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
	                            <textarea name="description" style="resize: none;">${role.description}</textarea>
	                        </div>
	                    </td>
	                </tr>
	            </tbody>
	        </table>
	    </div>
    </form>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#roleAddForm').form({
            url : '${path }/role/add',
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
                    var form = $('#roleAddForm');
                    parent.$.messager.alert('错误', eval(result.msg), 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <form id="roleAddForm" method="post" class="edit-user-pwd resource-form">
	    <div class="wraper">
	        <table cellpadding="0" cellspacing="0">
	            <tbody>
	                <tr>
	                    <td width="85" align="right">角色名称：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
	                            <input name="name" type="text" placeholder="请输入角色名称" class="easyui-validatebox span2" data-options="required:true" value="">
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td width="85" align="right">排序：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
	                            <input name="seq" value="0" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false">
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td width="85" align="right">状态：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
	                            <select name="status" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
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
	                            <textarea name="description" style="resize: none;"></textarea>
	                        </div>
	                    </td>
	                </tr>
	            </tbody>
	        </table>
	    </div>
    </form>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#organizationAddPid').combotree({
            url : '${path }/organization/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto'
        });
        
        $('#organizationAddForm').form({
            url : '${path }/organization/add',
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
                    parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为organization.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#organizationAddForm');
                    parent.$.messager.alert('提示', eval(result.msg), 'warning');
                }
            }
        });
        
    });
</script>
<div data-options="fit:true,border:false">
    <form id="organizationAddForm" method="post" class="edit-user-pwd resource-form">
	    <div class="wraper">
	        <table cellpadding="0" cellspacing="0">
	            <tbody>
	                <tr>
	                    <td width="85" align="right">编号：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
	                            <input name="code" type="text" placeholder="请输入部门编号" class="easyui-validatebox" data-options="required:true" >
	                        </div>
	                    </td>
	                    <td width="85" align="right">部门名称：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
	                            <input name="name" type="text" placeholder="请输入部门名称" class="easyui-validatebox" data-options="required:true" >
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td width="85" align="right">排序：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
	                            <input name="seq" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false" value="0">
	                        </div>
	                    </td>
	                    <td width="85" align="right">菜单图标：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
			                    <input name="icon" value="fi-folder"/>
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td width="85" align="right">地址：</td>
	                    <td colspan="3">
	                        <div class="edit-user-pwd-td">
	                            <input name="address" style="width: 100%;"/>
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td width="85" align="right">上级部门：</td>
	                    <td colspan="3">
	                        <div class="edit-user-pwd-td">
	                            <select id="organizationAddPid" name="pid" style="width:200px;height: 29px;"></select>
	                            <a class="easyui-linkbutton clear-button" href="javascript:void(0)" onclick="$('#pid').combotree('clear');" >清空</a>
	                        </div>
	                    </td>
	                </tr>
	            </tbody>
	        </table>
	    </div>
    </form>
</div>
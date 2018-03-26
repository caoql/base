<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#resourceEditPid').combotree({
            url : '${path }/resource/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            value : '${resource.pid}'
        });
        
        $('#resourceEditForm').form({
            url : '${path }/resource/edit',
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
                    parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为resource.jsp页面预定义好了
//                     parent.indexMenuZTree.reAsyncChildNodes(null, "refresh");
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#resourceEditForm');
                    parent.$.messager.alert('提示', eval(result.msg), 'warning');
                }
            }
        });
        $("#resourceEditStatus").val("${resource.status}");
        $("#resourceEditType").val("${resource.resourceType}");
        $("#resourceEditOpenMode").val("${resource.openMode}");
        $("#resourceEditOpened").val("${resource.opened}");
    });
</script>
<div style="padding: 3px;">
    <form id="resourceEditForm" method="post" class="edit-user-pwd resource-form">
	    <div class="wraper">
	        <table cellpadding="0" cellspacing="0">
	            <tbody>
	                <tr>
	                    <td width="85" align="right">资源名称：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
	                            <input name="id" type="hidden"  value="${resource.id}" >
	                            <input name="name" type="text" placeholder="请输入资源名称" value="${resource.name}" class="easyui-validatebox span2" data-options="required:true" >
	                        </div>
	                    </td>
	                    <td width="85" align="right">资源类型：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
	                            <select id="resourceEditType" name="resourceType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
			                        <option value="0">菜单</option>
			                        <option value="1">按钮</option>
			                    </select>
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td width="85" align="right">资源路径：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
	                            <input name="url" type="text" value="${resource.url}" placeholder="请输入资源路径" class="easyui-validatebox span2" >
	                        </div>
	                    </td>
	                    <td width="85" align="right">打开方式：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
			                    <select id="resourceEditOpenMode" name="openMode" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
			                       <option>无(用于上层菜单)</option>
			                       <option value="ajax">ajax</option>
			                       <option value="iframe">iframe</option>
			                   </select>
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td width="85" align="right">菜单图标：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
	                            <input name="icon" value="${resource.icon}"/>
	                        </div>
	                    </td>
	                    <td width="85" align="right">排序：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
			                    <input name="seq" value="${resource.seq}" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false">
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td width="85" align="right">状态：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
	                            <select id="resourceEditStatus" name="status" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
			                        <option value="0">正常</option>
			                        <option value="1">停用</option>
			                    </select>
	                        </div>
	                    </td>
	                    <td width="85" align="right">菜单状态：</td>
	                    <td>
	                        <div class="edit-user-pwd-td">
			                    <select id="resourceEditOpened" name="opened" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
			                        <option value="0">关闭</option>
			                        <option value="1">打开</option>
			                    </select>
		                    </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td width="85" align="right">上级资源：</td>
	                    <td colspan="3">
	                        <div class="edit-user-pwd-td">
	                            <select id="resourceEditPid" name="pid" style=" height: 29px;"></select>
	                            <a class="easyui-linkbutton clear-button" href="javascript:void(0)" onclick="$('#pid').combotree('clear');">清空</a>
	                        </div>
	                    </td>
	                </tr>
	            </tbody>
	        </table>
	    </div>
    </form>
</div>

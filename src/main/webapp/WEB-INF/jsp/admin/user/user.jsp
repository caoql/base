<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 引入通用的JSP处理 --%>
<%@ include file="../../common/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/base.jsp" %>
<title>用户管理界面</title>
</head>
<body>
<!-- 查询条件展示区域  begin-->
<div>
	<form id="searchForm" onsubmit="return false">
		<table>
			<tr>
				<td><label for="account">账号:</label></td>
				<td><input type="text" id="account" name="account"></td>
			</tr>
		</table>
		 <div class="form-button">
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="searchUserFun();">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="cleanUserFun();">清空</a>
        </div>
	</form>
</div>
<!-- 查询条件展示区域  end-->

<!-- 数据展示 begin -->
<div class="easyui-layout" data-options="fit:true,border:false">
    <table id="datagrid" class="easyui-datagrid" title="用户界面">
	</table>
</div>
<!-- 数据展示 end -->

<div id="toolbar" style="display: none;">
	<shiro:hasPermission name="/admin/user/add"><a onclick="addUserFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a></shiro:hasPermission>
	<shiro:hasPermission name="/admin/user/export"><a onclick="exportUserFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">导出</a></shiro:hasPermission>
	<shiro:hasPermission name="/admin/user/download"><a onclick="downloadUserTemplet();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">导入模板下载</a></shiro:hasPermission>
	<shiro:hasPermission name="/admin/user/import"><a onclick="batchImport();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">批量导入</a></shiro:hasPermission>
</div>

<!-- 弹窗区 -->
<div id="addForm"></div>
<div id="editForm"></div>
<div id="importForm"></div>
<div id="bindForm"></div>
<script type="text/javascript">
    var userDataGrid;
    $(function() {
       userDataGrid = $('#datagrid').datagrid({
            url : '${path}/admin/user/list',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'userId',
            pageSize : 20,
            pageList : [ 20,30,50,100],
            fitColumns: true,
            // 固定的列
            frozenColumns: [[{
                field : 'action',
                title : '操作',
                width : 250,
                align : 'center',
                formatter : function(value, row, index) {
                    var str = '';
					<shiro:hasPermission name="/admin/user/bindrole">
					      str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-bind" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="bindRoleFun(\'{0}\');" >绑定角色</a>', row.userId);
					</shiro:hasPermission>
					<shiro:hasPermission name="/admin/user/edit">  	
					      str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					      str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editUserFun(\'{0}\');" >编辑</a>', row.userId);
					</shiro:hasPermission>
					<shiro:hasPermission name="/admin/user/delete">
					      str += '&nbsp;|&nbsp;';
					      str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteUserFun(\'{0}\');" >删除</a>', row.userId);
					</shiro:hasPermission>
                    return str;
                }
            },{
                width : '120',
                title : '账号',
                align: 'center',
                field : 'account'
            }, {
                width : '120',
                title : '姓名',
                field : 'name',
                align: 'center'
            }]],
            // 列对象
            columns : [ [{
                width : '150',
                title : '手机号码',
                field : 'phone',
                align: 'center'
            }, {
                width : '60',
                title : '性别',
                field : 'sex',
                align: 'center'
            },{
                width : '100',
                title : '是否启用',
                field : 'isEnabled',
                align: 'center'
            },{
                width : '180',
                title : '创建时间',
                field : 'createTime',
                align: 'center',
                sortable : true
            },{
                width : '100',
                title : '创建人',
                field : 'creator',
                align: 'center'
            }, {
                width : '180',
                title : '更新时间',
                field : 'updateTime',
                align: 'center',
                sortable : true
            }, {
                width : '180',
                title : '更新人',
                field : 'updator',
                align: 'center'
            }, {
                width : '180',
                title : '备注',
                field : 'remark',
                align: 'center'
            }] ],
            onLoadSuccess:function(data){
            	$('.user-easyui-linkbutton-bind').linkbutton({text:'绑定角色'});
                $('.user-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.user-easyui-linkbutton-del').linkbutton({text:'删除'});
            },
            toolbar : '#toolbar'
        });
    });
    
    // 查询
    function searchUserFun() {
    	console.log($.serializeObject($('#searchForm')));
        userDataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    }
    
    // 清空
    function cleanUserFun() {
        $('#searchForm input').val('');
        userDataGrid.datagrid('load', {});
    }
    
    // 导出
    function exportUserFun() {
    	// 根据查询条件导出
    	var jsonData = $.serializeObject($('#searchForm'));
    	window.location.href= '${path}/admin/user/export?'+$.param(jsonData)+ '&exportName=export.user';
    }
    
    // 导入模板下载
    function downloadUserTemplet() {
    	window.location.href= '${path}/admin/user/download';
    }
    
    // 批量导入
    function batchImport() {
    	$('#importForm').dialog({
            title : '批量导入',
            width : 600,
            height : 350,
            href : '${path}/admin/user/importpage',
            modal: true,
            buttons : [{
            	id: 'btn-save',
                text : '确认'
            }]
        });
    }
    
    // 添加用户
    function addUserFun() {
       $('#addForm').dialog({
            title : '添加',
            width : 600,
            height : 350,
            href : '${path}/admin/user/addpage',
            modal: true ,
            buttons : [{
            	id: 'btn-save',
                text : '确认'
            }]
        });
    }
    
    // 绑定角色
    function bindRoleFun(id) {
    	$('#bindForm').dialog({
            title : '绑定角色',
            width : 600,
            height : 350,
            href : '${path}/admin/user/bindpage/' + id,
            modal: true ,
            buttons : [{
            	id: 'btn-save',
                text : '确认'
            }]
        });
    }
    
    
    // 编辑用户
    function editUserFun(id) {
    	$('#editForm').dialog({
            title : '编辑',
            width : 600,
            height : 350,
            href : '${path}/admin/user/editpage/' + id,
            modal: true ,
            buttons : [{
            	id: 'btn-save',
                text : '确认'
            }]
        });
    }
 
    // 删除用户-根据用户ID
    function deleteUserFun(id) {
        // 加parent会锁屏
        parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
            if (b) {
            	$$.progressLoad();
            	$.ajax({
            		type: 'DELETE',
            		url: '${path }/admin/user/delete/' + id,
            		success: function (data) {
            			if (data && data.code == 0) {
                            parent.$.messager.alert('提示', data.msg, 'info');
                            userDataGrid.datagrid('reload');
                        } else {
                            parent.$.messager.alert('错误', data.msg, 'error');
                        }
                        $$.progressClose();
            		},
					error: function(data) {
						console.log(data);
						alert("删除失败");
					}
            	});
            }
        });
    }
</script>
</body>
</html>

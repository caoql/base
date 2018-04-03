<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 引入通用的JSP处理 --%>
<%@ include file="../../common/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/base.jsp" %>
<title>角色管理界面</title>
</head>
<body>
<!-- 查询条件展示区域  begin-->
<div>
	<form id="searchForm" onsubmit="return false">
		<table>
			<tr>
				<td><label for="name">角色名称:</label></td>
				<td><input type="text" id="name" name="name"></td>
			</tr>
		</table>
		 <div class="form-button">
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="searchFun();">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="cleanFun();">清空</a>
        </div>
	</form>
</div>
<!-- 查询条件展示区域  end-->

<!-- 数据展示 begin -->
<div class="easyui-layout" data-options="fit:true,border:false">
    <table id="datagrid" class="easyui-datagrid" title="角色界面">
	</table>
</div>
<!-- 数据展示 end -->

<div id="toolbar" style="display: none;">
    <shiro:hasPermission name="/admin/role/add">
        <a onclick="addRoleFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
    </shiro:hasPermission>
</div>

<!-- 弹窗区 -->
<div id="addForm"></div>
<div id="editForm"></div>
<div id="grantForm"></div>
<script type="text/javascript">
    var roleDataGrid;
    $(function() {
       roleDataGrid = $('#datagrid').datagrid({
            url : '${path}/admin/role/list',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'roleId',
            pageSize : 20,
            pageList : [ 20,30,50,100],
            fitColumns: true,
            // 列对象
            columns : [ [{
                field : 'action',
                title : '操作',
                width : 250,
                align : 'center',
                formatter : function(value, row, index) {
                    var str = '';
                    <shiro:hasPermission name="/admin/role/grant">
                   		str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-grant" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="grantFun(\'{0}\');" >授权</a>', row.roleId);
                   	</shiro:hasPermission>
                   	<shiro:hasPermission name="/admin/role/edit">
	               	    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
	           			str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editRoleFun(\'{0}\');" >编辑</a>', row.roleId);
	           		</shiro:hasPermission>
                   	<shiro:hasPermission name="/admin/role/delete">
                   	    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
               			str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="delFun(\'{0}\');" >删除</a>', row.roleId);
               		</shiro:hasPermission>
                    return str;
                }
            },{
                width : '120',
                title : '角色名称',
                align: 'center',
                field : 'name'
            }, {
                width : '120',
                title : '描述',
                field : 'description',
                align: 'center'},
            {
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
                $('.role-easyui-linkbutton-grant').linkbutton({text:'授权'});
                $('.role-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.role-easyui-linkbutton-del').linkbutton({text:'删除'});
            },
            toolbar : '#toolbar'
        });
    });
    
    // 查询
    function searchFun() {
    	console.log($.serializeObject($('#searchForm')));
        roleDataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    }
    
    // 清空
    function cleanFun() {
        $('#searchForm input').val('');
        roleDataGrid.datagrid('load', {});
    }
    
    // 添加角色
    function addRoleFun() {
       $('#addForm').dialog({
            title : '添加',
            width : 600,
            height : 350,
            href : '${path}/admin/role/addpage',
            modal: true ,
            buttons : [{
            	id: 'btn-save',
                text : '确认'
            }]
        });
    }
    
    // 授权
    function grantFun(id) {
    	$('#grantForm').dialog({
            title : '授权',
            width : 600,
            height : 350,
            href : '${path}/admin/role/grantpage/' + id,
            modal: true ,
            buttons : [{
            	id: 'btn-save',
                text : '确认'
            }]
        });
    }
    
    // 编辑角色
    function editRoleFun(id) {
    	$('#editForm').dialog({
            title : '编辑',
            width : 600,
            height : 350,
            href : '${path}/admin/role/editpage/' + id,
            modal: true ,
            buttons : [{
            	id: 'btn-save',
                text : '确认'
            }]
        });
    }
 
    // 删除角色-根据角色ID
    function delFun(id) {
        // 加parent会锁屏
        parent.$.messager.confirm('询问', '您是否要删除当前角色？', function(b) {
            if (b) {
            	$$.progressLoad();
            	$.ajax({
            		type: 'DELETE',
            		url: '${path }/admin/role/delete/' + id,
            		success: function (data) {
            			if (data && data.code == 0) {
                            parent.$.messager.alert('提示', data.msg, 'info');
                            roleDataGrid.datagrid('reload');
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

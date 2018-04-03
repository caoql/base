<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- 引入通用的JSP处理 --%>
<%@ include file="../../common/global.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/base.jsp"%>
<title>资源管理界面</title>
</head>
<body>
	<!-- 查询条件展示区域  begin-->
	<div>
		<form id="searchForm" onsubmit="return false">
			<table>
				<tr>
					<td><label for="name">资源名称:</label></td>
					<td><input type="text" id="name" name="name"></td>
					<td align="right">是否启用：</td>
					<td><input type="text" name="isEnabled"
						class="easyui-combobox"
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
					<td align="right">资源类型：</td>
					<td><input type="text" name="type" class="easyui-combobox"
						data-options="editable:false,panelHeight:'auto',
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
			</table>
			<div class="form-button">
				<a href="javascript:void(0);" class="easyui-linkbutton"
					data-options="plain:true" onclick="searchFun();">查询</a> <a
					href="javascript:void(0);" class="easyui-linkbutton"
					data-options="plain:true" onclick="cleanFun();">清空</a>
			</div>
		</form>
	</div>
	<!-- 查询条件展示区域  end-->

	<!-- 数据展示 begin -->
	<div class="easyui-layout" data-options="fit:true,border:false">
		<table id="resourceTreeGrid" title="资源界面">
		</table>
	</div>
	<!-- 数据展示 end -->

	<div id="toolbar" style="display: none;">
		<shiro:hasPermission name="/admin/resource/add">
		<a onclick="addResourceFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
		 </shiro:hasPermission>
		 <a onclick="collapseAll();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">折叠所有</a>
		 <a onclick="expandAll();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">展开所有</a>
	</div>

	<!-- 弹窗区 -->
	<div id="addForm"></div>
	<div id="editForm"></div>
	<script type="text/javascript">
		var resourceTreeGrid;
		$(function() {
			resourceTreeGrid = $('#resourceTreeGrid').treegrid({
				url : '${path}/admin/resource/list',
				idField : 'resourceId',
				striped : true,
				fit: true,
			    fitColumns: true,
			    border: false,
			    rownumbers: true,
				treeField: 'name',
			    parentField: 'pid',
				// 列对象
				columns : [ [ {
					field : 'action',
					title : '操作',
					width : 200,
					align : 'center',
					formatter : function(value, row, index) {
						var str = '';
						<shiro:hasPermission name="/admin/resource/edit"> 
							str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editResourceFun(\'{0}\');" >编辑</a>', row.resourceId); 
						</shiro:hasPermission> 
						<shiro:hasPermission name="/admin/resource/delete">
							str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
							str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteResourceFun(\'{0}\');" >删除</a>', row.resourceId);
						</shiro:hasPermission> 
						return str;
					}
				}, {
					width : '200',
					title : '资源名称',
					align : 'left',
					field : 'name'
				}, {
					width : '120',
					title : '资源类型',
					align : 'center',
					field : 'type'
				}, {
					width : '250',
					title : 'URL',
					field : 'url',
					align : 'center'
				}, {
					width : '100',
					title : '同级顺序',
					field : 'nodeOrder',
					align : 'center'
				}, {
					width : '100',
					title : '父级ID',
					field : 'pid',
					align : 'center',
					hidden: true
				}, {
					width : '120',
					title : '描述',
					field : 'description',
					align : 'center'
				}, {
					width : '100',
					title : '是否启用',
					field : 'isEnabled',
					align : 'center'
				}, {
					width : '180',
					title : '创建时间',
					field : 'createTime',
					align : 'center',
					sortable : true
				}, {
					width : '100',
					title : '创建人',
					field : 'creator',
					align : 'center'
				}, {
					width : '180',
					title : '更新时间',
					field : 'updateTime',
					align : 'center',
					sortable : true
				}, {
					width : '180',
					title : '更新人',
					field : 'updator',
					align : 'center'
				}, {
					width : '180',
					title : '备注',
					field : 'remark',
					align : 'center'
				} ] ],
				onLoadSuccess : function(data) {
					$('.user-easyui-linkbutton-edit').linkbutton({text:'编辑'});
					$('.user-easyui-linkbutton-del').linkbutton({text:'删除'});
				},
				toolbar : '#toolbar'
			});
		});

		// 查询
		function searchFun() {
			console.log($.serializeObject($('#searchForm')));
			resourceTreeGrid.treegrid('load', $.serializeObject($('#searchForm')));
		}

		// 清空
		function cleanFun() {
			$('#searchForm input').val('');
			resourceTreeGrid.treegrid('load', {});
		}

		// 添加资源
		function addResourceFun() {
			$('#addForm').dialog({
				title : '添加',
				width : 600,
				height : 350,
				href : '${path}/admin/resource/addpage',
				modal : true,
				buttons : [ {
					id : 'btn-save',
					text : '确认'
				} ]
			});
		}
		
		/**
		 * 折叠所有
		 */
		function collapseAll() {
			$('#resourceTreeGrid').treegrid('collapseAll');
		}

		/**
		 * 展开所有
		 */
		function expandAll() {
			$('#resourceTreeGrid').treegrid('expandAll');
		}

		// 编辑资源
		function editResourceFun(id) {
			$('#editForm').dialog({
				title : '编辑',
				width : 600,
				height : 350,
				href : '${path}/admin/resource/editpage/' + id,
				modal : true,
				buttons : [ {
					id : 'btn-save',
					text : '确认'
				} ]
			});
		}

		// 删除资源-根据资源ID
		function deleteResourceFun(id) {
			// 加parent会锁屏
			parent.$.messager.confirm('询问', '您是否要删除当前资源及其子资源？', function(b) {
				if (b) {
					$$.progressLoad();
					$.ajax({
								url : '${path }/admin/resource/delete/' + id,
								type : 'DELETE',
								success : function(data) {
									if (data && data.code == 0) {
										parent.$.messager.alert('提示', data.msg, 'info');
										resourceTreeGrid.treegrid('reload');
									} else {
										parent.$.messager.alert('错误', data.msg,'error');
									}
									$$.progressClose();
								},
								error : function(data) {
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

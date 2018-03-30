<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/global.jsp"%>
<script type="text/javascript">
	var resourceTree;
	$(function() {
		resourceTree = $('#resourceTree')
				.tree(
						{
							url : '${path }/admin/resource/allTrees',
							parentField : 'pid',
							lines : true,
							checkbox : true,
							onClick : function(node) {
							},
							onLoadSuccess : function(node, data) {
								$$.progressLoad();
								$
										.post(
												'${path }/admin/role/findResourceIdListByRoleId',
												{
													id : '${id}'
												},
												function(result) {
													console.log(result);
													var ids = [];
													if (result.code == true
															&& result.data != undefined) {
														ids = result.data;
													}
													for (var i = 0; i < ids.length; i++) {
														if (resourceTree.tree(
																'find', ids[i])) {
															resourceTree
																	.tree(
																			'check',
																			resourceTree
																					.tree(
																							'find',
																							ids[i]).target);
														}
													}
												}, 'json');
								$$.progressClose();
							},
							cascadeCheck : false
						});

		// 绑定提交
		$('#btn-save').on('click', function() {
			var checknodes = resourceTree.tree('getChecked');
			var ids = [];
			if (checknodes && checknodes.length > 0) {
				for (var i = 0; i < checknodes.length; i++) {
					ids.push(checknodes[i].id);
				}
			}
			$('#resourceIds').val(ids);
			$$.progressLoad();
			// 校验表单默认规则
			if (!$('#roleGrantForm').form('validate')) {
				$$.progressClose();
				return false;
			}
			// 获取数据
			var formdata = $$.serializeToJson('#roleGrantForm', true);
			if (!formdata) {
				$$.progressClose();
				return false;
			}
			// 发送请求处理
			$.ajax({
				type : 'POST',
				url : '${path }/admin/role/grant',
				data : formdata,
				success : function(data) {
					$$.progressClose();
					if (data != null) {
						if (data.code == 0) {
							$('#grantForm').dialog('close');
							$$.refreshDatagrid('datagrid');
						} else {
							$.messager.alert('提示信息', data.msg, 'error');
						}
					} else {
						$.messager.alert('提示信息', '新增失败', 'error');
					}
				}
			});
		});
	});

	function checkAll() {
		var nodes = resourceTree.tree('getChecked', 'unchecked');
		if (nodes && nodes.length > 0) {
			for (var i = 0; i < nodes.length; i++) {
				resourceTree.tree('check', nodes[i].target);
			}
		}
	}
	function uncheckAll() {
		var nodes = resourceTree.tree('getChecked');
		if (nodes && nodes.length > 0) {
			for (var i = 0; i < nodes.length; i++) {
				resourceTree.tree('uncheck', nodes[i].target);
			}
		}
	}
	function checkInverse() {
		var unchecknodes = resourceTree.tree('getChecked', 'unchecked');
		var checknodes = resourceTree.tree('getChecked');
		if (unchecknodes && unchecknodes.length > 0) {
			for (var i = 0; i < unchecknodes.length; i++) {
				resourceTree.tree('check', unchecknodes[i].target);
			}
		}
		if (checknodes && checknodes.length > 0) {
			for (var i = 0; i < checknodes.length; i++) {
				resourceTree.tree('uncheck', checknodes[i].target);
			}
		}
	}
</script>
<div id="roleGrantLayout" class="easyui-layout"
	data-options="fit:true,border:false">
	<div data-options="region:'west'" title="系统资源"
		style="width: 300px; padding: 1px;">
		<div class="well well-small">
			<form id="roleGrantForm" method="post">
				<input name="id" type="hidden" value="${id}" readonly="readonly">
				<ul id="resourceTree"></ul>
				<input id="resourceIds" name="resourceIds" type="hidden" />
			</form>
		</div>
	</div>
	<div data-options="region:'center'" title=""
		style="overflow: hidden; padding: 10px;">
		<div class="role-button">
			<button class="btn btn-success" onclick="checkAll();">全选</button>
			<br /> <br />
			<button class="btn btn-warning" onclick="checkInverse();">反选</button>
			<br /> <br />
			<button class="btn btn-inverse" onclick="uncheckAll();">取消</button>
		</div>
	</div>
</div>
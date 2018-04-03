<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/global.jsp"%>
<div data-options="fit:true,border:false">
	<form onsubmit="return false" id="formfile"
		enctype="multipart/form-data"
		style="text-align: center; width: 100%; padding-top: 16px">
		<input type="file" name="importExcelFile">
		<p id="errortxt" class="errors"></p>
	</form>
</div>
<script type="text/javascript">
	$(function() {
		$('#btn-save').on('click', function() {
			var formData = new FormData($("#formfile")[0]);
			$$.progressLoad();
			$.ajax({
				url : '${path }/admin/user/import',
				type : "POST",
				data : formData,
				async : false,
				cache : false,
				contentType : false,
				processData : false,
				success : function(data) {
					$$.progressClose();
					if (data != null) {
						if (data.code == 0) {
							$('#importForm').dialog('close');
							$$.refreshDatagrid('datagrid');
						} else {
							$.messager.alert('提示信息', data.msg, 'error');
						}
					} else {
						$.messager.alert('提示信息','导入失败','error');
					}
				}
			})
		});
	});
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/global.jsp" %>
<style>
.datagrid-header td{
	font-weight: bold;
}
/* .datagrid-body td{
	background-color:rgba(0, 113, 0, 0.682353);
} */
</style>
<script type="text/javascript">
    $(function () {
        $('#sysLogDataGrid').datagrid({
            url: '${path }/sysLog/dataGrid',
            striped: true,
            pagination: true,
            singleSelect: true,
            idField: 'id',
            pageSize: 30,
            pageList: [20, 30, 50, 100, 200, 400, 500],
            columns: [[{
                width: '80',
                title: '登录账号',
                field: 'loginName',
                sortable: true
            }, {
                width: '80',
                title: '用户名',
                field: 'roleName'
            }, {
                width: '160',
                title: 'IP地址',
                field: 'clientIp',
                sortable: true
            }, {
                width: '1200',
                title: '用户操作内容记录',
                field: 'optContent'
            }, {
                width: '180',
                title: '登录时间',
                field: 'createTime',
                formatter: function(value, row, index){
                	var value1 = new Date(value);
                	return dateFormatter(value1);
                }
            }]]
        });
    });
    /**
     * 时间格式化
     * @param value
     * @returns {string}
     */
    function dateFormatter (value) {
        var date = new Date(value);
        var year = date.getFullYear().toString();
        var month = (date.getMonth() + 1);
        var day = date.getDate().toString();
        var hour = date.getHours().toString();
        var minutes = date.getMinutes().toString();
        var seconds = date.getSeconds().toString();
        if (month < 10) {
            month = "0" + month;
        }
        if (day < 10) {
            day = "0" + day;
        }
        if (hour < 10) {
            hour = "0" + hour;
        }
        if (minutes < 10) {
            minutes = "0" + minutes;
        }
        if (seconds < 10) {
            seconds = "0" + seconds;
        }
        return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="sysLogDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
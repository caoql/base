/**
 * V1.0.0 有些东西是依赖jquery,easyUI的,所以在引入这个文件之前请先引入jquery,easyUI相关的文件 二次封装,方便使用。
 */

var obj = {
	// 页面加载加载进度条启用
	progressLoad : function() {
		$(
				"<div class=\"datagrid-mask\" style=\"position:absolute;z-index: 9999;\"></div>")
				.css({
					display : "block",
					width : "100%",
					height : $(window).height()
				}).appendTo("body");
		$(
				"<div class=\"datagrid-mask-msg\" style=\"position:absolute;z-index: 9999;\"></div>")
				.html("正在处理，请稍候。。。").appendTo("body").css({
					display : "block",
					left : ($(document.body).outerWidth(true) - 190) / 2,
					top : ($(window).height() - 45) / 2
				});
	},

	// 页面加载加载进度条关闭
	progressClose : function() {
		$(".datagrid-mask").remove();
		$(".datagrid-mask-msg").remove();
	},
	/**
	 * 格式化表单内容为json字符串
	 * 
	 * @param selector
	 *            容器筛选器
	 * @param notEmptyField
	 *            true：过滤掉内容为空的字段，否则空字段保留
	 * @return 表单内容格式化后的json对象
	 */
	serializeToJson : function(selector, notEmptyField) {
		var obj = {};
		$.each($(selector).serializeArray(), function(i, o) {
			var n = o.name, v = isNaN(o.value) === true ? o.value : $
					.trim(o.value);
			if (!(notEmptyField && "" == v)) {
				obj[n] = (obj[n] === undefined) ? v
						: $.isArray(obj[n]) ? obj[n].concat(v) : [ obj[n], v ];
			}
		});
		return obj;
	},
	// 刷新datagrid数据列表
	refreshDatagrid : function(datagridId, type) {
		if ("queryAll" == type) {
			$('#' + datagridId).datagrid('options').queryParams = "{}";
		}
		$('#' + datagridId).datagrid('uncheckAll');
		$('#' + datagridId).datagrid('unselectAll');
		$('#' + datagridId).datagrid('reload');
	},
	// 刷新treegrid数据列表
	refreshTreegrid : function(treegridId, type) {
		if ("queryAll" == type) {
			$('#' + treegridId).treegrid('options').queryParams = "{}";
		}
		$('#' + treegridId).treegrid('uncheckAll');
		$('#' + treegridId).treegrid('unselectAll');
		$('#' + treegridId).treegrid('reload');
	},
	/**
	 * 弹出操作窗口，框架中所有弹窗中的内容都必需是一个单独的页面，通过url指定
	 * 
	 * @param href
	 *            弹窗中将要显示的页面
	 * @param title
	 *            弹窗标题
	 * @param maxHeight
	 *            弹窗最大高度，如果超过浏览器窗口高度，则按照浏览器窗口的高宽比自动调节缩小
	 * @param maxWidht
	 *            弹窗最大宽度 如果超过浏览器窗口的宽度，则按照参数widthRate调整
	 * @param widthRate
	 *            窗口宽度与浏览器宽度百分比，框架会按照该比调整弹窗宽度到最接近maxWidht
	 * 
	 */
	openJcdfDialog : function(href, title, maxHeight, maxWidth, widthRate) {
		if (!widthRate) {
			widthRate = 0.98;
		}
		window.top.jcdfDialog(window.self.name, href, title, maxHeight,
				maxWidth, widthRate);
	},
	/**
	 * 窗口关闭，如果采用了JCDF框架的弹窗，就必须采用JCDF的该关闭方法
	 */
	closeJcdfDialog : function() {
		window.top.closeJcdfDialog();
	},
	// 时间格式化
	dateFormatter : function(value) {
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
		return year + "-" + month + "-" + day + " " + hour + ":" + minutes
				+ ":" + seconds;
	}

}

var BASE = obj;
var $$ = obj;

/**
 * 
 * 增加formatString功能
 * 
 * 使用方法：$.formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 * 
 * @returns 格式化后的字符串
 */
$.formatString = function(str) {
	for (var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * 
 * @requires jQuery
 * 
 * 将form表单元素的值序列化成对象
 * 
 * @returns object
 */
$.serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

/**
 * 
 * 接收一个以逗号分割的字符串，返回List，list里每一项都是一个字符串
 * 
 * @returns list
 */
$.stringToList = function(value) {
    if (value != undefined && value != '') {
        var values = [];
        var t = value.split(',');
        for ( var i = 0; i < t.length; i++) {
            values.push('' + t[i]);/* 避免他将ID当成数字 */
        }
        return values;
    } else {
        return [];
    }
};
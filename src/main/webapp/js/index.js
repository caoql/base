//记录窗口中间内容展示区的高度，便于后面所欲iframe的统一控制
var centerHeight = 0;
//模拟菜单栏的数据
var menuData = [
	//type:F-菜单组,A-菜单项
	//订单管理
	{icon:"",group:"default",type:"F",level:0,menus:[{icon:"",group:"order",type:"A",level:1,menus:[],id:"orderInfoList",name:"订单记录",url:"ui/view/order/orderInfoList.html"}],id:"order",name:"订单管理",url:""},
	
	//系统管理
	{icon:"",group:"default",type:"F",level:0,menus:[{icon:"",group:"admin",type:"A",level:1,menus:[],id:"roleInfoList",name:"用户管理",url:"/base/admin/user/manager"}
	,{icon:"",group:"admin",type:"A",level:1,menus:[],id:"userInfoList",name:"角色管理",url:"/base/admin/role/manager"},
	{icon:"",group:"admin",type:"A",level:1,menus:[],id:"user2RoleInfoList",name:"资源管理",url:"/base/admin/resource/manager"},
	{icon:"",group:"admin",type:"A",level:1,menus:[],id:"resourceInfoList",name:"组织管理",url:"/base/admin/organization/manager"},
	],id:"admin",name:"系统管理",url:""}
];

/**
*初始化函数
*/
function init() {
	//获取登录用户信息
	setCurrentUser();
	//获取菜单列表
	getUserMenus(menuData);
	//获取centerTab的高度
	centerHeight = $("#welcomeTab").parent().height()-4;
	//面板统一设置
	$('#centerTab').tabs({
		cache:false,
		onClose:function() {
			return false;
		}
	});
	//Tab鼠标右击事件绑定
	bindTabEvent(); 
	bindTabMenuEvent();
}

/**
* 设置登录用户信息
*/
function setCurrentUser(){
	//后期要修改...
	$("#currentUser").html("CAOQL");
	$("#currentRole").html("管理员");
	//要后台请求
	/*$.ajax({
		type : "POST",
		url : appId + '/admin/login.do',
		data:{"username":"caoql","password":"123"},
		success : function(data) {
			if (null != data && data.code==0) {
				$("#currentUser").html("CAOQL");
				$("#currentRole").html("管理员");
			}  else {
				$.messager.alert('提示消息', data.message, 'warning'); 
			}
		}
	});*/
}

/**
 * 获取用户菜单栏
 * @param 菜单栏数据
 */
function getUserMenus(data) {
	generateMenu(data);
	//设置折叠面板选中第一个,索引从0开始
    $('#sysMenu').accordion('select', 0);
}

/**
 * 设置菜单栏
 * @param 菜单栏数据
 * @returns {String}
 */
function generateMenu(menus) {
	var lis = '';
	var len = menus.length;
	for(var i = 0; i < len; i < i++) {
		var icon = menus[i].icon;
		//菜单样式
		var iconCls = (icon === '' ? '' : icon);
		if(menus[i].type === 'F' && menus[i].level === 0) {
			//左侧最外层菜单组添加图标
			iconCls = 'icon-tools';
			//添加一个菜单组
			$('#sysMenu').accordion('add', {
				title:menus[i].name,
				iconCls:iconCls,
				//利用回掉函数来加载菜单项
				content:'<div class="module-panel"><ul class="easyui-tree">' + generateMenu(menus[i].menus) + '</ul></div>'
			});
		} else if(menus[i].type === 'F') {			
			lis += '<li iconCls="' + iconCls + '"><span>' + menus[i].name + '</span><ul>' + generateMenu(menus[i].menus) + '</ul></li>';
		} else if(menus[i].type === 'A') {
			lis += '<li iconCls="' + iconCls + '">' +
			'<a id=' + menus[i].id + ' link="' + menus[i].url + '" onclick=openNewTab("' + menus[i].id + '") >' + menus[i].name + '</a>' +
		    '</li>';
		}
	}
	return lis;
};


var index = 0;
/**
*以tab的形式打开一个模块
*/
function openNewTab(id) {
	index = index + 1;
	//超链接的内容
	var title = $('#' + id).text();
	//超链接的属性link值
	var href = $('#' + id).attr('link');
	if($('#centerTab').tabs('exists', title)) {
		$('#centerTab').tabs('select', title);
	} else {
		$('#centerTab').tabs('add', {
			id:id,
			title:title,
			content:'<iframe id="frame_"' + id + ' name="frame_' + id + '" width="100%" height="' + centerHeight + '" src="' + href + '" frameborder="0" scrolling="auto" marginheight="0" marginwidth="0"></iframe>',
			closable:true
		});
	}
}

/**
 *绑定tab的双击事件、右键事件 
 */ 
function bindTabEvent(){  
    $(document).on('dblclick','.tabs-inner',function(){  
        var subtitle = $(this).children("span").text();  
        if($(this).next().is('.tabs-close')){  
            $('#centerTab').tabs('close',subtitle);  
        }  
    });  
    $(document).on('contextmenu','.tabs-inner',function(e){  
        $('#mm').menu('show', {  
            left: e.pageX,  
            top: e.pageY  
     });  
     var subtitle =$(this).children("span").text();  
     $('#mm').data("currtab",subtitle);  
     $('#centerTab').tabs('select', subtitle);
     return false;  
    });  
 } 

 //绑定tab右键菜单事件  
function bindTabMenuEvent() {  
    //关闭当前  
    $('#mm-tabclose').click(function() {  
        var currtab_title = $('#mm').data("currtab");  
        if (currtab_title!="欢迎页面") {  
            $('#centerTab').tabs('close', currtab_title);  
        }  
    });  
    //全部关闭  
    $('#mm-tabcloseall').click(function() {  
        $('.tabs-inner span').each(function(i, n) {  
            if ($(this).parent().next().is('.tabs-close')) {  
                var t = $(n).text();  
                $('#centerTab').tabs('close', t);  
            }  
        });  
    });  
    //关闭除当前之外的TAB  
    $('#mm-tabcloseother').click(function() {  
        var currtab_title = $('#mm').data("currtab");  
        $('.tabs-inner span').each(function(i, n) {  
            if ($(this).parent().next().is('.tabs-close')) {  
                var t = $(n).text();  
                if (t != currtab_title)  
                    $('#centerTab').tabs('close', t);  
            }  
        });  
    });  
    //关闭当前右侧的TAB  
    $('#mm-tabcloseright').click(function() {  
        var nextall = $('.tabs-selected').nextAll();  
        if (nextall.length == 0) {   
            return false;  
        }  
        nextall.each(function(i, n) {  
            if ($('a.tabs-close', $(n)).length > 0) {  
                var t = $('a:eq(0) span', $(n)).text();  
                $('#centerTab').tabs('close', t);  
            }  
        });  
        return false;  
    });  
    //关闭当前左侧的TAB  
    $('#mm-tabcloseleft').click(function() {  
        var prevall = $('.tabs-selected').prevAll();  
        if (prevall.length == 1) {  
            return false;  
        }  
        prevall.each(function(i, n) {  
            if ($('a.tabs-close', $(n)).length > 0) {  
                var t = $('a:eq(0) span', $(n)).text();  
                $('#centerTab').tabs('close', t);  
            }  
        });  
        return false;  
    });  
}  

//打开对话框
function jcdfDialog(frameName, href, title, maxHeight, maxWidth, widthRate) {
	var dialogDiv = $("#jcdfDiglogDiv");
	if(!dialogDiv || dialogDiv.length <= 0) {
		var html = '<div id="jcdfDiglogDiv" style="display: none;">'+
			'<iframe id="jcdfDiglogDivIframe" name="" width="100%" height="200" src="" frameborder="0" scrolling="auto" marginheight="0" marginwidth="0"></iframe>'+
			'</div>';
		$("body").append(html);
	}
	$("#jcdfDiglogDivIframe").attr('src', href);
	$('#jcdfDiglogDiv').css('display','');
	$('#jcdfDiglogDiv').dialog({
		title:title,
		modal:true,
		maximizable:true,
		resizable:true,
		closed: false,
		onOpen:function() {
			$$.fillDialogWidthAndHeight("jcdfDiglogDiv", widthRate, maxHeight, maxWidth);
			$("#jcdfDiglogDivIframe").height($("#jcdfDiglogDivIframe").parent().height()-4);
		},
		onResize:function() {
			$("#jcdfDiglogDivIframe").height($("#jcdfDiglogDivIframe").parent().height()-4);
		},
		onClose:function() {
			$("#jcdfDiglogDivIframe").attr('src', 'about:blank');
		}
	});
}

//关闭窗口
function closeJcdfDialog() {
	$('#jcdfDiglogDiv').dialog('close');
}


/**
 * 操作
 */
$(function(){
	//退出登录
	$('#btn-logout').bind('click', function() {
		$.messager.confirm('提示消息', '确定退出?', function(r) {
			if(r) {
				$.ajax({
					type : 'DELETE',
					url : appId + '/admin/loginOut.do',
					success : function(data) {
						if (data && data.code == 0) {
							window.location.href = appId + '/login.html';
						}else{
							$.messager.alert('提示消息', data.message, 'warning');
						}
					}
				});
			}
		});
	});
});

//更换主题
function changeCss(cssName) {
	var ctrlLink = document.getElementById("changePF");

	var cssOld = ctrlLink.getAttribute("href");
	//不断尝试替换
	var cssNew = cssOld.replace("default", cssName);
	cssNew = cssNew.replace("bootstrap", cssName);
	cssNew = cssNew.replace("gray", cssName);
	//重设值
	ctrlLink.setAttribute("href", cssNew);
	  
}
  
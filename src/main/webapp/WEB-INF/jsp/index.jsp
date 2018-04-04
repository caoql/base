<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="common/base.jsp" %>
	<title>base系统</title> 
	<link rel="stylesheet" type="text/css" href="${path}/css/index.css" />
</head>
<body class="easyui-layout" data-options="fit:true">
	<!-- 顶部信息展示区 begin -->
    <div class="header" data-options="region:'north', border:false">
    	<div class="logo"></div>
    	<div class="title">base系统</div>
    	<div class="welcome"><span id="btn-loginout">退出</span>&nbsp;&nbsp;|您好,<span id="currentUser">XXX</span>(<em id="currentRole">YYY</em>)</div>
    </div>
    <!-- 顶部信息展示区 end -->
    
    <!-- 左边菜单栏  begin -->
    <div id="menubar" data-options="region:'west', title:'系统菜单', border:false, split:true, iconCls:'icon-blank', collapsed:false">
         <div class="easyui-layout" data-options="fit:true">
	         <!-- 系统菜单首部展示区          begin-->
	         <div id="menu_header" data-options="region:'center', border:false, split:true, collapsible:'true'">
	             <!-- 折叠面板展示菜单 -->
	             <div id="sysMenu" class="easyui-accordion" data-options="border:false">
	             </div> 
	         </div>
	         <!-- 系统菜单首部展示区          end-->
         </div>
    </div>
	<!-- 左边菜单栏  end -->	
	 
	<!-- 中右边欢迎页Tab begin -->
    <div class="main" data-options="region:'center', border:false">
        <div id="centerTab" class="easyui-tabs" data-options="plain:true, fit:true, tabHeight:40">
			<div id="welcomeTab" title="欢迎页面" data-options="closable:false">
			</div>
		</div>
    </div>
	<!-- 中右边欢迎页Tab end -->
	
	<!-- Tab右键展示区 begin -->
    <div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabclose">关闭</div>
        <div id="mm-tabcloseall">关闭全部</div>
        <div id="mm-tabcloseother">关闭其他</div>
        <div class="menu-sep"></div>
        <div id="mm-tabcloseright">关闭右侧标签</div>
        <div id="mm-tabcloseleft">关闭左侧标签</div>
    </div>
    <!-- Tab右键展示区 end -->
	<script type="text/javascript">
		// 初始化函数執行
		$(function(){
			//获取菜单列表
			getUserMenus();
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
			
			// 退出
			$('#btn-loginout').on('click', function() {
				parent.$.messager.confirm('询问', '确认要退出系统吗?', function(b) {
					if (b) {
						$$.progressLoad();
						$.ajax({
									url : '${path }/loginout',
									type : 'POST',
									success : function(data) {
										$$.progressClose();
										if (data && data.code == 0) {
											setTimeout(function(){
												window.location.href = "${path }/login.jsp";
											}, 1000);
										} else {
											parent.$.messager.alert('错误', data.msg,'error');
										}
									}
								});
					}
				});
			});
		});
		
		//记录窗口中间内容展示区的高度，便于后面所欲iframe的统一控制
		var centerHeight = 0;
		
		/**
		 * 获取用户菜单栏
		 * @param 菜单栏数据
		 */
		function getUserMenus() {
			// 发送请求获取数据
			$.ajax({
           		type: 'POST',
           		url: '${path }/getresources',
           		success: function (data) {
           			//console.log(data);
           			if (data && data.code == 0) {
           				generateMenu(data.data);
                     } else {
                         parent.$.messager.alert('错误', '出错了', 'error');
                     }
           		}
           	});
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
					'<a id=' + menus[i].id + ' link="${path}' + menus[i].url + '" onclick=openNewTab("' + menus[i].id + '") >' + menus[i].name + '</a>' +
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
		        	 iscloselog=true;
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
		        	 iscloselog=true;
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
	</script>
</body>
</html>

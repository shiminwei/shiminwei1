<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/base.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${projectTitle }</title>
<link rel="shortcut icon" href="${basePath}/fav.ico" type="image/x-icon">
<link href="${basePath }static/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${basePath }static/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${basePath }static/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="${basePath }static/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<link rel="stylesheet" href="${basePath }static/css/font-awesome.min.css">
<link rel="stylesheet" href="${basePath }static/css/font-awesome-ie7.min.css">
<!--[if IE]>
<link href="${basePath }static/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->
<!--[if lt IE 9]><script src="${basePath }static/js/speedup.js" type="text/javascript"></script><script src="${basePath }static/js/jquery-1.11.3.min.js" type="text/javascript"></script><![endif]-->
<!--[if gte IE 9]><!--><script src="${basePath }static/js/jquery-2.1.4.min.js" type="text/javascript"></script><!--<![endif]-->

<script src="${basePath }static/js/jquery.cookie.js" type="text/javascript"></script>
<script src="${basePath }static/js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath }static/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="${basePath }static/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

<!-- 可以用dwz.min.js替换前面全部dwz.*.js (注意：替换时下面dwz.regional.zh.js还需要引入)-->
<script src="${basePath }static/js/dwz.min.js" type="text/javascript"></script>

<script src="${basePath }static/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="${basePath }static/js/cd_common.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	DWZ.init("${basePath }static/dwz.frag.xml", {
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		keys: {statusCode:"statusCode", message:"message"}, //【可选】
		ui:{hideMode:'offsets'}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"${basePath }static/themes"}); // themeBase 相对于index页面的主题base路径
		}
	});
	if ($("#divCurPage").html() == '') {
		navTab.init();
		navTab.openTab('main',
				'${basePath }web/index', {
					title : '首页',
					fresh : true,
					data : {}
				});
	}

});
</script>
</head>
<body>
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="#"></a>
				<ul class="nav">
					<li><span style="color:#EEF1E3;">欢迎您，${ sessionScope.currentLogonUser.userCode}（${ sessionScope.currentLogonUser.userName}）</span></li>
					<li><a href="${basePath }logout">退出</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected">蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>
					<li theme="azure"><div>天蓝</div></li>
				</ul>
			</div>
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse"> 
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>

				<div class="accordion" fillSpace="sidebar">
					<div class="accordionHeader">
						<h2><span>Folder</span>我的主页</h2>
					</div>
					<div class="accordionContent">	
						<ul class="tree treeFolder">
							<li><a href="${basePath }web/index" target="navTab" rel="main">首页</a></li>
							<li><a href="${basePath }web/schedule" target="navTab" rel="main">调度详细</a></li>
						</ul>
					</div>
					<div class="accordionHeader">
						<h2><span>Folder</span>调度管理</h2>
					</div>
					<div class="accordionContent">	
						<ul class="tree treeFolder">
							<li><a href="${basePath }web/dataJob/list" target="navTab" rel="dataJobList">调度列表</a></li>
							<li><a href="${basePath }web/dataJob/log" target="navTab" rel="logInfo">调度日志</a></li>
						</ul>
					</div>
					
					
					
					<div class="accordionHeader">
						<h2><span>Folder</span>系统管理</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="${basePath }web/user/list" target="navTab" rel="userManager">联系人信息</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">首页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">首页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox" id="divCurPage"></div>
			</div>
		</div>
	</div>
	<div id="footer">Copyright &copy; 2016.安徽政大数据软件有限公司 All rights reserved</div>
</body>
</html>
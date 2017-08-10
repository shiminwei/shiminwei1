<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${projectTitle }</title>
<link rel="shortcut icon" href="${basePath}/fav.ico" type="image/x-icon">
	<link href="${basePath }static/themes/default/style.css"
		rel="stylesheet" type="text/css" media="screen" />
	<link href="${basePath }static/themes/css/core.css" rel="stylesheet"
		type="text/css" media="screen" />
	<link href="${basePath }static/themes/css/print.css" rel="stylesheet"
		type="text/css" media="print" />
	<link href="${basePath }static/uploadify/css/uploadify.css"
		rel="stylesheet" type="text/css" media="screen" />
	<!--[if IE]>
<link href="${basePath }static/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->
	<!--[if lt IE 9]><script src="${basePath }static/js/speedup.js" type="text/javascript"></script><script src="${basePath }static/js/jquery-1.11.3.min.js" type="text/javascript"></script><![endif]-->
	<!--[if gte IE 9]><!-->
	<script src="${basePath }static/js/jquery-2.1.4.min.js"
		type="text/javascript"></script>
	<!--<![endif]-->

	<script src="${basePath }static/js/jquery.cookie.js"
		type="text/javascript"></script>
	<script src="${basePath }static/js/jquery.validate.js"
		type="text/javascript"></script>
	<script src="${basePath }static/js/jquery.bgiframe.js"
		type="text/javascript"></script>
	<script src="${basePath }static/xheditor/xheditor-1.2.2.min.js"
		type="text/javascript"></script>
	<script src="${basePath }static/xheditor/xheditor_lang/zh-cn.js"
		type="text/javascript"></script>
	<script src="${basePath }static/uploadify/scripts/jquery.uploadify.js"
		type="text/javascript"></script>

	<!-- 可以用dwz.min.js替换前面全部dwz.*.js (注意：替换时下面dwz.regional.zh.js还需要引入)-->
	<script src="${basePath }static/js/dwz.min.js" type="text/javascript"></script>
	<script src="${basePath }static/js/dwz.regional.zh.js"
		type="text/javascript"></script>
	<script src="${basePath }static/js/cd_common.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			DWZ.init("${basePath }static/dwz.frag.xml", {
				statusCode : {
					ok : 200,
					error : 300,
					timeout : 301
				}, //【可选】
				pageInfo : {
					pageNum : "pageNum",
					numPerPage : "numPerPage",
					orderField : "orderField",
					orderDirection : "orderDirection"
				}, //【可选】
				keys : {
					statusCode : "statusCode",
					message : "message"
				}, //【可选】
				ui : {
					hideMode : 'offsets'
				}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
				debug : false, // 调试模式 【true|false】
				callback : function() {
					initEnv();
					$("#themeList").theme({
						themeBase : "${basePath }static/themes"
					}); // themeBase 相对于index页面的主题base路径
				}
			});
			if ($("#divCurPageIndex").html() == '') {
				navTab.init();
				navTab
						.openTab(
								'A_2032',
								'${basePath }/web/modelShow/economicList?year=2017&month=05',
								{
									title : '主要经济指标完成情况表',
									fresh : true,
									data : {}
								});
			}
			;
		});
	</script>
</head>

<body>
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="#"></a>

				<ul class="nav">
					<li><span><a style="color: #EEF1E3">欢迎您,${ sessionScope.currentLogonUser.userCode}（${ sessionScope.currentLogonUser.userName}）</a></span></li>
					<c:if
						test="${ sessionScope.currentLogonUser.userCode eq 'admin' and sessionScope.currentLogonUser.userId eq 1}">
						<li><a href="${basePath }admin/index" target="_blank">管理后台</a></li>
					</c:if>
					<li><a href="javascript:void(0)" onclick="gotoEditPwd()">修改密码</a></li>
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
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse">
					<h2>主菜单</h2>
					<div>收缩</div>
				</div>
				<div class="accordion" fillSpace="sidebar">
					<c:forEach items="${menuListNew}" var="menuFirst">
						<div class="accordionHeader">
							<h2>
								<span>Folder</span>${menuFirst.name }
							</h2>
						</div>
						<div class="accordionContent ">
							<ul class="tree treeFolder collapse">
								<c:forEach items="${menuFirst.childrenMenuList}"
									var="menuSecond">
									<li><c:if test="${menuSecond.functionId==null}">
											<a href="javascript:void(0)">${menuSecond.name}</a>
										</c:if> <c:if
											test="${menuSecond.functionId!=null && menuSecond.menuType ==2}">
											<a
												href=" ${basePath }pageList/public?functionid=${menuSecond.functionId}"
												target="navTab"
												<%-- rel="showCyList${menuSecond.functionId}" --%> rel="${menuSecond.parentCode }"
												title="${menuSecond.parentName }:&nbsp&nbsp${menuSecond.name}">

												${menuSecond.name}</a>
										</c:if> <c:if
											test="${menuSecond.functionId!=null && menuSecond.menuType ==3}">
											<c:choose>
												<c:when
													test="${ fn:startsWith(menuSecond.functionId,'http')}">
													<a href="${menuSecond.functionId}" target="navTab"
														rel="${menuSecond.code }">${menuSecond.name}</a>
												</c:when>
												<c:otherwise>
													<a href="${basePath }${menuSecond.functionId}"
														target="navTab"
														<%--  rel="${menuSecond.code }" --%> rel="${menuSecond.parentCode }"
														title="${menuSecond.parentName }:&nbsp&nbsp${menuSecond.name}">${menuSecond.name}</a>
												</c:otherwise>
											</c:choose>
										</c:if> <c:if
											test="${menuSecond.childrenMenuList !=null && fn:length(menuSecond.childrenMenuList) > 0}">
											<ul>
												<c:forEach items="${menuSecond.childrenMenuList}"
													var="menuThird">
													<li><c:if test="${menuThird.functionId==null}">
															<a href="javascript:void(0)">${menuThird.name}</a>
														</c:if> <c:if
															test="${menuThird.functionId!=null && menuThird.menuType ==2}">
															<a
																href=" ${basePath }pageList/public?functionid=${menuThird.functionId}"
																target="navTab"
																<%--  rel="showCyList${menuThird.functionId}" --%> rel="pageList">
																${menuThird.name}</a>
														</c:if> <c:if
															test="${menuThird.functionId!=null && menuThird.menuType ==3}">
															<c:choose>
																<c:when
																	test="${ fn:startsWith(menuThird.functionId,'http')}">
																	<a href="${menuThird.functionId}" target="navTab"
																		rel="${menuThird.code }">${menuThird.name}</a>
																</c:when>
																<c:otherwise>
																	<a href="${basePath }${menuThird.functionId}"
																		target="navTab"
																		<%-- rel="${menuThird.code }" --%> rel="pageList">${menuThird.name}</a>
																</c:otherwise>
															</c:choose>
														</c:if></li>
												</c:forEach>
											</ul>
										</c:if></li>
								</c:forEach>
							</ul>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>

		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<ul class="navTab-tab">
							<li tabid="A_2032" class="selected"><a href="javascript:;"><span>主要经济指标完成情况表</span>
									<a href="javascript:;" class="close">close</a> </a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 		禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">主要经济指标完成情况表</a> <a
						href="javascript:;" class="close">close</a></li>
				</ul>
<!-- 				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox" id="divCurPage">
						<div id="divCurPageIndex"
							style="width: 80%; margin: 0 auto; height: auto; overflow: x:scroll;"></div>
					</div>
				</div> -->
				<div class="navTab-panel tabsPageContent layoutBox" style="height:20%;">
					<div class="page unitBox"  id="divCurPageIndex"></div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">Copyright &copy; 2016.安徽政大数据软件有限公司 All rights
		reserved</div>
	<%-- <c:if test="${indexMenu ne null}">
	<script type="text/javascript">
	$(function() {
		if ($("#divCurPage").html() == '') {
			navTab.init();
			navTab.openTab('${indexMenu.code}',
					'${basePath }${indexMenu.functionId}', {
						title : '${indexMenu.name}',
						fresh : true,
						data : {}
					});
		}
	});
	</script>
	</c:if>  --%>



</body>
</html>
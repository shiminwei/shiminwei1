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
						<div class="accordionContent " >
							<ul class="tree treeFolder collapse">
								<c:forEach items="${menuFirst.childrenMenuList}"
									var="menuSecond">
									<li><c:if test="${menuSecond.functionId==null}">
											<a href="javascript:void(0)">${menuSecond.name}</a>
										</c:if> <c:if
											test="${menuSecond.functionId!=null && menuSecond.menuType ==2}">
											<a
												href=" ${basePath }pageList/public?functionid=${menuSecond.functionId}"
												target="navTab" rel="showCyList${menuSecond.functionId}">
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
														target="navTab" rel="${menuSecond.code }">${menuSecond.name}</a>
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
																target="navTab" rel="showCyList${menuThird.functionId}">
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
																		target="navTab" rel="${menuThird.code }">${menuThird.name}</a>
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
		<div id="container" >
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span
										class="home_icon">经济指标</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">${indexMenu.name}</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox" >
					<div class="page unitBox" id="divCurPage"  style="height:100%;overflow:scroll;">					
							<div style="width: 80%;margin: 0 auto; height: auto; overflow:x:scroll;">
						<div class="pageContent" >
								<h1 width="center" align="center"
									style="font-size: 20px; margin-top: 20px;">池州市2017年主要经济指标完成情况</h1>
								<table class="list" width="100%" style="margin-top: 10px;">
									<thead>
										<tr>
											<th width="center" align="center">指标名称</th>
											<th width="center" align="center">单位</th>
											<th width="center" align="center">金额</th>
											<th width="center" align="center">增幅</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td width="center" align="center">生产总值</td>
											<td width="center" align="center">万元</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>
										<tr>
											<td width="center" align="center">规模以上工业增加值</td>
											<td width="center" align="center">万元</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>
										<tr>
											<td width="center" align="center">固定资产投资完成额</td>
											<td width="center" align="center">万元</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>

										<tr>
											<td width="center" align="center">财政总收入</td>
											<td width="center" align="center">万元</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>

										<tr>
											<td width="center" align="center">财政支出</td>
											<td width="center" align="center">万元</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>

										<tr>
											<td width="center" align="center">社会消费品零售总额</td>
											<td width="center" align="center">万元</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>

										<tr>
											<td width="center" align="center">外贸进出口总额</td>
											<td width="center" align="center">万元</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>

										<tr>
											<td width="center" align="center">全社会用电量</td>
											<td width="center" align="center">万元</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>
										<tr>
											<td width="center" align="center">旅游总收入</td>
											<td width="center" align="center">万元</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>

										<tr>
											<td width="center" align="center">接待游客人数</td>
											<td width="center" align="center">万元</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>			
									</tbody>
								</table>


								<h1 width="center" align="center"
									style="font-size: 20px; margin-top: 50px;">池州市2017年财政收入分地域部门完成情况</h1>
								<table class="list" width="100%" style="margin-top: 10px;margin-bottom: 10px;">
									<thead>
										<tr>
											<th width="center" align="center" colspan="0">地区</th>
											<th width="center" align="center" colspan="0">完成实绩</th>
											<th width="center" align="center" colspan="5">分部门完成情况</th>
											<th width="center" align="center" colspan="5">分部门完成增幅</th>
											<th width="center" align="center" colspan="2">非税及占比情况</th>
										</tr>
										<tr>
											<th width="center" align="center"></th>
											<th width="center" align="center"></th>
											<th width="center" align="center">国税</th>
											<th width="center" align="center">地税</th>
											<th width="center" align="center">财政</th>
											<th width="center" align="center">海关</th>
											<th width="center" align="center">省直地税</th>
											<th width="center" align="center">国税</th>
											<th width="center" align="center">地税</th>
											<th width="center" align="center">财政</th>
											<th width="center" align="center">海关</th>
											<th width="center" align="center">省直地税</th>
											<th width="center" align="center">非税</th>
											<th width="center" align="center">占比</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td width="center" align="center">市直</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>
										<tr>
											<td width="center" align="center">贵池区</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>

										<tr>
											<td width="center" align="center">石台县</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>
										<tr>
											<td width="center" align="center">青阳县</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>
										<tr>
											<td width="center" align="center">东至县</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>
										<tr>
											<td width="center" align="center">九华山</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>
										<tr>
											<td width="center" align="center">开发区</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>
										<tr>
											<td width="center" align="center">江南集中区</td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
											<td width="center" align="center"></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">Copyright &copy; 2016.安徽政大数据软件有限公司 All rights
		reserved</div>
	<%-- 	<c:if test="${indexMenu ne null}">
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
	</c:if> --%>
</body>
</html>
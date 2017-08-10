<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../common/base.jsp" %> 
<c:if test="${type eq 1}">
<div class="accordion" fillSpace="sidebar">
		<div class="accordionContent">
		<ul class="tree treeFolder">
			<li><a href="${basePath }admin/department/show" target="navTab" rel="departmentList">组织机构管理</a></li>
		</ul>
	</div>
</div>
</c:if>
<c:if test="${type eq 2}">
<div class="accordion" fillSpace="sidebar">
		<div class="accordionContent">
		<ul class="tree treeFolder">
			<li><a href="tabsPage.html" target="navTab">报表配置</a></li>
		</ul>
	</div>
</div>
</c:if>
<c:if test="${type eq 3}">
<div class="accordion" fillSpace="sidebar">
		<div class="accordionContent">
		<ul class="tree treeFolder">
			<li><a href="${basePath }admin/user/list" target="navTab">采集用户</a></li>
			<li><a href="${basePath }admin/template/list" target="navTab">采集模板</a></li>
			<li><a href="${basePath }admin/datasourceConfig/list" target="navTab"  rel="datasourceConfigList">数据源</a></li>
		</ul>
	</div>
</div>
</c:if>
<c:if test="${type eq 4}">
<div class="accordion" fillSpace="sidebar">
		<div class="accordionContent">
		<ul class="tree treeFolder">
			<li><a href="tabsPage.html" target="navTab">系统公告</a></li>
		</ul>
	</div>
</div>
</c:if>
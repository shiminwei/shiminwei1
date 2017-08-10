<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<%@ taglib prefix="zd" uri="../../../../WEB-INF/zdtag.tld"%>
<style>
<!--
table thead tr th,table tbody tr td{
	text-align: center;
}
-->
</style>
<form id="pagerForm" method="post" action="${basePath }/admin/data/toReportInfo">
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
</form>

<form action="${basePath }/admin/data/toReportInfo" method="post"  name="searchForm"  id="searchForm" onsubmit="return navTabSearch(this);">
<div class="pageHeader">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>模板名称：</label>
				<!-- 查询按钮失效bug更改    peixizhu   2017-02-24 -->
				<input type="text" id="templateName" name="templateName" value="${sysDepartmentTemplate.templateName}"/>
				<%-- <input style="margin-left:-20px" type="text" id="searchForm" name="searchForm" name="templateName" value="${sysDepartmentTemplate.templateName}"/> --%>
			</li>
		</ul>
	</div>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" id="abyexport" href="#" onclick="cleanForm('searchForm','navTab')"><span style="">清空查询</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="90">
		<thead>
			<tr>
				<th>序号</th>
				<th width="18%">所属单位</th>
				<th>模版名称</th>
				<th>报送周期</th>
				<th width="15%" align="rigth">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.result}" var="item" varStatus="status">
			<tr target="sid_user" rel="1">
				<td>${status.index+1 }</td>
				<td>${item.departmentName }</td>
				<td>${item.templateName }</td>
				<td><zd:show  type="zq" value="${item.reportPeroid}"></zd:show ></td>
				<td>
					<a style="margin-left: 18px" class="button" href="${basePath}admin/data/queryReportInfo?zq=${item.reportPeroid}&templateName=${item.templateName }&deparmentId=${item.departmentId }" target="navTab" rel="toQueryReport"  title="查询报送详情"><span>详情查看及状态设置</span></a>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
<!-- 分页 -->
<%@include file="/common/page.jsp" %>
</div>
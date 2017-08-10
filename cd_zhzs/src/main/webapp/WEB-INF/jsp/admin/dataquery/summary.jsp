<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<%@ taglib prefix="zd" uri="../../../../WEB-INF/zdtag.tld"%>
<form id="pagerForm" method="post" action="${basePath }admin/data/summary">
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
</form>


<form method="post"  name="searchForm"  id="searchForm"
		action="${basePath }admin/data/summary" onsubmit="return navTabSearch(this);">
<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
				<label>查询部门：</label>
				<input  type="text" name="deparmentName" value="${reportLog. deparmentName}"/>
			</li>
			<li> 
				<label>模板名称：</label>
				<input type="text" name="templateName" value="${reportLog. templateName}"/>
			</li>
			</ul>
		</div>
</div>
<div class="panelBar">
		<ul class="toolBar">
			<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
			<li><a class="icon" id="abyexport" href="#"
				onclick="cleanForm('searchForm','navTab')"><span>清空查询</span></a></li>
		</ul>
	</div>
</form>
<div class="pageContent">
	
	<table class="list" width="100%" layoutH="90">
		<thead>
			<tr>
				<th align="center" width="50">序号</th>
				<th align="center" width="120">部门名称</th>
				<th align="center" width="120">模板名称</th>
				<th align="center" width="170">报送周期</th>
				<th align="center" width="170">历史报送次数</th>
				<th align="center" width="150">本期是否报送</th>
				<th align="center" width="170">最后一次报送时间</th>
				<th align="center" width="150">报送条数</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${pageList.result}" varStatus="status">
				<tr>
					<td align="center">${status.index+1 }</td>
					<td align="center">${list.deparmentName }</td>
					<td align="center">${list.templateName }</td>
					<td align="center"><zd:show  type="zq" value="${list.reportZq}"></zd:show ></td>
					<td align="center">${list.reportFrequency }</td>
					<td align="center"<c:if test="${list.periodIsReport == 0}">style="color:red" 
							</c:if>>${list.periodIsReport == 1?'是':'否'}</td>
					<td align="center"><zd:show  type="dateFormat" dateValue="${list.reportDate}" ></zd:show ></td>
					<td align="center">${list.reportDataNum }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<!-- 分页 -->
<%@include file="/common/page.jsp" %>
</div>	
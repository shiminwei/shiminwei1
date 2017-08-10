<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<form id="pagerForm" method="post" action="${basePath }admin/departmentLead/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> 
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" /> 
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="leadId" value="${leadId}">
</form>
<c:choose>
	<c:when test="${actionMethod=='edit' }">
		<h2 class="contentTitle">修改用户</h2>
	</c:when>
</c:choose>
<div class="pageHeader">
	<form method="post" action="${basePath }admin/departmentLead/list"
		onsubmit="return navTabSearch(this);"
		class="pageForm required-validate" id="searchForm" name="searchForm">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label style="font-weight: 900">前置机ID&nbsp;&nbsp;&nbsp;:</label>
					<input type="text" name="leadId" id="searchForm" name="searchForm"
					value="${leadId }" /></li>
				<dd>
					<div class="button">
						<div class="buttonContent">
							<button>查询</button>
						</div>
					</div>
				</dd>
				<dd>
					<div style="margin-left: 17px" class="button">
						<div class="buttonContent">
							<button onclick="cleanForm('searchForm','navTab')">清空</button>
						</div>
					</div>
				</dd>
			</ul>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add"
				href="${basePath }admin/departmentLead/toSaveOrEdit?istype=1"
				target="dialog" rel="addNotice" width="650" height="350"><span>新增部门前置机</span></a></li>
			<li><a title="确定要删除这些前置机吗?" class="delete" postType="BigDecimal"
				href="${basePath }admin/departmentLead/delete?ids=${list.id}"
				target="selectedTodo" rel="ids"><span>批量删除</span></a></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="91">
		<thead>
			<tr>
				<th align="center" width="6%"><input type="checkbox"
					group="ids" class="checkboxCtrl" /></th>
				<th align="center" width="12%">前置机ID</th>
				<th align="center" width="25%">地区部门名称</th>
				<th align="center" width="25%">状态</th>
				<th align="center">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.result }" var="list">
				<tr>
					<td align="center"><input name="ids" type="checkbox"
						value="${list.id}"></td>
					<td align="center">${list.leadId }</td>
					<td align="center">${list.areaName }-${list.departmentName }</td>
					<td align="center"
						<c:if test = "${list.status ==0}">style="color:red" </c:if>>
						${list.status == 1?'可用':'不可用'}</td>
					<td align="center"><a title="修改部门" target="dialog" width="650"
						height="350"
						href="${basePath }admin/departmentLead/toSaveOrEdit?id=${list.id}"><i
							class="fa fa-edit fa-lg"></i></a> <a style="margin-left: 80px;"
						rel="ids" title="确认要删除该配置机吗？" target="ajaxTodo"
						href="${basePath }admin/departmentLead/delete?ids=${list.id}"><i
							class="fa fa-trash fa-lg"></i></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>








	<!-- 分页 -->
	<%@include file="/common/page.jsp"%>
</div>
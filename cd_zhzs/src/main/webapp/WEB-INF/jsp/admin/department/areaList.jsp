<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<form id="pagerForm" method="post" action="${basePath }admin/department/showAreaList">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> 
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
</form>
<form method="post" action="${basePath }admin/department/showAreaList"
	onsubmit="return navTabSearch(this);"
	class="pageForm required-validate" id="searchForm" name="searchForm">
<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>地区名称:</label>
					<input type="text" name="areaName"  value="${areaName }" /></li>
				<li><label>地区编码:</label>
				<input type="text" name="areaCode"  value="${areaCode }" /></li>
			</ul>
		</div>
		<div class="subBar" style="display:none;">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>

</div>
<div class="panelBar" style="border-width: 0px 0px 1px 0px;">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" id="abyexport" href="#"
			onclick="cleanForm('searchForm','navTab')"><span style="">清空查询</span></a></li>
		<li><a class="add"
			href="${basePath }admin/department/toaddArea"
			target="navTab" rel="areaAdd"><span>新增地区</span></a></li>
		
		<li><a title="确实要删除这些用户吗?" target="selectedTodo" 
			postType="BigDecimal" href="${basePath }admin/department/toDeleteArea?ids=${list.areaId}"
			class="delete" rel="ids"><span>批量删除</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="90" >
		<thead>
			<tr>
				<th align="center" ><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th align="center" >地区名称</th>
				<th align="center" >上级地区</th>
				<th align="center" >地区代码</th>
				<th align="center" >操作</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="list" items="${pageList.result}" >
				<tr  target="area_id" rel="${list.areaId }">
					<td align="center"><input name="ids" type="checkbox" value="${list.areaId}"></td>
					<td align="center">${list.areaName }</td>
					<td align="center">
						<c:forEach var="list1" items="${areaList}" >
							<c:if test="${list.parentAreaId == list1.areaId}">${list1.areaName }</c:if>
						</c:forEach>
					</td>
					<td align="center">${list.areaCode }</td>
					<td align="center">
						<a title="修改地区" target="navTab" href="${basePath }admin/department/toeditArea?areaId=${list.areaId}"><i class="fa fa-edit fa-lg"></i></a> 
						<a title="确认要删除该地区吗？" target="ajaxTodo" href="${basePath }admin/department/toDeleteArea?ids=${list.areaId}" rel="areaManager"><i class="fa fa-trash fa-lg"></i></a> 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<!-- 分页 -->
<%@include file="/common/page.jsp"%>	
</div>	

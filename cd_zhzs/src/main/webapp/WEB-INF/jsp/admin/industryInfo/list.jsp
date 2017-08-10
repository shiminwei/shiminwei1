<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<form id="pagerForm" method="post"
	action="${basePath }admin/industryInfo/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input type="hidden"
		name="numPerPage" value="${pageList.numPerPage }" />
</form>

<form onsubmit="return navTabSearch(this);"
	action="${basePath }admin/industryInfo/list" method="post"
	id="industryInfo" name="searchForm">
<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
				  <label>名称：</label><input type="text" name="name"value="${bean.name }" />
				</li>
				<li>
				  <label>编号：</label><input type="text" name="industryId"value="${bean.industryId }" />
				</li>
			</ul>
		</div>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" onclick="cleanForm('industryInfo','navTab')" rel="industryInfoManage"
			targettype="navTab"><span>清空查询条件</span></a></li>
		<li><a class="add" href="${basePath }admin/industryInfo/toSaveOrUpdate?isadd=1"
			target="dialog" rel="addNotice" width="700" height="350"><span>新增行业信息</span></a></li>
		<li><a title="确定要删除这些吗?" target="selectedTodo"
			postType="String" href="${basePath }admin/industryInfo/toDelete" class="delete"
			rel="industryId"><span>批量删除</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="91">
		<thead>
			<tr>
				<th align="center" width="6%"><input type="checkbox"
					group="industryId" class="checkboxCtrl"></th>
				<th align="center" style="font-weight: 900">编号</th>
				<th align="center" style="font-weight: 900">名称</th>
				<th align="center" style="font-weight: 900" width="20%">操作</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach var="list" items="${pageList.result}" varStatus="status">
				<tr>
					<td align="center"><input name="industryId" type="checkbox"
						value="${list.industryId }"></td>
					<td align="center">${list.industryId }</td>
					<td align="center">${list.name }</td>
					<td align="center">
						<a  class="button" href="${basePath }admin/industryInfo/detail?industryId=${list.industryId }" mask="true" 
							target="dialog" rel="dlg_page10" width="700" height="350" fresh="true" title="查看详情" width="800" height="480" close="reloadNavTab" 
							param="{rel:'noticeQuery'}"><span>查看详情</span></a>					
						<a  title="修改行业信息" target="dialog" width="700" height="350"
							href="${basePath }admin/industryInfo/toSaveOrUpdate?isadd=0&industryId=${list.industryId }"
							class="btnEdit">编辑</a>
						<a title="确定要删除该行业信息吗?" target="ajaxTodo"
							href="${basePath }admin/industryInfo/toDelete?industryId=${list.industryId }"
							class="btnDel" rel="userManage">删除</a></td> 
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/common/page.jsp"%>
</div>
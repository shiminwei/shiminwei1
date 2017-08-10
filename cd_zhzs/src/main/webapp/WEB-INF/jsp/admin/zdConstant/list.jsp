<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<form id="pagerForm" method="post"
	action="${basePath }admin/zdConstant/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input type="hidden"
		name="numPerPage" value="${pageList.numPerPage }" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="${basePath }admin/zdConstant/list" method="post"
		id="searchForm" name="searchForm">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>类型名称：</label> <input type="text" name="name"
					value="${bean.name }" /></li>
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
			<li><a class="add" href="${basePath }admin/zdConstant/toSaveOrUpdate?istype=1"
				target="dialog" rel="addNotice" width="800" height="350"><span>新增码表</span></a></li>
			<li><a title="确实要删除这些吗?" target="selectedTodo"
				postType="String" href="${basePath }admin/zdConstant/toDelete" class="delete"
				rel="type"><span>批量删除</span></a></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="91">
		<thead>
			<tr>
				<th align="center" width="6%"><input type="checkbox"
					group="type" class="checkboxCtrl"></th>
				<th align="center">序号</th>
				<th align="center">类型CODE</th>
				<th align="center">类型名称</th>
				<th align="center">类型描述</th>
				<th align="center" width="10%">操作</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach var="list" items="${pageList.result}" varStatus="status">
				<tr>
					<td align="center"><input name="type" type="checkbox"
						value="${list.type }"></td>
					<td align="center">${status.index+1 }</td>
					<td align="center">${list.type }</td>
					<td align="center">${list.name }</td>
					<td align="center">${list.constantDesc }</td>
					<td align="center"><a style="margin-left: 19%" title="子码表管理" target="navTab"
						href="${basePath }admin/zdConstant/chileList?type=${list.type }&name=${list.name }" class="btnInfo"
						rel="chileList">子码表管理</a> <a style="margin-left: 25px" title="确定要删除该公告吗?" target="ajaxTodo"
						href="${basePath }admin/zdConstant/toDelete?type=${list.type }" class="btnDel"
						rel="userManage">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/common/page.jsp"%>
</div>

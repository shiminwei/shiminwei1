<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../../common/base.jsp"%>
<form id="pagerForm" method="post"
	action="${basePath }admin/zdConstant/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input type="hidden"
		name="numPerPage" value="${pageList.numPerPage }" />
</form>

<form onsubmit="return navTabSearch(this);"
	action="${basePath }admin/zdConstant/list" method="post"
	id="searchForm" name="searchForm">
<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>类型名称：</label> <input type="text" name="name"
					value="${bean.name }" /></li>
			</ul>
		</div>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" id="abyexport" href="#" onclick="cleanForm('searchForm','navTab')"><span>清空查询</span></a></li>
		<li><a class="add" href="${basePath }admin/zdConstant/toSaveOrUpdate?istype=1"
			target="dialog" rel="addNotice" width="800" height="350"><span>新增码表</span></a></li>
		<li><a title="确实要删除这些吗?" target="selectedTodo"
			postType="String" href="${basePath }admin/zdConstant/toDelete" class="delete"
			rel="type"><span>批量删除</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="91">
		<thead>
			<tr>
				<th align="center" width="60"><input type="checkbox"
					group="type" class="checkboxCtrl"></th>
				<th align="center">序号</th>
				<th align="center">类型CODE</th>
				<th align="center">类型名称</th>
				<th align="center">类型描述</th>
				<th align="center" width="12%">操作</th>
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
					<td align="center"><a style="margin-left:18%" title="子码表管理" target="navTab"
						href="${basePath }admin/zdConstant/chileList?type=${list.type }&name=${list.name }" class="btnInfo"
						rel="chileList">子码表管理</a> <a style="margin-left: 13px" title="确定要删除该公告吗?" target="ajaxTodo"
						href="${basePath }admin/zdConstant/toDelete?type=${list.type }" class="btnDel"
						rel="userManage">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div class="panelBar">
	<div class="pages">
		<span>显示</span> <select class="combox" name="numPerPage"
			onchange="navTabPageBreak({numPerPage:this.value,pageNum:1})">
			<option value="20">20</option>
			<option value="50">50</option>
			<option value="100">100</option>
			<option value="200">200</option>
		</select> <strong><script>
			$("select[name='numPerPage']").val('${pageList.numPerPage}');
		</script></strong> <span>条，共${pageList.totalCount}条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<form id="pagerForm" method="post"
	action="${basePath }admin/role/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	 <input type="hidden" name="roleName" value="${bean.roleName}" />
</form>


<form method="post" id="queryForm"
	action="${basePath }admin/role/list"
	onsubmit="return navTabSearch(this);">
<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>角色名称：</label> <input type="text" name="roleName"
					value="${bean.roleName }" /></li>
			</ul>
		</div>
</div>
<div class="panelBar" style="border-width: 0px 0px 1px 0px;">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" id="abyexport" href="#" onclick="cleanForm('queryForm','navTab')"><span>清空查询</span></a></li>
		<li><a class="add"
			href="${basePath }admin/role/toSaveOrUpdate?type=1"
			target="navTab" rel="roleAdd"><span>新增角色</span></a></li>

	</ul>
</div>
</form>
<div class="pageContent" style="width: auto; overflow: x:scroll;heigth:733px">
	<table class="list" width="100%" layoutH="90">
		<thead>
			<tr>
				
				<th align="center">序号</th>
				<th align="center">角色名称</th>
				<th align="center">状态</th>
				<th align="center">描述</th>
				<th align="center" width="12%">操作</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="list" items="${pageList.result}" varStatus="status">
				<tr>
				

					<td align="center">${ status.index + 1}</td>
					<td align="center">${list.roleName }</td>
					<td align="center">
					<c:if test="${list.status==1}"><span style=" color: green">正常使用</span></c:if>
					<c:if test="${list.status==0}"><span style=" color:red">已经禁用</span></c:if>
					</td>
					<td align="center">${list.roleDesc }</td>
					<td align="center"><a style="margin-left:18%" title="编辑" target="navTab"
						href="${basePath }admin/role/toSaveOrUpdate?roleId=${list.roleId}&type=2"
						class="btnEdit">编辑</a> <a style="margin-left:13%" title="删除" target="ajaxTodo"
						href="${basePath }admin/role/toDelete?roleId=${list.roleId }"
						class="btnDel" rel="webMnuList">删除</a> 
						</td>
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

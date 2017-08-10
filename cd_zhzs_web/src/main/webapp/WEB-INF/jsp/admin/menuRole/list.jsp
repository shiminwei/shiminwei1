<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<form id="pagerForm" method="post" action="${basePath }admin/menuRole/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageContent" style="width: auto; overflow: x:scroll;">
	<table class="list" width="100%" layoutH="28">
		<thead>
			<tr>

				<th align="center" width="6%">序号</th>
				<th align="center">角色名称</th>
				<th align="center">状态</th>
				<th align="center" width="6%">操作</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="list" items="${pageList.result}" varStatus="status">
				<tr>
					<td align="center">${ status.index + 1}</td>
					<td align="center">${list.roleName }</td>
					<td align="center"><c:if test="${list.status==1}">
							<span style="color: green">正常使用</span>
							</span>
						</c:if> <c:if test="${list.status==0}">
							<span style="color: red">已经禁用</span>
							</span>
						</c:if></td>
					<td align="center"><a style="margin-left: 25%" title="编辑" target="navTab"
						href="${basePath }admin/menuRole/show?roleId=${list.roleId}"
						class="btnInfo">编辑</a></td>
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

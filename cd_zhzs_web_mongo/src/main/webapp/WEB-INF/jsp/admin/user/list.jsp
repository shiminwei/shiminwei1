<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<form id="pagerForm" method="post"
	    action="${basePath }/admin//user/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> 
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> 
	<input	type="hidden" name="orderField" value="${param.orderField}"/> 
	<input	type="hidden" name="orderDirection" value="${param.orderDirection}"/>
	<%-- <input type="hidden" name="userCode" value="${param.userCode}" /> --%>

</form>


<form method="post" id="pagerForm"
	action="${basePath }/admin/user/list"
	onsubmit="return navTabSearch(this);">
<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>登录名：</label> <input type="text" name="userCode"
					value="${sysReportUser.userCode }" /></li>
			</ul>
		</div>
</div>
<div class="panelBar" style="border-width: 0px 0px 1px 0px;">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" id="abyexport" href="#" onclick="cleanForm('pagerForm','navTab')"><span>清空查询</span></a></li>
		<li><a class="add"
			href="${basePath }admin/user/toadd"
			target="navTab" rel="userAdd"><span>新增用户</span></a></li>
		<li><a title="确实要删除这些用户吗吗?" target="selectedTodo"
			postType="string" href="${basePath }admin/user/todeletes?ids=${list.userId}"
			class="delete"><span>批量删除</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent" style="width: auto; overflow: x:scroll;height: 731px">
	<table class="list" width="100%" layoutH="90">
		<thead>
			<tr>
				<th align="center"><input type="checkbox" group="ids"
					class="checkboxCtrl" group="ids"></th>
				<th align="center">序号</th>
				<th align="center">登录名</th>
				<th align="center">用户昵称</th>
				<th align="center">用户部门</th>
					<th align="center" width="8%">展现权限</th>
						<th align="center" width="8%">上报权限</th>
							<th align="center" width="9%">用户状态</th>
				<th align="center" width="12%">操作</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="list" items="${pageList.result}" varStatus="status">
				<tr>
					<td align="center"><input name="ids" type="checkbox"
						value="${list.userId}"></td>

					<td align="center">${ status.index + 1}</td>
					<td align="center">${list.userCode }</td>
					<td align="center">${list.userName }</td>
					<td align="center">${list.departmentName }</td>
					<td align="center">
					<c:if test="${list.hasWeb==1}"><span style=" color: blue">是</span></span></c:if>
					<c:if test="${list.hasWeb==0}"><span style=" color:red">否</span></span></c:if>
					</td>
					<td align="center">
						<c:if test="${list.hasReport==1}"><span style=" color: blue">是</span></span></c:if>
					<c:if test="${list.hasReport==0}"><span style=" color:red">否</span></span></c:if>
					</td>
					<td align="center">
					<c:if test="${list.status==1}"><span style=" color: green">正常使用</span></span></c:if>
					<c:if test="${list.status==0}"><span style=" color:red">已被禁用</span></span></c:if>
					
					</td>
					<td align="center"><a style="margin-left: 13%" title="编辑" target="navTab"
						href="${basePath }admin/user/toedit?userCode=${list.userCode}"
						class="btnEdit">编辑</a> <a style="margin-left: 12%" title="删除" target="ajaxTodo"
						href="${basePath }admin/user/todeletes?ids=${list.userId}"
						class="btnDel" rel="userList">删除</a> 
						
						<a style="margin-left: 12%" title="用户角色配置" target="navTab"
						href="${basePath }admin/user/editUserRole?userId=${list.userId}"
						class="btnLook">用户角色配置</a>
	
	
						</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

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
</div>
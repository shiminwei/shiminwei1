<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<form id="pagerForm" method="post"
	action="${basePath }web/user/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="userCode" value="${param.userCode}" />
</form>


<form method="post"  name="searchForm"  id="searchForm"
	action="${basePath }web/user/list" modelAttribute="sysUser" 
	onsubmit="return navTabSearch(this);">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label >用户姓名：</label> <input type="text" name="userName" value="${queryBean.userName }" /></li>
			</ul>
	</div>
</div>
<div class="panelBar" style="border-width: 0px 0px 1px 0px;">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" onclick="cleanForm('searchForm','navTab')" rel="userManage"
		targettype="navTab"><span>清空查询</span></a></li>
		<li><a class="add"
			href="${basePath }web/user/toaddOrEdit"
			target="dialog" mask="true" width="715" height="330" rel="userManager"><span>新增用户</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent" style="width: auto; overflow: x:scroll;">
	<table class="list" width="100%" layoutH="90">
		<thead>
			<tr>
				<th align="center" width="4%"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th align="center" width="20%">用户姓名</th>
				<th align="center" >Email</th>
				<th align="center" >手机号</th>
				<th align="center" >操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${pageList.result}" >
				<tr>
					<td align="center"><input name="ids" type="checkbox" value="${list._id}"></td>
					<td align="center">${list.userName }</td>
					<td align="center">${list.email }</td>
					<td align="center">${list.phoneNumber }</td>
					<td align="center">
						<a  title="修改用户" target="dialog" mask="true" width="715" height="330" href="${basePath }web/user/toaddOrEdit?_id=${list._id}"><i class="fa fa-edit fa-lg"></i></a> 
						<a style="margin-left: 10%" title="确认要删除用户吗？" target="ajaxTodo" href="${basePath }web/user/delete?_id=${list._id}" rel="userManage"><i class="fa fa-trash fa-lg"></i></a> 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
	
<!-- 分页 -->
<%@include file="/common/page.jsp"%>
<script type="text/javascript">
</script>
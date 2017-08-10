<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<form id="pagerForm" method="post"
	action="${basePath }admin/user/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="userCode" value="${param.userCode}" />
</form>


<form method="post"  name="searchForm"  id="searchForm"
	action="${basePath }admin/user/list" modelAttribute="sysReportUser" 
	onsubmit="return navTabSearch(this);">
<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label >用户名：</label> <input type="text" name="userName" value="${sysReportUser.userName }" /></li>
				<li><label>登录帐号：</label> <input type="text" name="userCode" value="${sysReportUser.userCode }" /></li>
			</ul>
		</div>
</div>
<div class="panelBar" style="border-width: 0px 0px 1px 0px;">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" onclick="cleanForm('searchForm','navTab')" rel="sysMessageManage"
		targettype="navTab"><span>清空查询</span></a></li>
		<li><a class="add"
			href="${basePath }admin/user/toadd"
			target="navTab" rel="userAdd"><span>新增用户</span></a></li>
		
		<li><a title="确实要删除这些用户吗?" target="selectedTodo"
			postType="BigDecimal" href="${basePath }admin/user/todeletes?ids=${list.userId}"
			class="delete" rel="ids"><span>批量删除</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent" style="width: auto; overflow: x:scroll;">
	<table class="list" width="100%" layoutH="90">
		<thead>
			<tr>
				<th align="center" ><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th align="center" >用户名</th>
				<th align="center" >登录帐号</th>
				<th align="center" >部门名称</th>
				<th align="center" >业务联系人</th>
				<th align="center" >业务联系电话</th>
				<th align="center" >技术联系人</th>
				<th align="center" width="7%">技术联系人电话</th>
				<th align="center">email</th>
				<th align="center">单位地址</th>
				<th align="center">上报权限</th>
				<th align="center">展现权限</th>
				<th align="center" >操作</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="list" items="${pageList.result}" >
				<tr  target="user_id" rel="${list.userId }">
					<td align="center"><input name="ids" type="checkbox" value="${list.userId}"></td>
					<td align="center">${list.userName }</td>
					<td align="center">${list.userCode }</td>
					<td align="center">${list.departmentName }</td>
					<td align="center">${list.bussinessConcact }</td>
					<td align="center">${list.bussinessConcactPhone }</td>
					<td align="center">${list.techConcact }</td>
					<td align="center">${list.techConcactPhone }</td>
					<td align="center">${list.email }</td>
					<td align="center">${list.address }</td>
					<td align="center" <c:if test = "${list.hasReport ==0}">style="color:red" </c:if>>${list.hasReport==0?"否":"是" }</td>
					<td align="center" <c:if test = "${list.hasWeb ==0}">style="color:red" </c:if>>${list.hasWeb==0?"否":"是" }</td>
					<td align="center">
						<a title="修改用户" target="navTab" href="${basePath }admin/user/toedit?userCode=${list.userCode}"><i class="fa fa-edit fa-lg"></i></a> 
						<a title="密码重置" href="javascript:;" onclick="resetPwdConfirmMsg('${list.userCode}')"><i class="fa fa-repeat fa-lg"></i></a> 
						<a title="确认要删除用户吗？" target="ajaxTodo" href="${basePath }admin/user/todeletes?ids=${list.userId}" rel="userManage"><i class="fa fa-trash fa-lg"></i></a> 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
	
<!-- 分页 -->
<%@include file="/common/page.jsp"%>
<script type="text/javascript">
function resetPwdConfirmMsg(userCode){
	alertMsg.confirm("是否确定重置用户登录密码？", {
		okCall: function(){
			$.post('${basePath}admin/user/resetPwd', {'userCode':userCode}, DWZ.ajaxDone, "json");
		}
	});
}
</script>
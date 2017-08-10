<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<form id="pagerForm" method="post"
	action="${basePath }admin/zdConstant/chileList?type=${bean.type}&name=${bean.name}">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input type="hidden"
		name="numPerPage" value="${pageList.numPerPage }" />
</form>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${basePath }admin/zdConstant/toSaveOrUpdate?istype=2&type=${bean.type}&name=${bean.name}" mask="true"
				target="dialog" rel="addNotice"  width="800" height="350"><span>新增子码表</span></a></li>
			<li><a title="确实要删除这些公告吗?" target="selectedTodo"
				postType="String" href="${basePath }admin/zdConstant/toDelete" class="delete"
				rel="ids"><span>批量删除</span></a></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="55">
		<thead>
			<tr>
				<th align="center" width="60"><input type="checkbox"
					group="ids" class="checkboxCtrl"></th>
				<th align="center" width="6%">序号</th>
				<th align="center">CODE</th>
				<th align="center">VALUE</th>
				<th align="center">状态</th>
				<th align="center" width="10%">操作</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach var="list" items="${pageList.result}" varStatus="status">
				<tr>
					<td align="center"><input name="ids" type="checkbox"
						value="${list.constantId }"></td>
					<td align="center">${status.index+1 }</td>
					<td align="center">${list.code }</td>
					<td align="center">${list.value }</td>
					<td align="center"><c:if test="${list.state==1}">
							<span style="color: green">正常使用</span>
							</span>
						</c:if> <c:if test="${list.state==0}">
							<span style="color: red">已经禁用</span>
							</span>
						</c:if>
					</td>
					<td align="center"><a style="margin-left: 19%"
						title="修改用户" target="dialog"
						href="${basePath }admin/zdConstant/toSaveOrUpdate?constantId=${list.constantId }&istype=3&type=${list.type }&name=${list.name }" class="btnEdit"  width="800" height="350">编辑</a>
						<a style="margin-left: 25px" title="确定要该条数据吗?" target="ajaxTodo"
						href="${basePath }admin/zdConstant/toDelete?ids=${list.constantId }&forwardType=2" class="btnDel"
						rel="userManage">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/common/page.jsp"%>
</div>

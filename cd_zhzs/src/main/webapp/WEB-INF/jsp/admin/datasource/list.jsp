<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }admin/datasourceConfig/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum}" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
</form>
<form onsubmit="return navTabSearch(this);" action="${basePath }admin/datasourceConfig/list" method="post" modelAttribute="datasourceBean"  name="searchForm"  id="searchForm">
<div class="pageHeader">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>数据源id：</label>
				<input type="text" name="id" value="${datasourceBean.id }"/>
			</li>
			<li>
				<label>数据源名称：</label>
				<input type="text" name="name" value="${datasourceBean.name }"/>
			</li>
		</ul>
	</div>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"> <span>查询数据</span></button></a></li>
		<li><a class="icon" id="abyexport" href="#" onclick="cleanForm('searchForm','navTab')"><span>清空查询</span></a></li>
		<li><a class="add"  href="	${basePath}admin/datasourceConfig/toAdd"   rel="departmentEdit" target="navTab"><span>添加</span></a></li>
		<li><a class="delete" href="${basePath}admin/datasourceConfig/delete?id={sid_datasource}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
		<li><a class="edit" href="${basePath}admin/datasourceConfig/toEdit?id={sid_datasource}" target="navTab"    rel="departmentEdit"><span>修改</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="table" width="102%"	layoutH="113">
		<thead>
			<tr>
				<th width="7.1%">数据源ID</th>
				<th width="10.4%">名称</th>
				<th>类型</th>
				<th>HOST</th>
				<th>PORT</th>
				<th>用户名</th>
				<th width="11.49%">描述</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.result}" var="item">
			<tr target="sid_datasource" rel="${item.id}">
				<td>${item.id}</td>
				<td>${item.name }</td>
				<td>${item.type }</td>
				<td>${item.host }</td>
				<td>${item.port }</td>
				<td>${item.user }</td>
				<td>${item.desc }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
<!-- 分页 -->
<%@include file="/common/page.jsp" %>
</div>

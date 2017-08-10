<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }admin/template/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="excelTemplate.name" value="${excelTemplate.name}" />
</form>
<style>
table thead tr th,table tbody tr td{
	text-align: center;
}
</style>
<form onsubmit="return navTabSearch(this);" action="${basePath }admin/template/list" method="post" modelAttribute="excelTemplate"   name="searchForm"  id="searchForm">
<div class="pageHeader">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>模板名称：</label>
				<input style="margin-left:-10px" type="text" name="name" value="${excelTemplate.name }"/>
			</li>
		</ul>
	</div>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"> <span>查询数据</span></button></a></li>
		<li><a class="icon" id="abyexport" href="#" onclick="cleanForm('searchForm','navTab')"><span>清空查询</span></a></li>
		<li><a class="add" href="${basePath }admin/template/toAdd" target="navTab" rel="addTemplate" mask="true" title="新增上传模板"><span>添加</span></a></li>
		<li><a class="delete" href="${basePath }admin/template/delete?name={template_name}" target="ajaxTodo" title="确定要删除吗?" mask="true"><span>删除</span></a></li>
		<li><a class="edit" href="${basePath }admin/template/toEdit?name={template_name}" target="navTab" mask="true"><span>修改</span></a></li>
	</ul>	
</div>
</form>
<div class="pageContent">
	<table class="table" width="102%" layoutH="113">
		<thead>
			<tr>
				<th>序号</th>
				<th>模版名称</th>
				<!-- <th>报送周期</th>-->
				<th>报送过期天数</th>
				<th>对应表</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.result}" var="item" varStatus="status">
			<tr target="template_name" rel="${item.name }">
				<td>${status.index+1 }</td>
				<td>${item.name }</td>
				<!-- <td>${periodType[item.zq] }</td>-->
				<td>${item.zqgqsj }</td>
				<td>${item.tableName }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
<!-- 分页 -->
<%@include file="/common/page.jsp" %>
</div>

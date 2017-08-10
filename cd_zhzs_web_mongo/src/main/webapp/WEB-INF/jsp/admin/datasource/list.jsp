<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }admin/datasource/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum}" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
</form>

<form onsubmit="return navTabSearch(this);" action="${basePath }admin/datasource/list" method="post" modelAttribute="datasourceBean"  name="searchForm"  id="searchForm">
<div class="pageHeader">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>数据源名称：</label>
				<input style="margin-left:-15px" type="text" name="name" value="${bean.name }"/>
			</li>
		</ul>
	</div>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" id="abyexport" href="#" onclick="cleanForm('searchForm','navTab')"><span>清空查询</span></a></li>
		<li><a class="add"  href="	${basePath}admin/datasource/toSaveOrUpdate?isType=1"   rel="departmentEdit" target="navTab"><span>添加</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%"	layoutH="91">
		<thead>
			<tr>
				<th  align="center" width="8%">数据源ID</th>
				<th  align="center">名称</th>
				<th  align="center">类型</th>
				<th  align="center">描述</th>
				<th  align="center" width="12%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.result}" var="item">
			<tr target="sid_datasource" rel="${item.id}">
				<td  align="center">${item.id}</td>
				<td  align="center">${item.name }</td>
				<td  align="center">${item.type }</td>
				<td  align="center">${item.desc }</td>
				<td  align="center">
								<a style="margin-left:18%" title="编辑" target="navTab"
						href="${basePath }admin/datasource/toSaveOrUpdate?isType=2&id=${item.id}"
						class="btnEdit">编辑</a> <a style="margin-left:18%" title="删除" target="ajaxTodo"
						href="${basePath }admin/datasource/toDelete?id=${item.id}"
						class="btnDel" rel="datasourceList">删除</a> 
				</td>

			</tr>
			</c:forEach>
		</tbody>
	</table>
<!-- 分页 -->
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
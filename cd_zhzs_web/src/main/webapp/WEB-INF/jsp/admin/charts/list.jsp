<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }admin/charts/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" /> 
	<input type="hidden" name="id" value="${bean.id}" />
	<input type="hidden" name="name" value="${bean.name}" />
</form>



<div class="pageHeader">
		<form method="post"
		action="${basePath }admin/charts/list"
		onsubmit="return navTabSearch(this);">
	
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>功能ID：</label>
				<input type="text" name="id" value="${bean.id }" />
			</li>
			<li> 	
				<label>功能名称：</label>
				<input type="text" name="name" value="${bean.name }" />
			</li>
		<dd><div  style="margin-left:-30px" class="button"><div class="buttonContent"><button>查询</button></div></div></dd>
		</ul>
		
	</div>
	</form>
</div>
<div class="pageContent" >
<div class="panelBar" style="border-width: 0px 0px 1px 0px;">
<ul class="toolBar">

	<li><a class="add" href="${basePath }admin/config/toSaveOrUpdate?id=${list.id}&type=1" target="navTab" rel="configAdd"><span>添加</span></a></li>
	<li><a title="确实要删除这些配置吗?" target="selectedTodo" postType="string" href="${basePath }admin/config/toDelete" class="delete"><span>批量删除</span></a></li>

</ul>
</div>
<table class="table" width="100%" layoutH="112">
	<thead>
		<tr>
		<th><input type="checkbox" group="ids" class="checkboxCtrl" group="ids" ></th>
		<th width="center" align="center">序号</th>
			<th width="center" align="center">ID</th>
			<th width="center" align="center">功能名称</th>	
			<th width="center" align="center">布局类型</th>			
			<th width="center" align="center">操作</th>
		</tr>
	</thead>
	<tbody>
	
	<c:forEach var="list" items="${pageList.result}" varStatus="status">
			<tr>
			<td><input name="ids" type="checkbox" value="${list.id}"></td>
			<td>${ status.index + 1}</td>
				<td>${list.id }</td>
				<td>${list.name }</td>
				<td>${list.layoutType}</td>
				<td>
					<a title="修改功能"   href="${basePath }admin/config/toSaveOrUpdate?id=${list.id}&type=2"   target="navTab" rel="configAdd" > <span style="color: red;">修改功能</span></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a title="结果集管理"   href="${basePath }admin/config/toSaveOrUpdate?id=${list.id}&type=3"   target="navTab" rel="configAdd" > <span style="color: blue;">结果集管理</span></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a title="条件管理"   href="${basePath }admin/config/toSaveOrUpdate?id=${list.id}&type=4"   target="navTab" rel="configAdd" > <span  style="color: blue;">条件管理</span></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a title="预览页面"   href="${basePath }pageList/public?functionid=${list.id}"   target="navTab" rel="configAdd" > <span  style="color: green; ">预览</span></a>
				</td>
			</tr>
			</c:forEach>
	</tbody>
</table>
</div>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value,pageNum:1})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<strong><script>  
                $("select[name='numPerPage']").val('${pageList.numPerPage}');  
            </script></strong>  
			<span>条，共${pageList.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${pageList.totalCount}" numPerPage="${pageList.numPerPage}" pageNumShown="10" currentPage="${pageList.pageNum}"></div>
	</div>

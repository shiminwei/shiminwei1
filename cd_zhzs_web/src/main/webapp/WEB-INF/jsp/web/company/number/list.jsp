<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }web/number/list?id=${bean.id }">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage }" />
	<input type="hidden" name="identifyNumber" value="${bean.identifyNumber }" />
</form>

<form onsubmit="return navTabSearch(this);" action="${basePath }web/number/list?id=${bean.id }" method="post" id="numberForm" name="searchForm">
<div class="pageHeader">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>识别号：</label>
				<input type="text" name="identifyNumber" value="${bean.identifyNumber }"/>
			</li>
		</ul>
	</div>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" onclick="cleanForm('numberForm','navTab')" rel="numberManage"
			targettype="navTab"><span>清空查询</span></a></li>
		<li><a class="add" href="${basePath }web/number/toAddOrEdit?id=${bean.id}" target="dialog" width="550" height="350" rel="addAddress"><span>新增</span></a></li>
		<li><a title="确实要删除这些地址吗?" target="selectedTodo"
			postType="String" href="${basePath }web/number/toDelete?"
			class="delete"  rel="numberId" ><span>批量删除</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="91">
		<thead>
			<tr>
				<th align="center" width="6%"><input type="checkbox" group="numberId" class="checkboxCtrl"></th>
				<th align="center" style="font-weight: 900" width="14%">序号</th>
				<th align="center" style="font-weight: 900" width="50%">识别号</th>
				<th align="center" style="font-weight: 900" width="30%">操作</th>
			</tr>
		</thead>		
		<tbody>
			<c:forEach var="list" items="${pageList.result}" varStatus="status">
				<tr>				
				<td align="center"><input name="numberId" type="checkbox" value="${list.numberId}"></td>
				<td align="center">${ status.index + 1}</td>
				<td align="center">${list.identifyNumber }</td>
				<td align="center">				
					<a title="修改" target="dialog" width="550" height="350"  
						href="${basePath }web/number/toAddOrEdit?numberId=${list.numberId}">修改</a>&nbsp;&nbsp;&nbsp;
					<a href="${basePath }web/number/detail?numberId=${list.numberId }" mask="true" target="dialog" rel="dlg_page10" width="550" height="350" 
						title="详情页面"><span>详情页面</span></a>&nbsp;&nbsp;&nbsp;
					<a title="确定要删除吗?" target="ajaxTodo"
						href="${basePath }web/number/toDelete?numberId=${list.numberId}" rel="numberManage">删除</a> 
				</td>
			</tr>
			</c:forEach>		
		</tbody>
	</table>
    <!-- 分页 -->
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
</div>
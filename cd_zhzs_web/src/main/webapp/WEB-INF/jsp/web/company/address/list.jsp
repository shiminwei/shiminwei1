<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }web/address/list?id=${bean.id }">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage }" />
	<input type="hidden" name="address" value="${bean.address }" />
</form>

<form onsubmit="return navTabSearch(this);" action="${basePath }web/address/list?id=${bean.id }" method="post" id="addressForm" name="searchForm">
<div class="pageHeader">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>企业地址：</label>
				<input type="text" name="address" value="${bean.address }"/>
			</li>
		</ul>
	</div>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" onclick="cleanForm('addressForm','navTab')" rel="addressManage"
			targettype="navTab"><span>清空查询</span></a></li>
		<li><a class="add" href="${basePath }web/address/toAddOrEdit?id=${bean.id}" target="dialog" width="750" height="450" rel="addAddress"><span>新增</span></a></li>
		<li><a title="确实要删除这些地址吗?" target="selectedTodo"
			postType="String" href="${basePath }web/address/toDelete?"
			class="delete"  rel="addressId" ><span>批量删除</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="91">
		<thead>
			<tr>
				<th align="center" width="6%"><input type="checkbox" group="addressId" class="checkboxCtrl"></th>
				<th align="center" style="font-weight: 900" width="50%">企业地址</th>
				<th align="center" style="font-weight: 900" width="10%">地址来源</th>
				<th align="center" style="font-weight: 900" width="10%">地址类型</th>
				<th align="center" style="font-weight: 900" width="26%">操作</th>
			</tr>
		</thead>		
		<tbody>
			<c:forEach var="list" items="${pageList.result}" >
				<tr  target="addressId" rel="${list.addressId}">				
				<td align="center"><input name="addressId" type="checkbox" value="${list.addressId}"></td>
				<td align="center">${list.address }</td>
				<td align="center">${addressSource[list.type] }</td>
				<td align="center">${addressType[list.addressType] }</td>
				<td align="center">				
					<a title="修改" target="dialog" width="750" height="450"  
						href="${basePath }web/address/toAddOrEdit?addressId=${list.addressId}">修改</a>&nbsp;&nbsp;&nbsp;
					<a href="${basePath }web/address/detail?addressId=${list.addressId }" mask="true" target="dialog" rel="dlg_page10" width="750" height="450" 
						title="详情页面"><span>详情页面</span></a>&nbsp;&nbsp;&nbsp;
					<a title="确定要删除该地址吗?" target="ajaxTodo"
						href="${basePath }web/address/toDelete?addressId=${list.addressId}" rel="addressManage">删除</a> 
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
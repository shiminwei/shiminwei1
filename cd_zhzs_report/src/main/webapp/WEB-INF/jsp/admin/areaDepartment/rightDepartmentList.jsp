<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<div class="pageHeader" style="border:1px #B8D0D6 solid;display:none;">
	<form id="pagerForm" onsubmit="return divSearch(this, 'jbsxBox-area');" action="${basePath }/admin/department/selectAreaDepartment" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="areaId" value="${departmentAreaId}" />
	</form>
</div>
	<form id="searchForm" onsubmit="return divSearch(this, 'jbsxBox-area');" action="${basePath }/admin/department/selectAreaDepartment" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="areaId" value="${departmentAreaId}" />
	<div class="pageHeader" style="border: 1px #B8D0D6 solid">
				<input type="hidden" name="pageNum" value="1" /> <input
					type="hidden" name="numPerPage" value="${model.numPerPage}" /> <input
					type="hidden" name="orderField" value="${param.orderField}" /> <input
					type="hidden" name="orderDirection" value="${param.orderDirection}" />
				<div class="searchBar">
					<table class="searchContent">
						<tr>
							<td style="font-weight: 900;">
								部门名称&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;<input type="text"
								name="departmentName" style="color: blue"
								value="${departmentName }" />
							</td>
						</tr>
					</table>
				</div>
	</div>
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="search" href="javascript:void(0)"><button	type="submit"><span>查询数据</span></button></a></li>
			<li><a class="icon" id="abyexport" href="#" onclick="cleanForm('searchForm','navTab')"><span>清空查询</span></a></li>
			<li><a href="javascript:;" onclick="batchConfirmMsg()" class="delete"><span>批量删除部门</span></a></li>
			<li><a class="add" href="${basePath }admin/department/toEditDepartment?areaId=${departmentAreaId}" target="dialog" fresh="true" width="790" height="400" mask="true" close="loadAreaDepartment"  param="{areaId:'${departmentAreaId}'}"><span>新增部门</span></a></li>
			<li><a href="${basePath }admin/department/toSubDepartment?selectedDepartmentId={sid_department}"  class="search" mask="true" target="dialog" rel="selectSubDepartment" width="800" height="600" fresh="true" title="查询部门权限配置"><span>查询部门权限配置</span></a></li>
		</ul>
	</div>
	</form>
	<div class="pageContent" style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
		<table class="list" width="99%" layoutH="164"  rel="jbsxBox-area">
			<thead>
				<tr>
					<th align="center" width="5%"><input type="checkbox" group="departmentIds" class="checkboxCtrl"></th>
					<th align="center"  orderField="number" class="asc" style="font-weight: 700; color: red;">部门编号</th>
					<th align="center" style="font-weight: 700">部门名称</th>					
					<th align="center" align="center" style="font-weight: 700">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${pageList.result}">
					<tr target="sid_department" rel="${list.departmentId}">
						<td align="center"><input name="departmentIds" type="checkbox"
							value="${list.departmentId}"></td>
						<td align="center">${list.departmentCode }</td>
						<td align="center" style="color: #6E00FF;">${list.departmentName}</td>
						<td align="center"><a title="修改部门" target="dialog" width="790" mask="true"
							height="400"
							href="${basePath }admin/department/toEditDepartment?selectedDepartmentId=${list.departmentId}"><i
								class="fa fa-edit fa-lg"></i></a> 
								<a style="margin-left: 10%"
							title="确认要删除该部门吗？" target="ajaxTodo" rel="areaDepartmentManager"
							href="${basePath }admin/department/deleteDepartment?departmentIds=${list.departmentId}" callback="loadAreaDepartment({areaId:'${departmentAreaId}'})" ><i
								class="fa fa-trash fa-lg"></i></a></td>		
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<jsp:include page="/common/pageJbsxBox.jsp" flush="true">     
	     	<jsp:param name="jbsxBoxId" value="jbsxBox-area"/> 
		</jsp:include> 
	</div>
<script type="text/javascript">
function batchConfirmMsg(url, data){
	var ids = "";
	var $box = navTab.getCurrentPanel();
	$box.find("input:checked").filter("[name='departmentIds']").each(function(i){
		var val = $(this).val();
		ids += i==0 ? val : ","+val;
	});
	alertMsg.confirm("确实要删除这些部门吗?", {
		okCall: function(){
			$.post('${basePath }admin/department/deleteDepartment',{departmentIds:ids} , loadAreaDepartment({areaId:'${departmentAreaId}'}), "json");
		}
	});
}
</script>
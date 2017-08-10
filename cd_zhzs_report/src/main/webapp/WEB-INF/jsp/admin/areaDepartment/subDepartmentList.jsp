<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post"
	action="${basePath }/admin/department/toSubDepartment">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="selectedDepartmentId" value="${selectedDepartmentId }" />
</form>
<div class="pageHeader">
	<h2 style="text-align: center;">部门名称：${sysDepartmentInfo.departmentName }</h2>
</div>
<div class="pageContent" >
	<div class="panelBar">
		<ul class="toolBar">
			<li><a title="确实要选择这些部门吗?" target="selectedTodo" targetType="dialog"  postType="String" href="${basePath }/admin/department/selectSubDepartment?departmentId=${selectedDepartmentId}&" class="edit" rel="subDepartmentIds" callback="dialogAjax" ><span>批量选择</span></a></li>
			<li><a title="确实要取消这些部门吗?" target="selectedTodo" targetType="dialog"  postType="String" href="${basePath }/admin/department/unSelectSubDepartment?departmentId=${selectedDepartmentId}&" class="delete" rel="subDepartmentIds" callback="dialogAjax" ><span>批量取消</span></a></li>
		</ul>	
	</div>
<div class="pageContent" layoutH="90">
		<input type="hidden" id="departmentId2" value="${sysDepartmentInfo.departmentId}">  
			<table class="table" width="102.51%"  >
				<thead>
					<tr>
						<th width="1%"><input type="checkbox" group="subDepartmentIds" class="checkboxCtrl"></th>
						<th width="25%"><a style="margin-left: 15.5%;height: 21px;line-height: 21px">部门列表</a> </th>
						<th width="5%"><a style="margin-left: 20%;height: 21px;line-height: 21px">操作</a></th>
					</tr>
				</thead>
			<tbody>
				<c:forEach items="${pageList.result }" var="department">
						<tr>
							<td align="left">
							<input
								type="checkbox" name="subDepartmentIds" 
								value="${department.departmentId }"
								<c:forEach items="${sysDepartmentAuthList }" var="departmentAuth">
								<c:if test="${department.departmentId eq departmentAuth.subDepartmentId }">checked="checked"</c:if> 
								</c:forEach> />
							</td>
							<td>${department.departmentName }</td>
							<td>
								<a href="${basePath }/admin/department/selectSubDepartment?subDepartmentIds=${department.departmentId }&departmentId=${selectedDepartmentId}" target="ajaxTodo"  title="确认选择？" callback="dialogAjax" >
									<span style="color: red; margin-left: 27px; cursor: pointer;">选择</span>
								</a>
								<a  href="${basePath }/admin/department/unSelectSubDepartment?subDepartmentIds=${department.departmentId }&departmentId=${selectedDepartmentId}" target="ajaxTodo"  title="确认取消选择？" callback="dialogAjax">
									<span style="color: blue; margin-left: 15px; cursor: pointer;">取消</span>
								</a>
							</td>
						</tr>
					</c:forEach>
			</tbody>
			</table>
</div>
<%@include file="/common/pageDialog.jsp"%>
</div>
<script type="text/javascript">
function dialogAjax(json){
    $.pdialog.reload("${basePath }/admin/department/toSubDepartment?selectedDepartmentId=${selectedDepartmentId}",{"dialogId":'selectSubDepartment'});
}
</script>
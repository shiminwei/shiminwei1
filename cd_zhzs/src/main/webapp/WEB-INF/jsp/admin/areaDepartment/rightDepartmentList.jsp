<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body>
	<div class="pageHeader" style="border: 1px #B8D0D6 solid">
		<form id="pagerForm" 
		onsubmit="return divSearch(this, 'jbsxBox');"
			action="demo/pagination/list1.html" method="post">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage" value="${model.numPerPage}" /> <input
				type="hidden" name="orderField" value="${param.orderField}" /> <input
				type="hidden" name="orderDirection" value="${param.orderDirection}" />
			<form id="pagerForm" onsubmit="return divSearch(this, 'jbsxBox');"
				action="demo/pagination/list1.html" method="post">
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
			</form>
		</form>
	</div>

	<div class="pageContent"
		style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
		<div class="panelBar">
								<ul class="toolBar">

									<li><a class="search" href="javascript:void(0)"><button
												type="submit">
												<span>查询数据</span>
											</button></a></li>
											<li><a title="确实要删除这些部门吗?" target="selectedTodo" 
											postType="BigDecimal" href="${basePath }admin/department/toDeleteTow?ids=${list.departmentId}&departmentName=${departmentName}"
											class="delete" rel="ids"><span>批量删除</span></a></li>
										<li><a class="add"
										href="${basePath }admin/department/toAddTow" target="dialog" fresh="true" width="790" height="400"
										rel="areaAdd"><span>新增部门</span></a></li>
									<li><a postType="BigDecimal" onclick="gotoSelectSubDepartment('1')" href="javascript:"
											class="search" rel="ids"><span>从部门配置</span></a></li>
								
								
								<li><a postType="BigDecimal" onclick="gotoSelectSubDepartment('2')" href="javascript:"
											class="search" rel="ids"><span>配置模版</span></a></li>
								
								
								</ul>
							</div>
		<table class="table" width="101.5%" layoutH="233" rel="jbsxBox">
			<thead>
				<tr>
					<th align="center" width="5%"><input type="checkbox"
						group="ids" class="checkboxCtrl"></th>
					<th align="center" orderField="number" class="asc" width="10%"
						style="font-weight: 700; color: red;">部门编号</th>
					<th width="30%" style="font-weight: 700"><a style="margin-left: 35%;height: 21px;line-height: 21px">部门名称</a></th>					
					<th width="30%" style="font-weight: 700"><a style="margin-left: 35%;height: 21px;line-height: 21px">上级部门</a></th>	
					<th align="center" style="font-weight: 700">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${areaDepartmentPageList.result}">
					<tr>
						<td align="center"><input name="ids" type="checkbox"
							value="${list.departmentId}"></td>
						<td align="center">${list.departmentCode }</td>
						<td><a style="margin-left: 35%">${list.departmentName}</td>
						<td><a style="margin-left: 35%"><c:forEach var="list1"
								items="${departmentList}">
								<c:if test="${list.parentDepartmentId == list1.departmentId}">${list1.departmentName}</c:if>
							</c:forEach></td>
						
						<td align="center"><a title="修改地区" target="dialog" width="790"
							height="400"
							href="${basePath }admin/department/toAddTow?selectedDepartmentId=${list.departmentId}&departmentName=${departmentName}"><i
								class="fa fa-edit fa-lg"></i></a> <a style="margin-left: 10%"
							title="确认要删除该部门吗？" target="ajaxTodo" rel="areaDepartmentManager"
							href="${basePath }admin/department/toDeleteTow?selectedDepartmentId=${list.departmentId}&departmentName=${departmentName}&departmentName2=${list.departmentName}"><i
								class="fa fa-trash fa-lg"></i></a></td>		
					</tr>
				</c:forEach>
				<%
						for (int j = 1; j <= 4; j++) {
						%>
				<tr>
					<td>&nbsp;</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<%
						}
						%>
			</tbody>
		</table>
		<div class="panelBar">
			<div style="display: none" class="pages">
				<span>显示</span> <select class="combox" name="numPerPage"
					onchange="navTabPageBreak({numPerPage:this.value}, 'jbsxBox')">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span>条，共50条</span>
			</div>
		</div>
	</div>
</body>
</html>
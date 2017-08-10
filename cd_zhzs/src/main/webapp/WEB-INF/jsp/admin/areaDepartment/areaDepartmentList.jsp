<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<form id="pagerForm" method="post"
	action="${basePath }admin/department/showAreaDepartmentList">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
</form>

<form method="post"
	action="${basePath }admin/department/showAreaDepartmentList"
	onsubmit="return navTabSearch(this);"
	class="pageForm required-validate" id="searchForm" name="searchForm">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>地区名称:</label> <input type="text" name="areaName"
					value="${areaName }" /></li>
				<li><label>地区编码:</label> <input type="text" name="areaCode"
					value="${areaCode }" /></li>
			</ul>
		</div>
	</div>
	<div class="panelBar" style="border-width: 0px 0px 1px 0px;">
		<ul class="toolBar">
			<li><a class="search" href="javascript:void(0)"><button
						type="submit">
						<span>查询数据</span>
					</button></a></li>
			<li><a class="icon" id="abyexport" href="#"
				onclick="cleanForm('searchForm','navTab')"><span style="">清空查询</span></a></li>
			<li><a class="add"
				href="${basePath }admin/department/toaddAreaTow" target="dialog"
				rel="areaAdd" mask="true" width="715" height="330"><span>新增地区</span></a></li>

			<li><a title="确实要删除这些用户吗?" target="selectedTodo"
				postType="BigDecimal"
				href="${basePath }admin/department/deleteArea?ids=${list.areaId}"
				class="delete" rel="ids"><span>批量删除</span></a></li>
		</ul>
	</div>
<!-- </form> -->
<div class="pageContent">
	<style type="text/css">
ul.rightTools {
	float: right;
	display: block;
}

ul.rightTools li {
	float: left;
	display: block;
	margin-left: 5px
}
</style>

	<div class="pageContent" style="padding: 5px">

		<div class="divider"></div>
		<div class="tabs">

			<div class="tabsHeader">
					<ul>
						<li><a href="javascript:;"><span style="font-weight: 900">地区列表</span></a></li>
						<li style="margin-left: 17.5%"><a href="javascript:;"><span style="font-weight: 900">部门列表</span></a></li>
					</ul>
			</div>
			<div class="tabsContent">

				<div>

					<div layoutH="121"
						style="float: left; display: block; overflow: scroll; width: 360px; border: solid 1px #CCC; line-height: 21px; background: #fff">

						<table class="list" width="100%" layoutH="148">
							<thead>
								<tr>
									<th align="center"><input type="checkbox" group="ids"
										class="checkboxCtrl"></th>
									<th align="center" width="19%" style="color: red">地区编号</th>
									<th align="center" width="24%">地区名称</th>
									<th align="center" width="24%">上级地区</th>
									<th align="center">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="list" items="${pageList.result}">
									<tr target="area_id" rel="${list.areaId }">
										<td align="center"><input name="ids" type="checkbox"
											value="${list.areaId}"></td>
										<td align="center">
											<a title="点击查看下级部门" href="${basePath }admin/department/selectAreaDepartment?departmentAreaId=${list.areaId}"
											target="ajax" rel="jbsxBox" style="color: black;">${list.areaCode }</a>
										</td>
										
										<td align="center"><a title="点击查看下级部门"
											href="${basePath }admin/department/selectAreaDepartment?departmentAreaId=${list.areaId}"
											target="ajax" rel="jbsxBox" style="color: #6E00FF">${list.areaName}</a>
										</td>
										
										<td align="center">
											<c:forEach var="list1" items="${areaList}">
												<c:if test="${list.parentAreaId == list1.areaId}">
												<a title="点击查看下级部门" href="${basePath }admin/department/selectAreaDepartment?departmentAreaId=${list.areaId}"
													target="ajax" rel="jbsxBox" style="color: black;">${list1.areaName}</a>
												</c:if>
											</c:forEach>
										</td>
										
										<td align="center"><a title="修改地区" target="dialog" width="715" height="330"
											rel="areaDepartmentManager" mask="true"
											href="${basePath }admin/department/toEditAreaTow?selectedDepartmentId=${list.areaId}"><i
												class="fa fa-edit fa-lg"></i></a> <a style="margin-left: 10%"
											title="确认要删除该地区吗？" target="ajaxTodo"
											rel="areaDepartmentManager" href="${basePath }admin/department/deleteArea?ids=${list.areaId}"
											><i class="fa fa-trash fa-lg"></i></a></td>
									</tr>
								</c:forEach>
								<%
										for (int j = 1; j <= 5; j++) {
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
						<!--左侧分页 -->
						<div class="panelBar">
							<div style="display: none" class="pages">
								<span>显示</span> <select class="combox" name="numPerPage"
									onchange="navTabPageBreak({numPerPage:this.value,pageNum:1})">
									<option value="15">20</option>
									<option value="50">50</option>
									<option value="100">100</option>
									<option value="200">200</option>
								</select> <strong> <script>
									$("select[name='numPerPage']").val(
											'${pageList.numPerPage}');
								</script>
								</strong> <span>条，共${pageList.totalCount}条</span>
							</div>
							<div class="pagination" targetType="navTab"
								totalCount="${pageList.totalCount}"
								numPerPage="${pageList.numPerPage}" pageNumShown="10"
								currentPage="${pageList.pageNum}"></div>
						</div>
					</div>


             
             
             
             

					<div id="jbsxBox" class="unitBox" style="margin-left: 368px;">
						<div class="pageHeader" style="border: 1px #B8D0D6 solid">
								<div class="searchBar">
									<table class="searchContent">
										<tr>
											<td style="font-weight: 900;">
												部门名称&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;<input type="text"
												name="departmentName" style="color: blue" value="${departmentName }"/>
											</td>
										</tr>
									</table>
								</div>
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
										<th align="center" orderField="${list.departmentCode }" class="asc" width="10%" style="font-weight: 700;">部门编号</th>
										<th width="30%" style="font-weight: 700"><a style="margin-left: 35%;height: 21px;line-height: 21px">部门名称</a></th>
										<th width="30%" style="font-weight: 700"><a style="margin-left: 35%;height: 21px;line-height: 21px">上级部门</a></th>
										<th align="center" style="font-weight: 700">操作</th>
									</tr>
								</thead>
								<tbody>
										<c:forEach var="list" items="${departmentList}">
											<tr>
												<td align="center"><input name="ids" type="checkbox"
													value="${list.departmentId}"></td>
												<td align="center">${list.departmentCode }</td>
												<td>
												<a  style="margin-left: 35%" > 
												${list.departmentName}</a>
												</td>
														<td><a style="margin-left: 35%"><c:forEach var="list1"
															items="${departmentList1}">
															<c:if test="${list.parentDepartmentId == list1.departmentId}">${list1.departmentName}</c:if>
															</c:forEach></td>
												<td align="center"><a title="修改地区" target="dialog" width="790" height="400"
													 href="${basePath }admin/department/toAddTow?selectedDepartmentId=${list.departmentId}&departmentName=${departmentName}"><i class="fa fa-edit fa-lg"></i></a> <a
													style="margin-left: 10%" title="确认要删除该部门吗？" target="ajaxTodo"
													rel="areaDepartmentManager" href="${basePath }admin/department/toDeleteTow?selectedDepartmentId=${list.departmentId}&departmentName=${departmentName}"
													><i class="fa fa-trash fa-lg"></i></a></td>
											</tr>
										</c:forEach>
										<%
										for (int j = 1; j <= 3; j++) {
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
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
	</div>
</div>
</form>
<script type="text/javascript">
function gotoSelectSubDepartment(e){
	var obj=document.getElementsByName('ids');
	var s=''; 
	for(var i=0; i<obj.length; i++){ 
		if(obj[i].checked){
			s+=obj[i].value+','; //如果选中，将value添加到变量s中 
		}
	} 
	var sList = s.split(",");
	if(sList.length>2){
		alert("只能选择一个部门!");
		return;
	}else if(sList.length==1){
		alert("请至少选择一个部门!");
		return;
	}
	var selectedDepartmentId = sList[0];
	if(e==1){
		$.pdialog.open("${basePath }admin/department/toSubDepartmentTow?StselectedDepartmentId="+selectedDepartmentId,"delectSubDepartmentTable","从部门配置",{width:"850",height:"730",mask:true,fresh:true,mask:true,mixable:true,minable:true} );
	}else{
		$.pdialog.open("${basePath }admin/department/toSelectTemplate?StselectedDepartmentId="+selectedDepartmentId,"departmentSelectTemplate","配置模板", {width:'700',height:'500',mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
	}
}
</script>
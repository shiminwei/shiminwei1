<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<div class="pageContent" >
	<form id="pagerForm" method="post"
		action="${basePath }admin/department/showAreaDepartmentList">
		<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
			type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	</form>
	<div class="divider"></div>
	<div class="panelBar" style="border-width: 0px 0px 1px 0px;">
		<ul class="toolBar">
			<li><a class="add"
				href="${basePath }admin/department/toAddArea" target="dialog"
				rel="areaAdd" mask="true" width="715" height="330"><span>新增地区</span></a></li>
			<li><a title="确实要删除这些地区吗?" target="selectedTodo"
				postType="BigDecimal"
				href="${basePath }admin/department/deleteArea"
				class="delete" rel="areaIds"><span>批量删除地区</span></a></li>
		</ul>
	</div>
	<div class="tabs">
		<div class="tabsHeader">
				<ul>
					<li><a href="javascript:;"><span style="font-weight: 900">地区列表</span></a></li>
					<li style="margin-left: 17.5%"><a href="javascript:;"><span style="font-weight: 900">部门列表</span></a></li>
				</ul>
		</div>
		<div class="tabsContent">
			<div>
				<div layoutH="72" style="float: left; display: block; overflow: scroll; width: 360px; border: solid 1px #CCC; line-height: 21px; background: #fff">
					<table class="list" width="100%" layoutH="100">
						<thead>
							<tr>
								<th align="center"><input type="checkbox" group="areaIds"
									class="checkboxCtrl"></th>
								<th align="center" width="19%" style="color: red">地区编号</th>
								<th align="center" width="24%">地区名称</th>
								<th align="center">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="list" items="${pageList.result}" varStatus="status">
								<tr target="area_id" rel="${list.areaId }"<c:if test="${(areaId ne null and areaId eq list.areaId ) or (areaId eq null and status.index eq 0)  }">class='selected'</c:if>>
									<td align="center"><input name="areaIds" type="checkbox"
										value="${list.areaId}"></td>
									<td align="center">
										<a title="点击查看部门列表" href="${basePath }admin/department/selectAreaDepartment?areaId=${list.areaId}"
										target="ajax" rel="jbsxBox-area" style="color: black;">${list.areaCode }</a>
									</td>
									
									<td align="center"><a title="点击查看部门列表"
										href="${basePath }admin/department/selectAreaDepartment?areaId=${list.areaId}"
										target="ajax" rel="jbsxBox-area" style="color: #6E00FF">${list.areaName}</a>
									</td>
									<td align="center"><a title="修改地区" target="dialog" rel="areaDepartmentManager" mask="true"
										href="${basePath }admin/department/toEditArea?areaId=${list.areaId}"><i
											class="fa fa-edit fa-lg"></i></a> <a 
										title="确认要删除该地区吗？" target="ajaxTodo"
										rel="areaDepartmentManager" href="${basePath }admin/department/deleteArea?areaIds=${list.areaId}"
										><i class="fa fa-trash fa-lg"></i></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<!--左侧分页 -->
					<%@include file="/common/page.jsp"%>
					</div>
					<div id="jbsxBox-area" class="unitBox" style="margin-left: 368px;display:block;"></div>
				</div>
			</div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
</div>
<script>
$(function(){
	loadAreaDepartment({areaId:'${areaId ne null ?areaId:pageList.result[0].areaId}'});
})
function loadAreaDepartment(param){
	$('#jbsxBox-area').loadUrl("${basePath }/admin/department/selectAreaDepartment?areaId="+param.areaId, null, function (message) {
        $('#jbsxBox-area').parent().find("[layoutH]").layoutH();
    });
	return true;
}
</script>
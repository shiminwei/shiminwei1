<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<div class="pageContent">
	<div class="divider"></div>
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>所有部门</span></a></li>
					<li style="margin-left: 12.1%"><a href="javascript:;"><span style="font-weight: 900">地区所属模版</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div>
				<div layoutH="48" style="float: left; display: block; overflow: auto; width: 270px; border: solid 1px #CCC; line-height: 21px; background: #fff">
					<ul class="tree treeFolder ">
						<c:forEach items="${areaList }" var="area">
							<li><a style="font-weight: 900" onclick="cleanDepartment()">${area.areaName }</a>
								<ul>
									<c:forEach items="${area.departmentList }" var="department">
										<li>
										<a href="${basePath }admin/department/showDepartmentTemplate?selectedDepartmentId=${department.departmentId }" target="ajax" rel="jbsxBox-template" >${department.departmentName }</a>
										</li>
									</c:forEach>
								</ul></li>
						</c:forEach>
					</ul>
				</div>
				<div id="jbsxBox-template" class="unitBox" style="margin-left: 277px;"></div>
			</div>
		</div>
	</div>
</div>
<script>
$(function(){
	loadDepartmentTemplate({departmentId:'${areaList[0].departmentList[0].departmentId}'});
})
function loadDepartmentTemplate(param){
	$('#jbsxBox-template').loadUrl("${basePath }admin/department/showDepartmentTemplate?selectedDepartmentId="+param.departmentId, null, function (message) {
        $('#jbsxBox-template').parent().find("[layoutH]").layoutH();
    });
	return true;
}
</script>


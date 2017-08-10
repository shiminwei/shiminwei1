<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<div class="pageHeader" style="display:none;">
	<form id="pagerForm" onsubmit="return divSearch(this, 'jbsxBox');" action="${basePath }admin/department/showDepartmentTemplate" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="selectedDepartmentId" value="${selectedDepartmentId}" />
	</form>
</div>
<form id="searchForm" onsubmit="return divSearch(this, 'jbsxBox-template');" action="${basePath }admin/department/showDepartmentTemplate" method="post">
<input type="hidden" name="pageNum" value="1" />
<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
<input type="hidden" name="selectedDepartmentId" value="${selectedDepartmentId}" />
<div class="pageHeader" style="border:1px #B8D0D6 solid">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>模板名称：</label>
				<input type="text" name="templateName" value="${templateName }"/>
			</li>
		</ul>
	</div>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"> <span>查询模版</span></button></a></li>
		<li><a class="icon" id="abyexport" href="#" onclick="cleanForm('searchForm','navTab')"><span>清空查询</span></a></li>
		<li><a class="add" href="${basePath }admin/template/toSelectTemplate?departmentId=${selectedDepartmentId}" target="dialog" rel="departmentSelectTemplate" mask="true" title="${selectedDepartmentName  }--  配置模板"  width="800" height="600" close="loadDepartmentTemplate" param="{departmentId:'${selectedDepartmentId}'}"><span>选择模版</span></a></li>
	</ul>
</div>
</form>

<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<table class="table" width="99%" layoutH="160" rel="jbsxBox" style="background-color: black">
		<thead>
			<tr>
				<th>序号</th>
				<th>模版名称</th>
				<th>报送周期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.result}" var="list" varStatus="status">
				<tr>
				<td>${status.index+1 }</td>
				<td>${list.templateName }</td>
				<td>
				<select name="periodType" onchange="changePeriodType(this)" id="sect">
						<c:forEach var="p_item" items="${periodType}">   
						<option  value="${p_item.key },${list.templateName},${selectedDepartmentId}"<c:if test="${list.reportPeroid eq p_item.key }"> selected="selected"</c:if>>${p_item.value }</option>
						</c:forEach>
				</select>
				</td>
				<td>
					<a title="确认要删除该模版吗？" target="ajaxTodo" href="${basePath }admin/template/unselectTemplate?templateNames=${list.templateName}&departmentId=${selectedDepartmentId}"  rel="departmentTemplateList" callback="loadDepartmentTemplate({departmentId:'${selectedDepartmentId}'})" ><i style="margin-top: 6%" class="fa fa-trash fa-lg"></i></a>
				</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<jsp:include page="/common/pageJbsxBox.jsp" flush="true">     
	     <jsp:param name="jbsxBoxId" value="jbsxBox-template"/> 
	</jsp:include> 
</div>
<script type="text/javascript">
 function changePeriodType(obj){
	 var devalue=obj.options;
	 var value=obj.value;
	 var valueTow = value.split(",");
	 var index = valueTow[0];//value值
	 var templateName = valueTow[1];//模版名称
	 var departmentId = valueTow[2];//所选部门Id
	 var text = devalue[index-1].text;
	 var message = "确定要将报送周期修改为"+text+"?";
	 alertMsg.confirm(message, {
			okCall: function(){
				ajaxTodo("${basePath }admin/template/editReportPeroid?templateName="+templateName+"&zq="+index+"&departmentId="+departmentId);
			}
		});
 }
</script>
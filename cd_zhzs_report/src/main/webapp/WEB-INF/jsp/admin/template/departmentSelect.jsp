<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %>
<form id="pagerForm" method="post" action="${basePath }admin/template/toSelectTemplate">
	<input type="hidden" name="departmentId" value="${departmentId }" />
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
</form>

<div class="pageHeader">
	<form onsubmit="return dialogSearch(this);" action="${basePath }admin/template/toSelectTemplate" method="post" modelAttribute="excelTemplate"   name="searchSelectTemplateForm"  id="searchSelectTemplateForm">
	<input type="hidden" name="departmentId" id="departmentId"  value="${departmentId }" />
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>模板名称：</label>
				<input type="text" name="name" value="${excelTemplate.name }"/>
			</li>
		<dd><div  style="margin-left:20px" class="button"><div class="buttonContent"><button>查询</button></div></div></dd>
				<dd><div style="margin-left: 17px" class="button"><div class="buttonContent" onclick="cleanForm('searchForm','navTab')"><button>清空</button></div></div></dd>
		</ul>
	</div>
	</form>
</div>
<div class="pageContent" overflow: x:scroll;>
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="edit" onclick="selectTemplate('1');" rel="templateNames"  href="javascript:;" callback="dialogAjaxDoneFather" title="确定要选择吗?"><span>多选</span></a></li>
		<li><a class="delete" onclick="selectTemplate('2');" rel="templateNames"  href="javascript:;" callback="dialogAjaxDoneFather" title="确定要选择吗?"><span>清空模版</span></a></li>
		</ul>	
	</div>
	<table class="table" width="102%" layoutH="115">
		<thead>
			<tr>
				<th width="7%"><input type="checkbox" group="templateNames" class="checkboxCtrl"></th>
				<th width="39%">模版名称</th>
				<th width="13%" align="center">报送周期</th>
				<th align="center">过期天数</th>
				<th width="27%">对应表</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.result}" var="item">
				<c:set var="iscontain" value="false" /> 
				<c:set var="reportPeroid" value="1" /> 
				<c:forEach var="departmentTemplate" items="${departmentTemplates}">   
				<c:if test="${departmentTemplate.templateName eq item.name}">     
				<c:set var="iscontain" value="true" />  
				<c:set var="reportPeroid" value="${departmentTemplate.reportPeroid}" />  
				</c:if> 
				</c:forEach>
			<tr target="template_name" rel="${item.name }" <c:if test="${iscontain }">style='background:#00FFFF;'</c:if>>
				<td><input name="templateNames" id="templateNames"  value="${item.name }" type="checkbox" <c:if test="${iscontain }">checked="checked"</c:if>></td>
				<td>${item.name }</td>
				<td>
				<select name="periodType" disabled="disabled">
						<c:forEach var="p_item" items="${periodType}">   
						<option value="${p_item.key }"<c:if test="${reportPeroid eq p_item.key }"> selected="selected"</c:if>>${p_item.value }</option>
						</c:forEach>
				</select>
				</td>
				<td>${item.zqgqsj}</td>
				<td>${item.tableName }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<!-- 分页 -->
<%@include file="/common/pageDialog.jsp"%>
<script type="text/javascript">
function selectTemplate(type){
	var departmentId=$("#departmentId").val();
	var templateNamesArray=new Array();
	var templatePeriodArray=new Array();
	
	var templateNames=document.getElementsByName("templateNames");
	var objarray=templateNames.length;
	var periodTypes=document.getElementsByName("periodType");
	 debugger;
	for (i=0;i<objarray;i++){
	  if(templateNames[i].checked == true){
		  templateNamesArray.push(templateNames[i].value);
		  templatePeriodArray.push(periodTypes[i].value);
	  }
	}
	if(templateNames.length>0 && templatePeriodArray.length>0){
		$.ajax({
			traditional: true,
			type:  'POST',
			url:"${basePath }admin/template/selectTemplate",
			data:{"departmentId":departmentId,"templateNames":templateNamesArray,"reportPeroid":templatePeriodArray,"type":type},
			dataType:"json",
			cache: false,
			success: dialogAjaxDoneFather,
			error: DWZ.ajaxError
		});
	}
}
function dialogAjaxDoneFather(json) {
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok) {
		if (json.navTabId) {
			var dialog = $("body").data(json.navTabId);
			$.pdialog.reload(dialog.data("url"), { data: {}, dialogId: json.navTabId, callback: null })
		}
		if ("closeCurrent" == json.callbackType) {
			$.pdialog.closeCurrent();
		}
	}
}
</script>
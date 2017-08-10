<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<form id="pagerForm" method="post"
	action="${basePath }/admin/department/toSubDepartment">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="de" value="1" />
</form>
<body>
<div class="pageHeader">
<form method="post" action="${basePath }/admin/department/toSubDepartment" 
			onsubmit="return navTabSearch(this);">
	<div class="searchBar">
			<li style="list-style-type:none;">
				<label>部门名称：</label>
				<dd>
					<select class="combox" name="parentDepartmentId">
					<c:forEach items="${pageList.result }" var="department">
					<option value="${department.departmentId }"<c:if test="${sysDepartmentInfo.departmentId == department.departmentId}">selected="selected"</c:if>>${department.departmentName }</option>
					</c:forEach>
					</select>
				</dd>
			</li>
	</div>
</form>
</div>

<div class="pageContent" >
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="edit" onclick="selectTemplate('1');" href="javascript:;" callback="dialogAjaxDoneFather" title="确定要选择吗?"><span>多选</span></a></li>
			<li style="margin-left: 650px"><a class="delete" target="selectedTodo" targetType='dialog' rel="templateNames" onclick="unSelectTemplate('2');" href="javascript:;"  callback="dialogAjaxDoneFather" title="确定要清空选择吗?"><span>清空所有下级部门</span></a></li>
		</ul>	
	</div>
<div class="pageContent" layoutH="90">
		<input type="hidden" id="departmentId2" value="${sysDepartmentInfo.departmentId}">  
			<table class="table" width="102.51%"  >
				<thead>
					<tr>
						<th width="1%"><li style="list-style: none"></li> </th>
						<th width="25%"><a style="margin-left: 15.5%;height: 21px;line-height: 21px">部门列表</a> </th>
						<th width="5%"><a style="margin-left: 20%;height: 21px;line-height: 21px">操作</a></th>
					</tr>
				</thead>
			<tbody>
				<c:forEach items="${pageList.result }" var="department">
						<tr>
							<td align="left"><a style="margin-left: 10px"></a> <input
								type="checkbox" name="c1" id="c1"
								value="${department.departmentId }"
								<c:forEach items="${sysDepartmentAuthList }" var="departmentAuth">
					<c:if test="${department.departmentId eq departmentAuth.subDepartmentId }">checked="checked"</c:if> 
					</c:forEach> />
								<a style="margin-left: 45px"></a></td>
							<td><a style="margin-left: 95px"></a>${department.departmentName }</td>
							<td><a
								onclick="selectTemplate('${sysDepartmentInfo.departmentId},${department.departmentId},3')"
								rel="departmentList"> <span
									style="color: red; margin-left: 27px; cursor: pointer;">单选</span></a>

								<a rel="departmentList"
								onclick="unSelectTemplate('${sysDepartmentInfo.departmentId},${department.departmentId},4')">
									<span style="color: blue; margin-left: 15px; cursor: pointer;">取消</span>
							</a></td>
						</tr>
					</c:forEach>
			</tbody>
			</table>
</div>
<%@include file="/common/pageDialog.jsp"%>
</div>
<script type="text/javascript">
function selectTemplate(parameter){
//	alert(parameter);
	var parameterSet = parameter.split(",");
// 	alert(parameterSet.length);
	var subDepartmentIdsArray=new Array();  
	var departmentId = "";
	var departmentIds = "";
	var type = "";
	if(parameterSet.length == 1){
		departmentId=document.getElementById("departmentId2").value;
    	departmentIds = document.getElementsByName("c1");
    type = "1";
    for(i=0;i<departmentIds.length;i++){
    	if(departmentIds[i].checked == true){
    		subDepartmentIdsArray.push(departmentIds[i].value);
        	} 
    	}
	}else{
		departmentId = parameterSet[0];
		subDepartmentIdsArray.push(parameterSet[1]);
		type = parameterSet[2];
	}
	if(departmentId != null){
		$.ajax({
			traditional: true,
			type:  'POST',
			url:"${basePath }/admin/department/subDepartment",
			data:{"subDepartmentIds":subDepartmentIdsArray,"departmentId":departmentId,"type":type},
			dataType:"json",
			cache: false,
			success: dialogAjaxDoneFather,
			error: DWZ.ajaxError
		});
	}
}

function unSelectTemplate(parameter){
// 	alert(parameter.length);
	var subDepartmentIdsArray=new Array();  
	var parameterSet = parameter.split(",");
	var departmentId2 = "";
	var departmentIds = "";
	var type = "";
	if(parameterSet.length == 1){
		departmentId2=document.getElementById("departmentId2").value;
		type = "2";
	}else{
		departmentId2 = parameterSet[0];
		subDepartmentIdsArray.push(parameterSet[1]);
		type = parameterSet[2];
	}
	if(departmentId2 != null){
		$.ajax({
			traditional: true,
			type:  'POST',
			url:"${basePath }/admin/department/unSubDepartment",
			data:{"subDepartmentIds":subDepartmentIdsArray,"departmentId":departmentId2,"type":type},
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
function validateCallback(form, callback) {  
    var $form = $(form);  
        //验证表单如果有非法字段 返回  
    if (!$form.valid()) {  
        return false;  
    }  
        //Ajax向后台提交数据  
    $.ajax({  
        type: form.method || 'POST',  
        url:$form.attr("action"),  
        //获取表单的内容数据  
                data:$form.serializeArray(),  
        dataType:"json",  
        cache: false,  
        //执行回调函数  
        success: callback || DWZ.ajaxDone,  
        error: DWZ.ajaxError  
    });  
        //保证不会通过普通表单提交  
        return false;  
}  

</script>

</body>
</html>
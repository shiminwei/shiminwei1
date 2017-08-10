<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<h2 class="contentTitle">修改部门</h2>


<div class="pageContent">
	
	<form method="post" action="${basePath }/admin/department/edit" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
		<input type="hidden" name="departmentId" value="${sysDepartmentInfo.departmentId}">  
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt style="font-weight: 900">部门名称：</dt>
				<dd>
					
						<input type="text" name="departmentName" value="${sysDepartmentInfo.departmentName}"/>

				</dd>
			</dl>
			<dl>
				<dt style="font-weight: 900">所属地区：</dt>
				<dd>
					<select class="combox" name="departmentAreaId" >
					<c:forEach items="${areaList }" var="area">
					<option value="${area.areaId }" <c:if test="${sysDepartmentInfo.departmentAreaId == area.areaId}">selected="selected"</c:if>>${area.areaName }</option>
					</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt style="font-weight: 900">上级部门：</dt>
				<dd>
					<select class="combox" name="parentDepartment" onchange="changeParentDepartment(this)">
					<option value="">--请选择--</option>
					<c:forEach items="${departmentList }" var="department">
					<option value="${department.departmentId}-${department.departmentCode}"<c:if test="${sysDepartmentInfo.parentDepartmentId == department.departmentId}">selected="selected"</c:if>>${department.departmentName }</option>
					</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt style="font-weight: 900">部门编码：</dt>
				<dd>
					
					<input type="text" id="code" name="departmentCode" value="${sysDepartmentInfo.departmentCode }" readonly="true"/>
			
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

<script type="text/javascript">
 var departmentCode='${sysDepartmentInfo.departmentCode }';
 
function changeParentDepartment(obj){
	var devalue=obj.value;
	if(devalue == ""){
		document.getElementById("code").value=departmentCode;
	}else{
		var devalueArray=devalue.split('-');
		var codevalue = document.getElementById("code").value = devalueArray[1];
		document.getElementById("code").readOnly="true";
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
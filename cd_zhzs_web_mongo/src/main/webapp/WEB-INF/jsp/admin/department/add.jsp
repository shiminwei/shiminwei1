<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<h2 class="contentTitle">新增部门</h2>


<div class="pageContent">
	
	<form method="post" action="${basePath }admin/department/add" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt style="font-weight: 900">部门名称：</dt>
				<dd>
					<input type="text" id="departmentName" name="departmentName" onblur="checkDepartmentName()"/>
					<strong><span id="checkdepartmentName" style="font-size:10px;line-height: 20px;color: red"></span></strong>
				</dd>
			</dl>
			<dl>
				<dt style="font-weight: 900">所属地区：</dt>
				<dd>
					<select class="combox" name="departmentAreaId" >
					<c:forEach items="${areaList }" var="area">
					<option value="${area.areaId }">${area.areaName }</option>
					</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt style="font-weight: 900">上级部门：</dt>
				<dd>
					<select class="combox" name="parentDepartment" id="dd" onchange="changeParentDepartment(this)">
					<option id="parentDepartment" value="">--请选择--</option>
					<c:forEach items="${departmentList }" var="department">
						<option value="${department.departmentId}-${department.departmentCode }"  areaCode="${department.departmentCode }">${department.departmentName }</option>
					</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt style="font-weight: 900">部门编码：</dt>
				<dd>
					
					<input type="text" id="code" name="departmentCode" value="" onblur="checkDepartmentCode();"/>
					<strong><span id="checkCode" style="font-size:10px;line-height: 20px;color: red"></span></strong>
				</dd>
			</dl>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="button" onclick="toSave()">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>

</div>


<script type="text/javascript">
var isSubmit;
function changeParentDepartment(obj){
	var devalue=obj.value;
	if(devalue == ""){
		var codevalue = document.getElementById("code").value ="";
		document.getElementById("code").removeAttribute("readOnly");
	}else{
		var devalueArray=devalue.split('-');
		var codevalue = document.getElementById("code").value = devalueArray[1];
		document.getElementById("code").readOnly="true";
	}	
}
function checkDepartmentCode(){
		var departmentCode = document.getElementById("code").value.trim();
		var parentDepartment = document.getElementById("dd").value;
		$.ajax({
		    type: "POST",
		    url: "department/checkDepartmentCode",
		    data: "departmentCode="+departmentCode+","+parentDepartment,
		    dataType: "text",
		    success: function(result){
		    	if (result=="true") {
		    		document.getElementById("checkCode").innerHTML = "&nbsp;&nbsp;&nbsp部门编码已存在,请重新输入";
		    		document.getElementById("code").value ="";
		    		isSubmit=true;
				}
		    	if (result=="false"){
					document.getElementById("checkCode").innerHTML = "&nbsp;&nbsp;&nbsp可以使用";
					isSubmit=false;
				}
		    	if(result=="else"){
					document.getElementById("checkCode").innerHTML = "&nbsp;&nbsp;&nbsp部门编码不能为空";
					isSubmit=true;
				}
		    	
		}
		});
}
function checkDepartmentName(){
	var departmentName = document.getElementById("departmentName").value;
	if(departmentName!=""){
		document.getElementById("checkdepartmentName").innerHTML = "";
	}
}

function toSave(){
	var departmentName = document.getElementById("departmentName").value;
	var departmentCode = document.getElementById("code").value;
	if(departmentName==""){
		document.getElementById("checkdepartmentName").innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp部门名称不能为空!";
		isSubmit=true;
	}
	if(departmentName!=""){
		document.getElementById("checkdepartmentName").innerHTML = "";
		isSubmit=false;
	}
	if(departmentCode==""){
		isSubmit=true;
	}
	if(isSubmit){
		 alert("请输入正确信息");
		 return false;
	}else{
		 if(confirm('确定要保存部门？')){
			 document.getElementById("button").type="submit";
		 }else{
			 return;
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
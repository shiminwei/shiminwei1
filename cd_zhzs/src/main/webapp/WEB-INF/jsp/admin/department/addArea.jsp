<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<h2 class="contentTitle">新增地区</h2>


<div class="pageContent">
	
	<form method="post" action="${basePath }/admin/department/addArea" 
	class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt style="font-weight: 900">地区名称：</dt>
				<dd>
					
						<input type="text" name="areaName" id="areaName" onblur="checkAreaName();" value="${areaInfo.areaName }"/>
						<strong><span id="checkareaName" style="font-size:10px;line-height: 20px;color: red"></span></strong>

				</dd>
			</dl>
			<dl>
				<dt style="font-weight: 900">地区编码：</dt>
				<dd>
					
						<input type="text" name="areaCode" id="areaCode" onblur="checkAreaCode();" value="${areaInfo.areaCode }"/>
						<strong><span id="checkareaCode" style="font-size:10px;line-height: 20px;color: red"></span></strong>

				</dd>
			</dl>
			<dl>
				<dt style="font-weight: 900">上级地区：</dt>
				<dd>
					<select class="combox" name="parentAreaId" >
					<option value="">无上级地区</option>
					<c:forEach items="${areaList }" var="area">
					<option value="${area.areaId }">${area.areaName }</option>
					</c:forEach>
					</select>
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
	function validateCallback(form, callback) {
		var $form = $(form);
		//验证表单如果有非法字段 返回  
		if (!$form.valid()) {
			return false;
		}
		//Ajax向后台提交数据  
		$.ajax({
			type : form.method || 'POST',
			url : $form.attr("action"),
			//获取表单的内容数据  
			data : $form.serializeArray(),
			dataType : "json",
			cache : false,
			//执行回调函数  
			success : callback || DWZ.ajaxDone,
			error : DWZ.ajaxError
		});
		//保证不会通过普通表单提交  
		return false;
	}

	function customvalidXxx(element) {
		if ($(element).val() == "xxx")
			return false;
		return true;
	}
	function checkAreaName(){
		var areaName = document.getElementById("areaName").value;
		if(areaName !=""){
			document.getElementById("checkareaName").innerHTML="";
			return false;
		}
		if(areaName ==""){
			document.getElementById("checkareaName").innerHTML="&nbsp;&nbsp;&nbsp地区名称不能为空";
			return true;
		}
	}
	isSubmit = true;
	isSubmit1= true;
	function checkAreaCode(){
		var areaCode = document.getElementById("areaCode").value.trim();
		$.ajax({
			type: "POST",
		    url: "department/checkAreaCode",
		    data: "areaCode="+areaCode,
		    dataType: "text",
		    success: function(result){
		    	if (result=="true") {
		    		document.getElementById("checkareaCode").innerHTML = "&nbsp;&nbsp;&nbsp地区编码已存在,请重新输入";
		    		document.getElementById("areaCode").value ="";
		    		isSubmit=true;
				}
		    	if (result=="false"){
					document.getElementById("checkareaCode").innerHTML = "&nbsp;&nbsp;&nbsp可以使用";
					isSubmit=false;
				}
		    	if(result=="else"){
					document.getElementById("checkareaCode").innerHTML = "&nbsp;&nbsp;&nbsp地区编码不能为空";
					isSubmit=true;
				}
			
		    }
			
		});
		
		
	}
	function toSave(){
		isSubmit1 = checkAreaName();
		var areaCode = document.getElementById("areaCode").value;
		if(areaCode==""){
			document.getElementById("checkareaCode").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp地区代码不能为空!";
			isSubmit = true;
		}
		if(areaCode !=""){
			document.getElementById("checkareaName").innerHTML="";
			isSubmit=false;
		}
		if(isSubmit||isSubmit1){
			 alert("请输入正确信息");
			 checkAreaName();
			 return false;
		}else{
			 if(confirm('确定要保存地区？')){
				 document.getElementById("button").type="submit";
			 }else{
				 return;
			 }
		}
	}
</script>
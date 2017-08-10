<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<div class="pageContent">
	
	<form method="post" action="${basePath }/admin/department/editAreaTow" 
	class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone)">
		<input type="hidden" name="areaId" id="areaId" value="${areaInfo.areaId}">  
		<div class="pageFormContent nowrap" layoutH="58">
			<dl>
				<dt style="font-weight: 900">地区名称：</dt>
				<dd>
					
						<input type="text" name="areaName" value="${areaInfo.areaName}"/>

				</dd>
			</dl>
			<dl>
				<dt style="font-weight: 900">上级地区：</dt>
				<dd>
					<select class="combox" name="parentAreaId" >
					<option value="">无上级地区</option>
					<c:forEach items="${areaList }" var="area">
					<option value="${area.areaId }" <c:if test="${areaInfo.parentAreaId == area.areaId}">selected="selected"</c:if>>${area.areaName }</option>
					</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt style="font-weight: 900">地区编码：</dt>
				<dd>
					<input type="text" name="areaCode" id="areaCode" value="${areaInfo.areaCode}" onblur="checkAreaCode();"/>
					<strong><span id="checkareaCode" style="font-size:10px;line-height: 20px;color: red"></span></strong>
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
	function checkAreaCode(){
		var areaId = document.getElementById("areaId").value.trim();
		var areaCode = document.getElementById("areaCode").value.trim();
		$.ajax({
			type: "POST",
		    url: "department/checkAreaCode2",
		    data: "areaCode="+areaCode+"&areaId="+areaId,
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
</script>

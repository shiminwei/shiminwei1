<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<h2 class="contentTitle">修改密码</h2>


<div class="pageContent">
	
	<form method="post" action="${basePath }web/user/editPwd" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt style="font-weight: 900">旧密码：</dt>
				<dd>
					<input type="text" name="oldPwd" id="pwd" onfocus="this.type='password'" onblur="checkUserPwd()"/>
					<strong><span id="checkPwd" style="font-size:10px;line-height: 20px;color: red"></span></strong>
				</dd>
			</dl>
			
			<dl style="margin-top:20px">
				<dt style="font-weight: 900">新密码：</dt>
				<dd>
					<input type="text" name="newPwd" id="pwd1" onfocus="this.type='password'"   class="required alphanumeric"/>
				</dd>
			</dl>
			
			<dl style="margin-top:20px">
				<dt style="font-weight: 900">确认新密码：</dt>
				<dd>
					<input type="text" name="newPwd2" id="pwd2" onfocus="this.type='password'" class="required" equalto="#pwd1"/>
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
var isSubmit=false;
function checkUserPwd(){
	var oldPwd = document.getElementById("pwd").value.trim();
	if (oldPwd==null||oldPwd=="") {		
	}else {
		$.ajax({
		    type: "POST",
		    url: "web/user/checkUserPwd",
		    data: "oldPwd="+oldPwd,
		    dataType: "text",
		    success: function(result){
		    	if (result=="true") {
		    		document.getElementById("checkPwd").innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp正确";
		    		isSubmit = true;
				}else{
		    		document.getElementById("checkPwd").innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp旧密码错误";
		    		document.getElementById("pwd").value = "";
		    		isSubmit = false;
				}

			}
		});
	}
}



function toSave(){
	checkUserPwd();
	var oldPwd = document.getElementById("pwd").value.trim();
	var newOldPwd = document.getElementById("pwd1").value.trim();
	var newOldPwd2 = document.getElementById("pwd2").value.trim();
	if(newOldPwd==""){
		isSubmit = false;
	}
	if(newOldPwd2==""){
		isSubmit = false;
	}
	if(newOldPwd!=newOldPwd2){
		isSubmit = false;
	}
	if (isSubmit){
		if(confirm('确定修改密码?')){
			document.getElementById("button").type="submit";
		}
	}else{
		alert("请填写正确信息!");
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
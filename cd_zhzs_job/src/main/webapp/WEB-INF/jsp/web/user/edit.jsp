<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<div class="pageContent">

	<form method="post" action="${basePath }web/user/addOrEdit?_id=${SysUser._id}"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="58">
			<input type="hidden" name="userId"
				value="${sysReportUserInfo.userId}">
			<dl style="margin-top: 10px">
				<dt style="font-weight: 900">用户姓名：</dt>
				<dd>
					<input type="text" name="userName" maxlength="20" class="required"
						value="${SysUser.userName }" /> <span class="info"></span>
				</dd>
			</dl>
			
			<dl style="margin-top: 10px">
				<dt style="font-weight: 900">Email：</dt>
				<dd>
					<input type="text" style="width: 170px;" onblur="checkEmail();" id="email" class="required" name="email" value="${SysUser.email }" />
						<span style="margin-left: 5%;color: red;font-weight: 900;height: 21px;line-height: 21px" id="errorMail"></span>
				</dd>
			</dl>
			<dl style="margin-top: 10px">
				<dt style="font-weight: 900">手机号：</dt>
				<dd>
					<input type="text" name="phoneNumber" id="phoneNumber" onblur="checkPhone();" class="required" style="width: 170px;"
						value="${SysUser.phoneNumber }" />
						<span style="margin-left: 5%;color: red;font-weight: 900;height: 21px;line-height: 21px" id="errorPhone"></span>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">提交</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>

<script type="text/javascript">
function checkEmail(){ //正则邮箱
	var email = $("#email").val();
	var filter  = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
	 if (email.match(filter)){
		 document.getElementById("errorMail").innerHTML= "可以使用!";
		 return true;
	 }else{
	 	document.getElementById("errorMail").innerHTML= "邮箱格式错误!";
	 	$("#email").val("");
		return false;
	 }
}
function checkPhone(){ //正则手机号
	var email = $("#phoneNumber").val();
	var filter  = "^1[\\d]{10}$";
	 if (email.match(filter)){
		 document.getElementById("errorPhone").innerHTML= "可以使用!";
		 return true;
	 }else{
	 	document.getElementById("errorPhone").innerHTML= "号码格式错误!";
	 	$("#phoneNumber").val("");
		return false;
	 }
}
</script>

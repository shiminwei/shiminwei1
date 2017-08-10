<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="common/base.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="shortcut icon" href="${basePath}/fav.ico" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${projectTitle }</title>
    <script src="${basePath }static/js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <link href="./style/alogin.css" rel="stylesheet" type="text/css" />
<script  language="javascript">
function checklogin(){
	var loginform=document.loginform;
	   if (loginform.loginCode.value == "") {
		   	loginform.loginCode.focus();
	    	return false;
	  }
	  if (loginform.loginPwd.value == "") {
		  	loginform.loginPwd.focus();
	    	return false;
	  }
	  loginform.loginCode.value =loginform.loginCode.value;
	  loginform.loginPwd.value=loginform.loginPwd.value;
	  loginform.submit();
}
function on_return(){
	 if(window.event.keyCode == 13){
	  	if (document.all('sub')!=null){
	   		document.all('sub').click();
	   	}
	 }
}

function checkIt(){
	var val = document.getElementById("select").value;
	var loginform=document.loginform;
	if(val==02){
		loginform.action= "${projectWebLoginUrl}";	
	}else{
		loginform.action = "${projectReportLoginUrl}";
	}
}
</script>
</head>
<body onkeydown="on_return();">
    
    <div class="Main">
	<form id="loginform" name="loginform"  method="post" action="${basePath }login">
		<div class="content">
        <ul>
            <li class="top"></li>
            <li class="top2"></li>
            <li class="topA"></li>
            <li class="topB"><span>
                <img src="images/login/logo.gif" alt="" style="" />
            </span></li>
            <li class="topC"></li>
            <li class="topD">
                <ul class="login">
					<li><span class="left">平&nbsp;&nbsp;&nbsp;台：</span> <span style="left">
                        <select onchange="checkIt();" id="select"><option value="03">加工平台</option><option value="02">展现平台</option><option value="01">采集平台</option> </select>
                         <c:if test="${msg ne  null}">
				             <span style="color: red;font-size: 12px;">${msg }</span>
				         </c:if>
                    </span></li>
                    <li><span class="left">用户名：</span> <span style="left">
                        <input type="text" name="loginCode" id="loginCode" value="${loginCode }" />  
                    </span></li>
                    <li><span class="left">密&nbsp;&nbsp;&nbsp;码：</span> <span style="left">
                       <input  type="password" name="loginPwd" id="loginPwd"  />  
                    </span></li>
                                       
                </ul>
            </li>
            <li class="topE"></li>
            <li class="middle_A"></li>
            <li class="middle_B"></li>
            <li class="middle_C">
            <span class="btn">
               
                <a  href="javascript:checklogin();" id="sub" ><img src="images/login/btnlogin.gif" /></a>
            </span>
            </li>
            <li class="middle_D"></li>
            <li class="bottom_A"></li>
            <li class="bottom_B">
            安徽政大数据软件有限公司
            </li>
        </ul>
		</div>
		</form>
    </div>
    
</body>
</html>

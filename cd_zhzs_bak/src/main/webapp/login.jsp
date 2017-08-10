<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="common/base.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安徽财大综合治税平台</title>
<style type="text/css">
<!--
*{overflow:hidden; font-size:12pt;}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image: url(static/images/bg.gif);
	background-repeat: repeat-x;
}
select,option ,input{
	width:164px; height:32px; line-height:34px; background:url(static/images/inputbg.gif) repeat-x; border:solid 1px #d1d1d1; font-size:9pt; font-family:Verdana, Geneva, sans-serif;
}
-->
</style>
<script  language=javascript>
function checklogin(){
	document.getElementById("loginform").submit();
}
</script>
</head>

<body>
<FORM id=loginform method=post action="${basePath }login" >
<table width="100%"  height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="561" style="background:url(static/images/lbg.gif)"><table width="940" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="238" style="background:url(static/images/login01.jpg)">&nbsp;</td>
          </tr>
          <tr>
            <td height="190"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="208" height="190" style="background:url(static/images/login02.jpg)">&nbsp;</td>
                <td width="518" style="background:url(static/images/login03.jpg)"><table width="320" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="40" height="50"><img src="static/images/select.gif" width="32" height="32"></td>
                    <td width="38" height="50">选择</td>
                    <td width="242" height="50"><select><option>采集平台</option><option>展现平台</option></select></td>
                  </tr>
                  <tr>
                    <td width="40" height="50"><img src="static/images/user.gif" width="30" height="32"></td>
                    <td width="38" height="50">用户</td>
                    <td width="242" height="50"><input type="text" name="loginCode" id="loginCode"></td>
                  </tr>
                  <tr>
                    <td height="50"><img src="static/images/password.gif" width="30" height="32"></td>
                    <td height="50">密码</td>
                    <td height="50"><input type="password" name="loginPwd" id="loginPwd" style="width:164px; height:32px; line-height:34px; background:url(static/images/inputbg.gif) repeat-x; border:solid 1px #d1d1d1; font-size:9pt; "></td>
                  </tr>
                  <tr>
                    <td height="40">&nbsp;</td>
                    <td height="40">&nbsp;</td>
                    <td height="60"><a href="javascript:checklogin();"><img src="static/images/login.gif" width="95" height="34"/></a></td>
                  </tr>
                </table></td>
                <td width="213" style="background:url(static/images/login04.jpg)" >&nbsp;</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td height="133" style="background:url(static/images/login05.jpg)">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</FORM>
<div style="text-align:center;">
<p>Copyright &copy; 2016 ANHUI FINANCE BIGDATA SOFTWARE CO.,LTD All Rights Reserved.</p>
</div>
</body>
</html>

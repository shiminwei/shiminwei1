<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<html>
<body>
<div class="pageContent">
<form class="pageForm required-validate" method="post" action="${basePath }/web/number/addOrEdit" onsubmit="return validateCallback(this,dialogAjaxDone)">
<div class="pageFormContent nowrap" layouth="97" style="height: 500px;overflow: auto;">
	<input type="hidden" name="numberId" value="${bean.numberId }"/>
	<input type="hidden" name="id" value="${bean.id }"/>
	<dl>
		<dt style="font-weight: 900">识别号：</dt>		
		<textarea name="identifyNumber" rows="2" cols="50" tools="mini"  class="required" maxlength="100">${bean.identifyNumber }</textarea></dd>
	</dl>	
</div>
<div style="margin-top: 42px" class="formBar">
	<ul>
		<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
		<li style="margin-left: 10px;float: left">
			<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
		</li>
	</ul>
</div>
</form>
</div>
</body>
</html>
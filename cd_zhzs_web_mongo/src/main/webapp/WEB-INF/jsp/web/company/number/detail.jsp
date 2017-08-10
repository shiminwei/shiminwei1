<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<html>
<body>
<div class="pageContent">
<form class="pageForm required-validate" method="post" action="" class="pageForm required-validate">
<div class="pageFormContent nowrap" layouth="97" style="height: 500px;overflow: auto;">
	<input type="hidden" name="numberId" value="${bean.numberId }"/>
	<input type="hidden" name="id" value="${bean.id }"/>
	<dl>
		<dt style="font-weight: 900">识别号：</dt>		
		<textarea name="address" rows="2" cols="50" tools="mini"  class="required" disabled="disabled">${bean.identifyNumber }</textarea></dd>
	</dl>	
</div>
<div style="margin-top: 42px" class="formBar">
	<ul>
		<li style="margin-left: 10px;float: left">
			<div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
		</li>
	</ul>
</div>
</form>
</div>
</body>
</html>
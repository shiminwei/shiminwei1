<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<html>
<body>
<form class="pageForm required-validate" method="post" action="${basePath }/admin/message/addOrEdit" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
<div class="pageFormContent nowrap" layouth="97" style="height: 500px;overflow: auto;">
	<input type="hidden" name="messageId" value="${bean.messageId }">
	<input type="hidden" name="userId" value="${bean.userId }"/>
	<input type="hidden" name="createTime" value="${bean.createTime }"/> 
	<dl>
		<dt style="font-weight: 900">标题：</dt>
		<dd><input name="messageTitle" type="text" style="width:400px;" value="${bean.messageTitle }" class="required"/></dd>
	</dl >
	<dl>
		<dt style="font-weight: 900">创建时间：</dt>
		<dd style="width: 200px">
			<input type="text" name="createTimeStr" class="date" dateFmt="yyyy-MM-dd" disabled="true" value="${bean.createTimeStr }"/>
		</dd>
		<dt style="font-weight: 900">创建人：</dt>
		<dd style="width: 200px">
			<input type="text" name="userName" readonly="readonly" value="${bean.userName }"/>
		</dd>
	</dl>	
	<dl>
		<dt style="font-weight: 900;margin-top:20px">内容：</dt>
		<dd style="margin-top:20px">
		<textarea class="required" class="editor" name="messageContent" rows="10" cols="100" tools="mini" >${bean.messageContent }</textarea></dd>
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
</body>
</html>
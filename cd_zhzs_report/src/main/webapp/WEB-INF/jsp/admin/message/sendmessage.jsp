<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<div class="pageContent">

<form method="post" action="${basePath }/admin/message/sendMessage" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)" >

<div class="pageFormContent nowrap" layouth="97" style="height: 500px;overflow: auto;">
	<input type="hidden" name="messageId" value="${bean.messageId }"> 
	<dl>
		<dt style="font-weight: 900">标题：</dt>
		<dd><input name="messageTitle" type="text" style="width:200px;" value="${bean.messageTitle }" class="required" readonly="readonly"/></dd>
	</dl >
	<dl>
		<dt style="font-weight: 900;margin-top:20px">请选择前置机ID：</dst>
		<dd style="margin-top:20px">
		<textarea id="leadId" class="required" name="district.leadId" rows="4" cols="90" tools="mini" readonly="readonly" ></textarea>
		<a href="${basePath }/admin/departmentLead/looklist?status=1" type="lookup" lookupGroup="district"  style="color: green">查询功能</a>
		<a onclick="toClean()" style="color: green">清空功能</a>
		</dd>
	</dl>		
</div>
	<div style="margin-top: 42px" class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">发送</button></div></div></li>
			<li style="margin-left: 10px;float: left">
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
	</form>
</div>

<script type="text/javascript">
  function toClean(){
	$("#leadId").val("");
 } 
</script>
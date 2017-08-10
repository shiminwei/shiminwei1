<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<html>
<body>
<div class="pageContent">
<form class="pageForm required-validate" method="post" action="${basePath }/web/address/addOrEdit" onsubmit="return validateCallback(this,dialogAjaxDone)">
<div class="pageFormContent nowrap" layouth="97" style="height: 500px;overflow: auto;">
	<input type="hidden" name="addressId" value="${bean.addressId }"/>
	<input type="hidden" name="id" value="${bean.id }"/>	
	<dl>
		<dt style="font-weight: 900">地址来源：</dt>
		<dd style="width: 200px">
			<select name="type" style="width: 120px"  >
				<c:forEach items="${addressSource }" var="area">
					<option value="${area.key}" <c:if test="${bean.type == area.key}">selected="selected"</c:if>>${area.value }</option>
				</c:forEach>
			</select>
		</dd>
		<dt style="font-weight: 900">地址类型：</dt>
		<dd style="width: 200px">
			<select name="addressType" style="width: 120px"  >
				<c:forEach items="${addressType }" var="area">
					<option value="${area.key }" <c:if test="${bean.addressType == area.key}">selected="selected"</c:if>>${area.value }</option>
				</c:forEach>
			</select>
		</dd>
	</dl>
	<dl>
		<dt style="font-weight: 900">企业地址：</dt>		
		<textarea name="address" rows="5" cols="80" tools="mini"  class="required" maxlength="500">${bean.address }</textarea></dd>
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
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp" %> 
<div class="pageContent">
	<form method="post" action="${basePath }/admin/department/editArea" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
	<input type="hidden" name="areaId" value="${areaInfo.areaId}">
		<div class="pageFormContent nowrap" layoutH="58">
			<c:if test="${type!='add'}">
				<dl>
					<dt style="font-weight: 900">地区编码：</dt>
					<dd style="width: auto;">
						<strong><span style="color: red">${areaInfo.areaCode}</span></strong>
					</dd>
				</dl>
			</c:if>
			<dl>
				<dt style="font-weight: 900">地区名称：</dt>
				<dd style="width: auto;">
						<input type="text" name="areaName" id="areaName" onblur="checkAreaName();" value="${areaInfo.areaName }" class="required textInput " style="cursor: auto; "/>
				</dd>
			</dl>
			<%--
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
			 --%>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
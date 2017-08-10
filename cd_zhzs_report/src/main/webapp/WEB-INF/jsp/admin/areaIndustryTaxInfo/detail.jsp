<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<div class="pageContent">

	<form method="post" action="${basePath }admin/industryInfo/saveOrUpdate"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="60">

			<dl>
				<dt>编号：</dt>
				<dd style="width: 200px">
					<input type="text" name="aitiId" readonly="readonly" value="${bean.aitiId }"/>
				</dd>
				<dt>名称：</dt>
				<dd style="width: 200px">
					<input type="text" name="name" readonly="readonly" class="required" value="${bean.name }"/>
				</dd>
			</dl>
			<dl>
				<dt>地区：</dt>
				<dd style="width: 200px">
					<select name="areaId" class="required" disabled="disabled">
						<option value="">--请选择--</option>
						<c:forEach var="item" items="${areaType}">   
						<option value="${item.key }" <c:if test="${bean.areaId==item.key }">selected="selected"</c:if>>
						${item.value }</option>
						</c:forEach>
					</select>
				</dd>
				<dt>行业：</dt>
				<dd style="width: 200px">
					<select name="industryId" class="required" disabled="disabled">
						<option value="">--请选择--</option>
						<c:forEach var="item" items="${industryType}">   
						<option value="${item.industryId }" <c:if test="${bean.industryId==item.industryId }">selected="selected"</c:if>>
						${item.name }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>					
			<dl>
				<dt>税种：</dt>
				<dd style="width: 200px">
					<select name="taxId" class="required" disabled="disabled">
						<option value="">--请选择--</option>
						<c:forEach var="item" items="${taxType}">   
						<option value="${item.taxId }" <c:if test="${bean.taxId==item.taxId }">selected="selected"</c:if>>
						${item.name }</option>
						</c:forEach>
					</select>
				</dd>
				<dt>税率(%)：</dt>
				<dd style="width: 200px">
					<input type="text" name="taxrate" readonly="readonly" class="required" value="${bean.taxrate }"/>%
				</dd>
			</dl>
			<dl>
				<dt>备注：</dt>
				<dd style="width: 200px" >
					<textarea name="remark" readonly="readonly" rows="4" cols="50">${bean.remark }</textarea>
				</dd>
				<dt></dt>
				<dd>
				</dd>
			</dl>		
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">关闭</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>


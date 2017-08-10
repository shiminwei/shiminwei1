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
					<input type="text" name="taxId" readonly="readonly" value="${bean.taxId }"/>
				</dd>
				<dt>名称：</dt>
				<dd style="width: 200px">
					<input type="text" name="name" readonly="readonly" class="required" value="${bean.name }"/>
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


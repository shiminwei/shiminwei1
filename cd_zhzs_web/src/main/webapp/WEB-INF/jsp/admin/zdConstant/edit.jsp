<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../../common/base.jsp"%>
<div class="pageContent">

	<form method="post" action="${basePath }admin/zdConstant/saveOrUpdate"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">

			<dl>
				<dt>标识类型：</dt>
				<dd style="width: 200px">
					<input type="text" name="type" class="required"
						value="${bean.type }"
						<c:if test="${bean.type!=''&&bean.type!=null}">readonly="readonly"</c:if> />
				</dd>
				<dt>标识类型名称：</dt>
				<dd style="width: 200px">
					<input type="text" name="name" class="required"
						value="${bean.name }" <c:if test="${bean.name!=''&&bean.name!=null}">readonly="readonly"</c:if> />
				</dd>
			</dl>
			<dl>
				<dt>CODE：</dt>
				<dd style="width: 200px">
					<input type="text" name="code" class="required"
						value="${bean.code}" />
				</dd>

				<dt>VALUE：</dt>
				<dd style="width: 200px">
					<input type="text" name="value" class="required"
						value="${bean.value }" />
				</dd>
			</dl>


			<dl>
				<dt>状态：</dt>
				<dd style="width: 200px">
					<select class="combox" name="state" style="width: 100px">

						<option value="1"
							<c:if test="${bean.state==1 }">selected="selected"</c:if>>正常</option>
						<option value="0"
							<c:if test="${bean.state==0 }">selected="selected"</c:if>>禁用</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>码表描述：</dt>
				<dd>
					<textarea rows="5" cols="74" name="constantDesc">${bean.constantDesc} </textarea>
				</dd>
			</dl>

			<input type="hidden" name="constantId" value="${bean.constantId} " />
			<input type="hidden" name="isType" value="${istype}" />
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">提交</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>


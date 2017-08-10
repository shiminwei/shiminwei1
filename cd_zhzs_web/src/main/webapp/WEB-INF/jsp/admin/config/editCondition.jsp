<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>





<div class="pageContent">

	<form id="myForm" method="post"
		action="${basePath}admin/config/addCondition?id=${functionId}"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>标识ID：</dt>
				<dd style="width: 200px">
					<input type="text" name="conditionId" value="" class="required"  />
				</dd>
			</dl>
			<dl>
				<dt>查询显示名称:</dt>
				<dd style="width: 200px">
					<input type="text" name="dispname" value="" class="required" />
				</dd>
			</dl>
			</dl>
			<dl>
				<dt>查询类型：</dt>
				<dd style="width: 200px">

					<select name="disptype" class="combox">
						<option value="1"
							<c:if test="${ list.disptype == 1}"> selected="selected" </c:if>>文本框</option>
						<option value="2"
							<c:if test="${ list.disptype == 2}"> selected="selected" </c:if>>年下拉框</option>
						<option value="3"
							<c:if test="${ list.disptype == 3}"> selected="selected" </c:if>>年月下拉框</option>
						<option value="4"
							<c:if test="${ list.disptype == 4}"> selected="selected" </c:if>>税种下拉框</option>
					</select>
				</dd>
			</dl>


			<dl>
				<dt>显示长度：</dt>
				<dd style="width: 200px">

					<select name="width" class="combox">
						<option value="20"
							<c:if test="${ list.width == 20}"> selected="selected" </c:if>>20</option>
						<option value="30"
							<c:if test="${ list.width == 30}"> selected="selected" </c:if>>30</option>
						<option value="40"
							<c:if test="${ list.width == 40}"> selected="selected" </c:if>>40</option>
					</select>
				</dd>
			</dl>


			<dl>
				<dt>默认值：</dt>
				<dd style="width: 200px">
					<input type="text" name="morenzhi" value="" />
				</dd>
			</dl>
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

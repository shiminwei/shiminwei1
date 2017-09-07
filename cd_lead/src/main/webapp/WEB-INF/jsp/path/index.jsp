<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<h2 class="contentTitle">当前配置</h2>


<div class="pageContent">

	<form method="post" action="${basePath}admin/config/saveOrUpdate"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">

			<dl>
				<dt>文件路径配置：</dt>
				<dd>
<input type="file" name="file_btn" id="file_btn"  value="浏览...">
				</dd>
			</dl>
			<%--隐藏的值 --%>
			<input type="hidden" name="type" value="${type }" /> <input
				type="hidden" name="id" value="${bean.id }" />

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






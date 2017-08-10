<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<div class="pageContent">
	<form method="post" action="${basePath }admin/template/add"
		enctype="multipart/form-data" class="pageForm required-validate"
		onsubmit="return iframeCallback(this,navTabAjaxDoneForward);">
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt>上传文件&emsp;：</dt>
				<dd>
					<input name="uploadExcel" type="file" accept=".xls,.xlsx"
						class="required" />
				</dd>
			</dl>
			<dl>
				<dt>数据库表名：</dt>
				<dd>
					<span style="float: left; display: block; line-height: 26px;">${templateTablePrefix }</span>
					<input name="tableName" type="text" class="required" />
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
<script type="text/javascript">
	function navTabAjaxDoneForward(json) {
		if (json.statusCode == DWZ.statusCode.ok) {
			navTab.reload("${basePath }admin/template/toAddOrEdit", {
				data : {
					"excelTemplateStr" : json.forwardUrl,
					"type" : 1
				},
				navTabId : "addTemplate",
				callback : null
			})
		} else {
			DWZ.ajaxDone(json);
		}

	}
</script>
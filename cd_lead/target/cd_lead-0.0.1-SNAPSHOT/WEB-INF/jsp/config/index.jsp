<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<h2 class="contentTitle">当前配置</h2>

<div class="pageContent">
	<form method="post" id="configForm"
		action="${basePath}config/toUpdateConfig"
		onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">

			<dl>
				<dt>配置机标识ID：</dt>
				<dd>
					<input type="text" name="leadId" value="${lead.leadId }"
						style="width: 200px" />
				</dd>
			</dl>

			<dl>
				<dt>是否匹配到用户：</dt>
				<dd>
					<select class="combox" name="isMatch" disabled="disabled">

						<option value="1"
							<c:if test="${lead.isMatch==1 }" >selected="selected"</c:if>>是</option>
						<option value="0"
							<c:if test="${lead.isMatch==0 }" >selected="selected"</c:if>>否</option>

					</select>
				</dd>
			</dl>
			
			<dl>
				<dt>运行状态：</dt>
				<dd>
					<select class="combox" name="isStart" disabled="disabled">

						<option value="1"
							<c:if test="${lead.isStart==1 }" >selected="selected"</c:if>>已启动</option>
						<option value="0"
							<c:if test="${lead.isStart==0 }" >selected="selected"</c:if>>已停止</option>

					</select>
				</dd>
			</dl>
			<dl>
				<dt>用户ID：</dt>
				<dd>
					<input type="text" name="userId" maxlength="20"
						value="${lead.userId }" readonly="readonly" style="width: 200px" />
				</dd>
			</dl>

			<dl>
				<dt>用户名称：</dt>
				<dd>
					<input type="text" name="userName" maxlength="20"
						value="${lead.userName }" readonly="readonly" style="width: 200px" />
				</dd>
			</dl>

			<dl>
				<dt>用户部门：</dt>
				<dd>
					<input type="text" name="departmentName" maxlength="20"
						value="${lead.departmentName }" readonly="readonly"
						style="width: 200px" />
				</dd>
			</dl>

			<dl>
				<dt>抓取目录路径：</dt>
				<dd>
					<input type="text" id="dirPath" name="dirPath" class="required"
						value="${lead.dirPath }" style="width: 322px" onblur="isDirPath()" />
				</dd>
			</dl>

			<dl>
				<dt>抓取频率：</dt>
				<dd>
					<select class="combox" name="quartzType" id="quartzType"
						onchange="updateQuartzStr()">
						<option value="">----请选择----</option>
						<c:forEach items="${quartzMap }" var="quartzMap">
							<option value="${quartzMap.key }"
								<c:if test="${quartzMap.key==lead.quartzType }" >selected="selected"</c:if>>${quartzMap.key }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>

			<dl>
				<dt>QUARTZ字符串配置：</dt>
				<dd>
					<textarea rows="5" cols="50" name="quartzStr" id="quartzStr">${lead.quartzStr}</textarea>
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
							<button type="button" onclick="toSave()">提交</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>

</div>

<script type="text/javascript">
	var isDir = true;
	var oldDirPath = '${lead.dirPath }';
	function isDirPath() {
		var dirPath = $("#dirPath").val();
		if (dirPath == null || "" == $.trim(dirPath)) {
			alert("请填写正确的抓取路径!");
		} else if (oldDirPath == dirPath) {
			isDir = true;
		} else {
			$.ajax({
				type : "POST",
				url : "${basePath}config/isFilePath",
				data : "filePath=" + dirPath,
				success : function(msg) {
					if (msg == "false") {
						alert("请填写正确的抓取路径!");
						isDir = false;
					} else {
						isDir = true;
					}
				}
			});
		}
	}

	function toSave() {
		if (isDir) {
			$("#configForm").submit();
		} else {
			alert("请填写正确的抓取路径!");
		}
	}

	function updateQuartzStr() {
		var quartzType = $("#quartzType option:selected").val();
		var quartzStr = $("#quartzStr");
		if (quartzType==""||quartzType==null) {
			quartzStr.val("");
		}else {
			var quartzMapJson = JSON.parse('${quartzMapJson}');	
			for ( var key in quartzMapJson) {
				if (quartzType == key) {
					quartzStr.val(quartzMapJson[key]);
					break;
				}
			}
		}
		
		

	}
</script>



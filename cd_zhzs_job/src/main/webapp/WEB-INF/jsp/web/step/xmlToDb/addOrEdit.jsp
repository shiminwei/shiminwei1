<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../../common/base.jsp"%>
<h2 class="contentTitle">配置步骤（数据源导出为XML数据）</h2>
<div class="pageContent">
	<form id="myForm" method="post"
		action="${basePath}/web/xml/import/addOrEdit"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>动作名称：</dt>
				<dd>
					<input type="text" name="name" maxlength="20" class="required"
						value="${bean.name }" style="width: 200px" />
				</dd>
			</dl>
			<dl>
				<dt>数据源：</dt>
				<dd>
					<select name="datasourceId" id="datasourceId" class="required combox"
						 onchange="toChangeTableName()">
						<option value="">----请选择数据源----</option>
						<c:forEach items="${datasourceList}" var="list" varStatus="status">
							<option value="${list.id }"
								<c:if test="${list.id== bean.datasourceId}">selected="selected"</c:if>>${list.name }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>

			<dl>
				<dt>数据库表名：</dt>
				<dd>
					<input id="districtTableName" name="district.tableName" 
						value="${bean.tableName }" type="text" readonly="readonly" /> <a
						class="btnLook" href="javascript:void(0);"
						onclick="toShowLookList()" lookupGroup="district">查找带回</a>
				</dd>
			</dl>
			<dl>
				<dt>文件名后缀 ：</dt>
				<dd>
					<select name="fileNamePatterns" id="fileNamePatterns"
						class="combox">
						<option value="">--请选择--</option>
						<c:forEach items="${fileNamePatternsMap}" var="map"
							varStatus="status">
							<option value="${map.key}"
								<c:if test="${map.key== bean.fileNamePatterns}">selected="selected"</c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>读取路径：</dt>
				<dd>
					<input id="filePath" name="filePath" type="text" vFileType="xml,XML" class="required noRootPath"
						style="width: 200px" value="${bean.filePath }" />
				</dd>
			</dl>
			
			<dl>
				<dt>根节点名称：</dt>
				<dd>
					<input name="rootName" type="text" 
						style="width: 200px" value="${bean.rootName }" />
				</dd>
			</dl>
			
			<dl>
				<dt>子节点名称：</dt>
				<dd>
					<input name="rowName" type="text" 
						style="width: 200px" value="${bean.rowName }" />
				</dd>
			</dl>
			
			<dl>
				<dt>数据源类型属性名称：</dt>
				<dd>
					<input name="dataTypeName" type="text" 
						style="width: 200px" value="${bean.dataTypeName }" />
				</dd>
			</dl>
			
			<dl>
				<dt>字段类型名称：</dt>
				<dd>
					<input name="colTypeName" type="text" 
						style="width: 200px" value="${bean.colTypeName }" />
				</dd>
			</dl>
			
			<dl>
				<dt>开始行：</dt>
				<dd>
					<input type="text" id="beginRowIndex" name="beginRowIndex"
						class="digits" value="${bean.beginRowIndex }" style="width: 200px" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span style="color: red">不输入默认从第一行开始导入</span>
				</dd>
			</dl>
			<dl>
				<dt>结束行：</dt>
				<dd>
					<input type="text" id="endRowIndex" name="endRowIndex"
						greaterThan="#beginRowIndex" class="digits"
						value="${bean.endRowIndex }" style="width: 200px" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span style="color: red">不输入默认全部导入</span>
				</dd>
			</dl>

			<input type="hidden" name="stepId" value="${bean.stepId }"> <input
				type="hidden" name="jobId" value="${jobId}"> <input
				type="hidden" name="type" value="${type}">

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
							<button type="button" class="close">返回</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>

</div>
<script type="text/javascript">
	var oldDatasourceId = '${bean.datasourceId}';
	function toShowLookList() {
		var datasourceId = $("#datasourceId").val();
		oldDatasourceId = datasourceId;
		$('.btnLook').attr('href',
				"web/lookTable/list?datasourceId=" + datasourceId);
		if (datasourceId == '') {
			alert("请先选择数据源!");
			$("#districtTableName").val("");
		}
	}

	function toSave() {
		var tableName = $("#districtTableName").val();
		$("#tableName").val(tableName);
		$("#myForm").submit();
	}

	function toChangeTableName() {
		var datasourceId = $("#datasourceId").val();
		if (datasourceId != oldDatasourceId) {
			$("#districtTableName").val("");
		}
	}
</script>


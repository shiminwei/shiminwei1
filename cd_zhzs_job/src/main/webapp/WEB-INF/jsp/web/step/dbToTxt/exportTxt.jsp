<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../../common/base.jsp"%>
<div class="panelBar" align="center" style="color:red">
	<h2 class="contentTitle" style="margin-right: 89%;margin-left: -2%">数据库导出记事本步骤</h2>
</div>
<style>
.tableCss {
	border: solid 1px #ededed;
	text-align: center;
	height: 30px;
}
</style>

<div class="pageContent">

	<form id="myForm" method="post"
		action="${basePath}/web/txt/dbToTxtSaveToMongo?jobId=${jobId}&stepId=${stepId}&sortNum=${sortNum}"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="82">
			<dl>
				<dt>动作名称：</dt>
				<dd>
					<input type="text" name="name" maxlength="20" value="${needStep.name}"
						style="width: 200px" />
				</dd>
			</dl>
			<dl>
				<dt>数据源：</dt>
				<dd>
					<select name="datasourceId" id="datasource" class="combox" onchange="clearData();">
						<option value="">--请选择--</option>
						<c:forEach items="${datasourceList}" var="list" varStatus="status">
							<option value="${list.id }"
								<c:if test="${list.id== needStep.datasourceId}">selected="selected"</c:if>>${list.name }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl style="margin-top: 1%">
				<dt>文件存储路径：</dt>
				<dd>
					<input id="path" name="path" class="required noRootPath" type="text"  style="width: 200px" value="${name}"/><span
							style="color: red">&nbsp;&nbsp;&nbsp;不需要输入根目录</span>
				</dd>
			</dl>
			<dl  style="margin-top: 1%">
				<dt>文件名后缀 ：</dt>
				<dd>
					<select name="fileNamePatterns" id="fileNamePatterns" class="combox" >
						<option value="">--请选择--</option>
						<c:forEach items="${fileNamePatternsMap}" var="map" varStatus="status">
							<option value="${map.key}" <c:if test="${map.key== needStep.fileNamePatterns}">selected="selected"</c:if>>${map.value}</option>
						</c:forEach>
					</select><span style="color: red;height:21px;line-height: 21px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;可不选</span>
				</dd>
			</dl>
			<dl  style="margin-top: 1%">
				<dt>分隔符：</dt>
				<dd>
					<input type="text" name="separator" maxlength="20" id="separator" 
						<c:if test="${needStep.fileNamePatterns == null}">value="\t"</c:if> value="${needStep.separator}"
						class="required"
					style="width: 237px" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color: red">代表制表符,默认为"/t"</span>
				</dd>
			</dl>
			<dl  style="margin-top: 1%">
				<dt>开始行：</dt>
				<dd>
					<input type="text" name="beginRow" maxlength="20" value="${needStep.beginRowIndex }" id="beginRowIndex" 
						style="width: 237px" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color: red">不输入默认从第一行开始导入</span>
				</dd>
			</dl>
			<dl>
				<dt>SQL脚本：</dt>
				<dd>
					<textarea rows="10" cols="80" name="sql" id="sql"  onblur="checkSql();">${needStep.sql}</textarea>
				</dd>
				<span style="color: red">输入的必须是"select"查询语句</span>
			</dl>
			<input type="hidden"  name="hideValue" id="hideValue" value=""/>
			<input type="hidden"  name="hideKey" id="hideKey" value=""/>
			<dl>
				<dt>获取数据库字段：</dt>
				<dd>
					<button type="button" onclick="toGetName()">获得数据库列名</button>
				</dd>
			</dl>
			<table class="list" width="40%" id="dataTable">
				<thead>
					<tr>
						<th align="center" style="width:300px">字段名称</th>
						<th align="center" style="width:300px">对应列名</th>
					</tr>

				</thead>
				<tbody id="table">
					<c:forEach items="${needStep.paramMap}" var="map" varStatus="status">
						<tr>
							<td style="margin-left:0;margin-right:0" ><input type="text" value="${map.key}" style="border:none;text-align:center;width: 100%;" readOnly="true"></td>
							<td style="margin-left:0;margin-right:0 " align="center"><input type="text" value="${map.value}"></td>
						</tr>
					</c:forEach>
				</tbody>
		</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="toSave()">提交</button>
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
	var flag1 = false;
	var flag2 = false;
	function clearData(){
		$("#table").empty();
		
	}
	function checkDataSource(){
		var dataSourceId = $("#datasource").val();
		if(dataSourceId==""||dataSourceId==null){
			alertMsg.error("请先选择数据源");
			return flag1;
		}else{
			flag1=true;
			return flag1;
		}
	}
	function checkSql(){
		var sqlStr = $("#sql").val().replace(/[\r\n]/g, " ");
		if(sqlStr!="" && sqlStr!=null){
			if(sqlStr.substring(0,6)=="select"){
				flag2=true;
				return flag2;
			}
		}
		alertMsg.error("请正确填写SQL脚本");
		return flag2;
	}

	function toGetName(){
		$("#table").empty();
		var dataSourceId = $("#datasource").val();
		var sqlStr = $("#sql").val();
		flag1=checkDataSource();
		flag2=checkSql();
		jsonStr=encodeURI(sqlStr);
		if(flag1&&flag2){
			 $.ajax({
					type : "POST",
					url : "web/excel/exportEx/getDbCol",
					data : "sqlStr=" + jsonStr + "&datasourceId="+ dataSourceId,
					dataType : "json",
					success : function(resp) {
						if(resp.flag==1){
							$.each(resp.list,function(index, obj) {
								$("#table").append("<tr class='tableCss'><td><input name='key' type='hidden' value="+obj+">"+ obj+" </td><td><input name='str' id='str' placeholder='请输入对应的列名称'> </td></tr>")
							})
						}else{
							alertMsg.error("表名无效");
						}
					}
				}) 
		}
	}
	
	function toSave() {
		var tdStr = document.getElementById("dataTable").getElementsByTagName(
				"TD");
		var myData = [];
		for (var i = 0, tdlen = tdStr.length; i < tdlen; i++) {
			var input = tdStr[i].getElementsByTagName("input");
			len = input.length;
			if (len == 0)
				continue;
			else {
				for (var j = 0; j < len; j++) {
					myData.push(input[j].value);
				}
			}
		}
		myData = myData.join(',');
		$("input[name='hideValue']").val(myData);
		$("#myForm").submit();
	}
</script>



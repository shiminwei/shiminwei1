<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../../common/base.jsp"%>
<div class="panelBar" align="center" style="color:red">
	<h2 class="contentTitle" style="margin-right: 91%;margin-left: -2%">以记事本文件导入</h2>
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
		action="${basePath}/web/txt/txtSaveToMongo?jobId=${jobId}&stepId=${stepId}&sortNum=${sortNum}"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="82">
			<dl>
				<dt>动作名称：</dt>
				<dd>
					<input type="text" name="name" maxlength="20" class="required" value="${needStep.name}"
						style="width: 237px" />
				</dd>
			</dl>
			<dl>
				<dt>数据源：</dt>
				<dd>
					<input name="datasourceId" type="hidden" id="dataValue"> 
					<select name="DataJobStepBase.datasourceId" id="datasource" class="combox"
						onchange="getDataSourceId();">
						<c:forEach items="${datasourceList}" var="list" varStatus="status">
							<option value="${list.id }"
								<c:if test="${list.id== needStep.datasourceId}">selected="selected"</c:if>>${list.name }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>数据库表名：</dt>
				<dd>
					<input name="tableName" type="hidden" id="hideName" >
					<input name="district.table_id" type="hidden" id="tableName">
					<input id="district.tableName" name="district.tableName" class="vname"
						type="text" readonly="readonly" value="${needStep.tableName}"/> <a
						class="btnLook" href="javascript:void(0);"
						lookupGroup="district">查找带回</a>
				</dd>
			</dl>
			<div id="uploadForm">
				<dl style="margin-top: 1%">
					<dt>选 择 文 件 ：</dt>
					<dd>
						<input id="file" name="file" type="file" accept=".txt" />
					</dd>
				</dl>
			<dl  style="margin-top: 1%">
				<dt>读取路径：</dt>
				<dd>
					<input id="path" name="path" type="text" class="required noRootPath" style="width: 237px" value="${name}"/><span
							style="color: red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;不需要输入根目录</span>
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
					<input type="text" class="digits" name="beginRow" maxlength="20" <c:if test="${needStep.beginRowIndex==2}">value=""</c:if> value="${needStep.beginRowIndex}" id="beginRowIndex" 
						style="width: 237px" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color: red">不输入默认从第一行开始导入</span>
				</dd>
			</dl>
			<dl  style="margin-top: 1%">
				<dt>结束行：</dt>
				<dd>
					<input type="text" class="digits" name="endRow" maxlength="20" <c:if test="${needStep.endRowIndex==999999999}">value=""</c:if> value="${needStep.endRowIndex}" greaterThan="#beginRowIndex" id="endRowIndex"
						style="width: 237px" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
						style="color: red">不输入默认全部导入</span>
				</dd>
			</dl>
				<dl  style="margin-top: 1%">
					<dt>获取对应列名：</dt>
					<dd>
						<button type="button" onclick="toGetTxtCols()">获得对应的列名</button>
					</dd>
				</dl>
			</div>
			<input type="hidden" name="hideKey" id="hideKey" value=""/>
			<input type="hidden"  name="hideValue" value=""/>
			<input type="hidden"  name="type" value="0"/>
			<table class="list" width="40%" id ="dataTable">
				<thead>
					<tr>
						<th align="center" style="width:300px">字段名称</th>
						<th align="center" style="width:300px">对应列名</th>
					</tr>

				</thead>
				<tbody id="table">
					<c:forEach items="${needStep.paramMap}" var="map" varStatus="status">
						<tr>
							<td style="margin-left:0;margin-right:0;"><input type="text" value="${map.key}" style="border:none;width:99.8%;text-align:center" readOnly="true"></td>
							<td style="margin-left:0;margin-right:0 ;">
								<select maxlength="20" class="required cols" class="combox" name="paramValue" style="width:280px;text-align:center">
									<c:forEach items="${txtCols }" var="cols">
										<option <c:if test="${cols== map.value}">selected="selected"</c:if>>${cols}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<!-- <button type="submit">提交</button> -->
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
	$(document).ready(function(){
		var sId = $("#datasource").val();
		$('#dataValue').val(sId);
		var ahref = "web/excel/importEx/lookList?datasourceId=" + sId;
		$('.btnLook').attr('href', ahref);
	});

	function getDataSourceId() {
		var sId = $("#datasource").val();
		$('#dataValue').val(sId);
		var ahref = "web/excel/importEx/lookList?datasourceId=" + sId;
		$('.btnLook').attr('href', ahref);
		$('#table').empty();
		$(".vname").val("");
	}
	function TxtChange() {
		var txtValue = $('#tableName').val();
		$('#hideName').val(txtValue);
		$('#table').empty();
		var objValue="";
		var dataSourceId = $('#datasource option:selected').val();
		 $.ajax({
				type : "POST",
				url : "web/excel/importEx/getCol",
				data : "table_name=" + txtValue + "&datasourceId="+ dataSourceId,
				dataType : "json",
				success : function(resp) {
					if(resp.flag==1){
						$.each(resp.datas,function(index, obj) {
							var tr = "<tr class='tableCss'><td><input name='paramMapKey' type='hidden' value="+obj.COLUMN_NAME+">"+ obj.COLUMN_NAME+" </td><td> <select name='paramMapValue' id='cols' class='combox cols' style='width:280px'><option id='base'>--请选择--</option></select></td></tr>";
							objValue +=obj.COLUMN_NAME+",";
							$("#table").append(tr);
						}) 
					}
						$("input[name='hideKey']").val(objValue.substr(0,objValue.length-1));
				}
			})
	}
	

	function toGetTxtCols() {
		var tablename = $('#hideName').val();
		var flag1 = false;
		var flag2 = false;
		tablename!=""?flag1 = true:alertMsg.error("请先选择表名");
		var file =$('#file')[0].files[0];
		file!=undefined?flag2=true:alertMsg.error("请先选择文件");
		$('.cols').empty();
		$('.cols').append("<option id='base' value=''>--请选择--</option>");
		if(flag1&&flag2){
		var formData = new FormData();
		formData.append('file',$('#file')[0].files[0]);
		formData.append('separator',$('#separator').val());
		var separator = $('#separator').val();
	     $.ajax({  
	          url: 'web/txt/getTxtCols' ,  
	          type: 'POST',  
	          data: formData,
	          async: false,  
	          cache: false,  
	          contentType: false,  
	          processData: false,  
	          dataType : "json",
	          success: function (data) { 
	        	  $.each(data, function(index, obj) {
						$('.cols').append("<option value="+obj+">"+obj+ "</option>");
					}) 
	          }  
	     })
	     	alertMsg.correct("获取对应列成功!")	;
		}
	}
	function toSave(){
		var tdStr = document.getElementById("dataTable").getElementsByTagName("TD");
	    var myDataKey = [];
	    var myDataValue = [];
	    for (var i = 0,tdlen= tdStr.length; i < tdlen; i++) {
	        var select = tdStr[i].getElementsByTagName("select");
	        var str ;
	        var input = tdStr[i].getElementsByTagName("input");
	        lenValue = select.length;
	        lenKey = input.length;
            for(var j=0;j<lenValue;j++){
            	myDataValue.push(select[j].value);
            }
            for(var k=0;k<lenKey;k++){
            	myDataKey.push(input[k].value);
                str+=input[k].value;
            }
	    }
	    myDataKey = myDataKey.join(',');
	    myDataValue = myDataValue.join(',');
	    $("input[name='hideValue']").val(myDataValue);
	    $("#hideKey").val(myDataKey);
		$("#myForm").submit();
	} 
</script>



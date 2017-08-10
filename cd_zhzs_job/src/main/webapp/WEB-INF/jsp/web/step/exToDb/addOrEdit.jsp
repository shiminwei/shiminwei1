<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../../common/base.jsp"%>

<c:choose>
   <c:when test="${method=='add' }">
   		<h2 class="contentTitle">新增步骤（Excel数据导入到数据库中）</h2>
   </c:when>
   <c:otherwise> 
   		<h2 class="contentTitle">修改步骤（Excel数据导入到数据库中）</h2>
   </c:otherwise>
</c:choose>
<style>
.tableCss {
	border: solid 1px #ededed;
	text-align: center;
	height: 30px;
}
</style>
<div class="pageContent">
	<form id="myForm" method="post" action="${basePath}/web/excel/importEx/saveImportToMongo?method=${method }" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>动作名称：</dt>
				<dd>
					<input type="text" name="name" maxlength="20" class="required" value="${bean.name }"
						style="width: 200px" />
				</dd>
			</dl>
			<dl>
				<dt>数据源：</dt>
				<dd>
					<select name="datasourceId" id="datasource" class="combox" onchange="getDataSourceId();">
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
					<input name="tableName" type="hidden" id="hideName" value=${bean.tableName }>
					<input name="district.table_id" type="hidden" id="tableName">
					<input id="district.tableName" name="district.tableName" class="vname" value="${bean.tableName }" type="text" readonly="readonly"/> 
					<a class="btnLook" href="javascript:void(0);" lookupGroup="district">查找带回</a>
				</dd>
			</dl>
			<div id="uploadForm">
				<dl style="margin-top: 10px">
					<dt>选 择 文 件 ：</dt>
					<dd>
						<input id="file" name="file" type="file" class="" />
					</dd>
				</dl>
				<dl>
					<dt>工作表：</dt>
					<dd>
						<input type="text" name="sheetIndex" id="sheet"
							maxlength="20" value="${bean.sheetIndex }"
							style="width: 200px" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
							style="color: red">"0"代表第一个工作表</span>
					</dd>
				</dl>
				<dl>
					<dt>文件名后缀 ：</dt>
					<dd>
						<select name="fileNamePatterns" id="fileNamePatterns" class="combox" >
							<option value="">--请选择--</option>
							<c:forEach items="${fileNamePatternsMap}" var="map" varStatus="status">
								<option value="${map.key}"<c:if test="${map.key== bean.fileNamePatterns}">selected="selected"</c:if>>${map.value }</option>
							</c:forEach>
						</select><span style="color: red;height:21px;line-height: 21px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;可不选</span>
					</dd>
				</dl>
				<dl>
					<dt>读取路径：</dt>
					<dd>
						<input id="path" name="path" type="text" class="required  noRootPath" style="width: 200px" vFileType="xls,xlsx,XLS,XLSX" value="${bean.filePath }"/><span
								style="color: red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;不需要输入根目录</span>
					</dd>
				</dl>
				<dl>
					<dt>开始行：</dt>
					<dd>
						<input type="text" name="beginRowIndex" maxlength="20" value="${bean.beginRowIndex }" id="beginRowIndex"
							style="width: 200px" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
							style="color: red">不输入默认从第一行开始导入</span>
					</dd>
				</dl>
				<dl>
					<dt>结束行：</dt>
					<dd>
						<input type="text" name="endRowIndex" maxlength="20" value="${bean.endRowIndex }" id="endRowIndex"
							style="width: 200px" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
							style="color: red">不输入默认全部导入</span>
					</dd>
				</dl>
				<dl>
					<dt>获取对应列名：</dt>
					<dd>
						<button type="button" onclick="toGetExcelCols()">获得对应的列名</button>
					</dd>
				</dl>
			</div>
			<input type="hidden" name="stepId" value="${stepId }">
			<input type="hidden" id="hideKey" name="hideKey" value=""/>
			<input type="hidden"  name="hideValue" value=""/>
			<input type="hidden"  name="jobId" value="${jobId }"/>
			<c:choose>
			   <c:when test="${method=='add' }">
			   		<input type="hidden"  name="type" value="${type }"/>
			   </c:when>
			   <c:otherwise> 
			   		<input type="hidden"  name="type" value="${bean.type }"/> 
			   </c:otherwise>
			</c:choose>
			<table class="list" width="600px" id ="dataTable">
				<thead>
					<tr>
						<th align="center" style="width:50%">字段名称</th>
						<th align="center" style="width:50%">对应列名</th>
					</tr>
				</thead>
				<tbody id="table">
					<c:forEach items="${bean.paramMap}" var="map" varStatus="status">
						<tr>
							<td style="margin-left:0;margin-right:0;"><input type="text" value="${map.key}" style="border:none;width:99.8%;text-align:center" readOnly="true"></td>
							<td style="margin-left:0;margin-right:0 ;">
								<select maxlength="20" class="required cols" class="combox" name="paramValue" style="width:280px;text-align:center">
									<c:forEach items="${excelCols }" var="cols">
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
		var datasourceId = $('#datasource option:selected').val();
		 $.ajax({
				type : "POST",
				url : "web/excel/importEx/getCol",
				data : "table_name=" + txtValue + "&datasourceId="+ datasourceId,
				dataType : "json",
				success : function(resp) {
					if(resp.flag==1){
						$.each(resp.datas,function(index, obj) {
							var tr = "<tr class='tableCss'><td><input name='paramMapKey' type='hidden' value="+obj.COLUMN_NAME+">"+ obj.COLUMN_NAME+" </td><td> <select name='paramMapValue' id='cols' class='combox cols' style='width:280px'><option id='base'>--请选择--</option></select></td></tr>";
							objValue +=obj.COLUMN_NAME+",";
							$("#table").append(tr);
						}) 
					}
						/* $.each(resp,function(index, obj) {
							var tr = "<tr class='tableCss'><td><input name='paramMapKey' type='hidden' value="+obj.COLUMN_NAME+">"+ obj.COLUMN_NAME+" </td><td> <select name='paramMapValue' id='cols' class='combox cols' style='width:280px'><option id='base'>--请选择--</option></select></td></tr>";
							objValue +=obj.COLUMN_NAME+",";
							$("#table").append(tr);
						}) */
				}
			}) 
	}
	

	function toGetExcelCols() {
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
		formData.append('sheetIndex',$('#sheet').val());
	     $.ajax({  
	          url: 'web/excel/importEx/getExcelCols' ,  
	          type: 'POST',  
	          data: formData,  
	          async: false,  
	          cache: false,  
	          contentType: false,  
	          processData: false,  
	          dataType : "json",
	          success: function (data) { 
	        	  $.each(data, function(index, obj) {
						$('.cols').append("<option value="+obj+">"+ obj + "</option>");
					}) 
	          }  
	     })
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



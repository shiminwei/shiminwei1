<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<h2 class="contentTitle">
	【${template.name }】数据
	<c:choose>
		<c:when test="${reportType ==2}">重报</c:when>
		<c:when test="${reportType ==3}">补报</c:when>
		<c:otherwise>报送</c:otherwise>
	</c:choose>
</h2>
<div class="pageContent">
	<form id="myFrom" method="post"
		action="${basePath }web/dataReport/report"
		enctype="multipart/form-data" class="pageForm required-validate"
		onsubmit="return iframeCallback(this,navTabAjaxDone);">
		<div class="pageFormContent " layoutH="80">
			<fieldset>
				<dl style="margin-top: 10px">
					<dt>选择上报时间：</dt>
					<dd>
						<select  class="combox" name="year" id="myYear"
							onchange="getMonth()">
							<c:forEach items="${year }" var="year">
								<option value="${year.key }"
									<c:if test="${nowYear == year.key}"> selected="selected"</c:if>>${year.value }</option>
							</c:forEach>
							
						</select> 
						<c:if test="${month != null && month.size() > 0 }">
						<select  class="combox" name="month" id="myMonth">
							<c:forEach items="${month }" var="month">
								<option value="${month.key }"
									<c:if test="${nowMonth== month.key}"> selected="selected"</c:if>>${month.value }</option>
							</c:forEach>
						</select></c:if>
					</dd>
				</dl>
				<dl style="margin-top: 10px">
					<dt>选 择 文 件 ：</dt>
					<dd>
						<input id="myuploadFile" name="file" type="file" class="" />
					</dd>
				</dl>
				<dl style="margin-top: 10px">
					<dt>操作：</dt>
					<dd>
						<input type="button" onclick="toCheckFile()" value="提交">
					</dd>
				</dl>
				<%-- 隐藏值 --%>
				<input type="hidden" name="templateName" value="${template.name }" />
				<input type="hidden" name="reportType" value="${reportType }" /> <input
					type="hidden" name="reportPeroid" value="${bean.reportPeroid }" />
			</fieldset>
			<h2 class="contentTitle" style="margin-top: 50px; color: red">错误列表：</h2>
			<fieldset>
				<div style="scroll-y: auto; overflow-x: hidden; height: 60%";>
					<table class="table" width="60%">
						<thead>
							<tr>
								<th width="60">序号</th>
								<th width="60">行数</th>
								<th width="60">列数</th>
								<th width="180">错误内容</th>
							</tr>
						</thead>
						<tbody id="myTbody">
						</tbody>
					</table>
				</div>
				<fieldset>
		</div>
	</form>



</div>
<script type="text/javascript">
	function getValue() {
		var falg = false;
		var myuploadFile =document.getElementById("myuploadFile").value;		
		var myMonth = $('#myMonth option:selected').text();
		if (myMonth == null || myMonth == "") {
			if ('${bean.reportPeroid }'!=4) {
				alert("请先选择月份");
				return falg;	
			}
		}
		if (myuploadFile == null || myuploadFile == "") {
			alert("请先选择上传文件");
		} else {
			falg = true;
		}
		return falg;
	}

	function toCheckFile() {
		if (getValue()) {
			var formData = new FormData($("#myFrom")[0]);
			var result;
			addTbody(null);
			$.ajax({
				url : '${basePath }web/dataReport/checkReport',
				type : 'POST',
				data : formData,
				async : false,
				cache : false,
				contentType : false,
				processData : false,
				success : function(returndata) {
					result = returndata;
				},
				error : function(returndata) {
					result = returndata;
				}
			});
			if (result == -1) {
				alert("文件格式不正确！请上传.xls或者.xlsx格式的文件！");
			} else if (result == -2) {
				alert("请检查您的表格，存在问题！");
				message = "表头为空！";
			} else if (result == -3) {
				alert("原始模板存在问题！");
			} else if (result == -4) {
				alert("上传文件与模板不一致！");
			} else if (result == -5) {
				alert("上报的数据为空，请填写数据！");
			} else if (result == 1) {
				toCheckContent();
			} else {
				alert("这下完蛋了！出现了未知错误！！");
			}
		}

	}

	function toCheckContent() {
		var formData = new FormData($("#myFrom")[0]);

		$.ajax({
			url : '${basePath }web/dataReport/checkExcelContent',
			type : 'POST',
			data : formData,
			async : false,
			cache : false,
			contentType : false,
			processData : false,
			success : function(returndata) {
				var obj = JSON.parse(returndata);
				if (obj.length > 0) {
					addTbody(obj);
				} else {
					$("#myFrom").submit();
				}
			},
			error : function(returndata) {
				alert(returndata);
			}
		});
	}

	function addTbody(obj) {
		var addStr = "";
		if (obj != null) {
			for (var i = 0; i < obj.length; i++) {
				var rowNum = obj[i].row + 1;
				var columnNum = obj[i].column + 1;

				addStr += "<tr><td   width='70' align='center' style='color: red'>"
						+ (i + 1) + "</td> ";
				addStr += "<td  width='70' align='center' style='color: red'> 第"
						+ rowNum + "行</td> ";
				addStr += "<td  width='70' align='center' style='color: red' >第"
						+ columnNum + "列</td> ";
				addStr += "<td   width='218' align='center'  style='color: red'>"
						+ obj[i].content + "</td></tr> ";
			}
		}
		myTbody.innerHTML = addStr;
	}

	function getMonth() {
		var myYear = $('#myYear option:selected').text();
		$.ajax({
			type : "POST",
			url : '${basePath }web/dataReport/getMonthMap',
			data : "templateName=" + '${template.name }' + "&reportZq="
					+ '${bean.reportPeroid }' + "&reportYear=" + myYear
					+ "&reportType=" + '${reportType }',
			dataType : "json",
			success : function(data) {
				addption(data);
			}
		});

	}
	function addption(data) {
		try{
			var myMonth = document.getElementById("myMonth");
			myMonth.options.length = 0;
			for ( var key in data) {
				myMonth.options.add(new Option(data[key], key));
			}
		}catch(e){}	
	}
</script>

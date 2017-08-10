<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<h2 class="contentTitle">配置增改</h2>


<div class="pageContent">

	<form id="myForm" method="post"
		action="${basePath}admin/config/saveOrUpdate"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">

			<dl>
				<dt>功能名称：</dt>
				<dd>
					<input type="text" name="name" maxlength="20" value="${bean.name }"
						class="required" style="width: 200px" />
				</dd>
			</dl>
			<dl>
				<dt>数据源：</dt>
				<dd>
					<select name="datasource" id="datasource" class="combox">
						<c:forEach items="${datasourceList}" var="list" varStatus="status">
							<option value="${list.id }"
								<c:if test="${list.id== bean.datasource}">selected="selected"</c:if>>${list.name }</option>

						</c:forEach>

					</select>
				</dd>
			</dl>
			<dl>
				<dt>查询方式：</dt>
				<dd>
					<select id="resultType" name="resultType"
						 class="combox">
						<option value="SQL"
							<c:if test="${bean.resultType=='SQL'}"> selected="selected"</c:if>>SQL</option>
						<option value="存储过程"
							<c:if test="${bean.resultType=='存储过程'}"> selected="selected"</c:if>>存储过程</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>SQL语句：</dt>
				<dd>
					<textarea rows="20" cols="80" id="sql" name="sql" class="required"
						onblur="toCheckSql()">${bean.results.sql }</textarea>
				</dd>

				<span style="color: red" id="errorSpan"></span>

			</dl>


<%-- 			<dl>
				<dt>存储过程参数列表：</dt>
				<dd>
					<table class="list" width="100%">
						<thead>
							<tr>
								<th>参数角标</th>
								<th>参数类型</th>
								<th>参数值</th>
							</tr>
						</thead>
						<tbody id="tbody">
							<c:forEach items="${bean.parameters}" var="parameters"
								varStatus="status">
								<tr>
									<td><input type="text" id="parameters.id"
										name="parameters[0].id" value="${parameters.id}" /></td>
									<td><select id="parameters.type" name="parameters[0].type"
										class="combox">
											<c:forEach items="${parameterType}" var="parameterType">
												<option value="${parameterType.key}"
													<c:if test="${parameterType.key==parameters.type}">selected="selected" </c:if>>${parameterType.value}</option>
											</c:forEach>
									</select></td>
									<td><input type="text" id="parameters.value"
										name="parameters[0].value" value="${parameters.value}" /></td>
								</tr>

							</c:forEach>
						</tbody>
					</table>
				</dd>
			</dl>

 --%>
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
	var flag = false;
	var oldSqlStr = "";
	var oldDatasource = "";
	var oldResultType = "";
	var parameterType='${parameterType}';
	var errorStr = "当前输入的SQL存在问题，请检查后再保存！";
	var partErrorStr="当前存储过程参数不一致";
	function  toCheckSql(stat) {
		var sqlStr = $("#sql").val().replace(/[\r\n]/g, " ");
		var datasource = $('#datasource option:selected').val();
		var resultType= $('#resultType option:selected').val();
		if (sqlStr != null && sqlStr != "" && datasource != null
				&& datasource != ""&&resultType!=null&&resultType!="") {
			if (sqlStr != oldSqlStr || datasource != oldDatasource || resultType != oldResultType) {
				oldSqlStr=sqlStr;
				oldDatasource=datasource;	
				oldResultType=resultType;
				var bean={
						results:{sql:sqlStr,},
						datasource:datasource,
						resultType:resultType,
				};
		
				var jsonStr=JSON.stringify(bean);
			 $.ajax({
		            type: "post",
		            async:false,
		            url: "<%=basePath%>admin/config/toCheckSql",
					data : {
						jsonStr : jsonStr
					},
					datatype : "json",
					success : function(result) {
						if (result == "1") {
							flag = true;
							toShowStr("");
						} else if (result == "-1"){
							flag = false;
							toShowStr(partErrorStr);
						}else {
							flag = false;
							toShowStr(errorStr);
						}
					}
				}); 
			}
		}
	}

	function toShowStr(str) {
		$("#errorSpan").text(str);
	}

	function toSave() {
		toCheckSql(true);
		if (flag) {
			$("#errorSpan").text("");
			$("#myForm").submit();
		} else {
			$("#errorSpan").text(errorStr);
		}
	}


</script>


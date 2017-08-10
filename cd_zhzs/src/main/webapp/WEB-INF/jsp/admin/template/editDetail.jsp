<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<div class="pageContent" >
	<form method="post" action="${basePath }admin/template/editDetail"  class="pageForm required-validate" onsubmit="return iframeCallback(this,navTabAjaxDone);">
		<input type="hidden" value="${excelTemplate.name }" name="templateName"/>
		<input type="hidden" value="${excelTemplate.tableName }" name="tableName"/>
		<div class="pageFormContent" layoutH="57">
		<fieldset>
			<legend>基本信息</legend>
			<dl>
				<dt>数据源：</dt>
				<dd>
					<select name="datasource" class="required" >
						<c:forEach var="item" items="${datasourceList}">   
						<option value="${item }" <c:if test="${ excelTemplate.datasource eq item}"> selected="selected"</c:if>>${item }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>模板名字：</dt>
				<dd><input type="text" name="templateName" class="required readonly"  value="${excelTemplate.name }"  readonly="true"/></dd>
			</dl>
			<!--
			<dl>
				<dt>上报周期：</dt>
				<dd>
					<select name="reportPeriod" class="required" >
						<c:forEach var="item" items="${periodType}">   
						<option value="${item.key }"<c:if test="${ excelTemplate.zq eq item.key}"> selected="selected"</c:if>>${item.value }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			-->
			<dl>
				<dt>过期天数：</dt>
				<dd><input type="text" name="reportPassDate" class="digits required"  value="${excelTemplate.zqgqsj }"/></dd>
			</dl>
			<dl>
				<dt>编号规则：</dt>
				<dd><input type="text" name="idRule" value="1111yyyyMMdd01" value="${excelTemplate.idcode }"/></dd>
			</dl>
			<dl>
				<dt>模板描述：</dt>
				<dd><input type="text" name="templateDesc"  style="width:300px;"  value="${excelTemplate.desc }"/></dd>
			</dl>
			<div class="divider"></div>
			<p>编号规则：前二位是地区编码，2-3位是部门编码，后2位是插入顺序</p>
			</fieldset>
			<fieldset>
			<legend>设置对应关系</legend>
			<table class="table" width="90%" >
				<thead>
					<tr>
						<th>excel列数</th>
						<th>中文字段名</th>
						<th>字段名</th>
						<th>数据类型</th>
						<th>长度(字符型需填写)</th>
						<th>是否为关键字段</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="colObj" items="${excelTemplate.cols}" varStatus="status">
					<tr>
						<td>${status.index +1}</td>
						<td><input type="hidden" value="${colObj.name }" name="excelColName"/>${colObj.name }</td>
						<td><dl><dd><input name="colName${status.index}" type="text"  class="required readonly"  value="${colObj.columnName }" readonly="true"></dd></dl></td>
						<td>
							<select name="colType" class="colType disabled" onchange="changeColType(this,'${status.index}');"  disabled="true">
								<c:forEach var="item" items="${colType}">   
								<option value="${item.key }" <c:if test="${ colObj.colType eq item.key}"> selected="selected"</c:if>>${item.value }</option>
								</c:forEach>
							</select>
						</td>
						<td><dl><dd><input name="colLength${status.index}" id="colLength${status.index}"type="text"  class="digits required readonly"   value="${colObj.colLength }"  readonly="true"></dd></dl></td>
						<td>
							<select name="isMain">
								<option value="0" <c:if test="${ colObj.isMain eq 0}"> selected="selected"</c:if>>否</option>
								<option value="1" <c:if test="${ colObj.isMain eq 1}"> selected="selected"</c:if>>是</option>
							</select>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			</fieldset>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
function changeColType(obj,index){
	if(obj.value==1){
		$("#colLength"+index).addClass("required").removeClass("readonly");
		$("#colLength"+index).attr("readOnly",'false');
	}else{
		$("#colLength"+index).removeClass("required").addClass("readonly");
		$("#colLength"+index).attr("readOnly",'true');
	}
}
</script>
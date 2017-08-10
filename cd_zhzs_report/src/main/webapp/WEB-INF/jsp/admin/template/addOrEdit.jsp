<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<div class="pageContent">
	<form method="post" action="${basePath }admin/template/saveOrUpdate"
		class="pageForm required-validate"
		onsubmit="return iframeCallback(this,navTabAjaxDone);">
		<input type="hidden" value="${excelTemplate.tableName }"
			name="tableName" /> <input type="hidden" name="type" value="${type}" />
		<div class="pageFormContent" layoutH="57">
			<fieldset>
				<legend>基本信息</legend>
				<dl>
					<dt>数据源：</dt>
					<dd>
						<select name="datasource" class="required">
							<c:forEach var="item" items="${datasourceList}">
								<option
									<c:if test="${item.id==excelTemplate.datasource }">selected="selected"</c:if>
									value="${item.id }">id：${item.id }&nbsp;&nbsp;--&nbsp;&nbsp;名称：${item.name }</option>
							</c:forEach>
						</select>
					</dd>
				</dl>
				<dl>
					<dt>模板名字：</dt>
					<dd>
						<input type="text" name="name" class="required"
							value="${excelTemplate.name }" style="width: 190px;"
							<c:if test="${type!=1 }">readonly="readonly"</c:if> />
					</dd>
				</dl>
				<dl>
					<dt>过期天数：</dt>
					<dd>
						<input type="text" name="zqgqsj" class="digits required"
							value="${excelTemplate.zqgqsj}" />
					</dd>
				</dl>
				<dl>
					<dt>编号规则：</dt>
					<dd>
						<input type="text" name="idcode" value="${excelTemplate.idcode}" />
					</dd>
				</dl>
				<dl>
					<dt>开始行数：</dt>
					<dd>
						<input type="text" name="start" class="digits"
							value="${excelTemplate.start}" />
					</dd>
				</dl>
				<dl>
					<dt>结束行数：</dt>
					<dd>
						<input type="text" name="end" class="digits"
							value="${excelTemplate.end}" />
					</dd>
				</dl>
				<dl>
					<dt>模板描述：</dt>
					<dd>
						<input type="text" name="desc" style="width: 300px;"
							value="${excelTemplate.desc}" />
					</dd>
				</dl>
				<div class="divider"></div>
				<p>编号规则：前二位是地区编码，2-3位是部门编码，后2位是插入顺序</p>
			</fieldset>
			<fieldset>
				<legend>设置对应关系</legend>
				<table class="list" width="90%">
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
						<c:forEach var="cols" items="${excelTemplate.cols}"
							varStatus="status">
							<tr>
								<td>${status.index +1}</td>
								<td><input name="cols[${status.index }].name" type="text"
									class="required" value="${cols.name }"
									<c:if test="${type!=1}">readonly="readonly"</c:if>></td>
								<td><input name="cols[${status.index }].columnName"
									type="text" class="required" value="${cols.columnName }"
									<c:if test="${type!=1}">readonly="readonly"</c:if>></td>
								<td><select name="cols[${status.index }].colType"
									<c:if test="${type!=1}">  disabled="true"</c:if>
									onchange="changeColType(this,'${status.index}');"
									<c:if test="${type!=1}">class="disabled"</c:if>>
										<c:forEach var="item" items="${colType}">
											<option
												<c:if test="${item.key==cols.colType }">selected="selected"</c:if>
												value="${item.key }">${item.value }</option>
										</c:forEach>
								</select></td>
								<td><dl>
										<dd>
											<input name="cols[${status.index }].colLength"
												id="colLength${status.index}" type="text"
												value="${cols.colLength}" class="digits required"
												<c:if test="${type!=1}">readonly="readonly"</c:if>>
										</dd>
									</dl></td>
								<td><select name="cols[${status.index }].isMain"
									<c:if test="${type!=1}">  disabled="true"</c:if>>
										<option
											<c:if test="${cols.isMain==0 }">selected="selected"</c:if>
											value="0">否</option>
										<option
											<c:if test="${cols.isMain==1 }">selected="selected"</c:if>
											value="1">是</option>
								</select></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</fieldset>
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
	function changeColType(obj, index) {
		if (obj.value == 1) {
			$("#colLength" + index).addClass("required")
					.removeClass("readonly");
			$("#colLength" + index).attr("readOnly", 'false');
		} else {
			$("#colLength" + index).removeClass("required")
					.addClass("readonly");
			$("#colLength" + index).attr("readOnly", 'true');
		}
	}
</script>
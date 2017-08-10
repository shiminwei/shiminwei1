<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<div class="panelBar" align="center" style="color:red">
<!-- 	<h2 style="margin-right: 90%">FTP文件操作</h2> -->
	<h2 class="contentTitle" style="margin-right: 93%;margin-left: -2%">FTP文件上传</h2>
</div>
<div class="pageContent">
	<form id="myForm" method="post" action="${basePath}web/ftp/saveToMongoUpload?jobId=${jobId}&stepId=${stepId}&sortNum=${sortNum}"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="80">
			<dl>
				<dt>动作名称：</dt>
				<dd>
					<input type="text" name="name" maxlength="20" class="required" value="${needStep.name}"
						style="width: 200px" />
				</dd>
			</dl>
			<dl style="margin-top: 1%">
				<dt>IP地址：</dt>
				<dd>
					<input type="text" name="address" maxlength="50"   class="required" value="${needStep.address}"
						style="width: 200px" />
				</dd>
			</dl>
			<dl>
				<dt>端口号：</dt>
				<dd>
					<input name="port" type="text" id="port" class="required" style="width: 200px" value="${needStep.port}"/> 
				</dd>
			</dl>
			<dl>
				<dt>用户名：</dt>
				<dd>
					<input name="username" type="text" id="username" class="required" style="width: 200px" value="${needStep.username}"> 
				</dd>
			</dl>
			<dl>
				<dt>密码：</dt>
				<dd>
					<input name="password" type="text" id="password" class="required" style="width: 200px" value="${needStep.password}"/> 
				</dd>
			</dl>
			<dl style="margin-top: 1%">
				<dt>文件上传路径 ：</dt>
				<dd>
					<input id="file" name="filePath" type="text" style="width: 200px" class="required noRootPath" value="${name }"/><span
							style="color: red;" class="unit">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;不需要输入根目录</span>
				</dd>
			</dl>
			<dl>
				<dt>文件名后缀 ：</dt>
				<dd>
					<select name="fileNamePatterns" id="fileNamePatterns" class="combox" >
						<option value="">--请选择--</option>
						<c:forEach items="${fileNamePatternsMap}" var="map" varStatus="status">
							<option value="${map.key}" 
							<c:if test="${map.key== needStep.fileNamePatterns}">selected="selected"</c:if>>${map.value}</option>
						</c:forEach>
					</select><span style="color: red;height:21px;line-height: 21px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;可不选</span>
				</dd>
			</dl>
			<dl>
				<dt>是否将原文件删除 ：</dt>
				<dd>
					<select id="delete" name="isDelete" class="combox" style="width: 200px">
						<option value="1"<c:if test="${needStep.isDelete==1 }">selected="selected"</c:if>>是</option>
						<option value="0"<c:if test="${needStep.isDelete==0 }">selected="selected"</c:if>>否</option>					
					</select>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">提交</button>
<!-- 							<button type="button" onclick="toSave()">提交</button> -->
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
</script>



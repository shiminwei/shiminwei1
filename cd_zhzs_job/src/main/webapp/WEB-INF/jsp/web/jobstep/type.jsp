<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %>
<h2 class="contentTitle">操作类型</h2>
<div class="pageContent">
	<form method="post" action="${basePath }/web/jobstep/next" enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<input type="hidden" id="jobId" name="jobId" value="${jobId}" />
			<dl>
				<dt>操作类型：</dt>
				<dd style="margin-left:-25px">
					<select id="type" name="type" style="width: 207px" >
						<c:forEach var="entry" items="${map}">   
        					<option value="<c:out value="${entry.key}" />"><c:out value="${entry.value}" /></option>  
    					</c:forEach> 
					</select>
				</dd>
			</dl>

		</div>
		<div class="formBar">
			<ul style ="width:300px;height:120px;float:left">
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">下一步</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

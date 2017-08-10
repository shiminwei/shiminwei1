<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp" %> 
<!--<h2 class="contentTitle" >修改用户</h2>  -->
<c:choose>
	<c:when test="${actionMethod=='edit' }">
		<h2 class="contentTitle" >修改用户</h2>
	</c:when>
	<c:otherwise>
		<h2 class="contentTitle" >新增用户</h2>
	</c:otherwise>
</c:choose>
<div class="pageContent">
	
	<form method="post" action="${basePath }admin/user/${actionMethod}" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<input type="hidden" name="userId" value="${sysReportUserInfo.userId}"> 
			<dl style="margin-top:10px">
				<dt style="font-weight: 900">用户名：</dt>
				<dd>
					<input type="text" name="userName" maxlength="20" class="required" value="${sysReportUserInfo.userName }" />
					<span class="info"></span>
				</dd>
			</dl>
			<dl style="margin-top:10px">
				<dt style="font-weight: 900">部门名称：</dt>
				<dd style="margin-left:-25px">
					<select maxlength="20" class="required"  class="combox" name="departmentId" >
					<c:forEach items="${departmentList }" var="department">
					<option 
					value="${department.departmentId}" name = "departmentName"
					<c:if test="${department.departmentName == sysReportUserInfo.departmentName}">selected="selected"
					</c:if>>                                                           
					${department.departmentName }</option>
					<span class="info"></span>
					</c:forEach>
					</select>
				</dd>
			</dl>
			<c:choose>
				<c:when test="${actionMethod=='edit' }">
				<dl style="margin-top: 10px">
					<dt style="font-weight: 900">展现权限：</dt>
					<dd>
						<input name="hasWeb1" type="radio" value="1" <c:if test="${sysReportUserInfo.hasWeb == 1}">checked="checked"</c:if>/>是
						<input name="hasWeb1" type="radio" value="0" <c:if test="${sysReportUserInfo.hasWeb == 0}">checked="checked"</c:if>/>否
					</dd>
				</dl>
				<dl style="margin-top: 10px">
					<dt style="font-weight: 900">上报权限：</dt>
					<dd>
						<input name="hasReport1" type="radio" value="1" <c:if test="${sysReportUserInfo.hasReport == 1}">checked="checked"</c:if> />是
						<input name="hasReport1" type="radio" value="0" <c:if test="${sysReportUserInfo.hasReport == 0}">checked="checked"</c:if>/>否
				</dd>
				</dl>

				</c:when>
				<c:otherwise>
					<dl style="margin-top: 10px">
						<dt style="font-weight: 900">展现权限：</dt>
						<dd>
							<input name="hasWeb1" type="radio" value="1" checked="checked"/>是 <input
								name="hasWeb1" type="radio" value="0" />否
						</dd>
					</dl>
					<dl style="margin-top: 10px">
						<dt style="font-weight: 900">上报权限：</dt>
						<dd>
							<input name="hasReport1" type="radio" value="1"  checked="checked"/>是 <input
							name="hasReport1" type="radio" value="0" />否
					</dd>
					</dl>
				</c:otherwise>
			</c:choose>




			<dl style="margin-top:10px">
				<dt style="font-weight: 900">业务联系人：</dt>
				<dd>
					<input type="text" name="bussinessConcact" value="${sysReportUserInfo.bussinessConcact }"/>
				</dd>
			</dl>
			<dl style="margin-top:10px">
				<dt style="font-weight: 900">业务联系人电话：</dt>
				<dd>
					<input type="text" name="bussinessConcactPhone" value="${sysReportUserInfo.bussinessConcactPhone }"/>
				</dd>
			</dl>
			<dl style="margin-top:10px">
				<dt style="font-weight: 900">技术联系人：</dt>
				<dd>
					<input type="text" name="techConcact" value="${sysReportUserInfo.techConcact }"/>
				</dd>
			</dl>
			<dl style="margin-top:10px">
				<dt style="font-weight: 900">技术联系人电话：</dt>
				<dd>
					<input type="text" name="techConcactPhone" value="${sysReportUserInfo.techConcactPhone }"/>
				</dd>
			</dl>
			<dl style="margin-top:10px">
				<dt style="font-weight: 900">email：</dt>
				<dd>
					<input type="text" name="email" value="${sysReportUserInfo.email }"/>
				</dd>
			</dl>
			<dl style="margin-top:10px">
				<dt style="font-weight: 900">单位地址：</dt>
				<dd>
					<input type="text" name="address" style="width:200px;" value="${sysReportUserInfo.address }"/>
				</dd>
			</dl>
			</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

<script type="text/javascript">

function validateCallback(form, callback) {  
    var $form = $(form);  
        //验证表单如果有非法字段 返回  
    if (!$form.valid()) {  
        return false;  
    }  
        //Ajax向后台提交数据  
    $.ajax({  
        type: form.method || 'POST',  
        url:$form.attr("action"),  
        //获取表单的内容数据  
                data:$form.serializeArray(),  
        dataType:"json",  
        cache: false,  
        //执行回调函数  
                success: callback || DWZ.ajaxDone,  
        error: DWZ.ajaxError  
    });  
        //保证不会通过普通表单提交  
        return false;  
}  

function customvalidXxx(element){
	if ($(element).val() == "xxx") return false;
	return true;
}
</script>

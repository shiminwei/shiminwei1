<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<h2 class="contentTitle">用户角色配置</h2>


<div class="pageContent">

	<form method="post" id="myFrom"
		action="${basePath}admin/user/saveOrUpdateUserRole"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent" layoutH="98">
			<div class="divider"></div>

			<c:forEach items="${roleList }" var="roleList">

				<label><input type="checkbox" name="roleId"
					value="${roleList.roleId }"
					<c:if test="${roleList.isHaveRole==1 }"> checked="checked"</c:if> />${roleList.roleName }</label>
			</c:forEach>
		</div>
		<div class="formBar">
			<label style="float: left"><input type="checkbox"
				class="checkboxCtrl" group="roleId" />全选</label>
			<ul>
				<li style="margin-left: 20px"><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="toSave()">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		<%-- 隐藏值 --%>
		<input type="hidden" name="userId" value="${bean.userId }" /> <input
			type="hidden" id="roleIds" name="roleIds" value="" />

	</form>

</div>


<script type="text/javascript">
	function toSave() {

		var obj = document.getElementsByName('roleId');
		var roleIds = '';
		for (var i = 0; i < obj.length; i++) {
			if (obj[i].checked)
				roleIds += obj[i].value + ',';
		}
		document.getElementById("roleIds").value = roleIds;
		$('#myFrom').submit();
	}
</script>


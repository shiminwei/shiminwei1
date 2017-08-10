<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<h2 class="contentTitle">菜单增改</h2>


<div class="pageContent">

	<form method="post" action="${basePath}admin/role/saveOrUpdate"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">

			<dl>
				<dt>角色名称：</dt>
				<dd>
					<input type="text" id="roleName" name="roleName" maxlength="20" value="${bean.roleName }"
						class="required" style="width: 200px; height:" onblur="checkSameRolename();" />
					<strong><span id="checkRolename" style="font-size:10px;line-height: 20px;color: red"></span></strong>
				</dd>
			</dl>
			<dl>
				<dt>是否可用：</dt>
				<dd style="margin-left:-25px">
					<select name="status" style="width: 207px"  >
						<option value="1" <c:if test="${bean.status==1 }">selected="selected"</c:if>>正常使用</option>
						<option value="0" <c:if test="${bean.status==0 }">selected="selected"</c:if>>停止使用</option>
					</select>
				</dd>
			</dl>

			<dl>
				<dt>角色描述：</dt>
				<dd>
					<textarea rows="20" cols="80" name="roleDesc">${bean.roleDesc }</textarea>
				</dd>
			</dl>
			 <%--隐藏的值 --%>
			 <input type="hidden" id="roleId" name="roleId" value="${bean.roleId }" />

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
	function customvalidXxx(element) {
		if ($(element).val() == "xxx")
			return false;
		return true;
	}

	function checkSameRolename(){
		var roleId = document.getElementById("roleId").value.trim();
		var roleName = document.getElementById("roleName").value.trim();
		if(roleName == '' || roleName == null){
			document.getElementById("checkRolename").innerHTML = "";
			return ;
		}
		$.ajax({
		    type: "POST",
		    url: "role/checkSameRolename",
		    data: {'roleId':roleId,'roleName':roleName},
		    dataType: "text",
		    success: function(result){
		    	if (result=="true") {
		    		document.getElementById("checkRolename").innerHTML = "&nbsp;&nbsp;&nbsp角色名称已存在,请重新输入";
		    		document.getElementById("roleName").value ="";
				}
		    	else{
					document.getElementById("checkRolename").innerHTML = "&nbsp;&nbsp;&nbsp可以使用";
				}	    	
		    }
		});
    }
</script>


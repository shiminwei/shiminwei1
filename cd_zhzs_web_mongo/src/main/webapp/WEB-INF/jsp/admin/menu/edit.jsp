<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<h2 class="contentTitle">菜单增改</h2>


<div class="pageContent">

	<form id="myFrom" method="post" action="${basePath}admin/webMenu/saveOrUpdate?menuLevel=${MenuLevel}"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>菜单名称：</dt>
				<dd>
					<input type="text"  id="district.functionName" maxlength="30" id="name" name="name" value="${bean.name }"
						class="required" style="width: 200px; height:" />
				</dd>
			</dl>
			<dl>
				<dt>选择功能：</dt>
				<dd>
					<select  id="mySelect" style="width: 207px" class="combox"  onchange="toAddName()">
						<option value="无功能只是菜单" <c:if test="${bean.menuType==1}">selected="selected"</c:if>>无功能只是菜单</option>
						<option value="添加功能" <c:if test="${bean.menuType==2}">selected="selected"</c:if>>添加功能</option>
						<option value="添加URL超链接"<c:if test="${bean.menuType==3}">selected="selected"</c:if>>添加URL超链接</option>
					</select>
				</dd>
			</dl>
			<dl id="myDiv" style="<c:if test="${bean.menuType !=2 }">display: none;</c:if>">
				<dt>选择功能：</dt>
				<dd>
					<input   id="district.functionName"  type="text" readonly="readonly" value="${configBean.name }"/>
					<a class="btnLook" href="config/lookList" lookupGroup="district" rel="asdasd">查找带回</a>	
				</dd>
			</dl>
			<dl id="myDiv2" style="<c:if test="${bean.menuType !=3 }">display: none;</c:if>">
				<dt>添加URL链接地址：</dt>
				<dd>
					<input type="text" id="content"  style="width: 181px;" name="url" value="${bean.functionId }"/>
				</dd>
			</dl>
			<%--隐藏的值 --%>
			<input type="hidden" name="menuType" value="${bean.menuType }" /> <input
				type="hidden" name="menuId" value="${bean.menuId }" /> <input
				type="hidden" name="code" value="${bean.code }" />
				<input type="hidden" name="parentCode" value="${oldBean.parentCode }" />
				<input type="hidden" name="parentName" value="${oldBean.parentName }" />
				<input type="hidden" name="orderNumber" value="${bean.orderNumber }" />
				<input  type="hidden"  id="district.functionId" name="functionId"  value="${bean.functionId}"/>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button  type="button" onclick="toSave()">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>

</div>


<script type="text/javascript">

function toAddName() {
	var txt=$('#mySelect option:selected').text();
	if(txt=="添加功能"){
		$("input[name='menuType']").val(2);
		$("#myDiv").show();
		$("#myDiv2").hide();
	}else if(txt=="添加URL超链接"){
		$("input[name='menuType']").val(3);
		$("#myDiv").hide();
		$("#myDiv2").show();
	}else{
		$("input[name='menuType']").val(1);
		$("#myDiv").hide();
		$("#myDiv2").hide();
	}
}

function toSave(){
	var txt=$('#mySelect option:selected').text();
	if(txt=="添加功能"){
	}else{
		$("#district.functionId").val("");
	}
	$("#myFrom").submit();
}
</script>


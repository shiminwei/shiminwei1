<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<div class="pageContent" style="width: 100%; height:100%;overflow: scroll;">
<form method="post" id="myFrom"
			action="${basePath }/admin/menuRole/saveOrUpdate"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,navTabAjaxDone)">
	<input type="hidden" id="menuId" name="menuId" value="${oldMenuIds}" />
	<input type="hidden" name="roleId" value="${roleId }" />
	<div class="pageFormContent nowrap" layoutH="65" style=" margin: 5px; overflow: scroll; border: solid 1px #CCC; line-height: 21px; background: #FFF;">
		<ul class="tree treeFolder treeCheck expand" id="myMenu">
			<c:forEach items="${oneList }" var="oneList">
				<li><a style="font-weight: 900" tvalue="${oneList.menuId }"
					<c:if test="${oneList.isHave==1 }">checked="checked"</c:if>>${oneList.name }</a>
					<ul>
						<c:forEach items="${twoList }" var="twoList">
							<c:if test="${oneList.code==twoList.parentCode }">
								<li><a href="#" onclick="" name="name"
									tvalue="${twoList.menuId }"
									<c:if test="${twoList.isHave==1 }">checked="checked"</c:if>>${twoList.name }</a>
									<ul>
										<c:forEach items="${threeList }" var="threeList">
											<c:if test="${twoList.code==threeList.parentCode }">
												<li><a href="#" onclick="" name="name"
													tvalue="${threeList.menuId }"
													<c:if test="${threeList.isHave==1 }">checked="checked"</c:if>>${threeList.name }</a></li>
											</c:if>
										</c:forEach>
									</ul></li>
							</c:if>
						</c:forEach>
					</ul></li>
			</c:forEach>
		</ul>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="toSave()">保存</button>
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
		function toSave() {
			var ckboxStr = $("div.checked input:first-child");
			var len = ckboxStr.length;
			var str1 = "";
			while (len--) {
				str1 += ckboxStr[len].value + ",";
			}
// 			alert(str1);
			var partckboxStr = $("div.indeterminate input:first-child");
			var partlen = partckboxStr.length;
			var partstr = "";
			while (partlen--) {
				partstr += partckboxStr[partlen].value + ",";
			}
// 			alert(partstr);
 	 		var str = str1+partstr;
			document.getElementById("menuId").value = str;
			$('#myFrom').submit();  
		}
	</script>
</body>
</html>
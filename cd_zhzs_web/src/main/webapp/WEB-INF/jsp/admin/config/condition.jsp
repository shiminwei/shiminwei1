<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<!-- <h2 class="contentTitle" style="margin-bottom: 0px">条件区域管理</h2> -->
<div class="panelBar" align="center" style="color:red">
	<h2 class="contentTitle" style="margin-right: 92%;margin-left: -2%">条件区域管理</h2>
</div>
<div class="pageContent" style="width: auto; overflow: x:scroll">

	<div class="panelBar" style="border-width: 0px 0px 1px 0px;">
		<ul class="toolBar">
			<li><a class="add"
				href="${basePath }admin/config/toEditCondition?functionId=${bean.functionId}"
				target="dialog" width="600" height="400" rel="conditionList"><span>新增条件</span></a></li>
		</ul>
	</div>
	<form id="myForm" method="post"
		action="${basePath}admin/config/saveOrUpdate"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone)">
		<table class="list" width="100%" layoutH="85">
			<thead>
				<tr>
					<th width="15%" align="center">标识ID</th>
					<th width="15%" align="center">查询显示名称</th>
					<th width="10%" align="center">查询类型</th>
					<th width="6%" align="center">显示长度</th>
					<th width="15%" align="center">默认值</th>
					<th width="15%" align="center">传值到子页面</th>
					<th width="6%" align="center">是否在标题显示</th>
					<th width="6%" align="center">是否隐藏显示</th>
					<th width="20%" align="center">操作</th>
				</tr>
			</thead>
			<tbody style="margin-left: 0px; width: 132px">
				<c:forEach var="list" items="${bean.conditionBeans}"
					varStatus="status">
					<tr>
						<td width="center" align="center" ><input style="margin-left: 15px" type="text"
							name="conditionBeans[${status.index }].conditionId"
							value="${list.conditionId}" /></td>
						<td width="center" align="center"><input type="text"
							name="conditionBeans[${status.index }].dispname"
							value="${list.dispname}" /></td>
						<td width="center" align="center"><select
							name="conditionBeans[${status.index }].disptype" class="combox"
							style="margin-left: 0px;">
								<option value="1"
									<c:if test="${ list.disptype == 1}"> selected="selected" </c:if>>文本框</option>
								<option value="2"
									<c:if test="${ list.disptype == 2}"> selected="selected" </c:if>>年份下拉框</option>
								<option value="3"
									<c:if test="${ list.disptype == 3}"> selected="selected" </c:if>>年月下拉框</option>
								<option value="4"
									<c:if test="${ list.disptype == 4}"> selected="selected" </c:if>>税种下拉框</option>
								<option value="5"
									<c:if test="${ list.disptype == 5}"> selected="selected" </c:if>>月份下拉框</option>
									<option value="6"
									<c:if test="${ list.disptype == 6}"> selected="selected" </c:if>>收款国库下拉框</option>
									<option value="7"
									<c:if test="${ list.disptype == 7}"> selected="selected" </c:if>>入库级次下拉框</option>
										<option value="8"
									<c:if test="${ list.disptype == 8}"> selected="selected" </c:if>>产业下拉框</option>
									<option value="9"
									<c:if test="${ list.disptype == 9}"> selected="selected" </c:if>>行业大类下拉框</option>
								<option value="10"
									<c:if test="${ list.disptype == 10}"> selected="selected" </c:if>>所属区域下拉框</option>
									<option value="11"
									<c:if test="${ list.disptype == 11}"> selected="selected" </c:if>>是否下拉框</option>
									<option value="12"
									<c:if test="${ list.disptype == 12}"> selected="selected" </c:if>>资金来源下拉框</option>
						</select></td>
						<td align="center"><select
							name="conditionBeans[${status.index }].width" class="combox"
							style="margin-left: 0px;">
								<option value="20"
									<c:if test="${ list.width == 20}"> selected="selected" </c:if>>20</option>
								<option value="30"
									<c:if test="${ list.width == 30}"> selected="selected" </c:if>>30</option>
								<option value="40"
									<c:if test="${ list.width == 40}"> selected="selected" </c:if>>40</option>
						</select></td>

						<td align="center"><input type="text"
							name="conditionBeans[${status.index }].morenzhi"
							value="${list.morenzhi}" /></td>
						<td align="center"><input type="text"
							name="conditionBeans[${status.index }].isByValue"
							value="${list.isByValue}" /></td>
						<td align="center"><select class="combox" name="conditionBeans[${status.index }].isShowTitile">
								<option value="0"
									<c:if test="${ list.isShowTitile == 0}"> selected="selected" </c:if>>否</option>
								<option value="1"
									<c:if test="${ list.isShowTitile == 1}"> selected="selected" </c:if>>是</option>
						</select></td>
						
						
						<td align="center"><select class="combox" name="conditionBeans[${status.index }].isHidden">
								<option value="0"
									<c:if test="${ list.isHidden == 0}"> selected="selected" </c:if>>否</option>
								<option value="1"
									<c:if test="${ list.isHidden == 1}"> selected="selected" </c:if>>是</option>
						</select></td>
						<td align="center"><a
							href="${basePath}admin/config/toUpOrDown?id=${bean.functionId}&conditionId=${list.conditionId}&type=1"
							class="up" target="ajaxTodo" rel="conditionList"
							style="color: green">上移</a> <a style="margin-left: 10px"
							target="ajaxTodo" rel="conditionList"
							href="${basePath}admin/config/toUpOrDown?id=${bean.functionId}&conditionId=${list.conditionId}&type=2"
							class="down" style="color: red;">下移</a> <a
							style="margin-left: 10px; color: blue" title="删除该条件"
							target="ajaxTodo" rel="conditionList"
							href="${basePath}admin/config/toDelCondition?id=${bean.functionId}&conditionId=${list.conditionId}">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
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
		<%-- --%>
		<input type="hidden" name="functionId" value="${bean.functionId}" />
		<input type="hidden" name="type" value="${type}" /> <input
			type="hidden" name="name" value="${bean.name}" /> <input
			type="hidden" name="datasource" value="${bean.datasource}" /> <input
			type="hidden" name="conditioncolumn" value="${bean.conditioncolumn}" />
		<input type="hidden" name="resultType" value="${bean.resultType}" />
		<input type="hidden" name="sql" value="${bean.results.sql}" />
	</form>

</div>
<script type="text/javascript">
	function toSave() {
		var flag = true;
		var dispname = document.getElementsByName("dispname");
		for (var i = 0; i < dispname.length; i++) {
			if (dispname[i].value == "" || dispname[i].value == null) {
				flag = false;
				alert("查询显示名称不能为空！！");
				break;
			}
		}
		if (flag) {
			$("#myForm").submit();
		}
	}
</script>

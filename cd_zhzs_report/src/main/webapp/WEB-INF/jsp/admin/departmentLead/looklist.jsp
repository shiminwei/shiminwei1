<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<form id="pagerForm" method = "post" action="${basePath }admin/departmentLead/looklist?status=1">
	<input type="hidden" name="pageNum" value="${pageList.pageNum}"/>
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}"/>	
</form>
<div class = "pageHeader">
	<form id="pagerForm" rel="pagerForm" method="post" action="${basePath }admin/departmentLead/looklist?status=1" onsubmit="return dwzSearch(this, 'dialog');">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label style="font-weight: 900">前置机ID&nbsp;&nbsp;&nbsp;:</label>
					<input type="text" name="leadId" id="searchForm" name="searchForm" value="${bean.leadId }"/>
				</li>
				<dd><div  style="margin-left:-30px" class="button"><div class="buttonContent"><button>查询</button></div></div></dd>
				<dd><div style="margin-left: 17px" class="button"><div class="buttonContent"><button onclick="toChose()">确定选择</button></div></div></dd>
			</ul>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="118" targetType="dialog">
		<thead>
			<tr>
				<th align="center" width="6%"><input type="checkbox" group="choseValue" class="checkboxCtrl"/></th>
				<th align="center" width="12%">前置机ID</th>
				<th align="center" width="25%">地区部门名称</th>
				<th align="center" width="25%">状态</th>
			</tr>
		</thead>	
		<tbody>
			<c:forEach items="${pageList.result }" var="list">
				<tr>
					<td align="center"><input name="choseValue" type="checkbox" value="${list.leadId}"/></td>
					<td align="center">${list.leadId }</td>
					<td align="center">${list.areaName }-${list.departmentName }</td>
					<td align="center" <c:if test = "${list.status ==0}">style="color:red" </c:if>>
					${list.status == 1?'可用':'不可用'}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>	
<!-- 分页 -->
<%@include file="/common/pageDialog.jsp"%>
</div>
<script type="text/javascript">
function toChose() {
	var choseValue = document.getElementsByName("choseValue");
	var size = 0;
	var leadId = "";
	for (var i = 0; i < choseValue.length; i++) {
		if (choseValue[i].checked) {
			leadId += choseValue[i].value+",";
			size++;
		}
	}
	if (size >= 1) {
		$.bringBack({
			'leadId' : leadId.substring(0, leadId.length-1)
		});
	} else {
		alert("请至少选择一个选项！");
	}
}
</script>
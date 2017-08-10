<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<form id="pagerForm" method="post"
	action="${basePath }admin/config/lookListConfig">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="functionId" value="${bean.functionId}" /> <input
		type="hidden" name="name" value="${bean.name}" />
		<input
		type="hidden" name="index" value="${index}" />
</form>



<div class="pageHeader">
		<form id="pagerForm" rel="pagerForm" method="post" action="${basePath }admin/config/lookList" onsubmit="return dwzSearch(this, 'dialog');">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>功能ID：</label> <input type="text" name="functionId"
					value="${bean.functionId }" /></li>
				<li><label>功能名称：</label> <input type="text" name="name"
					value="${bean.name }" /></li>
			<dd><div  style="margin-left:6px" class="button"><div class="buttonContent"><button>查询</button></div></div></dd>
			<dd><div style="margin-left: 6px" class="button"><div class="buttonContent"><button onclick="toChose()">确定选择</button></div></div></dd>
			</ul>
		<input type="hidden" name="index" value="${index}" />
		</div>
	</form>
</div>
<div class="pageContent" style="width: 900px; overflow: x:scroll">
	<table class="table" width="100%" layoutH="118" targetType="dialog">
		<thead>
			<tr>
				<th align="center"></th>
				<th width="center" align="center">序号</th>
				<th width="center" align="center">ID</th>
				<th width="center" align="center">功能名称</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="list" items="${pageList.result}" varStatus="status">
				<tr ondblclick="javascript:$.bringBack({'${reId}':'pageList/public?functionid=${list.functionId}','${reName}':'${list.name}'})" >
					<td align="center"><input name="choseValue" type="checkbox"
						value="${list.functionId}@@${list.name }"></td>
					<td>${ status.index + 1}</td>
					<td>${list.functionId }</td>
					<td>${list.name }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="panelBar">
	<div class="pages" >
		<span>显示</span>  <span>10条，共${pageList.totalCount}条</span>
	</div>
	<div class="pagination" targetType="dialog"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>

<script type="text/javascript">
	function toChose() {

		var choseValue = document.getElementsByName("choseValue");
		var size = 0;
		var str = "";
		for (var i = 0; i < choseValue.length; i++) {
			if (choseValue[i].checked) {
				str = choseValue[i].value;
				size++;
			}
		}
		if (size == 1) {
			var name = str.split("@@");
			$.bringBack({
				'${reId}' : name[0],
				'${reName}' : name[1]
			});
		} else {
			alert("请选择一个选项！");
		}

	}
</script>
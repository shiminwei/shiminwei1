<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<%@ taglib prefix="zd" uri="../../../../WEB-INF/zdtag.tld"%>
<form id="pagerForm" method="post" action="${basePath }web/dataQuery/list" onsubmit="return navTabSearch(this);">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageContent">
	<table class="list" width="100%" layoutH="28">
		<thead>
			<tr>
				<th  align="center">序号</th>
				<th  align="center">模板名称</th>
				<th  align="center">报送周期</th>
				<th  align="center">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="list" items="${pageList.result}"  varStatus="status">
			<tr >
				<td   align="center" >${status.index+1}</td>
				<td   align="center" >${list.name}</td>
				<td   align="center" ><zd:show  type="zq" value="${list.zq}"></zd:show ></td>
				<td   align="center">
					<a class="button" href="${basePath }web/dataQuery/toQueryDate?name=${list.name}&zq=${list.zq}" target="navTab" rel="toReportQueryData" ><span>查询历史数据</span></a>
				</td>

			</tr>
		</c:forEach>
		</tbody>
	</table>
<div class="panelBar">
	<div class="pages">
		<span>显示</span> <select class="combox" name="numPerPage"
			onchange="navTabPageBreak({numPerPage:this.value,pageNum:1})">
			<option value="20">20</option>
			<option value="50">50</option>
			<option value="100">100</option>
			<option value="200">200</option>
		</select> <strong><script>
			$("select[name='numPerPage']").val('${pageList.numPerPage}');
		</script></strong> <span>条，共${pageList.totalCount}条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>

</div>

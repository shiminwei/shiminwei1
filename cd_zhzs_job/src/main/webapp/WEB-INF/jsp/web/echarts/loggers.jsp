<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }web/tologgers" onsubmit="return navTabSearch(this);">
		<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> 
		<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
		<input type="hidden" name="date" value="${date}" />  
</form>
<form onsubmit="return navTabSearch(this);" action="${basePath }web/tologgers" method="post" id="jobsteplist" name="searchForm">
<div class="pageHeader">
	<div class="searchBar">
	<ul class="searchContent">
		<li style="width: 370px">
		    <label style="width: 80px">执行日期：</label>
		    <input type="text" id="date" name="date" value="${date}" class="date" readonly ="readonly" style="width: 90px ; margin-left:0px;"/>
		</li>
	</ul>
	</div>
</div>
<div class="panelBar" align="center" style="color:red">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit">
					<span>查询数据</span></button></a></li>
	</ul>
</div>
</form>  
<div class="pageContent">
	<table class="list" width="100%" layoutH="91">
		<thead>
			<tr>
				<th align="center">序号</th>
				<th align="center">任务ID</th>
				<th align="center">调度名称</th>
				<th align="center">执行日期</th>
				<th align="center">运行状态</th>
				<th align="center">运行结果</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="list" items="${pageList.result}" varStatus="status">
			<tr >
				<td align="center">${status.index+1 }</td>
				<td align="center">${list.jobId}</td>
				<td align="center">${list.name}</td>
				<td align="center">${fn:substring(list.executioTime, 0, 10)}</td>
				<c:if test="${list.result == 0}"><td align="center">异常</td></c:if>
				<c:if test="${list.result == 1}"><td align="center">正常</td></c:if>
				<td align="center">${list.content}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
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
<script type="text/javascript">
</script>
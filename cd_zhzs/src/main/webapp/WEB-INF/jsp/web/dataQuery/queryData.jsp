<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<%@ taglib prefix="zd" uri="../../../../WEB-INF/zdtag.tld"%>
<form id="pagerForm" method="post"
	action="${basePath }web/dataQuery/toQueryDate?type=1&name=${bean.name}"
	onsubmit="return navTabSearch(this);">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
		<input
		type="hidden" name="yearColName" value="${bean.yearColName}" />
				<input
		type="hidden" name="monthColName" value="${bean.monthColName}" />
		<input
		type="hidden" name="zq" value="${bean.zq}" />
		
</form>


<form method="post" name="searchForm" id="searchForm"
	action="${basePath }web/dataQuery/toQueryDate?type=1&name=${bean.name}&zq=${bean.zq}"
	onsubmit="return navTabSearch(this);">
<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>报送周期时间选择：</label> <select class="combox"
					name="yearColName">

						<c:forEach items="${year }" var="year">
							<option value="${year.key }"
								<c:if test="${year.key==nowYear }">selected="selected"</c:if>>${year.value }</option>
						</c:forEach>
				</select> 
				<c:if test="${month != null && month.size() > 0 }">
				<select class="combox" name="monthColName">
						<c:forEach items="${month }" var="month">
							<option value="${month.key }"
								<c:if test="${month.key==nowMonth }">selected="selected"</c:if>>${month.value }</option>
						</c:forEach>
				</select>
				</c:if></li>
			</ul>
		</div>
</div>
<div class="panelBar" style="border-width: 0px 0px 1px 0px;">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" id="abyexport" href="#"
			onclick="cleanForm('searchForm','navTab')"><span style="">清空查询</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="91">
		<thead>
			<tr>
			<th align="center">序号</th>
				<c:forEach var="cols" items="${pageList.queryBean.cols}"
					varStatus="status">
					<th align="center">${cols.name }</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="resultListOd" items="${resultList}"
				varStatus="status">
				<tr>
				<td align="center">${status.index+1}</td>
					<c:forEach var="list" items="${resultListOd}" varStatus="status1">
						<td align="center">${list}</td>
					</c:forEach>
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

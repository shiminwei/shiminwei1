<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<%@ taglib prefix="zd" uri="../../../../WEB-INF/zdtag.tld"%>
<form id="pagerForm" method="post"
	action="${basePath }admin/data/queryReportInfo?zq=${chileBean.zq}">
	<input type="hidden" name="pageNum" value="${pageList.pageNum}" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="beginYear" value="${chileBean.beginYear}" /> <input
		type="hidden" name="beginMonth" value="${chileBean.beginMonth}" /> <input
		type="hidden" name="endYear" value="${chileBean.endYear}" /> <input
		type="hidden" name="endMonth" value="${chileBean.endMonth}" />
</form>


<form onsubmit="return navTabSearch(this);"
	action="${basePath }admin/data/queryReportInfo?zq=${chileBean.zq}&templateName=${log.templateName }&deparmentId=${log.deparmentId }"
	method="post">
<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>开始时间：</label> <select class="combox"
					name="beginYear">
						<c:forEach items="${year }" var="year">
							<option value="${year.key }"
								<c:if test="${year.key==chileBean.beginYear}"> selected="selected"
	
	
	</c:if>>${year.value }</option>
						</c:forEach>
				</select> <select class="combox" name="beginMonth">
						<c:forEach items="${month }" var="month">
							<option value="${month.key }"
								<c:if test="${month.key==chileBean.beginMonth}"> selected="selected"</c:if>>${month.value }</option>
						</c:forEach>
				</select></li>
				<li><label>结束时间：</label> <select class="combox" name="endYear">
						<c:forEach items="${year }" var="year">
							<option value="${year.key }"
								<c:if test="${year.key==chileBean.endYear}"> selected="selected"</c:if>>${year.value }</option>
						</c:forEach>
				</select> <select class="combox" name="endMonth">
						<c:forEach items="${month }" var="month">
							<option value="${month.key }"
								<c:if test="${month.key==chileBean.endMonth}"> selected="selected"</c:if>>${month.value }</option>
						</c:forEach>
				</select></li>
			</ul>
		</div>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" id="abyexport" href="#"
			onclick="cleanForm('searchForm','navTab')"><span style="">清空查询</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="90">
		<thead>
			<tr>
				<th>序号</th>
				<th>周期</th>
				<th>是否报送</th>
				<th>报送方式</th>
				<th>设置报送状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.result }" var="list" varStatus="status">
				<tr target="sid_user" rel="1">
					<td
						<c:if test="${list.periodIsReport!=1}">style="color: red"</c:if>>${status.index+1 }</td>
					<td
						<c:if test="${list.periodIsReport!=1}">style="color: red"</c:if>>${list.reportYear}年${list.reportMonth}</td>
					<td
						<c:if test="${list.periodIsReport!=1}">style="color: red"</c:if>>${list.periodIsReport == 1?'是':'否'}</td>
					<td
						<c:if test="${list.periodIsReport!=1}">style="color: red"</c:if>><zd:show
							type="reportType" value="${list.reportType}"></zd:show></td>
					<td><a class="button"
						href="${basePath}admin/data/isReport?logId=${list.logId}&reportYear=${list.reportYear}&reportMonth=${list.deparmentName}&templateName=${log.templateName}&tableName=${log.tableName}&reportZq=${chileBean.zq}&deparmentId=${log.deparmentId}"
						target="ajaxTodo" rel="toQueryReport" minable="false"><span>设置成<c:if
									test="${list.periodIsReport!=1}">已</c:if> <c:if
									test="${list.periodIsReport==1}">未</c:if>报送
						</span></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%@include file="/common/page.jsp"%>
</div>
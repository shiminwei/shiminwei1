<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }admin/message/detail">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage }" />
</form>

<div class="pageHeader">
	<form onsubmit="return dialogSearch(this);" action="${basePath }admin/message/tohistory" method="post" id="searchForm" name="searchForm">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>前置机ID：</label>
				<input type="text" name="leadId" value="${bean.leadId}"/>
				<input type="hidden" name="messageId" value="${bean.messageId }" />
			</li>
			<dd><div class="button"><div class="buttonContent"><button>查询</button></div></div></dd>
		</ul>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
	</div>
	<table class="list" width="100%" layoutH="91">
		<thead>
			<tr>
				<th align="center" style="font-weight: 900" width="20%">前置机ID</th>
				<th align="center" style="font-weight: 900" width="20%">地区部门名称</th>
				<th align="center" style="font-weight: 900" width="15%">发送状态</th>
				<th align="center" style="font-weight: 900" width="15%">发送时间</th>
			</tr>
		</thead>	
		<tbody>
			<c:forEach var="list" items="${pageList.result}" >
				<tr>				
				<td align="center">${list.leadId }</td>
				<td align="center">${list.areaName }-${list.departmentName }</td>
				<td align="center">${StatecType[list.states ] }</td>
				<td align="center">${fn:substring(list.sendTime, 0, 19)}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
<!-- 分页 -->
<%@include file="/common/page.jsp" %>
</div>

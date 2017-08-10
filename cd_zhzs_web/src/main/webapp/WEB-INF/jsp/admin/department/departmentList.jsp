<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp" %> 
<div class="pageHeader" style="border:1px #B8D0D6 solid">
	<form id="pagerForm" onsubmit="return divSearch(this, 'jbsxBox');" action="${basePath }/admin/department/depList?areaId=${areaId }" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	</form>
</div>
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<div class="panel">
		<h1>行政部门</h1>
		<div>
			<table class="list" width="99%" layoutH="132" rel="jbsxBox">
				<thead>
					<tr>
						<th style="text-align: center;">所属区域</th>
						<th style="text-align: center;">序号</th>
						<th style="text-align: center;">部门名称</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
					<c:when test="${fn:length(pageList.result)>0}">
					<c:forEach items="${pageList.result}" var="dep" varStatus="status">
					<tr>
						<c:if test="${status.index eq 0 }">
							<td style="text-align: center;" rowspan='${fn:length(pageList.result)}'>${fn:split(dep.departmentName,'-')[0] }</td>
						</c:if>
						<td style="text-align: center;">${(pageList.pageNum-1)*pageList.numPerPage+status.index+1}</td>
						<td style="text-align: center;">${fn:split(dep.departmentName,'-')[1] }</td>
					</tr>
					</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
						<td colspan=3 style="text-align: center;">暂无数据</td>
					</tr>
					</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<!-- 分页 -->
			<jsp:include page="/common/pageJbsxBox.jsp" flush="true">     
	     		<jsp:param name="jbsxBoxId" value="jbsxBox"/> 
			</jsp:include> 
		</div>
</div>
</div>
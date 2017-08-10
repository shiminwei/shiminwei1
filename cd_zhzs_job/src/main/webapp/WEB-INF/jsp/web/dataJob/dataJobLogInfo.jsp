<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<script type="text/javascript">
	
</script>
<html>
<body>
	<div class="pageContent">
		<form class="pageForm required-validate" method="post" action="${basePath }web/dataJob/checkJobLogInfo" modelAttribute="dataJobLogger" 
			 name="searchForm"  id="searchForm" class="pageForm required-validate">
			<div class="pageFormContent nowrap" layouth="97"
				style="height: 500px; overflow: auto;">
				<h2 class="contentTitle" style="color:red">${dataJobLogger.name}</h2>
				<dl>
					<dt style="font-weight: 900; width: 80px">任务id：</dt>
					<dd style="width: 200px">${dataJobLogger.jobId }
						<%-- <input name="jobId" style="padding-left:10px" type="text" value="${dataJobLogger.jobId }"
							readonly="readonly" /> --%>
					</dd>
					<dt style="font-weight: 900; width: 80px">任务名称：</dt>
					<dd style="width: 200px">${dataJobLogger.name }
						<%-- <input name="name" style="padding-left:10px" type="text" value="${dataJobLogger.name }"
								readonly="readonly" /> --%>
					</dd>
				</dl>
				<dl>
					<dt style="font-weight: 900; width: 80px">调度类型：</dt>
					<dd style="width: 200px">
					<c:if test="${dataJobLogger.type == 1}">
					立即任务<!-- <input name="type" style="padding-left:10px" type="text"
							value="立即任务" readonly="readonly" /> --></c:if>
					<c:if test="${dataJobLogger.type == 2}">
					周期任务<!-- <input name="type" style="padding-left:10px" type="text"
							value="周期任务" readonly="readonly" /> --></c:if>
					<c:if test="${dataJobLogger.type == 3}">
					定时任务<!-- <input name="type" style="padding-left:10px" type="text"
							value="定时任务" readonly="readonly" /> --></c:if>
					</dd>
					<dt style="font-weight: 900; width: 80px">调度周期：</dt>
					<dd style="width: 200px">${dataJobLogger.runPeriod }
						<%-- <input name="runPeriod" style="padding-left:10px" type="text" value="${dataJobLogger.runPeriod }"
							readonly="readonly" /> --%>
					</dd>
				</dl>
				
				<h2 style="margin-top:115px; color:red" class="contentTitle">调度步骤</h2>
	<table class="list" width="100%" style="height:50%;overflow:auto;">
		<thead>
			<tr>
				<th  align="center" width=15%>id</th>
				<th  align="center">名称</th>
				<th  align="center">操作类型</th>
				<th  align="center">执行时间</th>
				<th  align="center">执行内容</th>
			</tr>
		</thead>
		<tbody>
				<c:forEach var="step" items="${pageList.result}" varStatus="status">
					<c:forEach var="steps" items="${step.steps}"
						varStatus="status">
						<tr>
							<td align="center">${steps.stepId}</td>
							<td align="center">${steps.name}</td>
							<td align="center">
								<c:forEach var="map" items="${map}">
									<c:if test="${map.key == steps.type}">${map.value}</c:if>
								</c:forEach>
							</td>
							<td align="center">${step.executioTime}</td>
							<td align="center">${step.content}</td>
						</tr>
					</c:forEach>
				</c:forEach>
		</tbody>
	</table>
</div>
</form>
	</div>
</body>
</html>

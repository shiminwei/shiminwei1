<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<%@ taglib prefix="zd" uri="../../../../WEB-INF/zdtag.tld"%>
<form id="pagerForm" method="post"
	action="${basePath }web/dataReport/list"
	onsubmit="return navTabSearch(this);">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="26">
		<thead>
			<tr>
				<th align="center">报送名称</th>
				<th align="center">报送周期</th>
				<th align="center">下载模板(点击名称下载)</th>
				<th align="center">本期是否报送</th>
				<th align="center">报送</th>
				<th align="center">重报</th>
				<th align="center">补报</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${pageList.result}">
				<tr>
					<td align="center"
						<c:if test="${list.nowIsReport == 0}">style="color: red" </c:if>>${list.name}</td>
					<td align="center"
						<c:if test="${list.nowIsReport == 0}">style="color: red" </c:if>><zd:show
							type="zq" value="${list.zq}"></zd:show></td>
					<td align="center"><a
						href="${basePath }web/dataReport/toDownload?templateId=${list._id}"
						<c:if test="${list.nowIsReport == 0}">style="color: red" </c:if>>${list.name}.xls</a></td>
					<td align="center"
						<c:if test="${list.nowIsReport == 0}">style="color: red" </c:if>>${list.nowIsReport == 1?'是':'否'}</td>
					<td align="center"><a class="button"
						onclick="show('${basePath }web/dataReport/toReport?reportType=1&reportPeroid=${list.zq}&templateName=${list.name}&reportPeroid=${list.zq}','报送')"><span>报送</span></a></td>
					<td align="center"><a class="button"
						onclick="show('${basePath }web/dataReport/toReport?reportType=2&reportPeroid=${list.zq}&templateName=${list.name}&reportPeroid=${list.zq}','重报')"><span>重报</span></a></td>
					<td align="center"><a class="button"
						onclick="show('${basePath }web/dataReport/toReport?reportType=3&reportPeroid=${list.zq}&templateName=${list.name}&reportPeroid=${list.zq}','补报')"><span>补报</span></a></td>
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

<script type="text/javascript">
	function show(url,urlTitle) {
		var datas = {};
		if (url.indexOf("?") > 0) {
			var urlRel = url.substr(0, url.indexOf("?"));
			var parmet = url.substr(url.indexOf("?") + 1, url.length);
			var strs = new Array();
			strs = parmet.split("&");
			for (var i = 0; i < strs.length; i++) {
				if (strs[i] != null && strs[i] != ""
						&& strs[i].indexOf("=") > 0) {
					var key = strs[i].substr(0, strs[i].indexOf("="));
					var value = strs[i].substr(strs[i].indexOf("=") + 1,
							strs[i].length);
					datas[key] = value;
				}
			}
		}
		navTab.openTab("toReportPage", urlRel, {
			title : urlTitle,
			fresh : true,
			data : datas
		});
	}
</script>

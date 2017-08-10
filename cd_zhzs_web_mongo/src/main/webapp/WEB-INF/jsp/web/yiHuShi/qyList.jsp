<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>

<form id="pagerForm" method="post"
	action="${basePath }web/yiHuShi/qyList">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
		 <input type="hidden" name="qymc" value="${qyBean.qymc}" />
 <input type="hidden" name="startDate" value="${startDate}" />
 <input type="hidden" name="endDate" value="${endDate}" />
</form>


<div class="pageContent" style="width: auto; overflow: x:scroll;">
	<table class="list" width="100%" layoutH="26">
		<thead>
			<tr>
				<th align="center">序号</th>
				<th align="center">纳税人名称</th>
				<th align="center">纳税人识别号</th>
				<th align="center">注册类型</th>
				<th align="center">法定代表人</th>
				<th align="center">经营地址</th>

			</tr>
		</thead>
		<tbody>

			<c:forEach items="${pageList.result}" var="list" varStatus="status">
				<tr>
					<td align="center">${status.index+1 }</td>
					<td align="center"><a title="企业查看"
						href="${basePath }web/cy/getQyDetil?qymc=${list[1] }&startDate=2010&endDate=2017"
						target="navTab" rel="getQyDetil"> ${list[1] }</a></td>
					<td align="center">${list[2] }</td>
					<td align="center">${list[3] }</td>
					<td align="center">${list[4] }</td>
					<td align="center">${list[5] }</td>
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

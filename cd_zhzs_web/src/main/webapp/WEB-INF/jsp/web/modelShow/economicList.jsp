<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }static/js/cd_common.js" />
<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/modelShow/economicList">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> <input
		type="hidden" name="name" value="${param.name}" />
</form>
<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/economicList"
	onsubmit="return navTabSearch(this);">

	<div class="pageHeader">
		<div class="searchBar">
			<h1 align="center" style="font-size: 30px; margin-bottom: -12px">${year}年<c:if
					test="${newMonth!=null&&newMonth!=''}">${newMonth}月</c:if>全市主要经济指标完成情况表
			</h1>
			<%--<h1 align="right" style="font-size: 16px">单位：${bean.unitName }</h1>--%>


			<ul class="searchContent"
				style="margin-bottom: -4px; margin-top: -9px">
				<li><label>所属时间：</label> <select id="year" name="year">
						<c:forEach items="${yearList}" var="years" varStatus="status2">
							<option value="${years.code }"
								<c:if test="${year==years.code}">selected="selected"</c:if>>${years.value }</option>
						</c:forEach>
				</select> <select name="month" onclick="toSelectMonth(this)">
						<option value="">请选择月份</option>
						<c:forEach items="${monthList}" var="months" varStatus="status2">
							<option value="${months.code }"
								<c:if test="${ month == months.code}">selected="selected"</c:if>>${months.value }</option>
						</c:forEach>
				</select></li>
			</ul>
		</div>
	</div>
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="search" href="javascript:void(0)"><button
						type="submit">
						<span>查询数据</span>
					</button></a></li>
			<li><a class="icon" id="abyexport" href="#"
				onclick="cleanForm('pagerForm','navTab')"><span>清空查询</span></a></li>
			<li><a class="excel" id="abyexport1" href='#' target="dwzExport"
				targetType="navTab" title="需要导出当前数据吗?"><span style="">导出数据</span></a></li>
		</ul>
	</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="105">
		<thead>
			<tr>
				<th width="13%" align="center">指标名称</th>
				<th width="10%" align="center">单位</th>
				<th width="20%" align="center">累计完成数</th>
				<th width="20%" align="center">上年同期</th>
				<th width="20%" align="center" colspan="2">增幅（%）</th>
		</thead>
		<tbody>
			<c:forEach items="${allResult}" var="list" varStatus="status">
				<tr>
					<td width="center"
						<c:if test="${fn:contains(list.ZHIBIAO_UNIT,'、')==true}">style="color: red"</c:if>
						<c:if test="${fn:contains(list.ZHIBIAO_UNIT,'、')==false}"><a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a</c:if>
						>
					${list.ZHIBIAO_UNIT}</td>
					<td align="center">${list.DW}</td>
					<td align="center">${list.LJ}</td>
					<td align="center">${list.SNTQ}</td>
					<td align="center">${list.TBZZ}</td>
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
		</script></strong> <span>条，共13条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>

<script type="text/javascript">
	function toSelectMonth(obj) {
		var myYear = document.getElementById("year").value;
		if (myYear == "" || myYear == null) {
			obj.options[0].selected = true;
			alert("请先选择年份");
			obj.disabled = false;
		}
	}
</script>

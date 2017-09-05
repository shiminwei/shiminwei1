<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }static/js/cd_common.js" />
<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/modelShow/czsrList">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> <input
		type="hidden" name="name" value="${param.name}" />
</form>
<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/czsrList"
	onsubmit="return navTabSearch(this);">
	<div class="pageHeader">
		<div class="searchBar">
			<h1 align="center" style="font-size: 30px;margin-bottom: -12px">${year}年<c:if test="${newMonth!=null&&newMonth!=''}">${newMonth}月</c:if>全省财政总收入完成情况表</h1>
			<h1 align="right" style="font-size: 16px">单位：万元</h1>
			<h2 class="contentTitle" align="center"></h2>
			<ul class="searchContent" style="margin-bottom: -4px;margin-top: -9px">
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
						<li><a class="excel" id="abyexport1"
				href='#'
				target="dwzExport" targetType="navTab" title="需要导出当前数据吗?"><span
					style="">导出数据</span></a></li>
		</ul>
	</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="122">
		<thead>
			<tr>			
				<th width="center" align="center" rowspan="3">地区</th>
				
				<th width="center" align="center" colspan="4">年度预期指标排名</th>
				
				<th width="center" align="center"  rowspan="2" colspan="2">累计完成数排名</th>				
				<th width="center" align="center" rowspan="2" colspan="2">进度排名</th>
				<th width="center" align="center" rowspan="2" colspan="2">增幅排名</th>
			</tr>
			<tr>
			<th width="center" align="center" colspan="2">预期收入</th>
			<th width="center" align="center" colspan="2">预期增幅</th>
			</tr>
			
			<tr>
			<th width="center" align="center">预期数</th>
			<th width="center" align="center">位次</th>
			<th width="center" align="center">增幅</th>
			<th width="center" align="center">位次</th>
			<th width="center" align="center">完成数</th>
			<th width="center" align="center">位次</th>
			<th width="center" align="center">进度</th>
			<th width="center" align="center">位次</th>
			<th width="center" align="center">增幅</th>
			<th width="center" align="center">位次</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.result}" var="list"	varStatus="status">
			<tr>
			<td align="center" <c:if test="${status.count==14}"> style="color: red;"</c:if>>${list.DQ}</td>
				<td align="center" >${list.YQS}</td>	
				<td align="center" >${list.YQWC}</td>
				<td align="center" >${list.ZF}</td>
				<td align="center" >${list.ZFWC}</td>
				<td align="center" >${list.CZZSR}</td>
				<td align="center" >${list.WC}</td>
				<td align="center" >${list.JD}</td>
				<td align="center" >${list.JDWC}</td>
				<td align="center" >${list.JSNTQZF}</td>
				<td align="center" >${list.WCT}</td>		
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
		</script></strong> <span>条，共16条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>

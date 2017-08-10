<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }static/js/cd_common.js" />
<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/modelShow/czzsrdiyubumenList">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> <input
		type="hidden" name="name" value="${param.name}" />
</form>
<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/czzsrdiyubumenList"
	onsubmit="return navTabSearch(this);">
	<div class="pageHeader">
		<div class="searchBar">
			<h1 align="center" style="font-size: 30px;margin-bottom: -12px">${year}年<c:if test="${newMonth!=null&&newMonth!=''}">${newMonth}月</c:if>全市财政收入分级分部门完成情况表</h1>
			<h1 align="right" style="font-size: 16px">单位：万元</h1>
			<h2 class="contentTitle" align="center"></h2>
			<ul class="searchContent" style="margin-bottom: -4px;margin-top: -9px">
				<li><label>所属时间：</label> <select id="year" name="year">
						<c:forEach items="${yearList}" var="years" varStatus="status2">
							<option value="${years.code }"
								<c:if test="${year==years.code}">selected="selected"</c:if>>${years.value }</option>
						</c:forEach>
				</select> <select name="month">					
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
	<table class="list" width="100%" layoutH="121">
		<thead>
			<tr>
				<th width="center" align="center" colspan="1" rowspan="2" height="29px" style="font-size: 15px">地区</th>
				<th width="center" align="center" colspan="2" style="font-size: 15px">年初预算</th>
				<th width="center" align="center" colspan="5" style="font-size: 15px">分级完成情况</th>
				<th width="center" align="center" colspan="2" style="font-size: 15px">其中：非税收入</th>
				<th width="center" align="center" colspan="1" rowspan="2" style="font-size: 15px">部门</th>
				<th width="center" align="center" colspan="5" style="font-size: 15px">分部门完成情况</th>
				
			</tr>
			<tr>
				<th width="center" align="center">计划</th>
				<th width="center" align="center">增幅</th>
				<th width="center" align="center">当月完成数</th>
				<th width="center" align="center">累计完成数</th>
				<th width="center" align="center">上年同期</th>
				<th width="center" align="center">执行进度</th>
				<th width="center" align="center">增幅</th>
				<th width="center" align="center">金额</th>
				<th width="center" align="center">占比</th>
				<th width="center" align="center">年度计划</th>
				<th width="center" align="center">当月完成数</th>
				<th width="center" align="center">累计完成数</th>
				<th width="center" align="center">执行进度</th>
				<th width="center" align="center">增幅</th>
	
			</tr>
	
		</thead>
		<tbody>
		<c:forEach items="${allResult}" var="list" varStatus="status">
			<tr >
				<td width="center" align="center">${list.CODE }</td>
				<td width="center" align="center">${list.JH }</td>
				<td width="center" align="center">${list.BSNZZ }</td>
				<td width="center" align="center">${list.DYRK }</td>
				<td width="center" align="center">${list.LJRK }</td>
				<td width="center" align="center">${list.SNTQLJ }</td>
				<td width="center" align="center">${list.GZMBZXJD }</td>
				<td width="center" align="center">${list.BSNZZT }</td>
				<td width="center" align="center">${list.JE }</td>
				<td width="center" align="center">${list.FSZF }</td>
					<c:if
						test="${list.FBM=='国税'||list.FBM=='地税'||list.FBM=='财政'||list.FBM =='海关'}">
						<td width="center" align="center" rowspan="2">${list.FBM }</td>
						<td width="center" align="center" rowspan="2">${list.GZMB }</td>
						<td width="center" align="center" rowspan="2">${list.DYRKT }</td>
						<td width="center" align="center" rowspan="2">${list.LJRKT }</td>
						<td width="center" align="center" rowspan="2">${list.GZMBZXJDT }</td>
						<td width="center" align="center" rowspan="2">${list.BSNZZS }</td>
					</c:if>
					<c:if test="${list.FBM=='全市合计'||list.FBM=='省直地税'}">
						<td width="center" align="center">${list.FBM }</td>
						<td width="center" align="center">${list.GZMB }</td>
						<td width="center" align="center">${list.DYRKT }</td>
						<td width="center" align="center">${list.LJRKT }</td>
						<td width="center" align="center">${list.GZMBZXJDT }</td>
						<td width="center" align="center">${list.BSNZZS }</td>
					</c:if>
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
		</script></strong> <span>条，共8条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>

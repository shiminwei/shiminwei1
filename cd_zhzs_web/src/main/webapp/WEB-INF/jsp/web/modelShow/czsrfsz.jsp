<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }static/js/cd_common.js" />
<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/modelShow/czsrfsz">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> <input
		type="hidden" name="name" value="${param.name}" />
</form>
<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/czsrfsz"
	onsubmit="return navTabSearch(this);">
	<div class="pageHeader">
		<div class="searchBar">
			<h1 align="center" style="font-size: 30px;margin-bottom: -12px">${year}年<c:if test="${newMonth!=null&&newMonth!=''}">${newMonth}月</c:if>全市分区域分税种完成情况表</h1>
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
	<table class="list" width="150%" layoutH="121">
		<thead>
			<tr>
				<th width="center" style="width:10%;" align="center" colspan="1" rowspan="2" style="font-size: 15px;position:fixed">科目名称</th>
				<th width="center" align="center" colspan="10" style="font-size: 15px">累计完成数</th>
				<th width="center" align="center" colspan="10" style="font-size: 15px">与上年同期增减数</th>
				<th width="center" align="center" colspan="10" style="font-size: 15px">增幅(%)</th>
			</tr>
			<tr>
				<th width="center" align="center">合计</th>
				<th width="center" align="center">市直</th>
				<th width="center" align="center">贵池区</th>
				<th width="center" align="center">石台县</th>
				<th width="center" align="center">青阳县</th>
				<th width="center" align="center">东至县</th>
				<th width="center" align="center">九华山风景区</th>
				<th width="center" align="center">开发区</th>
				<th width="center" align="center">江南集中区</th>
				<th width="center" align="center">平天湖区</th>
				
				<th width="center" align="center">合计</th>
				<th width="center" align="center">市直</th>
				<th width="center" align="center">贵池区</th>
				<th width="center" align="center">石台县</th>
				<th width="center" align="center">青阳县</th>
				<th width="center" align="center">东至县</th>
				<th width="center" align="center">九华山风景区</th>
				<th width="center" align="center">开发区</th>
				<th width="center" align="center">江南集中区</th>
				<th width="center" align="center">平天湖区</th>
				
				<th width="center" align="center">合计</th>
				<th width="center" align="center">市直</th>
				<th width="center" align="center">贵池区</th>
				<th width="center" align="center">石台县</th>
				<th width="center" align="center">青阳县</th>
				<th width="center" align="center">东至县</th>
				<th width="center" align="center">九华山风景区</th>
				<th width="center" align="center">开发区</th>
				<th width="center" align="center">江南集中区</th>
				<th width="center" align="center">平天湖区</th>
			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.result}" var="list"	varStatus="status">
			<tr>
			<td 
			<c:if test="${fn:contains(list.SHOW_NAME,'、')==true}">style="color: red;font-weight:900"</c:if>
			
			
			>
			<c:if test="${fn:contains(list.SHOW_NAME,'.')==true}"> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</c:if>
			${list.SHOW_NAME}
			</td>
			<td align="center" >${list.HJ}</td>	
			<td align="center" >${list.SZ}</td>	
			<td align="center" >${list.GC}</td>
			<td align="center" >${list.ST}</td>
			<td align="center" >${list.QY}</td>
			<td align="center" >${list.DZ}</td>
			<td align="center" >${list.JHS}</td>
			<td align="center" >${list.KFQ}</td>
			<td align="center" >${list.JZQ}</td>
			<td align="center" >${list.PTH}</td>
			
			<td align="center" >${list.ZJHJ}</td>	
			<td align="center" >${list.ZJSZ}</td>	
			<td align="center" >${list.ZJGC}</td>
			<td align="center" >${list.ZJST}</td>
			<td align="center" >${list.ZJQY}</td>
			<td align="center" >${list.ZJDZ}</td>
			<td align="center" >${list.ZJJHS}</td>
			<td align="center" >${list.ZJKFQ}</td>
			<td align="center" >${list.ZJJZQ}</td>
			<td align="center" >${list.ZJPTH}</td>
			
			
			
			<td align="center" >${list.ZFHJ}</td>	
			<td align="center" >${list.ZFSZ}</td>	
			<td align="center" >${list.ZFGC}</td>
			<td align="center" >${list.ZFST}</td>
			<td align="center" >${list.ZFQY}</td>
			<td align="center" >${list.ZFDZ}</td>
			<td align="center" >${list.ZFJHS}</td>
			<td align="center" >${list.ZFKFQ}</td>
			<td align="center" >${list.ZFJZQ}</td>
			<td align="center" >${list.ZFPTH}</td>
			</tr>
			</c:forEach>
		</tbody>

	</table>
</div>
<div class="panelBar">
	<div class="pages">
		<span>显示</span> <select class="combox" name="numPerPage"
			onchange="navTabPageBreak({numPerPage:this.value,pageNum:1})">
			<option value="50">50</option>
		</select> <strong><script>
			$("select[name='numPerPage']").val('${pageList.numPerPage}');
		</script></strong> <span>条，共24条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>

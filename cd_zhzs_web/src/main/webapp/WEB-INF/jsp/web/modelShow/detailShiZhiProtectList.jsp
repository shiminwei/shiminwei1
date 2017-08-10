<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<form id="pagerForm" method="post"
	action="${basePath }web/modelShow/detailShiZhiProtect">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage }" /> <input
		type="hidden" name="name" value="${bean.name }" /> <input
		type="hidden" name="isAboveScale" value="${bean.isAboveScale }" /> <input
		type="hidden" name="isCanvassBuisiness"
		value="${bean.isCanvassBuisiness }" />
</form>
<form onsubmit="return navTabSearch(this);"
	action="${basePath }web/modelShow/detailShiZhiProtect" method="post"
	id="companyForm" name="searchForm">
	<div class="pageHeader">
		<div class="searchBar">
			<h1 align="center" style="font-size: 30px; margin-bottom: -12px">${year}年<c:if test="${month!=null&&month!=''}">${month}月</c:if>全市招商引资企业纳税情况明细表
			</h1>
			<h1 align="right" style="font-size: 16px">单位：万元</h1>
			<h2 class="contentTitle" align="center"></h2>
			<ul class="searchContent"
				style="margin-bottom: -4px; margin-top: -9px">
				<li><label>所属时间：</label> <select name="year">
						<c:forEach items="${yearList}" var="yearList" varStatus="status2">
							<option value="${yearList.code }"
								<c:if test="${year==yearList.code}">selected="selected"</c:if>>${yearList.value }</option>
						</c:forEach>
				</select> <select name="month">
						<option value="">请选择月份</option>
						<c:forEach items="${monthList}" var="months" varStatus="status2">
							<option value="${months.code }"
								<c:if test="${ month == months.code}">selected="selected"</c:if>>${months.value }</option>
						</c:forEach>
				</select></li>
				</li>
				<li><label>企业名称：</label> <input type="text" name="qymc">
				</li>
			</ul>
		</div>
	</div>
	<div class="panelBar" style="margin-top: -2px">

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
	<table class="list" width="100%" layoutH="119">
		<thead>
			<tr>
				<th width="center" align="center" rowspan="2" >序号</th>
				<th align="center" style="font-weight: 900; font-size: 14px" rowspan="2" >企业名称</th>
				<th align="center" style="font-weight: 900; font-size: 14px" rowspan="2" >收款国库</th>
				<th align="center" style="font-weight: 900; font-size: 14px" rowspan="2" >当期税额</th>
				<th align="center" style="font-weight: 900; font-size: 14px"
					colspan="3">上年同期</th>
				<th align="center" style="font-weight: 900; font-size: 14px"
					colspan="3">国税</th>
				<th align="center" style="font-weight: 900; font-size: 14px"
					colspan="3">地税</th>
				<th align="center" style="font-weight: 900; font-size: 14px"
					colspan="3">海关</th>
			</tr>
				<th width="center" align="center">纳税额</th>
				<th width="center" align="center">增减额</th>
				<th width="center" align="center">增幅</th>
				<th width="center" align="center">纳税额</th>
				<th width="center" align="center">增减额</th>
				<th width="center" align="center">增幅</th>
				<th width="center" align="center">纳税额</th>
				<th width="center" align="center">增减额</th>
				<th width="center" align="center">增幅</th>
				<th width="center" align="center">纳税额</th>
				<th width="center" align="center">增减额</th>
				<th width="center" align="center">增幅</th>
			</tr>

		</thead>
		<tbody>
			<%
				for (int i = 1; i < 19; i++) {
					out.print("<tr>");
					out.print("<td align='center'>");
					out.print(i);
					out.print("</td>");
					for (int j = 1; j < 16; j++) {
						out.print("<td>");
						out.print("</td>");
					}
					out.print("</tr>");
				}
			%>
		</tbody>
	</table>
	<!-- 分页 -->
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
			</script></strong> <span>条，共18条</span>
		</div>
		<div class="pagination" targetType="navTab"
			totalCount="${pageList.totalCount}"
			numPerPage="${pageList.numPerPage}" pageNumShown="10"
			currentPage="${pageList.pageNum}"></div>
	</div>
</div>
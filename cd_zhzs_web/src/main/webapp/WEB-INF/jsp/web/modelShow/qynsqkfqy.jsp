<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }static/js/cd_common.js" />
<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/modelShow/qynsqkfqy">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> <input
		type="hidden" name="name" value="${param.name}" />
</form>
<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/qynsqkfqy"
	onsubmit="return navTabSearch(this);">
	<div class="pageHeader">
		<div class="searchBar">
		
			<h1 align="center" style="font-size: 30px;margin-bottom: -12px">${year}年<c:if test="${newMonth!=null&&newMonth!=''}">${newMonth}月</c:if>全市企业分区域纳税情况表</h1>
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
			<li><label>所在区域：</label> 
			
			<select  name="skgk">
			<option value="">请选择所属区域</option>
		<c:forEach items="${ssqyList}" var="ssqyList" varStatus="status">
		<option value="${ssqyList.code }"<c:if test="${ssqyList.code==skgk}">selected="selected"</c:if>>${ssqyList.value}</option>
		
		</c:forEach>
			</select>
			
			</li>	
			<li><label>企业名称：</label> <input type="text" name="qymc" value="${qymc }"> </li>
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
		</ul>
	</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="121">
		<thead>
			<tr>
				<th width="center" align="center" rowspan="2" height="26px" style="font-size: 15px">序号</th>
				<th width="center" align="center" rowspan="2" style="font-size: 15px">企业名称</th>
				<th width="center" align="center"  rowspan="2"style="font-size: 15px">所属区域</th>
				<th width="center" align="center" colspan="3" style="font-size: 15px">合计</th>
				<th width="center" align="center" colspan="3" style="font-size: 15px">国税</th>
				<th width="center" align="center" colspan="3" style="font-size: 15px">地税</th>
				<th width="center" align="center" colspan="3" style="font-size: 15px">海关</th>
			</tr>
			<tr>
				<th width="center" align="center">完成数</th>
				<th width="center" align="center">增减额</th>
				<th width="center" align="center">增幅</th>
				<th width="center" align="center">完成数</th>
				<th width="center" align="center">增减额</th>
				<th width="center" align="center">增幅</th>
				<th width="center" align="center">完成数</th>
				<th width="center" align="center">增减额</th>
				<th width="center" align="center">增幅</th>
				<th width="center" align="center">完成数</th>
				<th width="center" align="center">增减额</th>
				<th width="center" align="center">增幅</th>
			</tr>
		</thead>
		<tbody>
		<%
				for (int i = 1; i <18; i++) {
					out.print("<tr>");
					out.print("<td align='center'>");
					out.print(i);
					out.print("</td>");
					for(int j = 1; j < 15; j++){
						out.print("<td>");
						out.print("</td>");
					}
					out.print("</tr>");
				}
			%>
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
		</script></strong> <span>条，共17条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>

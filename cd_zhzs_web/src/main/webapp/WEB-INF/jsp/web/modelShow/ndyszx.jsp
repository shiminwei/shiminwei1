<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }static/js/cd_common.js" />
<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/modelShow/ndyszx">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> <input
		type="hidden" name="name" value="${param.name}" />
</form>
<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/ndyszx"
	onsubmit="return navTabSearch(this);">
	<div class="pageHeader">	
		<div class="searchBar">
					<h1 align="center" style="font-size: 30px; margin-bottom: -12px">${year}年市直预算单位执行表</h1>
			<h1 align="right" style="font-size: 16px">单位：万元</h1>
			<h2 class="contentTitle" align="center"></h2>
			<ul class="searchContent" style="margin-bottom: -4px;margin-top: -9px">
				<li><label>所属时间：</label>				
		<select name="year">		
		<option value="2014" <c:if test="${year==2014 }">selected="selected"</c:if>>2014</option>
		<option value="2015" <c:if test="${year==2015 }">selected="selected"</c:if>>2015</option>
		<option value="2016" <c:if test="${year==2016 }">selected="selected"</c:if>>2016</option>
		<option value="2017" <c:if test="${year==2017 }">selected="selected"</c:if>>2017</option>
		</select>
		<%-- <select name="month">
						<option value="">请选择月份</option>
						<c:forEach items="${monthList}" var="months" varStatus="status2">
							<option value="${months.code }"
								<c:if test="${ month == months.code}">selected="selected"</c:if>>${months.value }</option>
						</c:forEach>
				</select>--%>
					
				
				</li>	
<li><label>预算单位：</label><input type="text" />					
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
			<th width="center" align="center" colspan="1" height="26px" rowspan="4">序号</th>
				<th width="center" align="center" colspan="1" rowspan="4" style="font-size: 14px">预算单位</th>
				<th width="center" align="center" colspan="12"  style="font-size: 14px">A.&nbsp年初预算执行情况</th>	
				<th width="center" align="center" colspan="3" rowspan="2" style="font-size: 14px">B.&nbsp本级预算追加执行情况</th>	
				<th width="center" align="center" colspan="3" rowspan="2" style="font-size: 14px">C.&nbsp上级转移支付执行情况</th>	
				<th width="center" align="center" colspan="4" rowspan="2" style="font-size: 14px">D.&nbsp支出进度总执行情况</th>	
				<th width="center" align="center" colspan="1" rowspan="4" style="font-size: 14px">备注</th>	
				
			</tr>
	
			</tr>
				<tr>
				<th width="center" align="center" colspan="4">a.&nbsp&nbsp基本支出执行情况</th>
				<th width="center" align="center" colspan="4">b.&nbsp&nbsp项目支出执行情况</th>	
				<th width="center" align="center" colspan="4">年初预算合计执行情况</th>	
			</tr>
			<tr>
			<th width="center" align="center">预算数</th>	
			<th width="center" align="center">执行数</th>	
			<th width="center" align="center">执行进度</th>	
			<th width="center" align="center">与序时进度比</th>	
			<th width="center" align="center">预算数</th>	
			<th width="center" align="center">执行数</th>	
			<th width="center" align="center">执行进度</th>	
			<th width="center" align="center">与序时进度比</th>
			<th width="center" align="center">预算数</th>	
			<th width="center" align="center">执行数</th>	
			<th width="center" align="center">执行进度</th>	
			<th width="center" align="center">与序时进度比</th>	
			<th width="center" align="center">追加数</th>	
			<th width="center" align="center">执行数</th>	
			<th width="center" align="center">执行进度</th>				
			<th width="center" align="center">转移数</th>	
			<th width="center" align="center">执行数</th>	
			<th width="center" align="center">执行进度</th>	
			<th width="center" align="center">年度总预算数</th>	
			<th width="center" align="center">总执行数</th>	
			<th width="center" align="center">总执行进度</th>	
			<th width="center" align="center">与序时进度比</th>	
			</tr>
		</thead>
		<tbody>
				<%
				for (int i = 1; i < 21; i++) {
					out.print("<tr>");
					out.print("<td align='center'>");
					out.print(i);
					out.print("</td>");
					for(int j = 1; j < 25; j++){
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
		</script></strong> <span>条，共20条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>

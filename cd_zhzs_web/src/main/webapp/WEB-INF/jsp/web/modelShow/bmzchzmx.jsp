<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }static/js/cd_common.js" />
<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/modelShow/bmzchzmx">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> <input
		type="hidden" name="name" value="${param.name}" />
</form>
<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/bmzchzmx"
	onsubmit="return navTabSearch(this);">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>所属时间：</label> <select>
					<option value="">----请选择时间----</option>
						<option value="2014">2014</option>
						<option value="2015">2015</option>
						<option value="2016">2016</option>
						<option value="2017">2017</option>
				</select></li>		
				<li><label>收款人：</label><input type="text" /></li>	
				<li><label>经济科目：</label><input type="text" /></li>		
				<li><label>功能科目：</label><input type="text" /></li>			
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

		</ul>
	</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="91">
		<thead>
			<tr>
				<th width="center" align="center">序号</th>
				<th width="center" align="center">部门名称</th>
				<th width="center" align="center">项目名称</th>
				<th width="center" align="center">金额（单位：元）</th>
				<th width="center" align="center">用途</th>
				<th width="center" align="center">收款人</th>
				<th width="center" align="center">经济科目</th>
				<th width="center" align="center">功能科目</th>
			</tr>
		</thead>
		<tbody>
				<%
				for (int i = 1; i < 21; i++) {
					out.print("<tr>");
					out.print("<td align='center'>");
					out.print(i);
					out.print("</td>");
					for(int j = 1; j < 8; j++){
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

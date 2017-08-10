<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }static/js/cd_common.js" />
<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/modelShow/fhybd">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> <input
		type="hidden" name="name" value="${param.name}" />
</form>
<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/fhybd"
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
					<li><label>所属产业：</label> <select>
					<option>----请选择产业----</option>
						<option>第一产业</option>
						<option>第二产业</option>
						<option>第三产业</option>
						<option>其他产业</option>
				</select></li>
				
					<li><label>行业大类：</label> <select>
					<option>----请选择大类----</option>
						<option>农、林、牧、渔业</option>
						<option>制造业</option>
						<option>教育</option>
						<option>住宿和餐饮业</option>
						<option>租赁和商务服务业</option>
						<option>卫生和社会工作</option>
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

		</ul>
	</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="91">
		<thead>
			<tr>
				<th width="center" align="center">序号</th>
				<th width="center" align="center">企业名称</th>
				<th width="center" align="center">计税依据</th>
				<th width="center" align="center">实缴税额</th>
				<th width="center" align="center">疑似漏缴税额</th>
				<th width="center" align="center">所属产业</th>
				<th width="center" align="center">行业大类</th>
				<th width="center" align="center">行业中类</th>
				<th width="center" align="center">所属时间</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (int i = 1; i < 21; i++) {
					out.print("<tr>");
					out.print("<td align='center'>");
					out.print(i);
					out.print("</td>");
					for(int j = 1; j < 9; j++){
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

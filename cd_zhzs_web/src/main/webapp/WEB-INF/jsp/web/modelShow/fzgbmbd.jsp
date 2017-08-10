<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }static/js/cd_common.js" />
<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/modelShow/fzgbmbd">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> <input
		type="hidden" name="name" value="${param.name}" />
</form>
<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/fzgbmbd"
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
					<li><label>所属征管部门：</label> <select>
						<option>----请选择征管部门----</option>
						<option>财政收入</option>
						<option>国税收入</option>
						<option>地税收入</option>
						<option>海关收入</option>
						<option>省直地税收入</option>
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
				<th width="center" align="center">征管部门</th>
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
					for(int j = 1; j < 7; j++){
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

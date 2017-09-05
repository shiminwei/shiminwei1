<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>

<script src="${basePath }static/js/cd_common.js" />
<script src="${basePath }static/js/dwz.stable.js" />
<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/modelShow/ceshiTable">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> <input
		type="hidden" name="name" value="${param.name}" />
</form>
<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/bmryhz"
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
				<li><label>部门名称：</label><input type="text" /></li>
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
	<table class="table" width="200%" layoutH="140">
		<thead>
			<tr >
				<th rowspan="2">序号</th>
				<th colspan="2">部门</th>
				<th >行政编制人员</th>
				<th >行政在职人员</th>
				<th >行政离退人员</th>
				<th >事业编制人员</th>
				<th >事业在职人员</th>
				<th >事业离退人员</th>
				<th >行政编制人员</th>
				<th >行政在职人员</th>
				<th >行政离退人员</th>
				<th >事业编制人员</th>
				<th >事业在职人员</th>
				<th >事业离退人员</th>
				<th >行政编制人员</th>
				<th >结尾</th>
			</tr>
			<tr >
				<th >部门</th>
				
				<th  >行政在职人员</th>
				<th >行政离退人员</th>
				<th >事业编制人员</th>
				<th >事业在职人员</th>
				<th >事业离退人员</th>
				<th >行政编制人员</th>
				<th >行政在职人员</th>
				<th >行政离退人员</th>
				<th >事业编制人员</th>
				<th >事业在职人员</th>
				<th >事业离退人员</th>
				<th >行政编制人员</th>
				<th >结尾</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.result}" var="result">
				<tr >
					<c:forEach items="${result}" var="resultStr" varStatus="varSta">
						<td >${resultStr}</td>
					</c:forEach>
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
		</script></strong> <span>条，共${pageList.totalCount }条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>

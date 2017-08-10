<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }static/js/cd_common.js" />
<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/modelShow/ndyspf">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> <input
		type="hidden" name="name" value="${param.name}" />
</form>
<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/ndyspf"
	onsubmit="return navTabSearch(this);">
	<div class="pageHeader">
		<div class="searchBar">
			<h1 align="center" style="font-size: 30px; margin-bottom: -12px">${year}年度市直预算单位<c:if
					test="${lbmc!='' }">${lbmc}</c:if><c:if test="${lbmc==''||lbmc==null }">支出</c:if>预算批复<c:if test="${lbmc==''||lbmc==null }">总</c:if>表
			</h1>
			<h1 align="right" style="font-size: 16px">单位：万元</h1>
			<h2 class="contentTitle" align="center"></h2>
			<ul class="searchContent"
				style="margin-bottom: -4px; margin-top: -9px">
				<li><label>所属时间：</label> <select name="year">
						<option value="2014"
							<c:if test="${year==2014 }">selected="selected"</c:if>>2014</option>
						<option value="2015"
							<c:if test="${year==2015 }">selected="selected"</c:if>>2015</option>
						<option value="2016"
							<c:if test="${year==2016 }">selected="selected"</c:if>>2016</option>
						<option value="2017"
							<c:if test="${year==2017 }">selected="selected"</c:if>>2017</option>
				</select></li>
				<li><label>支出类别：</label> <select name="lbmc">
						<option value="">请选择支出类别</option>
						<option value="基本支出"
							<c:if test="${lbmc=='基本支出' }">selected="selected"</c:if>>基本支出</option>
						<option value="项目支出"
							<c:if test="${lbmc=='项目支出' }">selected="selected"</c:if>>项目支出</option>
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
			<li><a class="excel" id="abyexport1" href='#' target="dwzExport"
				targetType="navTab" title="需要导出当前数据吗?"><span style="">导出数据</span></a></li>
		</ul>
	</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="121">
		<thead>
			<tr>
				<th width="center" align="center" rowspan="4"
					style="font-size: 14px">单位名称</th>
				<th width="center" align="center" colspan="1" rowspan="4"
					style="font-size: 14px">项目名称</th>
				<c:if test="${lbmc!=''&&lbmc!=null}">
					<th width="center" align="center" colspan="1" rowspan="4"
						style="font-size: 14px">经济科目</th>
				</c:if>

				<th width="center" align="center" colspan="1" rowspan="4"
					style="font-size: 14px">合计</th>
				<th width="center" align="center" colspan="6"
					style="font-size: 14px">一般公共预算收入安排</th>
				<th align="center" colspan="1" rowspan="4" width="10%"
					style="font-size: 14px">社会保险基金收入安排</th>
				<th align="center" colspan="1" rowspan="4" width="10%"
					style="font-size: 14px">政府性基金收入安排</th>
				<th align="center" colspan="1" rowspan="4" width="10%"
					style="font-size: 14px">纳入财政专户管理安排</th>
				<th align="center" colspan="1" rowspan="4" width="10%"
					style="font-size: 14px">上级提前下达资金安排</th>
				<th align="center" colspan="1" rowspan="4" width="10%"
					style="font-size: 14px">上年结余</th>
				<th align="center" rowspan="4" style="font-size: 14px">其他收入安排</th>

			</tr>
			<tr>
			</tr>
			<tr>
				<th width="center" align="center" rowspan="2" colspan="1">小计</th>
				<th width="center" align="center" rowspan="2" colspan="1">财政拨款</th>
				<th width="center" align="center" colspan="4">纳入财政专户管理的非税收入安排</th>

			</tr>
			<tr>

				<th width="center" rowspan="" align="center">专项收入</th>
				<th width="center" rowspan="" align="center">行政事业性收入</th>
				<th width="center" rowspan="" align="center">罚没收入</th>
				<th width="center" align="center" style="width: 80px">纳入预算管理其他非税收入</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach begin="0" end="19">
		<tr>
		<c:if test="${lbmc==''||lbmc==null }">
		<c:forEach begin="0" end="14">
		<td>&nbsp</td>
			</c:forEach>
		</c:if>
<c:if test="${lbmc!=''&&lbmc!=null }">
		<c:forEach begin="0" end="15">
		<td>&nbsp</td>
			</c:forEach>
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
		</script></strong> <span>条，共20条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>

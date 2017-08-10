<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }static/js/cd_common.js" />




<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/modelShow/czzcList">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> <input
		type="hidden" name="name" value="${param.name}" />
</form>


<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/czzcList"
	onsubmit="return navTabSearch(this);">
		<div class="panelBar">
		<h1 width="center" align="center" style="font-size: 25px">池州市财政支出分级分科目完成情况</h1>
	</div>
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>所属时间</label> <select>
						<option value="2014">2014</option>
						<option value="2015">2015</option>
						<option value="2016">2016</option>
						<option value="2017">2017</option>
				</select></li>

				<li><label>支出科目</label><input type="text" /></li>
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
	<table class="list" width="100%" layoutH="118">
		<thead>
			<tr>
				<th width="center" align="center" colspan="0">序号</th>
				<th width="center" align="center" colspan="0">支出科目</th>
				<th width="center" align="center" colspan="2">市直</th>
				<th width="center" align="center" colspan="2">贵池区</th>
				<th width="center" align="center" colspan="2">九华山</th>
				<th width="center" align="center" colspan="2">开发区</th>
				<th width="center" align="center" colspan="2">江南集中区</th>
				<th width="center" align="center" colspan="2">石台县</th>
				<th width="center" align="center" colspan="2">青阳县</th>
				<th width="center" align="center" colspan="2">东至县</th>
			</tr>
				<tr>
					<th width="center" align="center"></th>
					<th width="center" align="center"></th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
				</tr>
			
		</thead>
		<tbody>
			<tr>
				<td align="center">1</td>
				<td align="center">一般公共服务</td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>	
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>								
			</tr>
			<tr>
				<td align="center">2</td>
				<td align="center">国防</td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>	
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>								
			</tr>
			<tr>
				<td align="center">3</td>
				<td align="center">公共安全</td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>	
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>								
			</tr>
						<tr>
				<td align="center">4</td>
				<td align="center">教育</td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>	
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>								
			</tr>
									<tr>
				<td align="center">5</td>
				<td align="center">科学技术</td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>	
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>								
			</tr>
				<tr>
				<td align="center">6</td>
				<td align="center">文化体育与传媒</td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>	
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>								
			</tr>	
			<tr>
				<td align="center">7</td>
				<td align="center">社会保障和就业</td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>	
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>								
			</tr>	
						<tr>
				<td align="center">8</td>
				<td align="center">医疗卫生与计划生育支出</td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>	
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>								
			</tr>	
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
		</script></strong> <span>条，共${pageList.totalCount}条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }static/js/cd_common.js" />
<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/modelShow/sbjzfxxmjhb">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> <input
		type="hidden" name="name" value="${param.name}" />
</form>
<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/sbjzfxxmjhb"
	onsubmit="return navTabSearch(this);">
	<div class="pageHeader">	
		<div class="searchBar">
					<h1 align="center" style="font-size: 30px;margin-bottom: -12px">${year}年度市直政府性投资项目计划表</h1>
			<h1 align="right" style="font-size: 16px">单位：万元</h1>
			<h2 class="contentTitle" align="center"></h2>
			<ul class="searchContent" style="margin-bottom: -4px;margin-top: -9px">
				<li><label>所属时间：</label>				
						<select name="year">
											<c:forEach items="${yearList}" var="yearList"
												varStatus="status2">
												<option value="${yearList.code }"
													<c:if test="${ year eq yearList.code}">selected="selected"</c:if>>${yearList.value }</option>
											</c:forEach>
										</select>

		
				<li><label>项目名称：</label>	<input name="xmmc" type="text" value="${xmmc }"/>
						
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
			
				<th width="center" align="center" colspan="1" rowspan="5" style="font-size: 14px">序号</th>
				<th width="center" align="center" colspan="1" rowspan="5" style="font-size: 14px">项目名称</th>
				<th width="center" align="center" colspan="1" rowspan="5" style="font-size: 14px">建设性质</th>	
				<th width="center" align="center" colspan="1" rowspan="5" style="font-size: 14px">建设规模及内容</th>	
				<th width="center" align="center" colspan="13" style="font-size: 14px">政府直接投资项目</th>
				<th width="center" align="center" colspan="2" style="font-size: 14px">PPP项目</th>
				<th width="center" align="center" colspan="1" rowspan="5" style="font-size: 14px">年度工作目标</th>
				<th width="center" align="center" colspan="1" rowspan="5" style="font-size: 14px">项目责任单位</th>
				<th width="center" align="center" colspan="1" rowspan="5" style="font-size: 14px">责任人</th>	
				<th width="center" align="center" colspan="1" rowspan="5" style="font-size: 14px">备注</th>
					
				
			</tr>
			<tr>
			</tr>
				<tr>
				<th width="center" align="center" rowspan="3">总投资</th>
				<th width="center" align="center" rowspan="3">截至上年底完成投资</th>
				<th width="center" align="center" colspan="9" >本年度计划投资及资金来源</th>	
				<th width="center" align="center" rowspan="3" >预备开工项目年度计划投资</th>	
				<th width="center" align="center" rowspan="3" >安排的前期工作经费</th>	
				<th width="center" align="center" rowspan="3" >总投资</th>	
				<th width="center" align="center" rowspan="3" >计划投资</th>	
			</tr>
			<tr>
			<th width="center"  align="center" rowspan="2">合计</th>	
			<th width="center"  align="center" rowspan="2">中央</th>	
			<th width="center"  align="center" rowspan="2">省级</th>	
			<th width="center"  align="center" colspan="5">市级</th>		
			<th width="center"   align="center" rowspan="2">县区及管委会</th>	
			</tr>
			<th width="center"  align="center" >小计</th>	
			<th width="center"  align="center" >财政</th>	
			<th width="center"  align="center" >城投</th>	
			<th width="center"  align="center" >交投</th>	
			<th width="center"  align="center" >其他</th>	
			<tr>
			</tr>
		</thead>
		<tbody>
			<c:forEach begin="0" end="20" var="i">
			<tr>
			<td 	align="center">${i+1}</td>
			<c:forEach begin="0" end="21" var="j">
			<td></td>
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
		</script></strong> <span>条，共20条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>

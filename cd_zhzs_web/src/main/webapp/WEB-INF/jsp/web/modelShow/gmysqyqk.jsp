<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }static/js/cd_common.js" />
<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/modelShow/gmysqyqk">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> <input
		type="hidden" name="name" value="${param.name}" />
</form>
<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/gmysqyqk"
	onsubmit="return navTabSearch(this);">
	<div class="pageHeader">	
		<div class="searchBar">
					<h1 align="center" style="font-size: 30px;margin-bottom: -12px">${year}年<c:if test="${month!=null&&month!=''}">${month}月</c:if>全市规模以上企业纳税明细表</h1>
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

										<select name="month">
											<option value="">请选择月份</option>
											<c:forEach items="${monthList}" var="monthList" varStatus="status2">
												<option value="${monthList.code }"
													<c:if test="${ month eq monthList.code}">selected="selected"</c:if>>${monthList.value }</option>
											</c:forEach>
							</select>
				</li>
				<li><label>企业名称：</label>	<input name="qymc" type="text" value="${qymc }"/>
				<li><label>所属区域：</label>	
				
				<select name="ssqy">
				<option value="">请选择所属区域</option>
						<c:forEach items="${ssqyList}" var="ssqyList" varStatus="status2">
												<option value="${ssqyList.code }"
													<c:if test="${ ssqy eq ssqyList.code}">selected="selected"</c:if>>${ssqyList.value }</option>
											</c:forEach>
										</select>
				
				
				
			</li>	
<li><label>额度排名：</label>	<input name="" type="text" value=""/>			
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
				<th width="center" align="center" colspan="1" rowspan="4" style="font-size: 14px">序号</th>
				<th width="center" align="center" colspan="1" rowspan="4" style="font-size: 14px">企业名称</th>
				<th width="center" align="center" colspan="1" rowspan="4" style="font-size: 14px">收款国库</th>	
				<th width="center" align="center" colspan="1" rowspan="4" style="font-size: 14px">累计完成数</th>	
				<th width="center" align="center" colspan="9" style="font-size: 14px">分征管部门</th>	
				
			</tr>
			<tr>
			</tr>
				<tr>
				<th width="center" align="center" colspan="3">国税</th>
				<th width="center" align="center" colspan="3">地税</th>
				<th width="center" align="center" colspan="3" >海关</th>			

			</tr>
			<tr>
			<th width="center"  align="center">纳税额</th>	
			<th width="center"   align="center">增减额</th>	
			<th width="center"	align="center">增幅（%）</th>	
			<th width="center"  align="center">纳税额</th>	
			<th width="center"   align="center">增减额</th>	
			<th width="center"	align="center">增幅（%）</th>
			<th width="center"  align="center">纳税额</th>	
			<th width="center"   align="center">增减额</th>	
			<th width="center"	align="center">增幅（%）</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach begin="0" end="20" var="i">
			<tr>
			<td 	align="center">${i+1}</td>
			<c:forEach begin="0" end="11" var="j">
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

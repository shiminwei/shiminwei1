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
	<div class="pageHeader">
		<div class="searchBar">
			<h1 align="center" style="font-size: 30px;margin-bottom: -12px">${year}年<c:if test="${newMonth!=null&&newMonth!=''}">${newMonth}月</c:if>池州市财政支出分级分科目执行情况表</h1>
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
				<li><label>预算种类：</label> <select  name="yskm">
					<option value="一般预算"<c:if test="${yskm=='一般预算'}">selected="selected"</c:if>>一般预算</option>	
					<option value="基金预算"<c:if test="${yskm=='基金预算'}">selected="selected"</c:if>>基金预算</option>	
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
<li><a class="excel" id="abyexport1"
				href='#'
				target="dwzExport" targetType="navTab" title="需要导出当前数据吗?"><span
					style="">导出数据</span></a></li>
		</ul>
	</div>
</form>
<div class="pageContent">
	<table class="list" width="140%" layoutH="121">
		<thead>
			<tr>
				<th style="width: 8%" width="center" align="center" colspan="0" rowspan="2" style="font-size: 14px">支出科目</th>
				<th width="center" align="center" colspan="3" style="font-size: 14px">合计</th>
				<th width="center" align="center" colspan="3" style="font-size: 14px">市直</th>
				<th width="center" align="center" colspan="3" style="font-size: 14px">江南集中区</th>
				<th width="center" align="center" colspan="3" style="font-size: 14px"> 贵池区</th>
				<th width="center" align="center" colspan="3" style="font-size: 14px">东至县</th>
				<th width="center" align="center" colspan="3" style="font-size: 14px">石台县</th>
				<th width="center" align="center" colspan="3" style="font-size: 14px">青阳县</th>
				<th width="center" align="center" colspan="3" style="font-size: 14px">开发区</th>
				<th width="center" align="center" colspan="3" style="font-size: 14px">九华山风景区</th>
				<th width="center" align="center" colspan="3" style="font-size: 14px">平天湖区</th>

			</tr>
				<tr>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">进度（%）</th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">进度（%）</th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">进度（%）</th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">进度（%）</th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">进度（%）</th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">进度（%）</th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">进度（%）</th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">进度（%）</th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">进度（%）</th>
					<th width="center" align="center">年度预算</th>
					<th width="center" align="center">累计完成</th>
					<th width="center" align="center">进度（%）</th>					
				</tr>
			
		</thead>
		<tbody>
			<c:forEach items="${pageList.result}" var="list"	varStatus="status">
			<tr>
			<td <c:if test="${status.count==1||status.count==3||status.count==4||status.count==5||status.count==7
			||status.count==8||status.count==9||status.count==22
			}"> style="color: red"</c:if>>${list.ZCKM}</td>
       		<td align="center" >${list.HJ_NDYS}</td>	
			<td align="center" >${list.HJ_LJWC}</td>	
			<td align="center" >${list.HJ_JD}</td>
         	<td align="center" >${list.SZ_NDYS}</td>	
			<td align="center" >${list.SZ_LJWC}</td>	
			<td align="center" >${list.SZ_JD}</td>
         	<td align="center" >${list.JNJZQ_NDYS}</td>	
			<td align="center" >${list.JNJZQ_LJWCS}</td>	
			<td align="center" >${list.JNJZQ_JD}</td>
    		<td align="center" >${list.GC_NDYS}</td>	
			<td align="center" >${list.GC_LJWCS}</td>	
			<td align="center" >${list.GC_JD}</td>
     	    <td align="center" >${list.DZ_NDYS}</td>	
			<td align="center" >${list.DZ_LJWCS}</td>	
			<td align="center" >${list.DZ_JD}</td>
            <td align="center" >${list.ST_NDYS}</td>	
			<td align="center" >${list.ST_LJWCS}</td>	
			<td align="center" >${list.ST_JD}</td>
     	    <td align="center" >${list.QY_NDYS}</td>	
			<td align="center" >${list.QY_LJWCS}</td>	
			<td align="center" >${list.QY_JD}</td>
            <td align="center" >${list.KFQ_NDYS}</td>	
			<td align="center" >${list.KFQ_LJWCS}</td>	
			<td align="center" >${list.KFQ_JD}</td>
      		<td align="center" >${list.JHS_NDYS}</td>	
			<td align="center" >${list.JHS_LJWCS}</td>	
			<td align="center" >${list.JHS_JD}</td>
			<td align="center" >${list.PTH_NDYS}</td>	
			<td align="center" >${list.PTH_LJWCS}</td>	
			<td align="center" >${list.PTH_JD}</td>

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
		</script></strong> <span>条，共24条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>

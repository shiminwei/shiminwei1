<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }static/js/cd_common.js" />
<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/companytax/detail">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> 
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="year" value="${year }" />
	<input type="hidden" name="month" value="${month }" />
	<input type="hidden" name="qymc" value="${qymc}" />
	<input type="hidden" name="ssqy" value="${ssqy}" />
	<input type="hidden" name="zsxm" value="${zsxm}" />
</form>
<form method="post" name="searchForm"
	action="${basePath }web/companytax/detail"
	onsubmit="return navTabSearch(this);">
	<div class="pageHeader">
		<div class="searchBar">
			<h1 align="center" style="font-size: 30px; margin-bottom: -12px">${year}年<c:if
					test="${month!=null&&month!=''}">${month}月</c:if><c:if test="${qymc!=null&&qymc!=''}">${qymc}</c:if>纳税累计情况表
			</h1>
			<h1 align="right" style="font-size: 16px">单位：万元</h1>
			<h2 class="contentTitle" align="center"></h2>
			<ul class="searchContent"
				style="margin-bottom: -4px; margin-top: -9px">
				<li><label>所属时间：</label> <select name="year">

						<c:forEach items="${yearList}" var="yearList" varStatus="status2">
							<option value="${yearList.code }"
								<c:if test="${ year eq yearList.code}">selected="selected"</c:if>>${yearList.value }</option>
						</c:forEach>
				</select> <select name="month">
						<option value="">请选择月份</option>
						<c:forEach items="${monthList}" var="monthList"
							varStatus="status2">
							<option value="${monthList.code }"
								<c:if test="${ month eq monthList.code}">selected="selected"</c:if>>${monthList.value }</option>
						</c:forEach>
				</select></li>
				<li><label>企业名称：</label> <input name="qymc" type="text"
					value="${qymc }" />
				<li><label>征管部门：</label> <select name="ssqy">
				        <option value="">请选征管部门</option>
						<option value="1"
							<c:if test="${ ssqy == '1'}">selected="selected"</c:if>>国税</option>
						<option value="2"
							<c:if test="${ ssqy == '2'}">selected="selected"</c:if>>地税</option>
						<option value="3"
							<c:if test="${ ssqy == '3'}">selected="selected"</c:if>>海关</option>
						<option value="4"
							<c:if test="${ ssqy == '4'}">selected="selected"</c:if>>省直地税</option>
				</select></li>
				<li><label>征收项目：</label> <select name="zsxm">
							<option value="">请选税种</option>
							<c:forEach items="${shuiZhongList }" var="shuiZhongList">
							<option value="${shuiZhongList.code }"
							<c:if test="${ zsxm eq shuiZhongList.code }">selected="selected"</c:if>>${shuiZhongList.value }</option>
							</c:forEach></select>
				</li>
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
	<table class="list" width="100%" layoutH="121" >
		<thead>
			<tr>
				<th width="center" align="center" style="font-size: 14px">序号</th>
				<th width="center" align="center" style="font-size: 14px">企业名称</th>
				<th width="center" align="center" style="font-size: 14px">社会信用代码</th>
				<th width="center" align="center" style="font-size: 14px">收款国库</th>
				<th width="center" align="center" style="font-size: 14px">征管部门</th>
				<th width="center" align="center" style="font-size: 14px">征收项目</th>
				<th width="center" align="center" style="font-size: 14px">实缴税额</th>
				<th width="center" align="center" style="font-size: 14px">所属时间</th>
			</tr>
			<tr height="30">
				<th align="center">合计</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<c:forEach items="${pageList.totalaList}" var="totalaList" varStatus="status">
					<th align="center">${totalaList }</th>
				</c:forEach>
				<th align="center"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${pageList.result}" varStatus="status">			
				<tr>		
				<td align="center">${ status.index + 1}</td>
				<td align="center">${list.NSRMC }</td>
				<td align="center">${list.NSRSBH }</td>
				<td align="center">${list.SKGK }</td>
				<td align="center">${list.SSGDS }</td>
				<td align="center">${list.ZSXM }</td>
				<td align="center">${list.SJJE }</td>
				<td align="center">${list.K_DATE }</td>
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
		</script></strong> <span>条，共${pageList.totalCount}条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>

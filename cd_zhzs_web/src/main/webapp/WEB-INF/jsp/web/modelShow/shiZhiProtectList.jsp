<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }web/modelShow/shiZhiProtect">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage }" />
	<input type="hidden" name="name" value="${bean.name }" />
	<input type="hidden" name="isAboveScale" value="${bean.isAboveScale }" />
	<input type="hidden" name="isCanvassBuisiness" value="${bean.isCanvassBuisiness }" />
</form>
<form onsubmit="return navTabSearch(this);" action="${basePath }web/modelShow/shiZhiProtect" method="post" id="companyForm" name="searchForm">
<div class="pageHeader">
	<div class="searchBar">
	
	<h1 align="center" style="font-size: 30px;margin-bottom: -12px">${year }年<c:if test="${month!=null&&month!=''}">${month}月</c:if>全市招商引资项目信息表</h1>
			<h1 align="right" style="font-size: 16px">单位：万元</h1>
			<h2 class="contentTitle" align="center"></h2>
		<ul class="searchContent" style="margin-bottom: -4px;margin-top: -9px">
			<li>
				<label style="width: 120px">成立时间：</label>
		 <select name="year">
						<c:forEach items="${yearList}" var="yearList" varStatus="status2">
							<option value="${yearList.code }"
								<c:if test="${year==yearList.code}">selected="selected"</c:if>>${yearList.value }</option>
						</c:forEach>
				</select> 
				</select> <select name="month">
						<option value="">请选择月份</option>
						<c:forEach items="${monthList}" var="months" varStatus="status2">
							<option value="${months.code }"
								<c:if test="${ month == months.code}">selected="selected"</c:if>>${months.value }</option>
						</c:forEach>
				</select>
			</li>
			<li>
				<label>申报单位：</label>
				<input type="text" name="name" value="${bean.name }"/>
			</li>
			<li>
				<label>项目名称：</label>
				<input type="text" name="name" value="${bean.name }"/>
			</li>
			<li>
				<label>所属区域：</label>
				<select name="sscy">
				<option value="">请选择所属区域</option>
						<c:forEach items="${ssqyList}" var="ssqyList" varStatus="status2">
												<option value="${ssqyList.code }"
													<c:if test="${ ssqy eq ssqyList.code}">selected="selected"</c:if>>${ssqyList.value }</option>
											</c:forEach>
										</select>
			</li>
		</ul>
	</div>
</div>

<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" onclick="cleanForm('companyForm','navTab')" rel=""
			targettype="navTab"><span>清空查询</span></a></li>
		<li><a title="模板下载" rel=""
			href="javascript:void(0)"
			class="down" ><span>导出数据</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="123">
		<thead>
			<tr>
				<th align="center" style="font-weight: 900;font-size: 14px" rowspan="2"  height="24px">序号</th>
				<th align="center" style="font-weight: 900;font-size: 14px" rowspan="2" >申报单位</th>
				<th align="center" colspan="3" style="font-weight: 900;font-size: 14px">投资方</th>
				<th align="center" colspan="2" style="font-weight: 900;font-size: 14px">落户</th>
				<th align="center" colspan="3" style="font-weight: 900;font-size: 14px">注册资本情况</th>
				<th align="center" style="font-weight: 900;font-size: 14px" rowspan="2" >开工时间</th>
				<th align="center" style="font-weight: 900;font-size: 14px" rowspan="2" >投产时间</th>
				<th align="center" style="font-weight: 900;font-size: 14px" rowspan="2" >落户企业纳税信用代码</th>
				<th align="center" style="font-weight: 900;font-size: 14px" rowspan="2" >纳税情况</th>
			</tr>
			<tr>
				<th width="center" align="center">投资人</th>
				<th width="center" align="center">名称</th>
				<th width="center" align="center">地址</th>
				<th align="center" style="font-weight: 900">名称</th>
				<th align="center" style="font-weight: 900">地址</th>
				<th align="center" style="font-weight: 900">注册资本</th>
				<th align="center" style="font-weight: 900">实收资本</th>
				<th align="center" style="font-weight: 900">到位资金</th>
			</tr>
		</thead>		
		<tbody>
					<%
				for (int i = 1; i < 19; i++) {
					out.print("<tr>");
					out.print("<td align='center'>");
					out.print(i);
					out.print("</td>");
					for(int j = 1; j < 14; j++){
						out.print("<td>");
						out.print("</td>");
					}
					out.print("</tr>");
				}
			%>
		</tbody>
	</table>
    <!-- 分页 -->
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
</div>
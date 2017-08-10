<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }static/js/cd_common.js" />
<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/modelShow/economicqsList">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> <input
		type="hidden" name="name" value="${param.name}" />
</form>
<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/economicqsList"
	onsubmit="return navTabSearch(this);">

	<div class="pageHeader">
		<div class="searchBar">
				<h1 align="center" style="font-size: 30px;margin-bottom: -12px">${year}年<c:if test="${newMonth!=null&&newMonth!=''}">${newMonth}月</c:if>全省主要经济指标完成情况表</h1>

		
			<ul class="searchContent" style="margin-bottom: -4px;margin-top: -9px">
				<li><label>所属时间：</label>				
										<select id="year" name="year">
											<c:forEach items="${yearList}" var="years"
												varStatus="status2">
												<option value="${years.code }"
													<c:if test="${year==years.code}">selected="selected"</c:if>>${years.value }</option>
											</c:forEach>
										</select>

										<select name="month"
											onclick="toSelectMonth(this)">
											<option value="">请选择月份</option>
											<c:forEach items="${monthList}" var="months" varStatus="status2">
												<option value="${months.code }"
													<c:if test="${ month == months.code}">selected="selected"</c:if>>${months.value }</option>
											</c:forEach>
										</select>	
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
		<li><a class="excel" id="abyexport1"
				href='#'
				target="dwzExport" targetType="navTab" title="需要导出当前数据吗?"><span
					style="">导出数据</span></a></li>
		</ul>
	</div>
</form>
<div class="pageContent" style="width: auto; overflow: x:scroll;">
	<div style="width: auto; height: auto; overflow: x:scroll;">
	<table class="list" width="180%" layoutH="105">
		<thead>
			<tr>
			
			<tr>			
				<th width="10%" align="center" rowspan="2">指标名称</th>
				<th width="5%" align="center" rowspan="2">单位</th>
				<th   align="center" colspan="2">合肥市</th>
				<th  align="center" colspan="2">淮北市</th>
				<th   align="center" colspan="2">亳州市</th>
				<th  align="center" colspan="2">宿州市</th>
				<th    align="center" colspan="2">蚌埠市</th>
				<th   align="center" colspan="2">阜阳市</th>
				<th    align="center" colspan="2">淮南市</th>
				<th  align="center" colspan="2">滁州市</th>
				<th   align="center" colspan="2">六安市</th>				
				<th   align="center" colspan="2">马鞍山市</th>
				<th    align="center" colspan="2">芜湖市</th>
				<th   align="center" colspan="2">宣城市</th>
				<th    align="center" colspan="2">铜陵市</th>
				<th   align="center" style="color: red" colspan="2">池州市</th>
				<th  align="center" colspan="2">安庆市</th>
				<th   align="center" colspan="2">黄山市</th>			
			</tr>		
				<th align="center">累计完成数</th>
				<th  align="center">增幅（%）</th>
				<th align="center">累计完成数</th>
				<th  align="center">增幅（%）</th>
				<th align="center">累计完成数</th>
				<th  align="center">增幅（%）</th>
				<th align="center">累计完成数</th>
				<th  align="center">增幅（%）</th>
				<th align="center">累计完成数</th>
				<th  align="center">增幅（%）</th>
				<th align="center">累计完成数</th>
				<th  align="center">增幅（%）</th>
				<th align="center">累计完成数</th>
				<th  align="center">增幅（%）</th>
				<th align="center">累计完成数</th>
				<th  align="center">增幅（%）</th>
				<th align="center">累计完成数</th>
				<th  align="center">增幅（%）</th>
				<th align="center">累计完成数</th>
				<th  align="center">增幅（%）</th>
				<th align="center">累计完成数</th>
				<th  align="center">增幅（%）</th>
				<th align="center">累计完成数</th>
				<th  align="center">增幅（%）</th>
				<th align="center">累计完成数</th>
				<th  align="center">增幅（%）</th>
				<th align="center">累计完成数</th>
				<th  align="center">增幅（%）</th>
				<th align="center">累计完成数</th>
				<th  align="center">增幅（%）</th>
				<th align="center">累计完成数</th>
				<th  align="center">增幅（%）</th>
			</tr>
		</thead>
		<tbody>
				<c:forEach items="${pageList.result}" var="list"	varStatus="status">
			<tr>
			<td <c:if test="${status.count==1||status.count==2||status.count==3||status.count==6||status.count==8
			||status.count==9||status.count==10||status.count==11||status.count==13||status.count==14
			}"> style="color: red"</c:if>>${list.SHOW_NAME}</td>
			<td align="center" >${list.SHOW_UNIT}</td>			
			<td align="center" >${list.CZZSRJDS_HFS}</td>			
			<td align="center" >${list.ZZ_HFS}</td>
			<td align="center" >${list.CZZSRJDS_HBS}</td>			
			<td align="center" >${list.ZZ_HBS}</td>
			<td align="center" >${list.CZZSRJDS_BZS}</td>			
			<td align="center" >${list.ZZ_BZS}</td>
			<td align="center" >${list.CZZSRJDS_SZS}</td>			
			<td align="center" >${list.ZZ_SZS}</td>		
			<td align="center" >${list.CZZSRJDS_BBS}</td>			
			<td align="center" >${list.ZZ_BBS}</td>
			<td align="center" >${list.CZZSRJDS_FYS}</td>			
			<td align="center" >${list.ZZ_FYS}</td>				
			<td align="center" >${list.CZZSRJDS_HNS}</td>			
			<td align="center" >${list.ZZ_HNS}</td>
			<td align="center" >${list.CZZSRJDS_CHUZS}</td>			
			<td align="center" >${list.ZZ_CHUZS}</td>			
			<td align="center" >${list.CZZSRJDS_LAS}</td>			
			<td align="center" >${list.ZZ_LAS}</td>		
			<td align="center" >${list.CZZSRJDS_MASS}</td>			
			<td align="center" >${list.ZZ_MASS}</td>
			<td align="center" >${list.CZZSRJDS_WHS}</td>			
			<td align="center" >${list.ZZ_WHS}</td>			
			<td align="center" >${list.CZZSRJDS_XCS}</td>			
			<td align="center" >${list.ZZ_XCS}</td>		
			<td align="center" >${list.CZZSRJDS_TLS}</td>			
			<td align="center" >${list.ZZ_TLS}</td>			
			<td align="center" >${list.CZZSRJDS_CZS}</td>			
			<td align="center" >${list.ZZ_CZS}</td>			
			<td align="center" >${list.CZZSRJDS_AQS}</td>			
			<td align="center" >${list.ZZ_AQS}</td>		
			<td align="center" >${list.CZZSRJDS_HSS}</td>			
			<td align="center" >${list.ZZ_HSS}</td>
			</tr>
			</tr>
	
		
		
					</c:forEach>
		</tbody>

	</table>
</div>
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
		</script></strong> <span>条，共13条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>

<script type="text/javascript">
function toSelectMonth(obj) {
	var myYear = document.getElementById("year").value;
	if (myYear == "" || myYear == null) {
		obj.options[0].selected = true;
		alert("请先选择年份");
		obj.disabled = false;
	}
}
</script>

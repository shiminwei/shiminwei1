<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }web/modelShow/qsqyfszzsfx">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage }" />
	<input type="hidden" name="name" value="${bean.name }" />
	<input type="hidden" name="isAboveScale" value="${bean.isAboveScale }" />
	<input type="hidden" name="isCanvassBuisiness" value="${bean.isCanvassBuisiness }" />
</form>
<form onsubmit="return navTabSearch(this);" action="${basePath }web/modelShow/qsqyfszzsfx" method="post" id="companyForm" name="searchForm">
<div class="pageHeader">
<div class="searchBar">
	<h1 align="center" style="font-size: 30px;margin-bottom: -12px">${year }年<c:if test="${month!=null&&month!=''}">${month}月</c:if>全市企业分行业治税分析表</h1>
	<h1 align="right" style="font-size: 16px">单位：万元</h1>
	<h2 class="contentTitle" align="center"></h2>
			<ul class="searchContent" style="margin-bottom: -4px;margin-top: -9px">
			<li><label>所属时间：</label> <select id="year" name="year">
						<c:forEach items="${yearList}" var="years" varStatus="status2">
							<option value="${years.code }"
								<c:if test="${year==years.code}">selected="selected"</c:if>>${years.value }</option>
						</c:forEach>
				</select> 
				<select name="month">					
							<option value="">请选择月份</option>
						<c:forEach items="${monthList}" var="months" varStatus="status2">
							<option value="${months.code }"
								<c:if test="${ month == months.code}">selected="selected"</c:if>>${months.value }</option>
						</c:forEach>
				</select></li>
			
			<li><label>所属产业：</label> 
				<select  id="cy" name="cy" onchange="toGetHangYe()">					
							<option>请选产业</option>
						<c:forEach items="${sscyList}" var="sscyList" varStatus="status2">
							<option value="${sscyList.code }">${sscyList.value }</option>
						</c:forEach>
				</select>			
			</li>
			<li><label>行业大类：</label> 
				<select id="hy" name="hy">					
							<option value="">请选行业大类</option>
				</select>			
			</li>
			</ul>
		</div>
</div>
<div class="panelBar" style="margin-top: -2px">
	
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
	<table class="list" width="100%" layoutH="119">
		<thead>
			<tr>
			<th align="center" style="font-weight: 900;font-size: 14px" rowspan="2" >序号</th>
				<th align="center" style="font-weight: 900;font-size: 14px" rowspan="2" >企业名称</th>
				<th align="center" style="font-weight: 900;font-size: 14px" rowspan="2">收款国库</th>
				<th align="center" style="font-weight: 900;font-size: 14px" rowspan="2">所属产业</th>
				<th align="center" style="font-weight: 900;font-size: 14px" rowspan="2">所属行业</th>
				<th align="center" style="font-weight: 900;font-size: 14px" colspan="2">预测税收</th>
				<th align="center" style="font-weight: 900;font-size: 14px" colspan="2">实际纳税</th> 
				<th align="center" style="font-weight: 900;font-size: 14px" rowspan="2">疑似欠税额</th>
			</tr>
			<tr>
				<th width="center" align="center" >依据</th>
				<th width="center" align="center" >税额</th>
				<th width="center" align="center" >依据</th>
				<th width="center" align="center" >税额</th>
			</tr>
			<tr>
				<th width="center" align="center" >合计</th>
				<th width="center" align="center" ></th>
				<th width="center" align="center" ></th>
				<th width="center" align="center" ></th>
				<th width="center" align="center" ></th>
				<th width="center" align="center" ></th>
				<th width="center" align="center" ></th>
				<th width="center" align="center" ></th>
				<th width="center" align="center" ></th>
				<th width="center" align="center" >0.00</th>
			</tr>
			
		</thead>
				
		<tbody>
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
		</script></strong> <span>条，共26条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>
</div>

<script type="text/javascript">
function  toGetHangYe() {
	var cy=$("#cy").val(); 
	if (cy!='') {
		$.ajax({
			 type: "POST",
	         url:'${basePath }web/modelShow/toGetHangYe?cy='+cy,
	         dataType: "json",
	         async: false,
	         success: function(data) {
	            toShowHy(data);
	         }
			  
			});
		
        
	}
}

function toShowHy(obj){
	var hy=document.getElementById('hy'); 
	hy.options.length=0; 
	hy.options.add(new Option("请选择行业大类","")); 
	for (var i = 0; i < obj.length; i++) {
		hy.options.add(new Option(obj[i].code,obj[i].value)); 	
	}
	
	
}
</script>

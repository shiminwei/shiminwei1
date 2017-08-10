<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }web/modelShow/fenHangYeList">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage }" />
	<input type="hidden" name="name" value="${bean.name }" />
	<input type="hidden" name="isAboveScale" value="${bean.isAboveScale }" />
	<input type="hidden" name="isCanvassBuisiness" value="${bean.isCanvassBuisiness }" />
</form>
<form onsubmit="return navTabSearch(this);" action="${basePath }web/modelShow/fenHangYeList" method="post" id="companyForm" name="searchForm">
<div class="pageHeader">
<div class="searchBar">
	<h1 align="center" style="font-size: 30px;margin-bottom: -12px">${year }年<c:if test="${newMonth!=null&&newMonth!=''}">${newMonth}月</c:if>全市企业分行业纳税情况表</h1>
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
				<th align="center" style="font-weight: 900;font-size: 14px" rowspan="3" width="25%">行业</th>
				<th align="center" style="font-weight: 900;font-size: 14px" colspan="3" rowspan="2">本月完成数</th>
				<th align="center" style="font-weight: 900;font-size: 14px" colspan="9">累计完成情况</th>
			</tr>
			<tr>
				<th width="center" align="center" colspan="3">完成数</th>
				<th width="center" align="center" colspan="3">同期增减额</th>
				<th width="center" align="center" colspan="3">增减(%)</th>
			</tr>
			
			
			<tr>
				<th width="center" align="center" >小计</th>
				<th width="center" align="center" >中央</th>
				<th width="center" align="center" >地方</th>
				<th width="center" align="center" >合计</th>
				<th width="center" align="center" >中央</th>
				<th width="center" align="center" >地方</th>
				<th width="center" align="center" >合计</th>
				<th width="center" align="center" >中央</th>
				<th width="center" align="center" >地方</th>
				<th width="center" align="center" >合计</th>
				<th width="center" align="center" >中央</th>
				<th width="center" align="center" >地方</th>
			</tr>
			
		</thead>
				<tr>
				<td align="left" >一，第一产业</td>
			<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			
			
			</tr>
				<tr>
				<td align="left">二，第二产业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>
				<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(一)&nbsp;采矿业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>
							<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(二)&nbsp;制造业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>
				<tr>
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其中：酒，饮料和精制茶制造业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>
			<tr>

				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;纺织业，纺织服装</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>
				<tr>
			
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;化学原料和化学制品制造业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>
				<tr>
	
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;非金属矿物制造业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>
		 	<tr>
		
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(三)&nbsp;电力，热力，燃气及水的生产和供应业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>
										<tr>

				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(四)&nbsp;建筑业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>
						<tr>
		
				<td >三，第三产业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>
											<tr>
				
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(一)&nbsp;批发和零售业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>
											<tr>
			
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(二)&nbsp;交通运输，仓储和邮政业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>
			<tr>
			
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(三)&nbsp;住宿和餐饮业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>
				<tr>
	
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(四)&nbsp;信息传递，软件和信息技术服务业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>
			<tr>
		
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(五)&nbsp;金融业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>
			<tr>
				
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(六)&nbsp;房地产业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>
			<tr>
				
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(七)&nbsp;租赁和商务服务业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>	
			<tr>
				
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(八)&nbsp;科学研究和技术服务业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>		
		
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(九)&nbsp;水利环境和公共设施管理业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>	
			
				<td  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(十)&nbsp;居民服务，修理和其他服务业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>		
			
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(十一)&nbsp;教育</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>				
			
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(十二)&nbsp;卫生和社会工作</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>	
			
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(十三)&nbsp;文化，体育和娱乐业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>				
				
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(十四)&nbsp;公共管理，社会保障和社会组织</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>					
				
				<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(十五)&nbsp;其他行业</td>
				<c:forEach begin="0" end="11">
			<td ></td>
			</c:forEach>
			</tr>		
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

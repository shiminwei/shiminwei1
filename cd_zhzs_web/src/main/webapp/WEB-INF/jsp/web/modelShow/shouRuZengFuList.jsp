<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage }" />
	<input type="hidden" name="name" value="${bean.name }" />
	<input type="hidden" name="isAboveScale" value="${bean.isAboveScale }" />
	<input type="hidden" name="isCanvassBuisiness" value="${bean.isCanvassBuisiness }" />
</form>
<form onsubmit="return navTabSearch(this);" action="" method="post" id="companyForm" name="searchForm">
<div class="pageHeader">
	<div class="searchBar" style="margin-bottom: -4px;margin-top: -9px">
	
	<h1 align="center" style="font-size: 30px;margin-bottom: -12px">全省各市财政收入，增幅排名表</h1>
			<h1 align="right" style="font-size: 16px">单位：万元</h1>
	</div>
</div>

<div class="panelBar" style="margin-top: -2px">
	<ul class="toolBar">
		<li><a title="模板下载" rel=""
			href="javascript:void(0)"
			class="down" ><span>导出数据</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="117">
		
		<thead>
			<tr>
				<th align="center" >序号</th>
				<th align="center" style="font-weight: 900">地区</th>
				<th align="center" style="font-weight: 900">财政总收入(万元)</th>
				<th align="center" style="font-weight: 900">收入位次</th>
				<th align="center" style="font-weight: 900">地区</th>
				<th align="center" style="font-weight: 900">较上年同期增幅(%)</th>
				<th align="center" style="font-weight: 900">增幅位次</th>
			</tr>
		</thead>		
		<tbody>
			<tr>
				<th align="center">1</th>
				<th align="center">合肥市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">2</th>
				<th align="center">芜湖市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">3</th>
				<th align="center">安庆市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">4</th>
				<th align="center">蚌埠市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">5</th>
				<th align="center">滁州市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">6</th>
				<th align="center">阜阳市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">7</th>
				<th align="center">马鞍山市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">8</th>
				<th align="center">宣城市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">9</th>
				<th align="center">淮南市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">10</th>
				<th align="center">六安市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">11</th>
				<th align="center">铜陵市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">12</th>
				<th align="center">亳州市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">13</th>
				<th align="center">宿州市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				 <th align="center">14</th>
				<th align="center">池州市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">15</th>
				<th align="center">淮北市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">16</th>
				<th align="center">黄山市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">17</th>
				<th align="center">安徽省</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>		
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
		</script></strong> <span>条，共17条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>
</div>
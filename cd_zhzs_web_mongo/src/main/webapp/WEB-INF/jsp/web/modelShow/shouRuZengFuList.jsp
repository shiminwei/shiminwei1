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
<div class="panelBar">
	<h1 width="center" align="center" style="font-size: 22px">全省各市财政收入，增幅排名</h1>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a title="模板下载" rel=""
			href="javascript:void(0)"
			class="down" ><span>导出数据</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="81">
		
		<thead>
			<tr>
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
				<th align="center">合肥市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">芜湖市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">安庆市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">蚌埠市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">滁州市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">阜阳市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">马鞍山市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">宣城市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">淮南市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">六安市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">铜陵市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">亳州市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">宿州市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">池州市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">淮北市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
				<th align="center">黄山市</th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
				<th align="center"></th>
			</tr>
			<tr>
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
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value,pageNum:1})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<strong><script>  
                $("select[name='numPerPage']").val('${pageList.numPerPage}');  
            </script></strong>  
			<span>条，共${pageList.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${pageList.totalCount}" numPerPage="${pageList.numPerPage}" pageNumShown="10" currentPage="${pageList.pageNum}"></div>
	</div>
</div>
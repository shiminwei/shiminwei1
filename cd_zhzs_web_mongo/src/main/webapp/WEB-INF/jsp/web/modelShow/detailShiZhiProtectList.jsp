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
	<h1 width="center" align="center" style="font-size: 25px">xxx企业纳税情况明细</h1>
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
				<th align="center" style="font-weight: 900">序号</th>
				<th align="center" style="font-weight: 900">日期</th>
				<th align="center" style="font-weight: 900">税款合计</th>
				<th align="center" style="font-weight: 900">消费税</th>
				<th align="center" style="font-weight: 900">车辆购置税</th>
				<th align="center" style="font-weight: 900">增值税</th>
				<th align="center" style="font-weight: 900">企业所得税</th>
				<th align="center" style="font-weight: 900">个人所得税</th>
				<th align="center" style="font-weight: 900">房产税</th>
				<th align="center" style="font-weight: 900">契税</th>
				<th align="center" style="font-weight: 900">营业税</th>
				<th align="center" style="font-weight: 900">企业所得税</th>
				<th align="center" style="font-weight: 900">个人所得税</th>
				<th align="center" style="font-weight: 900">土地增值税</th>
				<th align="center" style="font-weight: 900">教育费附加</th>
				<th align="center" style="font-weight: 900">印花税</th>			
			</tr>
		</thead>		
		<tbody>
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
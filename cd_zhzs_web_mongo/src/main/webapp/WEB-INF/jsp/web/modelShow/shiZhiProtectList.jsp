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
	<h1 width="center" align="center" style="font-size: 25px;">市直单位招商项目汇总表</h1>
	
</div>
<div class="pageHeader">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label style="width: 120px">成立时间：</label>
				<select class="combox" name="" style="width: 90px">				
					<option value="" >--请选择--</option>
					<option value="1">2014</option>
					<option value="0">2015</option>
					<option value="0">2016</option>
					<option value="0">2017</option>
				</select>
			</li>
			<li>
				<label>单位名称：</label>
				<input type="text" name="name" value="${bean.name }"/>
			</li>
			<li>
				<label>项目名称：</label>
				<input type="text" name="name" value="${bean.name }"/>
			</li>
			<li>
				<label>税额不低于：</label>
				<input type="text" name="name" value="${bean.name }"/>
			</li>
		</ul>
	</div>
</div>

<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" onclick="cleanForm('companyForm','navTab')" rel=""
			targettype="navTab"><span>清空查询</span></a></li>
		<li><a class="add" href="javascript:void(0)" target="dialog" width="750" height="600" rel=""><span>新增</span></a></li>
		<li><a title="确实要删除这些企业信息吗?" target="selectedTodo"
			postType="String" href="javascript:void(0)"
			class="delete"  rel="id" ><span>批量删除</span></a></li>
		<li><a title="模板下载" rel=""
			href="javascript:void(0)"
			class="down" ><span>导出数据</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="118">
		<thead>
			<tr>
				<th align="center" style="font-weight: 900">序号</th>
				<th align="center" style="font-weight: 900">申报单位</th>
				<th align="center" style="font-weight: 900">项目名称</th>
				<th align="center" style="font-weight: 900">公司名称</th>
				<th align="center" style="font-weight: 900">总投资(万元)</th>
				<th align="center" style="font-weight: 900">到位资金(万元)</th>
				<th align="center" style="font-weight: 900">企业纳税</th>
				<th align="center" style="font-weight: 900">落地户</th>
				<th align="center" style="font-weight: 900">联系电话</th>
				<th align="center" style="font-weight: 900">投资方名称</th>
				<th align="center" style="font-weight: 900">投资方本地注册公司名称</th>
				<th align="center" style="font-weight: 900">公司地址</th>
				<th align="center" style="font-weight: 900">到资合计(万元)</th>
				<th align="center" style="font-weight: 900">成立时间</th>
				<th align="center" style="font-weight: 900">加分</th>
				<th align="center" style="font-weight: 900">备注</th>		
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
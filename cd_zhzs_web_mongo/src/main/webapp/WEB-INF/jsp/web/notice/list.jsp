<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }web/notice/list>
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="demo_page1.html" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>标题名称：</label>
				<input type="text"/>
			</li>
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
				</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th>标题名称</th>
				<th>内容简介</th>
				<th>附件</th>
				<th>发布日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<tr target="sid_user" rel="1">
				<td>地税模板调整通知</td>
				<td>关于地税模板调整通知说明</td>
				<td><a>地税局净入库信息模板.xls</a></td>
				<td>2016-10-20</td>
				<td>
					<a class="button" href="${basePath }web/notice/detail?noticeId=1"target="dialog" rel="dlg_page2" width="800" height="600" fresh="true" title="查看详情" width="800" height="480"><span>查看详情</span></a>
				</td>
			</tr>
			<tr target="sid_user" rel="1">
				<td>地税模板调整通知</td>
				<td>关于地税模板调整通知说明</td>
				<td><a>地税局净入库信息模板.xls</a></td>
				<td>2016-10-20</td>
				<td>
					<a class="button" href="${basePath }web/notice/detail?noticeId=1"target="dialog" rel="dlg_page2" width="800" height="600" fresh="true" title="查看详情" width="800" height="480"><span>查看详情</span></a>
				</td>
			</tr>
			<tr target="sid_user" rel="1">
				<td>地税模板调整通知</td>
				<td>关于地税模板调整通知说明</td>
				<td><a>地税局净入库信息模板.xls</a></td>
				<td>2016-10-20</td>
				<td>
					<a class="button" href="${basePath }web/notice/detail?noticeId=1"target="dialog" rel="dlg_page2" width="800" height="600" fresh="true" title="查看详情" width="800" height="480"><span>查看详情</span></a>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共${totalCount}条</span>
		</div>

		<div class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="1"></div>

	</div>
</div>

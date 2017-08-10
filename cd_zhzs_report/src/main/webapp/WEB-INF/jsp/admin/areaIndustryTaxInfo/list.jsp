<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<form id="pagerForm" method="post"
	action="${basePath }admin/areaIndustryTaxInfo/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input type="hidden"
		name="numPerPage" value="${pageList.numPerPage }" />
</form>

<form onsubmit="return navTabSearch(this,'areaIndustryTaxInfoManage');"
action="${basePath }admin/areaIndustryTaxInfo/list" method="post"
id="areaIndustryTaxInfo" name="searchForm">
<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
				  <label>名称：</label><input type="text" name="name"value="${bean.name }" />
				</li>
				<li>
				  <label>编号：</label><input type="text" name="aitiId"value="${bean.aitiId }" />
				</li>
			</ul>
		</div>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" onclick="cleanForm('areaIndustryTaxInfo','navTab')" rel="areaIndustryTaxInfoManage"
			targettype="navTab"><span>清空查询条件</span></a></li>
		<li><a class="add" href="${basePath }admin/areaIndustryTaxInfo/toSaveOrUpdate?isadd=1"
			target="dialog" rel="addNotice" width="700" height="350"><span>新增</span></a></li>
		<li><a title="确定要删除这些吗?" target="selectedTodo"
			postType="String" href="${basePath }admin/areaIndustryTaxInfo/toDelete" class="delete"
			rel="aitiId"><span>批量删除</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="91">
		<thead>
			<tr>
				<th align="center" width="6%"><input type="checkbox"
					group="aitiId" class="checkboxCtrl"></th>
				<th align="center" style="font-weight: 900">编号</th>
				<th align="center" style="font-weight: 900">名称</th>
				<th align="center" style="font-weight: 900">地区</th>
				<th align="center" style="font-weight: 900">行业</th>
				<th align="center" style="font-weight: 900">税种</th>
				<th align="center" style="font-weight: 900">税率(%)</th>
				<th align="center" style="font-weight: 900">备注</th>
				<th align="center" style="font-weight: 900" width="20%">操作</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach var="list" items="${pageList.result}" varStatus="status">
				<tr>
					<td align="center"><input name="aitiId" type="checkbox"
						value="${list.aitiId }"></td>
					<td align="center">${list.aitiId }</td>
					<td align="center">${list.name }</td>
					<td align="center">${areaType[list.areaId] }</td>
					<td align="center">${list.industryname }</td>
					<td align="center">${list.taxname }</td>
					<td align="center">${list.taxrate }%</td>
					<td align="center">${list.remark }</td>				
					<td align="center">
						<a style="margin-left: 13%" class="button" href="${basePath }admin/areaIndustryTaxInfo/detail?aitiId=${list.aitiId }" mask="true" 
							target="dialog" rel="dlg_page10" width="700" height="350" fresh="true" title="查看详情" width="800" height="480" close="reloadNavTab" 
							param="{rel:'noticeQuery'}"><span>查看详情</span></a>					
						<a style="margin-left: 30px" title="修改" target="dialog" width="700" height="350"
							href="${basePath }admin/areaIndustryTaxInfo/toSaveOrUpdate?isadd=0&aitiId=${list.aitiId }"
							class="btnEdit">编辑</a>
						<a style="margin-left: 25px" title="确定要删除该条信息吗?" target="ajaxTodo"
							href="${basePath }admin/areaIndustryTaxInfo/toDelete?aitiId=${list.aitiId }"
							class="btnDel" rel="areaIndustryTaxInfoManage">删除</a></td> 
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/common/page.jsp"%>
</div>
<script type="text/javascript">
//查询
function toAreaIndustryTaxInfo() {
	$("#areaIndustryTaxInfo").submit();
}
//根据rel刷新指定的navtTab
function reloadNavTab(param){
	navTab.reloadFlag(param.rel); //根据rel刷新navTab
	return true;
}
</script>

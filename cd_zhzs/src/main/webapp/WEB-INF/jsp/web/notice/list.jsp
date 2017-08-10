<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }web/notice/list">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
</form>
<form onsubmit="return navTabSearch(this);" action="${basePath }web/notice/list" method="post" name="searchForm"  id="searchForm">
<div class="pageHeader">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>标题名称：</label>
				<input type="text" name="noticeTitle" value="${noticeTitle }"/>
			</li>
		</ul>
	</div>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" id="abyexport" href="#"
			onclick="cleanForm('searchForm','navTab')"><span style="">清空查询</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="90">
		<thead>
			<tr>
				<th width="10%" align="center">发布日期</th>
				<th width="15%" align="center">公告标题</th>
				<th width="35%" align="center">内容概要</th>
				<th width="25%" align="center">附件</th>
				<th width="15%" align="center">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.result}" var="item">
					<tr  <c:if test="${item.isReaded eq 0 }">style='color: #ff0000;'</c:if>>
						<td align="center"><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd" /></td>
						<td align="center">${item.noticeTitle }</td>
						<td align="center">${item.noticeSynopsis }</td>
						<td align="center"><c:forEach items="${item.sysNoticeFileList }" var="noticeFile"><a href="${basePath }/web/notice/toDownload?fileName=${noticeFile.filePath }" target="_blank">${noticeFile.filePath }</a></c:forEach></td>
						<td><a style="margin-left: 35.5%" class="button" href="${basePath }web/notice/detail?noticeId=${item.noticeId }" mask="true" target="dialog" rel="dlg_page10" width="800" height="600" fresh="true" title="查看详情" width="800" height="480" close="reloadNavTab" param="{rel:'noticeQuery'}"><span>查看详情</span></a></td>
					</tr>
				</c:forEach>
		</tbody>
	</table>
<!-- 分页 -->
<%@include file="/common/page.jsp" %>
<script type="text/javascript">
function toQueryDada(formName) {
	return navTabSearch($(this).find("form[name='"+formName+"']"));
}
</script>
</div>

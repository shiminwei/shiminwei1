<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }admin/notice/list">
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${basePath }admin/notice/list" method="post" id="searchForm" name="searchForm">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>标题名称：</label>
				<input type="text" name="noticeTitle1" value="${noticeTitle1 }"/>
			</li>
			<dd><div class="button"><div class="buttonContent"><button>查询</button></div></div></dd>
			<dd><div style="margin-left: 17px" class="button"><div class="buttonContent"><button onclick="cleanForm('searchForm','navTab')" >清空</button></div></div></dd>
		</ul>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${basePath }admin/notice/toAdd" target="navTab" rel="addNotice"><span>新增公告</span></a></li>
			<li><a title="确实要删除这些公告吗?" target="selectedTodo"
				postType="String" href="${basePath }admin/notice/deletes?ids=${list.noticeId}"
				class="delete"  rel="notice_Ids" ><span>批量删除</span></a></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="91">
		<thead>
			<tr>
				<th align="center"><input type="checkbox" group="notice_Ids" class="checkboxCtrl"></th>
				<th style="font-weight: 900">发布日期</th>
				<th style="font-weight: 900">公告标题</th>
				<th style="font-weight: 900">内容概要</th>
				<th style="font-weight: 900">附件</th>
				<th style="font-weight: 900" width="20%">操作</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach var="list" items="${pageList.result}" >
				<tr  target="notice_Id" rel="${list.noticeId}">				
				<td align="center"><input name="notice_Ids" type="checkbox" value="${list.noticeId}"></td>
				<td align="center"><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd" /></td>
				<td align="center">${list.noticeTitle }</td>
				<td align="center">${list.noticeSynopsis }</td>
				<td align="center"><c:forEach items="${list.sysNoticeFileList }" var="noticeFile"><a href="${basePath }/web/notice/toDownload?fileName=${noticeFile.filePath }" target="_blank">${noticeFile.filePath }</a></c:forEach></td>
				<td>
					<a style="margin-left: 13%" class="button" href="${basePath }web/notice/detail?noticeId=${list.noticeId }" mask="true" target="dialog" rel="dlg_page10" width="800" height="600" fresh="true" title="查看详情" width="800" height="480" close="reloadNavTab" param="{rel:'noticeQuery'}"><span>查看详情</span></a>
					
					<a style="margin-left: 30px" title="修改公告信息" target="navTab"
						href="${basePath }admin/notice/toEdit?noticeId=${list.noticeId}"
						class="btnEdit">编辑</a>

					<a style="margin-left: 25px" title="确定要删除该公告吗?" target="ajaxTodo"

						href="${basePath }admin/notice/deletes?notice_Ids=${list.noticeId}"
						class="btnDel" rel="userManage">删除</a> 
				</td>
			</tr>
			</c:forEach>
			
		</tbody>
	</table>
<!-- 分页 -->
<%@include file="/common/page.jsp" %>
</div>

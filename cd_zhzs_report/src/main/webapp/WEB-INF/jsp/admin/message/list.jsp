<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }admin/message/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage }" />
</form>

<form onsubmit="return navTabSearch(this);" action="${basePath }admin/message/list" method="post" id="sysMessage" name="searchForm">
<div class="pageHeader">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>标题：</label>
				<input type="text" name="messageTitle" value="${bean.messageTitle }"/>
			</li>
		</ul>
	</div>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" onclick="cleanForm('sysMessage','navTab')" rel="sysMessageManage"
			targettype="navTab"><span>清空查询条件</span></a></li>
		<li><a class="add" href="${basePath }admin/message/toAddOrEdit" target="dialog" width="800" height="400" rel="addMessage"><span>新增消息</span></a></li>
		<li><a title="确实要删除这些消息吗?" target="selectedTodo"
			postType="String" href="${basePath }admin/message/toDelete?"
			class="delete"  rel="messageId" ><span>批量删除</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="91">
		<thead>
			<tr>
				<th align="center"  width="6%"><input type="checkbox" group="messageId" class="checkboxCtrl"></th>
				<th align="center" style="font-weight: 900" width="15%">标题</th>
				<th align="center" style="font-weight: 900" width="25%">内容</th>
				<th align="center" style="font-weight: 900" width="10%">创建时间</th>
				<th align="center" style="font-weight: 900" width="10%">创建人</th>
				<th align="center" style="font-weight: 900" width="24%">操作</th>
			</tr>
		</thead>		
		<tbody>
			<c:forEach var="list" items="${pageList.result}" >
				<tr  target="message_Id" rel="${list.messageId}">				
				<td align="center"><input name="messageId" type="checkbox" value="${list.messageId}"></td>
				<td align="center">${list.messageTitle }</td>
				<td align="center">${list.messageContent }</td>
				<td align="center"><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd" /></td>
				<td align="center">${list.userName }</td>
				<td align="center">				
					<a title="修改消息" target="dialog" width="800" height="400"  
						href="${basePath }admin/message/toAddOrEdit?messageId=${list.messageId}">修改</a>&nbsp;&nbsp;&nbsp;
					<a title="发送消息界面" target="navTab" 
						href="${basePath }admin/message/toSendMessage?messageId=${list.messageId}"
						width="800" height="400" rel="sendmessage">发送</a> &nbsp;&nbsp;&nbsp;
					<a href="${basePath }admin/message/tohistory?messageId=${list.messageId }" mask="true" target="dialog" rel="dlg_page10" width="800" height="400" fresh="true" 
						title="查询发送历史记录" close="reloadNavTab" param="{rel:'messageQuery'}"><span>查询发送历史记录</span></a>&nbsp;&nbsp;&nbsp;
					<a title="确定要删除该消息吗?" target="ajaxTodo"
						href="${basePath }admin/message/toDelete?messageId=${list.messageId}" rel="sysMessageManage">删除</a> 
				</td>
			</tr>
			</c:forEach>		
		</tbody>
	</table>
<!-- 分页 -->
<%@include file="/common/page.jsp" %>
</div>
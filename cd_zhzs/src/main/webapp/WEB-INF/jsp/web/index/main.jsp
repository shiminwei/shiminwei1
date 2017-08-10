<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
	<div class="panel" defH="250">
		<h1>系统公告</h1>
		<div>
			<table class="list" style="width:100%">
				<thead>
					<tr>
						<th width="10%" align="center">发布时间</th>
						<th width="10%" align="center">公告标题</th>
						<th width="35%" align="center">内容概要</th>
						<th width="28%" align="center">附件</th>
						<th width="15%" align="center">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${pageList.result}" var="item">
					<tr  <c:if test="${item.isReaded eq 0 }">style='color: #ff0000;'</c:if>>
						<td align="center"><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd" /></td>
						<td align="center">${item.noticeTitle }</td>
						<td align="center">${item.noticeSynopsis }</td>
						<td style="text-align:center;"><c:forEach items="${item.sysNoticeFileList }" var="noticeFile"><a href="${basePath }/web/notice/toDownload?fileName=${noticeFile.filePath }" target="_blank">${noticeFile.filePath }</a></c:forEach></td>
						<td><a style="margin-left: 35.5%" class="button" href="${basePath }web/notice/detail?noticeId=${item.noticeId }" mask="true" target="dialog" rel="dlg_page10" width="800" height="600" fresh="true" title="查看详情" close="reloadNavTab" param="{rel:'main'}"><span>查看详情</span></a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

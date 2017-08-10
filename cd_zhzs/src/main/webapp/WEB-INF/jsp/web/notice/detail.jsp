<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<div class="page unitBox">
	<div class="accountInfo">
		<p><span style="width:100%; height:20px; line-height:20px; text-align:center; float:left;">${sysNotice.noticeTitle }</span></p>
		<p style="color: red;">附件&nbsp;&nbsp:&nbsp;&nbsp;&nbsp<c:forEach items="${sysNotice.sysNoticeFileList }" var="item"><a style="color: red;" href="${basePath }/web/notice/toDownload?fileName=${item.filePath }" target="_blank">${item.filePath }</a></c:forEach></p>
	</div>
	<div class="accountInfo">
		<p>概述:${sysNotice.noticeSynopsis }</p>
	</div>
	<div class="pageFormContent">
		<pre style="margin:5px;line-height:1.4em;white-space: pre-wrap;word-wrap: break-word;">
			　${sysNotice.noticeContent }
		</pre>
		</div>
	</div>
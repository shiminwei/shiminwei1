<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<%@ taglib prefix="zd" uri="../../../../WEB-INF/zdtag.tld"%>
<div class="pageContent sortDrag" selector="h1">
	<div class="panel" defH="320">
		<h1>系统公告</h1>
		<div>
			<table class="list" style="width: 100%">
				<thead>
					<tr>
						<th width="10%" align="center">发布时间</th>
						<th width="10%" align="center">公告标题</th>
						<th width="50%" align="center">内容概要</th>
						<th width="20%" align="center">附件</th>
						<th width="10%" align="center">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pageList.result}" var="item">
						<tr
							<c:if test="${item.isReaded eq 0 }">style='color: #ff0000;'</c:if>>
							<td align="center"><fmt:formatDate
									value="${item.createTime }" pattern="yyyy-MM-dd" /></td>
							<td align="center">${item.noticeTitle }</td>
							<td align="center">${item.noticeSynopsis }</td>
							<td style="text-align: center;"><c:forEach
									items="${item.sysNoticeFileList }" var="noticeFile">
									<a
										href="${basePath }/web/notice/toDownload?fileName=${noticeFile.filePath }"
										target="_blank">${noticeFile.filePath }</a>
								</c:forEach></td>
							<td><a style="margin-left: 35.5%" class="button"
								href="${basePath }web/notice/detail?noticeId=${item.noticeId }"
								mask="true" target="dialog" rel="dlg_page10" width="800"
								height="600" fresh="true" title="查看详情" close="reloadNavTab"
								param="{rel:'main'}"><span>查看详情</span></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="panel" defH="400">
		<h1>数据报送</h1>
		<div>
			<table class="list" width="100%">
				<thead>
					<tr>
						<th align="center">报送名称</th>
						<th align="center">报送周期</th>
						<th align="center">下载模板(点击名称下载)</th>
						<th align="center">本期是否报送</th>
						<th align="center">报送</th>
						<th align="center">重报</th>
						<th align="center">补报</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="list" items="${excelTemplatePage.result}">
						<tr>
							<td align="center"
								<c:if test="${list.nowIsReport == 0}">style="color: red" </c:if>>${list.name}</td>
							<td align="center"
								<c:if test="${list.nowIsReport == 0}">style="color: red" </c:if>><zd:show
									type="zq" value="${list.zq}"></zd:show></td>
							<td align="center"><a target="dwzExport" targettype="navTab"
								href="${basePath }web/dataReport/toDownload?templateId=${list._id}"
								<c:if test="${list.nowIsReport == 0}">style="color: red" </c:if>>${list.name}.xls</a></td>
							<td align="center"
								<c:if test="${list.nowIsReport == 0}">style="color: red" </c:if>>${list.nowIsReport == 1?'是':'否'}</td>
							<td align="center">
								<div style="margin: 0 auto; width: 46px;">
									<a class="button"
										href="${basePath }web/dataReport/toReport?reportType=1&reportPeroid=${list.zq}&templateName=${list.name}&reportPeroid=${list.zq}"
										target="navTab" rel="toReportPage"><span>报送</span></a>
								</div>
							</td>
							<td align="center">
								<div style="margin: 0 auto; width: 46px;">
									<a class="button"
										href="${basePath }web/dataReport/toReport?reportType=2&reportPeroid=${list.zq}&templateName=${list.name}&reportPeroid=${list.zq}"
										target="navTab" rel="toReportPage"><span>重报</span></a>
								</div>
							</td>
							<td align="center">
								<div style="margin: 0 auto; width: 46px;">
									<a class="button"
										href="${basePath }web/dataReport/toReport?reportType=3&reportPeroid=${list.zq}&templateName=${list.name}&reportPeroid=${list.zq}"
										target="navTab" rel="toReportPage"><span>补报</span></a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<script type="text/javascript">
	function downloadExcel() {

	}
</script>

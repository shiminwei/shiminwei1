<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<html>
<body>
<form class="pageForm required-validate" method="post" action="${basePath }/admin/notice/${actionMethod}" enctype="multipart/form-data" onsubmit="return iframeCallback(this,navTabAjaxDone)" novalidate="novalidate">
<div class="pageFormContent nowrap" layouth="97" style="height: 500px;overflow: auto;">
	<input type="hidden" name="noticeId" value="${sysNotice.noticeId }"> 
	<dl>
		<dt style="font-weight: 900">公告标题：</dt>
		<dd><input name="noticetitle" type="text" style="width:400px;" value="${sysNotice.noticeTitle }" class="required"/></dd>
	</dl >
	
	<dl <c:if test="${actionMethod=='edit' }">style="display:none"</c:if>>
		<dt style="font-weight: 900;margin-top:20px;">公告附件：</dt>
		<dd style="margin-top:20px"><input name="uploadExcel" type="file" accept=".xls,.xlsx"   value="${sysNoticeFile.filePath }"/></dd>
	</dl>
	
	<dl <c:if test="${actionMethod=='add' }">style="display:none"</c:if>>
		<dt style="font-weight: 900;margin-top:20px">已上传公告附件：</dt>
		<dd style="font-weight: 900; margin-top:25px">
			<c:forEach items="${sysNotice.sysNoticeFileList }" var="item"><a style="color:red;" href="${basePath }/web/notice/toDownload?fileName=${item.filePath }" target="_blank">${item.filePath }&nbsp;&nbsp;&nbsp;&nbsp(点击下载)</a></c:forEach>
		</dd>
	</dl>
	
	<dl <c:if test="${actionMethod=='add' }">style="display:none"</c:if> >
		<dt style="font-weight: 900;margin-top:20px">重新上传公告附件：</dt>
		<dd style="margin-top:20px;"><input name="uploadExcel2" type="file" accept=".xls,.xlsx" /></dd>
	</dl>
	
	
	<d1>
		<dt style="font-weight: 900;margin-top:20px">公告范围：</dt>
		<dd style="width:800px">
			<c:forEach items="${departmentList }" var="department">
			<label style="margin-top:20px">
			<input type="checkbox" name="c1" <c:forEach items="${sysNotice.sysNoticeDepartmentList }" var="hasDepartment">
			<c:if test="${hasDepartment.departmentId eq department.departmentId  }">checked="checked"</c:if></c:forEach> value="${department.departmentId }" />${department.departmentName }
			</label>
			</c:forEach>
		</dd>
	</d1>
	
	<dl>
		<dt style="font-weight: 900;margin-top:50px">公告概述：</dt>
		<dd style="margin-top:50px;text-align:center;">
		<input name="noticeSynopsis" style="width:400px" type="text" value="${sysNotice.noticeSynopsis }"/><span>(选填)</span></dd>
	</dl>
	
	<dl>
		<dt style="font-weight: 900;margin-top:20px">公告内容：</dt>
		<dd style="margin-top:20px">
		<textarea class="required" class="editor" name="description" rows="20" cols="110" tools="mini" >${sysNotice.noticeContent }</textarea></dd>
	</dl>
	
</div>
	<div style="margin-top: 42px" class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
			<li style="margin-left: 10px;float: left">
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
		</li>
		</ul>
	</div>
</form>

<script type="text/javascript">
	
	
</script>

</body>
</html>




<input type="radio" />是
<input type="radio" />否









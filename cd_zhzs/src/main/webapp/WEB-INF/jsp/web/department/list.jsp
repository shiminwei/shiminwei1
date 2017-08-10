<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<h2 class="contentTitle">查看组织机构</h2>

<div id="resultBox"></div>

<div style=" float:left; display:block; margin:20px;overflow:auto; width:210px; height:82%; overflow:auto; border:solid 1px #CCC; line-height:21px; background:#FFF;">

<ul class="tree treeFolder  expand">
	<c:forEach items="${areaList }" var="area">
	<li><a style = "font-weight: 900">${area.areaName }</a>
		<ul>
			<c:forEach items="${area.departmentList }" var="department">
			<li><a tname="name" tvalue="${department.departmentId }" >${department.departmentName }</a></li>
			</c:forEach>
		</ul>
	</li>
	</c:forEach>
</ul>
</div>
</body>
</html>
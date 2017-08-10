<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<title>国标行业</title>
<form id="pagerForm" method = "post" action="${basePath }admin/gbhy/toList">
	<input type="hidden" name="pageNum" value="1"/>
	<input type="hidden" name="numPerPage" value="${model.numPerPage}">	
	<input type="hidden" name="name_cy" value="${name_cy}">
	<input type="hidden" name="name_dl" value="${name_dl}">
	<input type="hidden" name="name_ml" value="${name_ml}">
</form>

<div class="pageHeader">
	<form method="post" action="" 
			onsubmit="return navTabSearch(this);">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>产业类型:</label> <select name="name_cy"><option>产业类型</option><option <c:if test="${ name_cy == '第一产业'}"> selected="selected" </c:if> value="第一产业">第一产业</option>
				<option <c:if test="${ name_cy == '第二产业'}"> selected="selected"</c:if> value="第二产业">第二产业</option>
				<option <c:if test="${ name_cy == '第三产业'}"> selected="selected"</c:if> value="第三产业">第三产业</option> </select> </li>
				<li><label>产业门类:</label> <input  type="text" name="name_ml" value="${name_ml }" /></li>
				<li><label>产业大类:</label> <input  type="text" name="name_dl" value="${name_dl }"/></li>
				<dd><div  style="margin-left:30px" class="button"><div class="buttonContent"><button>查询</button></div></div></dd>
				<dd><div style="margin-left: 17px" class="button"><div class="buttonContent" onclick="cleanForm('searchForm','navTab')"><button>清空</button></div></div></dd>
			</ul>		
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="list" width="100%" layoutH="63">
		<thead>
			<tr>
				<th width="center" align="center">序号</th>
				<th width="150">行业编码</th>
				<th>产业类型</th>
				<th>产业门类</th>
				<th>产业大类</th>
				<th>产业中类</th>
				<th>产业小类</th>
			</tr>	
		</thead>
		<tbody>
			<c:forEach items="${pageList.result}" var = "list" varStatus="status">
				<tr>
					<td align="center">${status.index+1 }</td>
					<td align="center">${list.idGbhy }</td>
					<td align="center">${list.nameCy }</td>
					<td align="center">${list.nameMl }</td>
					<td align="center">${list.nameDl }</td>
					<td align="center">${list.nameZl }</td>
					<td align="center">${list.nameGbhy }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<!-- 分页 -->
<%@include file="/common/page.jsp" %>
</div>













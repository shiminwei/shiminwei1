<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<div class="pageHeader">
	<form method="post" action="${basePath }web/cy/list"
		onsubmit="return navTabSearch(this);">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>年度：</label> <select class="combox" id="startDate"
					name="startDate">
						<option value="2014"
							<c:if test="${startDate==2014 }">selected="selected"</c:if>>2014</option>
						<option value="2015"
							<c:if test="${startDate==2015 }">selected="selected"</c:if>>2015</option>
						<option value="2016"
							<c:if test="${startDate==2016 }">selected="selected"</c:if>>2016</option>
						<option value="2017"
							<c:if test="${startDate==2017 }">selected="selected"</c:if>>2017</option>
				</select></li>
				
				<li><label>行业类型：</label> <select class="combox" id="type"
					name="type">
						<option value="1"
							<c:if test="${type==1 }">selected="selected"</c:if>>产业分类</option>
						<option value="2"
							<c:if test="${type==2 }">selected="selected"</c:if>>行业门类</option>
						<option value="3"
							<c:if test="${type==3 }">selected="selected"</c:if>>行业大类</option>
				</select></li>
				<dd>
					<div style="margin-left: -30px" class="button">
						<div class="buttonContent">
							<button>查询</button>
						</div>
					</div>
				</dd>
			</ul>
		</div>
	</form>
</div>
<div class="pageContent" style="width: auto; overflow: x:scroll;">
	<table class="list" width="100%" layoutH="50">
		<thead>
			<tr>

				<!-- 	<th align="center">序号</th> -->
				<th align="center" style="color: red">产业分类</th>
				<th align="center" style="color: red">行业门类</th>
				<th align="center" style="color: red">行业大类</th>
				<th align="center" style="color: blue;">税户</th>
				<th align="center" style="color: blue;">应缴税额(万元)</th>
				<th align="center" style="color: blue;">实缴税额(万元)</th>
				<th align="center" style="color: blue;">收入潜力(万元)</th>

			</tr>
		</thead>
		<tbody>

			<tr height="50">
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center" style="color: green; font-size: 15px">合计：${bean.shuiHu }</td>
				<td align="center" style="color: green; font-size: 15px">合计：${bean.yjSe }</td>
				<td align="center" style="color: green; font-size: 15px">合计：${bean.sjSe }</td>
				<td align="center" style="color: green; font-size: 15px">合计：${bean.ljSe}</td>
			</tr>
			<c:forEach var="list" items="${gbhyList}" varStatus="status">
				<tr>
					<%-- 			<td align="center">${status.index +1}</td> --%>
					<td align="center" style="color: red">${list.nameCy }<img  src="${basePath }static/images/down.jpg" width="5%" height="5%" align="right" > </td>
					<td align="center" style="color: red">${list.nameMl }</td>
					<td align="center" style="color: red">${list.nameDl }</td>
					<td align="center"><a title="税户查看"
						href="${basePath }web/cy/shuihuList?nameCy=${list.nameCy }&nameMl=${list.nameMl }&nameDl=${list.nameDl }&startDate=${startDate}"
						target="navTab" rel="configAdd" style="cursor:pointer;"> <span style="color: blue;">${list.shuiHu }</span></a>
					</td>

					<td align="center" style="color: blue">${list.yjSe }</td>
					
					<td align="center"><a title="实缴税额查询"
						href="${basePath }web/cy/shuieList?nameCy=${list.nameCy }&nameMl=${list.nameMl }&nameDl=${list.nameDl }&startDate=${startDate}"
						target="navTab" rel="configSe" style="cursor:pointer;"> <span style="color: blue;">${list.sjSe }</span></a>
					</td>
					
					<%-- <td align="center" style="color: blue">${list.sjSe } --%>
					
					
					
				<%-- 	
					
					<a title="实缴明细"
						href="${basePath }web/cy/shuieList?nameCy=${list.nameCy }&nameMl=${list.nameMl }&nameDl=${list.nameDl }&id_Year=${startDate}"
						target="navTab" rel="configAdd"> <span style="color: blue;">${list.sjSe }</span></a> --%></td>
					<td align="center" style="color: blue">${list.ljSe}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

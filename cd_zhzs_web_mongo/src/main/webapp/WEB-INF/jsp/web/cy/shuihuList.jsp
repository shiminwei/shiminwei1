<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="../../../../common/base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<form id="pagerForm" method="post"
	action="${basePath }web/cy/getShuihuList">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
		<input type="hidden" name="startDate" value="${bean.startDate}" />
		<input type="hidden" name="endDate" value="${bean.endDate}" />
		<input type="hidden" name="nameCy" value="${bean.nameCy}" />
		<input type="hidden" name="nameMl" value="${bean.nameMl}" />
		<input type="hidden" name="nameDl" value="${bean.nameDl}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="${basePath }web/cy/getShuihuList?startDate=${bean.startDate}&endDate=${bean.endDate}&nameCy=${bean.nameCy}&nameMl=${bean.nameMl}&nameDl=${bean.nameDl}"
		method="post">
		<div class="searchBar">
			<ul class="searchContent">

					<li><label style="width: 88px">企业名称</label>
					<input type="text" name="idGbhy" value="${bean.idGbhy}"> 
	</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">查询</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>

<div style="width: auto; height: auto; overflow: x:scroll;">

	<table class="list" width="100%" layoutH="62">
		<thead>
			<tr>
				<th align="center">序号</th>
				<th align="center">纳税人名称</th>
				<th align="center">纳税人识别号</th>
				<th align="center">纳税人状态</th>
				<th align="center">法定代表人</th>
				<th align="center">注册类型</th>
				<th align="center">经营地址</th>
				<th align="center">注册资本</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.result}" var="result" varStatus="status">
				<tr>
				
				<c:forEach items="${result}" var="result1" varStatus="status1">
					<td align="center">
					<c:if test="${status1.index==1}">
					<a title="企业查看"
						href="${basePath }web/cy/getQyDetil?qymc=${result1}&startDate=${bean.startDate}&endDate=${bean.endDate}"
						target="navTab" rel="getQyDetil"> ${result1}</a>
					</c:if>
					<c:if test="${status1.index!=1}">
					${result1}
					</c:if>
					</td>
					</c:forEach>
				</tr>
			</c:forEach>

		</tbody>
	</table>
</div>
<!-- 分页 -->
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value,pageNum:1})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<strong><script>  
                $("select[name='numPerPage']").val('${pageList.numPerPage}');  
            </script></strong>  
			<span>条，共${pageList.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${pageList.totalCount}" numPerPage="${pageList.numPerPage}" pageNumShown="10" currentPage="${pageList.pageNum}"></div>
	</div>

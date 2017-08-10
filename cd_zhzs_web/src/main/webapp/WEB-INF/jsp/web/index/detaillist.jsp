<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="../../../../common/base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<form id="pagerForm" method="post"
	action="${basePath }pageList/public?functionid=${bean.id }">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />

	<c:forEach items="${exportValue}" var="conditionBeans"
		varStatus="status">
		<input type="hidden" name="querValues"
			value="${exportValue[status.index] }" />
	</c:forEach>

	<c:forEach items="${exportValue}" var="exportValue" varStatus="status">
		<input type="hidden" name="exportValue" value="${exportValue }" />

	</c:forEach>

</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="${basePath }pageList/public?functionid=${bean.id }"
		method="post">
		<div class="searchBar">
			<ul style="width: 40px;" class="searchContent">
				<c:forEach items="${bean.conditionBeans}" var="conditionBeans"
					varStatus="status">
					<li width: 88px><label>${conditionBeans.dispname }：</label> <c:choose>
							<c:when test="${conditionBeans.disptype== 1}">
								<input type="text" name="querValues"
									value="${querValues[status.index][0] }" />

							</c:when>
							<c:when test="${conditionBeans.disptype== 2}">
								<select name="querValues">
									<option value="请选择">请选择年份</option>
									<option value="2014"
										<c:if test="${ querValues[status.index][0] eq '2014'}">selected="selected"</c:if>>2014</option>
									<option value="2015"
										<c:if test="${ querValues[status.index][0] eq '2015'}">selected="selected"</c:if>>2015</option>
									<option value="2016"
										<c:if test="${ querValues[status.index][0] eq '2016'}">selected="selected"</c:if>>2016</option>
									<option value="2017"
										<c:if test="${ querValues[status.index][0] eq '2017'}">selected="selected"</c:if>>2017</option>
								</select>
							</c:when>
							<c:when test="${conditionBeans.disptype== 3}">
								<select name="querValues" id="myYear">
									<option value="请选择">请选择年份</option>
									<option value="2014^"
										<c:if test="${ querValues[status.index][0] eq '2014^'}">selected="selected"</c:if>>2014</option>
									<option value="2015^"
										<c:if test="${ querValues[status.index][0] eq '2015^'}">selected="selected"</c:if>>2015</option>
									<option value="2016^"
										<c:if test="${ querValues[status.index][0] eq '2016^'}">selected="selected"</c:if>>2016</option>
									<option value="2017^"
										<c:if test="${ querValues[status.index][0] eq '2017^'}">selected="selected"</c:if>>2017</option>
								</select>
								<select  name="querValues" onclick="toSelectMonth(this)">
									<option value="请选择">请选择月份</option>
									<c:forEach items="${months}" var="months" varStatus="status2">
										<option value="${months }"
											<c:if test="${ querValues[status.index][1] eq months}">selected="selected"</c:if>>${months }</option>
									</c:forEach>
								</select>
							</c:when>
						</c:choose></li>
				</c:forEach>
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
<div class="pageContent">
	<div class="panelBar" style="border-width: 0px 0px 1px 0px;">
		<ul  class="toolBar">
			
			<li class=""><a class="icon" id="abyexport" href="#"
				onclick="toExport('${bean.id }')" target="dwzExport"
				targettype="navTab" title="需要导出这些记录吗?"><span>导出EXCEL</span> </a>
			</li>

		</ul>
	</div>
</div>

<div style="width: auto; height: auto; overflow: x:scroll;">

	<table class="list" width="100%" layoutH="88">
		<thead>
			<tr>
				<th align="center">序号</th>
				<c:forEach items="${bean.results.fieldBeans}" var="fieldBeans"
					varStatus="status">
					<c:if test="${fieldBeans.hidden==0}">
						<th align="center">${fieldBeans.jspFielName }</th>
					</c:if>
				</c:forEach>
			</tr>

			<tr>
				<th align="center">合计</th>
				<c:forEach items="${pageList.totalaList}" var="totalaList"
					varStatus="status">
					<th align="center">${totalaList }</th>
				</c:forEach>
			</tr>

			<tr>
				<th align="center">小计</th>
				<c:forEach items="${pageList.subtotalaList}" var="subtotalaList"
					varStatus="status">
					<th align="center">${subtotalaList }</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.result}" var="result" varStatus="status">
				<tr>
					<td align="center">${result[0]}</td>
					<c:forEach items="${bean.results.fieldBeans}" var="result1"
						varStatus="status1">



						<%-- 					<c:choose>
							<c:when test="${result1.hidden== '0'}">
								<c:choose>
									<c:when test="${result1.link!='0'}">
										<td align="center"><a title="明细查询" id="myDetail"
											href="#" onclick="toDetail('${result1.link}')"
											target="navTab" >${result[status1.index+1]}</a>
										</td>
									</c:when>
									<c:otherwise>
										<td align="center">${result[status1.index+1]}</td>
									</c:otherwise>
								</c:choose>
							</c:when>
						</c:choose> 
						
						 --%>
						 <td align="center"
						<c:if test="${result1.hidden =='1'}">hidden="hidden"</c:if>
						<c:if test="${result1.isByValue!='0'}">
						name="byValue${status.index}"
						</c:if> ><c:if test="${result1.link!='0'}">
									<a title="明细查询" id="myDetail" href="#"
										onclick="toDetail('${result1.link}','${status.index}')" target="navTab">
										${result[status1.index+1]}</a>
								</c:if> 
							<c:if test="${result1.link=='0'}">${result[status1.index+1]}</c:if>
							 </td>
						 
				

					</c:forEach>
				</tr>
			</c:forEach>

		</tbody>
	</table>
</div>
<!-- 分页 -->
<%@include file="../../../../common/page.jsp"%>
<script type="text/javascript">
	function toSelectMonth(obj) {
		var myYear = document.getElementById("myYear").value;
		if (myYear == "请选择") {
			obj.options[0].selected = true;
			alert("请先选择年份");
			obj.disabled = false;
		}
	}

	function toExport(functionId) {
		var nase = document.getElementsByName("exportValue");
		var querValues = "";
		for (var i = 0; i < nase.length; i++) {
			querValues += nase[i].value + ",";
		}
		$("#abyexport").attr(
				"href",
				"${basePath }pageList/exportExcel?functionid=" + functionId
						+ "&querValues=" + querValues);
	}

	function toDetail(functionid,indexValue) {	
		var byValueId="byValue"+indexValue;
		var indexValue=document.getElementsByName(byValueId);
		var querValues = "";
		for (var i = 0; i < indexValue.length; i++) {
			alert(indexValue[i].innerHTML.trim());
			querValues += indexValue[i].innerHTML.trim() + ",";
		}
		$("#myDetail").attr(
				"href",
				"${basePath }pageList/public?functionid=" + functionid
						+ "&querValues=" + querValues+"&detail=1");
	}
	String.prototype.trim=function() {
	    return this.replace(/(^\s*)|(\s*$)/g,'');
	}
</script>
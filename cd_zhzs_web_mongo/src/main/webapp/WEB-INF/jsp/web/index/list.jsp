<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }/static/js/cd_common.js">
	
</script>
<form id="pagerForm" method="post"
	action="${basePath }pageList/public?functionid=${bean.id }">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />

	<c:forEach items="${queryMap}" var="queryMap" varStatus="status">
		<c:forEach items="${queryMap.value}" var="queryMapValue"
			varStatus="statusValue">
			<input type="hidden" name="${queryMap.key }" value="${queryMapValue}" />
		</c:forEach>

	</c:forEach>
</form>

<form id="queryForm" onsubmit="return navTabSearch(this);"
	action="${basePath }pageList/public?functionid=${bean.id }"
	method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<c:forEach items="${bean.conditionBeans}" var="conditionBeans"
					varStatus="status">
					<li><label style="width: 85px;">${conditionBeans.dispname }：</label>
						<c:choose>
							<c:when test="${conditionBeans.disptype== 1}">
								<c:forEach items="${queryMap }" var="queryMap"
									varStatus="statusMap">
									<c:if test="${queryMap.key==conditionBeans.conditionId }">
										<input type="text" name="${conditionBeans.conditionId}"
											value="${queryMap.value[0]}" />
									</c:if>
								</c:forEach>
							</c:when>
							<c:when test="${conditionBeans.disptype== 2}">
								<c:forEach items="${queryMap }" var="queryMap">
									<c:if test="${queryMap.key==conditionBeans.conditionId }">
										<select name="${conditionBeans.conditionId}">
											<option value="">请选择年份</option>
											<c:forEach items="${yearList}" var="years"
												varStatus="status2">
												<option value="${years.code }"
													<c:if test="${ queryMap.value[0] eq years.code}">selected="selected"</c:if>>${years.value }</option>
											</c:forEach>
										</select>
									</c:if>
								</c:forEach>
							</c:when>
							<c:when test="${conditionBeans.disptype== 3}">
								<c:forEach items="${queryMap }" var="yearMonthQueryMap">
									<c:if
										test="${yearMonthQueryMap.key==conditionBeans.conditionId }">
										<select name="${conditionBeans.conditionId}">
											<option value="">请选择年份</option>
											<c:forEach items="${yearList}" var="years"
												varStatus="status2">
												<option value="${years.code }"
													<c:if test="${ yearMonthQueryMap.value[0] eq years.code}">selected="selected"</c:if>>${years.value }</option>
											</c:forEach>
										</select>

										<select name="${conditionBeans.conditionId}"
											onclick="toSelectMonth(this)">
											<option value="">请选择月份</option>
											<c:forEach items="${months}" var="months" varStatus="status2">
												<option value="${months.code }"
													<c:if test="${ yearMonthQueryMap.value[1] eq months.code}">selected="selected"</c:if>>${months.value }</option>
											</c:forEach>
										</select>
									</c:if>
								</c:forEach>
							</c:when>
							<c:when test="${conditionBeans.disptype== 4}">
								<c:forEach items="${queryMap }" var="queryMap">
									<c:if test="${queryMap.key==conditionBeans.conditionId }">
										<select style="margin-left: 10px"
											name="${conditionBeans.conditionId}">
											<option value="">请选税种</option>
											<c:forEach items="${shuiZhongList }" var="shuiZhongList">
												<option value="${shuiZhongList.code }"
													<c:if test="${ queryMap.value[0] eq shuiZhongList.code }">selected="selected"</c:if>>${shuiZhongList.value }</option>
											</c:forEach>

										</select>
									</c:if>
								</c:forEach>
							</c:when>
							<c:when test="${conditionBeans.disptype== 5}">
								<c:forEach items="${queryMap }" var="queryMap">
									<c:if test="${queryMap.key==conditionBeans.conditionId }">
										<select style="margin-left: 10px"
											name="${conditionBeans.conditionId}">
											<option value="">请选月份</option>
											<c:forEach items="${months }" var="months">
												<option value="${months.code }"
													<c:if test="${ queryMap.value[0] eq months.code }">selected="selected"</c:if>>${months.value }</option>
											</c:forEach>

										</select>
									</c:if>
								</c:forEach>
							</c:when>

						</c:choose></li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="panelBar" style="border-width: 0px 0px 1px 0px;">
		<ul class="toolBar">
			<li><a class="search" href="javascript:void(0)"><button
						type="submit">
						<span>查询数据</span>
					</button></a></li>
			<li><a class="icon" id="abyexport" href="#"
				onclick="cleanForm('queryForm','navTab')"><span>清空查询</span></a></li>
			<li><a class="excel" id="abyexport"
				href='${basePath }pageList/exportExcel?functionid=${bean.id }&querValues=${jsonQueryMap}'
				target="dwzExport" targetType="navTab" title="需要导出当前数据吗?"><span
					style="">导出数据</span></a></li>
		</ul>
	</div>
</form>
<div class="pageContent" style="width: auto; overflow: x:scroll;">
	<div style="width: auto; height: auto; overflow: x:scroll;">

		<table class="list" width="100%" layoutH="88">
			<thead>

				<c:if test="${bean.results.showType==2}">
					<tr>
						<th align="center"></th>
						<c:forEach items="${bean.results.fieldBeans}" var="fieldBeans"
							varStatus="status">
							<c:if
								test="${fieldBeans.mergeName!=''&&fieldBeans.mergeNum!='' }">
								<th align="center" colspan="${fieldBeans.mergeNum }">
									<div style="width:${fieldBeans.width }">${fieldBeans.mergeName }</div>
								</th>
							</c:if>
						</c:forEach>
					</tr>
					<tr>
						<th align="center">序号</th>
						<c:forEach items="${bean.results.fieldBeans}" var="fieldBeans"
							varStatus="status">
							<c:if test="${fieldBeans.hidden==0}">
								<th align="center">${fieldBeans.jspFielName }</th>
							</c:if>
						</c:forEach>
					</tr>

				</c:if>
				<c:if test="${bean.results.showType!=2}">
					<tr>
						<th align="center">序号</th>
						<c:forEach items="${bean.results.fieldBeans}" var="fieldBeans"
							varStatus="status">
							<c:if test="${fieldBeans.hidden==0}">
								<th align="center">
									<div style="width:${fieldBeans.width }">${fieldBeans.jspFielName }</div>
								</th>
							</c:if>
						</c:forEach>
					</tr>

				</c:if>
				<tr>
					<th align="center">合计</th>
					<c:forEach items="${pageList.totalaList}" var="totalaList"
						varStatus="status">
						<th align="center">${totalaList }</th>
					</c:forEach>
				</tr>

	<%-- 			<tr>
					<th align="center">小计</th>
					<c:forEach items="${pageList.subtotalaList}" var="subtotalaList"
						varStatus="status">
						<th align="center">${subtotalaList }</th>
					</c:forEach>
				</tr> --%>
			</thead>
			<tbody>

				<c:forEach items="${pageList.result}" var="result"
					varStatus="status">
					<c:set value="${ fn:split(result[0], ',') }" var="index0Value" />
					<tr style="${index0Value[1]}">
						<td align="center">${index0Value[0]}</td>
						<c:forEach items="${bean.results.fieldBeans}" var="result1"
							varStatus="status1">
							<td align="center"
								style="color:${result1.color};
							<c:if test="${result1.hidden =='1'}"> display:none; </c:if>"
								<c:if test="${result1.warningStr !=''&&result1.warningStr==result[status1.index+1]}"></c:if>
								<c:if test="${result1.isByValue!=null&&result1.isByValue!='0'}">
								name="${result1.isByValue}${status.index}"
								</c:if>>
								<c:if test="${result1.link!=''}">
									<a
										onclick="show('${index0Value[2]}','${basePath }/${result1.link}','${result1.linkTitle}','${result1.target}','${status.index}')">
										${result[status1.index+1]}</a>
								</c:if> <c:if test="${result1.link==''||result1.link==null}">${result[status1.index+1]}</c:if>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>
</div>
<div class="panelBar">
	<div class="pages">
		<span>显示</span> <select class="combox" name="numPerPage"
			onchange="navTabPageBreak({numPerPage:this.value,pageNum:1})">
			<option value="20">20</option>
			<option value="50">50</option>
			<option value="100">100</option>
			<option value="200">200</option>
		</select> <strong><script>
			$("select[name='numPerPage']").val('${pageList.numPerPage}');
		</script></strong> <span>条，共${pageList.totalCount}条</span><span>，共${pageList.totalPage}页</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>


<script type="text/javascript">
	/* alert(getNowDiv('0'));
	 */

	/**
	 * 获取当前TABLEID
	 */
	function getRel() {
		var locationHash = navTab.componentBox.context.location.hash;
		if (locationHash != null && locationHash.indexOf("#") >= 0) {
			locationHash = locationHash.trim().substr(1, locationHash.length);
		}
		return locationHash;
	}
	function toSelectMonth(obj) {
		var myYear = document.getElementById("myYear").value;
		if (myYear == "" || myYear == null) {
			obj.options[0].selected = true;
			alert("请先选择年份");
			obj.disabled = false;
		}
	}
	String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, '');
	}
	/**
	 * 跳转
	 */
	function show(urlStr, url, linkTitle, target, index) {
		var queryMapStr = '${queryMapStr}';
		if (url.indexOf("?") < 0) {
			url += "?1=1";
		}
		url += queryMapStr.replace(/^\s+|\s+$/g,"") + urlStr.replace(/^\s+|\s+$/g,"");
		if (linkTitle == null || linkTitle == '') {
			linkTitle = '明细查看';
		}
		if (target == '0') {
			target = "newTitleRel";
		} else {
			target = getRel();
		}
		navTab.openTab(target, url, {
			title : linkTitle,
			fresh : true,
			data : {}
		});
	}
</script>

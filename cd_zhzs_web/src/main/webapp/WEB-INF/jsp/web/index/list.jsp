<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }/static/js/cd_common.js">
	
</script>
<form id="pagerForm" method="post"
	action="${basePath }pageList/public?functionid=${bean.functionId }">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="targetType" value="${targetType}" />

	<c:forEach items="${queryMap}" var="queryMap" varStatus="status">
		<c:forEach items="${queryMap.value}" var="queryMapValue"
			varStatus="statusValue">
			<input type="hidden" name="${queryMap.key }" value="${queryMapValue}" />
		</c:forEach>

	</c:forEach>
</form>
<form id="queryForm"
	<c:if test="${targetType=='dialog'}">onsubmit="return dwzSearch(this, 'dialog');" </c:if>
	<c:if test="${targetType!='dialog'}">onsubmit="return navTabSearch(this);" </c:if>
	action="${basePath }pageList/public?functionid=${bean.functionId }&targetType=${targetType}"
	method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<h1 align="center"
				style="<c:if test="${targetType=='navTab'}">font-size: 30px;</c:if>
			<c:if test="${targetType!='navTab'}">font-size: 20px;</c:if> ">${showQueryName}${bean.name }</h1>
			<c:if test="${bean.unitName!=''}">

				<h1 align="right" style="font-size: 16px">单位：${bean.unitName }</h1>
			</c:if>
			<h2 class="contentTitle" align="center"></h2>
					
			<ul class="searchContent"
				style="margin-bottom: -4px; margin-top: -9px;">
				<c:forEach items="${bean.conditionBeans}" var="conditionBeans"
					varStatus="status">
					<%-- <c:if test="${conditionBeans.isHidden!=1}">  --%>
					
					
					
					<li><label style="width: 85px;" >
					<c:if test="${conditionBeans.isHidden!=1}">
						${conditionBeans.dispname }：
					</c:if>
					</label>
						<c:choose>
							<c:when test="${conditionBeans.disptype== 1}">
								<c:forEach items="${queryMap }" var="queryMap"
									varStatus="statusMap">
									<c:choose>
									<c:when test="${conditionBeans.isHidden!=1 && queryMap.key==conditionBeans.conditionId }">
							
											<input type="text" name="${conditionBeans.conditionId}"
												value="${queryMap.value[0]}" />
							
									</c:when>
									<c:when test="${conditionBeans.isHidden==1 && queryMap.key==conditionBeans.conditionId }">
							
											<input type="hidden" name="${conditionBeans.conditionId}"
												value="${queryMap.value[0]}" />
							
									</c:when>
									</c:choose>
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
							<c:when test="${conditionBeans.disptype== 6}">
								<c:forEach items="${queryMap }" var="queryMap">
									<c:if test="${queryMap.key==conditionBeans.conditionId }">
										<select style="margin-left: 10px"
											name="${conditionBeans.conditionId}">
											<option value="">请选择收款国库</option>
											<c:forEach items="${skgkList }" var="skgkList">
												<option value="${skgkList.code }"
													<c:if test="${ queryMap.value[0] eq skgkList.code }">selected="selected"</c:if>>${skgkList.value }</option>
											</c:forEach>

										</select>
									</c:if>
								</c:forEach>
							</c:when>
							<c:when test="${conditionBeans.disptype== 7}">
								<c:forEach items="${queryMap }" var="queryMap">
									<c:if test="${queryMap.key==conditionBeans.conditionId }">
										<select style="margin-left: 10px"
											name="${conditionBeans.conditionId}">
											<option value="">请选择征管部门</option>
											<c:forEach items="${rkjcList }" var="rkjcList">
												<option value="${rkjcList.code }"
													<c:if test="${ queryMap.value[0] eq rkjcList.code }">selected="selected"</c:if>>${rkjcList.value }</option>
											</c:forEach>

										</select>
									</c:if>
								</c:forEach>
							</c:when>
							<c:when test="${conditionBeans.disptype== 8}">
								<c:forEach items="${queryMap }" var="queryMap">
									<c:if test="${queryMap.key==conditionBeans.conditionId }">
										<select style="margin-left: 10px"
											name="${conditionBeans.conditionId}" onchange="toGetHangYe()" id="cy">
											<option value="">请选择产业</option>
											<c:forEach items="${sscyList }" var="sscyList">
												<option value="${sscyList.code }"
													<c:if test="${ queryMap.value[0] eq sscyList.code }">selected="selected"</c:if>>${sscyList.value }</option>
											</c:forEach>

										</select>
									</c:if>
								</c:forEach>
							</c:when>
							<c:when test="${conditionBeans.disptype== 9}">
								<c:forEach items="${queryMap }" var="queryMap">
									<c:if test="${queryMap.key==conditionBeans.conditionId }">
										<select style="margin-left: 10px"
											name="${conditionBeans.conditionId}" id="hy">
											<option value="">请选择行业大类</option>
											<c:forEach items="${ssdlList }" var="ssdlList">
												<option value="${ssdlList.code }"
													<c:if test="${ queryMap.value[0] eq ssdlList.code }">selected="selected"</c:if>>${ssdlList.value }</option>
											</c:forEach>

										</select>
									</c:if>
								</c:forEach>
							</c:when>
							<c:when test="${conditionBeans.disptype== 10}">
								<c:forEach items="${queryMap }" var="queryMap">
									<c:if test="${queryMap.key==conditionBeans.conditionId }">
										<select style="margin-left: 10px"
											name="${conditionBeans.conditionId}">
											<option value="">请选择所属区域</option>
											<c:forEach items="${ssqyList }" var="ssqyList">
												<option value="${ssqyList.code }"
													<c:if test="${ queryMap.value[0] eq ssqyList.code }">selected="selected"</c:if>>${ssqyList.value }</option>
											</c:forEach>

										</select>
									</c:if>
								</c:forEach>
							</c:when>
							<c:when test="${conditionBeans.disptype== 11}">
								<c:forEach items="${queryMap }" var="queryMap">
									<c:if test="${queryMap.key==conditionBeans.conditionId }">
										<select style="margin-left: 10px"
											name="${conditionBeans.conditionId}">
											<option value="">请选择</option>
											<c:forEach items="${whetherList }" var="whetherList">
												<option value="${whetherList.code }"
													<c:if test="${ queryMap.value[0] eq whetherList.code }">selected="selected"</c:if>>${whetherList.value }</option>
											</c:forEach>

										</select>
									</c:if>
								</c:forEach>
							</c:when>
							<c:when test="${conditionBeans.disptype== 12}">
								<c:forEach items="${queryMap }" var="queryMap">
									<c:if test="${queryMap.key==conditionBeans.conditionId }">
										<select style="margin-left: 10px"
											name="${conditionBeans.conditionId}">
											<option value="">请选择资金来源</option>
											<c:forEach items="${yslyList }" var="yslyList">
												<option value="${yslyList.code }"
													<c:if test="${ queryMap.value[0] eq yslyList.code }">selected="selected"</c:if>>${yslyList.value }</option>
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
			<li><a class="excel" id="abyexport1"
				href='${basePath }pageList/exportExcel?functionid=${bean.functionId }&querValues=${jsonQueryMap}'
				target="dwzExport" targetType="navTab" title="需要导出当前数据吗?"><span
					style="">导出数据</span></a></li>
		</ul>
	</div>
</form>
<div class="pageContent" >

		<table class="list" width="100%"
			<c:if test="${bean.unitName!=''}">layoutH="133"</c:if>
			<c:if test="${bean.unitName==''}">layoutH="118"</c:if>>
			<thead>
				<c:if test="${bean.results.showType==2}">
					
					<c:if test="${type eq'yes' }">
					<tr height="30">
					<th align="center" rowspan="3">序号</th>
					<c:forEach items="${bean.results.fieldBeans}" var="fieldBeans" varStatus="status">
						<c:if test="${fieldBeans.hidden==0}">
							<c:if test="${fieldBeans.jspFielName==''}">
								<th align="center" colspan="${fieldBeans.mergeNum }"  rowspan="${fieldBeans.rowNum }">
									<div style="width:${fieldBeans.width }">${fieldBeans.mergeName }</div></th>
							</c:if>
						</c:if>
						<c:if test="${fieldBeans.jspFielName!=''&& fieldBeans.rowNum!=''}">
							<th align="center" colspan="${fieldBeans.mergeNum }"  rowspan="${fieldBeans.rowNum }">
								<div style="width:${fieldBeans.width }">${fieldBeans.mergeName }</div></th>
						</c:if>	
						<c:if test="${fieldBeans.hidden==0}">
							<c:if test="${fieldBeans.mergeName!='' && fieldBeans.mergeNum!='1' &&
							 	fieldBeans.firstMergeName != '' && fieldBeans.firstMergeNum!='1'}">
								 <th align="center" rowspan="${fieldBeans.rowNum }" colspan="${fieldBeans.firstMergeNum }"><div
												style="width:${fieldBeans.width }">${fieldBeans.firstMergeName }</div></th>
							</c:if>
						</c:if>
					</c:forEach>
					</tr>
					
					<tr height="30">
					<c:forEach items="${bean.results.fieldBeans}" var="fieldBeans"
 							varStatus="status">
						<c:if test="${fieldBeans.hidden==0}">
							<c:if test="${fieldBeans.mergeNum!='1'&&fieldBeans.rowNum==''}">
								<th align="center" colspan="${fieldBeans.mergeNum }"><div
										style="width:${fieldBeans.width }">${fieldBeans.mergeName }</div></th>
							</c:if>
						</c:if>
					</c:forEach>
					</tr>
					
					<tr height="30">
					<c:forEach items="${bean.results.fieldBeans}" var="fieldBeans"
 							varStatus="status">
 						<c:if test="${fieldBeans.hidden==0}">
							<c:if test="${fieldBeans.jspFielName!=''}">
								<th align="center" ><div style="width:${fieldBeans.width }">${fieldBeans.jspFielName }</div></th>
							</c:if>
						</c:if>
					</c:forEach>
					</tr>
					</c:if>
					
					
					<c:if test="${type eq 'no' }">
					<tr height="30">
						<th align="center" rowspan="2">序号</th>
						<c:forEach items="${bean.results.fieldBeans}" var="fieldBeans"
							varStatus="status">
							<c:if
								test="${fieldBeans.mergeName!=''&&fieldBeans.mergeNum!='' }">
								<c:if test="${fieldBeans.mergeNum=='1'}">
									<th align="center" rowspan="2"><div
											style="width:${fieldBeans.width }">${fieldBeans.mergeName }</div></th>
								</c:if>
								<c:if test="${fieldBeans.mergeNum!='1'}">
									<th align="center" rowspan="${fieldBeans.rowNum }"
										colspan="${fieldBeans.mergeNum }"><div
											style="width:${fieldBeans.width }">${fieldBeans.mergeName }</div>
									</th>
								</c:if>
							</c:if>
						</c:forEach>
					</tr>
					<tr height="30">
						<c:forEach items="${bean.results.fieldBeans}" var="fieldBeans"
							varStatus="status">
							<c:if test="${fieldBeans.hidden==0}">
								<c:if test="${fieldBeans.jspFielName!=''}">
									<th align="center">${fieldBeans.jspFielName }</th>

								</c:if>

							</c:if>
						</c:forEach>
					</tr>
				</c:if>
				</c:if>
				<c:if test="${bean.results.showType!=2}">
					<tr height="30">
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
				<tr height="30">
					<th align="center">合计</th>
					<c:forEach items="${pageList.totalaList}" var="totalaList"
						varStatus="status">
						<th align="center">${totalaList }</th>
					</c:forEach>
				</tr>
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
								<c:if test="${result1.link!=''&&result1.link!=null}">
									<c:if test="${result1.targetType=='navTab'}">
										<a onclick="show('${basePath }/${result1.link}${index0Value[2]}','${result1.linkTitle}','${result1.target}')">
										${result[status1.index+1]} </a>
									</c:if>

									<c:if test="${result1.targetType=='dialog'}">
										<a href="${basePath }/${result1.link}${index0Value[2]}"
											title="${result1.linkTitle}" rel="newTitleRel" width="800"
											height="500" target="${result1.targetType}">
											${result[status1.index+1]}</a>
									</c:if>
								</c:if> <c:if test="${result1.link==''||result1.link==null}">${result[status1.index+1]}</c:if>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>

			</tbody>
		</table>
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
	<div class="pagination" targetType="${targetType}"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>


<script type="text/javascript">
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

	function show(url, linkTitle, rel) {
		if (linkTitle == null || linkTitle == '') {
			linkTitle = '明细查看';
		}
		if (rel == '0') {
			rel = "newTitleRel";
		} else {
			rel = getRel();
		}

		if (url != null && url != "") {
			var urlRel="";
			var datas = {};
			if(url.indexOf("?")>0){
				var urlRel = url.substr(0, url.indexOf("?"));
				var parmet=url.substr(url.indexOf("?")+1,url.length);
				var  strs= new Array(); 
				strs=parmet.split("&"); 
				for (var i = 0; i < strs.length; i++) {
					if (strs[i]!=null&&strs[i]!=""&&strs[i].indexOf("=")>0) {
						var key= strs[i].substr(0, strs[i].indexOf("="));
						var value= strs[i].substr(strs[i].indexOf("=")+1,strs[i].length);
						datas[key]=value;
					}				
				}
			}	
			navTab.openTab(rel, urlRel, {
				title : linkTitle,
				fresh : true,
				data :datas
			}
			);

		}
	}
	
	function  toGetHangYe() {
		var cy=$("#cy").val(); 
		if (cy!='') {
			$.ajax({
				 type: "POST",
		         url:'${basePath }web/modelShow/toGetHangYe?cy='+cy,
		         dataType: "json",
		         async: false,
		         success: function(data) {
		            toShowHy(data);
		         }
				  
				});
			
	        
		}
	}

	function toShowHy(obj){
		var hy=document.getElementById('hy'); 
		hy.options.length=0; 
		hy.options.add(new Option("请选择行业大类","")); 
		for (var i = 0; i < obj.length; i++) {
			hy.options.add(new Option(obj[i].code,obj[i].value)); 	
		}
		
		
	}
</script>

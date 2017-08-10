<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<form id="pagerForm" method="post" action="${basePath }web/cy/showCyList?functionid=${bean.id }"  class="pageForm required-validate"  onsubmit="return validateCallback(this,navTabAjaxDone);">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />



	<c:forEach items="${queryMap}" var="queryMap"
		varStatus="status">
		<input type="hidden" name="${queryMap.key }"
			value="${queryMap.value}" />
	</c:forEach>


</form>

<form id="queryForm" method="post"
	action="${basePath }web/cy/showCyList?functionid=${bean.id }"
	onsubmit="return navTabSearch(this);">
<div class="pageHeader">
<div class="searchBar">
			<ul class="searchContent">
				<c:forEach items="${bean.conditionBeans}" var="conditionBeans"
					varStatus="status">
					<li><label style="width: 88px">${conditionBeans.dispname }：</label>
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
								<c:forEach items="${queryMap }" var="yearQueryMap" >
									<c:if test="${yearQueryMap.key==conditionBeans.conditionId }">
										<select name="${conditionBeans.conditionId}">
											<option value="">请选择年份</option>
											<option value="2014"
												<c:if test="${ yearQueryMap.value[0] eq '2014'}">selected="selected"</c:if>>2014</option>
											<option value="2015"
												<c:if test="${ yearQueryMap.value[0] eq '2015'}">selected="selected"</c:if>>2015</option>
											<option value="2016"
												<c:if test="${ yearQueryMap.value[0] eq '2016'}">selected="selected"</c:if>>2016</option>
											<option value="2017"
												<c:if test="${ yearQueryMap.value[0] eq '2017'}">selected="selected"</c:if>>2017</option>
										</select>
									</c:if>
								</c:forEach>
							</c:when>
							<c:when test="${conditionBeans.disptype== 3}">
							<c:forEach items="${queryMap }" var="yearMonthQueryMap" >
							<c:if test="${yearMonthQueryMap.key==conditionBeans.conditionId }">
								<select name="${conditionBeans.conditionId}" id="myYear">
									<option value="">请选择年份</option>
									<option value="2014"
										<c:if test="${ yearMonthQueryMap.value[0] eq '2014'}">selected="selected"</c:if>>2014</option>
									<option value="2015"
										<c:if test="${ yearMonthQueryMap.value[0] eq '2015'}">selected="selected"</c:if>>2015</option>
									<option value="2016"
										<c:if test="${ yearMonthQueryMap.value[0] eq '2016'}">selected="selected"</c:if>>2016</option>
									<option value="2017"
										<c:if test="${ yearMonthQueryMap.value[0] eq '2017'}">selected="selected"</c:if>>2017</option>
								</select>
								<select name="${conditionBeans.conditionId}"
									onclick="toSelectMonth(this)">
									<option value="">请选择月份</option>
									<c:forEach items="${months}" var="months" varStatus="status2">
										<option value="${months }"
											<c:if test="${ yearMonthQueryMap.value[1] eq months}">selected="selected"</c:if>>${months }</option>
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
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
	<li><a class="icon" id="abyexport" href="#"
			onclick="cleanForm('queryForm','navTab')"><span>清空查询</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent" style="width: auto; overflow: x:scroll;">
	<div style="width: auto; height: auto; overflow: x:scroll;">
	<table class="list" width="100%" layoutH="62">
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
		</thead>
		<tbody id="myTbody">
			<c:forEach items="${pageList.result}" var="result" varStatus="status">
				<tr id="myTr${status.index }">
					<td align="center">${result[0]}</td>
					<c:forEach items="${bean.results.fieldBeans}" var="result1"
						varStatus="status1">
						<td align="center"
							style="color: blue;
							<c:if test="${result1.hidden =='1'}"> display:none  </c:if>"
							<c:if test="${result1.isByValue!='0'}">
						name="byValue${status.index}"
						</c:if>><c:if
								test="${status1.index==4}">
								<a title="税户查询" id="myDetail" style="color: blue;"
									href="${basePath }web/cy/getShuihuList?nameCy=${result[2]}&startDate=${hyQuertyBean.startDate}&endDate=${hyQuertyBean.endDate}"
									target="navTab" rel="configAdd"> ${result[status1.index+1]}</a>
							</c:if> 
							
							<c:if
								test="${status1.index==6}">
								<a title="税额明细" id="myDetail" style="color: blue;"
									href="${basePath }web/cy/shuieList?nameCy=${result[2]}&startDate=${hyQuertyBean.startDate}&endDate=${hyQuertyBean.endDate}"
									target="navTab" rel="ShuieRel"> ${result[status1.index+1]}</a>
							</c:if>
							
							<c:if test="${status1.index!=4&&status1.index!=6}">${result[status1.index+1]}</c:if>
							<c:if test="${status1.index==1 }">
								<img
									onclick="toZuan(1,'${result[status1.index+1]}','${status.index }',this)"
									src="${basePath }static/images/down.png" align="right" />
							</c:if></td>
					</c:forEach>
				</tr>
			</c:forEach>

		</tbody>
	</table>
	</div>
</div>
<div class="panelBar"></div>
<script type="text/javascript">
	function toQueryDada() {
		$("#queryForm").submit();
	}
	var addTrIdindex = 1;

	function toAddTr(data, idIndex, type) {
		var falg = false;
		var trId = "myTr" + idIndex;
		if (type == 2) {
			trId = "addTr" + idIndex;
		}
		
		var nameMl="";
		var nameDl="";
		var obj = JSON.parse(data);
		var str = "";
		var myTbody = document.getElementById("myTbody");
		if (idIndex % 2 == 0) {
			falg = true;
		}
		for (var i = 0; i < obj.length; i++) {
			if (type == 1) {
				addTrIdindex = addTrIdindex + parseInt(1);
				str += "<tr name='addTr' id='addTr" + addTrIdindex + "'";
			} else {
				str += "<tr name='addZiTr'";
			}

			if (falg) {
				str += "class='trbg'";
				falg = false;
			} else {
				falg = true;
			}
			str += "><td align='center'></td>";
			for (var j = 0; j < obj[i].length; j++) {
				str += "<td align='center' style='color:"
				if (type == 1) {
					str += "green"
				}
	if (j==1) {
		nameMl=obj[i][j];
	}else if(j==2){
		nameDl=obj[i][j];
	}
				str += ";'>" + getLink(obj[i][j], j, type,nameMl,nameDl);
				if (j == 1) {
					if (type == 1) {
						str += '<img onclick="toZuan(2,\''
								+ obj[i][j]
								+ '\','
								+ addTrIdindex
								+ ',this)" src="${basePath }static/images/down.png" align="right" />';
					}
				}
				str += "</td>";
			}
			str += "</tr>";
		}
		$("#" + trId).after(str);
	}

	function toZuan(type, name, idIndex, obj) {
		var alt = obj.getAttribute("alt");
		if (alt == 1) {
			obj.setAttribute("alt", "0");
			var addZiTr = document.getElementsByName("addZiTr");
			for (var i = addZiTr.length - 1; i >= 0; i--) {
				removeElement(addZiTr[i]);
			}
			if (type == 1) {
				var addTr = document.getElementsByName("addTr");
				for (var i = addTr.length - 1; i >= 0; i--) {
					removeElement(addTr[i]);
				}
			}
		} else {
			var querValues = '${jsonQueryMap}';
			$.ajax({
				type : "POST",
				url : "${basePath }web/cy/getChileCyList",
				data : "type=" + type + "&name=" + name + "&querValues=" + querValues,
				success : function(data) {
					toAddTr(data, idIndex, type);
					obj.setAttribute("alt", "1");
				},
				error : function() {
					alert("操作失败！");
				}
			});
		}
	}

	function removeElement(_element) {
		var _parentElement = _element.parentNode;
		if (_parentElement) {
			_parentElement.removeChild(_element);
		}
	}
	
	
	function getLink(str,j,type,nameMl,nameDl){
		
	var linkSt="";
		if (j==3) {
			if (type==1) {
				linkSt+='<a title="税户查询" id="myDetail"  style="color: green;cursor:pointer;"';
				linkSt+=' onclick="show(this,event,\''+nameMl+'\','+type+',1)" target="navTab" rel="configAdd" >';
				linkSt+=str+"</a>";
			}else {
				linkSt+='<a title="税户查询" id="myDetail"  style="color: black;cursor:pointer;"';
				linkSt+=' onclick="show(this,event,\''+nameDl+'\','+type+',1)" target="navTab" rel="configAdd" >';
				linkSt+=str+"</a>";
			}

		}else if(j==5){
			if (type==1) {
				linkSt+='<a title="税额明细" id="myDetail"  style="color: green;cursor:pointer;"';
				linkSt+=' onclick="show(this,event,\''+nameMl+'\','+type+',2)" target="navTab" rel="ShuieRel" >';
				linkSt+=str+"</a>";
			}else {
				linkSt+='<a title="税额明细" id="myDetail"  style="color: black;cursor:pointer;"';
				linkSt+=' onclick="show(this,event,\''+nameDl+'\','+type+',2)" target="navTab" rel="ShuieRel" >';
				linkSt+=str+"</a>";
			}

		}else{
			linkSt=	str;
		}
	return linkSt;
	}
	
	
	
	function show(obj, event,nameMl,type,showType) {
		var urlLink="";
		if (showType==1) {
			urlLink+="${basePath }web/cy/getShuihuList?";
		}else {
			urlLink+="${basePath }web/cy/shuieList?";
		}
		if (type==1) {
			urlLink+="nameMl="+nameMl+"&startDate=${hyQuertyBean.startDate}&endDate=${hyQuertyBean.endDate}";
		}else if (type==2) {
			urlLink+="nameDl="+nameMl+"&startDate=${hyQuertyBean.startDate}&endDate=${hyQuertyBean.endDate}";
		}
		var $this = $(obj);
		var title = $this.attr("title") || $this.text();
		var tabid = $this.attr("rel") || "_blank";
		var fresh = eval($this.attr("fresh") || "true");
		var external = eval($this.attr("external") || "false");
		var url = urlLink;
		if (!url.isFinishedTm()) {
			alertMsg.error($this.attr("warn") || DWZ.msg("alertSelectMsg"));
			return false;
		}
		navTab.openTab(tabid, url, {
			title : title,
			fresh : fresh,
			external : external
		});
		event.preventDefault();
	}
	
	
</script>

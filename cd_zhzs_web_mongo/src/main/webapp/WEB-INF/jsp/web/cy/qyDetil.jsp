<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
#exhibitorleft {
	float: left;
	width: 44%;
	height: 80%;
}

#exhibitorright {
	float: left;
	width: 56%;
	height: 80%;
}
</style>
</head>
<%@ include file="../../../../common/base.jsp"%>

<form id="pagerForm" method="post"
	action="${basePath }web/cy/getDataByYear">
	<input type="hidden" name="qymc" value=" ${bean.qymc }" /> <input
		type="hidden" name="startDate" value="2010" /> <input type="hidden"
		name="endDate" value="2018" />
</form>
<body>
	<h2 class="subTitle">${bean.qymc }</h2>


	<div class="pageContent sortDrag" selector="h1" layoutH="42">

		<div class="panel collapse" minH="100" defH="180">
			<h1>基本信息</h1>
			<div>
				<div id="exhibitor114" style="float: inherit">
					<div id="exhibitorleft">

						<p style="font-size: 10px">
							<span style="color: blue;">企业名称：</span><span>
								${bean.qymc } </span>
						</p>
						<p style="margin-top: 10px">
							<span style="color: blue;">纳税人识别号：</span><span>${qyBean.nsrsbh }
							</span>
						</p>
						<p style="margin-top: 10px">
							<span style="color: blue;">纳税人状态：</span><span>${qyBean.nsrzt }</span>
						</p>
						<p style="margin-top: 10px">
							<span style="color: blue;">法定代表人：</span><span>
								${qyBean.fddbr } </span>
						</p>
						<p style="margin-top: 10px">
							<span style="color: blue;">注册类型：</span><span>
								${qyBean.zclx } </span>
						</p>
						<p style="margin-top: 10px">
							<span style="color: blue;">注册地址：</span><span>
								${qyBean.zcdz } </span>
						</p>
						<p style="margin-top: 10px">
							<span style="color: blue;">经营地址：</span><span>
								${qyBean.jydz} </span>
						</p>
						<p style="margin-top: 10px">
							<span style="color: blue;">注册资本：</span><span>
								${qyBean.zczb } </span>
						</p>
					</div>
					<div id="exhibitorright">
						<p style="margin-top: 10px">
							<span style="color: blue;">是否是招商企业：</span><span
								<c:if test = "${qyxx.is_canvass_buisiness ==1}">style="color:red" </c:if>>${qyxx.is_canvass_buisiness==1?"是":"否" }
							</span>
						</p>
						<p style="margin-top: 10px">
							<span style="color: blue;">是否是规模以上企业：</span><span
								<c:if test = "${qyxx.is_above_scale ==1}">style="color:red" </c:if>>${qyxx.is_above_scale==1?"是":"否" }
							</span>
						</p>
					</div>
				</div>
			</div>
		</div>

		<div class="panel collapse">
				<h1>缴税信息</h1>
				<div>
					<form method="post" name="searchForm" id="searchForm"
						action="${basePath }web/cy/getDataByYear?qymc=${bean.qymc}&startDate=2010&endDate=2017"
						onsubmit="return navTabSearch(this);">
						<div class="searchBar">
							<ul class="searchContent">
								<li><label>年份：</label> 
								
								<%-- <input style="margin-left: -30px"
									type="text" name="year" value="${year }" /> --%>
									
									<select name="year" id="year" style="width:45%">
                                        <option value="">--请选择--</option>
                                         <c:forEach var="yearList" items="${yearList}">
                                            <option value="${yearList.code}" <c:if test="${yearList.code==year}">selected="selected"</c:if>>${yearList.value}</option>
                                        </c:forEach>
                                    </select>
									
									</li>
								<dd>
									<div style="margin-left: 10px;" class="button">
										<div class="buttonContent">
											<button>查询</button>
										</div>
									</div>
								</dd>
								<dd>
									<div style="margin-left: 17px" class="button">
										<div class="buttonContent"
											onclick="cleanForm('searchForm','navTab')">
											<button>清空</button>
										</div>
									</div>
								</dd>
							</ul>
						</div>
					</form>
					<table class="list" width="98%">
						<thead>
							<tr>
								<th width="80" align="center" style="color: blue;">序号</th>
								<th align="center" style="color: blue;">增值税</th>
								<th align="center" style="color: blue;">消费税</th>
								<th align="center" style="color: blue;">营业税</th>
								<th align="center" style="color: blue;">企业所得税（国税）</th>
								<th align="center" style="color: blue;">土地使用税（地税）</th>
								<th align="center" style="color: blue;">土地使用税</th>
								<th align="center" style="color: blue;">土地增值税</th>
								<th align="center" style="color: blue;">印花税</th>
								<th align="center" style="color: blue;">契税</th>
								<th align="center" style="color: blue;">城建税</th>
								<th align="center" style="color: blue;">教育附加</th>
								<th align="center" style="color: blue;">地方教育附加</th>
								<th colspan="1" align="center" style="color: blue;">合计</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${seList}" var="result" varStatus="status">
								<tr>

									<c:forEach items="${result}" var="result1" varStatus="status1">

										<td align="center">${result1}</td>


									</c:forEach>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

			<div class="panel collapse">
				<h1>计税依据</h1>
				<div>
					<table class="list" width="98%">
						<thead>
							<tr>
								<th align="center" style="color: blue;">项目名称</th>
								<th align="center" style="color: blue;">计税依据</th>
								<th align="center" style="color: blue;">日期</th>
								<th align="center" style="color: blue;">录入日期</th>
								<th align="center" style="color: blue;">项目备注</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${xmList}" var="result" varStatus="status">
								<tr>

									<c:forEach items="${result}" var="result1" varStatus="status1">

										<td align="center">${result1}</td>


									</c:forEach>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>

			<div class="panel collapse">
				<h1>国地税缴费记录</h1>
				<div>
					<table class="list" width="98%">
						<thead>
							<tr>
								<th align="center" style="color: blue;">纳税人名称</th>
								<th align="center" style="color: blue;">纳税人识别号</th>
								<th align="center" style="color: blue;">征收项目</th>
								<th align="center" style="color: blue;">征收品目</th>
								<th align="center" style="color: blue;">金额</th>
								<th align="center" style="color: blue;">入库日期</th>
								<th align="center" style="color: blue;">所属国地税入库</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${sjList}" var="result" varStatus="status">
								<tr>

									<c:forEach items="${result}" var="result1" varStatus="status1">

										<td align="center">${result1}</td>


									</c:forEach>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>

		</div>
</body>
</html>
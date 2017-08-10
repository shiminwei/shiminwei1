<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../../../../common/base.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${basePath }/static/js/dwz.stable.js"></script>
</head>

<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/zdfxmqkxx"
	onsubmit="return navTabSearch(this);">
	<div class="pageHeader">
		<div class="searchBar">
			<h1 align="center" style="font-size: 30px; margin-bottom: -12px;">
				${xmmc }项目资金安排使用情况表</h1>
			<h1 align="right" style="font-size: 16px">单位：万元</h1>
			<h2 class="contentTitle" align="center"></h2>
			<ul class="searchContent"
				style="margin-bottom: -4px; margin-top: -9px">
				<li><label>所属时间：</label> <select name="year">
						<option value="">请选择年份</option>
						<option value="2014"
							<c:if test="${year==2014 }">selected="selected"</c:if>>2014</option>
						<option value="2015"
							<c:if test="${year==2015 }">selected="selected"</c:if>>2015</option>
						<option value="2016"
							<c:if test="${year==2016 }">selected="selected"</c:if>>2016</option>
						<option value="2017"
							<c:if test="${year==2017 }">selected="selected"</c:if>>2017</option>
				</select> <select name="year">
						<option value="">请选择季度</option>
						<option value="第一季度">第一季度</option>
						<option value="第二季度">第二季度</option>
						<option value="第三季度">第三季度</option>
						<option value="第四季度">第四季度</option>
				</select></li>

			</ul>
		</div>
	</div>
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="search" href="javascript:void(0)"><button
						type="submit">
						<span>查询数据</span>
					</button></a></li>
		</ul>
	</div>
</form>
<div class="pageContent">
	<table class="list" style="width: 100%">
		<thead>
			<tr>
				<th width="10%" height="20px" rowspan="2" colspan="2" id="Myth">
					<div style="float: right; margin-top: 5px; margin-right: 10px">资金安排</div>
					<div id="chart123"></div>
					<div style="margin-top: 25px; margin-left: 10px">使用情况</div>
				</th>

				<th align="center" rowspan="2"><div
						style="height: 21px; margin-top: 20px;">上级资金</div></th>
				<th align="center" colspan="2">本级安排</th>
				<th align="center" rowspan="2"><div
						style="height: 21px; margin-top: 20px;">融资资金</div></th>
				<th align="center" rowspan="2"><div
						style="height: 21px; margin-top: 20px;">其他资金</div></th>
				<th width='5%' align="center" rowspan="2" rowspan="2"><div
						style="height: 21px; margin-top: 20px;">合计</div></th>
			</tr>
			<tr>
				<th width="center" align="center">财政</th>
				<th width="center" align="center">平台公司</th>
			</tr>

		</thead>
		<tbody>
			<tr>
				<td align="left" colspan="2" style="color: red;" width="5%">总投资</td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
			</tr>

			<tr>
				<td align="left" rowspan="4" style="color: red; width:3%"
					height="20px">已付</td>
			</tr>
			<tr>
				<td align="left" width="8%">以前年度支付</td>
				<td class="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
			</tr>
			<tr>
				<td align="left" width="8%">本年度支付</td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
			</tr>


			<tr>
				<td align="left" width="8%">小计</td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
			</tr>


			<tr>
				<td align="left" style="color: red" width="100px" colspan="2">未付</td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
			</tr>
		</tbody>
	</table>
</div>

<form method="post" name="pagerForm"
	action="${basePath }web/modelShow/czzcList"
	onsubmit="return navTabSearch(this);">
	<div class="pageHeader">
		<div class="searchBar">
			<h1 align="center"
				style="font-size: 20px; margin-top: 11px; margin-bottom: -3px">项目实施详细情况表</h1>
			<h1 align="right" style="font-size: 16px">单位：万元</h1>
			<h2 class="contentTitle" align="center"></h2>
		</div>
	</div>
</form>

<div class="pageContent">
	<table class="list" width="100%" layoutH="350">
		<thead>
			<tr>
				<th align="center">序号</th>
				<th align="center">子项目名称</th>
				<th align="center">施工单位</th>
				<th align="center">合同金额</th>
				<th align="center">审计金额</th>
				<th align="center">已付款</th>
				<th align="center">未付款</th>
				<th align="center">未付比例</th>
				<th align="center">已纳税额</th>
				<th align="center">备注</th>
			</tr>

		</thead>
		<tbody>
			<%
				for (int i = 1; i <8; i++) {
					out.print("<tr>");
					out.print("<td align='center'>");
					out.print(i);
					out.print("</td>");
					for (int j = 1; j <10; j++) {
						out.print("<td>");
						out.print("</td>");
					}
					out.print("</tr>");
				}
			%>
		</tbody>
	</table>
</div>
<div class="panelBar">
	<div class="pages" >
		<span>显示</span> <select class="combox" name="numPerPage"
			onchange="navTabPageBreak({numPerPage:this.value,pageNum:1})">
			<option value="20">20</option>
			<option value="50">50</option>
			<option value="100">100</option>
			<option value="200">200</option>
		</select> <strong><script>
			$("select[name='numPerPage']").val('${pageList.numPerPage}');
		</script></strong> <span>条，共7条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>
<script>
	void function() {
		var clientWidth = document.getElementById("Myth").clientWidth+2;
		var hd = Math.ceil(26*clientWidth/46.8);
		var arr = [];
		T$ = function(id) {
			return document.getElementById(id)
		};
		fx = function(t, b, c, d) {
			return c * t / d + b;
		};
		for (i = 0; i < clientWidth; i++) {
			arr
					.push('<div style="width:1px;height:1px;font-size:0;background-color:#D0D0D0;position:absolute;left:'
							+ (i - 1)
							+ 'px;top:'
							+ (Math.ceil(fx(i, 0, 26, hd)))
							+ 'px"><\/div>');
		}
		T$('chart123').innerHTML = arr.join('');
// 		alert(Math.ceil(fx(i, 0, 26, hd)));
	}();
// 	$(window).resize(function(){
// 	}); 
</script>
</html>


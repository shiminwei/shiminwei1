<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<script src="${basePath }static/js/cd_common.js" />
<form id="pagerForm" name="pagerForm" method="post"
	action="${basePath }web/companytax/qsqyfzgbm">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> 
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="year" value="${year }" />
	<input type="hidden" name="month" value="${month }" />
	<input type="hidden" name="qymc" value="${qymc}" />
	<input type="hidden" name="ssqy" value="${ssqy}" />
</form>
<form method="post" name="searchForm"
	action="${basePath }web/companytax/qsqyfzgbm"
	onsubmit="return navTabSearch(this);">
	<div class="pageHeader">
		<div class="searchBar">
			<h1 align="center" style="font-size: 30px; margin-bottom: -12px">${year}年<c:if
					test="${month!=null&&month!=''}">${month}月</c:if>全市<c:if test="${ssqyname!=null&&ssqyname!=''}">所属${ssqyname}</c:if>企业分征管部门纳税情况表
			</h1>
			<h1 align="right" style="font-size: 16px">单位：万元</h1>
			<h2 class="contentTitle" align="center"></h2>
			<ul class="searchContent"
				style="margin-bottom: -4px; margin-top: -9px">
				<li><label>所属时间：</label> <select name="year">

						<c:forEach items="${yearList}" var="yearList" varStatus="status2">
							<option value="${yearList.code }"
								<c:if test="${ year eq yearList.code}">selected="selected"</c:if>>${yearList.value }</option>
						</c:forEach>
				</select> <select name="month">
						<option value="">请选择月份</option>
						<c:forEach items="${monthList}" var="monthList"
							varStatus="status2">
							<option value="${monthList.code }"
								<c:if test="${ month eq monthList.code}">selected="selected"</c:if>>${monthList.value }</option>
						</c:forEach>
				</select></li>
				<li><label>企业名称：</label> <input name="qymc" type="text"
					value="${qymc }" />
				<li><label>征管部门：</label> <select name="ssqy">
						<option value="1"
							<c:if test="${ ssqy == '1'}">selected="selected"</c:if>>国税</option>
						<option value="2"
							<c:if test="${ ssqy == '2'}">selected="selected"</c:if>>地税</option>
						<option value="3"
							<c:if test="${ ssqy == '3'}">selected="selected"</c:if>>海关</option>
						<option value="4"
							<c:if test="${ ssqy == '4'}">selected="selected"</c:if>>省直地税</option>
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
			<li><a class="icon" id="abyexport" href="#"
				onclick="cleanForm('pagerForm','navTab')"><span>清空查询</span></a></li>
			<li><a class="excel" id="abyexport1" href='#' target="dwzExport"
				targetType="navTab" title="需要导出当前数据吗?"><span style="">导出数据</span></a></li>
		</ul>
	</div>
</form>
<div class="pageContent">
	<table class="list" 
		<c:if test="${ ssqy == '1'}">width="200%"</c:if>
		<c:if test="${ ssqy == '2'}">width="200%"</c:if> 
		<c:if test="${ ssqy == '3' or ssqy == '4' }">width="100%"</c:if>
	 layoutH="121" >
		<thead>
			<tr>
					<c:if test="${ ssqy == '1' }">
						<th  align="center" colspan="1" rowspan="4"
							style="font-size: 14px"width="3%" >序号</th>
						<th  align="center" colspan="1" rowspan="4" width="9%"
							style="font-size: 14px;">企业名称</th>
						<th  align="center" colspan="1" rowspan="4"
							style="font-size: 14px"  width="3%">所属区域</th>
						<th align="center" colspan="3" rowspan="3" width="9%" 
							style="font-size: 14px">累计完成情况</th>
					</c:if>
					<c:if test="${ ssqy == '2' }">
						<th  align="center" colspan="1" rowspan="4"
							style="font-size: 14px"width="2%" >序号</th>
						<th  align="center" colspan="1" rowspan="4" width="9%"
							style="font-size: 14px;">企业名称</th>
						<th  align="center" colspan="1" rowspan="4"
							style="font-size: 14px"  width="2%">所属区域</th>
						<th align="center" colspan="3" rowspan="3" width="6%" 
							style="font-size: 14px">累计完成情况</th>
					</c:if>
					<c:if test="${ ssqy != '2' and ssqy != '1' }">
						<th  align="center" colspan="1" rowspan="4"
							style="font-size: 14px"width="3%" >序号</th>
						<th  align="center" colspan="1" rowspan="4" width="15%"
							style="font-size: 14px;">企业名称</th>
						<th  align="center" colspan="1" rowspan="4"
							style="font-size: 14px"  width="5%">所属区域</th>
						<th align="center" colspan="3" rowspan="3" width="12%" 
						style="font-size: 14px">累计完成情况</th>
					</c:if>
					
					
					
				<th  align="center"
					colspan="${fn:length(newSzList)*3}" style="font-size: 14px">分税种（${ssqyname }）</th>
			</tr>
			<tr>
			</tr>
			<tr>
				<c:if test="${ ssqy == '1' }">
					<c:forEach items="${newSzList}" var="newSzList" varStatus="status">
						<th width="9%" align="center" colspan="3" >${newSzList }</th>
					</c:forEach>
				</c:if>
				<c:if test="${ ssqy == '2' }">
					<c:forEach items="${newSzList}" var="newSzList" varStatus="status">
						<th width="6%" align="center" colspan="3" >${newSzList }</th>
					</c:forEach>
				</c:if>
				<c:if test="${ ssqy != '1' and ssqy != '2' }">
					<c:forEach items="${newSzList}" var="newSzList" varStatus="status">
						<th width="12%" align="center" colspan="3" >${newSzList }</th>
					</c:forEach>
				</c:if>
			</tr>
			<tr>
				<c:if test="${ ssqy == '1' }">
					<th width="3%" align="center">完成数</th>
					<th width="3%" align="center">增减额</th>
					<th width="3%" align="center">增幅（%）</th>
				</c:if>
				<c:if test="${ ssqy == '2' }">
					<th width="2%" align="center">完成数</th>
					<th width="2%" align="center">增减额</th>
					<th width="2%" align="center">增幅（%）</th>
				</c:if>
				<c:if test="${ ssqy != '1' and ssqy != '2' }">
					<th width="4%" align="center">完成数</th>
					<th width="4%" align="center">增减额</th>
					<th width="4%" align="center">增幅（%）</th>
				</c:if>
				
				<c:forEach items="${newSzList}" var="newSzList" varStatus="status">
				
				<c:if test="${ ssqy == '1' }">
					<th width="3%" align="center">完成数</th>
					<th width="3%" align="center">增减额</th>
					<th width="3%" align="center">增幅（%）</th>
				</c:if>
				<c:if test="${ ssqy == '2' }">
					<th width="2%" align="center">完成数</th>
					<th width="2%" align="center">增减额</th>
					<th width="2%" align="center">增幅（%）</th>
				</c:if>
				<c:if test="${ ssqy != '1' and ssqy != '2' }">
					<th width="4%" align="center">完成数</th>
					<th width="4%" align="center">增减额</th>
					<th width="4%" align="center">增幅（%）</th>
				</c:if>
					
					
				</c:forEach>
			</tr>
			<c:if test="${ ssqy == '1'}">
				<tr>
					<th align="center">合计</th>					
					<th align="center"></th>
					<th align="center"></th>
					<th align="center">${pageList.totalaMap.LJWC }</th>
					<th align="center">${pageList.totalaMap.ZJE }</th>
					<th align="center">${pageList.totalaMap.ZF }</th>
					<th align="center">${pageList.totalaMap.ZZS }</th>
					<th align="center">${pageList.totalaMap.ZZS_ZJE }</th>
					<th align="center">${pageList.totalaMap.ZZS_ZF }</th>
					<th align="center">${pageList.totalaMap.XFS }</th>
					<th align="center">${pageList.totalaMap.XFS_ZJE }</th>
					<th align="center">${pageList.totalaMap.XFS_ZF }</th>
					<th align="center">${pageList.totalaMap.QYSDS}</th>
					<th align="center">${pageList.totalaMap.QYSDS_ZJE }</th>
					<th align="center">${pageList.totalaMap.QYSDS_ZF }</th>				
					<th align="center">${pageList.totalaMap.GRSDS }</th>
					<th align="center">${pageList.totalaMap.GRSDS_ZJE }</th>
					<th align="center">${pageList.totalaMap.GRSDS_ZF }</th>
					<th align="center">${pageList.totalaMap.CLGZS }</th>
					<th align="center">${pageList.totalaMap.CLGZS_ZJE }</th>
					<th align="center">${pageList.totalaMap.CLGZS_ZF }</th>
					<th align="center">${pageList.totalaMap.CLJJSR }</th>
					<th align="center">${pageList.totalaMap.CLJJSR_ZJE }</th>
					<th align="center">${pageList.totalaMap.CLJJSR_ZF }</th>
					<th align="center">${pageList.totalaMap.WHSYJSF }</th>
					<th align="center">${pageList.totalaMap.WHSYJSF_ZJE }</th>
					<th align="center">${pageList.totalaMap.WHSYJSF_ZF }</th>
					<th align="center">${pageList.totalaMap.SWBMFMSR }</th>
					<th align="center">${pageList.totalaMap.SWBMFMSR_ZJE }</th>
					<th align="center">${pageList.totalaMap.SWBMFMSR_ZF }</th>
				</tr>
			</c:if>		
			<c:if test="${ ssqy == '2'}">
				<tr>
					<th align="center">合计</th>					
					<th align="center"></th>
					<th align="center"></th>
			<th align="center">${pageList.totalaMap.WCS }</th>
					<th align="center">${pageList.totalaMap.ZJE }</th>
					<th align="center">${pageList.totalaMap.ZF }%</th>
					<th align="center">${pageList.totalaMap.WCS1 }</th>
					<th align="center">${pageList.totalaMap.ZJE1 }</th>
					<th align="center">${pageList.totalaMap.ZF1}%</th>
					<th align="center">${pageList.totalaMap.WCS2 }</th>
					<th align="center">${pageList.totalaMap.ZJE2 }</th>
					<th align="center">${pageList.totalaMap.ZF2}%</th>
					<th align="center">${pageList.totalaMap.WCS3 }</th>
					<th align="center">${pageList.totalaMap.ZJE3}</th>
					<th align="center">${pageList.totalaMap.ZF3}%</th>
					<th align="center">${pageList.totalaMap.WCS4}</th>
					<th align="center">${pageList.totalaMap.ZJE4 }</th>
					<th align="center">${pageList.totalaMap.ZF4}%</th>
					<th align="center">${pageList.totalaMap.WCS5 }</th>
					<th align="center">${pageList.totalaMap.ZJE5}</th>
					<th align="center">${pageList.totalaMap.ZF5}%</th>
					<th align="center">${pageList.totalaMap.WCS6 }</th>
					<th align="center">${pageList.totalaMap.ZJE6 }</th>
					<th align="center">${pageList.totalaMap.ZF6}%</th>
					<th align="center">${pageList.totalaMap.WCS7 }</th>
					<th align="center">${pageList.totalaMap.ZJE7 }</th>
					<th align="center">${pageList.totalaMap.ZF7}%</th>
					<th align="center">${pageList.totalaMap.WCS8 }</th>
					<th align="center">${pageList.totalaMap.ZJE8 }</th>
					<th align="center">${pageList.totalaMap.ZF8}%</th>
					<th align="center">${pageList.totalaMap.WCS9 }</th>
					<th align="center">${pageList.totalaMap.ZJE9 }</th>
					<th align="center">${pageList.totalaMap.ZF9}%</th>
					<th align="center">${pageList.totalaMap.WCS10 }</th>
					<th align="center">${pageList.totalaMap.ZJE10}</th>
					<th align="center">${pageList.totalaMap.ZF10}%</th>
					<th align="center">${pageList.totalaMap.WCS11 }</th>
					<th align="center">${pageList.totalaMap.ZJE11 }</th>
					<th align="center">${pageList.totalaMap.ZF11}%</th>
					<th align="center">${pageList.totalaMap.WCS12 }</th>
					<th align="center">${pageList.totalaMap.ZJE12 }</th>
					<th align="center">${pageList.totalaMap.ZF12}%</th>
					<th align="center">${pageList.totalaMap.WCS13 }</th>
					<th align="center">${pageList.totalaMap.ZJE13}</th>
					<th align="center">${pageList.totalaMap.ZF13}%</th>
					<th align="center">${pageList.totalaMap.WCS14 }</th>
					<th align="center">${pageList.totalaMap.ZJE14}</th>
					<th align="center">${pageList.totalaMap.ZF14}%</th>
					<th align="center">${pageList.totalaMap.WCS15 }</th>
					<th align="center">${pageList.totalaMap.ZJE15 }</th>
					<th align="center">${pageList.totalaMap.ZF15}%</th>
					<th align="center">${pageList.totalaMap.WCS16 }</th>
					<th align="center">${pageList.totalaMap.ZJE16 }</th>
					<th align="center">${pageList.totalaMap.ZF16}%</th>
					<th align="center">${pageList.totalaMap.WCS17 }</th>
					<th align="center">${pageList.totalaMap.ZJE17 }</th>
					<th align="center">${pageList.totalaMap.ZF17}%</th>
					<th align="center">${pageList.totalaMap.WCS18 }</th>
					<th align="center">${pageList.totalaMap.ZJE218}</th>
					<th align="center">${pageList.totalaMap.ZF18}%</th>
					<th align="center">${pageList.totalaMap.WCS19 }</th>
					<th align="center">${pageList.totalaMap.ZJE19}</th>
					<th align="center">${pageList.totalaMap.ZF19}%</th>
					<th align="center">${pageList.totalaMap.WCS20 }</th>
					<th align="center">${pageList.totalaMap.ZJE20 }</th>
					<th align="center">${pageList.totalaMap.ZF20}%</th>	
				</tr>
			</c:if>			
			<c:if test="${ ssqy == '3'}">
				<tr>
					<th align="center">合计</th>					
					<th align="center"></th>
					<th align="center"></th>
					<th align="center">${pageList.totalaMap.LJWC }</th>
					<th align="center">${pageList.totalaMap.ZJE }</th>
					<th align="center">${pageList.totalaMap.ZF }%</th>
					<th align="center">${pageList.totalaMap.JKGS }</th>
					<th align="center">${pageList.totalaMap.JKGS_ZJE }</th>
					<th align="center">${pageList.totalaMap.JKGS_ZF }%</th>
					<th align="center">${pageList.totalaMap.JKZZS }</th>
					<th align="center">${pageList.totalaMap.JKZZS_ZJE }</th>
					<th align="center">${pageList.totalaMap.JKZZS_ZF }%</th>
				</tr>
			</c:if>
			<c:if test="${ ssqy == '4'}">
				<tr>
					<th align="center">合计</th>					
					<th align="center"></th>
					<th align="center"></th>
					<th align="center">${pageList.totalaMap.LJWCS }</th>
					<th align="center">${pageList.totalaMap.LJZJE }</th>
					<th align="center">${pageList.totalaMap.LJZF }%</th>
					<th align="center">${pageList.totalaMap.HLWCS }</th>
					<th align="center">${pageList.totalaMap.HLZJE }</th>
					<th align="center">${pageList.totalaMap.HLZF }%</th>
				</tr>
			</c:if>
		</thead>
		<tbody>
			<c:forEach var="list" items="${pageList.result}" varStatus="status">			
				<tr>
				<c:if test="${ ssqy == '1'}">
				<td align="center">${ status.index + 1}</td>				
				<td align="center"><a href="${basePath }web/companyinfo/detail?id=${list.COMPANY_ID }"  mask="true" target="dialog" rel="dlg_page10" width="750" height="600" 
						title="企业基本信息"  minable="true"><span>${list.NSRMC }</span></a></td>
				<td align="center">${list.SSQY }</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NSRMC }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm=''" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.LJWC }</span></a></td>
				<td align="center">${list.ZJE }</td>
				<td align="center">${list.ZF }</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NSRMC }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='增值税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.ZZS }</span></a></td>
				<td align="center">${list.ZZS_ZJE }</td>
				<td align="center">${list.ZZS_ZF }</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NSRMC }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='消费税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.XFS }</span></a></td>
				<td align="center">${list.XFS_ZJE }</td>
				<td align="center">${list.XFS_ZF }</td>
				
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NSRMC }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='企业所得税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.QYSDS }</span></a></td>
				<td align="center">${list.QYSDS_ZJE }</td>
				<td align="center">${list.QYSDS_ZF }</td>
				
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NSRMC }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='个人所得税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.GRSDS }</span></a></td>
				<td align="center">${list.GRSDS_ZJE }</td>
				<td align="center">${list.GRSDS_ZF }</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NSRMC }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='车辆购置税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.CLGZS }</span></a></td>
				<td align="center">${list.CLGZS_ZJE }</td>
				<td align="center">${list.CLGZS_ZF }</td>
				
				
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NSRMC }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='废弃电器电子产品处理基金收入'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.CLJJSR }</span></a></td>
				<td align="center">${list.CLJJSR_ZJE }</td>
				<td align="center">${list.CLJJSR_ZF }</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NSRMC }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='文化事业建设费'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WHSYJSF }</span></a></td>
				<td align="center">${list.WHSYJSF_ZJE }</td>
				<td align="center">${list.WHSYJSF_ZF }</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NSRMC }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='税务部门罚没收入'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.SWBMFMSR }</span></a></td>
				<td align="center">${list.SWBMFMSR_ZJE }</td>
				<td align="center">${list.SWBMFMSR_ZF }</td>
				
				
				
				
				</c:if>
				<c:if test="${ ssqy == '2'}">
				<td align="center">${ status.index + 1}</td>
				<td align="center"><a href="${basePath }web/companyinfo/detail?id=${list.COMPANY_ID }"  mask="true" target="dialog" rel="dlg_page10" width="750" height="600" 
						title="企业基本信息"  minable="true"><span>${list.NAME }</span></a></td>					
				<td align="center">${list.AREA_NAME }</td>
				
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm=''" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS }</span></a></td>
				<td align="center">${list.ZJE }</td>
				<td align="center">${list.ZF }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='营业税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS1 }</span></a></td>
				<td align="center">${list.ZJE1 }</td>
				<td align="center">${list.Z1F }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='房产税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS2 }</span></a></td>
				<td align="center">${list.ZJE2 }</td>
				<td align="center">${list.ZF2 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='土地增值税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS3 }</span></a></td>
				<td align="center">${list.ZJE3 }</td>
				<td align="center">${list.ZF3 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='城镇土地使用税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS4 }</span></a></td>
				<td align="center">${list.ZJE4 }</td>
				<td align="center">${list.ZF4 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='契税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS5 }</span></a></td>
				<td align="center">${list.ZJE5 }</td>
				<td align="center">${list.ZF5 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='资源税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS6 }</span></a></td>
				<td align="center">${list.ZJE6 }</td>
				<td align="center">${list.ZF6 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='烟叶税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS7 }</span></a></td>
				<td align="center">${list.ZJE7 }</td>
				<td align="center">${list.ZF7 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='印花税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WVS8 }</span></a></td>
				<td align="center">${list.ZJE8 }</td>
				<td align="center">${list.ZF8 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='企业所得税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS9 }</span></a></td>
				<td align="center">${list.WCS9 }</td>
				<td align="center">${list.ZF9 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='个人所得税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS10 }</span></a></td>
				<td align="center">${list.WCS10 }</td>
				<td align="center">${list.ZF10 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='城市维护建设税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS11 }</span></a></td>
				<td align="center">${list.ZJE11 }</td>
				<td align="center">${list.ZF11 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='教育费附加'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS12 }</span></a></td>
				<td align="center">${list.ZJE12 }</td>
				<td align="center">${list.ZF12 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='地方教育附加'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS13 }</span></a></td>
				<td align="center">${list.ZJE13 }</td>
				<td align="center">${list.ZF13 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='地方教育附加'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS14 }</span></a></td>
				<td align="center">${list.ZJE14 }</td>
				<td align="center">${list.ZF14 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='地方教育附加'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS15 }</span></a></td>
				<td align="center">${list.ZJE15 }</td>
				<td align="center">${list.ZF15 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='地方教育附加'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS16 }</span></a></td>
				<td align="center">${list.ZJE16 }</td>
				<td align="center">${list.ZF16 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='地方教育附加'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS17 }</span></a></td>
				<td align="center">${list.ZJE17 }</td>
				<td align="center">${list.ZF17 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='地方教育附加'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS18 }</span></a></td>
				<td align="center">${list.ZJE18 }</td>
				<td align="center">${list.ZF18 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='地方教育附加'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS19 }</span></a></td>
				<td align="center">${list.ZJE19 }</td>
				<td align="center">${list.ZF19 }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NAME }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='地方教育附加'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.WCS20 }</span></a></td>
				<td align="center">${list.ZJE20 }</td>
				<td align="center">${list.ZF20 }%</td>
				</c:if>
				
				<c:if test="${ ssqy == '3'}">
				<td align="center">${ status.index + 1}</td>					
				<td align="center">
				<a href="${basePath }web/companyinfo/detail?id=${list.COMPANY_ID }"  mask="true" target="dialog" rel="dlg_page10" width="750" height="600" 
						title="企业基本信息"  minable="true"><span>${list.NSRMC }</span></a>
				</td>
				<td align="center">${list.SSQY }</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NSRMC }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm=''" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.LJWC }</span></a></td>
				<td align="center">${list.ZJE }</td>
				<td align="center">${list.ZF }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NSRMC }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='进口关税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.JKGS }</span></a></td>
				<td align="center">${list.JKGS_ZJE }</td>
				<td align="center">${list.JKGS_ZF }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NSRMC }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm='进口增值税'" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.JKZZS }</span></a></td>
				<td align="center">${list.JKZZS_ZJE }</td>
				<td align="center">${list.JKZZS_ZF }%</td>
				</c:if>
				<c:if test="${ ssqy == '4'}">
				<td align="center">${ status.index + 1}</td>					
				<td align="center">
				<a href="${basePath }web/companyinfo/detail?id=${list.COMPANY_ID }"  mask="true" target="dialog" rel="dlg_page10" width="750" height="600" 
						title="企业基本信息"  minable="true"><span>${list.NSRMC }</span></a>
				</td>
				<td align="center">${list.AREA_NAME }</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NSRMC }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm=''" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.LJWCS }</span></a></td>
				<td align="center">${list.LJZJE }</td>
				<td align="center">${list.LJZF }%</td>
				<td align="center"><a href="${basePath }web/companytax/detail?qymc=${list.NSRMC }&year=${year}&month=${month}&ssqy=${ssqy}&zsxm=''" 
				 mask="true" target="navTab" rel="detail" title="纳税累计情况表 " minable="true"><span>${list.HLWCS }</span></a></td>
				<td align="center">${list.HLZJE }</td>
				<td align="center">${list.HLZF }%</td>
				</c:if>
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
		</script></strong> <span>条，共${pageList.totalCount}条</span>
	</div>
	<div class="pagination" targetType="navTab"
		totalCount="${pageList.totalCount}"
		numPerPage="${pageList.numPerPage}" pageNumShown="10"
		currentPage="${pageList.pageNum}"></div>
</div>

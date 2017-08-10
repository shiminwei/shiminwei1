<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }web/companyinfo/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage }" />
	<input type="hidden" name="name" value="${bean.name }" />
	<input type="hidden" name="isAboveScale" value="${bean.isAboveScale }" />
	<input type="hidden" name="isCanvassBuisiness" value="${bean.isCanvassBuisiness }" />
</form>

<form onsubmit="return navTabSearch(this);" action="${basePath }web/companyinfo/list" method="post" id="companyForm" name="searchForm">
<div class="pageHeader">
	<div class="searchBar">
	
			<h1 align="center" style="font-size: 30px; margin-top: 11px;margin-bottom: -3px">企业基本信息</h1>
			<h1 align="right" style="font-size: 16px">&nbsp;</h1>
			<h2 class="contentTitle" align="center"></h2>
		<ul class="searchContent">
			<li>
				<label>企业名称：</label>
				<input type="text" name="name" value="${bean.name }"/>
			</li>
			<li>
				<label style="width: 120px">是否规模以上企业${bean.isAboveScale}：</label>
				<select name="isAboveScale" style="width: 90px">				
					<option value="" <c:if test="${bean.isAboveScale==null || bean.isAboveScale=='' }">selected="selected"</c:if>>--请选择--</option>
					<option value="1" <c:if test="${bean.isAboveScale=='1' }">selected="selected"</c:if>>是</option>
					<option value="0" <c:if test="${bean.isAboveScale=='0'}">selected="selected"</c:if>>否</option>
				</select>
			</li>
			<li>
				<label style="width: 100px">是否招商企业：</label>
				<select name="isCanvassBuisiness" style="width: 90px" >
					<option value="" <c:if test="${bean.isCanvassBuisiness==null || bean.isCanvassBuisiness=='' }">selected="selected"</c:if>>--请选择--</option>
					<option value="1" <c:if test="${bean.isCanvassBuisiness=='1' }">selected="selected"</c:if>>是</option>
					<option value="0" <c:if test="${bean.isCanvassBuisiness=='0' }">selected="selected"</c:if>>否</option>
				</select>
			</li>
		</ul>
	</div>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" onclick="cleanForm('companyForm','navTab')" rel="companyManage"
			targettype="navTab"><span>清空查询</span></a></li>
		<li><a class="add" href="${basePath }web/companyinfo/toAddOrEdit" target="dialog" width="750" height="600" rel="addCompanyInfo" mask="true"><span>新增</span></a></li>
		<li><a title="确实要删除这些企业信息吗?" target="selectedTodo"
			postType="String" href="${basePath }web/companyinfo/toDelete?"
			class="delete"  rel="id" ><span>批量删除</span></a></li>
		<li><a title="企业信息批量导入" target="navTab" rel="upExcel"
			href="${basePath }web/companyinfo/toExcel"
			class="excel" ><span>企业信息批量导入</span></a></li>
		<li><a title="模板下载" rel="companyManage"
			href="${basePath }web/companyinfo/downExcel"
			class="down" ><span>模板下载</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="155">
		<thead>
			<tr>
				<th align="center"><input type="checkbox" group="id" class="checkboxCtrl"></th>
				<th align="center" style="font-weight: 900">企业名称</th>
				<th align="center" style="font-weight: 900">企业状态</th>	
				<!-- <th align="center" style="font-weight: 900" width="4%">企业属性</th>	 -->
				<th align="center" style="font-weight: 900">企业类型</th>
				<!-- <th align="center" style="font-weight: 900" width="4%">行业类型</th> -->
				<!-- <th align="center" style="font-weight: 900" width="4%">国标行业</th> -->
				<th align="center" style="font-weight: 900">国标行业名称</th>
				<th align="center" style="font-weight: 900">是否规模以上企业</th>
				<th align="center" style="font-weight: 900">是否招商企业</th>
				<!-- <th align="center" style="font-weight: 900" width="5%">登记时间</th> -->
				<th align="center" style="font-weight: 900">所属区域</th>
				<th align="center" style="font-weight: 900">法人代表</th>	
				<!-- <th align="center" style="font-weight: 900" width="5%">注册资本</th> -->	
				<!-- <th align="center" style="font-weight: 900" width="4%">经营类别</th>	 -->		
				<th align="center" style="font-weight: 900">经营范围</th>			
				<th align="center" style="font-weight: 900" >操作</th>
			</tr>
		</thead>		
		<tbody>
			<c:forEach var="list" items="${pageList.result}" >
				<tr  target="id" rel="${list.id}" ondblclick="qyDitle(${list.id})">				
				<td align="center"><input name="id" type="checkbox" value="${list.id}"></td>
				<td align="center">${list.name }</td>
				<td align="center">${list.status}</td>
				<%-- <td align="center">${list.companyAttr}</td>	 --%>
				<td align="center">${list.companyType }</td>
				<%-- <td align="center">${list.industryType }</td> --%>
				<%-- <td align="center">${list.idGbhy }</td> --%>
				<td align="center">${list.nameGbhy }</td>
				<td align="center">
				<a title="修改企业规模？" target="ajaxTodo" style="color: ${list.isAboveScale==1?'red':'black'}"
						href="${basePath }web/companyinfo/isAboveScale?type=1&id=${list.id}" rel="companyManage">				
				<c:if test="${list.isAboveScale==1}">是</c:if>
				<c:if test="${list.isAboveScale==0}">否</c:if>
				</a>			
				</td>
				<td align="center">
				<a title="修改招商标志？" target="ajaxTodo" style="color: ${list.isCanvassBuisiness==1?'red':'black'}"
						href="${basePath }web/companyinfo/isAboveScale?type=2&id=${list.id}" rel="companyManage">		
				<c:if test="${list.isCanvassBuisiness==1}">是</c:if>
				<c:if test="${list.isCanvassBuisiness==0}">否</c:if>
				</a>
				</td>
				<%-- <td align="center">${list.registerDate }</td> --%>
				<td align="center">${list.areaName }</td>	
				<td align="center">${list.legalPerson }</td>	
				<%-- <td align="center">${list.registerCapital }</td> --%>
				<%-- <td align="center">${list.managementCategory }</td>	 --%>		
				<td align="center" title="${list.dealArea }"><c:if test="${fn:length(list.dealArea)>20}">${fn:substring(list.dealArea,0,20)}...</c:if><c:if test="${fn:length(list.dealArea)<=20}">${list.dealArea }</c:if></td>				
				<td align="center" style="float:left;">				
					<a title="修改" target="dialog" width="750" height="600" style="color: blue"   mask="true"
						href="${basePath }web/companyinfo/toAddOrEdit?id=${list.id}">修改</a>&nbsp;&nbsp;
					<a title="确定要删除该企业信息吗?" target="ajaxTodo" style="color: blue"
						href="${basePath }web/companyinfo/toDelete?id=${list.id}" rel="companyManage">删除</a>&nbsp;&nbsp;
					<a href="${basePath }web/companyinfo/detail?id=${list.id }" style="color: blue" mask="true" target="dialog" rel="dlg_page10" width="750" height="600" 
						title="详情页面"  minable="true"><span>详情页面</span></a>&nbsp;&nbsp;					
					<a title="企业地址管理" target="navTab" rel="addressManage" style="color: blue" 
						href="${basePath }/web/address/list?id=${list.id}">企业地址管理</a>&nbsp;&nbsp;					
					<a title="纳税识别/信用代码管理" target="navTab" rel="numberManage" style="color: blue"  
						href="${basePath }/web/number/list?id=${list.id}">纳税识别/信用代码管理</a> 
				</td>
			</tr>
			</c:forEach>		
		</tbody>
	</table>
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
</div>

<script type="text/javascript">
function qyDitle(qyid){
	$.pdialog.open("${basePath }web/companyinfo/detail?id="+qyid,"dlg_page10","", {title:"详情页面",width:"750",height:"600",fresh:true,minable:true});
}
</script>
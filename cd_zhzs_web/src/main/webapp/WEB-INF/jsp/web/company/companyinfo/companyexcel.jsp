<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }web/companyinfo/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage }" />
</form>

<form class="pageForm required-validate" id="previewExcel" action="${basePath }/web/companyinfo/previewExcel" method="post" enctype="multipart/form-data" onsubmit="return iframeCallback(this,navTabAjaxDone)" novalidate="novalidate">
<div class="pageHeader">
	<div class="searchBar">
	<ul class="searchContent">
		<li>
			<input id="uploadExcel" name="uploadExcel" type="file" accept=".xls,.xlsx" style="width: 270px" value=""/>
		</li>
		<li style="width: 370px">
		    <label style="width: 100px">预览后的文件名：</label>
		    <input type="text" id="filename" name="filename" value="${filename }" title="${filename }" readonly="readonly" style="width: 180px ; margin-left: 0px;"/>
		</li>
	</ul>
	</div>
</div>
<div class="panelBar" align="center" style="color:red">
	<ul class="toolBar">
	    <li><a class="preview" onclick="toPreviewExcel()" rel="upExcel"
				targettype="navTab"><span>预览</span></a></li>
		<li><a class="save" onclick="toSaveExcel()" rel="upExcel"
				targettype="navTab"><span>保存</span></a></li>
		<li><a class="clear" onclick="clean('upExcel')" rel="upExcel"
				targettype="navTab"><span>清空</span></a></li>
	</ul>
	<li align="center">${message }</li>
</div>
</form>
<div class="pageContent">	
	<table class="table" width="230%" layoutH="112">
		<thead>
			<tr>
				<th align="center" style="font-weight: 900" width="6%">解析结果</th>
				<th align="center" style="font-weight: 900" width="7%">企业名称</th>
				<th align="center" style="font-weight: 900" width="3%">企业状态</th>	
				<th align="center" style="font-weight: 900" width="3%">企业属性</th>	
				<th align="center" style="font-weight: 900" width="3%">企业类型</th>
				<th align="center" style="font-weight: 900" width="3%">行业类型</th>
				<th align="center" style="font-weight: 900" width="3%">国标行业</th>
				<th align="center" style="font-weight: 900" width="4%">国标行业名称</th>
				<th align="center" style="font-weight: 900" width="5%">是否规模以上企业</th>
				<th align="center" style="font-weight: 900" width="4%">是否招商企业</th>
				<th align="center" style="font-weight: 900" width="3%">登记时间</th>
				<th align="center" style="font-weight: 900" width="3%">所属区域</th>
				<th align="center" style="font-weight: 900" width="3%">法人代表</th>
				<th align="center" style="font-weight: 900" width="3%">联络员</th>			
				<th align="center" style="font-weight: 900" width="4%">联络员电话</th>
				<th align="center" style="font-weight: 900" width="3%">政治面貌</th>
				<th align="center" style="font-weight: 900" width="3%">注册资本</th>
				<th align="center" style="font-weight: 900" width="3%">币种</th>
				<th align="center" style="font-weight: 900" width="4%">经营类别</th>
				<th align="center" style="font-weight: 900" width="4%">经营范围</th>
				<th align="center" style="font-weight: 900" width="4%">管辖单位</th>				
				<th align="center" style="font-weight: 900" width="4%">主管管辖单位</th>
				<th align="center" style="font-weight: 900" width="5%">(受委托)登记机关</th>
				<th align="center" style="font-weight: 900" width="4%">主管登记机关</th>
				<th align="center" style="font-weight: 900" width="4%">核准日期</th>
				<th align="center" style="font-weight: 900" width="5%">联系电话</th>			
			</tr>
		</thead>		
		<tbody>
			<c:forEach var="list" items="${pageList.result}" >
				<tr>				
				<td align="center" style="color:red" title="${list.checkResult }">${list.checkResult }</td>
				<td align="center">${list.name }</td>
				<td align="center">${list.status}</td>
				<td align="center">${list.companyAttr}</td>
				<td align="center">${list.companyType }</td>
				<td align="center">${list.industryType }</td>
				<td align="center">${list.idGbhy }</td>
				<td align="center">${list.nameGbhy }</td>
				<td align="center">${list.isAboveScale}</td>
				<td align="center">${list.isCanvassBuisiness}</td>
				<td align="center">${list.registerDate }</td>
				<td align="center">${list.areaName }</td>
				<td align="center">${list.legalPerson }</td>
				<td align="center">${list.linkman }</td>			
				<td align="center">${list.linkmanMobile }</td>
				<td align="center">${list.politicalOutlook }</td>
				<td align="center">${list.registerCapital }</td>
				<td align="center">${list.currency }</td>
				<td align="center">${list.managementCategory }</td>
				<td align="center">${list.dealArea }</td>
				<td align="center">${list.jurisdictionUnit }</td>			
				<td align="center">${list.mainJurisdictionUnit }</td>
				<td align="center">${list.registrationUint }</td>
				<td align="center">${list.mainRegistrationUint }</td>
				<td align="center">${list.approvalDate }</td>
				<td align="center">${list.contactMobile }</td>
			</tr>
			</c:forEach>		
		</tbody>
	</table>
</div>
<script type="text/javascript">
//预览
function toPreviewExcel() {
	$("#previewExcel").submit();
}
//保存
function toSaveExcel() {
	var filename = $("#filename").val();
	if(filename == '' || filename==null || filename == undefined){
		alertMsg.error("请上传文件并预览 !");	
		return false;
	}
	var data = {"filename":filename};
	$.ajax({ 
		type:"POST", 
		url:"${basePath }web/companyinfo/saveExcel",
		data : data,
		dataType : "json",
		cache : false,
		success :function(json){
			if (json[DWZ.keys.statusCode] == DWZ.statusCode.ok){
				alertMsg.correct(json[DWZ.keys.message]);
				if (json.navTabId){
					navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
				} else {
					var $pagerForm = $("#pagerForm", navTab.getCurrentPanel());
					var args = $pagerForm.size()>0 ? $pagerForm.serializeArray() : {}
					navTabPageBreak(args, json.rel);
				}
				if ("closeCurrent" == json.callbackType) {
					$.pdialog.closeCurrent();
				}
			}else if(json[DWZ.keys.statusCode] == DWZ.statusCode.error){
				alertMsg.error(json[DWZ.keys.message]);	
				return false;
			}
		}
	});
}

//清空,并重新加载navTab
function clean(rel){
	navTab.reloadFlag(rel);
	return true;
}

// 根据rel刷新指定的navtTab
function reloadNavTab(param){
	navTab.reloadFlag(param.rel);
	return true;
}
</script>
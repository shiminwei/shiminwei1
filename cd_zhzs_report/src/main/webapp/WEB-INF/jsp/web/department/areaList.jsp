<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp" %> 
<div class="pageContent">
<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div>
				<div layoutH="40" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
				    <ul class="tree treeFolder">
						<li><a href="javascript">行政区域</a>
							<ul>
								<c:forEach items="${areaList }" var="area">
								<li><a name="webAreaList" href="${basePath }/web/department/depList?areaId=${area.areaId }" target="ajax" rel="jbsxBox">${area.areaName }</a></li>
								</c:forEach>
							</ul>
						</li>
						
				     </ul>
				</div>
				<div id="jbsxBox" class="unitBox" style="margin-left:246px;">
				</div>
			</div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>
<script>
$(function(){
	 $('#jbsxBox').loadUrl("${basePath }/web/department/depList?areaId=${areaList[0].areaId }", null, function (message) {
         $('#jbsxBox').parent().find("[layoutH]").layoutH();
     });
})
</script>
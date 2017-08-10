<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<link rel="stylesheet" href="${basePath }static/css/switch.css">



<form id="pagerForm" method="post" action="${basePath }web/dataJob/log" onsubmit="return navTabSearch(this);">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>
<form onsubmit="return navTabSearch(this);" action="${basePath }web/dataJob/log" method="post" modelAttribute="dataJobBean"  name="searchForm"  id="searchForm">
<div class="pageHeader">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label style="width:65px;">任务id：</label>
				<input type="text" name="jobId" value="${dataJobBean.jobId }"/>
			</li>
			<li>
				<label style="width:65px;">任务名称：</label>
				<input type="text" name="name" value="${dataJobBean.name }"/>
			</li>
		</ul>
	</div>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"> <span>查询日志</span></button></a></li>
		<li><a class="icon" id="abyexport" href="#" onclick="cleanForm('searchForm','navTab')"><span>清空查询</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="91">
		<thead>
			<tr>
				<th  align="center" width=20%>任务id</th>
				<th  align="center" width=20%>任务名称</th>
				<th  align="center" width=20%>任务类型</th>
				<th  align="center" width=20%>任务周期</th>
				<th  align="center">详细</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="list" items="${newResult}"  varStatus="status">
			<tr >
				<td   align="center" >${list.jobId}</td>
				<td   align="center" >${list.name}</td>
				<td   align="center" >
					<c:if test="${list.type==1 }">立即任务</c:if>
					<c:if test="${list.type==2 }">周期任务</c:if>
					<c:if test="${list.type==3 }">定时任务</c:if>
				</td>
				<td   align="center" >${list.runPeriod}</td>
				<td   align="center">
					<div width=100%>
						<div ><a  style="margin-left:15px"class="button" href="${basePath }web/dataJob/checkJobLogInfo?jobId=${list.jobId}" target="navTab" rel="jobsteplist" ><span>详细</span></a></div>
				   	</div>
				</td>

			</tr>
		</c:forEach>
		</tbody>
	</table>
	<script type="text/javascript">
		function jobDetail(jobId){
			debugger;
			var options = {mask:true, 
                    width:850, height:600, 
                    maxable: eval("true"), 
                    resizable:eval("true") 
                }; 
			$.pdialog.open("${basePath }web/dataJob/checkJobLogInfo?jobId="+jobId,"jobPeriodConfig","查看详细", options);
		}
		
		function abc(){
			alert("11111");
			$.ajax({
		        type: 'get',
		        url: "http://localhost:8080/cd_zhzs_job/web/dataJob/getScheduleLogList",
		        dataType: "json",
		        data: {},
		        success: function (data) {
		        	debugger;
		            if (data && data.length == 5) {
		                var strHTML = "<ul>";
		                for (var i = 0; i < data.length; i++) {
		                    strHTML += "<li>" + data[i] + "</li>";
		                }
		                strHTML +="</ul>"
		            //    $("#runTime").html(strHTML);
		            } else {
		            //    $("#runTime").html("");
		            }
		        }
		    });
		} 
	</script>
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

</div>

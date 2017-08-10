<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<link rel="stylesheet" href="${basePath }static/css/switch.css">



<form id="pagerForm" method="post" action="${basePath }web/dataJob/list" onsubmit="return navTabSearch(this);">
	 <input type="hidden" name="pageNum" value="${pageList.pageNum }" /> 
	 <input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	 <input type="hidden" id="order" name="order" value="${order }" />
	 <input type="hidden" id="usedOrder" name="usedOrder" value="${usedOrder }" />
	 <input type="hidden" id="runOrder" name="runOrder" value="${runOrder }" />
	 <input type="hidden" id="typeOrder" name="typeOrder" value="${typeOrder }" />
</form>
<form onsubmit="return navTabSearch(this);" action="${basePath }web/dataJob/list" method="post" modelAttribute="queryBean"  name="searchForm"  id="searchForm">
<div class="pageHeader">
	<div class="searchBar">
		<ul class="searchContent">
			<%-- <li>
				<label style="width:25px;">id：</label>
				<input type="text" name="_id" value="${queryBean._id }"/>
			</li> --%>
			<li>
				<label style="width:65px;">任务名称：</label>
				<input type="text" name="name" value="${queryBean.name }"/>
			</li>
			<li>
				<label style="width:65px;">任务类型：</label>
				<%-- <input type="text" name="type" value="${queryBean.type }"/> --%>
				<select id="type" name="type">
					<option value="0" >==请选择==</option>
					<option value="1" <c:if test="${queryBean.type==1 }"> selected="selected"</c:if>>立即任务</option>
					<option value="2" <c:if test="${queryBean.type==2 }"> selected="selected"</c:if>>周期任务</option>
					<option value="3" <c:if test="${queryBean.type==3 }"> selected="selected"</c:if>>定时任务</option>
				</select>
			</li>
			<li>
				<label style="width:65px;">运行状态：</label>
				<select id="status" name="status">
					<option value="0">==请选择==</option>
					<option value="1" <c:if test="${queryBean.status==1 }"> selected="selected"</c:if>>正在运行</option>
					<option value="2" <c:if test="${queryBean.status==2 }"> selected="selected"</c:if>>未运行</option>
					<option value="3" <c:if test="${queryBean.status==3 }"> selected="selected"</c:if>>暂停运行</option>
					<option value="4" <c:if test="${queryBean.status==4 }"> selected="selected"</c:if>>运行结束</option>
				</select>
			</li>
		</ul>
	</div>
</div>
<div class="panelBar">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"> <span>查询数据</span></button></a></li>
		<li><a class="icon" id="abyexport" href="#" onclick="cleanForm('searchForm','navTab')"><span>清空查询</span></a></li>
		<li><a class="add"  href="${basePath}web/dataJob/toAdd" target="navTab"><span>新增任务</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent">
	<table class="list" width="100%" layoutH="91">
		<thead>
			<tr>
				<th  align="center" width=15% class="desc" id="orderTd">任务id</th>
				<th  align="center" width=15% >任务名称</th>
				<th  align="center" width=15% class="desc" id="typeTd">任务类型</th>
				<th  align="center" class="desc" id="usedTd">使用状态</th><!-- 0 表示未使用 1 表示使用-->
				<th  align="center" width=15%>周期</th>
				<th  align="center" width=10% class="desc" id="runTd">运行状态</th>
				<th  align="center" width=20%>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="list" items="${pageList.result}"  varStatus="status">
			<tr >
				<td   align="center" 
					<c:if test="${list.status == 1}">style="color:green"</c:if>
					<c:if test="${list.status == 2}">style="color:gray"</c:if>
					<c:if test="${list.status == 3}">style="color:red"</c:if>>${list.jobId}</td>
				<td   align="center" >${list.name}</td>
				<td   align="center" >
					<c:if test="${list.type==1 }">立即任务</c:if>
					<c:if test="${list.type==2 }">周期任务</c:if>
					<c:if test="${list.type==3 }">定时任务</c:if>
				</td>
				<td  align="center">
					<div>
			   			<label><a href="${basePath }web/dataJob/changeUsedOrNot?jobId=${list.jobId}&status=${list.status }&used=${list.used }" target="ajaxTodo" rel="startDataJob" >
			   			<input class="mui-switch" type="checkbox"<c:if test="${list.used==1 }">checked</c:if>></a>
			   			</label>
				   	</div>	
				</td>
				<td   align="center" >${list.runPeriod}</td>
				<td   align="center" >
					<c:if test="${list.status == 1}">正在运行</c:if>
					<c:if test="${list.status == 2}">未运行</c:if>
					<c:if test="${list.status == 3}">暂停运行</c:if>
					<c:if test="${list.status == 4}">运行结束</c:if></td>
				<td   align="center">
					<div width=100%>
						<div ><a  style="margin-left:15px"class="button" href="${basePath }web/jobstep/list?jobId=${list.jobId}" target="navTab" rel="jobsteplist" ><span>查看步骤</span></a></div>
						<div ><a style="margin-left:15px" class="button" href="${basePath }web/dataJob/checkJobLogInfo?jobId=${list.jobId}&flag=1" target="navTab"><span>查看日志</span></a></div>
						<div > <!-- 1:正在运行 2:未运行 3:暂停运行 -->
						<%-- <a style="margin-left:15px" class="button" href="${basePath }web/dataJob/startDataJob?_id=${list._id}" target="ajaxTodo" rel="startDataJob" id="abc"><span>开始调度</span></a> --%>
							<c:if test="${list.status == 2 ||list.status == 4}">
								<a style="margin-left:15px" class="button" href="${basePath }web/dataJob/startDataJob?jobId=${list.jobId}" target="ajaxTodo" rel="startDataJob" id="abc"><span style="color: green;">创建调度</span></a>
							</c:if>
						</div>
						<div > 
							<c:if test="${list.status == 1}">
								<a style="margin-left:15px;" class="button" href="${basePath }web/dataJob/stopDataJob?jobId=${list.jobId}" target="ajaxTodo" rel="startDataJob" id="abc"><span style="color: red;">停止调度</span></a>
							</c:if>
				   		</div>
				   		<div > 
							<c:if test="${list.status == 3}">
								<a style="margin-left:15px;" class="button" href="${basePath }web/dataJob/resumeDataJob?jobId=${list.jobId}" target="ajaxTodo" rel="startDataJob" id="abc"><span style="color: navy;">恢复调度</span></a>
							</c:if>
				   		</div>
				   		
				   		<div > 
							<c:if test="${list.status == 3}">
								<a style="margin-left:15px;" class="button" href="${basePath }web/dataJob/deleteDataJob?jobId=${list.jobId}" target="ajaxTodo" rel="startDataJob" id="abc"><span>销毁调度</span></a>
							</c:if>
				   		</div>
				   		
				   		
				   			<%-- <ul>
							<li style="margin-left: 8%"><a class="button" href="${basePath }web/jobstep/list?_id=${list._id}" target="navTab" rel="jobsteplist" ><span>查看步骤</span></a></li>
						<li style="margin-left: 37%"><a class="button" href="${basePath }web/dataJob/checkJobLogInfo?_id=${list._id}" target="navTab"><span>查看日志</span></a></li>
						<li style="margin-left: 65%"><a class="button" href="${basePath }web/dataJob/startDataJob?_id=${list._id}" target="ajaxTodo" rel="startDataJob" id="abc"><span>开始调度</span></a></li>
						<li style="margin-left: 65%"><a class="button" href="${basePath }web/dataJob/startDataJob?_id=${list._id}" target="ajaxTodo" rel="startDataJob" id="abc"><span>停止调度</span></a></li>
					</ul> --%>
				</td>

			</tr>
		</c:forEach>
		</tbody>
	</table>
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

<script type="text/javascript">
	$(document).ready(function(){
		var order = $("#order").val();
		var usedOrder = $("#usedOrder").val();
		var runOrder =$("#runOrder").val();
		var typeOrder =$("#typeOrder").val();
		if(order==""||order==null){
			$('#orderTd').attr("class","desc");
		}
		if(order=="desc"){
			$('#orderTd').attr("class","asc");
		}
		if(order=="asc"){
			$('#orderTd').attr("class","desc");
		}
		if(usedOrder==""||usedOrder==null){
			$('#usedTd').attr("class","desc");
		}
		if(usedOrder=="desc"){
			$('#usedTd').attr("class","asc");
		}
		if(usedOrder=="asc"){
			$('#usedTd').attr("class","desc");
		}
		
		if(runOrder==""||runOrder==null){
			$('#runTd').attr("class","desc");
		}
		if(runOrder=="desc"){
			$('#runTd').attr("class","asc");
		}
		if(runOrder=="asc"){
			$('#runTd').attr("class","desc");
		}
		
		if(typeOrder==""||typeOrder==null){
			$('#typeTd').attr("class","desc");
		}
		if(typeOrder=="desc"){
			$('#typeTd').attr("class","asc");
		}
		if(typeOrder=="asc"){
			$('#typeTd').attr("class","desc");
		}
		$('#orderTd').click(function(){
			if(usedOrder!=null||usedOrder!=""){
				$('#usedOrder').attr("value","");
			}
			if(runOrder!=null||runOrder!=""){
				$('#runOrder').attr("value","");
			}
			if(typeOrder!=null||typeOrder!=""){
				$('#typeOrder').attr("value","");
			}
			if(order==""||order==null){
				$('#order').attr("value","desc");
			}
			if(order=="desc"){
				$('#order').attr("value","asc");
			}
			if(order=="asc"){
				$('#order').attr("value","desc");
			}
			$("#pagerForm").submit();
		});
		//按照任务类型排序
		$('#typeTd').click(function(){
			if(usedOrder!=null||usedOrder!=""){
				$('#usedOrder').attr("value","");
			}
			if(runOrder!=null||runOrder!=""){
				$('#runOrder').attr("value","");
			}
			if(typeOrder==""||typeOrder==null){
				$('#typeOrder').attr("value","desc");
			}
			if(typeOrder=="desc"){
				$('#typeOrder').attr("value","asc");
			}
			if(typeOrder=="asc"){
				$('#typeOrder').attr("value","desc");
			}
			$("#pagerForm").submit();
			
		});
		//按照使用状态排序
		$('#usedTd').click(function(){
			if(runOrder!=null||runOrder!=""){
				$('#runOrder').attr("value","");
			}
			if(typeOrder!=null||typeOrder!=""){
				$('#typeOrder').attr("value","");
			}
			if(usedOrder==""||usedOrder==null){
				$('#usedOrder').attr("value","desc");
			}
			if(usedOrder=="desc"){
				$('#usedOrder').attr("value","asc");
			}
			if(usedOrder=="asc"){
				$('#usedOrder').attr("value","desc");
			}
			$("#pagerForm").submit();
		});
		//按照运行状态排序
		$('#runTd').click(function(){
			if(usedOrder!=null||usedOrder!=""){
				$('#usedOrder').attr("value","");
			}
			if(typeOrder!=null||typeOrder!=""){
				$('#typeOrder').attr("value","");
			}
			if(runOrder==""||runOrder==null){
				$('#runOrder').attr("value","desc");
			}
			if(runOrder=="desc"){
				$('#runOrder').attr("value","asc");
			}
			if(runOrder=="asc"){
				$('#runOrder').attr("value","desc");
			}
			$("#pagerForm").submit();
			
		});
	});
</script>

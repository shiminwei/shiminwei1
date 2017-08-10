<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<form id="pagerForm" method="post" action="${basePath }web/jobstep/list" onsubmit="return navTabSearch(this);">
		<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> 
		<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> 
		<input type="hidden" name="jobId" value="${jobId}" />
</form>
<form onsubmit="return navTabSearch(this);" action="${basePath }web/jobstep/list" method="post" id="searchForm" name="searchForm">
<div class="panelBar" align="center" style="color:red">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit">
					<span>查询数据</span></button></a></li>
		<li><a class="add" href="${basePath }web/jobstep/toType?jobId=${jobId}" target="navTab" 
					rel="toAddJobStep"><span>新增步骤</span></a></a></li>
	</ul>
</div>
</form>  
<div class="pageContent">
	<table class="list" width="100%" layoutH="45">
		<thead>
			<tr>
				<th align="center">序号</th>
				<th align="center">操作类型</th>
				<th align="center">名称</th>
				<th align="center" style="display:none;">stepId</th>
				<th align="center" style="display:none;">sortNum</th>
				<th align="center">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="list" items="${pageList.result}" varStatus="status">
			<tr >
				<td align="center">${status.index+1}</td>
				<td align="center">${map[list.type]}</td>
				<td align="center">${list.name}</td>
				<td align="center" style="display:none;">${list.stepId}</td>
				<td align="center" style="display:none;">${list.sortNum}</td>
				<td align="center" width="300px">				
					<a style="margin-left: 30px" title="修改" target="navTab"rel="toEditJobStep" class="btnEdit"  
						href="${basePath }web/${editmap[list.type] }?jobId=${jobId}&stepId=${list.stepId}">修改</a>
					<a style="margin-left: 25px" title="确定要删除吗?" target="ajaxTodo" class="btnDel"
						href="${basePath }web/jobstep/toScriptDelete?jobId=${jobId}&stepId=${list.stepId}&sortNum=${list.sortNum}" rel="jobsteplist">删除</a>&nbsp;&nbsp;&nbsp;					
					<div style="float: left;">
						<a style="color: red;margin-left: 25px;cursor:pointer;font-weight: 900" target="ajaxTodo" class="up" rel="jobsteplist" 
							href="javascript:void(0)">上移</a>				
						<a style="color: red;margin-left: 10px;cursor:pointer;font-weight: 900" target="ajaxTodo" class="down" rel="jobsteplist"  
							href="javascript:void(0)">下移</a>
					</div>
					<c:if test="${list.type=='5'||list.type=='4'}">
						<a style="margin-left:10px" href="${basePath }web/excel/toDownload?jobId=${jobId}&stepId=${list.stepId}">导出Excel模板</a>
					</c:if>
				</td>
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
<script type="text/javascript">
$(function() {
	//上移 
	var $up = $(".up")
	$up.click(function() {
		var $tr = $(this).parents("tr"); //获取当前点击的tr行
		var $prevTr = $tr.prev(); //获取当前点击的上一个tr行
		$tr.fadeOut().fadeIn();	//淡入淡出效果
		var current=[];
		 $tr.find("td").each(function(){
			 current.push($.trim($(this).text())); //将当前tr中的td值遍历加入current中
 		}); 
		var previous=[];
		$prevTr.find("td").each(function(){
			previous.push($.trim($(this).text())); //将上一个tr中的td值遍历加入previous中
 		});
		ajaxTodo("${basePath }web/jobstep/upOrDown?jobId=${jobId}" + "&type=1&stepid1="+current[3]+"&stepid2="+previous[3]+"&stepSort1="+current[4]+"&stepSort2="+previous[4]); 
	});	
	
	//下移 
	var $down = $(".down");
	$down.click(function() {
		var $tr = $(this).parents("tr"); //获取当前点击的tr行
			$tr.fadeOut().fadeIn();	//淡入淡出效果
		var $nextTr = $tr.next(); //获取当前点击的下一个tr行
		var current=[];
		 $tr.find("td").each(function(){
			 current.push($.trim($(this).text())); //将当前tr中的td值遍历加入current中
 		});
		var next=[];
		$nextTr.find("td").each(function(){
 			next.push($.trim($(this).text())); //将下一个tr中的td值遍历加入next中
 		});
		ajaxTodo("${basePath }web/jobstep/upOrDown?jobId=${jobId}" +"&type=2&stepid1="+current[3]+"&stepid2="+next[3]+"&stepSort1="+current[4]+"&stepSort2="+next[4]);	 
	});
});
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<form id="pagerForm" method="post"
	action="${basePath }admin/zdConstant/chileList?type=${bean.type}&name=${bean.name}">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input type="hidden"
		name="numPerPage" value="${pageList.numPerPage }" />
</form>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${basePath }admin/zdConstant/toSaveOrUpdate?istype=2&type=${bean.type}&name=${bean.name}"
				target="dialog" rel="addNotice"  width="800" height="350"><span>新增子码表</span></a></li>
			<li><a title="确实要删除这些公告吗?" target="selectedTodo"
				postType="String" href="${basePath }admin/zdConstant/toDelete" class="delete"
				rel="ids"><span>批量删除</span></a></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="53">
		<thead>
			<tr>
				<th align="center" width="60"><input type="checkbox"
					group="ids" class="checkboxCtrl"></th>
				<th align="center" width="3%">序号</th>
				<th align="center">CODE</th>
				<th align="center">VALUE</th>
				<th align="center" style="display:none;">序号</th>
				<th align="center">状态</th>
				<th align="center">操作</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach var="list" items="${pageList.result}" varStatus="status">
				<tr>
					<td align="center"><input name="ids" type="checkbox"
						value="${list.constantId }"></td>
					<td align="center">${status.index+1 }</td>
					<td align="center" >${list.code }</td>
					<td align="center" >${list.value }</td>
					<td align="center" style="display:none;">${list.constantId}</td>		
					<td align="center"><c:if test="${list.state==1}">
							<span style="color: green">正常使用</span>
							</span>
						</c:if> <c:if test="${list.state==0}">
							<span style="color: red">已经禁用</span>
							</span>
						</c:if>
					</td>
					<td align="center" width="227px"><a style="margin-left: 30px"
						title="修改" target="dialog"
						href="${basePath }admin/zdConstant/toSaveOrUpdate?constantId=${list.constantId }&istype=3&type=${list.type }&name=${list.name }" class="btnEdit"  width="800" height="350">编辑</a>
						<a style="margin-left: 25px" title="确定要删除该条数据吗?" target="ajaxTodo"
						href="${basePath }admin/zdConstant/toDelete?ids=${list.constantId }&forwardType=2" class="btnDel"
						rel="userManage">删除</a>
						<a href="javascript:"  class="up" rel="zdConstantManage" style="color: red;margin-left: -8px;cursor:pointer;font-weight: 900">上移</a>
						<a href="javascript:"  class="down" rel="zdConstantManage" style="color: red;margin-left: 18px;cursor:pointer;font-weight: 900">下移</a>	
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<input type="hidden" name="type2" value="${bean.type }">
	<input type="hidden" name="name2" value="${bean.name }">
</div>
<script type="text/javascript">
$(function() {
	//上移 
	var $up = $(".up")
	$up.click(function() {
		var $tr = $(this).parents("tr"); 	//获取当前点击的tr行
		var $prevTr = $tr.prev();//获取当前点击的上一个tr行
		$tr.fadeOut().fadeIn();				//淡入淡出效果
		valArr1=[];
		 $tr.find("td").each(function(){
 			valArr1.push($.trim($(this).text())); //讲当前tr中的td值遍历加入valArr1中
 		});
// 			alert("当前menuID="+valArr1[4]); 
		valArr=[];
		$prevTr.find("td").each(function(){
 			valArr.push($.trim($(this).text())); //讲上一个tr中的td值遍历加入valArr中
 		});
// 	 		alert("上一个menuID="+valArr[4]);
		var type = 3;
		if(valArr[4]==undefined){
			type = 1;
		}
 		ajaxTodo("${basePath }admin/zdConstant/upOrDown?menuId="+valArr1[4]+"&needMobeId="+valArr[4]+"&type="+type); 
	});
	
	
	//下移 
	var $down = $(".down");
	$down.click(function() {
		var $tr = $(this).parents("tr"); 	//获取当前点击的tr行
			$tr.fadeOut().fadeIn();				//淡入淡出效果
		var $prevTr = $tr.next();//获取当前点击的上一个tr行
		valArr1=[];
		 $tr.find("td").each(function(){
 			valArr1.push($.trim($(this).text())); //讲当前tr中的td值遍历加入valArr1中
 		});
// 			alert("当前menuID="+valArr1[4]); 
		valArr=[];
		$prevTr.find("td").each(function(){
 			valArr.push($.trim($(this).text())); //讲上一个tr中的td值遍历加入valArr中
 		});
// 	 		alert("上一个menuID="+valArr[4]); 
		var type = 3;
		if(valArr[4]==undefined){
			type = 2;
		}
 		ajaxTodo("${basePath }admin/zdConstant/upOrDown?menuId="+valArr1[4]+"&needMobeId="+valArr[4]+"&type="+type); 
	});
});
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


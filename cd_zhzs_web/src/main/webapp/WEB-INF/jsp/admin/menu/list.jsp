<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<form id="pagerForm" method="post"
	action="${basePath }admin/webMenu/list?menuLevel=${bean.menuLevel }&parentCode=${bean.parentCode}">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" /> <input
		type="hidden" name="numPerPage" value="${pageList.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="menuLevel" value="${bean.menuLevel}" />
<%-- 	<input type="hidden" name="parentCode" value="${bean.parentCode}" /> --%>
</form>


<form method="post"
	action="${basePath }admin/webMenu/list?menuLevel=${bean.menuLevel }&parentCode=${bean.parentCode}"
	onsubmit="return navTabSearch(this);">
<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>菜单名称：</label> <input type="text" name="name" value="${bean.name}" /></li>
			</ul>
			
		</div>
</div>
<div class="panelBar" style="border-width: 0px 0px 1px 0px;">
	<ul class="toolBar">
		<li><a class="search" href="javascript:void(0)"><button type="submit"><span>查询数据</span></button></a></li>
		<li><a class="icon" id="abyexport" href="#" onclick="cleanForm('pagerForm','navTab')"><span>清空查询</span></a></li>
		<li><a class="add"
			href="${basePath }admin/webMenu/toSaveOrUpdate?menuId=${list.menuId}&type=1&menuLevel=${bean.menuLevel}&parentCode=${bean.parentCode}&parentName=${bean.parentName}"
			target="navTab" rel="configAdd"><span>新增菜单</span></a></li>
		<li><a title="确实要删除这些配置吗?" target="selectedTodo"
			postType="string" href="${basePath }admin/webMenu/toDelete"
			class="delete"><span>批量删除</span></a></li>
	</ul>
</div>
</form>
<div class="pageContent" style="width: auto;overflow: x:scroll;">
	<table class="list" width="100%" layoutH="90">
		<thead>
			<tr>
				<th align="center" width="4%"><input type="checkbox" group="ids"
					class="checkboxCtrl" ></th>
				<th align="center" width="8%" >序号</th>
				<th align="center">菜单CODE</th>
				<th align="center">菜单名称</th>
				<th align="center" style="display:none;" width="7%">序号</th>
				<th align="center" width="25%">操作</th>
			</tr>
		</thead>
		<tbody id="tb">
			<c:forEach var="list" items="${pageList.result}" varStatus="status">
				<tr>
					<td align="center"><input name="ids" type="checkbox"
						value="${list.code}"></td>

					<td align="center">${ status.index + 1}</td>
					<td align="center" >${list.code }</td>
					<td align="center">${list.name }</td>
					<td align="center" style="display:none;">${list.menuId}</td>
					<td align="center"><a style="margin-left: 13%" title="编辑" target="navTab"
						href="${basePath }admin/webMenu/toSaveOrUpdate?menuId=${list.menuId}&type=2&menuLevel=${bean.menuLevel}&parentCode=${list.parentCode}"
						class="btnEdit">编辑</a> <a style="margin-left: 7%"  title="删除" target="ajaxTodo"
						href="${basePath }admin/webMenu/toDelete?ids=${list.code }"
						class="btnDel" rel="webMnuList">删除</a> 
						<c:if test="${bean.menuLevel!=3 }">
						<a style="margin-left: 7%" title="子菜单管理"
						target="navTab"
						href="${basePath }admin/webMenu/chileList?menuId=${list.menuId }&menuLevel=${bean.menuLevel}&parentCode=${list.code}&parentName=${list.name}"
						class="btnInfo" rel="webMnuList">子菜单管理</a><a style="margin-left: -7px"></a>
						</c:if>
						<a href="javascript:"  class="up"><span style="color: red;cursor:pointer;font-weight: 900;">上移</span></a>
						<a href="javascript:"  class="down"><span style="color: red;cursor:pointer;font-weight: 900;margin-left:6%">下移</span></a> 		
						</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
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
	 			valArr1.push($.trim($(this).text())); //将当前tr中的td值遍历加入valArr1中
	 		});
// 			alert("当前menuID="+valArr1[4]); 
			valArr=[];
			$prevTr.find("td").each(function(){
	 			valArr.push($.trim($(this).text())); //将上一个tr中的td值遍历加入valArr中
	 		});
// 	 		alert("上一个menuID="+valArr[4]);
			var type = 3;
			if(valArr[4]==undefined){
				type = 1;
			}
	 		ajaxTodo("${basePath }admin/webMenu/upOrDown?menuId="+valArr1[4]+"&needMobeId="+valArr[4]+"&type="+type); 
		});
		
		
		//下移 
		var $down = $(".down");
		$down.click(function() {
			var $tr = $(this).parents("tr"); 	//获取当前点击的tr行
			$tr.fadeOut().fadeIn();				//淡入淡出效果
			var $prevTr = $tr.next();//获取当前点击的上一个tr行
			valArr1=[];
			 $tr.find("td").each(function(){
	 			valArr1.push($.trim($(this).text())); //将当前tr中的td值遍历加入valArr1中
	 		});
// 			alert("当前menuID="+valArr1[4]); 
			valArr=[];
			$prevTr.find("td").each(function(){
	 			valArr.push($.trim($(this).text())); //将上一个tr中的td值遍历加入valArr中
	 		});
// 	 		alert("上一个menuID="+valArr[4]); 
			var type = 3;
			if(valArr[4]==undefined){
				type = 2;
			}
	 		ajaxTodo("${basePath }admin/webMenu/upOrDown?menuId="+valArr1[4]+"&needMobeId="+valArr[4]+"&type="+type); 
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


















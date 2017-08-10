<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../../../common/base.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body>
	<div id="resultBox"></div>
	<div class="panel" defH="40">
		<h1>操作</h1>
		<div>
			<ul class="leftTools">
				<li style="margin-right: 1.8%;float: left;margin-left:1.5%"><a class="button" href="javascript:" onclick="gotoAddDepartment()"><span>新增部门</span></a></li>
				<li style="margin-right: 1.8%;float: left"><a class="button" href="javascript:" onclick="gotoDeleteDepartment()"><span>删除部门</span></a></li>
				<li style="margin-right: 1.8%;float: left"><a class="button" href="javascript:" onclick="gotoEditDepartment()"><span>修改部门</span></a></li>
				
			</ul>
		</div>
	</div>

<div style=" display:block; margin:2%;overflow:auto; width:17%;height:500px; overflow:auto; border:solid 1px #CCC; line-height:2%; background:#FFF;">
<ul class="tree treeFolder expand" >
	<c:forEach items="${areaList }" var="area">
	<li><a style = "font-weight: 900">${area.areaName }</a>
		<ul >
			<c:forEach items="${area.departmentList }" var="department">
			<li ><a href="javascript:" onclick="selectDepartment('${department.departmentId }')" name="name" value="${department.departmentId }" >${department.departmentName }</a></li>
			</c:forEach>
		</ul>
	</li>
	</c:forEach>
</ul>

</div>

<div>
<input type="hidden" name="selectedDepartmentId" id="selectedDepartmentId">


</div>
<script type="text/javascript">

function selectDepartment(id){
	var id = $("#selectedDepartmentId").val(id);
}

function gotoAddDepartment(){
	var selectedDepartmentId=$("#selectedDepartmentId").val();
		$.pdialog.open("${basePath }admin/department/toAdd?departmentId="+selectedDepartmentId,"departmentSelectTemplate","", {width:"740",height:"333",mask:true,fresh:true});
}


function gotoEditDepartment(){
	var selectedDepartmentId=$("#selectedDepartmentId").val();
	//alert(selectedDepartmentId);
	if(selectedDepartmentId!=null && selectedDepartmentId != undefined && selectedDepartmentId != ''){
		navTab.openTab('tabid', "${basePath }admin/department/toEdit?StselectedDepartmentId="+selectedDepartmentId , { title:"编辑部门信息", fresh:true, data:{} });
	}else{
	alert("请选择需要修改的部门 !");	
	}
}

function gotoEditDepartment(){
	var selectedDepartmentId=$("#selectedDepartmentId").val();
	if(selectedDepartmentId!=null && selectedDepartmentId != undefined && selectedDepartmentId != ''){
		$.pdialog.open("${basePath }admin/department/toEdit?selectedDepartmentId="+selectedDepartmentId,"departmentEdit","", {title:"编辑部门信息",width:"740",height:"333",mask:true,fresh:true});
	}else{
		alertMsg.error("请选择需要修改的部门 !");	
	}
}

function gotoDeleteDepartment(){
	var selectedDepartmentId=$("#selectedDepartmentId").val();
	if(selectedDepartmentId!=null && selectedDepartmentId != undefined && selectedDepartmentId != ''){
		ajaxTodo("${basePath }admin/department/toDelete?StselectedDepartmentId="+selectedDepartmentId);
	}else{
		alertMsg.error("请选择需要删除的部门 !");	
	}
}

</script>
</body>
</html>
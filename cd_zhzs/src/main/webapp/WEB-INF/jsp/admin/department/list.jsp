<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body>
	<input type="hidden" name="selectedDepartmentName" id="selectedDepartmentName">
	<input type="hidden" name="selectedDepartmentId" id="selectedDepartmentId">
	<div class="panel" defH="40">
		<h1>操作</h1>
		<div>
			<ul class="leftTools">
				<li style="margin-right: 15px;float: left"><a class="button" href="javascript:" onclick="gotoAddDepartment()"><span>新增部门</span></a></li>
				<li style="margin-right: 15px;float: left"><a class="button" href="javascript:" onclick="gotoDeleteDepartment()"><span>删除部门</span></a></li>
				<li style="margin-right: 15px;float: left"><a class="button" href="javascript:" onclick="gotoEditDepartment()"><span>修改部门</span></a></li>
				<li style="margin-right: 15px;float: left"><a class="button" href="javascript:" onclick="gotoSelectSubDepartment()"><span>从部门配置</span></a></li>
				<li style="margin-right: 15px;float: left"><a class="button" href="javascript:" onclick="gotoSelectTemplate()"><span>配置模板</span></a></li>
			</ul>
		</div>
	</div>
	<div class="divider"></div>
	<div style=" display:block; margin:20px;overflow:auto; width:17%;height:74%; overflow:auto; border:solid 1px #CCC; line-height:21px; background:#FFF;">
		<ul class="tree treeFolder" >
			<c:forEach items="${areaList }" var="area">
			<li><a style = "font-weight: 900" onclick="cleanDepartment()">${area.areaName }</a>
				<ul >
					<c:forEach items="${area.departmentList }" var="department">
					<li ><a href="javascript:" onclick="selectDepartment('${department.departmentId }','${area.areaName }','${department.departmentName }')" name="name" value="${department.departmentId }" >${department.departmentName }</a></li>
					</c:forEach>
				</ul>
			</li>
			</c:forEach>
			
		</ul>
	</div>
<script type="text/javascript">

function selectDepartment(id,areaName,name){
	$("#selectedDepartmentId").val(id);
	$("#selectedDepartmentName").val(areaName+" - "+name);
}
function cleanDepartment(){
	$("#selectedDepartmentId").val('');
	$("#selectedDepartmentName").val('');
}

function gotoDeleteDepartment(){
	var selectedDepartmentId=$("#selectedDepartmentId").val();
	if(selectedDepartmentId!=null && selectedDepartmentId != undefined && selectedDepartmentId != ''){
		ajaxTodo("${basePath }admin/department/toDelete?StselectedDepartmentId="+selectedDepartmentId);
	}else{
		alertMsg.error("请选择需要删除的部门 !");	
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
function gotoSelectSubDepartment(){
	var selectedDepartmentId=$("#selectedDepartmentId").val();
	if(selectedDepartmentId!=null && selectedDepartmentId != undefined && selectedDepartmentId != ''){
		$.pdialog.open("${basePath }admin/department/toSubDepartment?StselectedDepartmentId="+selectedDepartmentId,"delectSubDepartmentTable","从部门配置",{width:"850",height:"730",mask:true,fresh:true,mask:true,mixable:true,minable:true} );
	}else{
		alertMsg.error("请选择需要配置的部门 !");	
	}
}
function gotoSelectTemplate(){
	var selectedDepartmentId=$("#selectedDepartmentId").val();
	if(selectedDepartmentId!=null && selectedDepartmentId != undefined && selectedDepartmentId != ''){
		var selectedDepartmentName=$("#selectedDepartmentName").val();
		$.pdialog.open("${basePath }admin/template/toSelectTemplate?departmentId="+selectedDepartmentId,"departmentSelectTemplate", selectedDepartmentName+" - 配置模板", {width:'700',height:'500',mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true});
	}else{
		alertMsg.error("请选择需要配置的部门 !");	
	}
}

function gotoAddDepartment(){
	var selectedDepartmentId=$("#selectedDepartmentId").val();
		$.pdialog.open("${basePath }admin/department/toAdd?departmentId="+selectedDepartmentId,"departmentSelectTemplate","", {width:"740",height:"333",mask:true,fresh:true});
}

</script>
</body>
</html>
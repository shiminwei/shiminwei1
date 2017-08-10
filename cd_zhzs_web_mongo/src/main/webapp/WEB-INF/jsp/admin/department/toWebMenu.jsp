<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body>
	<h2 class="contentTitle">组织机构菜单管理页面</h2>
	<div id="resultBox">

		<div
			style="float: left; display: block; margin: 20px; overflow: auto; width: 180px; height: 400px; overflow: auto; border: solid 1px #CCC; line-height: 21px; background: #FFF;">

			<p>部门列表</p>
			<form id="myFrom" method="post" action="${basePath }admin/wDepartMneu/saveOrUpdate"
				class="pageForm required-validate"
				onsubmit="return validateCallback(this)">
				<ul class="tree treeFolder  expand" oncheck="kkk">
					<c:forEach items="${areaList }" var="area">
						<li><a style="font-weight: 900">${area.areaName }</a>
							<ul>
								<c:forEach items="${area.departmentList }" var="department">
									<li><a href="javascript:" value="${department.departmentName }"
										onclick="toShowMenu('${department.departmentId }')"
										name="name" value="${department.departmentId }">${department.departmentName }</a></li>
								</c:forEach>
							</ul></li>
					</c:forEach>
				</ul>
			</form>

		</div>

		<div style="float: left; display: block; margin: 20px; overflow: auto; width: 180px; height: 400px; overflow: auto; border: solid 1px #CCC; line-height: 21px; background: #FFF;">

			<p>菜单列表</p>
			<ul class="tree treeFolder treeCheck expand" id="myMenu" >
				<c:forEach items="${oneList }" var="oneList">
					<li><a style="font-weight: 900" tvalue="${oneList.code }">${oneList.name }</a>
						<ul>
							<c:forEach items="${twoList }" var="twoList">
								<c:if test="${oneList.code==twoList.parentCode }">
									<li><a href="#" onclick="" name="name"
										tvalue="${twoList.code }">${twoList.name }</a>
										<ul>
											<c:forEach items="${threeList }" var="threeList">
												<c:if test="${twoList.code==threeList.parentCode }">
													<li><a href="#" onclick="" name="name"
														tvalue="${threeList.code }">${threeList.name }</a></li>
												</c:if>
											</c:forEach>
										</ul></li>
								</c:if>
							</c:forEach>
						</ul></li>
				</c:forEach>
			</ul>
		</div>

<div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="toSave()">提交</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
</div>


	</div>
	<script type="text/javascript">
	var deId;
		function selectMenuList() {
			var resultBox = $("#resultBox");
			alert(resultBox);

		}
		
		
		function toShowMenu(id){
	
			deId=id;
		}
		
		function  toSave(){
			 var oidStr=""; //定义一个字符串用来装值的集合    
			    
			//jquery循环t2下的所有选中的复选框    
			  $("#myMenu input:checked").each(function(i,a){    
			    //alert(a.value);   

   				oidStr +=a.value+',';  //拼接字符串    
			  });   
			 
			 
			 if(deId==""){
				 alert("清选择部门！！！");
			 }else{
				 $('form[id=myFrom]').attr('action','${basePath }admin/wDepartMneu/saveOrUpdate?departmentId='+deId+'&menuId='+oidStr); 
				 $('#myFrom').submit();
 
			 }
			
		}
		
		
		
		function selectDepartment(id) {
			var id = $("#selectedDepartmentId").val(id);
			
		}

		function gotoEditDepartment() {
			var selectedDepartmentId = $("#selectedDepartmentId").val();
			//alert(selectedDepartmentId);
			if (selectedDepartmentId != null
					&& selectedDepartmentId != undefined
					&& selectedDepartmentId != '') {
				navTab.openTab('tabid',
						"${basePath }admin/department/toEdit?StselectedDepartmentId="
								+ selectedDepartmentId, {
							title : "编辑部门信息",
							fresh : true,
							data : {}
						});
			} else {
				alert("请选择需要修改的部门 !");
			}
		}
	</script>
</body>
</html>
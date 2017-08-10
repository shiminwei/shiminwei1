<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<h2 class="contentTitle">结果集管理</h2>

<div class="pageContent" style="width: auto; overflow: x:scroll;">
	<form id="myForm" method="post"
		action="${basePath}admin/config/saveOrUpdate"
		class="pageForm required-validate"
		onsubmit="return iframeCallback(this, navTabAjaxDone)">
		<table class="list" width="100%" layoutH="75">
			<thead>
				<tr>
					<th width="50" align="left">字段名称</th>
					<th width="50" align="left">页面名称</th>
					<th width="40" align="left">合并列展示名称</th>
					<th width="40" align="left">合并列数</th>
					<th width="30" align="left">显示长度</th>
					<th width="30" align="left">显示颜色</th>
					<th width="30" align="left">是否隐藏</th>
					<th width="30" align="left">合计</th>
					<!-- 					<th width="30" align="left">小计</th> -->
					<th width="30" align="left">原页面打开</th>
					<th width="50" align="left">传值到子页面</th>
					<th width="30" align="left">匹配预警字符串</th>
					<th align="left">链接地址以及标题</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${fieldBeans}" varStatus="status">
					<tr>
						<td><input type="text" style="margin-left: 0px; width: 50"
							name="fieldBeans[${status.index }].sqlFielName"
							readonly="readonly" value="${list.sqlFielName}" /></td>

						<td><input type="text" style="margin-left: 0px; width: 40"
							name="fieldBeans[${status.index }].jspFielName"
							value="${list.jspFielName}" /></td>
						<td><input type="text" style="margin-left: 0px; width: 30"
							name="fieldBeans[${status.index }].mergeName"
							value="${list.mergeName}" /></td>
						<td><input type="text" style="margin-left: 0px; width: 30px"
							name="fieldBeans[${status.index }].mergeNum"
							value="${list.mergeNum}" /></td>
						<td><input type="text" style="margin-left: 0px; width: 30px"
							name="fieldBeans[${status.index }].width" value="${list.width}" /></td>
						<td><input type="text" style="margin-left: 0px; width: 30px"
							name="fieldBeans[${status.index }].color" value="${list.color}" />
						</td>
						<td><select name="fieldBeans[${status.index }].hidden"
							style="margin-left: 0px; width: 30" class="combox">
								<option value="0"
									<c:if test="${ list.hidden == 0}"> selected="selected" </c:if>>否</option>
								<option value="1"
									<c:if test="${ list.hidden == 1}"> selected="selected" </c:if>>是</option>
						</select></td>
						<td><select name="fieldBeans[${status.index }].totalaggr"
							style="margin-left: 0px; width: 30" class="combox">
								<option value="0"
									<c:if test="${ list.totalaggr == 0}"> selected="selected" </c:if>>否</option>
								<option value="1"
									<c:if test="${ list.totalaggr == 1}"> selected="selected" </c:if>>是</option>
						</select></td>
<%-- 						<td><select name="fieldBeans[${status.index }].subtotalaggr"
							style="margin-left: 0px; width: 30" class="combox">
								<option value="0"
									<c:if test="${ list.subtotalaggr == 0}"> selected="selected" </c:if>>否</option>
								<option value="1"
									<c:if test="${ list.subtotalaggr == 1}"> selected="selected" </c:if>>是</option>
						</select></td> --%>

						<td><select name="fieldBeans[${status.index }].target"
							style="margin-left: 0px; width: 30" class="combox">
								<option value="0"
									<c:if test="${ list.target == 0}"> selected="selected" </c:if>>否</option>
								<option value="1"
									<c:if test="${ list.target == 1}"> selected="selected" </c:if>>是</option>
						</select></td>
						<td><input type="text"
							name="fieldBeans[${status.index }].isByValue"
							style="margin-left: 0px; width: 10" value="${list.isByValue}" /></td>
						<td><input type="text"
							name="fieldBeans[${status.index }].warningStr"
							style="margin-left: 0px; width: 20" value="${list.warningStr}" /></td>

						<td><a> <input id="district.functionId${status.index }"
								style="margin-left: 0px; width: 90px"
								name="fieldBeans[${status.index }].link" type="text"
								value="${list.link}" /> <input
								id="district.functionName${status.index }" type="text"
								name="fieldBeans[${status.index }].linkTitle"
								style="margin-left: 0px; width: 90px" value="${list.linkTitle}" />

						</a> <a href="config/lookListConfig?index=${status.index }"
							lookupGroup="district" rel="asdasd" style="color: green">查询功能</a>
							<a onclick="toClean('district.functionId'+${status.index })"
							style="color: green">清空功能</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<%-- 隐藏域  --%>
		<input type="hidden" name="id" value="${bean.id}" /> <input
			type="hidden" name="type" value="${type}" /> <input type="hidden"
			name="name" value="${bean.name}" /> <input type="hidden"
			name="datasource" value="${bean.datasource}" /> <input type="hidden"
			name="conditioncolumn" value="${bean.conditioncolumn}" /> <input
			type="hidden" name="resultType" value="${bean.resultType}" /> <input
			type="hidden" name="sql" value="${bean.results.sql}" />


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
	</form>
</div>

<script type="text/javascript">
 function toClean(id){
	document.getElementById(id).value="";
 }

 function toSave(){
	 var flag=true;
	 var jspFielName=document.getElementsByName("jspFielName");
	 for (var i = 0; i < jspFielName.length; i++) {
		 if(jspFielName[i].value==""||jspFielName[i].value==null){
			 flag=false;
			 alert("页面名称不能为空！！");
			 break;
		 }

	}
	 if (flag) {
		 $("#myForm").submit(); 
	}
 }
</script>
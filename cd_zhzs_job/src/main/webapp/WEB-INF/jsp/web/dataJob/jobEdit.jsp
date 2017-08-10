<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<c:choose>
	<c:when test="${operateMethod=='edit' }">
		<h2 class="contentTitle" >修改任务</h2>
	</c:when>
	<c:otherwise>
		<h2 class="contentTitle" >新增任务</h2>
	</c:otherwise>
</c:choose>

<div class="pageContent">
	
	<form method="post" action="${basePath }web/dataJob/addJob" class="pageForm required-validate"
		onsubmit="return validateCallback(this,navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt style="font-weight:900;width:80px;">任务名称：</dt>
				<dd>
					<input type="text" style="padding-left:10px;" class="required" id="jobName" name="name"/><span class="info"></span>
				</dd>
			</dl>
			<dl>
				<dt style="font-weight:900;width:80px;">任务类型：</dt>
				<dd>
					<select name="type" style="width:160px;padding-left:5px;padding-bottom:2px;" onchange="changeJobName(this)">
						<option value="1">立即任务</option>
						<option value="2">周期任务</option>
						<option value="3">定时任务</option>
					</select>
				</dd>
			</dl>
			<dl id="period" style="display:none">
				<dt style="font-weight:900;width:80px;">任务周期：</dt>
				<dd>
					<input type="text"  style="width:154px;" readonly class="required" id="runPeriod" name="runPeriod" value="0"/><span class="info"></span>
				</dd>
			</dl>
			
			<div style="padding-top: 80px;">
				<ul>
					<li style="position: relative;float: left; margin-left: 60px;">
						<button type="submit">保存</button></li>
					<li style="padding-left:136px;"><button type="button" class="close">取消</button></li>
				</ul>
			</div>
		</div>
		
	</form>

</div>
<script type="text/javascript">
	// 切换任务类型
	function changeJobName(val){
		if(val.value == '2' || val.value == '3'){
			$("#runPeriod").val("");
			$("#period").css("display","");
			$("#period").css("display","block");
		//	window.open("${basePath }web/dataJob/toAjax",null,"height=200,width=400,status=yes,toolbar=no,menubar=no,location=no");
		
			var options = {mask:true, 
			                    width:850, height:600, 
			                    maxable: eval("true"), 
			                    resizable:eval("true") 
			                }; 
			//$.pdialog.close("jobPeriodConfig"); 
			$.pdialog.open("${basePath }web/dataJob/toAjax","jobPeriodConfig","配置cron表达式", options);
		}else{
			$("#runPeriod").val("0");
			$("#period").css("display","");
			$("#period").css("display","none");
		}
	}
	// 保存任务
	/* function toSave() {
		var jobName = $("#jobName").val();
		if(jobName == null || jobName.length == 0){
			alert("任务名称不能为空");
			$("#jobName").focus();
		}else{
			if (confirm('确定要保存任务吗？')) {
				document.getElementById("button").type = "submit";
			} else {
				return;
			}
		}
	} */

</script>
</body>
</html>
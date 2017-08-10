<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<div class="pageContent">
	<form method="post" action="${basePath }admin/departmentLead/saveOrEdit?do=${actionMethod}"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="60">
		<c:choose>
			<c:when test="${actionMethod=='add' }">
				<dl>
					<dt style="font-weight: 900">前&nbsp;置&nbsp;机&nbsp;ID&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</dt>
					<dd style="width: 150px"><input style="text-align:center;width: 154px" type="text" name="processorId" id="leadId" class="required"
				   	readOnly='readOnly'
				    value="${sysDepartmentLead.leadId }"/>
				    <strong><span id="check" style="font-size:10px;line-height: 20px;color: red"></span></strong></dd>
				</dl>
			</c:when>
			<c:otherwise>
				<dl>
					<dt style="font-weight: 900">前&nbsp;置&nbsp;机&nbsp;ID&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</dt>
					<dd style="width: 150px"><input style="text-align:center;width: 154px" type="text" name="processorId" id="leadId" class="required leadId"
				   	onload="getLeadId()" readOnly='readOnly'/>
				    <strong><span id="check" style="font-size:10px;line-height: 20px;color: red"></span></strong></dd>
				</dl>
			</c:otherwise>
		</c:choose>	
		    <dl>
				<dt style="font-weight: 900">所&nbsp;属&nbsp;部&nbsp;门&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</dt>
				<dd style="width: 150px"> 
				<select style="width: 154px" class="combox" name="departmentId" class="required">
				<c:forEach items="${sysDepartmentList }" var="sysDepartment">
					<option <c:if test="${sysDepartment.departmentId == sysDepartmentLead.departmentId}">selected="selected"</c:if> value="${sysDepartment.departmentId}">
					${sysDepartment.departmentName}
					</option>
				</c:forEach>
				</select>	
				</dd>
			</dl>
			<dl>
				<dt style="font-weight: 900">状&nbsp;态&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</dt>
				<dd style="width: 150px">
				<select style="width: 154px" class="combox" name="state">
					<option <c:if test="${ sysDepartmentLead.status == '1'}"> selected="selected"</c:if> value="可用">
					可用</option>
					<option <c:if test="${ sysDepartmentLead.status == '0'}"> selected="selected"</c:if> value="不可用">
					不可用</option>
				</select>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >提交</button></div></div> </li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" class="close">取消</button></div> </div> </li>
			</ul>			
		</div>
	</form>
</div>
<script type="text/javascript">

function validateCallback(form, callback) {  
    var $form = $(form);  
        //验证表单如果有非法字段 返回  
    if (!$form.valid()) {  
        return false;  
    }  
        //Ajax向后台提交数据  
    $.ajax({  
        type: form.method || 'POST',  
        url:$form.attr("action"),  
        //获取表单的内容数据  
                data:$form.serializeArray(),  
        dataType:"json",  
        cache: false,  
        //执行回调函数  
                success: callback || DWZ.ajaxDone,  
        error: DWZ.ajaxError  
    });  
        //保证不会通过普通表单提交  
        return false;  
}  

function customvalidXxx(element){
	if ($(element).val() == "xxx") return false;
	return true;
}

window.onload=getLeadId();
function getLeadId(){
	$.ajax({
		type:"POST",
		url:"departmentLead/getLeadId",
		data:"",
		dataType:"text",
		success:function(result){
			 $(".leadId").val(result);
		}
	});
	
}
/* window.onload=readOnly();
function readOnly(){
	var leadId = $("#leadId").val();
	if(leadId!=null && leadId!=""){
		document.getElementById('leadId').setAttribute('readonly','readonly');
	}
} */
/* function checkLeadId(){
	var leadId = $("#leadId").val();	
// 	alert(leadId);
	$.ajax({
		type:"POST",
		url:"departmentLead/checkLeadId",
		data:"leadId="+leadId,
		dateType:"text",
		success:function(result){
			if(result=="false"){
				//alert("存在");
				$("#leadId").val("ID已存在,请重新输入");
				document.getElementById("checkLeadId").innerHTML == "cunz";
				$("#leadId").val("");
			}else{
				//alert("不存在");
				$("#checkLeadId").val("可以使用");
			}
		}
	});
}
 */
</script>
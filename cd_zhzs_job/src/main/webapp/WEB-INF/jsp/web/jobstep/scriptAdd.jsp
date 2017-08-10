<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<h2 class="contentTitle">SQL脚本</h2>
<div class="pageContent">
	<form id="myForm" method="post" action="${basePath}web/sqlScripts/saveOrUpdate"
		  class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="120" id ="divId">
			<input type="hidden" name="type" value="${type}" /> 
			<input type="hidden" name="jobId" value="${jobId }" />
			<input type="hidden" name="aOUpdate" value="0" />
			<dl>
				<dt>名称：</dt>
				<dd>
					<input type="text" name="name" maxlength="20" value="" class="required" style="width: 200px" />
				</dd>
			</dl>
			<dl>
				<dt>选择数据源：</dt>
				<dd>
					<select name="datasourceId" id="datasource" class="combox" onchange="toCheckSql('1')">
						<c:forEach items="${datasourceList}" var="list" varStatus="status">
							<option value="${list.id }">${list.name }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>SQL语句：</dt>
				<dd>
					<textarea rows="10" cols="80" id="sql" name="sql" class="required" onblur="toCheckSql('2')"></textarea>
				</dd>
				<span style="color: red" id="errorSpan"></span>
			</dl>
		</div>
		<div class="formBar">
			<ul style ="width:300px;height:120px;float:left">
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="toSave()">提交</button>
						</div>
					</div>
				</li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	var flag = false;
	var param_;
	function  toCheckSql(changesql) {
		param_ = {};
		var sql = $("#sql").val().replace(/[\r\n]/g, " ");
		var datasourceId = $('#datasource option:selected').val();
		if (sql != null && sql != "" && datasourceId != null && datasourceId != "")
		{
			var stemp = $.trim(sql.replaceAll("#",""));
			if(stemp.length == 0){
				flag = false;
				toShowStr("请输入正确的SQL语句");
				return ;
			}
			var d_sql = sql.split(";");
			if(d_sql.length <0 || stemp.replaceAll(";","") == ''){
				flag = false;
				toShowStr("请输入正确的SQL语句");
				return ;
			}
			var count = 0 ;
			for(var i = 0; i < d_sql.length; i++){
				var call = false ;
				if(d_sql[i] == ''){
					continue ;
				}
				if(d_sql[i].indexOf("##") > -1){
					var call_flag = true ;
					if(d_sql[i].toLowerCase().indexOf("call") < 0){
						call_flag = false;
					}
					if(d_sql[i].indexOf("(") < 0 || d_sql[i].indexOf(")") < 0){
						call_flag = false;
					}
					var temp = d_sql[i].replaceAll("#","");
					temp = temp.replaceAll(" ","");
					if(temp.length < 7){
						call_flag = false;
					}
					if(call_flag == false){
						flag = false ;
						$("#errorSpan").text("存储过程以##开始,检查有误。请输入正确的存储过程语句");
						return ;
					}
					call = true ;
				}
				var a_sqls = d_sql[i].split("@");
				for (var j = 0; j < a_sqls.length; j++)
				{
					if(j%2 == 0)continue;
					if($.trim(a_sqls[j]) == '' || a_sqls[j] == null){
						$("#errorSpan").text("@@之间的参数名不可为空");
						flag = false;
						return ;
					}
					if(call){
						var x_sql = a_sqls[j].split("_");
						if(x_sql.length != 3){
							$("#errorSpan").text("存储过程参数名拼接有误,正确格式如@in_varchar_abc@");
							flag = false;
							return ;
						}
					}
					param_[a_sqls[j].split("#")[0]] = "@"+a_sqls[j]+"@";
					count++;
				}			
			}
			for(var key in param_ ){
				count--;
			}
			if(count != 0){
				$("#errorSpan").text("SQL语句中有重名的参数名");
				flag = false;
				return ;
			}
			$.ajax({
		          type: "post",
		          async:false,
		          url: "<%=basePath%>/web/sqlScripts/toCheckSql",
				  data : {"sql":sql,"datasourceId":datasourceId},
				  datatype : "json",
				  success : function(result){
					if(result == "true"){
						flag = true;
						toShowStr("");
						if(changesql == '2'){
							addText();
						}
					}else{
						flag = false;
						toShowStr(result !=''? result:"当前输入的SQL执行存在问题，请检查SQL语句和数据源！");
					}
				}
			}); 
		}
	}
	
	var $more=$("#divId");
    function addText(){
    	$more.children(".add_").remove(); 
        for(var key in param_ ){
        	var fieldValue = "";
			fieldValue += "<dl class='add_'><dt>@"+key+"@：</dt>" ;
			fieldValue += "<dd><input type='text' name='"+param_[key]+"' style='width: 200px' ></dd></dl>";
			$more.append(fieldValue);
		}
    }
    
	function toShowStr(str) {
		$("#errorSpan").text(str);
	}

	function toSave() {
		if (flag) {
			$("#errorSpan").text("");
			$("#myForm").submit();
		}
	}
</script>
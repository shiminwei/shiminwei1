<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<html>
<body>
<div class="pageContent">
<form class="pageForm required-validate" method="post" action="${basePath }/web/companyinfo/addOrEdit" onsubmit="return validateCallback(this,dialogAjaxDone)">
<div class="pageFormContent nowrap" layouth="97" style="height: 500px;overflow: auto;">
	<input type="hidden" id="beanid" name="id" value="${bean.id }">
	<input type="hidden" name="isDelete" value="${bean.isDelete }">
	<input type="hidden" name="gszjKId" value="${bean.gszjKId }">
	<input type="hidden" name="dsjKId" value="${bean.dsjKId }">
	<input type="hidden" name="gsjKId" value="${bean.gsjKId }">
		<dl>
					<dt style="font-weight: 900; width: 130px"></dt>
					<dd align="center" style="width: 100%;">
						<span align="center" style="font-size: 20px; font-weight: bold;">${bean.name }</span>
						<%-- <input type="text" value="${bean.name }" style="width: 50%;" />  --%>
						<%-- 			
					
						<textarea id="name" name="name" rows="2" cols="60" tools="mini"
							class="required" onblur="checkSameName();" disabled="true">${bean.name }</textarea>
						<strong><span id="checkName"
							style="font-size: 10px; line-height: 20px; color: red"></span></strong> --%>
					</dd>
				</dl>


				<dl style="margin-top: 30px;">
					<dt style="font-weight: 900; width: 130px">法人代表：</dt>
					<dd style="width: 200px">
						<input name="legalPerson" type="text" value="${bean.legalPerson }"
							/>
					</dd>
					<dt style="font-weight: 900; width: 130px">所属区域：</dt>
					<dd style="width: 200px">
						<select style="width: 108px" name="areaId" disabled="true">
							<c:forEach items="${areaList }" var="area">
								<option value="${area.areaId }@${area.areaName }"
									<c:if test="${bean.areaId == area.areaId}">selected="selected"</c:if>>${area.areaName }</option>
							</c:forEach>
						</select>
					</dd>
				</dl>
				<dl>


					<dl>
						<dt style="font-weight: 900; width: 130px">企业状态：</dt>
						<dd style="width: 200px">
							<input name="status" type="text" value="${bean.status }"
								/>
						</dd>
						<dt style="font-weight: 900; width: 130px">是否招商企业：</dt>
						<dd style="width: 200px">
							<select name="isCanvassBuisiness" style="width: 108px"
								disabled="true">
								<option value="1"
									<c:if test="${bean.isCanvassBuisiness==1 }">selected="selected"</c:if>>是</option>
								<option value="0"
									<c:if test="${bean.isCanvassBuisiness==0 }">selected="selected"</c:if>>否</option>
							</select>
						</dd>
					</dl>
					<dl>
					<dt style="font-weight: 900; width: 130px">企业属性：</dt>
						<dd style="width: 200px">
							<input name="companyAttr" type="text"
								value="${bean.companyAttr }" />
						</dd>
			
						<%-- 			<dt style="font-weight: 900; width: 130px">行业类型：</dt>
					<dd style="width: 200px">
						<input name="industryType" type="text"
							value="${bean.industryType }" />
					</dd> --%>
					
						<dt style="font-weight: 900; width: 130px">是否规模以上企业：</dt>
						<dd style="width: 200px">
							<select name="isAboveScale" style="width: 108px" disabled="true">
								<option value="1"
									<c:if test="${bean.isAboveScale==1 }">selected="selected"</c:if>>是</option>
								<option value="0"
									<c:if test="${bean.isAboveScale==0 }">selected="selected"</c:if>>否</option>
							</select>
						</dd>
					</dl>
					<dl>
						<%-- 		<dt style="font-weight: 900;width: 130px">国标行业：</dt>
		<dd style="width: 200px">
			<input type="text" name="idGbhy" value="${bean.idGbhy }" readonly="readonly"/>
		</dd> --%>
					<dt style="font-weight: 900; width: 130px">企业类型：</dt>
						<dd style="width: 200px">
							<input name="companyType" type="text"
								value="${bean.companyType }" />
						</dd>
						
						<dt style="font-weight: 900; width: 130px">从业人员：</dt>
						<dd style="width: 200px">
							<input name="cyrs" readonly="readonly" type="text" value="${bean.cyrs.split('[.]')[0] }"
								/>
						</dd>

					</dl>
					<dl>
				<dt style="font-weight: 900; width: 130px">国标行业名称：</dt>
						<dd style="width: 200px">
							<input type="text" name="nameGbhy" value="${bean.nameGbhy }"
								/>
						</dd>
						<dt style="font-weight: 900; width: 130px;color: red;">销售收入：</dt>
						<dd style="width: 200px">
							<input name="salesRevenue" type="text" readonly="readonly"
								value="${bean.salesRevenue }" />
						</dd>
					</dl>
					<dl>
						<dt style="font-weight: 900; width: 130px">登记日期：</dt>
						<dd style="width: 200px">
							<input name="registerDate" type="text"
								value="${bean.registerDate }" />
						</dd>
						<dt style="font-weight: 900; width: 130px">2015纳税税额：</dt>
						<dd style="width: 200px">
							<input name="previousYearTax" type="text" readonly="readonly" value="${bean.previousYearTax }"
								/>
						</dd>
					</dl>

				</dl>
				<dl>
					<dt style="font-weight: 900; width: 130px">注册资本：</dt>
					<dd style="width: 200px">
						<input name="registerCapital" type="text"
							value="${bean.registerCapital }" />
					</dd>
					<dt style="font-weight: 900; width: 130px">2016纳税税额：</dt>
					<dd style="width: 200px">
						<input name="lastYearTax" type="text" readonly="readonly" value="${bean.lastYearTax }"
							/>
					</dd>
				</dl>

				<dl>
					<dt style="font-weight: 900; width: 130px">联络员：</dt>
					<dd style="width: 200px">
						<input name="linkman" type="text" value="${bean.linkman }"
							/>
					</dd>
					<dt style="font-weight: 900; width: 130px">2017纳税税额：</dt>
					<dd style="width: 200px">
						<input name="thisYearTax" type="text" readonly="readonly" value="${bean.thisYearTax }"
							/>
					</dd>
					<%-- 		<dt style="font-weight: 900;width: 130px">政治面貌：</dt>
		<dd style="width: 200px"><input name="politicalOutlook" type="text" value="${bean.politicalOutlook }" readonly="readonly"/></dd>	 --%>
				</dl>


				<dl>
					<dt style="font-weight: 900; width: 130px">联络员电话：</dt>
					<dd style="width: 200px">
						<input name="linkmanMobile" type="text"
							value="${bean.linkmanMobile }" />
					</dd>
		<dt style="font-weight: 900; width: 130px;color:red;">三年纳税合计：</dt>
					<dd style="width: 200px">
						<input name="taxAmount" type="text" readonly="readonly" value="${bean.taxAmount }"
							/>
					</dd>

				</dl>


				<%-- 	<dl>
		<dt style="font-weight: 900;width: 130px">注册资本：</dt>
		<dd style="width: 200px"><input name="registerCapital" type="text" value="${bean.registerCapital }" readonly="readonly"/></dd>
		<dt style="font-weight: 900;width: 130px">币种：</dt>
		<dd style="width: 200px"><input name="currency" type="text" value="${bean.currency }" readonly="readonly"/></dd>	
	</dl> --%>
				<dl>
					<dt style="font-weight: 900; width: 130px">经营类别：</dt>
					<dd style="width: 200px">
						<input name="managementCategory" type="text"
							value="${bean.managementCategory }" />
					</dd>

					<dl>
						<dt style="font-weight: 900; width: 130px">经营范围：</dt>
						<dd style="width: 200px">
							<textarea name="dealArea" rows="5" cols="75" tools="mini"
								disabled="true">${bean.dealArea }</textarea>
						</dd>
						</dd>

					</dl>
					<dl>
						<dt style="font-weight: 900; width: 130px">管辖单位：</dt>
						<dd style="width: 200px">
							<textarea name="jurisdictionUnit" rows="2" cols="75" tools="mini"
								disabled="true">${bean.jurisdictionUnit }</textarea>
						</dd>
					</dl>
					<dl>
						<dt style="font-weight: 900; width: 130px">主管管辖单位：</dt>
						<dd style="width: 200px">
							<textarea name="mainJurisdictionUnit" rows="2" cols="75"
								tools="mini" disabled="true">${bean.mainJurisdictionUnit }</textarea>
						</dd>
					</dl>
					<dl>
						<dt style="font-weight: 900; width: 130px">(受委托)登记机关：</dt>
						<dd style="width: 200px">
							<textarea name="registrationUint" rows="2" cols="75" tools="mini"
								disabled="true">${bean.registrationUint }</textarea>
						</dd>
					</dl>
					<dl>
						<dt style="font-weight: 900; width: 130px">主管登记机关：</dt>
						<dd style="width: 200px">
							<textarea name="mainRegistrationUint" rows="2" cols="75"
								tools="mini" disabled="true">${bean.mainRegistrationUint }</textarea>
						</dd>
					</dl>
					<dl>
						<dt style="font-weight: 900; width: 130px">核准日期：</dt>
						<dd style="width: 200px">
							<input name="approvalDate" type="text"
								value="${bean.approvalDate }" />
						</dd>
						<dt style="font-weight: 900; width: 130px">联系电话：</dt>
						<dd style="width: 200px">
							<input name="contactMobile" type="text"
								value="${bean.contactMobile }" />
						</dd>
					</dl>
</div>
	<div style="margin-top: 20px" class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
			<li style="margin-left: 10px;float: left">
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
</form>
</div>
</body>
<script type="text/javascript">
	function checkSameName(){
		var id = document.getElementById("beanid").value.trim();
		var name = document.getElementById("name").value.trim();
		if(name == '' || name == null){
			document.getElementById("checkName").innerHTML = "";
			return ;
		}
		$.ajax({
		    type: "POST",
		    url: "web/companyinfo/checkSameName",
		    data: {'id':id,'name':name},
		    dataType: "text",
		    success: function(result){
		    	if (result=="true") {
		    		document.getElementById("checkName").innerHTML = "&nbsp;&nbsp;&nbsp企业名称已存在,请重新输入";
		    		document.getElementById("name").value ="";
				}
		    	else{
					document.getElementById("checkName").innerHTML = "&nbsp;&nbsp;&nbsp可以使用";
				}	    	
		    }
		});
    }
</script>
</html>
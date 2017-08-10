<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<html>
<body>


	<div class="pageContent">
		<form class="pageForm required-validate" method="post" action=""
			class="pageForm required-validate">
			<div class="pageFormContent nowrap" layouth="97"
				style="height: 500px; overflow: auto;">
				<input type="hidden" name="id" value="${bean.id }">

		<h1 align="center"  style="font-size: 25px; font-weight: bold;">${bean.name } </h1>
	<%-- 			 <dl  align="center">
					<dt style="font-weight: 900"></dt>
					<dd style="width: 100%; margin-left: 10%">
						<span style="font-size: 20px; font-weight: bold;">${bean.name }</span>
						<input type="text" value="${bean.name }" style="width: 50%;" /> 
									
					
						<textarea id="name" name="name" rows="2" cols="60" tools="mini"
							class="required" onblur="checkSameName();" disabled="true">${bean.name }</textarea>
						<strong><span id="checkName"
							style="font-size: 10px; line-height: 20px; color: red"></span></strong>
					</dd>
				</dl> --%>
<dl style="margin-top: 30px;">
					<dt style="font-weight: 900; width: 130px">法人代表：</dt>
					<dd style="width: 200px">
						<input name="legalPerson" type="text" value="${bean.legalPerson }"
							readonly="readonly" />
					</dd>
					<dt style="font-weight: 900; width: 130px">所属区域：</dt>
					<dd style="width: 200px">
						<select style="width: 108px" name="areaId" disabled="true">
							<c:forEach items="${areaList }" var="area">
								<option value="${area.value }"
									<c:if test="${bean.areaName == area.value}">selected="selected"</c:if>>${area.value }</option>
							</c:forEach>
						</select>
					</dd>
				</dl>
				<dl>


					<dl>
						<dt style="font-weight: 900; width: 130px">企业状态：</dt>
						<dd style="width: 200px">
							<input name="status" type="text" value="${bean.status }"
								readonly="readonly" />
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
								value="${bean.companyAttr }" readonly="readonly" />
						</dd>

						<%-- 			<dt style="font-weight: 900; width: 130px">行业类型：</dt>
					<dd style="width: 200px">
						<input name="industryType" type="text"
							value="${bean.industryType }" readonly="readonly" />
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
								value="${bean.companyType }" readonly="readonly" />
						</dd>

						<dt style="font-weight: 900; width: 130px">从业人员（人）：</dt>
						<dd style="width: 200px;margin-left: 20px">
							<input name="cyrs" type="text"
								value="${bean.cyrs}" readonly="readonly" />
						</dd>

					</dl>
					<dl>
						<dt style="font-weight: 900; width: 130px">国标行业名称：</dt>
						<dd style="width: 200px">
							<input type="text" name="nameGbhy" value="${bean.nameGbhy }"
								readonly="readonly" />
						</dd>
						<dt style="font-weight: 900; width: 130px; color: red;">销售收入(万元)：</dt>
						<dd style="width: 200px;margin-left: 20px">
							<input name="salesRevenue" type="text"
								value="${bean.salesRevenue }" readonly="readonly" /> 
						<%-- 		<a
								class="btnLook" width="800"
								height="400"  href="pageList/public?functionid=201704121202067346241158&=${bean.name }&startTime=2015&endTime=2017&targetType=dialog"
								target="dialog" rel="xssr_detail">企业销售收入明细</a> --%>
						</dd>
					</dl>
					<dl>
						<dt style="font-weight: 900; width: 130px">登记日期：</dt>
						<dd style="width: 200px">
							<input name="registerDate" type="text"
								value="${bean.registerDate }" readonly="readonly" />
						</dd>
						<dt style="font-weight: 900; width: 130px">2015纳税税额(万元)：</dt>
						<dd style="width: 200px;margin-left: 20px">
							<input name="previousYearTax" type="text"
								value="${bean.previousYearTax }" readonly="readonly" />
				<%-- 				<a
								class="btnLook" width="800"
								height="400"  href="pageList/public?functionid=201704251450451052734758&企业名称=${bean.name }&所属时间=2015&targetType=dialog"
								target="dialog" rel="nsse_detail">企业纳税税额明细</a> --%>
						</dd>
					</dl>
				</dl>
				<dl>
					<dt style="font-weight: 900; width: 130px">注册资本(万元)：</dt>
					<dd style="width: 200px">
						<input name="registerCapital" type="text"
							value="${bean.registerCapital }" readonly="readonly" />
					</dd>
					<dt style="font-weight: 900; width: 130px">2016纳税税额(万元)：</dt>
					<dd style="width: 200px;margin-left: 20px">
						<input name="lastYearTax" type="text" value="${bean.lastYearTax }"
							readonly="readonly" />
<%-- 								<a
								class="btnLook" width="800"
								height="400"  href="pageList/public?functionid=201704251450451052734758&企业名称=${bean.name }&所属时间=2016&targetType=dialog"
								target="dialog" rel="nsse_detail">企业纳税税额明细</a> --%>
					</dd>
				</dl>

				<dl>
					<dt style="font-weight: 900; width: 130px">联络员：</dt>
					<dd style="width: 200px">
						<input name="linkman" type="text" value="${bean.linkman }"
							readonly="readonly" />
					</dd>
					<dt style="font-weight: 900; width: 130px">2017纳税税额(万元)：</dt>
					<dd style="width: 200px;margin-left: 20px">
						<input name="thisYearTax" type="text" value="${bean.thisYearTax }"
							readonly="readonly" />
<%-- 								<a
								class="btnLook" width="800"
								height="400"  href="pageList/public?functionid=201704251450451052734758&企业名称=${bean.name }&所属时间=2017&targetType=dialog"
								target="dialog" rel="nsse_detail">企业纳税税额明细</a> --%>
					</dd>
					
					<%-- 		<dt style="font-weight: 900;width: 130px">政治面貌：</dt>
		<dd style="width: 200px"><input name="politicalOutlook" type="text" value="${bean.politicalOutlook }" readonly="readonly"/></dd>	 --%>
				</dl>


				<dl>
					<dt style="font-weight: 900; width: 130px">联络员电话：</dt>
					<dd style="width: 200px">
						<input name="linkmanMobile" type="text"
							value="${bean.linkmanMobile }" readonly="readonly" />
					</dd>
					<dt style="font-weight: 900; width: 130px; color: red;">三年纳税合计(万元)：</dt>
					<dd style="width: 200px;margin-left: 20px">
						<input name="taxAmount" type="text" value="${bean.taxAmount }"
							readonly="readonly" />
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
							value="${bean.managementCategory }" readonly="readonly" />
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
								value="${bean.approvalDate }" readonly="readonly" />
						</dd>
						<dt style="font-weight: 900; width: 130px">联系电话：</dt>
						<dd style="width: 200px">
							<input name="contactMobile" type="text"
								value="${bean.contactMobile }" readonly="readonly" />
						</dd>
					</dl>
			</div>
			<div style="margin-top: 42px" class="formBar">
				<ul>
					<li style="margin-left: 10px; float: left">
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">关闭</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>

<script type="text/javascript">
	function toShowSalesRevenue() {

	}
</script>
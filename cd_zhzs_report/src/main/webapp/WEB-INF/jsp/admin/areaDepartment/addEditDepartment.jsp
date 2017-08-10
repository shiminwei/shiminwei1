<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<div class="pageContent">
		<form method="post" action="${basePath }admin/department/editDepartment" 	class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
			<input type="hidden" name="departmentId" value="${sysDepartmentInfo.departmentId}">
			<div class="pageFormContent nowrap" layoutH="58">
				<c:if test="${type!='add'}">
					<dl>
					<dt style="font-weight: 900">部门编码：</dt>
					<dd>
						<dd>
							<strong><span id="checkareaCode"
								style="font-size: 10px; line-height: 20px; color: red">${sysDepartmentInfo.departmentCode }</span></strong>
						</dd>
					</dd>
				</dl>
				</c:if>
				<dl>
					<dt style="font-weight: 900">部门名称：</dt>
					<dd>
						<input type="text" id="departmentName" value="${sysDepartmentInfo.departmentName }" name="departmentName"  class="required textInput " style="cursor: auto; "/>
					</dd>
				</dl>
				<dl>
					<dt style="font-weight: 900">所属地区：</dt>
					<dd>
						<select class="combox" name="departmentAreaId" class="required " >
							<c:forEach items="${areaList }" var="area">
								<option value="${area.areaId }"
									<c:if test="${sysDepartmentInfo.departmentAreaId == area.areaId or areaId==  area.areaId }">selected="selected"</c:if>>${area.areaName }</option>
							</c:forEach>
						</select>
					</dd>
				</dl>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button  type="submit">保存</button>
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
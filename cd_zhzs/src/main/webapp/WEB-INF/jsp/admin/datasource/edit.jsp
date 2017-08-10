<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<h2 class="contentTitle">数据源编辑</h2>


<div class="pageContent">
	
	<form method="post" action="	${basePath}admin/datasourceConfig/${operateMethod}" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)"  modelAttribute="datasourceBean">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>数据源ID：</dt>
				<dd>
					<input type="text" name="id" maxlength="20" class="required" value="${datasourceBean.id}"/>
				</dd>
			</dl>
			<dl>
				<dt>名称：</dt>
				<dd>
					<input type="text" name="name" maxlength="20" class="required" value="${datasourceBean.name}"/>
				</dd>
			</dl>
			<dl>
				<dt>类型：</dt>
				<dd>
					<select name="type">
						<option value="oracle" <c:if test="${datasourceBean.type eq 'oracle'}"> selected="selected"</c:if>>oracle</option>
						<option value="mysql" <c:if test="${datasourceBean.type eq 'mysql'}"> selected="selected"</c:if>>mysql</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>别名：</dt>
				<dd>
					<input type="text" name="aliasName" value="${datasourceBean.aliasName}"/>
				</dd>
			</dl>
			<dl>
				<dt>服务名：</dt>
				<dd>
					<input type="text" name="server" value="${datasourceBean.server}"/>
				</dd>
			</dl>
			<dl>
				<dt>数据库名称：</dt>
				<dd>
					<input type="text" name="database" value="${datasourceBean.database}"/>
				</dd>
			</dl>
			<dl>
				<dt>数据库地址：</dt>
				<dd>
					<input type="text" name="host"  class="required" value="${datasourceBean.host}"/>
				</dd>
			</dl>
			<dl>
				<dt>端口号：</dt>
				<dd>
					<input type="text" name="port"  class="required" value="${datasourceBean.port}"/>
				</dd>
			</dl>
			<dl>
				<dt>字符集编码：</dt>
				<dd>
					<input type="text" name="charset"  value="${datasourceBean.charset}"/>
				</dd>
			</dl>
			<dl>
				<dt>用户名：</dt>
				<dd>
					<input type="text" name="user"  class="required" value="${datasourceBean.user}"/>
				</dd>
			</dl>
			<dl>
				<dt>密码：</dt>
				<dd>
					<input type="text" name="pwd" class="required"  value="${datasourceBean.pwd}"/>
				</dd>
			</dl>
			<dl>
				<dt>SID：</dt>
				<dd>
					<input type="text" name="sid" class="required"  value="${datasourceBean.sid}"/>
				</dd>
			</dl>
			<dl>
				<dt>描述：</dt>
				<dd>
					<textarea name="desc" rows=2 cols=20>${datasourceBean.desc}</textarea>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>


<script type="text/javascript">
function customvalidXxx(element){
	if ($(element).val() == "xxx") return false;
	return true;
}
</script>


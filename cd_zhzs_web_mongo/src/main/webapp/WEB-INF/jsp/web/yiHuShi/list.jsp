<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ include file="../../../../common/base.jsp"%>


<link rel="stylesheet" type="text/css"
	href="${basePath }static/css/demo.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath }static/css/style2.css" />

<div id="container_buttons">
	<input type="text" style="width: 500px; height: 35px; font-size: 20px;"
		id="qymc" />
	<p style="float: right;">
		<a title="��ҵ�б�" href="#" onclick="toShow()" class="a_demo_two"> ��ѯ
		</a>
	</p>
</div>

<script type="text/javascript">
	function toShow() {
		var qymc = document.getElementById("qymc").value;
		if (qymc.trim().length <= 0) {
			alert("��������ҵ���ƣ���");
		} else {
			var url = "${basePath }web/yiHuShi/qyList";
			url += "?qymc=" + qymc;
			navTab.openTab('qyList', url, {
				title : "��ҵ�б�",
				fresh : true,
				data : {}
			});
		}

	}
</script>
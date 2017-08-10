<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>



<!--   <script type="text/javascript">
		var iframe = document.getElementById("iframeDiv");
		iframe.style.width = window.innerWidth -( window.innerWidth*0.113)+ 'px';
		iframe.style.height = window.innerHeight-( window.innerHeight*0.25) + 'px';

</script>  -->
<body>

<div id="iframeDiv" style="width: 100%;height: 750px">
 	<iframe id="iframe" src="${queryUrl}" width="100%" height="100%"></iframe>
	</div> 
</body> 
</html>
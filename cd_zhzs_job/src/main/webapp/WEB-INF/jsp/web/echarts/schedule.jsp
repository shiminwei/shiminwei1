<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<html>
<head>
<link rel='stylesheet' href='${basePath }static/css/theme.css' />
<link href='${basePath }static/js/fullcalendar/fullcalendar.css' rel='stylesheet' />
<link href='${basePath }static/js/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='${basePath }static/js/jquery-ui-1.10.2.custom.min.js'></script>
<script src='${basePath }static/js/fullcalendar/fullcalendar.min.js'></script>
<script type="text/javascript">

	$(document).ready(function() {
		var date = new Date();
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();
		$('#calendar').fullCalendar({
			theme: true,
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
			editable: true,
			events:
				[
				 <c:forEach items="${list}" var="list" varStatus="status">
				 {
					 	title: '步骤名称：${list.name};运行情况：${list.content},${list.result}',
					 	/* name:'${list.result}', */
						start: new Date('${list.year}', '${list.month-1}', '${list.day}','${list.hour}','${list.minu}'),
						allDay: false
					},
				</c:forEach> 
				/*  {
					title: 'All Day Event',
					start: new Date(y, m, 1)
				},
				{
					title: 'Long Event',
					start: new Date(y, m, d-5),
					end: new Date(y, m, d-2)
				},
				{
					id: 999,
					title: 'Repeating Event',
					start: new Date(y, m, d-3, 16, 0),
					allDay: false
				},
				{
					title: '新增时间',
					start: new Date(y, m, 12, 16, 0),
					allDay: false
				},
				{
					id: 999,
					title: 'Repeating Event',
					start: new Date(y, m, d+4, 16, 0),
					allDay: false
				},
				{
					title: 'Meeting',
					start: new Date(y, m, d, 10, 30),
					allDay: false
				},
				{
					title: '二号调度:步骤名称为二号执行成功',
					start: new Date(y, m, d, 09, 15),
					allDay: false
				},
				{
					title: 'Lunch',
					start: new Date(y, m, d, 12, 0),
					end: new Date(y, m, d, 14, 0),
					allDay: false
				},
				{
					title: 'Birthday Party',
					start: new Date(y, m, d+1, 19, 0),
					end: new Date(y, m, d+1, 22, 30),
					allDay: false
				},
				{
					title: 'Click for Google',
					start: new Date(y, m, 28),
					end: new Date(y, m, 29),
					url: 'http://google.com/'
				} */
			]
		});
		
	});

</script>
<style>

	body {
		padding-top:0px;
		text-align: center;
		font-size: 13px;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		overflow-x: hidden;
    	overflow-y: auto; 
		}

	#calendar {
		width: 900px;
		margin: 0 auto;
		}

</style>
</head>
<body>
<div style="margin-top:60px;margin-left:80px">
<div id='calendar' style="display:block"></div>
</div>
</body>
</html>
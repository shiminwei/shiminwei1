<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>

<link rel="stylesheet" href="${basePath }static/css/myProgress.css">
<script src="${basePath }static/js/echarts.min.js"></script>

<script src="${basePath }static/js/jquery.myProgress.js"></script>

<%-- 
<div style="width: 100%; margin-left: 1%">

	<div id="main1" style="width: 49%; float: left;">

		<div style="margin-top: 10%; margin-bottom: 9%; margin-left: 2%">
			<span style="font-weight: 900; font-size: 17px">全市总收入<a style="margin-left: 2%"></a> :<a style="margin-left: 2%"></a> ${bennianSj }
				万元</span>
		</div>

		<div style="margin-bottom: -23%; margin-left: 12px">
			<span style="font-weight: 900; font-size: 17px">收入进度<a style="margin-left: 6%"></a>:<a style="margin-left: 15%"></a></span>
					<center>
			<div class="progress-out" id="div3" >
				<div class="percent-show">
					<span>0</span>%
				</div>
				<div class="progress-in"></div>
			</div>
		</center>
		</div>
		

		
		<div style="margin-top: 10%; margin-left: 2%">
			<span style="font-weight: 900; font-size: 17px">收入预期<a style="margin-left: 5.5%"></a> :<a style="margin-left: 1.8%"></a> ${bennianSe }
				万元</span>
		</div>
	</div>



	<div id="main2" style="width: 49%; height: 300%; margin-left: 52%">
	</div>
</div>


 --%>
 <style>
<!--
div.calBlock{
  display: inline-block;
  width: 47%;
  height:400px;
  position: relative;
  vertical-align: middle;
  margin:10px;
  border:1px solid #E3E3E3;
}
div.calBlock h2{
	text-align: center;color: #000000;font-size: 18px;
}
.totalSummary_content{
	width:80%; margin-top: 110px;
}
.totalSummary{ width:100%; height:50px;padding: 10px;} 
.totalSummary_sub{
	float:left;
}  
.totalSummary_sub span{
	font-weight: 900; font-size: 18px;
}  
.totalSummary_sub_left{
	width:30%;
}  
.totalSummary_sub_left span{
	float: right;
}  
.totalSummary_sub_right{
	width: 60%;
    padding-left: 15px;
}  
-->
</style>
<div style="width: 100%;  height: 100%;overflow:scroll;">
	<div class="calBlock"  id="main1">
		<div class="totalSummary_content">
			<div class="totalSummary">
				<div class="totalSummary_sub totalSummary_sub_left"><span>全市总收入:</span></div>
				<div class="totalSummary_sub totalSummary_sub_right"><span>${bennianSj }万元</span></div>
			</div>
			<div class="totalSummary">
				<div class="totalSummary_sub totalSummary_sub_left"><span>收入进度:</span></div>
				<div class="totalSummary_sub totalSummary_sub_right">
					<div class="progress-out" id="div3">
					<div class="percent-show">
						<span>0</span>%
					</div>
					<div class="progress-in"></div>
				</div>
				</div>
			</div>
			<div class="totalSummary">
				<div class="totalSummary_sub totalSummary_sub_left"><span>收入预期:</span></div>
				<div class="totalSummary_sub  totalSummary_sub_right"><span>${bennianSe }万元</span></div>
			</div>
		</div>
	</div>
	<div class="calBlock">
 		<div id="main2" style="height:90%;width:100%"></div>
	</div>
	<div class="calBlock">
 		<div  id="main3" style="height:90%;width:100%"></div>
	</div>
	<div class="calBlock">
 		<div  id="main4" style="height:90%;width:100%"></div>
	</div>
</div>

<script type="text/javascript">
$(function(){
	var windowWidth = window.innerWidth; 
//	alert(windowWidth);
	if(windowWidth == 1024){
		$(".totalSummary_sub span").css("font-size","14px");
	}
});
	function addOption(se, titlestr, index) {
		var obj = eval('(' + se + ')');
		var date2014 = new Array();
		var date2015 = new Array();
		var date2016 = new Array();
		for (var i = 0; i < obj.length; i++) {
			for (var j = 0; j < obj[i].length; j++) {
				var year = obj[i][j].substring(0, 4);
				if (year == 2014) {
					date2014.push(obj[i][index]);
				} else if (year == 2015) {
					date2015.push(obj[i][index]);
				} else if (year == 2016) {
					date2016.push(obj[i][index]);
				}
			}
		}
		var option = {
			title : {
				top :15,
				left : 15,
				text : titlestr,
				x : 'left',
				y : 'top',
				textStyle : {
					fontSize : 14, //echarts图中标注的字体大小
				}
			},
			tooltip : {
				trigger : 'axis',
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			legend : {
				top : 15,
				right : 10,
				data : [ '2014', '2015', '2016' ]
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			xAxis : [ {
				type : 'category',
				data : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月',
						'十月', '十一月', '十二月' ]
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : [ {
				name : '2014',
				type : 'bar',
				data : date2014,

				itemStyle : {
					normal : {
						color : '#7CA384',
					}
				}
			}, {
				name : '2015',
				type : 'bar',
				data : date2015,
				itemStyle : {
					normal : {
						color : '#2F4554',
					}
				}

			}, {
				name : '2016',
				type : 'bar',
				data : date2016,

				itemStyle : {
					normal : {
						color : '#C35652',
					}
				}

			} ]
		};
		return option;
	}
	var bennianSj = '${bennianSj}';
	var bennianSe = '${bennianSe}';
	var percentbl = '${bennianBl}';
	$(function() {
		$("#div3").myProgress({
			speed : 1000,
			percent : percentbl,
			width : "100%"
		});
	})
	var SeList = '${listStr}';
	var myChart2 = echarts.init(document.getElementById('main2'));
	var myChart3 = echarts.init(document.getElementById('main3'));
	var myChart4 = echarts.init(document.getElementById('main4'));
	myChart2.setOption(addOption(SeList, '国、地税总收入(万元)', 3));
	myChart3.setOption(addOption(SeList, '国税收入(万元)', 1));
	myChart4.setOption(addOption(SeList, '地税收入(万元)', 2));
</script> 


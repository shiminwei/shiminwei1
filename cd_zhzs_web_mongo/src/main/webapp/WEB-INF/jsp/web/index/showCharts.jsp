<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>

<script src="${basePath }static/js/echarts.min.js"></script>
<script type="text/javascript" src="${basePath }static/js/jquery.min.js""></script>
<script type="text/javascript" src="${basePath }static/js/jquery.myProgress.js"></script>
<link type="text/css" href="${basePath }static/css/myProgress.css" rel="stylesheet">
<div style="width: 100%; height: 400px;">
	<div id="main1" style="width:49%; height:400px; margin-left: -30px">
<center style="margin-top: px">
	<br>
	<div class="" id="div1">
	</div>
	<br/>

	<div class="" id="div2">
	
	</div>
	<br/>
	<div class="progress-out" id="div3" style="margin-left: 200px;">
    	<div class="percent-show"><span>0</span>%</div>
    	<div class="progress-in"></div>
	</div>
	<div class="123" id="div4" style="margin-top: 25px;margin-left: -300px">
		<span class="123" style="margin-left: -165px;margin-top: -200px">全市总收入 : </span>
	</div>
	<div class="123" id="div4" style="margin-top: 25px;margin-left: -300px">
		<span class="123" style="margin-left: -165px;margin-bottom: -200px">收入预期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp: </span>
	</div>

	<br/>
</center>	
	</div>
	<div id="main2" style="width: 49%; height: 400px; margin-left: 52%;margin-top: -350px">
	</div>
</div>

<div style="width: 100%; height: 400px;">
	<div id="main3" style="width: 49%; float: left; height: 400px;">
	</div>
	<div id="main4" style="width: 49%; height: 400px; margin-left: 52%">
	</div>
</div>



<script type="text/javascript">
	//var myChart1 = echarts.init(document.getElementById('main1'));
	var myChart2 = echarts.init(document.getElementById('main2'));
	var myChart3 = echarts.init(document.getElementById('main3'));
	//var myChart4 = echarts.init(document.getElementById('main4'));

	var xAxisData = [];
	var data1 = [];
	var data2 = [];
	for (var i = 0; i < 100; i++) {
		xAxisData.push('类目' + i);
		data1.push((Math.sin(i / 5) * (i / 5 - 10) + i / 6) * 5);
		data2.push((Math.cos(i / 5) * (i / 5 - 10) + i / 6) * 5);
	}
	
	  $(function () {
	        $("#div1").myProgress({speed: 1000, percent: 30, width: "100px", height: "10px"});
	        $("#div2").myProgress({speed: 500, percent: 100});
	        $("#div4").myProgress({speed: 1000, percent: 1});
	        $("#div3").myProgress({speed: 1000, percent: 90,width: "400px"});
	    })
	
	var option2 = {
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b}: {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			x : 'left',
			data : [ '直达', '营销广告', '搜索引擎', '邮件营销', '联盟广告', '视频广告', '百度', '谷歌',
					'必应', '其他' ]
		},
		series : [ {
			name : '访问来源',
			type : 'pie',
			selectedMode : 'single',
			radius : [ 0, '30%' ],

			label : {
				normal : {
					position : 'inner'
				}
			},
			labelLine : {
				normal : {
					show : false
				}
			},
			data : [ {
				value : 335,
				name : '直达',
				selected : true
			}, {
				value : 679,
				name : '营销广告'
			}, {
				value : 1548,
				name : '搜索引擎'
			} ]
		}, {
			name : '访问来源',
			type : 'pie',
			radius : [ '40%', '55%' ],

			data : [ {
				value : 335,
				name : '直达'
			}, {
				value : 310,
				name : '邮件营销'
			}, {
				value : 234,
				name : '联盟广告'
			}, {
				value : 135,
				name : '视频广告'
			}, {
				value : 1048,
				name : '百度'
			}, {
				value : 251,
				name : '谷歌'
			}, {
				value : 147,
				name : '必应'
			}, {
				value : 102,
				name : '其他'
			} ]
		} ]
	};

	var option3 = {
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		legend : {
			data : [ '直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎', '百度', '谷歌', '必应',
					'其他' ]
		},
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		xAxis : [ {
			type : 'category',
			data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
		} ],
		yAxis : [ {
			type : 'value'
		} ],
		series : [ {
			name : '直接访问',
			type : 'bar',
			data : [ 320, 332, 301, 334, 390, 330, 320 ]
		}, {
			name : '邮件营销',
			type : 'bar',
			stack : '广告',
			data : [ 120, 132, 101, 134, 90, 230, 210 ]
		}, {
			name : '联盟广告',
			type : 'bar',
			stack : '广告',
			data : [ 220, 182, 191, 234, 290, 330, 310 ]
		}, {
			name : '视频广告',
			type : 'bar',
			stack : '广告',
			data : [ 150, 232, 201, 154, 190, 330, 410 ]
		}, {
			name : '搜索引擎',
			type : 'bar',
			data : [ 862, 1018, 964, 1026, 1679, 1600, 1570 ],
			markLine : {
				lineStyle : {
					normal : {
						type : 'dashed'
					}
				},
				data : [ [ {
					type : 'min'
				}, {
					type : 'max'
				} ] ]
			}
		}, {
			name : '百度',
			type : 'bar',
			barWidth : 5,
			stack : '搜索引擎',
			data : [ 620, 732, 701, 734, 1090, 1130, 1120 ]
		}, {
			name : '谷歌',
			type : 'bar',
			stack : '搜索引擎',
			data : [ 120, 132, 101, 134, 290, 230, 220 ]
		}, {
			name : '必应',
			type : 'bar',
			stack : '搜索引擎',
			data : [ 60, 72, 71, 74, 190, 130, 110 ]
		}, {
			name : '其他',
			type : 'bar',
			stack : '搜索引擎',
			data : [ 62, 82, 91, 84, 109, 110, 120 ]
		} ]
	};

	
	// 使用刚指定的配置项和数据显示图表。
	//myChart1.setOption(option1);
	myChart2.setOption(option2);
	myChart3.setOption(option3);
	//myChart4.setOption(option4);
</script>


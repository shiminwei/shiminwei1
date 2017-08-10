<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common/base.jsp"%>
<link rel="stylesheet" href="${basePath }static/css/myProgress.css">
<script src="${basePath }static/js/echarts.min.js"></script>
<script src="${basePath }static/js/jquery.myProgress.js"></script>
<style>
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
</style>
<div style="width: 100%;  height: 100%">
	<div class="calBlock">
 		<div id="piechart" style="height:90%;width:100%"></div>
	</div>
	<div class="calBlock">
 		<div id="histogram" style="height:90%;width:100%"></div>
	</div>
</div>
<script type="text/javascript">	
	/**柱状图
	**/
	function histogram(value_) {	
		var date = new Array();
		var failure = new Array();
		var success = new Array();
		if(value_ != null && value_ != ''){
			var val_s = value_.split("#");
			if(val_s != null){
				for(var i=val_s.length ;i >= 0 ;i--){
					if(val_s[i] == null) continue;
					var val_ = val_s[i].split("_");
					if(val_ != null && val_.length == 3){
						date.push(val_[0]);
						failure.push(val_[1]);
						success.push(val_[2]);
					}
				}
			}
		}
		
		var option = {
			title : {
				top : 0,
				left : 0,
				text : '调度成功失败图',
				x : 'left',
				y : 'top',
				textStyle : {
					fontSize : 18,
				}
			},
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'shadow'
				}
			},
			legend : {
				top : 15,
				right : 10,
				data : ['成功', '失败']
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			xAxis : [ {
				type : 'category',
				data : date
			} ],
			yAxis : [ {			
				min: 0,
			    max: parseInt('${max_}'),
				interval:parseInt('${interval_}')
			} ],
			series : [ {
				name : '成功',
				type : 'bar',
				data : success,
				barMaxWidth:30,
				itemStyle : {
					normal : {
						color : 'green',
					}
				}
			}, {
				name : '失败',
				type : 'bar',
				data : failure,
				barMaxWidth:30,
				itemStyle : {
					normal : {
						color : 'red',
					}
				}

			} ],
			barGap:'1%'
		};
		return option;
	}
	
	/** 饼图
	**/
	function piechart(key_ ,value_) {		
		var color = new Array();
		var data = new Array();
		var key_val = [];
		var obj = eval("("+value_+")");
		var key_ = key_.split("#");
		for(var i=0; i<key_.length; i++){
			data.push(key_[i]);
			color.push(obj[key_[i]].color);
			key_val.push(obj[key_[i]].value)
		}
		var option = { title : {
	        text: '调度情况统计',
	        subtext: '',
	        x:'right'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	        data: data
	    },
	    color:color,
	    series : [
	        {
	            name: '调度情况统计',
	            type: 'pie',
	            radius : '50%',
	            center: ['45%', '50%'],
	            data:key_val,
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]};
		return option;
	}
	
	$(document).ready(function(){ 	
		setTimeout("readys()", 50 )	
	}); 
	function readys(){ 
		var piechart_val = '${piechart_val}';
		var piechart_key = '${piechart_key}';
		var histogram_val = '${histogram}' ;
		var piechart_fun = echarts.init(document.getElementById('piechart'));
		var histogram_fun = echarts.init(document.getElementById('histogram'));
		piechart_fun.setOption(piechart(piechart_key,piechart_val));
		histogram_fun.setOption(histogram(histogram_val));
		histogram_fun.on("click", onClick); 
	} 
	
	function onClick(param) {
		var tabid = "loggerManager";
		var title = "日志列表";
		var url = "${basePath }web/tologgers";
		var data = {'date':param.name};
		navTab.openTab(tabid, url,{ title:title, fresh:true, data:data});
	}  
	
</script> 


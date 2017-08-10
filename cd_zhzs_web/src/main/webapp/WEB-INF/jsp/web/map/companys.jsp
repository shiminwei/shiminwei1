<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="../../../../../common/base.jsp"%>
<head>  
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <title>百度地图API的使用</title> 
    <style type="text/css">
		body, html{
		width: 100%;
		height: 100%;
		margin:0;
		font-family:"微软雅黑";
		}
		#l-map{
		height:790px;
		width:100%;
		}
		#r-result{
		width:100%; 
		font-size:14px;
		line-height:20px;
		}
		.table-c table{
		border-right:2px solid #000000;
		border-bottom:2px solid #000000
		} 
		.table-c table td{
		border-left:2px solid #000000;
		border-top:2px solid #000000
		} 
		.lable{
	    width: 35%;
	    height: 40px;
	    text-align: center;
	    background-color: #41c8ef;
		}
		.title{
	    width: 75%;
	    height: 40px;
		}
		.searchBar {
	    line-height: 60px;
	    margin-top: 20px;
	    margin-bottom: 20px;
	}
	</style>  
    <!-- 百度地图API-->
<script src="http://api.map.baidu.com/api?v=1.4&callback=initialize" type="text/javascript"></script>  <script type="text/javascript">  
	var adds;
	var map;
	var myGeo ;
	var marker;
    function initialize() {  
        //创建地图实例  
        map = new BMap.Map("l-map");
        map.centerAndZoom(new BMap.Point(117.48 ,30.67), 13);
    	map.enableScrollWheelZoom(true);
    	myGeo = new BMap.Geocoder();
    	getCompanys();
    	$('#getPoint').trigger('click');
    	$("#r-result").hide();
    }
    var index = 0;
	adds = [
    		/*  "包公井花园A栋306室,奥运村小宾馆",
    		"池州市贵池区翠柏中路16号,池州市莫泰168连锁旅店",
    		"贵池区清风大道,东方威尼斯休闲会所",
    		"池州市贵池区杏花大道82号,贵池区龙豪快捷宾馆"  */
    	];
	function getCompanys(){
		$.ajax({
			type : "POST",
			url : "web/address/getAllCompany",
			async: false,
			data : "",
			dataType : "json",
			success : function(result) {
				$.each(result,function(index, obj) {
					adds.push(obj.QYXX);
				})
			}
		}) 
	}
	function bdGEO(){
		/* for(var i in adds){
		    console.log(adds[i]);
		} */
		var add = adds[index];
		geocodeSearch(add);
		index++;
	}
	function geocodeSearch(add){
		if(index < adds.length){
			setTimeout(window.bdGEO,40);
		} 
		myGeo.getPoint(add, function(point){
			if (point) {
				var address = new BMap.Point(point.lng, point.lat);
				var start= add.indexOf(",");
				var newAddr = add.substring(0,start);
				addMarker(address,new BMap.Label(newAddr,{offset:new BMap.Size(20,-10)}),add);
			}
		}, "池州市");
	}
	// 编写自定义函数,创建标注
	function addMarker(point,label,add){
		marker = new BMap.Marker(point);
		map.addOverlay(marker);
		marker.setLabel(label);
		marker.addEventListener("click", function(){
			var start= add.indexOf(",");
			 var newAddr= add.substring(start+1,add.length);
			 var company = add.substring(0,start);
			$("#company").text(company);
			$("#regist").text(newAddr);
			
		});
	}
	
    window.onload = initialize;  
    </script>  
</head>  
<div style=" float: left; width: 20%;"  class="table-c">
	
		<table border="0" cellspacing="1" cellpadding="0" width="100%">
		<tr>
			<td class="lable">纳税人名称</td>
			<td class="title" ><lable id="company">池州市永晶金属科技有限公司</lable></td>
		</tr>
		<tr>
			<td class="lable">注册地址</td>
			<td class="title"><lable id="regist">安徽省池州市</lable></td>
		</tr>
		<tr>
			<td class="lable">纳税人识别号</td>
			<td class="title">34011178952623582</td>
		</tr>
		<tr>
			<td class="lable">法人代表</td>
			<td class="title">张三</td>
		</tr>
		<tr>
			<td class="lable">联系电话</td>
			<td class="title">18100512017</td>
		</tr>
		<tr>
			<td class="lable">主管地税机关</td>
			<td class="title">18100512017</td>
		</tr>
		<tr>
			<td class="lable">主管国税机关</td>
			<td class="title">18100512017</td>
		</tr>
		<tr>
			<td class="lable">本月税收总额</td>
			<td class="title"> <div style="float:left">1990</div><label style="float:right;margin-right: 20px;">元</label></td>
		</tr>
		<tr>
			<td class="lable">上年同期总额</td>
			<td class="title"><div style="float:left">1990</div><label style="float:right;margin-right: 20px;">元</label></td>
		</tr>
		<tr>
			<td class="lable">增幅</td>
			<td class="title"><div style="float:left">1990</div><label style="float:right;margin-right: 20px;">%</label></td>
		</tr>
		</table>
	
	
	
	</div>
	<div>
	
<div style="float: left; width: 80%;background-color:#41c8ef">
	<form method="post" onsubmit="return navTabSearch(this);">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>年度：</label> <select class="combox" id="startDate"
					name="startDate">
						<option value="2014"
							<c:if test="${startDate==2014 }">selected="selected"</c:if>>2014</option>
						<option value="2015"
							<c:if test="${startDate==2015 }">selected="selected"</c:if>>2015</option>
						<option value="2016"
							<c:if test="${startDate==2016 }">selected="selected"</c:if>>2016</option>
						<option value="2017"
							<c:if test="${startDate==2017 }">selected="selected"</c:if>>2017</option>
				</select></li>
				
				<li><label>纳税人名称：</label> <input type="text" name="nsrmc"
					/></li>
				<dd>
					<div style="margin-left: -30px" class="button">
						<div class="buttonContent">
							<button>查询</button>
						</div>
					</div>
				</dd>
			</ul>
		</div>
	</form>
</div>
		</div>
		<div id="l-map" style=" float: left; width: 80%"></div>
	</div>
	
	<div id="r-result">
		<input type="button" id="getPoint" value="批量地址解析" onclick="bdGEO()" />
		<div id="result"></div>
	</div>
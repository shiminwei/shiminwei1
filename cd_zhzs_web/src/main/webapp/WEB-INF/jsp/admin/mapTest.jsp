<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="../../../../../common/base.jsp"%>
<head>  
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <title>百度地图API的使用</title>  
    <!-- 百度地图API-->
<script src="http://api.map.baidu.com/api?v=1.4&callback=initialize" type="text/javascript"></script>  <script type="text/javascript">  
    function initialize() {  
        //创建地图实例  
        var map = new BMap.Map('map');  
        //创建一个坐标
        var point =new BMap.Point(113.264641,23.154905);
        //地图初始化，设置中心点坐标和地图级别  
        map.centerAndZoom(point,15);  
    }  
    window.onload = initialize;  
    </script>  
</head>  
 <div id="map" style="width:500px;height:320px"></div>  

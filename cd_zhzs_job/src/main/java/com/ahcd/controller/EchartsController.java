package com.ahcd.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ahcd.common.DateUtil;
import com.ahcd.pojo.Page;
import com.ahzd.pojo.DataJob;
import com.ahzd.pojo.DataJobLogger;
import com.ahzd.service.DataJobLoggerMongoService;
import com.ahzd.service.EchartsMongoService;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;

@Controller  
@RequestMapping("/web")  
public class EchartsController {
	@Resource
	private EchartsMongoService echartsMongoService;
	@Resource
	private DataJobLoggerMongoService dataJobLoggerMongoService;
	@RequestMapping("/index")  
    public String index(HttpServletRequest request ){		
		/** 饼图
		 *  分组统计调度情况
		 * */	
		JSONObject retlist = new JSONObject();
		StringBuffer piechart_key = new StringBuffer();
		doPiechart("used","","",retlist,piechart_key);
		
		/** 柱状图
		 *  按日期分组统计成功/失败个数
		 *  备注：统计当前日期前30天的前10条数据
		 * */
		StringBuffer histogram = new StringBuffer();
		String date = DateUtil.date2Str("yyyy-MM-dd", new Date());
		long length = doHistogram(date,date,histogram,0);
		long model = getOrdinateModel(length);
		request.setAttribute("piechart_key", piechart_key.length()>0?piechart_key.substring(1).toString():"");
		request.setAttribute("piechart_val", retlist);
		request.setAttribute("histogram", histogram.length()>0?histogram.substring(1).toString():0);
		request.setAttribute("max_", (model*(length/model)+model));
		request.setAttribute("interval_",(model*(length/model)+model)/10);
		return "/web/echarts/echarts";
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("schedule")
	public String getSchedule(HttpServletRequest request){
		List list = new ArrayList();
		List<DataJobLogger> dataJobLogger = dataJobLoggerMongoService.getAllJobLogger();
		for (int i = 0; i<dataJobLogger.size()-1; i++) {
			HashMap map = new HashMap();
			String name = dataJobLogger.get(i).getName();
			String content = dataJobLogger.get(i).getContent();
			//将内容后面含有特殊符号的将其去掉，不然前端报错
//			if(content!=null){
//				String str = content.substring(content.length()-2, content.length()-1);
//				String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】'；：”“’。，、？]"; 
//				Pattern p = Pattern.compile(regEx);
//				Matcher m = p.matcher(str);
//				content = content.substring(0, content.length()-1);
//				content = content+m.replaceAll("").trim();
//			}
			
			String executioTime = dataJobLogger.get(i).getExecutioTime();
			String result = dataJobLogger.get(i).getResult()==0?"失败":"成功";
			String []dateTime = executioTime.split(" ");
			String []date=dateTime[0].split("-");
			String []time = dateTime[1].split(":");
			map.put("year",date[0]);
			map.put("month", date[1]);
			map.put("day", date[2]);
			map.put("hour", time[0]);
			map.put("minu", time[1]);
			map.put("name", name);
			map.put("content", content);
			map.put("result", result);
			list.add(map);
		}
		request.setAttribute("list",list);
		return "/web/echarts/schedule"; 
	} 
	/**
	 * 点击柱状图跳转到日志列表页面
	 * **/
	@RequestMapping("/tologgers")  
    public String tologgers(HttpServletRequest request, Model model, Page<DataJobLogger> page, DataJobLogger bean){
		String date = request.getParameter("date");
		page = echartsMongoService.loggerList(page,date);
		request.setAttribute("pageList", page);
		request.setAttribute("bean", bean);
		request.setAttribute("date", date);
		return "/web/echarts/loggers";
	}
	
	//调度情况统计
	@SuppressWarnings({ "static-access" })
	private void doPiechart(String getkey,String wherekey,String wherevalue,JSONObject retlist,StringBuffer retval){
		Criteria criteria = new Criteria();
		GroupBy groupBy = GroupBy.key(getkey);
		if(!"".equals(wherevalue) && wherevalue != null){
			criteria = new Criteria().where(wherekey).is(Integer.parseInt(wherevalue));
		}
		groupBy.initialDocument("{count:0}");
		groupBy.reduceFunction("function(doc, out){out.count++}");
		groupBy.finalizeFunction("function(out){return out;}");
		GroupByResults<DataJob> type = echartsMongoService.fingUsedGroupCount(criteria,"zhzs_job_config",groupBy);	
		@SuppressWarnings("unchecked")
		List<BasicDBObject> list = (List<BasicDBObject>) type.getRawResults().toMap().get("retval");
		JSONObject oblist ;
		JSONObject object ;
		for(BasicDBObject obj: list){
			oblist = new JSONObject();
			object = new JSONObject();
			String[] stemp = getUsedColor(wherevalue+String.valueOf(obj.get(getkey))).split("#");
			if(stemp.length > 1){
				object.put("name", stemp[0]);
				object.put("value", obj.get("count")!=null?String.valueOf(obj.get("count")).split("[.]")[0]:0);
				oblist.put("color", stemp[1]);
				oblist.put("value", object);
				retlist.put(stemp[0], oblist);
				retval.append("#"+stemp[0]);
			}else if( !"".equals(stemp[0]) && stemp.length==1){	
				doPiechart("status",getkey,stemp[0],retlist,retval);
			}
		}
	}
	
	//根据调度任务中的[使用状态或运行状态]获得列名和对应饼图的颜色
	private String getUsedColor(String used){
		int temp = -1;
		if(used != null){
			temp = Integer.parseInt(used.split("[.]")[0]);
		}
		String usedColor = "" ;
		switch (temp) {
		case 0: usedColor = "未使用#gray";
		break ;
		case 1: usedColor = "1";
		break ;
		case 11: usedColor = "正在运行#green";
		break ;
		case 12: usedColor = "未运行#yellow";
		break ;
		case 13: usedColor = "暂停运行#red";
		break ;
		case 14: usedColor = "运行结束#blue";
		break ;
		}
		return usedColor;
	}
	
	//调度成功失败
	private long doHistogram(String original ,String date ,StringBuffer ret,long length){
		// 状态     0 异常   1正常
		long excep = echartsMongoService.logcount(date,0);
		long normal = echartsMongoService.logcount(date,1);
		if(excep > 0 || normal > 0){
			ret.append("#"+date);
			ret.append("_"+excep);
			ret.append("_"+normal);
			if(excep > length){
				length = excep;
			}
			if(normal > length){
				length = normal;
			}
		}
		int count = ret.toString().split("#").length;
		if(count > 10 || date.endsWith(getPreviousDay(original,30))){
			return length;
		}else{
			return doHistogram(original,getPreviousDay(date,1),ret,length);
		}
	}
	
	//获得指定日期的前N天
	private static String getPreviousDay(String current,int count){  
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
		date = new SimpleDateFormat("yyyy-MM-dd").parse(current); 
		} catch (ParseException e) { 
		e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-count); 

		String previous=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		return previous; 
		} 
	//获得最大纵坐标的倍数
	private long getOrdinateModel(long length){
		long ret = 10 ;
		while(true){
			length = length/10 ;
			if(length >= 10){
				ret = ret * 10 ;
			}else{
				return ret ;
			}
		}
	}
}

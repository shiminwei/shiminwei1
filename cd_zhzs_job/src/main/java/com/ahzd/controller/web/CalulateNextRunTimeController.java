package com.ahzd.controller.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ahzd.util.CronTrigger;
import com.ahzd.util.SimpleTriggerContext;

@Controller
public class CalulateNextRunTimeController {
	static SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String formatDt(Date dt){
	    try{
	        return sdt.format(dt);
	    }catch(Exception ex){
	        return "";
	    }
	}
	@ResponseBody
	@RequestMapping("/web/calulateNextRunTime")
	public List<String> calulateNextRunTime(HttpServletRequest request,String cronExpression){
		List<String> resultList=new ArrayList<String>();
		CronTrigger trigger = new CronTrigger(cronExpression);
		SimpleTriggerContext context = new SimpleTriggerContext();
		Date dt = trigger.nextExecutionTime(context);
		context.update(dt, dt, dt);
		for(int i=0;i<5;i++){
		    dt = trigger.nextExecutionTime(context);
		    context.update(dt, dt, dt);
		    resultList.add(formatDt(dt));
		}
		return resultList;
	}
 }

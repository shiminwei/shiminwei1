package com.ahcd.common;

import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class QuartzVo implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
	        System.out.println("hello job, "+new Date());
	    }


}
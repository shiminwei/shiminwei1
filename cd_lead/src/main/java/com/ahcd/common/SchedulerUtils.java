package com.ahcd.common;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerUtils {

	/**
	 * 
	   * 功能说明    :启动调度 
	   * @author   : fei yang 
	   * @version ：2017年2月16日 下午2:33:52 
	   * @return
	 */
	public static boolean toStart() {
		boolean flag = false;
		JobDetail jobDetail = JobBuilder.newJob(QuartzVo.class).withIdentity("helloJob", "group1").build();
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler;
		try {
			scheduler = schedulerFactory.getScheduler();
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger", "cronTrigger")
					.withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?")).build();
			// 添加job，以及其关联的trigger
			scheduler.scheduleJob(jobDetail, trigger);
			// 启动job
			scheduler.start();
			flag = true;
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
}

package com.ahzd.quartz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.ahzd.pojo.DataJob;
import com.ahzd.service.DataJobMongoService;

public class ScheduleInit implements ApplicationListener<ApplicationEvent> {

	@Resource
	private DataJobMongoService dataJobMongoService;

	/**
	 * 项目重启后调度任务重启
	 */
	@SuppressWarnings("static-access")
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		List<DataJob> dataJobs = dataJobMongoService.findAll();
		if (dataJobs!=null) {
			for (int i = 0; i < dataJobs.size(); i++) {
				DataJob dataJob=dataJobs.get(i);
				if (dataJob.getUsed()==1&&dataJob.getStatus()==1) {//已使用并且正在运行的任务重启
					DataJobSchuleUtils utils=new DataJobSchuleUtils();
					utils.toCreateScheduleJobByDataJob(dataJob);
					utils.toStartScheduleJob();
				}
			}
		}
	}
}

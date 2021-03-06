package com.ahzd.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import com.ahcd.common.Constant;

public class ScheduleJobListener implements JobListener {

	@Override // 相当于为我们的监听器命名
	public String getName() {
		return "scheduleJobListener";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
//		System.out.println(
//				getName() + "触发对" + context.getJobDetail().getJobClass() + "的开始执行的监听工作，这里可以完成任务前的一些资源准备工作或日志记录");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		//System.out.println("被否决执行了，可以做些日志记录。");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
//		System.out.println(
//				getName() + "触发对" + context.getJobDetail().getJobClass() + "结束执行的监听工作，这里可以进行资源销毁工作或做一些新闻扒取结果的统计工作");	
		boolean handleState=(Boolean) context.getJobDetail().getJobDataMap().get(Constant.JOB_PARAM_RESULT_KEY);
		if (handleState) {
//			System.out.println("任务处理完成=======》");
//		}else {
//			System.out.println("任务处理失败=======》");
		}
	}

}

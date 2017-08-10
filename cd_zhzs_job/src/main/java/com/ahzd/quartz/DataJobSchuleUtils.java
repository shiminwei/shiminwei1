package com.ahzd.quartz;

import org.quartz.CronExpression;

import com.ahcd.common.Constant;
import com.ahzd.pojo.DataJob;

public class DataJobSchuleUtils {
	
	/**
	 * 
	   * 功能说明    :创建调度任务
	   * @author   : fei yang 
	   * @version ：2017年4月18日 上午9:29:40 
	   * @param dataJob
	   * @return
	 */
	public static boolean toCreateScheduleJobByDataJob(DataJob dataJob) {
		boolean flag = false;
		if (dataJob != null && dataJob.getSteps() != null && dataJob.getSteps().size() > 0) {
			try {
				ScheduleUtils scheduleUtils = new ScheduleUtils();
				String jobName=dataJob.get_id();//调度任务名称
				//String cronExpression = dataJob.getNextRunTime();// 调度周期	
				String cronExpression = dataJob.getRunPeriod();// 调度周期
				if(cronCheck(cronExpression)){
					scheduleUtils.createScheduleJobWithListener(jobName, Constant.GROUP_PARAM_KEY, cronExpression, JobHandle.class, dataJob);
					return true;
				}
			} catch (Exception e) {
				return flag;
			}
		}
		return flag;
	}
	/**
	 * 功能名称：暂停调度任务
	 * 
	 * 
	 */
	public static boolean toPauseByDataJob(DataJob dataJob){
		boolean flag = false;
		if (dataJob != null && dataJob.getSteps() != null && dataJob.getSteps().size() > 0) {
			try {
				ScheduleUtils scheduleUtils = new ScheduleUtils();
				String jobName=dataJob.get_id();//调度任务名称
				scheduleUtils.pauseJob(jobName, Constant.GROUP_PARAM_KEY);
				flag = true;
				return flag;
			} catch (Exception e) {
				return flag;
			}
		}
		return flag;
	}
	
	/**
	 * 功能名称：恢复调度任务
	 * 
	 * 
	 */
	public static boolean resumeByDataJob(DataJob dataJob){
		boolean flag = false;
		if (dataJob != null && dataJob.getSteps() != null && dataJob.getSteps().size() > 0) {
			try {
				ScheduleUtils scheduleUtils = new ScheduleUtils();
				String jobName=dataJob.get_id();//调度任务名称
				scheduleUtils.resumeJob(jobName, Constant.GROUP_PARAM_KEY);
				flag = true;
				return flag;
			} catch (Exception e) {
				return flag;
			}
		}
		return flag;
	}
	/**
	 * 功能名称：删除调度任务
	 * 
	 * 
	 */
	public static boolean deleteByDataJob(DataJob dataJob){
		boolean flag = false;
		if (dataJob != null && dataJob.getSteps() != null && dataJob.getSteps().size() > 0) {
			try {
				String jobName=dataJob.get_id();//调度任务名称
				ScheduleUtils.deleteScheduleJob(jobName, Constant.GROUP_PARAM_KEY);
				flag = true;
				return flag;
			} catch (Exception e) {
				return flag;
			}
		}
		return flag;
	}
	
	
	/**
	 * 校验cron表达式是否有误      2017-04-20  peixizhu 
	 */
	public static Boolean cronCheck(String cronExpression){
		return CronExpression.isValidExpression(cronExpression);
	}
	
	/**
	 * 
	   * 功能说明    :开始执行调度任务 
	   * @author   : fei yang 
	   * @version ：2017年4月18日 上午9:53:45 
	   * @param dataJob
	 */
	public static void toStartScheduleJob() {
		ScheduleUtils scheduleUtils = new ScheduleUtils();
		scheduleUtils.startSchedule();
	}


//	public static void main(String[] args) {
//		DataJob dataJob = new DataJob();
//		dataJob.set_id("我是调度1的ID");
//		dataJob.setName("我是调度1");
//		dataJob.setRunPeriod("0/10 * * * * ?");
//		List<DataJobStepBase> stepBases = new ArrayList<DataJobStepBase>();
//		DataJobStepBase stepBase1 = new DataJobStepBase();
//		stepBase1.setName("我是步骤1ID");
//		stepBase1.setName("我是步骤1");
//		stepBase1.setType(1);
//		DataJobStepBase stepBase2 = new DataJobStepBase();
//		stepBase1.setName("我是步骤2ID");
//		stepBase2.setName("我是步骤2");
//		stepBase2.setType(2);
//		stepBases.add(stepBase1);
//		stepBases.add(stepBase2);
//		dataJob.setSteps(stepBases);		
//		boolean flag = toCreateScheduleJobByDataJob(dataJob);
//		System.out.println("最终执行状态==="+flag);
//		
//		if (flag) {
//			toStartScheduleJob();
//		}else {
//			System.out.println("添加任务异常");
//		}
//	}
}

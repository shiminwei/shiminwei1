package com.ahzd.quartz;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Matcher;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ahcd.common.Constant;

/**
 * 定时任务辅助类
 * 
 * Created by liyd on 12/19/14.
 */
public class ScheduleUtils {
	/** 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(ScheduleUtils.class);
	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
	private static Scheduler scheduler = null;

	/**
	 * 初始化scheduler
	 */
	public ScheduleUtils() {
		try {
			scheduler = schedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

/**
 * 
   * 功能说明    :调度开始 
   * @author   : fei yang 
   * @version ：2017年4月17日 下午5:20:30
 */
	public void startSchedule() {
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			LOG.error("调度开始异常", e);
			throw new ScheduleException("调度开始异常");
		}
	}

	/**
	 * 
	 * 功能说明 :创建定时任务
	 * @author : fei yang
	 * @version ：2017年4月17日 下午4:36:20
	 * @param jobName
	 * @param jobGroup
	 * @param cronExpression
	 * @param methodClass
	 */
	public void createScheduleJob(String jobName, String jobGroup, String cronExpression,
			Class<? extends Job> methodClass, Object param) {
		// 构建job信息
		JobDetail jobDetail = JobBuilder.newJob(methodClass).withIdentity(jobName, jobGroup).build();
		// 表达式调度构建器
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
		// 放入参数，运行时的方法可以获取
		jobDetail.getJobDataMap().put(Constant.JOB_PARAM_KEY, param);
		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(scheduleBuilder)
				.build();
		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			LOG.error("创建定时任务失败", e);
			throw new ScheduleException("创建定时任务失败");
		}
	}

	/**
	 * 
	   * 功能说明    : 创建定时任务(添加JOB监听)
	   * @author   : fei yang 
	   * @version ：2017年4月18日 上午9:06:38 
	   * @param jobName
	   * @param jobGroup
	   * @param cronExpression
	   * @param methodClass
	   * @param param
	   * @param jobListener
	 */
	public void createScheduleJobWithListener(String jobName, String jobGroup, String cronExpression,
			Class<? extends Job> methodClass, Object param) {
		// 构建job信息
		JobDetail jobDetail = JobBuilder.newJob(methodClass).withIdentity(jobName, jobGroup).build();
		//jobDetail.addJobListener("myJobListener");//这里的名字和myJobListener中getName()方法的名字一样
	//	scheduler.addJobListener(jobListener);//向scheduler注册我们的监听器
		// 表达式调度构建器
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
		// 放入参数，运行时的方法可以获取
		jobDetail.getJobDataMap().put(Constant.JOB_PARAM_KEY, param);
		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(scheduleBuilder)
				.build();
		try {
			JobListener jobListener=new ScheduleJobListener();
			Matcher<JobKey> matcher = KeyMatcher.keyEquals(jobDetail.getKey());  
			scheduler.getListenerManager().addJobListener(jobListener, matcher);  
			scheduler.scheduleJob(jobDetail, trigger);
			
		} catch (SchedulerException e) {
			LOG.error("创建定时任务失败", e);
			throw new ScheduleException("创建定时任务失败");
		}
	}
	/**
	 * 
	 * 功能说明 : 运行一次任务
	 * 
	 * @author : fei yang
	 * @version ：2017年4月17日 下午4:45:00
	 * @param jobName
	 * @param jobGroup
	 */
	public void runOnce(String jobName, String jobGroup) {
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		try {
			scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			LOG.error("运行一次定时任务失败", e);
			throw new ScheduleException("运行一次定时任务失败");
		}
	}

	/**
	 * 
	 * 功能说明 :暂停任务 (挂起)
	 * 
	 * @author : fei yang
	 * @version ：2017年4月17日 下午4:45:29
	 * @param jobName
	 * @param jobGroup
	 */
	public void pauseJob(String jobName, String jobGroup) {
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		try {
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			LOG.error("暂停定时任务失败", e);
			throw new ScheduleException("暂停定时任务失败");
		}
	}

	/**
	 * 
	 * 功能说明 : 恢复任务
	 * 
	 * @author : fei yang
	 * @version ：2017年4月17日 下午4:46:08
	 * @param jobName
	 * @param jobGroup
	 */
	public void resumeJob(String jobName, String jobGroup) {
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		try {
			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			LOG.error("暂停定时任务失败", e);
			throw new ScheduleException("暂停定时任务失败");
		}
	}

	/**
	 * 
	 * 功能说明 : 获取jobKey
	 * 
	 * @author : fei yang
	 * @version ：2017年4月17日 下午4:46:30
	 * @param jobName
	 * @param jobGroup
	 * @return
	 */
	public static JobKey getJobKey(String jobName, String jobGroup) {
		return JobKey.jobKey(jobName, jobGroup);
	}

	/**
	 * 
	 * 功能说明 : 获取触发器key
	 * 
	 * @author : fei yang
	 * @version ：2017年4月17日 下午4:47:57
	 * @param jobName
	 * @param jobGroup
	 * @return
	 */
	public static TriggerKey getTriggerKey(String jobName, String jobGroup) {
		return TriggerKey.triggerKey(jobName, jobGroup);
	}

	/**
	 * 
	 * 功能说明 : 获取表达式触发器
	 * 
	 * @author : fei yang
	 * @version ：2017年4月17日 下午4:49:34
	 * @param jobName
	 * @param jobGroup
	 * @return
	 */
	public CronTrigger getCronTrigger(String jobName, String jobGroup) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
			return (CronTrigger) scheduler.getTrigger(triggerKey);
		} catch (SchedulerException e) {
			LOG.error("获取定时任务CronTrigger出现异常", e);
			throw new ScheduleException("获取定时任务CronTrigger出现异常");
		}
	}

	/**
	 * 
	 * 功能说明 : 更新定时任务
	 * 
	 * @author : fei yang
	 * @version ：2017年4月17日 下午4:49:05
	 * @param jobName
	 * @param jobGroup
	 * @param cronExpression
	 * @param param
	 */
	public void updateScheduleJob(String jobName, String jobGroup, String cronExpression, Object param) {
		try {
			TriggerKey triggerKey = ScheduleUtils.getTriggerKey(jobName, jobGroup);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (SchedulerException e) {
			LOG.error("更新定时任务失败", e);
			throw new ScheduleException("更新定时任务失败");
		}
	}

	/**
	 * 
	 * 功能说明 : 删除定时任务
	 * 
	 * @author : fei yang
	 * @version ：2017年4月17日 下午4:50:07
	 * @param jobName
	 * @param jobGroup
	 */
	public static void deleteScheduleJob(String jobName, String jobGroup) {
		try {
			scheduler.deleteJob(getJobKey(jobName, jobGroup));
		} catch (SchedulerException e) {
			LOG.error("删除定时任务失败", e);
			throw new ScheduleException("删除定时任务失败");
		}
	}
}

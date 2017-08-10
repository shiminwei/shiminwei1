package com.ahzd.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ahcd.common.Constant;
import com.ahcd.common.SimpleMailSender;
import com.ahcd.common.StepTypeEnum;
import com.ahcd.log.Logger;
import com.ahcd.pojo.MailSenderInfo;
import com.ahcd.pojo.OperateResult;
import com.ahzd.dao.DataJobLoggerMongoDao;
import com.ahzd.pojo.DataJob;
import com.ahzd.pojo.DataJobLogger;
import com.ahzd.pojo.DataJobStepBase;
import com.ahzd.pojo.DataJobStepDbToEx;
import com.ahzd.pojo.DataJobStepDbToTxt;
import com.ahzd.pojo.DataJobStepDbToXml;
import com.ahzd.pojo.DataJobStepExToDb;
import com.ahzd.pojo.DataJobStepFileToFtp;
import com.ahzd.pojo.DataJobStepFtpToLocal;
import com.ahzd.pojo.DataJobStepSqlScripts;
import com.ahzd.pojo.DataJobStepTxtToDb;
import com.ahzd.service.DataJobLoggerMongoService;
import com.ahzd.service.DataJobMongoService;
import com.ahzd.service.DataJobStepDbToXmlService;
import com.ahzd.service.DataJobStepFileToFtpService;
import com.ahzd.service.DataJobStepSqlScriptsService;
import com.ahzd.service.DataJobStepTxtToDbService;
import com.ahzd.service.ExAndDbService;

/**
 * JOB 处理类
 * 
 * @author : fei yang
 * @version ：2017年4月18日 上午9:22:51
 */
@Component
public class JobHandle implements Job {
	
	private DataJobLogger logger = new DataJobLogger();
	private OperateResult operateResult = new OperateResult();
	@Autowired
	private DataJobStepSqlScriptsService dataJobStepSqlScriptsService;
	@Autowired
	private DataJobLoggerMongoDao dataJobLoggerMongoDao;
	@Resource
	private DataJobStepFileToFtpService fileToFtpService;
	@Resource
	private ExAndDbService exAndDbService;
	@Resource
	private DataJobMongoService dataJobMongoService;
	@Resource
	private DataJobStepTxtToDbService dataJobStepTxtToDbService;
	@Resource
	private DataJobStepDbToXmlService dataJobStepDbToXmlService;
	@Autowired
	private DataJobLoggerMongoService dataJobLoggerMongoService;
	
	private SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private DataJobStepSqlScripts stepSql = new DataJobStepSqlScripts();
	private DataJobStepFileToFtp needStep = new DataJobStepFileToFtp();
	private DataJobStepFtpToLocal local = new DataJobStepFtpToLocal();
	private DataJobStepExToDb stepToDb = new DataJobStepExToDb();
	private DataJobStepDbToEx stepToEx = new DataJobStepDbToEx();
	private DataJobStepTxtToDb txtToDb = new DataJobStepTxtToDb();
	private DataJobStepDbToTxt txtBean = new DataJobStepDbToTxt();
	private DataJobStepDbToXml dbToXml = new DataJobStepDbToXml();
	
	@SuppressWarnings("rawtypes")
	List list = new ArrayList();
	
	private static JobHandle jobHandle;
	public void setInfo(DataJobStepSqlScriptsService dataJobStepSqlScriptsService){
		this.dataJobStepSqlScriptsService = dataJobStepSqlScriptsService;
	}
	@PostConstruct
	public void init(){
		jobHandle = this;
		jobHandle.dataJobStepSqlScriptsService = this.dataJobStepSqlScriptsService;
	}
	@SuppressWarnings({ "unchecked" })
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		boolean handleState = true;
		boolean flag = false;
		try {
			DataJob dataJob = (DataJob) context.getMergedJobDataMap().get(Constant.JOB_PARAM_KEY);
			if (dataJob != null && dataJob.getSteps() != null && dataJob.getSteps().size() > 0) {
				System.out.println("开始对==》" + dataJob.getName() + "执行任务");
				List<DataJobStepBase> jobStepBases = (List<DataJobStepBase>) dataJob.getSteps();
				for (int i = 0; i < jobStepBases.size(); i++) {
					DataJobStepBase stepBase = jobStepBases.get(i);
					if (stepBase != null) {
						if (stepBase.getType() == StepTypeEnum.SQL_SCRIPT.getIndex()) {// 类型 1：从oracle执行sql
							stepSql = (DataJobStepSqlScripts) stepBase;
							operateResult  = this.checkSteps(dataJob,stepSql);
							if(!operateResult.isResult()){
								flag = false;
								saveLoggerInfo(dataJob,operateResult,1,flag);
								sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
								break;
							}else{
								flag = true;
								saveLoggerInfo(dataJob,operateResult,1,flag);
							}
						} else if (stepBase.getType() == StepTypeEnum.EX_TO_DB2003.getIndex()) {// 类型 2：excel2003的导入到DB操作     .xls
							stepToDb = (DataJobStepExToDb) stepBase;
							operateResult  = this.exportExcelToMongo(dataJob,stepToDb);
							if(!operateResult.isResult()){
								flag = false;
								saveLoggerInfo(dataJob,operateResult,2,flag);
								sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
								break;
							}else{
								flag = true;
								saveLoggerInfo(dataJob,operateResult,2,flag);
							}
						}else if (stepBase.getType() == StepTypeEnum.EX_TO_DB2007.getIndex()) {// 类型3：excel2007的导入到DB操作        .xlsx
							stepToDb = (DataJobStepExToDb) stepBase;
							operateResult = this.exportExcelToMongo(dataJob,stepToDb);
							if(!operateResult.isResult()){
								flag = false;
								saveLoggerInfo(dataJob,operateResult,3,flag);
								sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
								break;
							}else{
								flag = true; 
								saveLoggerInfo(dataJob,operateResult,3,flag);
							}
						}else if (stepBase.getType() == StepTypeEnum.DB_TO_EX2003.getIndex()) {// 类型4：DB导入到excel2003的操作        .xls
							stepToEx = (DataJobStepDbToEx) stepBase;
							operateResult = this.checkExportExcel97(dataJob, stepToEx);
							if(!operateResult.isResult()){
								flag = false;
								saveLoggerInfo(dataJob,operateResult,4,flag);
								sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
								break;
							}else{
								flag = true;
								saveLoggerInfo(dataJob,operateResult,4,flag);
							}
						} else if (stepBase.getType() == StepTypeEnum.DB_TO_EX2007.getIndex()) {//类型5：DB导入到excel2007的操作         .xlsx
							stepToEx = (DataJobStepDbToEx) stepBase;
							operateResult = this.checkExportExcel97(dataJob, stepToEx);
							if(!operateResult.isResult()){
								flag = false;
								saveLoggerInfo(dataJob,operateResult,5,flag);
								sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
								break;
							}else{
								flag = true;
								saveLoggerInfo(dataJob,operateResult,5,flag);
							}
						} else if (stepBase.getType() == StepTypeEnum.FILE_TO_FTP.getIndex()) {// 类型 6：ftp上传的操作
							needStep = (DataJobStepFileToFtp) stepBase;
							operateResult = this.checkFtpUpLoad(dataJob,needStep);
							if(!operateResult.isResult()){
								flag = false;
								saveLoggerInfo(dataJob,operateResult,6,flag);
								sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
								break;
							}else{
								flag = true;
								saveLoggerInfo(dataJob,operateResult,6,flag);
							}
						} else if (stepBase.getType() == StepTypeEnum.FTP_TO_LOCAL.getIndex()) {// 类型7：从ftp下载的操作
							local = (DataJobStepFtpToLocal) stepBase;
							operateResult = this.checkFtpDownLoad(dataJob,local);
							if(!operateResult.isResult()){
								flag = false;
								saveLoggerInfo(dataJob,operateResult,7,flag);
								sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
								break;
							}else{
								flag = true;
								saveLoggerInfo(dataJob,operateResult,7,flag);
							}
						} else if (stepBase.getType() == StepTypeEnum.DB_TO_XML.getIndex()) {// 类型8：数据库导出XML
							dbToXml = (DataJobStepDbToXml) stepBase;
							operateResult = this.dbToXml(dataJob,dbToXml);
							if(!operateResult.isResult()){
								flag = false;
								saveLoggerInfo(dataJob,operateResult,8,flag);
								sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
								break;
							}else{
								flag = true;
								saveLoggerInfo(dataJob,operateResult,8,flag);
							}
						} else if (stepBase.getType() == StepTypeEnum.XML_TO_DB.getIndex()) {// 类型9：XML导入数据库
//							dbToXml = (DataJobStepDbToXml) stepBase;
//							this.dbToXml(dataJob,dbToXml);
							System.out.println("当前去处理XML导入数据库的操作====》");
						} else if (stepBase.getType() == StepTypeEnum.TXT_TO_DB.getIndex()) {// 类型10：txt的操作
							txtToDb = (DataJobStepTxtToDb) stepBase;
							operateResult = this.txtToDb(dataJob,txtToDb);
							if(!operateResult.isResult()){
								flag = false;
								saveLoggerInfo(dataJob,operateResult,10,flag);
								sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
								break;
							}else{
								flag = true;
								saveLoggerInfo(dataJob,operateResult,10,flag);
							}
						} else if (stepBase.getType() == StepTypeEnum.DB_TO_TXT.getIndex()) {// 类型11：导出txt的操作
							txtBean = (DataJobStepDbToTxt) stepBase;
							operateResult = this.dbToTxt(dataJob,txtBean);
							if(!operateResult.isResult()){
								flag = false;
								saveLoggerInfo(dataJob,operateResult,11,flag);
								sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
								break;
							}else{
								flag = true;
								saveLoggerInfo(dataJob,operateResult,11,flag);
							}
						} else {
							System.out.println("未知操作类型==>处理失败！");
							handleState = false;
							break;
						}
						
					} else {
						handleState = false;
						break;
					}
				}
				jobHandle.dataJobLoggerMongoService.save(logger);
				/*if(log.getResult() == 1){
					// 调度成功之后修改状态 
					dataJob.setStatus(1);  // 1 正在运行   2未运行
					dataJob.setUsed(1); // 1 已使用   0 未使用
					jobHandle.dataJobMongoService.editJobInfo(dataJob);	
				}*/
				getHandleResult(context, handleState);
			}
		} catch (Exception e) {
			e.printStackTrace();
			getHandleResult(context, handleState);
		}
	}
	
	// 日志共通方法
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveLoggerInfo(DataJob dataJob,OperateResult operateResult,int type,boolean flag){
		List resultList = new ArrayList();
		logger.setJobId(dataJob.getJobId());
		logger.setExecutioTime(now.format(new Date()));
		logger.setName(dataJob.getName());
		resultList.add(operateResult);
		logger.setOperateResult(resultList);
		logger.setType(dataJob.getType());
		logger.setRunPeriod(dataJob.getRunPeriod());
		list.clear(); // 赋值之前先清空历史数据
		if(type == 1){
			list.add(stepSql);
		}else if(type == 2 || type == 3){
			list.add(stepToDb);
		}else if(type == 4 || type == 5){
			list.add(stepToEx);
		}else if(type == 6){
			list.add(needStep);
		}else if(type == 7){
			list.add(local);
		}else if(type == 8){
			list.add(dbToXml);
		}else if(type == 9){
			//list.add(stepSql);
		}else if(type == 10){
			list.add(txtToDb);
		}else if(type == 11){
			list.add(txtBean);
		}
		logger.setSteps(list);
		if(flag){
			logger.setResult(1);
			logger.setContent("尊敬的用户您好： "+logger.getName()+"调度:"+"步骤名称为"+logger.getSteps().get(0).getName()+"执行成功, "+operateResult.getMsg());
		}else{
			logger.setResult(0);
			logger.setContent("尊敬的用户您好： "+logger.getName()+"调度:"+"步骤名称为"+logger.getSteps().get(0).getName()+"执行失败, "+operateResult.getMsg());
		}
	}
	
	/**
	 * 说明 ：调度任务失败，给对应联系人发送邮件
	 * @author pxz
	 * @param  DataJobLogger logger
	 */
	public void sendMailToPerson(DataJobLogger logger){
		 // 设置邮件服务器信息
		  MailSenderInfo mailInfo = new MailSenderInfo();
		  mailInfo.setValidate(true);
		  // 收件人邮箱
		  mailInfo.setToAddress("313539982@qq.com");
		  // 邮件标题
		  mailInfo.setSubject("调度任务执行失败提示");
		  // 邮件内容
		  StringBuffer buffer = new StringBuffer();
		  buffer.append(logger.getContent());
		  //  buffer.append("JAF 1.1.1 jar包下载地址：http://www.oracle.com/technetwork/java/javase/downloads/index-135046.html");
		  mailInfo.setContent(buffer.toString());
		  // 发送文体格式
		  try {
			//sms.sendTextMail(mailInfo);
			SimpleMailSender.sendMail(mailInfo.getToAddress(), mailInfo.getSubject(), mailInfo.getContent());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		  Logger.info("邮件发送完毕");
	}
	
	/**
     * 功能说明    : 调度执行sql
     * @author   : peixizhu 
     * @version ：2017年4月26日 
	 */
	public OperateResult checkSteps(DataJob dataJob,DataJobStepSqlScripts stepSql){
		operateResult = jobHandle.dataJobStepSqlScriptsService.excute(stepSql);
		return operateResult;
	} 
	
	/**
     * 功能说明    : 调度执行将excel导入的数据库
     * @author   : peixizhu 
     * @version ：2017年4月27日 
	 */
	public OperateResult exportExcelToMongo(DataJob dataJob, DataJobStepExToDb stepToDb){
		operateResult = jobHandle.exAndDbService.excuteJobStepExToDb(stepToDb);
		return operateResult;
	}
	
	/**
     * 功能说明    : 调度执行txt导入数据库
     * @author : peixizhu 
     * @version ：2017年5月3日 
	 */
	@SuppressWarnings("static-access")
	public OperateResult txtToDb(DataJob dataJob,DataJobStepTxtToDb txtToDb){
		operateResult = jobHandle.dataJobStepTxtToDbService.txtToDb(txtToDb);
		return operateResult;
	}
	
	/**
     * 功能说明    : 调度执行txt导出数据库
     * @author : peixizhu 
     * @version ：2017年5月3日 
	 */
	@SuppressWarnings("static-access")
	public OperateResult dbToTxt(DataJob dataJob,DataJobStepDbToTxt txtBean){
				operateResult = jobHandle.dataJobStepTxtToDbService.dbtoTxt(txtBean);
		return operateResult;
	}
	
	/**
     * 功能说明    : 调度执行excel97/excel2003导出数据库
     * @author   : peixizhu 
     * @version ：2017年4月27日 
	 */
	public OperateResult checkExportExcel97(DataJob dataJob,DataJobStepDbToEx stepToEx){
		operateResult = jobHandle.exAndDbService.excuteJobStepDbToEx(stepToEx);
		return operateResult;
	}
	
	
	/**
     * 功能说明    : 调度执行数据库导出xml
     * @author   : peixizhu 
     * @version ：2017年4月27日 
	 */
	public OperateResult dbToXml(DataJob dataJob,DataJobStepDbToXml dbToXml){
		operateResult = jobHandle.dataJobStepDbToXmlService.dbToXml(dbToXml);
		return operateResult;
	}
	
	/**
     * 功能说明    : 调度执行ftp上传
     * @author   : peixizhu 
     * @version ：2017年4月26日 
	 */
	public OperateResult checkFtpUpLoad(DataJob dataJob,DataJobStepFileToFtp needStep){
		operateResult = jobHandle.fileToFtpService.toStartUpLoadFile(needStep);
		return operateResult;
	}
	/**
     * 功能说明    : 调度执行ftp上传
     * @author   : peixizhu 
     * @version ：2017年4月26日 
	 */
	public OperateResult checkFtpDownLoad(DataJob dataJob,DataJobStepFtpToLocal local){
		operateResult = jobHandle.fileToFtpService.toStartDownLoadFile(local);
		return operateResult;
	}

	/**
	 * 
	   * 功能说明    : 更新处理结果
	   * @author   : fei yang 
	   * @version ：2017年4月18日 上午9:48:36 
	   * @param context
	   * @param handleState
	 */
	public void getHandleResult(JobExecutionContext context, boolean handleState) {
		context.getJobDetail().getJobDataMap().put(Constant.JOB_PARAM_RESULT_KEY, handleState);
	}
}

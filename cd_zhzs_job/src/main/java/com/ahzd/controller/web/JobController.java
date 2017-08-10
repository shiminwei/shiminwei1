package com.ahzd.controller.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.quartz.JobExecutionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ahcd.common.JobConstant;
import com.ahcd.common.OpreateResult;
import com.ahcd.log.Logger;
import com.ahcd.pojo.OperateResult;
import com.ahcd.pojo.Page;
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
import com.ahzd.quartz.DataJobSchuleUtils;
import com.ahzd.quartz.JobHandle;
import com.ahzd.service.DataJobLoggerMongoService;
import com.ahzd.service.DataJobMongoService;
import com.ahzd.service.DataJobStepFileToFtpService;
import com.ahzd.service.DataJobStepSqlScriptsService;
import com.ahzd.service.ExAndDbService;

@Controller  
@RequestMapping("/web/dataJob") 
public class JobController {
	@Resource
	private DataJobMongoService dataJobMongoService;
	@Resource
	private DataJobLoggerMongoService dataJobLoggerMongoService;
	@Resource
	private DataJobStepSqlScriptsService dataJobStepSqlScriptsService;
	@Resource
	private ExAndDbService exAndDbService;
	@Resource
	private DataJobStepFileToFtpService fileToFtpService;
	@Resource
	private DataJobLoggerMongoDao dataJobLoggerMongoDao;
	
	private DataJobLogger logger = new DataJobLogger();
	private SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private JobHandle jobH = new JobHandle(); 
	
	private DataJobStepSqlScripts stepSql = new DataJobStepSqlScripts();
	private DataJobStepFileToFtp needStep = new DataJobStepFileToFtp();
	private DataJobStepFtpToLocal local = new DataJobStepFtpToLocal();
	private DataJobStepExToDb stepToDb = new DataJobStepExToDb();
	private DataJobStepDbToEx stepToEx = new DataJobStepDbToEx();
	private DataJobStepTxtToDb txtToDb = new DataJobStepTxtToDb();
	private DataJobStepDbToTxt txtBean = new DataJobStepDbToTxt();
	private DataJobStepDbToXml dbToXml = new DataJobStepDbToXml();
	/**
	 * @param request model  page  pageNum  numPerPage  bean
	 * @author pxz
	 * @category 获取任务列表    从mongodb获取
	 * @return "/web/dataJob/dataList"
	 */
	@RequestMapping("/list")
	public String getJobList(HttpServletRequest request, Model model, Page<DataJob> page,
			@RequestParam(value ="pageNum", required = false) Integer pageNum ,@RequestParam(value ="numPerPage", required = false) Integer numPerPage,DataJob bean,String order,String typeOrder,String usedOrder,String runOrder){
		Logger.info("★★★★★★★★★★测试开始★★★★★★★★★★");
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		page = dataJobMongoService.getJobPage(page,bean,order,typeOrder,usedOrder,runOrder);
		request.setAttribute("queryBean", bean);
		request.setAttribute("pageList", page);
		model.addAttribute("order", order);
		model.addAttribute("typeOrder", typeOrder);
		model.addAttribute("usedOrder", usedOrder);
		model.addAttribute("runOrder", runOrder);
		return "/web/dataJob/dataList";
	}
	
	/**
	 * @Description: 去新增任务
	 * @ClassName: QuartzManagerFactory
	 * @Copyright: Copyright (c) 2017
	 * @author peixizhu
	 * @date 2017-4-13 
	 */
	@RequestMapping("/toAdd")  
    public String toAdd(HttpServletRequest request,Model model){ 
		request.setAttribute("operateMethod", "add");
		return "web/dataJob/jobEdit";
	}
	
	/**
	 * @Description: 切换任务类型对应弹框
	 * @ClassName: toAjax
	 * @Copyright: Copyright (c) 2017
	 * @author peixizhu
	 * @date 2017-4-13 
	 */
	@RequestMapping("/toAjax")  
    public String toAjax(HttpServletRequest request,Model model){ 
		return "web/dataJob/cron";
	}
	
	/**
	 * @Description: 新增任务
	 * @ClassName: addJob
	 * @Copyright: Copyright (c) 2017
	 * @author peixizhu
	 * @date 2017-4-14 
	 */
	@ResponseBody
	@RequestMapping("/addJob")
	public OpreateResult addJob(HttpServletRequest request,Model model,@ModelAttribute DataJob bean){
		SimpleDateFormat now = new SimpleDateFormat("yyyyMMddHH");
		bean.setStatus(2);// 新增时初始状态为 2   未运行 
		bean.setUsed(0);// 新增时初始状态为0
		bean.setJobId("ahzd"+now.format(new Date())+new Random().nextInt(999999));
		OpreateResult opreateResult = new OpreateResult();
		if(bean.getName() != null && !"".equals(bean.getName())){
			dataJobMongoService.save(bean);
			opreateResult.setStatusCode("200");
			opreateResult.setMessage("操作成功");
			opreateResult.setNavTabId("dataJobList");
		}else{
			opreateResult.setStatusCode("300");
			opreateResult.setMessage("操作失败");
			
		}
		opreateResult.setCallbackType("closeCurrent");
		return opreateResult;
	}
	
	/**
	 * @Description: 调度日志
	 * @ClassName: getScheduleLogList
	 * @Copyright: Copyright (c) 2017
	 * @author peixizhu
	 * @date 2017-5-15 
	 */
	@RequestMapping("log")
	public String getScheduleLogList(HttpServletRequest request,Model model,Page<DataJobLogger> page,
			@RequestParam(value ="pageNum", required = false) Integer pageNum ,@RequestParam(value ="numPerPage", required = false) Integer numPerPage,DataJobLogger dataJob){
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		String name = dataJob.getName();
		if(name != null && name.contains(",")){
			name = name.substring(name.indexOf(",")+1, name.length());
			dataJob.setName(name);
		}
		List<DataJobLogger> resultList = new ArrayList<DataJobLogger>();
		resultList = dataJobLoggerMongoService.find();
		page = dataJobLoggerMongoService.getJobLoggerPage(page,dataJob);
		List<DataJobLogger> newResult = new ArrayList<DataJobLogger>();
	//	resultList = page.getResult();
		for(int i=0;i<resultList.size();i++){
			if(i==0){
				newResult.add(resultList.get(0));
			}else if(!(resultList.get(i-1).getJobId()).equals(resultList.get(i).getJobId())){
				newResult.add(resultList.get(i));
			}
		}
		page.setTotalCount(newResult.size());
		request.setAttribute("newResult", newResult);
		request.setAttribute("dataJobBean", dataJob);
		request.setAttribute("pageList", page);
		return "/web/dataJob/schedulingLog";
	}
	
	/**
	 * @Description: 查看日志
	 * @ClassName: checkJobLogInfo
	 * @Copyright: Copyright (c) 2017
	 * @author peixizhu
	 * @date 2017-4-18 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/checkJobLogInfo")
	public String checkJobLogInfo(HttpServletRequest request,Model model,Page<DataJobLogger> page,
			@RequestParam(value ="pageNum", required = false) Integer pageNum ,@RequestParam(value ="numPerPage", required = false) Integer numPerPage, String jobId){
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		DataJobLogger jobLogger = new DataJobLogger();
		page = dataJobLoggerMongoService.getJobLoggerPageById(page,jobId);
		List resultList = page.getResult();
		if(page.getResult() != null && resultList.size() > 0){
			jobLogger = (DataJobLogger) resultList.get(0);
			jobLogger.setJobId(jobId);
		}
		request.setAttribute("pageList", page);
		request.setAttribute("map", JobConstant.getAllStepTypes()); // 获取枚举中的操作类型
		request.setAttribute("dataJobLogger",jobLogger);
		return "/web/dataJob/dataJobLogInfo";
	}
	
	/**
	 * @Description: 开始调度
	 * @ClassName: startDataJob
	 * @Copyright: Copyright (c) 2017
	 * @author peixizhu
	 * @throws JobExecutionException 
	 * @date 2017-4-18 
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@ResponseBody
	@RequestMapping("/startDataJob")
	public OpreateResult startDataJob(HttpServletRequest request,String jobId){
		DataJob dataJob = new DataJob();
		DataJob dataJob2 = new DataJob();//本对象供调度成功后修改参数使用的
		OpreateResult opreateResult = new OpreateResult();
//		try {
//			name=new String(name.getBytes("iso-8859-1"),"utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} 
		OperateResult op = new OperateResult();
		dataJob = dataJobMongoService.findOne(jobId);
//		dataJob = dataJobMongoService.findByName(name);
		if(dataJob.getUsed()==0){
			opreateResult.setStatusCode("300");
			opreateResult.setMessage("未使用状态不能进行调度任务");
			opreateResult.setNavTabId("dataJobList");
			return opreateResult;
		}
		List<DataJobStepBase> jobStepBases = (List<DataJobStepBase>) dataJob.getSteps();
		if(jobStepBases == null||jobStepBases.equals("")||jobStepBases.size()==0){
			opreateResult.setStatusCode("300");
			opreateResult.setMessage("无执行步骤，没有任何方法。");
			opreateResult.setNavTabId("dataJobList");
			return opreateResult;
		}
		if("0".equals(dataJob.getRunPeriod())){
			op = executionNow(dataJob);
			if(op.isResult()){
				dataJob2.setStatus(4);  // 1 正在运行   2未运行  3暂停运行 4运行结束
				dataJob2.setUsed(1);
				dataJobMongoService.editJobInfo(dataJob2);
				opreateResult.setStatusCode("200");
				opreateResult.setMessage("任务已执行完毕，运行结束!");
				opreateResult.setNavTabId("dataJobList");
				return opreateResult;
			}else{
				Logger.info("添加任务异常");
				opreateResult.setStatusCode("300");
				opreateResult.setMessage(op.getMsg());
				opreateResult.setNavTabId("dataJobList");
				return opreateResult;
			}
		}else{
			// 创建定时任务
			Boolean boo = DataJobSchuleUtils.toCreateScheduleJobByDataJob(dataJob);
			Logger.info("添加定时任务  "+boo);
			if(boo == true){
				// 创建成功，则开始执行定时任务
				DataJobSchuleUtils.toStartScheduleJob();
				//dataJob2.set_id(jobId);
				dataJob2.setStatus(1);
				dataJob2.setUsed(1);
				dataJobMongoService.editJobInfo(dataJob2);
				opreateResult.setStatusCode("200");
				opreateResult.setMessage("调度成功");
				opreateResult.setNavTabId("dataJobList");
				return opreateResult;
			}else{
				Logger.info("添加任务异常");
				opreateResult.setStatusCode("300");
				opreateResult.setMessage("调度失败");
				opreateResult.setNavTabId("dataJobList");
				return opreateResult;
			}
		}
	}
	
	// 日志共通方法
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void saveLoggerInfo(DataJob dataJob,OperateResult operateResult,int type,boolean flag){
			List resultList = new ArrayList();
			List list =new ArrayList();
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
				logger.setContent("尊敬的用户您好： "+logger.getName()+"调度:"+"步骤名称为"+logger.getSteps().get(0).getName()+"执行成功,"+operateResult.getMsg());
			}else{
				logger.setResult(0);
				logger.setContent("尊敬的用户您好： "+logger.getName()+"调度:"+"步骤名称为"+logger.getSteps().get(0).getName()+"执行失败,"+operateResult.getMsg());
			}
		}
	
	/**
	 * @category 立刻执行调度
	 * @param dataJob
	 * @return operateResult
	 * @author pxz
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public OperateResult executionNow(DataJob dataJob){
		List listStep = new ArrayList();
		boolean flag = false;
		OperateResult operateResult = new OperateResult();
		List<DataJobStepBase> jobStepBases = (List<DataJobStepBase>) dataJob.getSteps();
		if(jobStepBases != null){
			for (int i = 0; i < jobStepBases.size(); i++) {
				DataJobStepBase stepBase = jobStepBases.get(i);
				if(stepBase.getType() == 1){
					stepSql = (DataJobStepSqlScripts) stepBase;
					operateResult = jobH.checkSteps(dataJob,stepSql);
					if(!operateResult.isResult()){
						flag = false;
						saveLoggerInfo(dataJob,operateResult,1,flag);
						jobH.sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
						break;
					}else{
						flag = true;
						saveLoggerInfo(dataJob,operateResult,1,flag);
					}
				}else if(stepBase.getType() == 2){
					stepToDb = (DataJobStepExToDb) stepBase;
					operateResult = jobH.exportExcelToMongo(dataJob,stepToDb);
					if(!operateResult.isResult()){
						flag = false;
						saveLoggerInfo(dataJob,operateResult,2,flag);
						jobH.sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
						break;
					}else{
						flag = true;
						saveLoggerInfo(dataJob,operateResult,2,flag);
					}
				}else if(stepBase.getType() == 3){
					stepToDb = (DataJobStepExToDb) stepBase;
					operateResult = jobH.exportExcelToMongo(dataJob,stepToDb);
					if(!operateResult.isResult()){
						flag = false;
						saveLoggerInfo(dataJob,operateResult,3,flag);
						jobH.sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
						break;
					}else{
						flag = true;
						saveLoggerInfo(dataJob,operateResult,3,flag);
					}
				}else if(stepBase.getType() == 4){
					stepToEx = (DataJobStepDbToEx) stepBase;
					operateResult = jobH.checkExportExcel97(dataJob,stepToEx);
					if(!operateResult.isResult()){
						flag = false;
						saveLoggerInfo(dataJob,operateResult,4,flag);
						jobH.sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
						break;
					}else{
						flag = true;
						saveLoggerInfo(dataJob,operateResult,4,flag);
					}
				}else if(stepBase.getType() == 5){
					stepToEx = (DataJobStepDbToEx) stepBase;
					operateResult = jobH.checkExportExcel97(dataJob,stepToEx);
					if(!operateResult.isResult()){
						flag = false;
						saveLoggerInfo(dataJob,operateResult,5,flag);
						jobH.sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
						break;
					}else{
						flag = true;
						saveLoggerInfo(dataJob,operateResult,5,flag);
					}
				}else if(stepBase.getType() == 6){
					needStep = (DataJobStepFileToFtp) stepBase;
					operateResult = jobH.checkFtpUpLoad(dataJob,needStep);
					if(!operateResult.isResult()){
						flag = false;
						saveLoggerInfo(dataJob,operateResult,6,flag);
						jobH.sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
						break;
					}else{
						flag = true;
						saveLoggerInfo(dataJob,operateResult,6,flag);
					}
				}else if(stepBase.getType() == 7){
					local = (DataJobStepFtpToLocal) stepBase;
					operateResult = jobH.checkFtpDownLoad(dataJob,local);
					if(!operateResult.isResult()){
						flag = false;
						saveLoggerInfo(dataJob,operateResult,7,flag);
						jobH.sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
						break;
					}else{
						flag = true;
						saveLoggerInfo(dataJob,operateResult,7,flag);
					}
				}else if(stepBase.getType() == 8){
					dbToXml = (DataJobStepDbToXml) stepBase;
					operateResult = jobH.dbToXml(dataJob,dbToXml);
					if(!operateResult.isResult()){
						flag = false;
						saveLoggerInfo(dataJob,operateResult,8,flag);
						jobH.sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
						break;
					}else{
						flag = true;
						saveLoggerInfo(dataJob,operateResult,8,flag);
					}
				}else if(stepBase.getType() == 9){
					dbToXml = (DataJobStepDbToXml) stepBase;
					operateResult = jobH.dbToXml(dataJob,dbToXml);
					listStep.add(local);
				}else if(stepBase.getType() == 10){
					txtToDb =  (DataJobStepTxtToDb) stepBase;
					operateResult = jobH.txtToDb(dataJob,txtToDb);
					if(!operateResult.isResult()){
						flag = false;
						saveLoggerInfo(dataJob,operateResult,10,flag);
						jobH.sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
						break;
					}else{
						flag = true;
						saveLoggerInfo(dataJob,operateResult,10,flag);
					}
				}else if(stepBase.getType() == 11){
					txtBean =  (DataJobStepDbToTxt) stepBase;
					operateResult = jobH.dbToTxt(dataJob,txtBean);
					if(!operateResult.isResult()){
						flag = false;
						saveLoggerInfo(dataJob,operateResult,11,flag);
						jobH.sendMailToPerson(logger); // 这里是任务执行失败后发送邮件给联系人
						break;
					}else{
						flag = true;
						saveLoggerInfo(dataJob,operateResult,11,flag);
					}
				}
			}
			dataJobLoggerMongoDao.save(logger);
//			if(log.getResult() == 1){
//				// 调度成功之后修改状态 
//				dataJob.setStatus(4);  // 1 正在运行   2未运行  3暂停运行 4运行结束
//				dataJob.setUsed(1);
//				dataJobMongoService.editJobInfo(dataJob);
//				operateResult.setMsg("任务已执行完毕，运行结束!");
//			}
		}else{
			operateResult.setMsg("没有执行步骤，请检查调度任务。");
		}
		return operateResult;
	}
	
	/**
	 * 功能说明:停止运行
	 * @param request
	 * @param _id
	 * @return
	 * @throws JobExecutionException
	 */
	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping("/stopDataJob")
	public OpreateResult stopDataJob(HttpServletRequest request,String jobId){
		OpreateResult opreateResult = new OpreateResult();
		DataJob dataJob = dataJobMongoService.findOne(jobId);
		if(!dataJob.getRunPeriod().equals("0")){
			boolean flag = DataJobSchuleUtils.toPauseByDataJob(dataJob);
			if(flag){
				DataJob dataJob2 = new DataJob();
				dataJob2.setJobId(jobId);
				dataJob2.setStatus(3);
				dataJob2.setUsed(1);
				dataJobMongoService.editJobInfo(dataJob2);
				opreateResult.setStatusCode("200");
				opreateResult.setMessage("操作成功，已停止运行");
				return opreateResult;
			}
		}else{
			if(jobId!=null&&!"".equals(jobId)){
				DataJob dataJob2 = new DataJob();
				dataJob2.setJobId(jobId);
				dataJob2.setStatus(3);
				dataJob2.setUsed(1);
				dataJobMongoService.editJobInfo(dataJob2);
				opreateResult.setStatusCode("200");
				opreateResult.setMessage("操作成功，已停止运行");
				return opreateResult;
			}
		}
		opreateResult.setStatusCode("300");
		opreateResult.setMessage("操作失败");
		return opreateResult;
	}
	
	
	/**
	 * 功能说明:恢复运行
	 * @param request
	 * @param _id
	 * @return
	 * @throws JobExecutionException
	 */
	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping("/resumeDataJob")
	public OpreateResult resumeDataJob(HttpServletRequest request,String jobId){
		OpreateResult opreateResult = new OpreateResult();
		DataJob dataJob = dataJobMongoService.findOne(jobId);
		if(!dataJob.getRunPeriod().equals("0")){
			boolean flag = DataJobSchuleUtils.resumeByDataJob(dataJob);
			if(flag){
				DataJob dataJob2 = new DataJob();
				dataJob2.setJobId(jobId);
				dataJob2.setStatus(3);
				dataJob2.setUsed(1);
				dataJobMongoService.editJobInfo(dataJob2);
				opreateResult.setStatusCode("200");
				opreateResult.setMessage("操作成功，已恢复运行");
				return opreateResult;
			}
		}else{
			if(jobId!=null&&!"".equals(jobId)){
				DataJob dataJob2 = new DataJob();
				dataJob2.setJobId(jobId);
				dataJob2.setStatus(3);
				dataJob2.setUsed(1);
				dataJobMongoService.editJobInfo(dataJob2);
				opreateResult.setStatusCode("200");
				opreateResult.setMessage("操作成功，已恢复运行");
				return opreateResult;
			}
		}
		opreateResult.setStatusCode("300");
		opreateResult.setMessage("操作失败");
		return opreateResult;
	}
	
	/**
	 * 功能说明:销毁运行
	 * @param request
	 * @param _id
	 * @return
	 * @throws JobExecutionException
	 */
	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping("/deleteDataJob")
	public OpreateResult deleteDataJob(HttpServletRequest request,String jobId){
		OpreateResult opreateResult = new OpreateResult();
		DataJob dataJob = dataJobMongoService.findOne(jobId);
		if(!dataJob.getRunPeriod().equals("0")){
			boolean flag = DataJobSchuleUtils.deleteByDataJob(dataJob);
			if(flag){
				DataJob dataJob2 = new DataJob();
				dataJob2.setJobId(jobId);
				dataJob2.setStatus(2);
				dataJob2.setUsed(1);
				dataJobMongoService.editJobInfo(dataJob2);
				opreateResult.setStatusCode("200");
				opreateResult.setMessage("操作成功，已停止运行");
				return opreateResult;
			}
		}else{
			if(jobId!=null&&!"".equals(jobId)){
				DataJob dataJob2 = new DataJob();
				dataJob2.setJobId(jobId);
				dataJob2.setStatus(2);
				dataJob2.setUsed(1);
				dataJobMongoService.editJobInfo(dataJob2);
				opreateResult.setStatusCode("200");
				opreateResult.setMessage("操作成功，已停止运行");
				return opreateResult;
			}
		}
		opreateResult.setStatusCode("300");
		opreateResult.setMessage("操作失败");
		return opreateResult;
	}
	
	/**
	 * 功能说明:将使用状态切换
	 * @param request
	 * @param used
	 * @param jobId
	 * @param status
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping("/changeUsedOrNot")
	public OpreateResult changeUsedOrNot(HttpServletRequest request,String used,String jobId,String status){
		OpreateResult opreateResult = new OpreateResult();
		int numStatus = Integer.valueOf(status);
		int numUsed = Integer.valueOf(used);
		if(numStatus==1){
			opreateResult.setStatusCode("300");
			opreateResult.setMessage("正在运行，无法切换状态，请先停止运行");
			return opreateResult;
		}
		DataJob dataJob  = new DataJob();
		dataJob.setJobId(jobId);
		dataJob.setStatus(2);
		if(numUsed==1){
			dataJob.setUsed(0);
			DataJob dataJob2 = dataJobMongoService.findOne(jobId);
			DataJobSchuleUtils.deleteByDataJob(dataJob2);
		}else{
			dataJob.setUsed(1);
		}
		dataJobMongoService.editJobInfo(dataJob);
		opreateResult.setStatusCode("200");
		opreateResult.setMessage("操作成功");
		return opreateResult;
	}
}

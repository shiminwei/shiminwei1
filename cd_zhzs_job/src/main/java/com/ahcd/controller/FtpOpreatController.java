package com.ahcd.controller;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ahcd.common.FileUtil;
import com.ahcd.common.JobConstant;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StepTypeEnum;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.OperateResult;
import com.ahzd.dao.DataJobMongoDao;
import com.ahzd.pojo.DataJob;
import com.ahzd.pojo.DataJobStepBase;
import com.ahzd.pojo.DataJobStepFileToFtp;
import com.ahzd.pojo.DataJobStepFtpToLocal;
import com.ahzd.service.DataJobStepExToDbService;
import com.ahzd.service.DataJobStepFileToFtpService;
import com.ahzd.service.DataJobStepMongoService;
import com.ahzd.service.DataSourceMongoService;
@Controller  
@RequestMapping("/web/ftp")  
public class FtpOpreatController {
	@Resource
	private DataSourceMongoService dataSourceMongoService;
	@Resource
	private DataJobStepMongoService dataJobStepMongoService;
	@Resource
	private DataJobStepFileToFtpService fileToFtpService;
	@Autowired                                          
	private DataJobMongoDao dataJobMongoDao;
	@Autowired
	private  DataJobStepExToDbService dataJobStepExToDbService;
	/**
	 * 跳转到上传FTP页面
	 * 页面请求路口：/web/ftp/toFtpUpload?id=${}
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/toFtpUpload")
	public String toFtpUpload(HttpServletRequest request, Model model,String jobId,String stepId) {
		if(stepId != null && !StringUtil.isBlank(stepId)){
			Query query = new Query();
			query.addCriteria(Criteria.where("jobId").is(jobId));
			DataJob job = dataJobMongoDao.findOne(query);
			List<DataJobStepBase> dlist = (List<DataJobStepBase>) job.getSteps();
			DataJobStepFileToFtp needStep = new DataJobStepFileToFtp();
			for (int i = 0; i < dlist.size(); i++) {
				if(dlist.get(i).getStepId().equals(stepId)){
					needStep = (DataJobStepFileToFtp) dlist.get(i);
				}
			}
			String filePath = needStep.getFilePath();
			String name = FileUtil.getNamePart(filePath);
			request.setAttribute("name", name);
			request.setAttribute("needStep", needStep);
			request.setAttribute("stepId", stepId);
			request.setAttribute("sortNum", needStep.getSortNum());
		}
		LinkedHashMap<String,String> map = JobConstant.fileNamePatterns();
		request.setAttribute("jobId", jobId);
		request.setAttribute("fileNamePatternsMap", map);
		return "web/step/fileToFtp/importFtp";
	}
	
	/**
	 * 跳转到从FTP下载页面
	 * 页面请求路口：/web/ftp/toFtpDownLoad?id=${}
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/toFtpDownLoad")
	public String toFtpDownLoad(HttpServletRequest request, Model model,String jobId,String stepId) {
		if(stepId != null && !StringUtil.isBlank(stepId)){
			DataJobStepFtpToLocal needStep = dataJobStepExToDbService.selectJobStep(jobId, stepId, DataJobStepFtpToLocal.class);
			String localPath = needStep.getLocalPath();
			String name = FileUtil.getNamePart(localPath);
			request.setAttribute("name", name);
			request.setAttribute("needStep", needStep);
			request.setAttribute("method",stepId);
			request.setAttribute("stepId", stepId);
			request.setAttribute("sortNum", needStep.getSortNum());
		}
		LinkedHashMap<String,String> map = JobConstant.fileNamePatterns();
		request.setAttribute("id", jobId);
		request.setAttribute("fileNamePatternsMap", map);
		return "web/step/ftpToLocal/exportFtp";
	}
	
	
	/**
	 * 根据stepId保存到step(上传)
	 * 方法请求路径:/web/ftp/saveToMongoUpload?id=${id}
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveToMongoUpload")
	public OpreateResult saveToMongoUpload(HttpServletRequest request,DataJobStepFileToFtp dbFtp,String jobId,String stepId,String sortNum) {
		int a;
		dbFtp.setType(StepTypeEnum.FILE_TO_FTP.getIndex());
		if(stepId == null || StringUtil.isBlank(stepId)){
			dbFtp.setFilePath(JobConstant.getFileSavePath(jobId, dbFtp.getFilePath(), dbFtp.getFileNamePatterns()));
		}
		if(StringUtil.isBlank(dbFtp.getStepId())){
			dbFtp.setStepId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		OpreateResult opreateResult = new OpreateResult();
		if(stepId !=null && !StringUtil.isBlank(stepId)){
			//获取文件的原始名称
			String oldName = dataJobStepExToDbService.getOldName(dbFtp.getFilePath(), jobId, stepId, DataJobStepFileToFtp.class);
			dbFtp.setFilePath(JobConstant.getFileSavePath(jobId,oldName, dbFtp.getFileNamePatterns()));
			dbFtp.setStepId(stepId);
			dbFtp.setSortNum(Integer.valueOf(sortNum));
			a = fileToFtpService.updateJobStep(dbFtp, jobId);
			opreateResult.setStatusCode("200");
			opreateResult.setMessage("修改成功!");
			opreateResult.setNavTabId("jobsteplist");
			opreateResult.setCallbackType("closeCurrent");
			return opreateResult;
		}else{
			a = dataJobStepMongoService.addJobStep(dbFtp, jobId);
		}
		if(a==1){
			opreateResult.setStatusCode("200");
			opreateResult.setMessage("新增成功!");
			opreateResult.setNavTabId("jobsteplist");
			opreateResult.setCallbackType("closeCurrent");
		}else{
			opreateResult.setStatusCode("300");
			opreateResult.setMessage("操作失败!");
		}
		return opreateResult;
	}
	
	
	/**
	 * 根据stepId保存到step(下载)
	 * 方法请求路径:/web/ftp/saveToMongoDownLoad?id=${id} 
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveToMongoDownLoad")
	public OpreateResult saveToMongoDownLoad(HttpServletRequest request,DataJobStepFtpToLocal dbFtp,String jobId,String stepId,String sortNum) {
		int a;
		dbFtp.setType(StepTypeEnum.FTP_TO_LOCAL.getIndex());
		if(stepId == null || StringUtil.isBlank(stepId)){
			dbFtp.setLocalPath(JobConstant.getFileSavePath(jobId,dbFtp.getLocalPath(), dbFtp.getFileNamePatterns()));
		}
		if(StringUtil.isBlank(dbFtp.getStepId())){
			dbFtp.setStepId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		OpreateResult opreateResult = new OpreateResult();
		if(stepId !=null && !StringUtil.isBlank(stepId)){
			//获取文件的原始名称
			String oldName = dataJobStepExToDbService.getOldNameTow(dbFtp.getLocalPath(), jobId, stepId, DataJobStepFtpToLocal.class);
			dbFtp.setLocalPath(JobConstant.getFileSavePath(jobId,oldName, dbFtp.getFileNamePatterns()));
			dbFtp.setStepId(stepId);
			dbFtp.setSortNum(Integer.valueOf(sortNum));
			a = fileToFtpService.updateJobStepDownLoad(dbFtp, jobId);
			opreateResult.setStatusCode("200");
			opreateResult.setMessage("修改成功!");
			opreateResult.setNavTabId("jobsteplist");
			opreateResult.setCallbackType("closeCurrent");
			return opreateResult;
		}else{
			a = dataJobStepMongoService.addJobStep(dbFtp, jobId);
		}
		if(a==1){
			opreateResult.setStatusCode("200");
			opreateResult.setMessage("新增成功");
			opreateResult.setNavTabId("jobsteplist");
			opreateResult.setCallbackType("closeCurrent");
		}else{
			opreateResult.setStatusCode("300");
			opreateResult.setMessage("新增失敗");
		}
		return opreateResult;
	}
	
	/**
	 * 文件上传至ftp方法
	 * 方法请求路径:/web/ftp/uploadFileToFtp?id=${id}&stepId=${}
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping("/uploadFileToFtp")
	public OperateResult uploadFileToFtp(HttpServletRequest request,String id,String stepId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		DataJob job = dataJobMongoDao.findOne(query);
		List<DataJobStepBase> dlist = (List<DataJobStepBase>) job.getSteps();
		DataJobStepFileToFtp needStep = new DataJobStepFileToFtp();
		for (int i = 0; i < dlist.size(); i++) {
			if(dlist.get(i).getStepId().equals(stepId)){
				needStep = (DataJobStepFileToFtp) dlist.get(i);
			}
		}
		return fileToFtpService.toStartUpLoadFile(needStep);//传入step对象
	}
	
	/**
	 * 从ftp下载文件
	 * * 方法请求路径:/web/ftp/downFileFromFtp?id=${}&stepId=${}
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping("/downFileFromFtp")
	public OperateResult downFileFromFtp(HttpServletRequest request,String id,String stepId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		DataJob job = dataJobMongoDao.findOne(query);
		List<DataJobStepBase> dlist = (List<DataJobStepBase>) job.getSteps();
		DataJobStepFtpToLocal needStep = new DataJobStepFtpToLocal();
		for (int i = 0; i < dlist.size(); i++) {
			if(dlist.get(i).getStepId().equals(stepId)){
				needStep = (DataJobStepFtpToLocal) dlist.get(i);
			}
		}
		return fileToFtpService.toStartDownLoadFile(needStep);//传入step对象
	}
}

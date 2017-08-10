package com.ahcd.controller;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ahcd.common.DBconn;
import com.ahcd.common.FileUtil;
import com.ahcd.common.JobConstant;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.ReadTxt;
import com.ahcd.common.StepTypeEnum;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.DatasourceBean;
import com.ahzd.pojo.DataJobStepDbToTxt;
import com.ahzd.pojo.DataJobStepTxtToDb;
import com.ahzd.service.DataJobStepExToDbService;
import com.ahzd.service.DataJobStepMongoService;
import com.ahzd.service.DataJobStepTxtToDbService;
import com.ahzd.service.DataSourceMongoService;
import com.alibaba.fastjson.JSON;

@Controller  
@RequestMapping("/web/txt")  
public class TxtOpreatController {
	@Resource
	private DataSourceMongoService dataSourceMongoService;
	@Resource
	private DataJobStepMongoService dataJobStepMongoService;
	@Autowired
	private  DataJobStepExToDbService dataJobStepExToDbService;
	@Autowired
	private  DataJobStepTxtToDbService TxtToDbService;
	
	/**
	 * 功能说明:跳转到新增txt文本导入数据库步骤页面
	 * 页面请求路口：/web/txt/toTxtToDb?jobId=${jobId}
	 * @param request
	 * @param model
	 * @param jobId
	 * @return
	 */
	@RequestMapping("/toTxtToDb")
	public String toTxtToDb(HttpServletRequest request, Model model,String jobId,String stepId) {
		if(stepId != null && !StringUtil.isBlank(stepId)){
			DataJobStepTxtToDb needStep = dataJobStepExToDbService.selectJobStep(jobId, stepId, DataJobStepTxtToDb.class);
			String filePath = needStep.getFilePath();
			String name = FileUtil.getNamePart(filePath);
			List<String> list = new ArrayList<String>();
			Map<String,String> parmaMap = needStep.getParamMap();
			Set<String> keys = parmaMap.keySet();
			for (String key : keys) {
				list.add(parmaMap.get(key));
			}
			request.setAttribute("txtCols", list);
			request.setAttribute("name", name);
			request.setAttribute("needStep", needStep);
			request.setAttribute("stepId", stepId);
			request.setAttribute("sortNum", needStep.getSortNum());
		}
		String type = request.getParameter("type");
		List<DatasourceBean> list = dataSourceMongoService.getAllDatasource();
		LinkedHashMap<String,String> map = JobConstant.fileNamePatterns();
		request.setAttribute("fileNamePatternsMap", map);
		model.addAttribute("datasourceList", list);
		model.addAttribute("type", type);
		model.addAttribute("jobId",jobId);
		return "web/step/txtToDb/importTxt";
	}
	
	/**
	 * 功能说明:跳转到新增数据库导出到记事本步骤页面
	 * 页面请求路口：/web/txt/toDbToTxt?jobId=${jobId}
	 * @param request
	 * @param model
	 * @param jobId
	 * @return
	 */
	@RequestMapping("/toDbToTxt")
	public String toDbToTxt(HttpServletRequest request, Model model,String jobId,String stepId) {
		if(stepId != null && !StringUtil.isBlank(stepId)){
			DataJobStepDbToTxt needStep = dataJobStepExToDbService.selectJobStep(jobId, stepId, DataJobStepDbToTxt.class);
			String filePath = needStep.getFilePath();
			String name = FileUtil.getNamePart(filePath);
			List<String> list = new ArrayList<String>();
			Map<String,String> parmaMap = needStep.getParamMap();
			Set<String> keys = parmaMap.keySet();
			for (String key : keys) {
				list.add(parmaMap.get(key));
			}
			request.setAttribute("txtCols", list);
			request.setAttribute("name", name);
			request.setAttribute("needStep", needStep);
			request.setAttribute("stepId", stepId);
			request.setAttribute("sortNum", needStep.getSortNum());
		}
		String type = request.getParameter("type");
		List<DatasourceBean> list = dataSourceMongoService.getAllDatasource();
		LinkedHashMap<String,String> map = JobConstant.fileNamePatterns();
		request.setAttribute("fileNamePatternsMap", map);
		model.addAttribute("datasourceList", list);
		model.addAttribute("type", type);
		model.addAttribute("jobId",jobId);
		return "web/step/dbToTxt/exportTxt";
	}
	
	/**
	 * 
	 *功能说明:获得txt中的所有列名
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping("/getTxtCols")
	public void getTxtCols(@RequestParam(value="file",required=false) MultipartFile file,HttpServletRequest request, HttpServletResponse response,String separator) throws IOException{
		String[] head = {};
		try {
			InputStream in =file.getInputStream();
			head = ReadTxt.getHead(in,ReadTxt.checkSep(separator));
		} catch (IOException e) {
			e.printStackTrace();
		}
        String jsonString = JSON.toJSONString(head);
	    response.setHeader("content-type", "text/html;charset=UTF-8");
		response.getWriter().print(jsonString);
	}
	
	/**
	 * 功能说明   txt信息存储到MongoDB中
	 * 方法请求路径:/web/txt/txtSaveToMongo?jobId=${jobId}
	 * @param request
	 * @param response
	 * @param path
	 * @param fileNamePatterns
	 * @param dbTxt
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/txtSaveToMongo")
	public OpreateResult txtSaveToMongo(HttpServletRequest request, DataJobStepTxtToDb dbTxt,String jobId,String stepId,String sortNum){
		String beginRow = request.getParameter("beginRow"); 
		String endRow = request.getParameter("endRow");
		String fileNamePatterns = request.getParameter("fileNamePatterns");
		if(dbTxt.getStepId()==null || StringUtil.isBlank(dbTxt.getStepId())){
			dbTxt.setStepId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		String path = request.getParameter("path");
		int begin,end;
		if(beginRow == ""||StringUtil.isBlank(beginRow)){
			begin = 2; 
		}else{
			begin =Integer.parseInt(beginRow);
		}
		if(endRow == ""||StringUtil.isBlank(endRow)){
			end = 999999999;
		}else{
			end =Integer.parseInt(endRow);
		}
		dbTxt.setBeginRowIndex(begin);
		dbTxt.setEndRowIndex(end);
		if(stepId!=null && !StringUtil.isBlank(stepId)){
			DataJobStepTxtToDb needStep = dataJobStepExToDbService.selectJobStep(jobId, stepId, DataJobStepTxtToDb.class);
			int num = needStep.getFileNamePatterns().length()-2;
			if(num == -2){
				dbTxt.setFilePath(JobConstant.getFileSavePath(jobId,path, fileNamePatterns));
			}
			String old[] = path.split("\\.");
			String end1 = old[old.length-1];
			int sort = old[0].length();
			String oldName = old[0].substring(0, sort-num)+"."+end1;
			dbTxt.setFilePath(JobConstant.getFileSavePath(jobId, oldName, fileNamePatterns));
			dbTxt.setSortNum(Integer.valueOf(sortNum));
			dbTxt.setType(StepTypeEnum.TXT_TO_DB.getIndex());
			if(dbTxt.getTableName()==null||StringUtil.isBlank(dbTxt.getTableName())){
				dbTxt.setTableName(needStep.getTableName());
			}
		}else{
			dbTxt.setFilePath(JobConstant.getFileSavePath(jobId, path, fileNamePatterns));
			dbTxt.setType(StepTypeEnum.TXT_TO_DB.getIndex());
		}
		String key = request.getParameter("hideKey");
		String value = request.getParameter("hideValue");
		String[] strKey = key.split(",");
		String[] strValue = value.split(",");
		Map<String,String> paramMap = new HashMap<String,String>();
		for (int i = 0; i < strKey.length; i++) {
			System.out.println(strKey[i]);
			paramMap.put(strKey[i], strValue[i]);
		}
		dbTxt.setParamMap(paramMap);
		int a = 0;
		if(stepId!=null && !StringUtil.isBlank(stepId)){
			a = TxtToDbService.updateJobStepTxtUpload(dbTxt, jobId);
		}else{
			a = dataJobStepMongoService.addJobStep(dbTxt, jobId);
		}
		OpreateResult opreateResult = new OpreateResult();
		if(a==1){
			opreateResult.setStatusCode("200");
			opreateResult.setMessage("操作成功!");
			opreateResult.setNavTabId("jobsteplist");
			opreateResult.setCallbackType("closeCurrent");
		}else{
			opreateResult.setStatusCode("300");
			opreateResult.setMessage("操作失败!");
		}
		return opreateResult;
	}
	public static void main(String[] args) {
		Connection con = DBconn.getConnectionById("1");
		System.out.println(con);
	}
	
	/**
	 * 功能说明   数据库导出到txt信息存储到MongoDB中
	 * 方法请求路径:/web/txt/txtSaveToMongo?jobId=${jobId}
	 * @param request
	 * @param response
	 * @param path
	 * @param fileNamePatterns
	 * @param dbTxt
	 * @return
	 */
	@ResponseBody
	@RequestMapping("dbToTxtSaveToMongo")
	public OpreateResult dbToTxtSaveToMongo(HttpServletRequest request,DataJobStepDbToTxt dbTxt,String path,
			String fileNamePatterns,String jobId,String stepId,String sortNum){
		if(stepId!=null && !StringUtil.isBlank(stepId)){
			DataJobStepDbToTxt needStep = dataJobStepExToDbService.selectJobStep(jobId, stepId, DataJobStepDbToTxt.class);
			int num = needStep.getFileNamePatterns().length()-2;
			if(num == -2){
				dbTxt.setFilePath(JobConstant.getFileSavePath(jobId,path, fileNamePatterns));
			}
			String old[] = path.split("\\.");
			String end1 = old[old.length-1];
			int sort = old[0].length();
			String oldName = old[0].substring(0, sort-num)+"."+end1;
			dbTxt.setFilePath(JobConstant.getFileSavePath(jobId, oldName, fileNamePatterns));
			dbTxt.setSortNum(Integer.valueOf(sortNum));
			dbTxt.setType(StepTypeEnum.DB_TO_TXT.getIndex());
		}else{
			dbTxt.setFilePath(JobConstant.getFileSavePath(jobId, path, fileNamePatterns));
			dbTxt.setType(StepTypeEnum.DB_TO_TXT.getIndex());
		}
		if(dbTxt.getStepId()==null || StringUtil.isBlank(dbTxt.getStepId())){
			dbTxt.setStepId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		String beginRow = request.getParameter("beginRow"); 
		int begin;
		if(beginRow == ""||StringUtil.isBlank(beginRow)){
			begin = 2; 
		}else{
			begin =Integer.parseInt(beginRow);
		}
		dbTxt.setBeginRowIndex(begin);
		String cols = request.getParameter("hideValue");
		String[] str = cols.split(",");
		Map<String,String> paramMap = new  LinkedHashMap<String,String>();
		List<String> key = new ArrayList<String>();
		List<String> value = new ArrayList<String>();
		for (int i = 0; i <str.length ; i++) {
			if(i%2==0){
				key.add(str[i]);
			}else{
				value.add(str[i]);
			}
		}
		for (int i = 0; i < key.size(); i++) {
			paramMap.put(key.get(i), value.get(i));
		}
		dbTxt.setParamMap(paramMap);
		OpreateResult opreateResult = new OpreateResult();
		int a;
		if(stepId!=null && !StringUtil.isBlank(stepId)){
			a = TxtToDbService.updateJobStepTxtDownLoad(dbTxt, jobId);
		}else{
			a = dataJobStepMongoService.addJobStep(dbTxt, jobId);
		}
		if(a==1){
			opreateResult.setStatusCode("200");
			opreateResult.setMessage("操作成功!");
			opreateResult.setNavTabId("jobsteplist");
			opreateResult.setCallbackType("closeCurrent");
		}else{
			opreateResult.setStatusCode("300");
			opreateResult.setMessage("操作失敗!");
		}
		return opreateResult;
	}
}











package com.ahcd.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ahcd.common.JobConstant;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.Page;
import com.ahzd.pojo.DataJobStepBase;
import com.ahzd.service.DataJobStepMongoService;
import com.ahzd.service.DataSourceMongoService;
import com.ahzd.service.ExAndDbService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Controller  
@RequestMapping("/web/jobstep")  
public class DataJobStepController {
	
	@Resource  
	private DataJobStepMongoService dataJobStepMongoService;
	@Resource
	private DataSourceMongoService dataSourceMongoService;
	@Resource
	private ExAndDbService exToDbService;
	
	/**
	 * 功能说明 :步骤--查询列表
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Page<DataJobStepBase> page, DataJobStepBase bean,String jobId) {
//		if("".equals(jobId) || jobId ==null){
//			//从任务列表传入
//			jobId =  request.getParameter("jobId");
//		}
		if(jobId.contains(",")){
			String [] jobIds = jobId.split(",");
			jobId = jobIds[0];
		}
		page.setQueryBean(bean);
		page = dataJobStepMongoService.selectJobSteps(page,jobId);
		request.setAttribute("map", JobConstant.getAllStepTypes());
		request.setAttribute("editmap", getToEditMethod());
		request.setAttribute("excutemap", getToExcuteMethod());
		request.setAttribute("pageList", page);
		request.setAttribute("bean", bean);
		request.setAttribute("jobId", jobId);
		return "/web/jobstep/list";
	}

	/**
	 * 功能说明 :步骤--删除
	 */
	@ResponseBody
	@RequestMapping("/toScriptDelete")
	public OpreateResult toScriptDelete(HttpServletRequest request,String jobId,String stepId,String sortNum) {
		OpreateResult opreateResult = new OpreateResult("200", "删除成功!", "", "", "");
		int ret = dataJobStepMongoService.deleteJobStep(jobId, stepId ,Integer.parseInt(sortNum));
		if(ret < 0){
			opreateResult.setStatusCode("300");
			opreateResult.setMessage("删除失败!");
			return opreateResult;
		}
		return opreateResult;
	}
		
	/**
	 * 功能说明 :步骤--上/下移动
	 */
	@ResponseBody
	@RequestMapping("/upOrDown")
	public OpreateResult upOrDown(HttpServletRequest request,String jobId,String stepid1,String stepid2,
			String stepSort1,String stepSort2 ,String type){
		OpreateResult opreateResult = new OpreateResult();
		if(stepid2 == null || "".equals(stepid2) || "undefined".equals(stepid2)){
			opreateResult.setStatusCode("300");
			if(!StringUtil.isEmpty(type) && "1".equals(type)){
				opreateResult.setMessage("已经是第一个了!");
			}else{
				opreateResult.setMessage("已经是最后一个了!");
			}
			return opreateResult;
		}
		int ret = dataJobStepMongoService.updateStepSort(jobId, stepid1, Integer.parseInt(stepSort2));
		if(ret < 0){
			return new OpreateResult("300", "操作失败!", "", "", "");
		}
		ret = dataJobStepMongoService.updateStepSort(jobId, stepid2, Integer.parseInt(stepSort1));
		if(ret < 0){
			return new OpreateResult("300", "操作失败!", "", "", "");
		}
		opreateResult = new OpreateResult("200", "操作成功!", "", "", "");
		return opreateResult;	
	}
	
	/**
	 * 功能说明 :步骤--选择类型
	 */
	@RequestMapping("/toType")
	public String selecttype(HttpServletRequest request, Model model,DataJobStepBase bean,String jobId) {
		request.setAttribute("bean", bean);
		request.setAttribute("jobId", jobId);
		request.setAttribute("map", JobConstant.getAllStepTypes());
		return "/web/jobstep/type";
	}
	
	/**
	 * 功能说明 :步骤--下一步
	 */
	@RequestMapping("/next")
	public void next(HttpServletRequest request,HttpServletResponse response, Model model,DataJobStepBase bean,String jobId) {
		OpreateResult op = new OpreateResult();	
		String path = request.getContextPath();
	    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
        op.setStatusCode("200");
		op.setMessage("跳转至"+JobConstant.getAllStepTypes().get(bean.getType())+"页面");
		op.setForwardUrl(basePath+"/web"+getToAddMethod().get(bean.getType())+"?type="+bean.getType()+"&jobId="+jobId);
		op.setCallbackType("forward");
		response.setHeader("content-type", "text/html;charset=UTF-8");
        OutputStream out;
		try {
			out = response.getOutputStream();
			out.write(JSON.toJSONString(op,SerializerFeature.WriteMapNullValue).getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 根据[操作类型]获得去新增页面
	 * */
	private Map<Integer,String> getToAddMethod(){
		Map<Integer,String> map = new HashMap<Integer,String>();
		map.put(1, "/sqlScripts/toScript");
		map.put(2, "/excel/toImportEx");
		map.put(3, "/excel/toImportEx");
		map.put(4, "/excel/toExportEx");
		map.put(5, "/excel/toExportEx");
		map.put(6, "/ftp/toFtpUpload");
		map.put(7, "/ftp/toFtpDownLoad");
		map.put(8, "/xml/export/toAddOrEdit");
		map.put(9, "/xml/import/toAddOrEdit");
		map.put(10, "/txt/toTxtToDb");
		map.put(11, "/txt/toDbToTxt");
		return map;
	}
	/** 根据[操作类型]获得去编辑页面
	 * */
	private Map<Integer,String> getToEditMethod(){
		Map<Integer,String> map = new HashMap<Integer,String>();
		map.put(1, "/sqlScripts/toScriptEdit");
		map.put(2, "/excel/toEditImportEx");
		map.put(3, "/excel/toEditImportEx");
		map.put(4, "/excel/toEditExportEx");
		map.put(5, "/excel/toEditExportEx");
		map.put(6, "/ftp/toFtpUpload");
		map.put(7, "/ftp/toFtpDownLoad");
		map.put(8, "/xml/export/toAddOrEdit");
		map.put(9, "/xml/import/toAddOrEdit");
		map.put(10, "/txt/toTxtToDb");
		map.put(11, "/txt/toDbToTxt");

		return map;
	}
	/** 根据[操作类型]获得去执行
	 * */
	private Map<Integer,String> getToExcuteMethod(){
		Map<Integer,String> map = new HashMap<Integer,String>();
		map.put(1, "/sqlScripts/excuteSqlScripts");
		return map;
	}
}

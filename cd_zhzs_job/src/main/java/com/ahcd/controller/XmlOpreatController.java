package com.ahcd.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ahcd.common.JobConstant;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahzd.pojo.DataJobStepDbToXml;
import com.ahzd.pojo.DataJobStepXmlToDb;
import com.ahzd.service.DataJobStepDbToXmlService;
import com.ahzd.service.DataJobStepMongoService;
import com.ahzd.service.DataJobStepXmlToDbService;
import com.ahzd.service.DataSourceMongoService;

@Controller
@RequestMapping("web/xml")
public class XmlOpreatController {

	@Resource
	private DataSourceMongoService dataSourceMongoService;

	@Resource
	private DataJobStepMongoService dataJobStepMongoService;

	@Resource
	private DataJobStepDbToXmlService dataJobStepDbToXmlService;
	
	@Resource
	private DataJobStepXmlToDbService dataJobStepXmlToDbService;

	/**
	 * 
	 * 功能说明 : 根据不同数据源的表数据导出Xml格式数据
	 * 
	 * @author : fei yang
	 * @version ：2017年5月3日 上午10:39:26
	 * @param request
	 * @param model
	 * @param jobId   任务ID
	 * @param stepId 步骤ID
	 * @param type  操作类型
	 * @return
	 */
	@RequestMapping("export/toAddOrEdit")
	public String exportXml(HttpServletRequest request, Model model, String jobId, String stepId,Integer type) {
		request.setAttribute("fileNamePatternsMap", JobConstant.fileNamePatterns());
		model.addAttribute("datasourceList", dataSourceMongoService.getAllDatasource());
		model.addAttribute("jobId", jobId);
		if (StringUtil.isNotEmpty(stepId)) {
			DataJobStepDbToXml bean = dataJobStepDbToXmlService.selectJobStep(jobId, stepId, DataJobStepDbToXml.class);
			model.addAttribute("bean", bean);
			type=bean.getType();
		}
		model.addAttribute("type", type);
		return "web/step/dbToXml/addOrEdit";
	}

	/**
	 * 
	 * 功能说明 : 新增或者修改步骤（据源的表数据导出Xml格式数据）
	 * 
	 * @author : fei yang
	 * @version ：2017年5月3日 下午3:26:24
	 * @param request
	 * @param response
	 * @param jobId    任务ID
	 * @param dataJobStepDbToXml    数据源导出XML实体
	 * @return
	 */
	@ResponseBody
	@RequestMapping("export/addOrEdit")
	public OpreateResult addOrEditExportXmlToMongo(HttpServletRequest request, String jobId,
			DataJobStepDbToXml dataJobStepDbToXml) {
		return dataJobStepDbToXmlService.addOrEditExportXmlToMongo(jobId, dataJobStepDbToXml);
	}

	
	/**
	 * 
	   * 功能说明    : 跳转步骤（XML导入数据库）
	   * @author   : fei yang 
	   * @version ：2017年5月5日 上午10:09:18 
	   * @param request
	   * @param model
	   * @param jobId
	   * @param bean
	   * @param stepId
	   * @return
	 */
	@RequestMapping("import/toAddOrEdit")
	public String importXml(HttpServletRequest request, Model model, String jobId, String stepId,Integer type) {
		request.setAttribute("fileNamePatternsMap", JobConstant.fileNamePatterns());
		model.addAttribute("datasourceList", dataSourceMongoService.getAllDatasource());
		model.addAttribute("jobId", jobId);
		if (StringUtil.isNotEmpty(stepId)) {
			DataJobStepXmlToDb bean = dataJobStepXmlToDbService.selectJobStep(jobId, stepId, DataJobStepXmlToDb.class);
			model.addAttribute("bean", bean);
			type=bean.getType();
		}
		model.addAttribute("type", type);
		return "web/step/xmlToDb/addOrEdit";
	}

	
	/**
	 * 
	   * 功能说明    :新增或者修改步骤（XML导入数据库） 
	   * @author   : fei yang 
	   * @version ：2017年5月5日 上午10:09:57 
	   * @param request
	   * @param jobId
	   * @param dataJobStepXmlToDb
	   * @return
	 */
	@ResponseBody
	@RequestMapping("import/addOrEdit")
	public OpreateResult addOrEditImportXmlToMongo(HttpServletRequest request, String jobId,
			DataJobStepXmlToDb dataJobStepXmlToDb) {
		return dataJobStepXmlToDbService.addOrEditImportXmlToMongo(jobId, dataJobStepXmlToDb);
	}
	
	
}

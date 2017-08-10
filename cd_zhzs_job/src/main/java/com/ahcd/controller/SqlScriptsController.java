package com.ahcd.controller;

import java.util.HashMap;
import java.util.List;
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
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.OperateResult;
import com.ahzd.pojo.DataJobStepSqlScripts;
import com.ahzd.service.DataJobStepMongoService;
import com.ahzd.service.DataJobStepSqlScriptsService;
import com.ahzd.service.DataSourceMongoService;

@Controller  
@RequestMapping("/web/sqlScripts")  
public class SqlScriptsController {
	@Resource
	DataJobStepMongoService dataJobStepMongoService;
	@Resource  
	private DataJobStepSqlScriptsService dataJobStepSqlScriptsService;
	@Resource
	private DataSourceMongoService dataSourceMongoService;
	
	/**
	 * 功能说明 :SQL脚本--toADD
	 */
	@RequestMapping("/toScript")
	public String toScript(HttpServletRequest request, Model model,DataJobStepSqlScripts bean,String jobId) {
		// 获得所有的数据源mongoDB
		List<DatasourceBean> datasourceList = dataSourceMongoService.getAllDatasource();
		request.setAttribute("datasourceList", datasourceList);
		request.setAttribute("bean", bean);
		request.setAttribute("jobId",request.getParameter("jobId"));
		request.setAttribute("type", request.getParameter("type"));
		request.setAttribute("map", JobConstant.getAllStepTypes());
		return "/web/jobstep/scriptAdd";
	}
	
	/**
	 * 功能说明 :SQL脚本--toEDIT
	 */
	@RequestMapping("/toScriptEdit")
	public <T> String toScriptEdit(HttpServletRequest request, Model model,String jobId,String stepId) {
		@SuppressWarnings("unchecked")
		DataJobStepSqlScripts bean = (DataJobStepSqlScripts) dataJobStepSqlScriptsService.selectJobStep(jobId,stepId,(Class<T>) DataJobStepSqlScripts.class);
		// 获得所有的数据源mongoDB
		List<DatasourceBean> datasourceList = dataSourceMongoService.getAllDatasource();
		request.setAttribute("datasourceList", datasourceList);
		request.setAttribute("map", JobConstant.getAllStepTypes());
		request.setAttribute("bean", bean);
		request.setAttribute("jobId", jobId);
		return "/web/jobstep/scriptEdit";
	}
	
	/**
	 * 功能说明 :SQL脚本--SAVEorUPDATE
	 */
	@ResponseBody
	@RequestMapping("/saveOrUpdate")
	public OpreateResult saveOrUpdate(HttpServletRequest request,HttpServletResponse response, Model model,DataJobStepSqlScripts bean,String jobId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		String[] sql = bean.getSql().split("@");
		for(int i=0 ;i< sql.length ;i++){
			if(i%2 == 1){
				paramMap.put("@"+sql[i]+"@", request.getParameter("@"+sql[i]+"@"));
			}
		}
		bean.setParamMap(paramMap);
		int ret = 0 ;
		String aOUpdate = request.getParameter("aOUpdate");
		if(!"".equals(aOUpdate) && "0".equals(aOUpdate)){ 
			ret = dataJobStepMongoService.addJobStep(bean,jobId);
		}else{
			ret = dataJobStepSqlScriptsService.updateJobStep(bean, jobId);
		}
		if(ret < 0){
			return new OpreateResult("300", "操作失败!", "", "", "");
		}
		return new OpreateResult("200", "操作成功!", "jobsteplist", "closeCurrent", "");
	}
	
	/**
	 * 功能说明 :SQL脚本--执行excuteSqlScripts 
	 */
	@ResponseBody
	@RequestMapping("/excuteSqlScripts")
	public OpreateResult excuteJobStepSqlScripts(HttpServletRequest request,String jobId,String stepId) {
		OpreateResult opreateResult = new OpreateResult("200", "执行成功!", "", "", "");
		OperateResult result = dataJobStepSqlScriptsService.excute(jobId, stepId);
		if(result == null || !result.isResult()){
			opreateResult.setStatusCode("300");
			if(result != null && !"".equals(result.getMsg())){
				opreateResult.setMessage(result.getMsg());
			}else{
				opreateResult.setMessage("执行失败！请检查SQL语句和参数");
			}	
			return opreateResult;
		}
		return opreateResult;
	}
		
	/**
	 * 功能说明 :SQL脚本--checkSQL 
	 * */
	@ResponseBody
	@RequestMapping("toCheckSql")
	public void toCheckSql(HttpServletResponse response, HttpServletRequest request,DataJobStepSqlScripts bean) {
		try {
			String result = "当前输入的SQL存在问题，请检查SQL语句！";
			String sql = bean.getSql();
			if(sql != null && !"".equals(sql) && sql.split(";").length > 0){
				result = dataJobStepSqlScriptsService.checkSql(bean);
			}
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
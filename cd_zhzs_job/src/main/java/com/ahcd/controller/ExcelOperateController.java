package com.ahcd.controller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ahcd.common.DBconn;
import com.ahcd.common.DbUntil;
import com.ahcd.common.ExcelUtils;
import com.ahcd.common.FileUtil;
import com.ahcd.common.JobConstant;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.ReadExcel;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.DataTableBean;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.ExcelBean;
import com.ahcd.pojo.ExcelColumnBean;
import com.ahcd.pojo.ExcelRowBean;
import com.ahcd.pojo.ExcelSheetBean;
import com.ahcd.pojo.Page;
import com.ahzd.pojo.DataJob;
import com.ahzd.pojo.DataJobStepBase;
import com.ahzd.pojo.DataJobStepDbToEx;
import com.ahzd.pojo.DataJobStepExToDb;
import com.ahzd.service.DataJobStepDbToExService;
import com.ahzd.service.DataJobStepExToDbService;
import com.ahzd.service.DataJobStepMongoService;
import com.ahzd.service.DataSourceMongoService;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/web/excel") 
public class ExcelOperateController {

	@Resource
	private DataSourceMongoService dataSourceMongoService;
	@Resource
	private DataJobStepMongoService dataJobStepMongoService;
	@Autowired
	private  DataJobStepExToDbService dataJobStepExToDbService;
	@Autowired
	private DataJobStepDbToExService dataJobStepDbToExService;
	
	/**
	 * 功能说明:跳转到将excel导入到DB文件步骤页面
	 * @param request
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/toImportEx")
	public String importEx(HttpServletRequest request, Model model,String jobId,String stepId) {
		String type = request.getParameter("type");
		List<DatasourceBean> list = dataSourceMongoService.getAllDatasource();
		LinkedHashMap<String,String> map = JobConstant.fileNamePatterns();
		request.setAttribute("fileNamePatternsMap", map);
		model.addAttribute("datasourceList", list);
		model.addAttribute("type", type);
		model.addAttribute("jobId",jobId);
		model.addAttribute("method","add");
		return "web/step/exToDb/addOrEdit";
	}
	/**
	 * 
	 * 功能说明:跳转到DB导出Excel文件步骤页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/toExportEx")
	public String exportEx(HttpServletRequest request, Model model,String jobId,String stepId) {
		String type = request.getParameter("type");
		List<DatasourceBean> list = dataSourceMongoService.getAllDatasource();
		LinkedHashMap<String,String> map = JobConstant.fileNamePatterns();
		request.setAttribute("fileNamePatternsMap", map);
		model.addAttribute("datasourceList", list);
		model.addAttribute("type", type);
		model.addAttribute("jobId",jobId);
		model.addAttribute("method","add");
		return "web/step/dbToEx/addOrEdit";
	}
	/**
	 * 
	 * 功能说明:跳转到Excel导入到DB的页面
	 * 
	 */
	
	@RequestMapping("/toEditImportEx")
	public String toEditImportEx(HttpServletRequest request, Model model,String jobId,String stepId){
		DataJobStepExToDb bean = dataJobStepExToDbService.selectJobStep(jobId, stepId, DataJobStepExToDb.class);
		// 获得所有的数据源mongoDB
		List<DatasourceBean> datasourceList = dataSourceMongoService.getAllDatasource();
		LinkedHashMap<String,String> map = JobConstant.fileNamePatterns();
		String filePath = bean.getFilePath();
		Integer sheetIndex = bean.getSheetIndex();
		String filename = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
		if(sheetIndex==null){
			sheetIndex=0;
		}
		bean.setFilePath(filename);
		List<String> list = new ArrayList<String>();
		Map<String,String> parmaMap = bean.getParamMap();
		Set<String> keys = parmaMap.keySet();
		for (String key : keys) {
			list.add(parmaMap.get(key));
		}
		request.setAttribute("excelCols", list);
		request.setAttribute("fileNamePatternsMap", map);
		request.setAttribute("datasourceList", datasourceList);
		request.setAttribute("map", JobConstant.getAllStepTypes());
		request.setAttribute("bean", bean);
		request.setAttribute("jobId", jobId);
		request.setAttribute("stepId", bean.getStepId());
		return "web/step/exToDb/addOrEdit";
	}
	/**
	 * 
	 * 功能说明:跳转到DB导入到excel的页面
	 * 
	 */
	@RequestMapping("/toEditExportEx")
	public String toEditExportEX(HttpServletRequest request, Model model,String jobId,String stepId){
		DataJobStepDbToEx bean = dataJobStepExToDbService.selectJobStep(jobId, stepId, DataJobStepDbToEx.class);
		// 获得所有的数据源mongoDB
		List<DatasourceBean> datasourceList = dataSourceMongoService.getAllDatasource();
		LinkedHashMap<String,String> map = JobConstant.fileNamePatterns();
		String filePath = bean.getFilePath();
		String filename = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
		bean.setFilePath(filename);
		request.setAttribute("fileNamePatternsMap", map);
		request.setAttribute("datasourceList", datasourceList);
		request.setAttribute("map", JobConstant.getAllStepTypes());
		request.setAttribute("bean", bean);
		request.setAttribute("jobId", jobId);
		request.setAttribute("stepId", bean.getStepId());
		return "web/step/dbToEx/addOrEdit";
	}
	/**
	 * 
	 * 功能说明：将导入Excel==》DB信息存储到MongoDB中
	 * @param request
	 * @param response
	 * @param dataJob
	 * @param exDb
	 * @param base
	 * @param step
	 * @return
	 */
	@ResponseBody
	@RequestMapping("importEx/saveImportToMongo")
	public OpreateResult saveImportToMongo(HttpServletRequest request,HttpServletResponse response,DataJob dataJob,DataJobStepExToDb  exDb,DataJobStepBase base,String jobId,String method){
		String fileNamePatterns = request.getParameter("fileNamePatterns");
		String path = request.getParameter("path");
		String key = request.getParameter("hideKey");
		String value = request.getParameter("hideValue");
		String[] strKey = key.split(",");
		String[] strValue = value.split(",");
		 Map<String,String> paramMap = new LinkedHashMap<String,String>();
		for (int i = 0; i < strKey.length; i++) {
			paramMap.put(strKey[i], strValue[i]);
		}
		int result=0;
		String filePath = "";
		String stepId= exDb.getStepId();
		exDb.setParamMap(paramMap);
		if(StringUtil.isBlank(stepId)){
			exDb.setStepId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		
		if(method.equals("add")){
			filePath = JobConstant.getFileSavePath(jobId, path, fileNamePatterns);
			exDb.setFilePath(filePath);
			result = dataJobStepMongoService.addJobStep(exDb, jobId);
		}else{
			String name = FileUtil.getNamePart(path);
			DataJobStepExToDb needStep = dataJobStepExToDbService.selectJobStep(jobId, stepId, DataJobStepExToDb.class);
			String pattern = needStep.getFileNamePatterns();
			if(pattern==null||pattern.equals("")){
				filePath = JobConstant.getFileSavePath(jobId, path, fileNamePatterns);
			}else{
				int num = needStep.getFileNamePatterns().length()-2;
				String old[] = name.split("\\.");
				String end = old[old.length-1];
				int sort = old[0].length();
				String newName = old[0].substring(0, sort-num)+"."+end;
				filePath=JobConstant.getFileSavePath(jobId, newName, fileNamePatterns);
			}
			exDb.setFilePath(filePath);
			result = dataJobStepExToDbService.updateJobStep(exDb, jobId);
		}
		OpreateResult opreateResult = new OpreateResult();
		if(result==1){
			opreateResult.setStatusCode("200");
			opreateResult.setMessage("操作成功");
			opreateResult.setNavTabId("jobsteplist");
			opreateResult.setCallbackType("closeCurrent");
		}else{
			opreateResult.setStatusCode("300");
			opreateResult.setMessage("操作失敗");
		}
		return opreateResult;
		
	}
	
	
	/**
	 * 功能说明：将导入DB==》Excel信息存储到MongoDB中
	 * @param request
	 * @param response
	 * @param path
	 * @param fileNamePatterns
	 * @param dbEx
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/exportEx/saveExportToMongo")
	public OpreateResult saveExportToMongo(HttpServletRequest request,HttpServletResponse response,String path ,String fileNamePatterns,DataJobStepDbToEx  dbEx,String jobId,String stepId,String method){
		String cols = request.getParameter("hideValue");
		String[] str = cols.split(",");
		Map<String,String> paramMap = new  LinkedHashMap<String,String>();
		List<String> key = new ArrayList<String>();
		List<String> value = new ArrayList<String>();
		//将DB字段与数据库的字段映射成Map
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
		if(StringUtil.isBlank(stepId)){
			dbEx.setStepId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		String filePath="";
		int result =0 ;
		dbEx.setParamMap(paramMap);
		if(method.equals("add")){
			filePath = JobConstant.getFileSavePath(jobId, path, fileNamePatterns);
			dbEx.setFilePath(filePath);
			result = dataJobStepMongoService.addJobStep(dbEx, jobId);
		}else{
			String name = FileUtil.getNamePart(path);
			DataJobStepDbToEx needStep = dataJobStepExToDbService.selectJobStep(jobId, stepId, DataJobStepDbToEx.class);
			String pattern = needStep.getFileNamePatterns();
			if(pattern==null||pattern.equals("")){
				filePath = JobConstant.getFileSavePath(jobId, path, fileNamePatterns);
			}else{
				int num = needStep.getFileNamePatterns().length()-2;
				String old[] = name.split("\\.");
				String end = old[old.length-1];
				int sort = old[0].length();
				String newName = old[0].substring(0, sort-num)+"."+end;
				filePath = JobConstant.getFileSavePath(jobId, newName, fileNamePatterns);
			}
			dbEx.setFilePath(filePath);
			result = dataJobStepDbToExService.updateJobStep(dbEx, jobId);
		}
		OpreateResult opreateResult = new OpreateResult();
		if(result==1){
			opreateResult.setStatusCode("200");
			opreateResult.setMessage("操作成功");
			opreateResult.setNavTabId("jobsteplist");
			opreateResult.setCallbackType("closeCurrent");
		}else{
			opreateResult.setStatusCode("300");
			opreateResult.setMessage("操作失敗");
		}
		return opreateResult;
		
	}
	/**
	 * 
	 * 功能说明:获得当前数据源下面的所有表
	 * @param request
	 * @param model
	 * @param dataSourceId
	 * @param pageList
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/importEx/lookList")
	public String getLookList(HttpServletRequest request, Model model,String datasourceId,Page<DataTableBean> pageList,DatasourceBean bean){
		pageList.setNumPerPage(10);
		String tableName = request.getParameter("name");
		if (StringUtil.isNotEmpty(datasourceId)) {
			DatasourceBean datasourceBean = dataSourceMongoService.findById(datasourceId);
			pageList = DbUntil.getAllTableByData(pageList, datasourceBean.getId(), datasourceBean.getType(),datasourceBean.getDatabase(), tableName);
			pageList.setPageNum(pageList.getPageNum());
			pageList.setNumPerPage(pageList.getNumPerPage());			
		}
		model.addAttribute("pageList", pageList);
		model.addAttribute("tableName", tableName);
		model.addAttribute("datasourceId", datasourceId);
		return "web/step/exToDb/lookList";
	}
	
	/**
	 * 
	 * 功能说明:获得数据库中选中表的所有字段名称
	 * @param request
	 * @param response
	 * @param mode
	 * @param table_name
	 * @param dataSourceId
	 * @throws IOException
	 */
	@RequestMapping("/importEx/getCol")
	public void getCol(HttpServletRequest request, HttpServletResponse response,Model mode,String table_name,String datasourceId){
		List<LinkedHashMap<String, Object>> datas =  DbUntil.getAllCols(datasourceId,table_name);
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			if(datas==null)
				map.put("flag", "2");
			else
				map.put("flag", "1");
			map.put("datas", datas);
			String jsonString = JSON.toJSONString(map);
			response.getWriter().print(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 *功能说明:获得Excel表格中的所有列名
	 * @param file
	 * @param request
	 * @param response
	 * @param sheetIndex
	 * @throws Exception
	 */
	@RequestMapping("importEx/getExcelCols")
	public void getExcelCols(@RequestParam(value="file",required=false) MultipartFile file,HttpServletRequest request,HttpServletResponse response,String sheetIndex){
		try {
			int sheet;
			List<String> list = new ArrayList<String>();
			InputStream in = file.getInputStream();
			String filename = file.getOriginalFilename(); 
			ExcelBean bean = ExcelUtils.readExcelByIs(in,filename);
			List<ExcelSheetBean> listob = null;  
	        listob = bean.getExcelSheetBeans();
	        List<ExcelColumnBean>  columns = null;
	        if(!StringUtil.isEmpty(sheetIndex)){
	        	//用户输入了需要导出的sheetIndex
	        	sheet=Integer.parseInt(sheetIndex);
	        	ExcelRowBean excelbean = listob.get(sheet).getTableHeader();
	        	columns = excelbean.getExcelColumnBeans();
	        	for (int j = 0; j < columns.size(); j++) {
	    			String cols = columns.get(j).getContent().toString();
	    			list.add(cols);
	    		}
	        }else{
	        	//未输入即要导出所有的sheet
	    		ExcelRowBean excelbean = listob.get(0).getTableHeader();
	    		columns = excelbean.getExcelColumnBeans();
	    		for (int j = 0; j < columns.size(); j++) {
	    			String cols = columns.get(j).getContent().toString();
	    			list.add(cols);
	        	}
	        }
	        String jsonString = JSON.toJSONString(list);
		    response.setHeader("content-type", "text/html;charset=UTF-8");
		    response.getWriter().print(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * 功能说明:DB==》Excel获得数据库中表对应的字段
	 * @param request
	 * @param response
	 * @param model
	 * @param sqlStr
	 * @param dataSourceId
	 * @throws IOException
	 */
	@RequestMapping("/exportEx/getDbCol")
	public void getDbCol(HttpServletRequest request,HttpServletResponse response, Model model,String sqlStr,String datasourceId){
		List<String> list = new ArrayList<String>();
		int num =0;
		Connection conn = DBconn.getConnectionById(datasourceId);
		PreparedStatement ps = null;
		ResultSet rs = null;
        try {
        	ps = conn.prepareStatement(sqlStr);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();//ResultSet 对象的列的编号、类型和属性
			rsmd.getColumnName(1);
			num = rsmd.getColumnCount();
			 for (int j = 1; j <= num; j++) {
				 list.add(rsmd.getColumnName(j));//输出和显示的指定列的建议标题	
			}	
		} catch (SQLException e) {
			list = null;
		}
        Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(list==null)
				map.put("flag", "2");
			else
				map.put("flag", "1");
			map.put("list", list);
			String jsonString = JSON.toJSONString(map);
			response.setHeader("content-type", "text/html;charset=UTF-8");
			response.getWriter().print(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 功能说明：下载Excel模板
	 * @param request
	 * @param response
	 * @param jobId
	 * @param stepId
	 * @throws Exception
	 */
	@RequestMapping("toDownload")
	public void toDownload(HttpServletRequest request,HttpServletResponse response,String jobId,String stepId){
		DataJobStepDbToEx bean = dataJobStepExToDbService.selectJobStep(jobId, stepId, DataJobStepDbToEx.class);
		Map<String,String> paramMap = bean.getParamMap();
		Set<String> keys = paramMap.keySet();
		List<String> list = new ArrayList<String>();
		for (String key : keys) {
			String value = paramMap.get(key);
			list.add(value);
		}
		try {
			HSSFWorkbook wb = ReadExcel.toWriteWorkbook(list, "yyyy-YY-dd");
			if (wb != null) {
				response.reset();
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 设定输出文件头
				response.setHeader("Content-Disposition", "attachment; filename=" + new String(bean.getName().getBytes("gbk"), "iso-8859-1") + ".xls");
				OutputStream out= response.getOutputStream();
				wb.write(out);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

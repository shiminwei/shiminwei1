package com.ahcd.controller.admin;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ahcd.common.Constant;
import com.ahcd.common.DateUtil;
import com.ahcd.common.ImportExcelUtil;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.PingYinUtil;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.ExcelTemplateCol;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentTemplate;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.IJsonConfigService;
import com.ahcd.service.ISysDepartmentTemplateService;
import com.ahzd.service.ExcelTemplateMongoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Controller	
@RequestMapping("/admin/template")
public class TemplateManageController {
	@Resource  
	private IJsonConfigService jsonConfigService; 
	
	@Resource  
	private ISysDepartmentTemplateService departmentTemplateService; 
	
	@Resource  
	private ExcelTemplateMongoService excelTemplateMongoService; 
	
	
	@RequestMapping("/list")  
    public String list(HttpServletRequest request,Model model,@ModelAttribute("excelTemplate")ExcelTemplate excelTemplate,
    		@RequestParam(value ="pageNum", required = false) Integer pageNum ,@RequestParam(value ="numPerPage", required = false) Integer numPerPage){ 
		Page<ExcelTemplate> page=new Page<ExcelTemplate>();
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		//page=jsonConfigService.getExcelTemplatePage(page, excelTemplate);
		page=excelTemplateMongoService.getExcelTemplatePage(page, excelTemplate);
		Map<Integer,String> colType=Constant.colType();
		//Map<Integer,String> periodType=Constant.periodType();
		request.setAttribute("colType", colType);
		//request.setAttribute("periodType", periodType);
		request.setAttribute("pageList", page);
		request.setAttribute("excelTemplate", excelTemplate);
		return "admin/template/list";
	}
	
	
	@RequestMapping("/toAdd")  
    public String toAdd(HttpServletRequest request,Model model){ 
		request.setAttribute("templateTablePrefix", Constant.templateTablePrefix);
		
		return "admin/template/add";
	}
	
	@RequestMapping(value="/add", produces = "application/json; charset=utf-8")  
    public void add(HttpServletRequest  request,Model model,HttpSession session, HttpServletResponse response,MultipartFile uploadExcel){ 
		OpreateResult op = new OpreateResult();
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
	        String tableName=request.getParameter("tableName");
	        String fileName = uploadExcel.getOriginalFilename();
	        //boolean isExist=jsonConfigService.checkExistByTableName(Constant.templateTablePrefix+tableName);
	        boolean isExist=excelTemplateMongoService.checkExistByTableName(Constant.templateTablePrefix+tableName);
	        
	        if(isExist){
	        	op.setStatusCode("300");
				op.setMessage("上传失败，数据库表名已存在");
	        }else{
	        	InputStream in =null;
		        List<List<Object>> listob = null;  
		        MultipartFile file = multipartRequest.getFile("uploadExcel");  
		        if(file!=null && file.isEmpty()){  
		            throw new Exception("文件不存在！");  
		        }  
		        in = file.getInputStream();  
		        listob = new ImportExcelUtil().getBankListByExcel(in,file.getOriginalFilename());  
		        in.close();
		        if(listob!=null && listob.size()>0){
		        	List<Object> cols=listob.get(0);
		        	session.setAttribute(tableName, cols);
		        }
		        String path = request.getContextPath();
			    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		        op.setStatusCode("200");
				op.setMessage("上传成功");
				op.setForwardUrl(basePath+"/admin/template/toAddDetail?tableName="+tableName+"&fileName="+fileName);
				op.setCallbackType("forward");
	        }
	        response.setHeader("content-type", "text/html;charset=UTF-8");
	        OutputStream out = response.getOutputStream();
	        out.write(JSON.toJSONString(op,SerializerFeature.WriteMapNullValue).getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	@RequestMapping("/toAddDetail")  
    public String toAddDetail(HttpServletRequest request,Model model,HttpSession session){ 
		List<String> datasourceList=jsonConfigService.getAllDatasourceId();
		request.setAttribute("datasourceList", datasourceList);
		String tableName=request.getParameter("tableName");
		String fileName = request.getParameter("fileName");
//		String fileType = fileName.substring(fileName.lastIndexOf("."));
		String templateName = fileName.substring(0, fileName.lastIndexOf("."));
		List<Object>  cols=(List<Object>) session.getAttribute(tableName);
		List<String> colNames=new ArrayList<String>();
		if (cols!=null) {
			colNames=PingYinUtil.getFirstSpellByList(cols);
		}
		Map<Integer,String> colType=Constant.colType();
		//Map<Integer,String> periodType=Constant.periodType();
		request.setAttribute("colType", colType);
		//request.setAttribute("periodType", periodType);
		request.setAttribute("cols", cols);
		request.setAttribute("colNames", colNames);
		request.setAttribute("tableName", tableName);
		request.setAttribute("templateName", templateName);
		return "admin/template/addDetail";
	}
	
	@ResponseBody
	@RequestMapping("/addDetail")  
    public OpreateResult addDetail(HttpServletRequest request,Model model,HttpSession session){ 
		SysReportUser u=(SysReportUser) session.getAttribute(Constant.SESSION_USER);
		String tableName=request.getParameter("tableName");
		String datasource=request.getParameter("datasource");
		String templateName=request.getParameter("templateName1");
		//String reportPeriod=request.getParameter("reportPeriod");
		String reportPassDate=request.getParameter("reportPassDate");
		String idRule=request.getParameter("idRule");
		String start=request.getParameter("start");
		String end=request.getParameter("end");
		String templateDesc=request.getParameter("templateDesc");
		String[] excelColName=request.getParameterValues("excelColName");
		//String[] colName=request.getParameterValues("colName");
		String[] colType=request.getParameterValues("colType");
		//String[] colLength=request.getParameterValues("colLength");
		String[] isMain=request.getParameterValues("isMain");
		ExcelTemplate excelTemplate=new ExcelTemplate();
		excelTemplate.setTableName(Constant.templateTablePrefix+tableName);
		excelTemplate.setDatasource(datasource);
		excelTemplate.setFileType("excel");
		excelTemplate.setName(templateName);
		//excelTemplate.setZq(Integer.parseInt(reportPeriod));
		excelTemplate.setZqgqsj(Integer.parseInt(reportPassDate));
		excelTemplate.setIdcode(idRule);
		excelTemplate.setDesc(templateDesc);
		excelTemplate.setUser(u.getUserName());
		if(!StringUtil.isBlank(start)){
			excelTemplate.setStart(Integer.parseInt(start));
		}
		if(!StringUtil.isBlank(end)){
			excelTemplate.setEnd(Integer.parseInt(end));
		}
		List<ExcelTemplateCol> cols=new ArrayList<ExcelTemplateCol>();
		if(excelColName!=null  && colType!=null && isMain!=null &&
				excelColName.length==colType.length
				&& colType.length==isMain.length
				){
			for(int i=0;i<excelColName.length;i++){
				String colName=request.getParameter("colName"+i);
				String colLength=request.getParameter("colLength"+i);
				ExcelTemplateCol excelTemplateCol=new ExcelTemplateCol();
				excelTemplateCol.setColOrder(i);
				excelTemplateCol.setName(excelColName[i]);
				excelTemplateCol.setColumnName(colName);
				excelTemplateCol.setIsMain(Integer.parseInt(isMain[i]));
				excelTemplateCol.setColType(Integer.parseInt(colType[i]));
				if(!StringUtil.isBlank(colLength)){
					excelTemplateCol.setColLength(Integer.parseInt(colLength));
				}
				cols.add(excelTemplateCol);
			}
		}
		excelTemplate.setCols(cols);
		
		//OpreateResult op=jsonConfigService.saveTemplateConifg(excelTemplate);
		OpreateResult op=excelTemplateMongoService.save(excelTemplate);
		return op;
		
	}
	
	@ResponseBody
	@RequestMapping("/delete")  
    public OpreateResult delete(HttpServletRequest request,Model model,@RequestParam(value ="name", required = true) String name ){ 
//		OpreateResult op=jsonConfigService.deleteTemplateConifgByName(name);
		OpreateResult op=excelTemplateMongoService.deleteTemplateConifgByName(name);
		return op;
	}
	
	@RequestMapping("/toSelectTemplate")  
    public String toSelectTemplate(HttpServletRequest request,Model model,@RequestParam(value ="departmentId", required = true) String departmentId,@ModelAttribute("excelTemplate")ExcelTemplate excelTemplate,
    		@RequestParam(value ="pageNum", required = false) Integer pageNum ,@RequestParam(value ="numPerPage", required = false) Integer numPerPage ){ 
		Page<ExcelTemplate> page=new Page<ExcelTemplate>();
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		page=jsonConfigService.getExcelTemplatePage(page, excelTemplate);
		List<SysDepartmentTemplate> departmentTemplates=departmentTemplateService.getDepartmentTemplate(departmentId);
		Map<Integer,String> periodType=Constant.periodType();
		request.setAttribute("periodType", periodType);
		request.setAttribute("pageList", page);
		request.setAttribute("departmentId", departmentId);
		request.setAttribute("excelTemplate", excelTemplate);
		request.setAttribute("departmentTemplates", departmentTemplates);
		return "admin/template/departmentSelect";
	}
	
	@ResponseBody
	@RequestMapping("/selectTemplate")  
    public OpreateResult selectTemplate(HttpServletRequest request,Model model,@RequestParam(value ="departmentId", required = true) String departmentId,
    		@RequestParam(value ="templateNames", required = true) String[] templateNames,
    		@RequestParam(value ="reportPeroid", required = true) String[] reportPeroid){ 
		List<SysDepartmentTemplate> sysDepartmentTemplateList=new ArrayList<SysDepartmentTemplate>();
		if(templateNames!=null && templateNames.length>0){
			for(int i=0;i<templateNames.length;i++){
				String templateName=templateNames[i];
				SysDepartmentTemplate sysDepartmentTemplate=new SysDepartmentTemplate();
				sysDepartmentTemplate.setDepartmentId(departmentId);
				sysDepartmentTemplate.setTemplateName(templateName);
				sysDepartmentTemplate.setReportPeroid(Integer.parseInt(reportPeroid[i]));
				sysDepartmentTemplateList.add(sysDepartmentTemplate);
			}
		}
		return departmentTemplateService.batchAddDepartmentTemplate(sysDepartmentTemplateList);
	}
	
	@ResponseBody
	@RequestMapping("/unselectTemplate")  
    public OpreateResult unselectTemplate(HttpServletRequest request,Model model,@RequestParam(value ="departmentId", required = true) String departmentId,@RequestParam(value ="templateNames", required = true) String[] templateNames){ 
		List<SysDepartmentTemplate> sysDepartmentTemplateList=new ArrayList<SysDepartmentTemplate>();
		if(templateNames!=null && templateNames.length>0){
			for(int i=0;i<templateNames.length;i++){
				String templateName=templateNames[i];
				SysDepartmentTemplate sysDepartmentTemplate=new SysDepartmentTemplate();
				sysDepartmentTemplate.setDepartmentId(departmentId);
				sysDepartmentTemplate.setTemplateName(templateName);
				sysDepartmentTemplateList.add(sysDepartmentTemplate);
			}
		}
		return departmentTemplateService.batchDeleteDepartmentTemplate(sysDepartmentTemplateList);
	}
	
	@RequestMapping("/toEdit")  
    public String toEdit(HttpServletRequest request,Model model,@RequestParam(value ="name", required = true) String name ){ 
//		ExcelTemplate excelTemplate=jsonConfigService.getExcelTemplateByName(name);
//		List<String> datasourceList=jsonConfigService.getAllDatasourceId();
		ExcelTemplate excelTemplate=excelTemplateMongoService.findByName(name);
		List<String> datasourceList=jsonConfigService.getAllDatasourceId();
		request.setAttribute("datasourceList", datasourceList);
		Map<Integer,String> colType=Constant.colType();
		//Map<Integer,String> periodType=Constant.periodType();
		request.setAttribute("colType", colType);
		//request.setAttribute("periodType", periodType);
		request.setAttribute("templateTablePrefix", Constant.templateTablePrefix);
		request.setAttribute("excelTemplate", excelTemplate);
		return "admin/template/editDetail";
	}
	
	@ResponseBody
	@RequestMapping("/editDetail")  
    public OpreateResult editDetail(HttpServletRequest request,Model model,HttpSession session){ 
		SysReportUser u=(SysReportUser) session.getAttribute(Constant.SESSION_USER);
		String templateName=request.getParameter("templateName");
		String datasource=request.getParameter("datasource");
		//String reportPeriod=request.getParameter("reportPeriod");
		String reportPassDate=request.getParameter("reportPassDate");
		String idRule=request.getParameter("idRule");
		String templateDesc=request.getParameter("templateDesc");
		String[] excelColName=request.getParameterValues("excelColName");
		String[] isMain=request.getParameterValues("isMain");
		ExcelTemplate excelTemplate=jsonConfigService.getExcelTemplateByName(templateName);
		if(excelTemplate!=null){
			excelTemplate.setDatasource(datasource);
			//excelTemplate.setZq(Integer.parseInt(reportPeriod));
			excelTemplate.setZqgqsj(Integer.parseInt(reportPassDate));
			excelTemplate.setIdcode(idRule);
			excelTemplate.setDesc(templateDesc);
			excelTemplate.setUser(u.getUserName());
			excelTemplate.setUpdateTime(DateUtil.formateDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			
			List<ExcelTemplateCol> cols=excelTemplate.getCols();
			
			if(excelColName!=null  && isMain!=null && excelColName.length==isMain.length){
					for(int i=0;i<excelColName.length;i++){
						for(ExcelTemplateCol excelTemplateCol:cols){
							if(excelTemplateCol.getName().equals(excelColName[i])){
								excelTemplateCol.setIsMain(Integer.parseInt(isMain[i]));
							}
						}
					}
				
			}
			
		}
//		OpreateResult op=jsonConfigService.updateTemplateConifg(excelTemplate);
		OpreateResult op=excelTemplateMongoService.updateTemplateConifg(excelTemplate);
		return op;
		
	}
}

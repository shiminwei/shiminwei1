
package com.ahcd.controller.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.ahcd.common.Constant;
import com.ahcd.common.DateUtil;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.PropertyManager;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.CompanyInfo;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysAreaInfo;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.pojo.ZdConstant;
import com.ahcd.service.ISysAreaInfoService;
import com.ahcd.service.impl.CompanyInfoServiceImpl;
import com.ahcd.service.impl.ZdConstantServiceImpl;

/**
 * 说明 :企业基础信息
 * @version ：2017年03月10日 
 */
@Controller
@RequestMapping("/web/companyinfo")
public class CompanyInfoController {
	/**下载模板-文件名
	 * */
	private final static String FILE_NAME = "companyInfo";
	/**下载模板-文件类型
	 * */
	private final static String FILE_TYPE = ".xlsx";
	/**展示文件名
	 * */
	private final static String FILE_DISPLAY_NAME = "企业信息批量导入模板";
	@Resource
	private CompanyInfoServiceImpl companyInfoService;
	@Resource  
	private ISysAreaInfoService areaService; 
	@Resource  
	private ZdConstantServiceImpl zdConstantService;
	/**
	 * 功能说明 :企业基础信息列表
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Page<CompanyInfo> page, CompanyInfo bean) {
		page.setQueryBean(bean);
		Page<CompanyInfo> pageList=companyInfoService.getCompanyInfoPage(page);
		request.setAttribute("pageList", pageList);
		request.setAttribute("bean", bean);
		return "web/company/companyinfo/list";
	}

	/**
	 * 功能说明 :跳转新增/编辑企业基础信息
	 */
	@RequestMapping("/toAddOrEdit")
	public String toAddOrEdit(HttpServletRequest request, String id,CompanyInfo bean) {
		if(!StringUtil.isBlank(id)){
			bean = companyInfoService.getCompanyInfoById(new BigDecimal(id));
		}
		List<SysAreaInfo> areaList = areaService.getArea();
		request.setAttribute("areaList", areaList);
		request.setAttribute("bean", bean);
		return "web/company/companyinfo/edit";
	}

	/**
	 * 功能说明 :新增/修改企业基础信息
	 */
	@ResponseBody
	@RequestMapping("/addOrEdit")
	public OpreateResult addOrEdit(HttpServletRequest request, CompanyInfo bean) {
		String navTabId = "companyManage";
		OpreateResult opreateResult = new OpreateResult("200", "操作成功", navTabId, "closeCurrent", "");
		int re = -1;
		if(!StringUtil.isBlank(bean.getAreaId())){
			String[] area = bean.getAreaId().split("@");
			bean.setAreaId(area[0]);
			bean.setAreaName(area[1]);
		}	
		if(StringUtil.isBlank(bean.getId())){
			bean.setIsDelete("0");
			re = companyInfoService.insertCompanyInfo(bean);
		}else{
			re = companyInfoService.updateCompanyInfo(bean);
		}	
		if (re <= 0) {
			opreateResult = new OpreateResult("300", "操作失败", navTabId, "closeCurrent", "");
		}
		return opreateResult;
	}

	/**
	 * 功能说明 :删除企业基础信息
	 */
	@ResponseBody
	@RequestMapping("/toDelete")
	public OpreateResult toDelete(HttpServletRequest request, String id) {
		OpreateResult opreateResult = new OpreateResult("200", "删除成功", "", "", "");
		int re = companyInfoService.deleteById(id);
		if (re <= 0) {
			opreateResult = new OpreateResult("300", "删除失败", "", "", "");
		}
		return opreateResult;
	}
	
	/**
	 * 功能说明 :置反[是否规模以上企业/是否招商企业]
	 */
	@ResponseBody
	@RequestMapping("/isAboveScale")
	public OpreateResult isAboveScale(HttpServletRequest request, String id,String type) {
		OpreateResult opreateResult = new OpreateResult("200", "修改成功", "", "", "");
		int re = companyInfoService.reverse(new BigDecimal(id),type);
		if (re <= 0) {
			opreateResult = new OpreateResult("300", "修改失败", "", "", "");
		}
		return opreateResult;
	}
	
	/**
	 * 功能说明 :详情页面
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest request, Model model, String id,CompanyInfo bean) {	
	//	bean = companyInfoService.getCompanyInfoById(new BigDecimal(id));
		if(id==null || StringUtil.isBlank(id)){
			id = "207827";
		}
		bean = companyInfoService.selectInfoTaxById(new BigDecimal(id));
		if (bean!=null) {
			if (bean.getRegisterDate()!=null) {
				bean.setRegisterDate(DateUtil.changeFormat(bean.getRegisterDate(), "yyyyMMdd", "yyyy年MM月dd日"));				
			}
			if (bean.getApprovalDate()!=null) {
				bean.setApprovalDate(DateUtil.changeFormat(bean.getApprovalDate(), "yyyyMMdd", "yyyy年MM月dd日"));
			}
		}
			model.addAttribute("bean", bean);
			//List<SysAreaInfo> areaList = areaService.getArea();
			List<ZdConstant> areaList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_SSQY_TYPE);// 所属区域
			request.setAttribute("areaList", areaList);
			return "web/company/companyinfo/detail";
	}
	
	/**
	 * 功能说明 :企业重名判断 
	 */
	@RequestMapping("/checkSameName")
	public void checkSameName(HttpServletRequest request, HttpServletResponse response,BigDecimal id ,String name) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id",id);
		map.put("name",name);
		int rel = companyInfoService.getCount(map);
		try {
			String ret = "false";
			if (rel >= 1) {
				ret = "true";
			}
			response.getWriter().print(ret);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能说明 :下载模板
	 */
	@RequestMapping("downExcel")
	public void downExcel(HttpServletResponse response,
			HttpServletRequest request) {
		try {
			//读到流中
			InputStream inStream = new FileInputStream(getFilePath() + FILE_NAME 
					+ FILE_TYPE);//文件的存放路径
			//设置输出的格式
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(FILE_DISPLAY_NAME.getBytes("gb2312"), "iso8859-1")
					+ FILE_TYPE);//设定输出文件头
			//循环取出流中的数据
			byte[] b = new byte[100];
			int len;
			while ((len = inStream.read(b)) > 0)
				response.getOutputStream().write(b, 0, len);
			inStream.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能说明 :跳转导入Excel页面
	 */
	@RequestMapping("/toExcel")
	public String toUpExcel(HttpServletRequest request, Page<CompanyInfo> page,CompanyInfo bean,String type) {	
		List<CompanyInfo> list = new ArrayList<CompanyInfo>();
		HttpSession session = request.getSession();
		SysReportUser user = (SysReportUser) session.getAttribute(Constant.SESSION_USER);
		String filename = (String) session.getAttribute(user.getUserId()+"-filename");
		@SuppressWarnings("unchecked")
		List<CompanyInfo> tempList = (List<CompanyInfo>) session.getAttribute(user.getUserId()+"-list");
		//先预览后跳入这里
		if(!StringUtil.isBlank(filename)){
			String filePath = getFilePath();
			if(!StringUtil.isBlank(filePath)){		
				filePath = filePath +user.getUserId()+File.separator +filename;
				getFromExcel(filePath,list,false);
				Boolean bool = true;
				if(!StringUtil.isBlank(list)&& list.size()>0){
					bool = checkCompanyInfo(list);
				}
				if(!bool){
					request.setAttribute("message","有些企业信息解析有误。即使点击保存按钮，其信息也不会导入系统中。请修改后再导入！");
				}
				request.setAttribute("filename", filename);
				session.removeAttribute(user.getUserId()+"-filename");
			}
		}
		//先保存后跳入这里
		else if(!StringUtil.isBlank(tempList)&&tempList.size()>0){
			list.addAll(tempList);
			request.removeAttribute("filename");
			session.removeAttribute(user.getUserId()+"-list");
			request.setAttribute("message", "这些企业信息解析有误或者在系统中企业名称已经存在，请修改后再导入！");
		}
		//从企业信息管理跳入这里
		else{
			request.removeAttribute("message");
			request.removeAttribute("filename");
			session.removeAttribute(user.getUserId()+"-filename");
		}
		page.setResult(list);
		page.setTotalCount(list.size());
		request.setAttribute("pageList", page);		
		return "web/company/companyinfo/companyexcel";
	}
	
	/**
	 * 功能说明 :预览Excel信息,将文件保存在临时路径
	 */
	@ResponseBody
	@RequestMapping("/previewExcel")
	public OpreateResult previewExcel(HttpServletRequest request,MultipartFile uploadExcel) {
		List<CompanyInfo> list = new ArrayList<CompanyInfo>();
		HttpSession session = request.getSession();
		SysReportUser user = (SysReportUser) session.getAttribute(Constant.SESSION_USER);
		String filename =uploadExcel.getOriginalFilename();
		if(StringUtil.isBlank(filename)){
			return new OpreateResult("300", "请选择上传文件", "upExcel", "", "");
		}		
		String filePath = getFilePath();
		if(StringUtil.isBlank(filePath)){
			return  new OpreateResult("300", "上传文件的临时路径配置不存在", "upExcel", "", "");
		}
		filename = saveTempFile(uploadExcel,filePath,user.getUserId());
		if(StringUtil.isBlank(filename)){
			return new OpreateResult("300", "预览失败", "upExcel", "", "");
		}		
		filePath = filePath +user.getUserId()+File.separator ;
		int count = getFromExcel(filePath+filename,list,true);
		if(count < 2){
			return new OpreateResult("300", "上传的文件不能为空", "upExcel", "", "");
		}
		session.setAttribute(user.getUserId()+"-filename", filename);	
		return new OpreateResult("200", "预览成功", "upExcel", "", "");	
	}
	
	/**
	 * 功能说明 :保存Excel信息
	 */
	@ResponseBody
	@RequestMapping("/saveExcel")
	public OpreateResult saveExcel(HttpServletRequest request,String filename) {
	
		List<CompanyInfo> list = new ArrayList<CompanyInfo>();
		HttpSession session = request.getSession();
		SysReportUser user = (SysReportUser) session.getAttribute(Constant.SESSION_USER);
		if(StringUtil.isBlank(filename)){
			return new OpreateResult("300", "请上传文件并预览", "upExcel", "", "");
		}
		String filePath = getFilePath();
		if(StringUtil.isBlank(filePath)){
			return  new OpreateResult("300", "上传文件的临时路径配置不存在", "upExcel", "", "");
		}
		filePath = filePath +user.getUserId()+File.separator ;
		int count = getFromExcel(filePath+filename,list,false);
		if(count < 2 ){
			return new OpreateResult("300", "上传的文件不能为空", "upExcel", "", "");
		}
		checkCompanyInfo(list);
		int ret = companyInfoService.saveCompanyInfoExcel(list);
		if(ret <=0){
			return  new OpreateResult("300", "保存失败", "upExcel", "", "");
		}
		//返回解析有误的数据
		if(!StringUtil.isBlank(list) && list.size()>0){
			session.setAttribute(user.getUserId()+"-list", list);
		}
		//删除临时文件夹/user.getUserId()
		File fileFolder =new File(filePath);    
		if(fileFolder.exists()||fileFolder.isDirectory()){        
			deleteFiles(fileFolder);    
		}
		
		return new OpreateResult("200", "保存成功", "upExcel", "", "");	
	}
	
	/**检查实体类中数据的有效性
	 * */
	private Boolean checkCompanyInfo(List<CompanyInfo> list){
		Boolean flag = true;
		Map<String,CompanyInfo> map = new HashMap<String, CompanyInfo>();
		Map<String,String> areaMap = areaNameMap();
		StringBuffer sb = null;
		for(CompanyInfo com : list){
			sb = new StringBuffer();
			//检查地区有效性
			String temp = areaMap.get(com.getAreaName());
			if(StringUtil.isBlank(temp)){
				flag = false ;
				sb.append("；所属区域在系统中不存在");
				com.setCheckResult(""+com.getCheckResult());
			}
			//企业名称不可为空
			if(StringUtil.isBlank(com.getName())){
				flag = false ;
				sb.append("；企业名称不可为空");
			//检查导入模板是否有重名的企业名称
			}else{		
				CompanyInfo compay = map.get(com.getName());
				if(compay != null){
					String str = compay.getCheckResult();
					if("".equals(str)||str==null){
						compay.setCheckResult("导入模板中有重名的企业。");
					}else{
						if(!str.contains("导入模板中有重名的企业")){
							compay.setCheckResult(str.substring(0,str.length()-1)+"；导入模板中有重名的企业。");
						}
					}			
					sb.append("；导入模板中有重名的企业");
					flag = false ;
				 }
				map.put(com.getName(), com);			
			}
			com.setCheckResult(sb.append("。").substring(1).toString());
			com.setAreaId(temp);
		}
		return flag;
	}
	
	/**将文件保存到临时路径中
	 * */
	private String saveTempFile(MultipartFile uploadExcel,String filePath,BigDecimal userId){
		String fileName = "";
		try{
			fileName = uploadExcel.getOriginalFilename();		
			if(!StringUtil.isBlank(fileName)){
				filePath = filePath +userId;
				File fileFolder =new File(filePath);    
				//如果文件夹不存在则创建    
				if(!fileFolder.exists()&&!fileFolder.isDirectory()){        
					fileFolder.mkdir();    
				}
				filePath = filePath+File.separator+fileName;
				uploadExcel.transferTo(new File(filePath));					
			 }	
			}catch(Exception e){
				e.printStackTrace();
				fileName = "" ;
			}	
		return fileName;
	}
	
	/**根据后缀名读取不同格式的Excel数据
	 * */
	private int getFromExcel(String filename,List<CompanyInfo> list,Boolean isPreview){
		InputStream is=null;
		Workbook wb=null;
		String type=filename.substring(filename.lastIndexOf(".")+1);
		File file=new File(filename);
		try {
			is=new FileInputStream(file);
			if(type.equals("xls")){
				wb=new HSSFWorkbook(is);
				return readXls(wb,list,isPreview);
			}else if(type.equals("xlsx")){
				wb=new XSSFWorkbook(is);
				return readXlsx(wb,list,isPreview);
			}
		} catch (Exception e) {
		} finally{
			try {
				is.close();
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	/**
	 * 读取xls文件记录转为实体类
	 */
	private int readXls(Workbook wb,List<CompanyInfo> list,Boolean isPreview){
		Sheet sheet=wb.getSheetAt(0);
		int count = sheet.getLastRowNum();
		//文件为空判断或者是预览
		if(count < 2 || isPreview){
			return sheet.getLastRowNum();
		}
		@SuppressWarnings("rawtypes")
		Class clazz = CompanyInfo.class;
		Field[] fields = clazz.getDeclaredFields();
		Map<String,Integer> map = new HashMap<String,Integer>();
		XSSFRow xss=(XSSFRow) sheet.getRow(1);
		for(int i =0;i<xss.getLastCellNum();i++){
			String cel = xss.getCell(i).getStringCellValue();
			if(StringUtil.isBlank(cel))continue;
			for(Field field:fields){
				if(field != null && cel.equals(field.getName())){
					map.put(cel,i);
				}
			}
		}
		CompanyInfo com = null ;
		for(int i=0;i<=sheet.getLastRowNum();i++){
			HSSFRow hssfrow=(HSSFRow) sheet.getRow(i);
			for(Entry<String,Integer> entry:map.entrySet()){
				if(com == null){com = new CompanyInfo();}
				HSSFCell hssfcell=hssfrow.getCell(entry.getValue());
				if(!StringUtil.isBlank(hssfcell)){
					hssfcell.setCellType(Cell.CELL_TYPE_STRING);
					String cellValue=hssfcell.getStringCellValue();
					if(StringUtil.isBlank(cellValue))continue;
					try {
						Field temFiled = com.getClass().getDeclaredField(entry.getKey());
						Boolean boo = temFiled.isAccessible();
						temFiled.setAccessible(true);
						toChangByType(temFiled,com,cellValue);
						temFiled.setAccessible(boo);
					} catch (NoSuchFieldException e) {					
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {						
						e.printStackTrace();
					} catch (IllegalAccessException e) {						
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			if(!StringUtil.isBlank(com)){
				list.add(com);
				com = null;
			}
		}
		return sheet.getLastRowNum();
	}
	
	/**
	 * 读取xlsx文件记录转为实体类
	 */
	private int readXlsx(Workbook wb,List<CompanyInfo> list,Boolean isPreview){
		Sheet sheet=wb.getSheetAt(0);
		int count = sheet.getLastRowNum();
		//文件为空判断或者是预览
		if(count < 2 || isPreview){
			return sheet.getLastRowNum();
		}
		@SuppressWarnings("rawtypes")
		Class clazz = CompanyInfo.class;
		Field[] fields = clazz.getDeclaredFields();
		Map<String,Integer> map = new HashMap<String,Integer>();
		XSSFRow xss=(XSSFRow) sheet.getRow(1);
		for(int i =0;i<xss.getLastCellNum();i++){
			String cel = xss.getCell(i).getStringCellValue();
			if(StringUtil.isBlank(cel))continue;
			for(Field field:fields){
				if(field != null && cel.equals(field.getName())){
					map.put(cel,i);
				}
			}
		}
		CompanyInfo com = null ;
		for (int i = 2; i <=sheet.getLastRowNum(); i++) {
			XSSFRow xssfrow=(XSSFRow) sheet.getRow(i);
			for(Entry<String,Integer> entry:map.entrySet()){
				if(com == null){com = new CompanyInfo();}
				XSSFCell xssfcell=xssfrow.getCell(entry.getValue());
				if(!StringUtil.isBlank(xssfcell)){
					xssfcell.setCellType(Cell.CELL_TYPE_STRING);
					String cellValue=xssfcell.getStringCellValue();
					if(StringUtil.isBlank(cellValue))continue;
					try {
						Field temFiled = com.getClass().getDeclaredField(entry.getKey());
						Boolean boo = temFiled.isAccessible();
						temFiled.setAccessible(true);
						toChangByType(temFiled,com,cellValue);
						temFiled.setAccessible(boo);
					} catch (NoSuchFieldException e) {					
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {						
						e.printStackTrace();
					} catch (IllegalAccessException e) {						
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			if(!StringUtil.isBlank(com)){
				list.add(com);
				com = null;
			}
		}
		return sheet.getLastRowNum();
	}
	
	/**根据属性类型设置值
	 * */
	private void toChangByType(Field field, CompanyInfo com, String str)
			throws IllegalArgumentException, IllegalAccessException,
			ParseException {
		Class<?> clazz = field.getType();
		if (Date.class == clazz) {
			field.set(com, DateUtil.convertStringToDate("yyyy-MM-dd", str));
		} else if (Integer.class == clazz) {
			field.set(com, Integer.parseInt(str));
		} else if (BigDecimal.class == clazz) {
			field.set(com, new BigDecimal(str));
		} else {
			field.set(com, str);
		}
	}
	
	/**地区Map
	 * */
	private Map<String,String> areaNameMap(){
		Map<String,String> map = new HashMap<String, String>();		
		List<SysAreaInfo> areaList = areaService.getArea();
		if(!StringUtil.isBlank(areaList) && areaList.size()>0){
			for(SysAreaInfo temp : areaList){
				map.put(temp.getAreaName(), temp.getAreaId());
			}
		}
		return map;
	}
	
	/**获得文件临时路径
	 * */
	private String getFilePath(){
		String filePath= PropertyManager.getConfigProperty("config_path")
				+PropertyManager.getConfigProperty("upload_excel_path");
		return filePath;
	}
	
	/**删除临时文件
	 * */
	private void deleteFiles(File file){
	    for(File temp:file.listFiles()){
	        if(temp.isDirectory()){
	        	deleteFiles(temp);
	        }
	        else{
	        	temp.delete();
	        }
	    }
	    file.delete();
	}
	
	
	
}

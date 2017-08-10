package com.ahcd.controller.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.ahcd.common.Constant;
import com.ahcd.common.ExcelUtil;
import com.ahcd.common.FileUploadUtil;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.ProblemBean;
import com.ahcd.pojo.SysDepartmentTemplate;
import com.ahcd.pojo.SysReportLog;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.pojo.WebDataReportBean;
import com.ahcd.service.impl.SysReportLogServiceImpl;
import com.ahzd.service.ExcelTemplateMongoService;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/web/dataReport")
public class WebDataReportController {

	//@Resource
	//private JsonConfigServiceImpl jsonConfigService;
	//@Resource
	//private SysDepartmentTemplateServiceImpl departmentTemplateService;

	@Resource
	private SysReportLogServiceImpl sysReportLogService;

	@Resource  
	private ExcelTemplateMongoService excelTemplateMongoService; 
	/**
	 * 
	 * 功能说明 : 获取分页列表
	 * 
	 * @author : fei yang
	 * @version ：2016年11月2日 上午11:01:00
	 * @param request
	 * @param model
	 * @param page
	 * @param bean
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Page<ExcelTemplate> page) {
		SysReportUser user = (SysReportUser) request.getSession().getAttribute(Constant.SESSION_USER);
		//page = jsonConfigService.getPageListByUserId(page, user.getUserId());
		page = excelTemplateMongoService.getPageListByUserId(page, user.getUserId());
		if(page.getResult() != null && page.getResult().size() > 0){
			page = sysReportLogService.getIsReportList(page, user.getDepartmentId());
		}	
		model.addAttribute("pageList", page);
		model.addAttribute("isReport", 0);
		return "web/dataReport/list";
	}

	/**
	 * 
	 * 功能说明 : 跳转到上报页面
	 * 
	 * @author : fei yang
	 * @version ：2016年11月3日 上午10:17:06
	 * @param request
	 * @param model
	 * @param bean
	 * @return
	 */
	@RequestMapping("/toReport")
	public String toReport(HttpServletRequest request, Model model, SysDepartmentTemplate bean, String reportType,
			String year, String month) {
		//ExcelTemplate template = jsonConfigService.getExcelTemplateByName(bean.getTemplateName());
		if(StringUtil.isBlank(bean.getTemplateName())){
			return "web/dataReport/report";
		}
		try {
			String tempalename = new String(bean.getTemplateName().getBytes("ISO-8859-1"),"UTF-8");
			bean.setTemplateName(tempalename);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ExcelTemplate template = excelTemplateMongoService.findByName(bean.getTemplateName());
		Map<String, String> map = Constant.getMapByType(bean.getReportPeroid());

		SysReportLog record = new SysReportLog();
		record.setTemplateName(template.getName());
		record.setReportZq(new BigDecimal(template.getZq()));
		record.setReportYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		model.addAttribute("year", Constant.getQueryYear());
		model.addAttribute("month", map);
		model.addAttribute("nowYear", Calendar.getInstance().get(Calendar.YEAR));
		model.addAttribute("nowMonth", Constant.getDefaultSelectedValue(bean.getReportPeroid()));
		model.addAttribute("template", template);
		model.addAttribute("reportType", reportType);
		model.addAttribute("bean", bean);
		return "web/dataReport/report";
	}

	/**
	 * 
	 * 功能说明 : 上传文件类型以及表头是否与模板一致
	 * 
	 * @author : fei yang
	 * @version ：2016年11月3日 下午4:36:28
	 * @param request
	 * @param model
	 * @param bean
	 * @param file
	 * @return * @return -1： 文件格式不正确 -2：表头为空 -3 原始模板存在问题 -4 上传文件与模板不一致 0 未知错误 1
	 *         可以上传了
	 * 
	 */
	@RequestMapping("/checkReport")
	public void checkReport(HttpServletResponse response, HttpServletRequest request, Model model,
			SysDepartmentTemplate bean, MultipartFile file) {
		int checkValue = ExcelUtil.checkExcelHead(file, bean.getTemplateName());
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.print(checkValue);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功能说明 : 检测上传模板内容
	 * 
	 * @author : fei yang
	 * @version ：2016年11月7日 上午10:13:09
	 * @param response
	 * @param request
	 * @param model
	 * @param bean
	 * @param file
	 */
	@RequestMapping("/checkExcelContent")
	public void checkExcelContent(HttpServletResponse response, HttpServletRequest request, Model model,
			SysDepartmentTemplate bean, MultipartFile file) {
		try {
			//ExcelTemplate template = jsonConfigService.getExcelTemplateByName(bean.getTemplateName());
			ExcelTemplate template = excelTemplateMongoService.findByName(bean.getTemplateName());
			List<Object> objList = ExcelUtil.readExcel(file.getInputStream());
			List<ProblemBean> list = ExcelUtil.checkExeclContent(objList, template);
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.print(JSON.toJSONString(list));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功能说明 :报送数据
	 * 
	 * @author : fei yang
	 * @version ：2016年11月7日 上午10:13:30
	 * @param response
	 * @param request
	 * @param model
	 * @param bean
	 * @param file
	 * @param webDataReportBean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/report")
	public OpreateResult report(HttpServletResponse response, HttpServletRequest request, Model model,
			SysDepartmentTemplate bean, MultipartFile file, WebDataReportBean webDataReportBean) {
		if (StringUtil.isBlank(webDataReportBean.getMonth())) {
			webDataReportBean.setMonth("0");
		}
		SysReportUser user = (SysReportUser) request.getSession().getAttribute(Constant.SESSION_USER);
		//ExcelTemplate template = jsonConfigService.getExcelTemplateByName(bean.getTemplateName());
		ExcelTemplate template = excelTemplateMongoService.findByName(bean.getTemplateName());
		OpreateResult opreateResult = new OpreateResult("300", "报送失败", "reportUpload", "closeCurrent", "");
		try {
			List<Object> objList = ExcelUtil.readExcel(file.getInputStream());
			objList = ExcelUtil.replaceExeclContent(objList, template);// 替换
			String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss-SSS").format(new Date());
			System.out.println("插入SQL开始时间=====》" + time);
			boolean flag = ExcelUtil.execlImpToDb(objList, template, user, webDataReportBean);
			time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss-SSS").format(new Date());
			System.out.println("插入SQL结束时间=====》" + time);
			if (flag) {
				opreateResult = new OpreateResult("200", "数据报送成功", "reportUpload", "closeCurrent", "");
				sysReportLogService.addReportLog(user, webDataReportBean, bean, template, objList.size());
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return opreateResult;
	}

	@RequestMapping("/toQueryData")
	public String toQueryData(HttpServletRequest request, Model model) {

		return "web/dataReport/toQueryData";

	}

	@ResponseBody
	@RequestMapping("/queryData")
	public OpreateResult queryData(HttpServletRequest request, Model model) {
		OpreateResult opreateResult = new OpreateResult();
		opreateResult.setStatusCode("200");
		opreateResult.setNavTabId("reportUpload");
		opreateResult.setMessage("数据报送成功");
		opreateResult.setCallbackType("closeCurrent");
		return opreateResult;
	}

	/**
	 * 
	 * 功能说明 :模板下载
	 * 
	 * @author : fei yang
	 * @version ：2016年11月2日 下午1:48:24
	 * @param response
	 * @param request
	 * @param model
	 * @param fileName
	 */
	@RequestMapping("toDownload")
	public void toDownload(HttpServletResponse response, HttpServletRequest request, Model model, String fileName) {
		try {
			FileUploadUtil.downloadFile(response, FileUploadUtil.excelTemplatePath, fileName, ".xls");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功能说明 :获取月份
	 * 
	 * @author : fei yang
	 * @version ：2016年11月7日 下午6:32:23
	 * @param response
	 * @param request
	 * @param model
	 * @param record
	 */
	@RequestMapping("/getMonthMap")
	public void getMonthMap(HttpServletResponse response, HttpServletRequest request, Model model,
			SysReportLog record) {
		try {
			Map<String, String> newMap = Constant.getMapByType(record.getReportZq().intValue());
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.print(JSON.toJSONString(newMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

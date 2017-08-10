package com.ahcd.controller.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.ahcd.common.Constant;
import com.ahcd.common.DateUtil;
import com.ahcd.common.ExcelUtils;
import com.ahcd.common.OpreateResult;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.ExcelBean;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentTemplate;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.ISysDepartmentTemplateService;
import com.ahzd.service.DataSourceMongoService;
import com.ahzd.service.ExcelTemplateMongoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/admin/template")
public class TemplateManageController {

	@Resource
	private ISysDepartmentTemplateService departmentTemplateService;

	@Resource
	private ExcelTemplateMongoService excelTemplateMongoService;

	@Resource
	private DataSourceMongoService dataSourceMongoService;

	/**
	 * 
	 * 功能说明 : 后台采集模板列表
	 * 
	 * @author : fei yang
	 * @version ：2017年4月10日 上午11:00:38
	 * @param request
	 * @param model
	 * @param page
	 *            分页
	 * @param excelTemplate
	 *            模板实体
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Page<ExcelTemplate> page, ExcelTemplate excelTemplate) {
		page = excelTemplateMongoService.getExcelTemplatePage(page, excelTemplate);
		Map<Integer, String> colType = Constant.colType();
		model.addAttribute("colType", colType);
		model.addAttribute("pageList", page);
		model.addAttribute("excelTemplate", excelTemplate);
		return "admin/template/list";
	}

	/**
	 * 
	 * 功能说明 :跳转到选择模板文件以及填写表名页面
	 * 
	 * @author : fei yang
	 * @version ：2017年4月12日 下午1:12:47
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request, Model model) {
		request.setAttribute("templateTablePrefix", Constant.templateTablePrefix);
		return "admin/template/add";
	}

	/**
	 * 
	 * 功能说明 : 添加模板跳转 （校验模板以及表名的合法性）
	 * 
	 * @author : fei yang
	 * @version ：2017年4月12日 上午9:24:00
	 * @param request
	 * @param uploadExcel
	 * @param tableName
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public OpreateResult add(HttpServletRequest request, MultipartFile uploadExcel, String tableName, Model model) {
		OpreateResult op = new OpreateResult("300", "添加模板异常");
		try {
			tableName = Constant.templateTablePrefix + tableName;
			boolean isExist = excelTemplateMongoService.checkExistByTableName(tableName);
			if (isExist) {
				op.setMessage("上传失败，数据库表名已存在");
			} else {
				ExcelBean excelBean = ExcelUtils.readExcelByMultipartFile(uploadExcel);
				switch (excelTemplateMongoService.toCheckTemplate(excelBean)) {
				case 1:
					ExcelTemplate excelTemplate = excelTemplateMongoService.geTemplateByExcelBean(excelBean);
					excelTemplate.setTableName(tableName);
					op=new OpreateResult("200", "模板Excel检验合法,请继续下一步设置","", "forward", JSON.toJSONString(excelTemplate));
					break;
				case -1:
					op.setMessage("需要上传模板存在问题");
					break;
				case -2:
					op.setMessage("模板Excel为空文件");
					break;
				case -3:
					op.setMessage("模板Excel第一张表为空表");
					break;
				case -4:
					op.setMessage("模板Excel第一张表为空表头");
					break;
				case -5:
					op.setMessage("模板Excel不需要有内容");
					break;
				case -6:
					op.setMessage("模板Excel表头列名称存在空值");
					break;
				case -7:
					op.setMessage("模板Excel不需要有其他表");
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			return new OpreateResult("300", "添加模板异常");
		}
		return op;
	}

	/**
	 * 
	 * 功能说明 : 模板的进一步配置页面
	 * 
	 * @author : fei yang
	 * @version ：2017年4月12日 上午9:24:44
	 * @param request
	 * @param model
	 * @param excelTemplateStr
	 * @param type
	 *            1：新增 2：修改
	 * @param _id
	 *            模板的MongoId
	 * @return
	 */
	@RequestMapping("/toAddOrEdit")
	public String toAddDetail(HttpServletRequest request, Model model, String excelTemplateStr, Integer type,
			String _id) {
		ExcelTemplate excelTemplate = new ExcelTemplate();
		try {
			if (type == 1) {// 新增
				excelTemplate = JSON.toJavaObject(JSONObject.parseObject(excelTemplateStr), ExcelTemplate.class);
			} else {// 修改
				excelTemplate = excelTemplateMongoService.findById(_id);
			}
		} catch (Exception e) {
			excelTemplate = new ExcelTemplate();
		}
		List<DatasourceBean> datasourceList = dataSourceMongoService.getAllDatasource();
		model.addAttribute("datasourceList", datasourceList);
		model.addAttribute("excelTemplate", excelTemplate);
		model.addAttribute("colType", Constant.colType());
		model.addAttribute("type", type);
		return "admin/template/addOrEdit";
	}

	/**
	 * 
	 * 功能说明 : 模板新增以及修改的方法
	 * 
	 * @author : fei yang
	 * @version ：2017年4月12日 下午1:25:34
	 * @param request
	 * @param session
	 * @param model
	 * @param excelTemplate
	 * @param type
	 *            1 新增 2修改
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveOrUpdate")
	public OpreateResult saveOrUpdate(HttpServletRequest request, HttpSession session, Model model,
			ExcelTemplate excelTemplate, Integer type) {
		OpreateResult op = new OpreateResult("300", "操作失败");
		SysReportUser u = (SysReportUser) session.getAttribute(Constant.SESSION_USER);
		excelTemplate.setUser(u.getUserName());
		excelTemplate.setUpdateTime(DateUtil.formateDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		if (type == 1) {// 新增模板
			op = excelTemplateMongoService.save(excelTemplate);
		} else {// 修改模板
			op = excelTemplateMongoService.updateTemplateConifg(excelTemplate);
		}
		return op;
	}

	/**
	 * 
	 * 功能说明 :删除模板
	 * 
	 * @author : fei yang
	 * @version ：2017年4月12日 下午2:46:05
	 * @param request
	 * @param id
	 *            模板MongoID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public OpreateResult delete(HttpServletRequest request, String id) {
		OpreateResult op = excelTemplateMongoService.deleteTemplateConifgById(id);
		return op;
	}

	@RequestMapping("/toSelectTemplate")
	public String toSelectTemplate(HttpServletRequest request, Model model,
			@RequestParam(value = "departmentId", required = true) String departmentId,
			@ModelAttribute("excelTemplate") ExcelTemplate excelTemplate,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "numPerPage", required = false) Integer numPerPage) {
		Page<ExcelTemplate> page = new Page<ExcelTemplate>();
		if (numPerPage != null) {
			page.setNumPerPage(numPerPage);
		}
		if (pageNum != null) {
			page.setPageNum(pageNum);
		}
		page = excelTemplateMongoService.getExcelTemplatePage(page, excelTemplate);
		List<SysDepartmentTemplate> departmentTemplates = departmentTemplateService.getDepartmentTemplate(departmentId);
		Map<Integer, String> periodType = Constant.periodType();
		request.setAttribute("periodType", periodType);
		request.setAttribute("pageList", page);
		request.setAttribute("departmentId", departmentId);
		request.setAttribute("excelTemplate", excelTemplate);
		request.setAttribute("departmentTemplates", departmentTemplates);
		return "admin/template/departmentSelect";
	}

	@ResponseBody
	@RequestMapping("/selectTemplate")
	public OpreateResult selectTemplate(HttpServletRequest request, Model model,
			@RequestParam(value = "departmentId", required = true) String departmentId,
			@RequestParam(value = "templateNames", required = true) String[] templateNames,
			@RequestParam(value = "reportPeroid", required = true) String[] reportPeroid,String type) {
		OpreateResult op = new OpreateResult();
		if(type.equals("1")){//全选
			//1.删除部门下所有模版
			departmentTemplateService.deleteByDepartmentId(departmentId);
			List<String> reportPeroidList=Arrays.asList(reportPeroid);  
			List<String> newReportPeroidList=new ArrayList<String>();
			for (int i = 0; i < reportPeroidList.size(); i++) {
				String a = reportPeroidList.get(i);
				if(a.length()>1){
					String b[] = a.split(",");
					newReportPeroidList.add(b[0]);
				}else{
					newReportPeroidList.add(reportPeroidList.get(i));
				}
			}
			//得到新的周期数组
			Object[] newReportPeroid = newReportPeroidList.toArray();
			List<SysDepartmentTemplate> sysDepartmentTemplateList = new ArrayList<SysDepartmentTemplate>();
			if (templateNames != null && templateNames.length > 0) {
				for (int i = 0; i < templateNames.length; i++) {
					String templateName = templateNames[i];
					SysDepartmentTemplate sysDepartmentTemplate = new SysDepartmentTemplate();
					sysDepartmentTemplate.setDepartmentId(departmentId);
					sysDepartmentTemplate.setTemplateName(templateName);
					sysDepartmentTemplate.setReportPeroid(Integer.parseInt((String) newReportPeroid[i]));
					sysDepartmentTemplateList.add(sysDepartmentTemplate);
				}
			}
			//保存
			return departmentTemplateService.batchAddDepartmentTemplate(sysDepartmentTemplateList);
		}else if(type.equals("2")){
			departmentTemplateService.deleteByDepartmentId(departmentId);
			op.setNavTabId("departmentSelectTemplate");
			op.setStatusCode("200");
			op.setMessage("清空模版成功!");
		}
		return op;
	}

	@ResponseBody
	@RequestMapping("/unselectTemplate")
	public OpreateResult unselectTemplate(HttpServletRequest request, Model model,
			@RequestParam(value = "departmentId", required = true) String departmentId,
			@RequestParam(value = "templateNames", required = true) String[] templateNames) {
		List<SysDepartmentTemplate> sysDepartmentTemplateList = new ArrayList<SysDepartmentTemplate>();
		if (templateNames != null && templateNames.length > 0) {
			for (int i = 0; i < templateNames.length; i++) {
				String templateName = templateNames[i];
				SysDepartmentTemplate sysDepartmentTemplate = new SysDepartmentTemplate();
				sysDepartmentTemplate.setDepartmentId(departmentId);
				sysDepartmentTemplate.setTemplateName(templateName);
				sysDepartmentTemplateList.add(sysDepartmentTemplate);
			}
		}
		return departmentTemplateService.batchDeleteDepartmentTemplate(sysDepartmentTemplateList);
	}

	/**
	 * 修改报送周期
	 * 
	 * @author : feiyue yang
	 * @param model
	 * @param templateId
	 * @param zq
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editReportPeroid")
	public OpreateResult editReportPeroid(HttpServletRequest request, Model model,
			String templateName, String zq,String departmentId) {
		List<SysDepartmentTemplate> departmentTemplates = departmentTemplateService.getDepartmentTemplate(departmentId);
		SysDepartmentTemplate sysDepartmentTemplate = new SysDepartmentTemplate();
		for (int i = 0; i < departmentTemplates.size(); i++) {
			if (departmentTemplates.get(i).getTemplateName().equals(templateName)) {
				sysDepartmentTemplate = departmentTemplates.get(i);
			}
		}
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("reportPeroid", zq);
		map.put("templateName", templateName);
		map.put("departmentId", departmentId);
		sysDepartmentTemplate.setReportPeroid(Integer.parseInt(zq));
		departmentTemplateService.updateTemplateById(map);
		OpreateResult opreateResult = new OpreateResult();
		opreateResult.setStatusCode("200");
		opreateResult.setMessage("操作成功!");
		opreateResult.setRel("jbsxBox2");
		return opreateResult;
	}
	
	/**
	 * 删除模版
	 * @author : feiyue yang
	 * @param model
	 * @param templateName
	 * @param zq
	 * @param departmentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteTemplate")
	public OpreateResult deteleTemolate(HttpServletRequest request, Model model,SysDepartmentTemplate SysDepartmentTemplate) {
		departmentTemplateService.delete(SysDepartmentTemplate);
		OpreateResult result = new OpreateResult();
		result.setStatusCode("200");
		result.setMessage("删除成功!");
		result.setRel("jbsxBox2");
		result.setCallbackType("forward");
		String path = request.getContextPath();
	    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		result.setForwardUrl(basePath+"/admin/department/template?currentDepartmentId="+SysDepartmentTemplate.getDepartmentId());
		return result;
	}

}

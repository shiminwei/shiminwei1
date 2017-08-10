package com.ahcd.controller.admin;

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

import com.ahcd.common.Constant;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysAreaInfo;
import com.ahcd.pojo.SysDepartmentAuth;
import com.ahcd.pojo.SysDepartmentInfo;
import com.ahcd.pojo.SysDepartmentTemplate;
import com.ahcd.service.ISysAreaInfoService;
import com.ahcd.service.ISysDepartmentAuthService;
import com.ahcd.service.ISysDepartmentInfoService;
import com.ahcd.service.ISysDepartmentTemplateService;
import com.ahzd.service.ExcelTemplateMongoService;

@Controller
@RequestMapping("/admin/department")
public class DepartmentController {
	@Resource
	private ISysAreaInfoService areaService;
	@Resource
	private ISysDepartmentInfoService departmentService;
	@Resource
	private ISysDepartmentAuthService sysDepartmentAuthService;
	@Resource
	private ISysDepartmentTemplateService departmentTemplateService;

	@Resource
	private ExcelTemplateMongoService excelTemplateMongoService;

	private void base(HttpServletRequest request) {
		List<SysAreaInfo> areaList = areaService.getArea();
		List<SysDepartmentInfo> departmentList = departmentService.getDepartment();
		request.setAttribute("areaList", areaList);
		request.setAttribute("departmentList", departmentList);
	}

	// --------------------------------------------------------地区—部门管理第二版本
	/**
	 * 跳转到地区--部门页面
	 */
	@RequestMapping("/showAreaDepartmentList")
	public String showAreaDepartmentList(HttpServletRequest request, HttpSession session, @RequestParam(value = "areaId", required = false)String areaId,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "numPerPage", required = false) Integer numPerPage) {
		Page<SysAreaInfo> page = new Page<SysAreaInfo>();
		if (numPerPage != null) {
			page.setNumPerPage(numPerPage);
		} 
		if (pageNum != null) {
			page.setPageNum(pageNum);
		}
		page = areaService.selectAreasByPage(page);
		request.setAttribute("pageList", page);
		request.setAttribute("areaId", areaId);
		return "admin/areaDepartment/leftAreaList";
	}

	/**
	 * 删除所选择地区
	 * 
	 */
	@ResponseBody
	@RequestMapping("/deleteArea")
	public OpreateResult deleteArea(HttpServletRequest request,
			@RequestParam(value = "areaIds", required = true) String areaIds) {

		if (areaIds != null) {
			String[] areaId = areaIds.split(",");
			for (int i = 0; i < areaId.length; i++) {
				areaService.isdelete(new String(areaId[i]));
			}
		} 
		OpreateResult result = new OpreateResult();
		result.setStatusCode("200");
		result.setNavTabId("areaDepartmentManager");
		result.setMessage("删除成功");
		return result;
	}

	/**
	 * 跳转到增加地区页面
	 * 
	 */
	@RequestMapping("/toAddArea")
	public String toAddArea(HttpServletRequest request, Model model) {
		List<SysAreaInfo> areaList = areaService.getArea();
		request.setAttribute("areaList", areaList);
		request.setAttribute("type", "add");
		return "admin/areaDepartment/addEditArea";
	}

	/**
	 * 跳转到修改地区页面
	 * 
	 */
	@RequestMapping("/toEditArea")
	public String toEditArea(HttpServletRequest request, Model model, @RequestParam(value = "areaId", required = true)String areaId ) {
		SysAreaInfo areaInfo = areaService.selectAreaCodeByAreaId(areaId);
		request.setAttribute("type", "edit");
		request.setAttribute("areaInfo", areaInfo);
		return "admin/areaDepartment/addEditArea";
	}

	/**
	 * 新增或修改地区
	 * 
	 */
	@ResponseBody
	@RequestMapping("/editArea")
	public OpreateResult editArea(HttpServletRequest request, @ModelAttribute SysAreaInfo areaInfo) {
		OpreateResult opreateResult = new OpreateResult();
		if (areaInfo == null || StringUtil.isBlank(areaInfo.getAreaId())) {
			areaInfo.setIdDelete("0");//默认新增
			areaService.insertInfo(areaInfo);
		} else {
			areaService.updateInfo(areaInfo);
		}
		request.getSession().setAttribute("departmentName3", areaInfo.getAreaName());
		opreateResult.setStatusCode("200");
		opreateResult.setNavTabId("areaDepartmentManager");
		opreateResult.setMessage("操作成功");
		opreateResult.setCallbackType("closeCurrent");
		return opreateResult;
	}

	/**
	 * 根据某个地区id查具体部门
	 */
	@RequestMapping("/selectAreaDepartment")
	public String selectAreaDepartment(HttpServletRequest request, @RequestParam(value = "areaId", required = true)String areaId, String departmentName,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "numPerPage", required = false) Integer numPerPage) {
		Page<SysDepartmentInfo> page = new Page<SysDepartmentInfo>();
		if (numPerPage != null) {
			page.setNumPerPage(numPerPage);
		} 
		if (pageNum != null) {
			page.setPageNum(pageNum);
		}
		SysDepartmentInfo sysDepartmentInfo = new SysDepartmentInfo();
		sysDepartmentInfo.setDepartmentAreaId(areaId);
		sysDepartmentInfo.setDepartmentName(departmentName);
		page.setQueryBean(sysDepartmentInfo);
		page = departmentService.selectSysDepartmentInfoByID(page);
		request.setAttribute("pageList", page);
		request.setAttribute("departmentName", departmentName);
		request.setAttribute("departmentAreaId", areaId);
		return "admin/areaDepartment/rightDepartmentList";
	}

	/**
	 * 跳转到新增或修改部门页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/toEditDepartment")
	public String toEditDepartment(HttpServletRequest request,@RequestParam(value = "areaId", required = false)String areaId, String selectedDepartmentId) {
		String type = "add";
		base(request);
		if (selectedDepartmentId != null && !StringUtil.isBlank(selectedDepartmentId)) {
			SysDepartmentInfo sysDepartmentInfo = departmentService.selectDapartmentInfoByID(selectedDepartmentId);
			request.setAttribute("sysDepartmentInfo", sysDepartmentInfo);
			type = "edit";
		}
		request.setAttribute("type", type);
		request.setAttribute("areaId", areaId);
		return "admin/areaDepartment/addEditDepartment";
	}

	/**
	 * 新增或修改部门
	 * 
	 * @param request
	 * @param sysDepartmentInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editDepartment")
	public OpreateResult editDepartment(HttpServletRequest request,
			@ModelAttribute SysDepartmentInfo sysDepartmentInfo) {
		String type = "add";
		if (sysDepartmentInfo.getDepartmentId() != null && !StringUtil.isBlank(sysDepartmentInfo.getDepartmentId())) {
			type = "edit";
		}
		if (type.equals("add")) {
			departmentService.insertDepartmentInfo(sysDepartmentInfo);
		} else {
			departmentService.updateDapartmentInfoByID(sysDepartmentInfo);
		}
		String path = request.getContextPath();
	    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		
		OpreateResult opreateResult = new OpreateResult();
		opreateResult = new OpreateResult("200", "操作成功!", "areaDepartmentManager", "closeCurrent", basePath+"/admin/department/showAreaDepartmentList?areaId="+sysDepartmentInfo.getDepartmentAreaId());
		return opreateResult;
	}

	/**
	 * 删除部门
	 */
	@ResponseBody
	@RequestMapping("/deleteDepartment")
	public OpreateResult deleteDepartment(HttpServletRequest request, @RequestParam(value = "departmentIds", required = true) String departmentIds) {
		if (departmentIds != null || !StringUtil.isBlank(departmentIds)) {
			String ids1[] = departmentIds.split(",");
			for (int i = 0; i < ids1.length; i++) {
				departmentService.isdelete(ids1[i]);
			}
		} 
		OpreateResult opreateResult = new OpreateResult();
//		opreateResult = new OpreateResult("200", "操作成功!", "areaDepartmentManager", "", "");
		return opreateResult;
	}

	/**
	 * 跳转到选择从部门
	 */
	@RequestMapping("/toSubDepartment")
	public String toSubDepartment(HttpServletRequest request,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "numPerPage", required = false) Integer numPerPage,
			@RequestParam(value = "selectedDepartmentId", required = true) String selectedDepartmentId) {
		Map<String, Object> params = new HashMap<String, Object>();
		Page<SysDepartmentInfo> page = new Page<SysDepartmentInfo>();
		if (numPerPage != null) {
			page.setNumPerPage(numPerPage);
		}
		if (pageNum != null) {
			page.setPageNum(pageNum);
		}
		params.put("StselectedDepartmentId", selectedDepartmentId);
		page.setQueryBean(params);
		Page<SysDepartmentInfo> pageList = departmentService.selectPage(page);

		SysDepartmentInfo sysDepartmentInfo = departmentService.selectDapartmentInfoByID(selectedDepartmentId);
		List<SysDepartmentAuth> sysDepartmentAuthList = sysDepartmentAuthService.getNoticeDepartmentAuth(selectedDepartmentId);
		request.setAttribute("pageList", pageList);
		request.setAttribute("sysDepartmentAuthList", sysDepartmentAuthList);
		request.setAttribute("sysDepartmentInfo", sysDepartmentInfo);
		request.setAttribute("selectedDepartmentId", selectedDepartmentId);
		return "admin/areaDepartment/subDepartmentList";
	}

	/**
	 * 多选或修改从部门
	 */
	@ResponseBody
	@RequestMapping("/selectSubDepartment")
	public OpreateResult selectSubDepartment(HttpServletRequest request,
			@RequestParam(value = "subDepartmentIds", required = true) String[] subDepartmentIds,
			@RequestParam(value = "departmentId", required = true) String departmentId) {
		OpreateResult opreateResult = new OpreateResult();
		for (int i = 0; i < subDepartmentIds.length; i++) {
			HashMap<String, String> parameterMap = new HashMap<String, String>();
			parameterMap.put("departmentId", departmentId);
			parameterMap.put("subDepartmentId", subDepartmentIds[i]);
			sysDepartmentAuthService.deleteByDepartmentId(parameterMap);
			SysDepartmentAuth sysDepartmentAuth = new SysDepartmentAuth();
			sysDepartmentAuth.setDepartmentId(departmentId);
			sysDepartmentAuth.setSubDepartmentId(subDepartmentIds[i]);
			sysDepartmentAuthService.insertSubDepartments(sysDepartmentAuth);
		}
		opreateResult.setStatusCode("200");
		opreateResult.setMessage("选择成功!");
		opreateResult.setNavTabId("selectSubDepartment");
		return opreateResult;
	}

	/**
	 * 清空从部门
	 */
	@ResponseBody
	@RequestMapping("/unSelectSubDepartment")
	public OpreateResult unSelectSubDepartment(HttpServletRequest request,
			@RequestParam(value = "departmentId", required = true) String departmentId,
			@RequestParam(value = "subDepartmentIds", required = true) String[] subDepartmentIds) {
		OpreateResult opreateResult = new OpreateResult();
		for (int i = 0; i < subDepartmentIds.length; i++) {
			HashMap<String, String> parameterMap = new HashMap<String, String>();
			parameterMap.put("departmentId", departmentId);
			parameterMap.put("subDepartmentId", subDepartmentIds[i]);
			sysDepartmentAuthService.deleteByDepartmentId(parameterMap);
		}
		opreateResult.setStatusCode("200");
		opreateResult.setMessage("取消成功!");
		opreateResult.setNavTabId("selectSubDepartment");
		return opreateResult;
	}

	@RequestMapping("/showAreaDepartmentTemplateList")
	public String showAreaDepartmentTemplateList(HttpServletRequest request) {
		List<SysAreaInfo> areaList = areaService.getArea();
		if (areaList != null && areaList.size() > 0) {
			for (int i = 0; i < areaList.size(); i++) {
				List<SysDepartmentInfo> departmentList = departmentService
						.getSysDepartmentInfoByAreaId(areaList.get(i).getAreaId());
				areaList.get(i).setDepartmentList(departmentList);
			}
		}
		request.setAttribute("areaList", areaList);
		return "admin/departmentTemplate/leftAreaDepartmentList";
	}
	
	/**
	 * 根据部门id展现所属模版
	 */
	@RequestMapping("/showDepartmentTemplate")
	public String showDepartmentTemplate(HttpServletRequest request, @RequestParam(value = "selectedDepartmentId", required = true) String selectedDepartmentId,
			@RequestParam(value = "templateName", required = false) String templateName,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "numPerPage", required = false) Integer numPerPage) {
		Page<SysDepartmentTemplate> page = new Page<SysDepartmentTemplate>();
		if (numPerPage != null) {
			page.setNumPerPage(numPerPage);
		}
		if (pageNum != null) {
			page.setPageNum(pageNum);
		}
		SysDepartmentTemplate sysDepartmentTemplate=new SysDepartmentTemplate();
		sysDepartmentTemplate.setDepartmentId(selectedDepartmentId);
		if(StringUtil.notBlank(templateName)){
			sysDepartmentTemplate.setTemplateName(templateName);
		}
		page = departmentTemplateService.getDepartmentTemplatePage(page, sysDepartmentTemplate);
		Map<Integer, String> periodType = Constant.periodType();
		request.setAttribute("periodType", periodType);
		request.setAttribute("pageList", page);
		request.setAttribute("selectedDepartmentId", selectedDepartmentId);
		request.setAttribute("templateName", templateName);

		return "admin/departmentTemplate/rightDepartmentTemplate";
	}
	
	/**
	 * 跳转到选择配置模版
	 */
	@RequestMapping("/toSelectTemplate")
	public String toSelectTemplate(HttpServletRequest request, Model model,
			@RequestParam(value = "StselectedDepartmentId", required = true) String StselectedDepartmentId,
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
		List<SysDepartmentTemplate> departmentTemplates = departmentTemplateService
				.getDepartmentTemplate(StselectedDepartmentId);
		Map<Integer, String> periodType = Constant.periodType();
		request.setAttribute("periodType", periodType);
		request.setAttribute("pageList", page);
		request.setAttribute("departmentId", StselectedDepartmentId);
		request.setAttribute("excelTemplate", excelTemplate);
		request.setAttribute("departmentTemplates", departmentTemplates);
		return "admin/template/departmentSelect";
	}
}
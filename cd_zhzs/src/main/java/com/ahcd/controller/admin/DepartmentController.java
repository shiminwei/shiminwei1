package com.ahcd.controller.admin;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
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

import com.ahcd.common.Constant;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysAreaInfo;
import com.ahcd.pojo.SysDepartmentAuth;
import com.ahcd.pojo.SysDepartmentInfo;
import com.ahcd.pojo.SysDepartmentTemplate;
import com.ahcd.service.IJsonConfigService;
import com.ahcd.service.ISysAreaInfoService;
import com.ahcd.service.ISysDepartmentAuthService;
import com.ahcd.service.ISysDepartmentInfoService;
import com.ahcd.service.ISysDepartmentTemplateService;

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
	private IJsonConfigService jsonConfigService; 
	@Resource  
	private ISysDepartmentTemplateService departmentTemplateService; 
	
	
	@RequestMapping("/show")
	public String show(HttpServletRequest request, Model model) {
		List<SysAreaInfo> areaList = areaService.getArea();
		if (areaList != null && areaList.size() > 0) {
			for (int i = 0; i < areaList.size(); i++) {
				List<SysDepartmentInfo> departmentList = departmentService
						.getSysDepartmentInfoByAreaId(areaList.get(i).getAreaId());
				areaList.get(i).setDepartmentList(departmentList);
			}
		}
		request.setAttribute("areaList", areaList);
		return "admin/department/list";
	}
	
	@RequestMapping("/showArea")
	public String showArea(HttpServletRequest request,Model model){
		List<SysAreaInfo> areaList = areaService.getArea();
		request.setAttribute("areaList", areaList);
		return "admin/department/areaList";
	}
	/**
	 * 跳转到新增页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request, Model model) {
		base(request);
		return "admin/department/add";
	}

	/**
	 * 新增
	 * 
	 * @param request
	 * @param sysDepartmentInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public OpreateResult add(HttpServletRequest request, @ModelAttribute SysDepartmentInfo sysDepartmentInfo) {
		String parentDepartmentIds = request.getParameter("parentDepartment");
		if(!parentDepartmentIds.equals("")){
		String parentDepartmentId = parentDepartmentIds.substring(0,parentDepartmentIds.lastIndexOf("-"));
		sysDepartmentInfo.setParentDepartmentId(parentDepartmentId);
		}
		departmentService.insertDepartmentInfo(sysDepartmentInfo);

		OpreateResult opreateResult = new OpreateResult();
		opreateResult.setStatusCode("200");
		opreateResult.setNavTabId("departmentList");
		opreateResult.setMessage("操作成功");
		opreateResult.setCallbackType("closeCurrent");
		return opreateResult;
	}

	/**
	 * 跳转到修改页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/toEdit")
	public String toEdit(HttpServletRequest request, Model model) {
		base(request);
		String selectedDepartmentId = request.getParameter("selectedDepartmentId");
		SysDepartmentInfo sysDepartmentInfo = departmentService.selectDapartmentInfoByID(selectedDepartmentId);
		request.setAttribute("sysDepartmentInfo", sysDepartmentInfo);
		return "admin/department/edit";
	}
	
	/**
	 * 修改
	 * 
	 * @param request
	 * @param updateSysDepartmentInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/edit")
	public OpreateResult edit(HttpServletRequest request, @ModelAttribute SysDepartmentInfo updateSysDepartmentInfo) {
			String parentDepartmentIds = request.getParameter("parentDepartment");
			if(!parentDepartmentIds.equals("")){
			String parentDepartmentId = parentDepartmentIds.substring(0,parentDepartmentIds.lastIndexOf("-"));
			updateSysDepartmentInfo.setParentDepartmentId(parentDepartmentId);
			departmentService.updateDapartmentInfoByID(updateSysDepartmentInfo);
		}else{
			departmentService.updateDapartmentInfoByID(updateSysDepartmentInfo);
		}
			
		OpreateResult opreateResult = new OpreateResult();
		opreateResult.setStatusCode("200");
		opreateResult.setNavTabId("departmentList");
		opreateResult.setMessage("操作成功");
		opreateResult.setCallbackType("closeCurrent");
		return opreateResult;
	}

	
	private void base(HttpServletRequest request) {
		List<SysAreaInfo> areaList = areaService.getArea();
		List<SysDepartmentInfo> departmentList = departmentService.getDepartment();
		request.setAttribute("areaList", areaList);
		request.setAttribute("departmentList", departmentList);
	}

	/**
	 * 检查部门编码是否重复
	 */
	@RequestMapping("/checkDepartmentCode")
	public void checkDepartmentCode(HttpServletRequest request, HttpServletResponse response, String departmentCode) {
		List<SysDepartmentInfo> departmentInfoList = departmentService.getDepartmentNames();
		String [] departmentCodeAndParentDepartment = departmentCode.split(",");
		if(departmentCodeAndParentDepartment.length==1){
		boolean flag = false;
		try {
			for (int i = 0; i < departmentInfoList.size(); i++) {
				if (departmentInfoList.get(i).getDepartmentCode().equals(departmentCodeAndParentDepartment[0])) {
					flag = true;
				}
			}
			response.getWriter().print(flag);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}else if(departmentCodeAndParentDepartment.length==2){
			try {
				response.getWriter().print("false");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().print("else");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 跳转到新增从部门
	 */
	@RequestMapping("/toSubDepartment")
	public String toAuthDepartment(HttpServletRequest request,
			@RequestParam(value ="pageNum", required = false) Integer pageNum,
			@RequestParam(value ="numPerPage", required = false) Integer numPerPage,
			@RequestParam(value ="StselectedDepartmentId", required = false) String StselectedDepartmentId){
		Map<String,Object> params = new HashMap<String,Object>();
		Page<SysDepartmentInfo> page=new Page<SysDepartmentInfo>();
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		params.put("StselectedDepartmentId", StselectedDepartmentId);
		page.setQueryBean(params);
		Page<SysDepartmentInfo> pageList=departmentService.selectPage(page);
		
		
		String selectedDepartmentId = request.getParameter("StselectedDepartmentId");
		SysDepartmentInfo sysDepartmentInfo = departmentService.selectDapartmentInfoByID(selectedDepartmentId);
		List<SysDepartmentAuth> sysDepartmentAuthList = sysDepartmentAuthService.getNoticeDepartmentAuth(selectedDepartmentId);
		request.setAttribute("pageList", pageList);
		request.setAttribute("sysDepartmentAuthList", sysDepartmentAuthList);
		request.setAttribute("sysDepartmentInfo", sysDepartmentInfo);
		return "admin/department/subDepartment";
	}
	
	/**
	 * 多选或修改从部门
	 */
	@ResponseBody
	@RequestMapping("/subDepartment")
	public OpreateResult AuthDepartment(HttpServletRequest request,
			@RequestParam(value ="subDepartmentIds", required = false) String[] subDepartmentIds,
			@RequestParam(value ="departmentId", required = true) String departmentId,
			@RequestParam(value ="type", required = true) String type){
		OpreateResult opreateResult = new OpreateResult();
		if(subDepartmentIds == null){
				opreateResult.setStatusCode("300");
				opreateResult.setMessage("请勾选下级部门!");
				return opreateResult;
			}
		if(type.equals("1")){
		sysDepartmentAuthService.deleteAllByDepartmentId(departmentId);
		}
		for(int i=0;i<subDepartmentIds.length;i++){
			HashMap<String, String> parameterMap = new HashMap<String, String>();
			if(type.equals("4") || type.equals("3")){
			parameterMap.put("departmentId", departmentId);
			parameterMap.put("subDepartmentId", subDepartmentIds[0]);
			sysDepartmentAuthService.deleteByDepartmentId(parameterMap);
			}
			SysDepartmentAuth sysDepartmentAuth = new SysDepartmentAuth();
			sysDepartmentAuth.setDepartmentId(departmentId);
			sysDepartmentAuth.setSubDepartmentId(subDepartmentIds[i]);
			sysDepartmentAuthService.insertSubDepartments(sysDepartmentAuth);
		}
		opreateResult.setStatusCode("200");
		opreateResult.setMessage("选择成功!");
		opreateResult.setNavTabId("delectSubDepartmentTable");
		return opreateResult;
		}
	
	/**
	 * 清空从部门
	 */
	@ResponseBody
	@RequestMapping("/unSubDepartment")
	public OpreateResult AuthDepartment(HttpServletRequest request,
			@RequestParam(value ="departmentId", required = false) String departmentId,
			@RequestParam(value ="subDepartmentIds", required = false) String[] subDepartmentIds,
			@RequestParam(value ="type", required = true) String type){
		OpreateResult opreateResult = new OpreateResult();
		if(type.equals("2")){
		int a = sysDepartmentAuthService.deleteAllByDepartmentId(departmentId);
		if(a != 0){
		opreateResult.setStatusCode("200");
		opreateResult.setMessage("清空下级部门成功!");
		opreateResult.setNavTabId("delectSubDepartmentTable");
		return opreateResult;
		}else{
		opreateResult.setStatusCode("300");
		opreateResult.setMessage("尚未选择下级部门!");
		opreateResult.setCallbackType("closeCurrent");
		return opreateResult;
		}
		}else{
			HashMap<String, String> parameterMap = new HashMap<String, String>();
			parameterMap.put("departmentId", departmentId);
			parameterMap.put("subDepartmentId", subDepartmentIds[0]);
			sysDepartmentAuthService.deleteByDepartmentId(parameterMap);
			opreateResult.setStatusCode("200");
			opreateResult.setNavTabId("delectSubDepartmentTable");
			opreateResult.setMessage("取消成功!");
		}
		return opreateResult;
	}
	
	/**
	 * 删除部门
	 */
	@ResponseBody
	@RequestMapping("/toDelete")
	public OpreateResult toDeleteDepartment(HttpServletRequest request,String StselectedDepartmentId){
		departmentService.isdelete(StselectedDepartmentId);
		OpreateResult opreateResult = new OpreateResult();
		opreateResult.setStatusCode("200");
		opreateResult.setNavTabId("departmentList");
		opreateResult.setMessage("删除成功");
		opreateResult.setCallbackType("closeCurrent");
		return opreateResult;
	}
	
	/**
	 * 删除所选择地区
	 * 
	 */
	@ResponseBody
	@RequestMapping("/toDeleteArea")
	public OpreateResult toDeleteArea(HttpServletRequest request,@RequestParam(value="ids",required=true)String ids){
		
		String[] areaIds = request.getParameterValues("ids");
		if(ids != null && areaIds.length>1){
			String[] areaId = areaIds[1].split(",");
				for(int i = 0;i<areaId.length;i++){
					areaService.isdelete(new String(areaId[i]));
				}
			}else{
				areaService.isdelete(ids);
			}
		OpreateResult result = new OpreateResult();
		result.setStatusCode("200");
		result.setNavTabId("areaManager");
		result.setMessage("删除成功");
		return result;
	}
	
	
	/**
	 * 跳转到修改地区页面
	 * 
	 */
	@RequestMapping("/toeditArea")
	public String toeditArea(HttpServletRequest request, Model model) {
		base(request);
		String selectedDepartmentId = request.getParameter("areaId");
		SysAreaInfo areaInfo = areaService.selectAreaCodeByAreaId(selectedDepartmentId);
		SysDepartmentInfo sysDepartmentInfo = departmentService.selectDapartmentInfoByID(selectedDepartmentId);
		request.setAttribute("sysDepartmentInfo", sysDepartmentInfo);
		
		List<SysAreaInfo> areaList = areaService.getArea();
		request.setAttribute("areaList", areaList);
		
		request.setAttribute("areaInfo", areaInfo);
		return "admin/department/editArea";
	}
	
	/**
	 * 修改地区信息
	 * 
	 */
	@ResponseBody
	@RequestMapping("/editArea")
	public OpreateResult editArea(HttpServletRequest request,SysAreaInfo areaInfo){
		areaService.updateInfo(areaInfo);
		OpreateResult result = new OpreateResult();
		result.setStatusCode("200");
		result.setNavTabId("areaManager");
		result.setMessage("操作成功");
		result.setCallbackType("closeCurrent");
		return result;
	}
	
	/**
	 * 跳转到增加地区页面
	 * 
	 */
	@RequestMapping("/toaddArea")
	public String toaddArea(HttpServletRequest request,Model model){
		List<SysAreaInfo> areaList = areaService.getArea();
		request.setAttribute("areaList", areaList);
		
		return "admin/department/addArea";
	}
	/**
	 * 新增新地区
	 * 
	 */
	@ResponseBody
	@RequestMapping("/addArea")
	public OpreateResult addArea(HttpServletRequest request,@ModelAttribute SysAreaInfo sysAreaInfo){
		areaService.insertInfo(sysAreaInfo);
		OpreateResult opreateResult = new OpreateResult();
		opreateResult.setStatusCode("200");
		opreateResult.setNavTabId("areaManager");
		opreateResult.setMessage("操作成功");
		opreateResult.setCallbackType("closeCurrent");
		return opreateResult;
	}
	/**
	 * 增加地区时，检查用户输入的地区号有无重复
	 * @throws IOException 
	 * 
	 */
	@RequestMapping("/checkAreaCode")
	public void checkAreaCode(HttpServletRequest request,HttpServletResponse response, String areaCode) {
		List<SysAreaInfo> areaList = areaService.getArea();
		if(areaCode!=""){
			boolean flag = false;
			try {
				for (int i = 0; i < areaList.size(); i++) {
					if(areaCode.equals(areaList.get(i).getAreaCode())){
						flag = true;
					}
				}
				response.getWriter().print(flag);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().print("else");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 获取所有的地区、
	 * 
	 * 
	 */
	@RequestMapping("/showAreaList")
	public String showAreaList(HttpServletRequest request, Model model,@ModelAttribute("sysAreaInfo")SysAreaInfo sysAreaInfo,
			@RequestParam(value ="pageNum", required = false) Integer pageNum ,
			@RequestParam(value ="numPerPage", required = false) Integer numPerPage,
			String areaCode,
			String areaName) {
		Page<SysAreaInfo> page=new Page<SysAreaInfo>();
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		System.out.println(page);
		if(sysAreaInfo==null) sysAreaInfo=new SysAreaInfo();
		page.setQueryBean(sysAreaInfo);
		page=areaService.selectAreasByPage(page);
		request.setAttribute("pageList", page);
		request.setAttribute("areaCode", areaCode);
		request.setAttribute("areaName", areaName);
		List<SysAreaInfo> areaList = areaService.getArea();
		request.setAttribute("areaList", areaList);
		request.setAttribute("sysAreaInfo", sysAreaInfo);
		return "admin/department/areaList";
	}
	/**
	 * 修改地区时，检查用户输入的地区号有无重复
	 * @throws IOException 
	 * 
	 */
	@RequestMapping("/checkAreaCode2")
	public void checkAreaCode2(HttpServletRequest request,HttpServletResponse response,String areaId, String areaCode) {
		List<SysAreaInfo> areaList = areaService.getArea();
		SysAreaInfo info = areaService.selectAreaCodeByAreaId(areaId);
		
		if(areaCode!=""){
			boolean flag = false;
			
			try {
				if(areaCode.equals(info.getAreaCode())){
					flag=false;
					response.getWriter().print(flag);
					return;
				}
				for (int i = 0; i < areaList.size(); i++) {
					if(areaCode.equals(areaList.get(i).getAreaCode())){
						flag = true;
					}
				}
				response.getWriter().print(flag);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().print("else");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
//	--------------------------------------------------------地区—部门管理第二版本
	/**
	 * 跳转到地区--部门页面
	 */
	@RequestMapping("/showAreaDepartmentList")
	public String showAreaDepartmentList(HttpServletRequest request, HttpSession session,Model model,
			@ModelAttribute("sysAreaInfo")SysAreaInfo sysAreaInfo,
			String areaCode,
			String areaName,
			String departmentName,
			String departmentNameq,
			String type,
			@RequestParam(value ="pageNum", required = false) Integer pageNum ,
			@RequestParam(value ="numPerPage", required = false) Integer numPerPage){
		Page<SysAreaInfo> page=new Page<SysAreaInfo>();
		String departmentName3 = (String) session.getAttribute("departmentName3");
			if(departmentName!=null||!StringUtil.isBlank(departmentName)){
				session.setAttribute("departmentName3", "");
			}else{
				departmentName = departmentName3;
			}
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}else{
			page.setNumPerPage(25);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		if(sysAreaInfo==null) sysAreaInfo=new SysAreaInfo();
		page.setQueryBean(sysAreaInfo);
		page=areaService.selectAreasByPage(page);
		request.setAttribute("pageList", page);
		request.setAttribute("areaCode", areaCode);
		request.setAttribute("areaName", areaName);
		List<SysAreaInfo> areaList = areaService.getArea();
		request.setAttribute("areaList", areaList);
		//根据name模糊查询
		if(departmentName != null && !StringUtil.isBlank(departmentName)){
			List<SysDepartmentInfo> departmentList = departmentService.selectSysDepartmentInfoByName(departmentName.trim());
			Collections.reverse(departmentList);
			request.setAttribute("departmentList", departmentList);
			List<SysDepartmentInfo> departmentList1 = departmentService.selectSysDepartmentInfo();
			request.setAttribute("departmentList1", departmentList1);
		}
		request.setAttribute("departmentName", departmentName);
		request.setAttribute("sysAreaInfo", sysAreaInfo);
		return "admin/areaDepartment/areaDepartmentList";
	}

	/**
	 * 删除所选择地区
	 * 
	 */
	@ResponseBody
	@RequestMapping("/deleteArea")
	public OpreateResult deleteArea(HttpServletRequest request,@RequestParam(value="ids",required=true)String ids){
		
		String[] areaIds = request.getParameterValues("ids");
		if(ids != null && areaIds.length>1){
			String[] areaId = areaIds[1].split(",");
				for(int i = 0;i<areaId.length;i++){
					areaService.isdelete(new String(areaId[i]));
				}
			}else{
				areaService.isdelete(ids);
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
	@RequestMapping("/toaddAreaTow")
	public String toaddAreaTow(HttpServletRequest request,Model model){
		List<SysAreaInfo> areaList = areaService.getArea();
		request.setAttribute("areaList", areaList);
		String type = "add";
		request.setAttribute("add", type);
		return "admin/areaDepartment/addEditArea";
	}
	

	/**
	 * 跳转到修改地区页面
	 * 
	 */
	@RequestMapping("/toEditAreaTow")
	public String toEditAreaTow(HttpServletRequest request,Model model,String selectedDepartmentId){
		base(request);
		SysAreaInfo areaInfo = areaService.selectAreaCodeByAreaId(selectedDepartmentId);
		SysDepartmentInfo sysDepartmentInfo = departmentService.selectDapartmentInfoByID(selectedDepartmentId);
		request.setAttribute("sysDepartmentInfo", sysDepartmentInfo);
		List<SysAreaInfo> areaList = areaService.getArea();
		request.setAttribute("areaList", areaList);
		String type = "edit";
		request.setAttribute("edit", type);
		request.setAttribute("areaInfo",areaInfo);
		return "admin/areaDepartment/addEditArea";
	}
	
	/**
	 * 新增或修改地区
	 * 
	 */
	@ResponseBody
	@RequestMapping("/addAreaTow")
	public OpreateResult addAreaTow(HttpServletRequest request,@ModelAttribute SysAreaInfo areaInfo){
		OpreateResult opreateResult = new OpreateResult();
		if(areaInfo == null || StringUtil.isBlank(areaInfo.getAreaId())){
			areaService.insertInfo(areaInfo);
		}else{
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
	public String areaDepartment(HttpServletRequest request,
			String departmentAreaId,
			String departmentName,
			@RequestParam(value ="pageNum", required = false) Integer pageNum ,
			@RequestParam(value ="numPerPage", required = false) Integer numPerPage){
		Page<SysDepartmentInfo> page = new Page<SysDepartmentInfo>();
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}else{
			page.setNumPerPage(25);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		SysDepartmentInfo sysDepartmentInfo = new SysDepartmentInfo();
		sysDepartmentInfo.setDepartmentAreaId(departmentAreaId);
		sysDepartmentInfo.setDepartmentName(departmentName);
		page.setQueryBean(sysDepartmentInfo);
		page = departmentService.selectSysDepartmentInfoByID(page);
		List<SysDepartmentInfo> departmentList = departmentService.selectSysDepartmentInfo();
		request.setAttribute("departmentList", departmentList);
		List<SysAreaInfo> areaList = areaService.getArea();
		request.setAttribute("areaList", areaList);
		request.setAttribute("areaDepartmentPageList", page);
		request.setAttribute("departmentName", departmentName);
		return "admin/areaDepartment/rightDepartmentList";
	}
	
	/**
	 * 跳转到新增或修改部门页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAddTow")
	public String toAddTow(HttpServletRequest request, Model model,String selectedDepartmentId,String departmentName) {
		String type = "add";
		base(request);
		if(selectedDepartmentId!=null&&!StringUtil.isBlank(selectedDepartmentId)){
			SysDepartmentInfo sysDepartmentInfo = departmentService.selectDapartmentInfoByID(selectedDepartmentId);
			request.setAttribute("sysDepartmentInfo", sysDepartmentInfo);
			request.setAttribute("departmentName", departmentName);
			type = "edit";
		}
		request.setAttribute("type", type);
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
	@RequestMapping("/addEditDepartment")
	public OpreateResult addEditDepartment(HttpServletRequest request, 
			@ModelAttribute SysDepartmentInfo sysDepartmentInfo,String type,String departmentId) {
		String parentDepartmentIds = request.getParameter("parentDepartment");
		String departmentName = "";
		String parentDepartmentId = "";
		if(parentDepartmentIds!=null&&!StringUtil.isBlank(parentDepartmentIds)){
			parentDepartmentId = parentDepartmentIds.substring(0,parentDepartmentIds.lastIndexOf("-"));
		}
			if(type.equals("add")){
				sysDepartmentInfo.setParentDepartmentId(parentDepartmentId);
				departmentService.insertDepartmentInfo(sysDepartmentInfo);
				SysAreaInfo SysAreaInfo = areaService.selectAreaCodeByAreaId(sysDepartmentInfo.getDepartmentAreaId());
				departmentName =  SysAreaInfo.getAreaName();
			}else{
				SysAreaInfo SysAreaInfo = areaService.selectAreaCodeByAreaId(sysDepartmentInfo.getDepartmentAreaId());
				sysDepartmentInfo.setParentDepartmentId(parentDepartmentId);
				departmentService.updateDapartmentInfoByID(sysDepartmentInfo);
				departmentName = SysAreaInfo.getAreaName();
			}
		request.getSession().setAttribute("departmentName3", departmentName);
		OpreateResult opreateResult = new OpreateResult();
		opreateResult = new OpreateResult("200", "操作成功!", "areaDepartmentManager", "closeCurrent", "");
		return opreateResult;
	}
	/**
	 * 删除部门
	 */
	@ResponseBody
	@RequestMapping("/toDeleteTow")
	public OpreateResult toDeleteTow(HttpServletRequest request,String selectedDepartmentId,String departmentName,
			@RequestParam(value="ids",required=false)String ids,String departmentName2){
		if(ids!=null||!StringUtil.isBlank(ids)){
			String ids1[] = ids.split(",");
			for (int i = 0; i < ids1.length; i++) {
				departmentService.isdelete(ids1[i]);
			}
		}else{
			departmentService.isdelete(selectedDepartmentId);
		}
		if(departmentName==null||StringUtil.isBlank(departmentName)){
			String departmentName3[] = departmentName2.split("--");
			departmentName = departmentName3[0];
		}
		request.getSession().setAttribute("departmentName3", departmentName);
		OpreateResult opreateResult = new OpreateResult();
		opreateResult = new OpreateResult("200", "操作成功!", "areaDepartmentManager", "", "");
		return opreateResult;
	}
	
	/**
	 * 跳转到新增从部门
	 */
	@RequestMapping("/toSubDepartmentTow")
	public String toAuthDepartmentTow(HttpServletRequest request,
			@RequestParam(value ="pageNum", required = false) Integer pageNum,
			@RequestParam(value ="numPerPage", required = false) Integer numPerPage,
			@RequestParam(value ="StselectedDepartmentId", required = false) String StselectedDepartmentId){
		Map<String,Object> params = new HashMap<String,Object>();
		Page<SysDepartmentInfo> page=new Page<SysDepartmentInfo>();
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		params.put("StselectedDepartmentId", StselectedDepartmentId);
		page.setQueryBean(params);
		Page<SysDepartmentInfo> pageList=departmentService.selectPage(page);
		
		
		String selectedDepartmentId = request.getParameter("StselectedDepartmentId");
		SysDepartmentInfo sysDepartmentInfo = departmentService.selectDapartmentInfoByID(selectedDepartmentId);
		List<SysDepartmentAuth> sysDepartmentAuthList = sysDepartmentAuthService.getNoticeDepartmentAuth(selectedDepartmentId);
		request.setAttribute("pageList", pageList);
		request.setAttribute("sysDepartmentAuthList", sysDepartmentAuthList);
		request.setAttribute("sysDepartmentInfo", sysDepartmentInfo);
		return "admin/department/subDepartment";
	}
	
	/**
	 * 跳转到选择配置模版
	 */
	@RequestMapping("/toSelectTemplate")  
    public String toSelectTemplate(HttpServletRequest request,Model model,@RequestParam(value ="StselectedDepartmentId", required = true) String StselectedDepartmentId,@ModelAttribute("excelTemplate")ExcelTemplate excelTemplate,
    		@RequestParam(value ="pageNum", required = false) Integer pageNum ,@RequestParam(value ="numPerPage", required = false) Integer numPerPage ){ 
		Page<ExcelTemplate> page=new Page<ExcelTemplate>();
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		page=jsonConfigService.getExcelTemplatePage(page, excelTemplate);
		List<SysDepartmentTemplate> departmentTemplates=departmentTemplateService.getDepartmentTemplate(StselectedDepartmentId);
		Map<Integer,String> periodType=Constant.periodType();
		request.setAttribute("periodType", periodType);
		request.setAttribute("pageList", page);
		request.setAttribute("departmentId", StselectedDepartmentId);
		request.setAttribute("excelTemplate", excelTemplate);
		request.setAttribute("departmentTemplates", departmentTemplates);
		return "admin/template/departmentSelect";
	}
}
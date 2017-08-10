package com.ahcd.controller.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ahcd.common.OpreateResult;
import com.ahcd.pojo.SysAreaInfo;
import com.ahcd.pojo.SysDepartmentInfo;
import com.ahcd.pojo.SysWebMenu;
import com.ahcd.service.ISysAreaInfoService;
import com.ahcd.service.ISysDepartmentInfoService;
import com.ahcd.service.ISysWebMenuService;

@Controller	
@RequestMapping("/admin/department")
public class DepartmentController {
	@Resource  
	private ISysAreaInfoService areaService; 
	@Resource  
	private ISysDepartmentInfoService departmentService; 
	
	@Resource
	private ISysWebMenuService sysWebMenuService; 
	
	@RequestMapping("/show")
	public String show(HttpServletRequest request,Model model){
		List<SysAreaInfo> areaList=areaService.getArea();
		if(areaList!=null && areaList.size()>0){
			
			for (int i = 0; i < areaList.size(); i++) {
				 List<SysDepartmentInfo> departmentList=
						 departmentService.getSysDepartmentInfoByAreaId(areaList.get(i).getAreaId());
				 areaList.get(i).setDepartmentList(departmentList);
			}
		}
		request.setAttribute("areaList", areaList);
		return "admin/department/list";
	}
	
	/**
	 * 跳转到新增页面 
	 */
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request,Model model){
		base(request);
		return "admin/department/add";
	}
	
	/**
	 * 新增
	 * 
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
	 * 
	   * 功能说明    :部门对应展现菜单管理 
	   * @author   : fei yang 
	   * @version ：2016年10月24日 下午4:44:01 
	   * @param request
	   * @param model
	   * @return
	 */
	@RequestMapping("/toWebMenu")
	public String toWebMenu(HttpServletRequest request,Model model){
		List<SysAreaInfo> areaList=areaService.getArea();
		if(areaList!=null && areaList.size()>0){
			for (int i = 0; i < areaList.size(); i++) {
				 List<SysDepartmentInfo> departmentList=
						 departmentService.getSysDepartmentInfoByAreaId(areaList.get(i).getAreaId());
				 areaList.get(i).setDepartmentList(departmentList);
			}
		}
		request.setAttribute("areaList", areaList);
		SysWebMenu bean=new SysWebMenu();
		bean.setMenuType(new BigDecimal(1));
		List<SysWebMenu> oneList=		sysWebMenuService.getAllByType(bean);
		bean.setMenuType(new BigDecimal(2));
		List<SysWebMenu> twoList=		sysWebMenuService.getAllByType(bean);
		bean.setMenuType(new BigDecimal(3));
		List<SysWebMenu> threeList=		sysWebMenuService.getAllByType(bean);
		model.addAttribute("oneList", oneList);
		model.addAttribute("twoList", twoList);
		model.addAttribute("threeList", threeList);

		
		return "admin/department/toWebMenu";
	}
	
	private void base(HttpServletRequest request) {
		List<SysAreaInfo> areaList = areaService.getArea();
		List<SysDepartmentInfo> departmentList = departmentService.getDepartment();
		request.setAttribute("areaList", areaList);
		request.setAttribute("departmentList", departmentList);
	}
	
}

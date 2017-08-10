package com.ahcd.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysAreaInfo;
import com.ahcd.pojo.SysDepartmentInfo;
import com.ahcd.service.ISysAreaInfoService;
import com.ahcd.service.ISysDepartmentInfoService;

@Controller	
@RequestMapping("/admin/department")
public class DepartmentController {
	@Resource  
	private ISysAreaInfoService areaService; 
	@Resource  
	private ISysDepartmentInfoService departmentService; 
	
	@RequestMapping("/show")
	public String show(HttpServletRequest request,Model model){
		List<SysAreaInfo> areaList=areaService.getArea();
		request.setAttribute("areaList", areaList);
		return "admin/department/areaList";
	}
	@RequestMapping("/depList")
	public String depList(HttpServletRequest request,Model model, @RequestParam(value ="areaId", required = true) String areaId,@RequestParam(value ="pageNum", required = false) Integer pageNum ,@RequestParam(value ="numPerPage", required = false) Integer numPerPage){
		Page<SysDepartmentInfo> page=new Page<SysDepartmentInfo>();
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		SysDepartmentInfo sysDepartmentInfo=new SysDepartmentInfo();
		sysDepartmentInfo.setDepartmentAreaId(areaId);
		page.setQueryBean(sysDepartmentInfo);
		page=departmentService.selectPage(page);
		request.setAttribute("pageList", page);
		request.setAttribute("areaId", areaId);
		return "admin/department/departmentList";
	}
}

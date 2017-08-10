package com.ahcd.controller.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentInfo;
import com.ahcd.pojo.SysDepartmentLead;
import com.ahcd.pojo.SysMessage;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.ISysDepartmentInfoService;
import com.ahcd.service.ISysDepartmentLeadService;
import com.ahcd.service.IUserService;
import com.ahcd.service.impl.SysDepartmentInfoServiceImpl;

@Controller
@RequestMapping("admin/departmentLead")
public class DepartmentLeadController {
	@Resource
	private IUserService userService;

	@Resource
	private ISysDepartmentLeadService sysDepartmentLeadService; 
	
	@Resource
	private ISysDepartmentInfoService departmentService;
	/**
	 * 配置列表
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request,
			@RequestParam(value ="pageNum", required = false) Integer pageNum ,
			@RequestParam(value ="numPerPage", required = false) Integer numPerPage,
			@RequestParam(value ="leadId", required = false) String leadId){
		Page<SysDepartmentLead> page=new Page<SysDepartmentLead>();
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		System.out.println(page);
		//Map<String,Object> params = new HashMap<String,Object>();
		//params.put("leadId", leadId);
		//page.setQueryBean(params);
		SysDepartmentLead sysDepartmentLead = new SysDepartmentLead();
		sysDepartmentLead.setLeadId(leadId);
		page.setQueryBean(sysDepartmentLead);
		Page<SysDepartmentLead> pageList = sysDepartmentLeadService.selectSysDepartmentLeadPage(page);
		request.setAttribute("pageList", pageList);
		request.setAttribute("leadId", leadId);
		return "admin/departmentLead/list";
	}
	
	/**
	 * 跳转到新增或修改页面
	 */
	@RequestMapping("/toSaveOrEdit")
	public String toSaveOrUpdate(HttpServletRequest request,BigDecimal id){
		//List<SysReportUser> sysReportUserList = userService.getUserInfo();
		//request.setAttribute("sysReportUserList", sysReportUserList);
		List<SysDepartmentInfo> sysDepartmentList = departmentService.getDepartment();
		if(id!=null && !StringUtil.isBlank(id)){
			SysDepartmentLead sysDepartmentLead = sysDepartmentLeadService.selectByPrimaryKey(id);
			request.setAttribute("sysDepartmentLead", sysDepartmentLead);
			request.setAttribute("actionMethod", "add");
		}
		request.setAttribute("sysDepartmentList", sysDepartmentList);
		//request.setAttribute("actionMethod", "add");
		return "admin/departmentLead/addOrEdit";
	}
	
	/**
	 * 新增或修改方法
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveOrEdit")
	public OpreateResult saveOrUpdate(HttpServletRequest request){
		String processorId = request.getParameter("processorId");
		String departmentId = request.getParameter("departmentId");
		String state = request.getParameter("state");
		String method = request.getParameter("do");
		BigDecimal status = new BigDecimal("1");
		if(state.equals("不可用")){
			status = new BigDecimal("0");
		}
		int a = 6;
		int b = 8;
		SysDepartmentLead sysDepartmentLead = new SysDepartmentLead();
		sysDepartmentLead.setLeadId(processorId);
		sysDepartmentLead.setDepartmentId(departmentId);
		sysDepartmentLead.setStatus(status);
		/*SysDepartmentLead checkSysDepartmentLead = sysDepartmentLeadService.checkSysdepartmentLeadById(new BigDecimal(processorId));
		if(checkSysDepartmentLead == null){
			a = sysDepartmentLeadService.insert(sysDepartmentLead);
		}else{
			b = sysDepartmentLeadService.updateByPrimaryKey(sysDepartmentLead);
		}*/
		if(method.equals("add")){
			b = sysDepartmentLeadService.updateByPrimaryKey(sysDepartmentLead);
		}else{
			
			a = sysDepartmentLeadService.insert(sysDepartmentLead);
		}
		
		OpreateResult opreateResult = new OpreateResult();
		if(a == 1 || b == 1){
			opreateResult.setStatusCode("200");
			opreateResult.setNavTabId("sysDepartmentLead");
			opreateResult.setMessage("操作成功");
			opreateResult.setCallbackType("closeCurrent");
			return opreateResult;
		}else{
			opreateResult.setStatusCode("300");
			opreateResult.setNavTabId("sysDepartmentLead");
			opreateResult.setMessage("删除失败!");
			return opreateResult;
		}
	}
	
	/**
	 * 单个或批量删除
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public OpreateResult delete(HttpServletRequest request,@RequestParam(value="ids",required = true) BigDecimal ids){
		int a = 6;
		if(ids == null && StringUtil.isBlank(ids)){
			String[] userIds = request.getParameterValues("ids");
			if(userIds != null){
				String[] newIds = userIds[1].split(",");
				for(int i = 0;i<newIds.length;i++){
					a = sysDepartmentLeadService.updateIsDelete(new BigDecimal(newIds[i]));
					if(a != 1){
						break;
					}
				}
			}
		}else{
			a = sysDepartmentLeadService.updateIsDelete(ids);
		}
		
		OpreateResult opreateResult = new OpreateResult();
		if(a == 1){
			opreateResult.setStatusCode("200");
			opreateResult.setNavTabId("sysDepartmentLead");
			opreateResult.setMessage("删除成功");
			return opreateResult;
		}else{
			opreateResult.setStatusCode("300");
			opreateResult.setNavTabId("sysDepartmentLead");
			opreateResult.setMessage("删除失败!");
			return opreateResult;
		}
	}
	
	/**
	 * 检查leadId是否重复
	 * @param request
	 * @param response
	 * @param leadId
	 */
	@RequestMapping("/checkLeadId")
	public void checkLeadId(HttpServletRequest request,HttpServletResponse response,String leadId){
		List<SysDepartmentLead> SysDepartmentLeadList = sysDepartmentLeadService.selectSysdepartmentLeadInfo();
		for(int i=0;i<SysDepartmentLeadList.size();i++){
			if(SysDepartmentLeadList.get(i).getLeadId().equals(leadId)){
				try {
					response.getWriter().print("false");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
//			else{
//				try {
//					response.getWriter().print("true");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
		}
	}
	
	/**
	 * 自动获取前置机Id
	 * 
	 * 
	 */
	@RequestMapping("/getLeadId")
	public void getLeadId(HttpServletRequest request,HttpServletResponse response){
		 String s  = UUID.randomUUID().toString();
		 String newStr = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
		 try {
			response.getWriter().print(newStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 发送消息时查询配置列表
	 * @return
	 */
	@RequestMapping("/looklist")
	public String looklist(HttpServletRequest request, Model model, Page<SysDepartmentLead> page, SysDepartmentLead bean,String status){
		bean.setStatus(new BigDecimal(status));
		page.setQueryBean(bean);
		Page<SysDepartmentLead> pageList = sysDepartmentLeadService.selectSysDepartmentLeadList(page);
		request.setAttribute("pageList", pageList);
		request.setAttribute("bean", bean);
		
		return "admin/departmentLead/looklist";
	}

	
	
	
}

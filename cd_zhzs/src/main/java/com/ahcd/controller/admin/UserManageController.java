package com.ahcd.controller.admin;

import java.math.BigDecimal;
import java.util.List;

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
import com.ahcd.common.PasswordUtil;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysAreaInfo;
import com.ahcd.pojo.SysDepartmentInfo;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.ISysAreaInfoService;
import com.ahcd.service.ISysDepartmentInfoService;
import com.ahcd.service.IUserService;

@Controller
@RequestMapping("/admin/user")
public class UserManageController {
	@Resource
	private ISysDepartmentInfoService departmentService;

	@Resource
	private ISysAreaInfoService areaInfoService;

	@Resource
	private IUserService userService;

	/**
	 * 跳转到用户列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,@ModelAttribute("sysReportUser")SysReportUser sysReportUser,
			@RequestParam(value ="pageNum", required = false) Integer pageNum ,@RequestParam(value ="numPerPage", required = false) Integer numPerPage) {
		Page<SysReportUser> page=new Page<SysReportUser>();
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		System.out.println(page);
		if(sysReportUser==null) sysReportUser=new SysReportUser();
		page.setQueryBean(sysReportUser);
		page=userService.selectUserPage(page);
		request.setAttribute("pageList", page);
		request.setAttribute("sysReportUser", sysReportUser);
		return "admin/user/list";
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/toadd")
	public String toadd(HttpServletRequest request) {
		List<SysDepartmentInfo> departmentList = departmentService.getDepartment();
		request.setAttribute("departmentList", departmentList);
		request.setAttribute("actionMethod", "add");
		return "admin/user/edit";
	}
	/**
	 * 新增方法
	 */
	@ResponseBody
	@RequestMapping("/add")
	public OpreateResult add(HttpServletRequest request, SysReportUser sysReportUser,String departmentId,HttpSession session) {
		if(sysReportUser!=null){
			sysReportUser.setUserPwd(PasswordUtil.generate(Constant.defaultPwd));
		}
		SysDepartmentInfo departmentInfo2 = departmentService.selectDapartmentInfoByID(departmentId);
		if(departmentInfo2==null) {
			OpreateResult opreateResult = new OpreateResult();
			opreateResult.setStatusCode("300");
			opreateResult.setMessage("新增失败，部门信息有误");
			return opreateResult;
		}
		SysAreaInfo sysAreaInfo = areaInfoService.selectAreaCodeByAreaId(departmentInfo2.getDepartmentAreaId());
		if(sysAreaInfo==null) {
			OpreateResult opreateResult = new OpreateResult();
			opreateResult.setStatusCode("300");
			opreateResult.setMessage("新增失败，部门信息有误");
			return opreateResult;
		}
		String areaCode = sysAreaInfo.getAreaCode();
		// 获取userCode
		String userNum = "0" + Integer.toString(userService.selectUserNum());
		if(userNum.length()>2){
			userNum = userNum.replaceFirst("0", "");
		}
		String departmentCode=departmentInfo2.getDepartmentCode();
		// userCode
		String userCode = areaCode + departmentCode + userNum;
		sysReportUser.setUserCode(userCode);
		// 部门名
		sysReportUser.setDepartmentId(departmentId);
		userService.insertUser(sysReportUser);
		OpreateResult opreateResult = new OpreateResult();
		opreateResult.setStatusCode("200");
		opreateResult.setNavTabId("userManage");
		opreateResult.setMessage("新增成功");
		opreateResult.setCallbackType("closeCurrent");
		return opreateResult;
	}
	
	/**
	 * 删除方法
	 */
	
	@ResponseBody
	@RequestMapping("/todeletes")
	public OpreateResult todeletes(HttpServletRequest request,@RequestParam(value="ids",required = true) BigDecimal ids){
		if(ids == null){
		String[] userIds = request.getParameterValues("ids");
		if(userIds != null){
			String[] userId = userIds[1].split(",");
			for(int i = 0;i<userId.length;i++){
				userService.isdelete(new BigDecimal(userId[i]));
			}
		}
		}else{
			userService.isdelete(ids);
		}
		
		OpreateResult result = new OpreateResult();
		result.setStatusCode("200");
		result.setNavTabId("userManage");
		result.setMessage("删除成功");
		return result;
	}
	

	
	/**
	 * 跳转到修改方法
	 */
	@RequestMapping("/toedit")
	public String toedit(HttpServletRequest request,String userCode){
		SysReportUser sysReportUserInfo = userService.selectUserInfoByUserCode(userCode);
		List<SysDepartmentInfo> departmentList = departmentService.getDepartment();
		
		request.setAttribute("departmentList", departmentList);
		request.setAttribute("sysReportUserInfo", sysReportUserInfo);
		request.setAttribute("actionMethod", "edit");
		
		return "admin/user/edit";
	}
	
	/**
	 * 修改方法
	 * @param request
	 * @param department
	 * @param sysReportUser
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/edit")
	public OpreateResult edit(HttpServletRequest request,String departmentId,SysReportUser sysReportUser){
		String userId = request.getParameter("userId");
		if(sysReportUser!=null){
			sysReportUser.setUserPwd(PasswordUtil.generate(Constant.defaultPwd));
		}
	
		SysReportUser sysReportUser2 = userService.getUserById(new BigDecimal(userId));
		sysReportUser.setUserCode(sysReportUser2.getUserCode());
		sysReportUser.setDepartmentId(departmentId);
		//更新用户信息表
		userService.updateInfo(sysReportUser);		
		
		OpreateResult opreateResult = new OpreateResult();
		opreateResult.setStatusCode("200");
		opreateResult.setNavTabId("userManage");
		opreateResult.setMessage("修改成功");
		opreateResult.setCallbackType("closeCurrent");
		return opreateResult;
	}
	
	@ResponseBody
	@RequestMapping("/resetPwd")
	public OpreateResult resetPwd(HttpServletRequest request,@RequestParam(value ="userCode", required = true) String userCode){
		int num=userService.resetPwd(userCode);
		OpreateResult opreateResult = new OpreateResult();
		if(num==1){
			opreateResult.setStatusCode("200");
			opreateResult.setNavTabId("userManage");
			opreateResult.setMessage("重置成功");
			opreateResult.setCallbackType("closeCurrent");
		}else{
			opreateResult.setStatusCode("300");
			opreateResult.setNavTabId("userManage");
			opreateResult.setMessage("重置失败");
			opreateResult.setCallbackType("closeCurrent");
		}
		return opreateResult;
	}
}

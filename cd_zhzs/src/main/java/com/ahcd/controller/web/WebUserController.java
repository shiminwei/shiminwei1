package com.ahcd.controller.web;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ahcd.common.Constant;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.PasswordUtil;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.IUserService;

@Controller
@RequestMapping("/web/user")
public class WebUserController {
	@Resource
	private IUserService userService;
	

	/**
	 * 跳转到修改密码
	 * @return
	 */
	@RequestMapping("/toEditPwd")
	public String toEditpwd(){
		return "web/index/editPwd";
	}
	
	@ResponseBody
	@RequestMapping("/editPwd")
	public OpreateResult editPwd(HttpServletRequest request,String newPwd,HttpSession session,HttpServletResponse response){
		OpreateResult opreateResult = new OpreateResult();
		String userCode = session.getAttribute("userName").toString();
		//修改密码
		int a = userService.updatePwdByUserName(userCode,newPwd);
		if(a==1){
			opreateResult.setStatusCode("200");
			opreateResult.setNavTabId("userManage");
			opreateResult.setMessage("修改成功");
			opreateResult.setCallbackType("closeCurrent");
		}else{
			opreateResult.setStatusCode("300");
			opreateResult.setNavTabId("userManage");
			opreateResult.setMessage("修改失败");
			opreateResult.setCallbackType("closeCurrent");
			return opreateResult;
		}
		return opreateResult;
	}
	
	/**
	 * 检测旧密码
	 * @param session
	 * @param oldPwd
	 * @param response
	 */
	@RequestMapping("/checkUserPwd")
	public void checkUserPwd(HttpSession session,String oldPwd,HttpServletResponse response) {
		SysReportUser sysReportUser=(SysReportUser) session.getAttribute(Constant.SESSION_USER);
		boolean flag = PasswordUtil.verify(oldPwd, sysReportUser.getUserPwd());		
		try {
			response.getWriter().print(flag);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}

package com.ahcd.controller;


import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ahcd.common.Constant;
import com.ahcd.common.PasswordUtil;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.IUserService;

@Controller  
@RequestMapping("/")  
public class IndexController {
	 @Resource  
	 private IUserService userService;  

	 
	 
	@RequestMapping("/")  
    public String toLogin(HttpServletRequest request,@ModelAttribute("msg") String msg,@ModelAttribute("loginCode") String loginCode){  
		request.setAttribute("msg",msg);
		request.setAttribute("loginCode",loginCode);
		return "../../login";
	}
	@RequestMapping("/login")  
    public String login(HttpServletRequest request,Model model,HttpSession session,RedirectAttributes attr){  
		String loginCode=request.getParameter("loginCode");
		String loginPwd=request.getParameter("loginPwd");
		session.setAttribute("userName", loginCode);
		if(StringUtil.isBlank(loginCode) || StringUtil.isBlank(loginPwd)){
			//帐号或者密码不能为空
			attr.addFlashAttribute("msg", "帐号或者密码不能为空");
			return "redirect:/";
		}
		SysReportUser u=userService.getUserByLoginCode(loginCode);
		if(u!=null && PasswordUtil.verify(loginPwd, u.getUserPwd())){
			//在Session里保存信息  
	        session.setAttribute(Constant.SESSION_USER, u);  
			return "redirect:/index";
		}else{
			//帐号或者密码不对
			attr.addFlashAttribute("msg", "帐号或密码错误，请重新输入");
			attr.addFlashAttribute("loginCode", loginCode);
			return "redirect:/";
		}
	}
	@RequestMapping("/index")  
    public String index(HttpServletRequest request,Model model){  
		return "web/index/index";
	}
	@RequestMapping("/logout")  
    public String logout(HttpServletRequest request,Model model,HttpSession session,RedirectAttributes attr){  
		session.removeAttribute(Constant.SESSION_USER);
		return "redirect:/";
	}
	
	@RequestMapping("/toLogin")  
    public String toLogin(HttpServletRequest request,Model model,HttpSession session,RedirectAttributes attr){  
		return "../../toLogin";
	}
}

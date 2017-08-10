package com.ahcd.controller;

import java.io.Console;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ahcd.common.Constant;
import com.ahcd.common.PasswordUtil;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.User;
import com.ahcd.service.IUserService;

@Controller  
@RequestMapping("/")  
public class IndexController {
	 @Resource  
	 private IUserService userService;  
	 
	@RequestMapping("/")  
    public String toLogin(HttpServletRequest request,Model model){  
		return "../../login";
	}
	@RequestMapping("/login")  
    public String login(HttpServletRequest request,Model model,HttpSession session){  
		String loginCode=request.getParameter("loginCode");
		String loginPwd=request.getParameter("loginPwd");
		if(StringUtil.isBlank(loginCode) || StringUtil.isBlank(loginPwd)){
			//帐号或者密码不能为空
			return "redirect:/?msg=1";
		}
		User u=userService.getUserByLoginCode(loginCode);
		if(u!=null && PasswordUtil.verify(loginPwd, u.getPassword())){
			//在Session里保存信息  
	        session.setAttribute(Constant.SESSION_USER, u);  
			return "redirect:/index";
		}else{
			//帐号或者密码不对
			return "redirect:/?msg=2";
		}
	}
	@RequestMapping("/index")  
    public String index(HttpServletRequest request,Model model){  
		return "index/index";
	}
}

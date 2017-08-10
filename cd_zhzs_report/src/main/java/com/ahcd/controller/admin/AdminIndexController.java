package com.ahcd.controller.admin;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ahcd.common.OpreateResult;
import com.ahcd.common.PasswordUtil;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.IUserService;

@Controller  
@RequestMapping("/admin")  
public class AdminIndexController {
	@Resource
	private IUserService userService;
	
	@RequestMapping("/index")  
    public String toLogin(HttpServletRequest request,Model model){  
		return "admin/index";
	}
}

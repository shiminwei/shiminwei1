package com.ahcd.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  
@RequestMapping("/admin")  
public class AdminIndexController {
	@RequestMapping("/index")  
    public String toLogin(HttpServletRequest request,Model model){  
		return "admin/index";
	}
}

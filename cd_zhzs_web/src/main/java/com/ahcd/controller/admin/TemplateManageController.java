package com.ahcd.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller	
@RequestMapping("/admin/template")
public class TemplateManageController {
	@RequestMapping("/list")  
    public String list(HttpServletRequest request,Model model){ 
		
		return "admin/template/list";
	}
	@RequestMapping("/toAdd")  
    public String query(HttpServletRequest request,Model model){ 
		
		return "admin/template/add";
	}
}

package com.ahcd.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller	
@RequestMapping("/admin/notice")
public class NoticeManageController {

	@RequestMapping("/list")  
    public String list(HttpServletRequest request,Model model){ 
		
		return "admin/notice/list";
	}
	
	@RequestMapping("/toAdd")  
    public String toAdd(HttpServletRequest request,Model model){ 
		
		return "admin/notice/edit";
	}
}

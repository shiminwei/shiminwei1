package com.ahcd.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ahcd.common.StringUtil;

@Controller  
@RequestMapping("/admin")  
public class AdminMenuController {
	@RequestMapping("/menu")  
    public String menu(HttpServletRequest request,Model model){ 
		String typeStr=request.getParameter("type");
		int type=1;
		if(!StringUtil.isBlank(typeStr)){
			type=Integer.parseInt(typeStr);
		}
		request.setAttribute("type", type);
		return "admin/menu";
	}
}

package com.ahcd.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller	
@RequestMapping("/admin/data")
public class DataQueryController {
	@RequestMapping("/summary")  
    public String list(HttpServletRequest request,Model model){ 
		
		return "admin/dataquery/summary";
	}
	@RequestMapping("/toReportInfo")  
    public String query(HttpServletRequest request,Model model){ 
		
		return "admin/dataquery/toReportInfo";
	}
	@RequestMapping("/queryReportInfo")  
    public String queryReportInfo(HttpServletRequest request,Model model){ 
		
		return "admin/dataquery/queryReportInfo";
	}
}

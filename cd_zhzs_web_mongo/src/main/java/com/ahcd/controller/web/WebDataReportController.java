package com.ahcd.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller	
@RequestMapping("/web/dataReport")
public class WebDataReportController {
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model){
		return "web/dataReport/list";
	}
	
	@RequestMapping("/toReport")
	public String toReport(HttpServletRequest request,Model model){
		return "web/dataReport/report";
	}
	@RequestMapping("/toQueryData")
	public String toQueryData(HttpServletRequest request,Model model){
		return "web/dataReport/toQueryData";
	}
	@RequestMapping("/queryData")
	public String queryData(HttpServletRequest request,Model model){
		return "web/dataReport/queryData";
	}
}

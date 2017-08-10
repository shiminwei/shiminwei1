package com.ahcd.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller	
@RequestMapping("/web/notice")
public class WebNoticeController {
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model){
		return "web/notice/list";
	}
	@RequestMapping("/detail")
	public String detail(HttpServletRequest request,Model model){
		return "web/notice/detail";
	}
}

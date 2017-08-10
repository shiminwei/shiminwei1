package com.ahcd.controller.web;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ahcd.service.GbhyService;
import com.ahcd.service.HyQuertyService;

@Controller
@RequestMapping("/web")
public class WebIndexContoller {
	@Resource
	private GbhyService gbhyService;
	@Resource
	private HyQuertyService hyQuertyService;


	
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model) {
		return "web/index/main";
	}


	@RequestMapping("/error")
	public String indexError(HttpServletRequest request, Model model) {
		return "web/index/error";
	}

}

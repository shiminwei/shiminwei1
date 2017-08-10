package com.ahcd.controller.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("web/url")
public class WebUrlShowController {

/**
 * 
   * 功能说明    : Jvopit立方体主页面
   * @author   : fei yang 
   * @version ：2017年3月22日 上午9:24:49 
   * @param request
   * @param model
   * @param query
   * @return
 */

	@RequestMapping("/show")
	public String showCyList(HttpServletRequest request, Model model, String queryUrl) {
		model.addAttribute("queryUrl", queryUrl);
		return "web/url/show";
	}
	
	/**
	 * 
	   * 功能说明    : Jvopi繁忙错误页面busy
	   * @author   : fei yang 
	   * @version ：2017年3月22日 上午9:24:49 
	   * @param request
	   * @param model
	   * @param query
	   * @return
	 */
	@RequestMapping("/error")
	public  String toError(Model model, String query) {
		return  "web/index/error";
	}
}

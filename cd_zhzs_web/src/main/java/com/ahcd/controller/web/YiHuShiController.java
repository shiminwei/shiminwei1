package com.ahcd.controller.web;

//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.QyBean;
import com.ahcd.service.YiHuShiService;

/**
 * @author : fei yang
 * @version ：2016年12月20日 上午11:02:41
 */
@Controller
@RequestMapping("web/yiHuShi")
public class YiHuShiController {

	@Resource
	private YiHuShiService yiHuShiService;

	@RequestMapping("list")
	public String getList(HttpServletRequest request, Model model) {
		return "web/yiHuShi/list";
	}

	@RequestMapping("qyList")
	public String getQyList(HttpServletRequest request, Model model, Page<String[]> page, QyBean qyBean,
			String startDate, String endDate) {
		page = yiHuShiService.toQuery(page, qyBean);
		model.addAttribute("qyBean", qyBean);
		model.addAttribute("pageList", page);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "web/yiHuShi/qyList";
	}

}

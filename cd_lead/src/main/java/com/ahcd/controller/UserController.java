package com.ahcd.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.impl.UserServiceImpl;

@Controller
@RequestMapping("user")
public class UserController {

	@Resource
	private UserServiceImpl userService;

	@RequestMapping("list")
	public String getList(HttpServletRequest request, Model model, Page<SysReportUser> page, SysReportUser user) {
		page.setQueryBean(user);
		page = userService.selectUserPage(page);
		model.addAttribute("pageList", page);
		return "user/list";
	}
}

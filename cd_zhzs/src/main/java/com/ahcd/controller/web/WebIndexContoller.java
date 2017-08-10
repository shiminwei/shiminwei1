package com.ahcd.controller.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ahcd.common.Constant;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysNotice;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.ISysNoticeService;

@Controller	
@RequestMapping("/web")
public class WebIndexContoller {
	@Resource
	private ISysNoticeService sysNoticeService;
	
	@RequestMapping("/index")
	public String list(HttpServletRequest request,Model model,HttpSession session){
		Map<String,Object> params=new HashMap<String,Object>();
		SysReportUser user = (SysReportUser)session.getAttribute(Constant.SESSION_USER);
		//获取最近十条公告
		Page<SysNotice> page=new Page<SysNotice>();
		page.setNumPerPage(10);
		page.setPageNum(1);
		params.put("departmentId", user.getDepartmentId());
		params.put("userId", user.getUserId());
		page.setQueryBean(params);
		Page<SysNotice> pageList=sysNoticeService.getNoticePageByUser(page);
		request.setAttribute("pageList", pageList);
		return "web/index/main";
	}
}

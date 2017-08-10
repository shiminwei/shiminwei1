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
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysNotice;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.ISysNoticeService;
import com.ahcd.service.impl.SysReportLogServiceImpl;
import com.ahzd.service.ExcelTemplateMongoService;

@Controller	
@RequestMapping("/web")
public class WebIndexContoller {
	@Resource
	private ISysNoticeService sysNoticeService;
	
	@Resource
	private SysReportLogServiceImpl sysReportLogService;

	@Resource  
	private ExcelTemplateMongoService excelTemplateMongoService; 
	
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
		
		Page<ExcelTemplate> excelTemplatePage = new Page<ExcelTemplate>();
		excelTemplatePage.setNumPerPage(99999);
		excelTemplatePage = excelTemplateMongoService.getPageListByUserId(excelTemplatePage, user.getUserId());
		if(excelTemplatePage.getResult() != null && excelTemplatePage.getResult().size() > 0){
			excelTemplatePage = sysReportLogService.getIsReportList(excelTemplatePage, user.getDepartmentId());
		}	
		model.addAttribute("excelTemplatePage", excelTemplatePage);
		return "web/index/main";
	}
}

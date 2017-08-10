package com.ahcd.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysGbhy;
import com.ahcd.service.ISysGbhyService;

@Controller
@RequestMapping("admin/gbhy")
public class WebHangYeController {
	@Resource
	private ISysGbhyService gbhyService;
	/**
	 * 跳转到国标行业列表
	 */
	@RequestMapping("toList")
	public String list(HttpServletRequest request,
			@RequestParam(value ="name_cy", required = false) String name_cy,
			@RequestParam(value = "name_ml",required = false) String name_ml,
			@RequestParam(value = "name_dl",required = false) String name_dl,
			@RequestParam(value ="pageNum", required = false) Integer pageNum,
			@RequestParam(value ="numPerPage", required = false) Integer numPerPage) {
		Map<String,Object> params = new HashMap<String,Object>();
		Page<SysGbhy> page=new Page<SysGbhy>();
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		params.put("name_cy",name_cy);
		params.put("name_ml",name_ml);
		params.put("name_dl",name_dl);
		page.setQueryBean(params);
		Page<SysGbhy> pageList=gbhyService.getNoticePage(page);
		request.setAttribute("pageList", pageList);
		request.setAttribute("name_cy", name_cy);
		request.setAttribute("name_ml", name_ml);
		request.setAttribute("name_dl", name_dl);
		return "admin/gbhy/list";
	}
	
}

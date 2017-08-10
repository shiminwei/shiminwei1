package com.ahcd.controller.admin;

import java.math.BigDecimal;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ahcd.common.OpreateResult;
import com.ahcd.pojo.IndustryInfo;
import com.ahcd.pojo.Page;
import com.ahcd.service.impl.IndustryInfoServiceImpl;


/**
 * 说明 :行业信息
 * @author : chenxt
 * @version ：2017年02月23日 
 */
@Controller
@RequestMapping("admin/industryInfo")
public class IndustryInfoController {

	@Resource
	private IndustryInfoServiceImpl industryInfoService;
	

	/**
	 * 
	 * 功能说明 :查询行业信息表列表
	 *
	 * @author : chenxt
	 * @version ：2017-02-23
	 * @param request
	 * @param model
	 * @param page
	 * @param bean
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Page<IndustryInfo> page, IndustryInfo bean) {
		page = industryInfoService.selectIndustryInfoPage(page, bean);
		request.setAttribute("pageList", page);
		request.setAttribute("bean", bean);
		return "admin/industryInfo/list";
	}
	
	/**
	 * 
	 * 功能说明 :新增或者修改跳转
	 * 
	 * @author : chenxt
	 * @version ：2017-02-23
	 * @param request
	 * @param model
	 * @param industryId
	 * @return
	 */
	@RequestMapping("toSaveOrUpdate")
	public String toSaveOrUpdate(HttpServletRequest request, Model model, String industryId, Integer isadd,
			IndustryInfo bean) {
		if (isadd == 0) {// 修改
			bean = industryInfoService.selectByPrimaryKey(new BigDecimal(industryId));
		}
		model.addAttribute("bean", bean);
		return "admin/industryInfo/edit";
	}
	
	/**
	 * 
	 * 功能说明 : 新增或者修改
	 * 
	 * @author : chenxt
	 * @version ：2017-02-23
	 * @param request
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveOrUpdate")
	public OpreateResult SaveOrUpdate(HttpServletRequest request, IndustryInfo bean) {
		String navTabId = "industryInfoManage";
		OpreateResult opreateResult = new OpreateResult("200", "操作成功", navTabId, "closeCurrent", "");
		int re = industryInfoService.saveOrUpdate(bean);
		if (re <= 0) {
			opreateResult = new OpreateResult("300", "操作失败", navTabId, "closeCurrent", "");
		}
		return opreateResult;
	}
	
	/**
	 * 
	 * 功能说明 : 删除
	 * 
	 * @author : chenxt
	 * @version ：2017-02-23
	 * @param request
	 * @param industryId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/toDelete")
	public OpreateResult toDeleteDepartment(HttpServletRequest request, String industryId) {
		OpreateResult opreateResult = new OpreateResult("200", "删除成功", "", "", "");
		int re = industryInfoService.deleteById(industryId);
		if (re <= 0) {
			opreateResult = new OpreateResult("300", "删除失败", "", "", "");
		}
		return opreateResult;
	}
	
	/**
	 * 
	 * 功能说明 :详情页面
	 * 
	 * @author : chenxt
	 * @version ：2017-02-23
	 * @param request
	 * @param model
	 * @param industryId
	 * @return
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest request, Model model, String industryId,IndustryInfo bean) {	
		bean = industryInfoService.selectByPrimaryKey(new BigDecimal(industryId));
		model.addAttribute("bean", bean);
		return "admin/industryInfo/detail";
	}

}

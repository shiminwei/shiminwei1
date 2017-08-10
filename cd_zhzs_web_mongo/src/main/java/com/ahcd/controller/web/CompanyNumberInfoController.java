package com.ahcd.controller.web;

import java.math.BigDecimal;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.CompanyNumberInfo;
import com.ahcd.pojo.Page;
import com.ahcd.service.impl.CompanyNumberInfoServiceImpl;

/**
 * 说明 :企业纳税识别和/社会信用代码信息
 * @version ：2017年03月10日 
 */
@Controller
@RequestMapping("/web/number")
public class CompanyNumberInfoController {
	@Resource
	private CompanyNumberInfoServiceImpl companyNumberInfoService;
	/**
	 * 功能说明 :信息列表
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Page<CompanyNumberInfo> page, CompanyNumberInfo bean,String id) {
		page.setQueryBean(bean);
		Page<CompanyNumberInfo> pageList=companyNumberInfoService.getCompanyNumberInfoPage(page);
		request.setAttribute("pageList", pageList);
		request.setAttribute("bean", bean);
		return "web/company/number/list";
	}

	/**
	 * 功能说明 :跳转新增/编辑信息
	 */
	@RequestMapping("/toAddOrEdit")
	public String toAddOrEdit(HttpServletRequest request, String numberId,CompanyNumberInfo bean,String id) {
		if(!StringUtil.isBlank(id))bean.setId(new BigDecimal(id));//新增
		if(!StringUtil.isBlank(numberId)){
			bean = companyNumberInfoService.getCompanyNumberInfoByNumberId(numberId);
		}
		request.setAttribute("bean", bean);
		return "web/company/number/edit";
	}

	/**
	 * 功能说明 :新增/修改信息
	 */
	@ResponseBody
	@RequestMapping("/addOrEdit")
	public OpreateResult addOrEdit(HttpServletRequest request, CompanyNumberInfo bean) {
		String navTabId = "numberManage";
		OpreateResult opreateResult = new OpreateResult("200", "操作成功", navTabId, "closeCurrent", "");
		int re = -1;
		if(StringUtil.isBlank(bean.getNumberId())){
			re = companyNumberInfoService.insertCompanyNumberInfo(bean);
		}else{
			re = companyNumberInfoService.updateCompanyNumberInfo(bean);
		}	
		if (re <= 0) {
			opreateResult = new OpreateResult("300", "操作失败", navTabId, "closeCurrent", "");
		}
		return opreateResult;
	}

	/**
	 * 功能说明 :删除信息
	 */
	@ResponseBody
	@RequestMapping("/toDelete")
	public OpreateResult toDelete(HttpServletRequest request, String numberId) {
		OpreateResult opreateResult = new OpreateResult("200", "删除成功", "", "", "");
		int re = companyNumberInfoService.deleteByNumberId(numberId);
		if (re <= 0) {
			opreateResult = new OpreateResult("300", "删除失败", "", "", "");
		}
		return opreateResult;
	}
	
	/**
	 * 功能说明 :详情页面
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest request, Model model, String numberId,CompanyNumberInfo bean) {	
		bean = companyNumberInfoService.getCompanyNumberInfoByNumberId(numberId);
		model.addAttribute("bean", bean);
		return "web/company/number/detail";
	}
}

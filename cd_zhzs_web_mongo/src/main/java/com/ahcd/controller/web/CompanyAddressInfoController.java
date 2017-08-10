package com.ahcd.controller.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.CompanyAddressInfo;
import com.ahcd.pojo.Page;
import com.ahcd.service.impl.CompanyAddressInfoServiceImpl;

/**
 * 说明 :企业地址信息
 * @version ：2017年03月10日 
 */
@Controller
@RequestMapping("/web/address")
public class CompanyAddressInfoController {
	@Resource
	private CompanyAddressInfoServiceImpl companyAddressInfoService;
	/**
	 * 功能说明 :企业地址信息列表
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Page<CompanyAddressInfo> page, CompanyAddressInfo bean,String id) {
		if(!StringUtil.isBlank(id)){
			bean.setId(new BigDecimal(id));
		}
		page.setQueryBean(bean);
		Page<CompanyAddressInfo> pageList=companyAddressInfoService.getCompanyAddressInfoPage(page);
		request.setAttribute("pageList", pageList);
		request.setAttribute("bean", bean);
		addAttribute(model);
		return "web/company/address/list";
	}

	/**
	 * 功能说明 :跳转新增/编辑企业地址信息
	 */
	@RequestMapping("/toAddOrEdit")
	public String toAddOrEdit(HttpServletRequest request, Model model, String addressId,CompanyAddressInfo bean,String id) {
		if(!StringUtil.isBlank(id))bean.setId(new BigDecimal(id));//新增地址
		if(!StringUtil.isBlank(addressId)){
			bean = companyAddressInfoService.getCompanyAddressInfoById(addressId);
		}
		request.setAttribute("bean", bean);
		addAttribute(model);
		return "web/company/address/edit";
	}

	/**
	 * 功能说明 :新增/修改企业地址信息
	 */
	@ResponseBody
	@RequestMapping("/addOrEdit")
	public OpreateResult addOrEdit(HttpServletRequest request, CompanyAddressInfo bean) {
		String navTabId = "addressManage";
		OpreateResult opreateResult = new OpreateResult("200", "操作成功", navTabId, "closeCurrent", "");
		int re = -1;
		if(StringUtil.isBlank(bean.getAddressId())){
			re = companyAddressInfoService.insertCompanyAddressInfo(bean);
		}else{
			re = companyAddressInfoService.updateCompanyAddressInfo(bean);
		}	
		if (re <= 0) {
			opreateResult = new OpreateResult("300", "操作失败", navTabId, "closeCurrent", "");
		}
		return opreateResult;
	}

	/**
	 * 功能说明 :删除企业地址信息
	 */
	@ResponseBody
	@RequestMapping("/toDelete")
	public OpreateResult toDelete(HttpServletRequest request, String addressId) {
		OpreateResult opreateResult = new OpreateResult("200", "删除成功", "", "", "");
		int re = companyAddressInfoService.deleteByAddressId(addressId);
		if (re <= 0) {
			opreateResult = new OpreateResult("300", "删除失败", "", "", "");
		}
		return opreateResult;
	}
	
	/**
	 * 功能说明 :详情页面
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest request, Model model, String addressId,CompanyAddressInfo bean) {	
		bean = companyAddressInfoService.getCompanyAddressInfoById(addressId);
		model.addAttribute("bean", bean);
		addAttribute(model);
		return "web/company/address/detail";
	}
	
	private void addAttribute(Model model){
		model.addAttribute("addressSource", addressSource());
		model.addAttribute("addressType", addressType());
	}
	/** 
	 * 功能说明 :地址来源枚举
	 * */
	@SuppressWarnings({ "serial" })
	private static final Map<String, String> addressSource() {
		Map<String, String> addressSource = new HashMap<String, String>() {
			{
				put("1", "国税");
				put("2", "地税");
				put("3", "财政");
			}
		};
		return addressSource;
	}	
	/** 
	 * 功能说明 :地址来源枚举
	 * */
	@SuppressWarnings({ "serial" })
	private static final Map<String, String> addressType() {
		Map<String, String> addressType = new HashMap<String, String>() {
			{
				put("1", "经营地址");
				put("2", "注册地址");
			}
		};
		return addressType;
	}
}

package com.ahcd.controller.admin;

import java.math.BigDecimal;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ahcd.common.OpreateResult;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.TaxInfo;
import com.ahcd.service.impl.TaxInfoServiceImpl;


/**
 * 说明 :税种信息
 * @author : chenxt
 * @version ：2017年02月24日 
 */
@Controller
@RequestMapping("admin/taxInfo")
public class TaxInfoController {

	@Resource
	private TaxInfoServiceImpl taxInfoService;
	

	/**
	 * 
	 * 功能说明 :查询税种信息表列表
	 *
	 * @author : chenxt
	 * @version ：2017-02-24
	 * @param request
	 * @param model
	 * @param page
	 * @param bean
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Page<TaxInfo> page, TaxInfo bean) {
		page = taxInfoService.selectTaxInfoPage(page, bean);
		request.setAttribute("pageList", page);
		request.setAttribute("bean", bean);
		return "admin/taxInfo/list";
	}
	
	/**
	 * 
	 * 功能说明 :新增或者修改跳转
	 * 
	 * @author : chenxt
	 * @version ：2017-02-24
	 * @param request
	 * @param model
	 * @param taxId
	 * @return
	 */
	@RequestMapping("toSaveOrUpdate")
	public String toSaveOrUpdate(HttpServletRequest request, Model model, String taxId, Integer isadd,
			TaxInfo bean) {
		if (isadd == 0) {// 修改
			bean = taxInfoService.selectByPrimaryKey(new BigDecimal(taxId));
		}
		model.addAttribute("bean", bean);
		return "admin/taxInfo/edit";
	}
	
	/**
	 * 
	 * 功能说明 : 新增或者修改
	 * 
	 * @author : chenxt
	 * @version ：2017-02-24
	 * @param request
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveOrUpdate")
	public OpreateResult SaveOrUpdate(HttpServletRequest request, TaxInfo bean) {
		String navTabId = "taxInfoManage";
		OpreateResult opreateResult = new OpreateResult("200", "操作成功", navTabId, "closeCurrent", "");
		int re = taxInfoService.saveOrUpdate(bean);
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
	 * @version ：2017-02-24
	 * @param request
	 * @param taxId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/toDelete")
	public OpreateResult toDeleteDepartment(HttpServletRequest request, String taxId) {
		OpreateResult opreateResult = new OpreateResult("200", "删除成功", "", "", "");
		int re = taxInfoService.deleteById(taxId);
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
	 * @version ：2017-02-24
	 * @param request
	 * @param model
	 * @param taxId
	 * @return
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest request, Model model, String taxId,TaxInfo bean) {	
		bean = taxInfoService.selectByPrimaryKey(new BigDecimal(taxId));
		model.addAttribute("bean", bean);
		return "admin/taxInfo/detail";
	}

}

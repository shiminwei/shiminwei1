package com.ahcd.controller.admin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ahcd.common.Constant;
import com.ahcd.common.OpreateResult;
import com.ahcd.pojo.AreaIndustryTaxInfo;
import com.ahcd.pojo.Page;
import com.ahcd.service.impl.AreaIndustryTaxInfoServiceImpl;
import com.ahcd.service.impl.IndustryInfoServiceImpl;
import com.ahcd.service.impl.TaxInfoServiceImpl;


/**
 * 说明 :分行业分税种分地区税率信息
 * @author : chenxt
 * @version ：2017年02月24日 
 */
@Controller
@RequestMapping("admin/areaIndustryTaxInfo")
public class AreaIndustryTaxInfoController {

	@Resource
	private AreaIndustryTaxInfoServiceImpl areaIndustryTaxInfoService;
	@Resource
	private IndustryInfoServiceImpl industryInfoService;
	@Resource
	private TaxInfoServiceImpl taxInfoService;
	
	/**
	 * 
	 * 功能说明 :查询分行业分税种分地区税率表列表
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
	public String list(HttpServletRequest request, Model model, Page<AreaIndustryTaxInfo> page, AreaIndustryTaxInfo bean) {
		Map<BigDecimal,String> areaType=Constant.AreaType();
		page = areaIndustryTaxInfoService.selectAreaIndustryTaxInfoPage(page, bean);
		request.setAttribute("pageList", page);
		request.setAttribute("bean", bean);
		request.setAttribute("areaType", areaType);
		return "admin/areaIndustryTaxInfo/list";
	}
	
	/**
	 * 
	 * 功能说明 :新增或者修改跳转
	 * 
	 * @author : chenxt
	 * @version ：2017-02-24
	 * @param request
	 * @param model
	 * @param industryId
	 * @return
	 */
	@RequestMapping("toSaveOrUpdate")
	public String toSaveOrUpdate(HttpServletRequest request, Model model, String aitiId, Integer isadd,
			AreaIndustryTaxInfo bean) {		
		if (isadd == 0) {// 修改
			bean = areaIndustryTaxInfoService.selectByPrimaryKey(new BigDecimal(aitiId));
		}
		addSelected(model);
		model.addAttribute("bean", bean);
		return "admin/areaIndustryTaxInfo/edit";
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
	public OpreateResult SaveOrUpdate(HttpServletRequest request, AreaIndustryTaxInfo bean) {
		String navTabId = "areaIndustryTaxInfoManage";
		OpreateResult opreateResult = new OpreateResult("200", "操作成功", navTabId, "closeCurrent", "");
		bean.setTaxrate(formatBigDecimal(bean.getTaxrate()));
		int re = areaIndustryTaxInfoService.saveOrUpdate(bean);
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
	 * @param industryId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/toDelete")
	public OpreateResult toDeleteDepartment(HttpServletRequest request, String aitiId) {
		OpreateResult opreateResult = new OpreateResult("200", "删除成功", "", "", "");
		int re = areaIndustryTaxInfoService.deleteById(aitiId);
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
	 * @param industryId
	 * @return
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest request, Model model, String aitiId,AreaIndustryTaxInfo bean) {	
		bean = areaIndustryTaxInfoService.selectByPrimaryKey(new BigDecimal(aitiId));
		model.addAttribute("bean", bean);
		addSelected(model);
		return "admin/areaIndustryTaxInfo/detail";
	}

	private void addSelected(Model model){
		model.addAttribute("areaType", Constant.AreaType());
		model.addAttribute("industryType", industryInfoService.selectIndustryInfoList(null));
		model.addAttribute("taxType", taxInfoService.selectTaxInfoList(null));
	}
	private BigDecimal formatBigDecimal(BigDecimal big){
		 if(big == null){
			 return new BigDecimal(0);
		 }
		 
		 DecimalFormat df = new DecimalFormat("##.0000");
	     df.setRoundingMode(RoundingMode.HALF_UP);
	     return new BigDecimal(df.format(big.divide(new BigDecimal(100))));
	}
}

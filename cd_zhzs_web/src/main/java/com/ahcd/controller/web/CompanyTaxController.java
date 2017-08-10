package com.ahcd.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ahcd.common.Constant;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.ZdConstant;
import com.ahcd.service.impl.CompanyTaxServiceImpl;
import com.ahcd.service.impl.ZdConstantServiceImpl;

/**
 * 展示前台数据模型
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("web/companytax")
public class CompanyTaxController {

	@Resource
	private ZdConstantServiceImpl zdConstantService;
	@Resource
	private CompanyTaxServiceImpl companyTaxServiceImpl;
	/**
	 * 
	 * @Title: qsqyfzgbm 全市企业分征管部门纳税情况表
	 * @author: feiyang
	 * @date: 2017年5月9日 上午12:11:25
	 * @param model
	 * @param pageList
	 * @param year
	 * @param month
	 * @param qymc
	 * @param ssqy
	 * @return:
	 */
	@RequestMapping("qsqyfzgbm")
	public String qsqyfzgbm(HttpServletRequest request,Model model, Page<Map<String,String>> pageList, String year, String month, String qymc) {
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<ZdConstant> skgkList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_SKGK_TYPE);
		String ssqy = request.getParameter("ssqy");
		if("".equals(ssqy)|| ssqy == null || "1234".indexOf(ssqy)<0){
			ssqy = "1" ;
		}
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("skgkList", skgkList);
		model.addAttribute("pageList", pageList);
		if(year != null){
			year = year.split(",")[0];
		}
		model.addAttribute("year", year);
		if(month != null){
			month = month.split(",")[0];
		}
		model.addAttribute("month", month);
		if(qymc != null){
			qymc = qymc.split(",")[0];
		}
		model.addAttribute("qymc", qymc);
		if(ssqy != null){
			ssqy = ssqy.split(",")[0];
		}
		model.addAttribute("ssqy", ssqy);
		List<String> newSzList = new ArrayList<String>();
		if (StringUtil.isNotEmpty(ssqy)) {
			if (ssqy.equals("1")) {
				newSzList.add("增值税");
				newSzList.add("消费税");
				newSzList.add("企业所得税");
				newSzList.add("个人所得税");
				newSzList.add("车辆购置税");
				newSzList.add("废弃电器电子产品处理基金收入");
				newSzList.add("文化事业建设费");
				newSzList.add("税务部门罚没收入");
				Map<String,String> param = new HashMap<String, String>();
				param.put("nsrmc", qymc);
				param.put("startdate", year+"01");
				month = month=="" || month ==null?"12":month;
				param.put("enddate", year+month);
				pageList.setQueryBean(param);
				pageList = companyTaxServiceImpl.selectTaxInGSJ(pageList);
				model.addAttribute("pageList", pageList);
				model.addAttribute("ssqyname", "国税");
			} else if (ssqy.equals("2")) {
				newSzList.add("个人所得税");
				newSzList.add("企业所得税");
				newSzList.add("其他收入");
				newSzList.add("印花税");
				newSzList.add("土地增值税");
				newSzList.add("地方教育附加");
				newSzList.add("城市维护建设税");
				newSzList.add("城镇土地使用税");
				newSzList.add("契税");
				newSzList.add("房产税");
				newSzList.add("教育费附加");
				newSzList.add("文化事业建设费");
				newSzList.add("残疾人就业保障金");
				newSzList.add("水利建设专项收入");
				newSzList.add("烟叶税");
				newSzList.add("税务部门罚没收入");
				newSzList.add("耕地占用税");
				newSzList.add("营业税");
				newSzList.add("资源税");	
				newSzList.add("车船税");
				Map<String,String> param = new HashMap<String, String>();
				param.put("nsrmc", qymc);
				param.put("startdate", year+"01");
				month = month=="" || month ==null?"12":month;
				param.put("enddate", year+month);
				pageList.setQueryBean(param);
				pageList = companyTaxServiceImpl.getLocalPage(pageList);
				model.addAttribute("pageList", pageList);
				model.addAttribute("ssqyname", "地税");
			} else if (ssqy.equals("3")) {
				newSzList.add("进口关税");
				//newSzList.add("船舶吨税");
				newSzList.add("进口增值税");
				//newSzList.add("行李和邮递物品进口税");
				Map<String,String> param = new HashMap<String, String>();
				param.put("nsrmc", qymc);
				param.put("startdate", year+"01");
				month = month=="" || month ==null?"12":month;
				param.put("enddate", year+month);
				pageList.setQueryBean(param);
				pageList = companyTaxServiceImpl.selectTaxInHG(pageList);
				model.addAttribute("pageList", pageList);
				model.addAttribute("ssqyname", "海关");
			} else if (ssqy.equals("4")) {
				newSzList.add("海螺");
				Map<String,String> param = new HashMap<String, String>();
				param.put("nsrmc", qymc);
				param.put("startdate", year+"01");
				month = month=="" || month ==null?"12":month;
				param.put("enddate", year+month);
				pageList.setQueryBean(param);
				pageList = companyTaxServiceImpl.getSZLocalPage(pageList);
				model.addAttribute("pageList", pageList);
				model.addAttribute("ssqyname", "省直地税");
			}
		}
		model.addAttribute("newSzListSize", newSzList.size() * 3 + 4);
		model.addAttribute("newSzList", newSzList);
		return "web/modelShow/qsqyfzgbm";
	}
	
	@RequestMapping("detail")
	public String detail(HttpServletRequest request,Model model, Page<Map<String,String>> pageList, String year, 
			String month, String qymc,String ssqy,String zsxm) {
		if(ssqy != null){
			ssqy = ssqy.split(",")[0];
		}
		String ssgds = getSsgdsName(Integer.parseInt(ssqy));
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<ZdConstant> skgkList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_SKGK_TYPE);
		List<ZdConstant> shuiZhongList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_SHUIZHONG_TYPE);// 税种
		model.addAttribute("shuiZhongList", shuiZhongList);
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("skgkList", skgkList);
		model.addAttribute("pageList", pageList);
		if(year != null){
			year = year.split(",")[0];
		}
		model.addAttribute("year", year);
		if(month != null){
			month = month.split(",")[0];
		}
		model.addAttribute("month", month);
		if(qymc != null){
			qymc = qymc.split(",")[0];
		}
		model.addAttribute("qymc", qymc);
		model.addAttribute("ssqy", ssqy);
		if(zsxm != null){
			zsxm = zsxm.split(",")[0];
		}
		model.addAttribute("zsxm", zsxm);
		Map<String,String> param = new HashMap<String, String>();
		param.put("nsrmc", qymc);
		param.put("ssgds", ssgds);
		param.put("zsxm", zsxm);
		param.put("startdate", year+"01");
		month = month=="" || month ==null?"12":month;
		param.put("enddate", year+month);
		pageList.setQueryBean(param);
		pageList = companyTaxServiceImpl.getDetailPage(pageList);
		model.addAttribute("pageList", pageList);
		return "web/modelShow/detail";
	}

	private String getSsgdsName(int i) {
		switch (i) {
		case 2 : return "地税";
		case 3 : return "海关";
		case 4 : return "";
		default : return "国税";
		}
	}
}

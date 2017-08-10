package com.ahcd.controller.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ahcd.common.Constant;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.ZdConstant;
import com.ahcd.service.impl.SysWebCzsrServiceImpl;
import com.ahcd.service.impl.SysWebZyjjzbServiceImpl;
import com.ahcd.service.impl.ZdConstantServiceImpl;
import com.alibaba.fastjson.JSON;

/**
 * 展示前台数据模型
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("web/modelShow")
public class ModelShowController {

	@Resource
	private ZdConstantServiceImpl zdConstantService;

	@Resource
	private SysWebCzsrServiceImpl sysWebCzsrService;

	@Resource
	private SysWebZyjjzbServiceImpl sysWebZyjjzbServiceImpl;

	/**
	 * 
	 * 功能说明 : 市直项目汇总
	 * 
	 * @author : feiyue yang
	 * @version ：2017年3月30日 上午9:20:55
	 * @return
	 */
	@RequestMapping("shiZhiProtect")
	public String toShiZhiProtectList(Model model, String year, Page<String> pageList, String month, String ssqy) {
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<ZdConstant> ssqyList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_SSQY_TYPE);
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("ssqyList", ssqyList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("ssqy", ssqy);
		model.addAttribute("pageList", pageList);
		return "web/modelShow/shiZhiProtectList";
	}

	/**
	 * 
	 * 功能说明 : 招商纳税情况明细
	 * 
	 * @author : feiyue yang
	 * @version ：2017年3月30日 上午9:20:55
	 * @return
	 */
	@RequestMapping("detailShiZhiProtect")
	public String toDetailShiZhiProtectList(Model model, Page<String> pageList, String year, String month) {
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		return "web/modelShow/detailShiZhiProtectList";
	}

	/**
	 * 
	 * 功能说明 : 2016年十月全省各市财政收入，增幅排名
	 * 
	 * @author : feiyue yang
	 * @version ：2017年3月30日 上午9:20:55
	 * @return
	 */
	@RequestMapping("shouRuZengFu")
	public String toShouRuZengFu() {

		return "web/modelShow/shouRuZengFuList";
	}

	/**
	 * 
	 * 功能说明 : 主要经济指标完成情况
	 * 
	 * @author : fei yang
	 * @version ：2017年3月30日 上午9:20:55
	 * @return
	 */
	@RequestMapping("economicList")
	public String economicList(Model model, Page<String> pageList, String year, String month) {
		model.addAttribute("pageList", pageList);
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<Map<String, Object>> allResult = sysWebZyjjzbServiceImpl.selectAllResult(year + month,
				(Integer.parseInt(year) - 1) + month);
		model.addAttribute("allResult", allResult);
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("newMonth", getNewMonthInt(month));
		return "web/modelShow/economicList";
	}

	/**
	 * 
	 * 功能说明 : 全市分区县主要经济指标完成情况
	 * 
	 * @author : fei yang
	 * @version ：2017年3月30日 上午9:20:55
	 * @return
	 */
	@RequestMapping("economicfqxList")
	public String economicfqxList(Model model, Page<Map<String, Object>> pageList, String year, String month) {
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<Map<String, Object>> mapList = sysWebZyjjzbServiceImpl.selectAllJjzbFqy(year + month);
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("newMonth", getNewMonthInt(month));
		pageList.setResult(mapList);
		pageList.setTotalCount(mapList.size());
		model.addAttribute("pageList", pageList);
		return "web/modelShow/economicfqxList";
	}

	/**
	 * 
	 * 功能说明 : 全省主要经济指标完成情况
	 * 
	 * @author : fei yang
	 * @version ：2017年3月30日 上午9:20:55
	 * @return
	 */
	@RequestMapping("economicqsList")
	public String economicqsList(Model model, Page<Map<String, Object>> pageList, String year, String month) {
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<Map<String, Object>> mapList = sysWebZyjjzbServiceImpl.selectAllJjzbQs(year + month);
		pageList.setResult(mapList);
		pageList.setTotalCount(mapList.size());
		model.addAttribute("pageList", pageList);
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("newMonth", getNewMonthInt(month));
		return "web/modelShow/economicqsList";
	}

	/**
	 * 
	 * 功能说明 : 分行业税收情况表
	 * 
	 * @version ：2017年4月27日 上午9:20:55
	 * @return
	 */
	@RequestMapping("fenHangYeList")
	public String fenHangYeList(Model model, Page<String> pageList, String year, String month) {
		model.addAttribute("pageList", pageList);
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<ZdConstant> sscyList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_SSCY_TYPE);// 所属产业
		List<ZdConstant> ssdlList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_SSDL_TYPE);// 所属大类
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("sscyList", sscyList);
		model.addAttribute("ssdlList", ssdlList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("newMonth", getNewMonthInt(month));
		return "web/modelShow/fenHangYeList";
	}

	/**
	 * 
	 * 功能说明 : 财政收入分级分部门完成情况
	 * 
	 * @author : fei yang
	 * @version ：2017年3月30日 下午2:20:53
	 * @param model
	 * @param pageList
	 * @return
	 */
	@RequestMapping("czzsrdiyubumenList")
	public String czzsrdiyubumenList(Model model, Page<String> pageList, String year, String month) {
		model.addAttribute("pageList", pageList);
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<Map<String, Object>> allResult = sysWebCzsrService.selectAllResult(year + month);
		model.addAttribute("allResult", allResult);
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("newMonth", getNewMonthInt(month));
		return "web/modelShow/czzsrdiyubumenList";
	}

	/**
	 * 
	 * 功能说明 :财政收入分区域分税种分税种完成情况
	 * 
	 * @author : fei yang
	 * @version ：2017年4月27日 上午10:19:20
	 * @param model
	 * @param pageList
	 * @param year
	 * @param month
	 * @return
	 */
	@RequestMapping("czsrfsz")
	public String czsrfsz(Model model, Page<Map<String, Object>> pageList, String year, String month) {
		pageList.setNumPerPage(50);
		model.addAttribute("pageList", pageList);
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<Map<String, Object>> allResult = sysWebZyjjzbServiceImpl.selectFqyfsz(year + month,
				Integer.parseInt(year) - 1 + month);
		pageList.setResult(allResult);
		pageList.setTotalCount(allResult.size());
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("newMonth", getNewMonthInt(month));
		return "web/modelShow/czsrfsz";
	}

	/**
	 * 
	 * 功能说明 :财政支出分级分科目完成情况
	 * 
	 * @author : fei yang
	 * @version ：2017年3月30日 上午10:27:27
	 * @param model
	 * @param pageList
	 * @return
	 */
	@RequestMapping("czzcList")
	public String czzcList(Model model, Page<Map<String, Object>> pageList, String year, String month, String yskm) {
		if (StringUtil.isBlank(yskm)) {
			yskm = "一般预算";
		}
		pageList.setNumPerPage(100);
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<Map<String, Object>> allResult = sysWebZyjjzbServiceImpl.selectFjfkm(year + month, yskm);
		pageList.setResult(allResult);
		pageList.setTotalCount(allResult.size());
		model.addAttribute("pageList", pageList);
		model.addAttribute("allResult", allResult);
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("yskm", yskm);
		model.addAttribute("newMonth", getNewMonthInt(month));
		return "web/modelShow/czzcList";
	}

	/**
	 * 
	 * 功能说明 :全省收入完成情况
	 * 
	 * @author : fei yang
	 * @version ：2017年3月30日 上午10:27:27
	 * @param model
	 * @param pageList
	 * @return
	 */
	@RequestMapping("czsrList")
	public String qssrList(Model model, Page<Map<String, Object>> pageList, String year, String month) {
		model.addAttribute("pageList", pageList);
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<Map<String, Object>> allResult = sysWebZyjjzbServiceImpl.selectQssrqk(year + month, year + month);
		pageList.setResult(allResult);
		pageList.setTotalCount(allResult.size());

		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("newMonth", getNewMonthInt(month));
		return "web/modelShow/qssrList";
	}

	/**
	 * 
	 * 功能说明 :全省支出完成情况
	 * 
	 * @author : fei yang
	 * @version ：2017年3月30日 上午10:27:27
	 * @param model
	 * @param pageList
	 * @return
	 */
	@RequestMapping("qszcList")
	public String qszcList(Model model, Page<String> pageList, String year, String month) {
		model.addAttribute("pageList", pageList);
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("newMonth", getNewMonthInt(month));
		return "web/modelShow/qszcList";
	}

	/**
	 * 
	 * 功能说明 : 全市收支情况表
	 * 
	 * @author : fei yang
	 * @version ：2017年4月24日 下午4:09:29
	 * @param model
	 * @param pageList
	 * @param year
	 * @param month
	 * @return
	 */
	@RequestMapping("qsszList")
	public String qsszList(Model model, Page<String> pageList, String year, String month) {
		model.addAttribute("pageList", pageList);
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("newMonth", getNewMonthInt(month));
		return "web/modelShow/qsszList";
	}

	/**
	 * 
	 * 功能说明 : 企业纳税信息（分国税、地税、海关）
	 * 
	 * @author : fei yang
	 * @version ：2017年4月1日 上午10:39:25
	 * @return
	 */
	@RequestMapping("qynsqkfqy")
	public String qynsqkfqy(Model model, Page<String> pageList, String year, String month, String skgk, String qymc) {
		model.addAttribute("pageList", pageList);
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<ZdConstant> ssqyList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_SSQY_TYPE);
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("ssqyList", ssqyList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("skgk", skgk);
		model.addAttribute("qymc", qymc);
		model.addAttribute("newMonth", getNewMonthInt(month));
		return "web/modelShow/qynsqkfqy";
	}

	/**
	 * 
	 * 功能说明 : 分行业比对
	 * 
	 * @author : fei yang
	 * @version ：2017年4月1日 上午10:39:25
	 * @return
	 */
	@RequestMapping("fhybd")
	public String fhybd(Model model, Page<String> pageList) {
		model.addAttribute("pageList", pageList);
		return "web/modelShow/fhybd";
	}

	/**
	 * 
	 * 功能说明 : 分区域比对
	 * 
	 * @author : fei yang
	 * @version ：2017年4月1日 上午10:39:25
	 * @return
	 */
	@RequestMapping("fqybd")
	public String fqybd(Model model, Page<String> pageList) {
		model.addAttribute("pageList", pageList);
		return "web/modelShow/fqybd";
	}

	/**
	 * 
	 * 功能说明 : 分征管部门比对
	 * 
	 * @author : fei yang
	 * @version ：2017年4月1日 上午10:39:25
	 * @return
	 */
	@RequestMapping("fzgbmbd")
	public String fzgbmbd(Model model, Page<String> pageList) {
		model.addAttribute("pageList", pageList);
		return "web/modelShow/fzgbmbd";
	}

	/**
	 * 
	 * 功能说明 : 部门人员编制汇总
	 * 
	 * @author : fei yang
	 * @version ：2017年4月1日 上午10:39:25
	 * @return
	 */
	@RequestMapping("bmryhz")
	public String bmryhz(Model model, Page<String> pageList) {
		model.addAttribute("pageList", pageList);
		return "web/modelShow/bmryhz";
	}

	/**
	 * 
	 * 功能说明 : 部门 行政在职信息明细
	 * 
	 * @author : fei yang
	 * @version ：2017年4月1日 上午10:39:25
	 * @return
	 */
	@RequestMapping("xzzzmc")
	public String xzzzmc(Model model, Page<String> pageList) {
		model.addAttribute("pageList", pageList);
		return "web/modelShow/xzzzmc";
	}

	/**
	 * 
	 * 功能说明 : 部门支出汇总
	 * 
	 * @author : fei yang
	 * @version ：2017年4月1日 上午10:39:25
	 * @return
	 */
	@RequestMapping("bmzchz")
	public String bmzchz(Model model, Page<String> pageList) {
		model.addAttribute("pageList", pageList);
		return "web/modelShow/bmzchz";
	}

	/**
	 * 
	 * 功能说明 : 部门支出汇总明细
	 * 
	 * @author : fei yang
	 * @version ：2017年4月1日 上午10:39:25
	 * @return
	 */
	@RequestMapping("bmzchzmx")
	public String bmzchzmx(Model model, Page<String> pageList) {
		model.addAttribute("pageList", pageList);
		return "web/modelShow/bmzchzmx";
	}

	/**
	 * 
	 * 功能说明 :测试进度页面
	 * 
	 * @author : fei yang
	 * @version ：2017年4月1日 下午1:49:43
	 * @param model
	 * @param pageList
	 * @return
	 */
	@RequestMapping("jinduTest")
	public String jinduTest(Model model, Page<String> pageList) {
		model.addAttribute("pageList", pageList);
		return "web/modelShow/jinduTest";
	}

	/**
	 * 
	 * 功能说明 : 政府性项目情况表详细页面
	 * 
	 * @author : fei yang
	 * @version ：2017年4月21日 下午2:23:38
	 * @param model
	 * @param pageList
	 * @return
	 */
	@RequestMapping("zdfxmqkxx")
	public String zdfxmqkxx(Model model, Page<String> pageList, String xmmc) {
		model.addAttribute("pageList", pageList);
		model.addAttribute("xmmc", xmmc);
		return "web/modelShow/zdfxmqkxx";
	}

	/**
	 * 
	 * 功能说明 : 年度预算批复情况
	 * 
	 * @author : fei yang
	 * @version ：2017年4月21日 下午2:23:38
	 * @param model
	 * @param pageList
	 * @return
	 */
	@RequestMapping("ndyspf")
	public String ndyspf(Model model, Page<String> pageList, String year, String lbmc) {
		model.addAttribute("pageList", pageList);
		model.addAttribute("year", year);
		model.addAttribute("lbmc", lbmc);
		return "web/modelShow/ndyspf";
	}

	/**
	 * 
	 * 功能说明 : 年度预算执行情况
	 * 
	 * @author : fei yang
	 * @version ：2017年4月21日 下午2:23:38
	 * @param model
	 * @param pageList
	 * @return
	 */
	@RequestMapping("ndyszx")
	public String ndyszx(Model model, Page<String> pageList, String year) {
		model.addAttribute("pageList", pageList);
		model.addAttribute("year", year);
		return "web/modelShow/ndyszx";
	}

	/**
	 * 
	 * 功能说明 :替换月份数据 （01==》1）
	 * 
	 * @author : fei yang
	 * @version ：2017年4月24日 下午12:30:36
	 * @param monthStr
	 * @return
	 */
	public static String getNewMonthInt(String monthStr) {
		if (StringUtil.isNotEmpty(monthStr) && monthStr.length() == 2) {
			String firstStr = monthStr.substring(0, 1);
			if (firstStr.equals("0")) {
				return monthStr.substring(1, 2);
			}
		}
		return monthStr;
	}

	/**
	 * 
	 * @Title: qynspm 企业纳税排名
	 * @author: feiyang
	 * @date: 2017年5月8日 下午11:48:20
	 * @param model
	 * @param pageList
	 * @param year
	 * @return:
	 */
	@RequestMapping("qynspm")
	public String qynspm(Model model, Page<String> pageList, String year, String month, String qymc, String ssqy) {
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<ZdConstant> skgkList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_SKGK_TYPE);
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("skgkList", skgkList);
		model.addAttribute("pageList", pageList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("qymc", qymc);
		model.addAttribute("ssqy", ssqy);
		return "web/modelShow/qynspm";
	}

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
	public String qsqyfzgbm(HttpServletRequest request, Model model, Page<String> pageList, String year, String month,
			String qymc) {
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<ZdConstant> skgkList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_SKGK_TYPE);
		String ssqy = "";
		try {
			ssqy = new String(request.getParameter("ssqy").getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("skgkList", skgkList);
		model.addAttribute("pageList", pageList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("qymc", qymc);
		model.addAttribute("ssqy", ssqy);
		List<String> newSzList = new ArrayList<String>();
		if (StringUtil.isNotEmpty(ssqy)) {
			if (ssqy.equals("国税")) {
				newSzList.add("增值税");
				newSzList.add("消费税");
				newSzList.add("企业所得税");
				newSzList.add("个人所得税");
				newSzList.add("车辆购置税");
			} else if (ssqy.equals("地税")) {
				newSzList.add("增值税");
				newSzList.add("营业税");
				newSzList.add("房产税");
				newSzList.add("土地增值税");
				newSzList.add("城镇土地使用税");
				newSzList.add("契税");
				newSzList.add("资源税");
				newSzList.add("烟叶税");
				newSzList.add("印花税");
				newSzList.add("企业所得税");
				newSzList.add("个人所得税");
				newSzList.add("城市维护建设税");
				newSzList.add("教育费附加");
				newSzList.add("地方教育附加");
			} else if (ssqy.equals("海关")) {
				newSzList.add("关税");
				newSzList.add("船舶吨税");
				newSzList.add("进口增值税、消费税");
				newSzList.add("行李和邮递物品进口税");

			} else if (ssqy.equals("省直地税")) {
				newSzList.add("海螺");
			}
		}
		model.addAttribute("newSzListSize", newSzList.size() * 3 + 4);
		model.addAttribute("newSzList", newSzList);
		return "web/modelShow/qsqyfzgbm";
	}

	@RequestMapping("toGetHangYe")
	public void toGetHangYe(HttpServletResponse response, String cy) {
		
			String cy1="";
			try {
				cy1 = new String(cy.getBytes("ISO-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (StringUtil.isNotEmpty(cy1)) {
				List<ZdConstant> ssdlList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_SSDL_TYPE);// 所属大类
				List<ZdConstant> list = new ArrayList<ZdConstant>();
				for (int i = 0; i < ssdlList.size(); i++) {
					String str = ssdlList.get(i).getConstantDesc();
					if (StringUtil.isNotEmpty(str) && str.equals(cy1)) {
						list.add(ssdlList.get(i));
					}
				}
				String newList = JSON.toJSONString(list);
				System.out.println(newList);
				response.setHeader("Content-type", "text/html;charset=UTF-8");
				try {
					response.getWriter().write(newList);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
	}

	/**
	 * 
	 * @Title: gmysqyjbqk 全市招商引资企业纳税明细表
	 * @author: feiyang
	 * @date: 2017年5月9日 上午2:24:15
	 * @param model
	 * @param pageList
	 * @param year
	 * @param month
	 * @param qymc
	 * @param ssqy
	 * @return:
	 */
	@RequestMapping("zsyzqyqk")
	public String gmysqyjbqk(Model model, Page<String> pageList, String year, String month, String qymc, String ssqy) {
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<ZdConstant> ssqyList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_SSQY_TYPE);
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("ssqyList", ssqyList);
		model.addAttribute("pageList", pageList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("qymc", qymc);
		model.addAttribute("ssqy", ssqy);
		return "web/modelShow/zsyzqyqk";
	}

	/**
	 * 
	 * @Title: gmysqyqk 全市规模以上企业纳税明细情况表
	 * @author: feiyang
	 * @date: 2017年5月9日 上午2:11:24
	 * @param model
	 * @param pageList
	 * @param year
	 * @param month
	 * @param qymc
	 * @param ssqy
	 * @return:
	 */
	@RequestMapping("gmysqyqk")
	public String gmysqyqk(Model model, Page<String> pageList, String year, String month, String qymc, String ssqy) {
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<ZdConstant> ssqyList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_SSQY_TYPE);
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("ssqyList", ssqyList);
		model.addAttribute("pageList", pageList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("qymc", qymc);
		model.addAttribute("ssqy", ssqy);
		return "web/modelShow/gmysqyqk";
	}

	/**
	 * 
	 * @Title: qsqyfszzsfx 全市企业分行业治税分析表
	 * @author: feiyang
	 * @date: 2017年5月9日 上午3:27:42
	 * @param model
	 * @param pageList
	 * @param year
	 * @param month
	 * @param qymc
	 * @param sscy
	 * @return:
	 */
	@RequestMapping("qsqyfszzsfx")
	public String qsqyfszzsfx(Model model, Page<String> pageList, String year, String month, String qymc, String sscy) {
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<ZdConstant> sscyList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_SSCY_TYPE);
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("sscyList", sscyList);
		model.addAttribute("pageList", pageList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("qymc", qymc);
		model.addAttribute("sscy", sscy);
		return "web/modelShow/qsqyfszzsfx";
	}

	/**
	 * 
	 * @Title: sbjzfxxmjhb 市本级政府性投资项目计划表
	 * @author: feiyang
	 * @date: 2017年5月9日 下午3:41:58
	 * @param model
	 * @param pageList
	 * @param year
	 * @param month
	 * @param qymc
	 * @param sscy
	 * @return:
	 */
	@RequestMapping("sbjzfxxmjhb")
	public String sbjzfxxmjhb(Model model, Page<String> pageList, String year, String month, String xmmc) {
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		model.addAttribute("yearList", yearList);
		model.addAttribute("monthList", monthList);
		model.addAttribute("pageList", pageList);
		model.addAttribute("year", year);
		model.addAttribute("month", month);

		model.addAttribute("xmmc", xmmc);
		return "web/modelShow/sbjzfxxmjhb";
	}

	@RequestMapping("ceshiTable")
	public String ceshiTable(Model model, Page<String[]> page) {
		List<String[]> reList = new ArrayList<String[]>();
		List<String[]> neList = new ArrayList<String[]>();
		for (int i = 0; i < 50; i++) {
			String[] array = { String.valueOf(i), "企业名称", "纳税人识别好", "纳税人识别好", "纳税人识别好", "纳税人识别好", "纳税人识别好", "纳税人识别好",
					"纳税人识别好", "纳税人识别好", "纳税人识别好", "纳税人识别好", "纳税人识别好", "纳税人识别好", "纳税人识别好", "纳税人识别好" };
			reList.add(array);
		}
		for (int i = 0; i < reList.size(); i++) {
			if (i >= (page.getPageNum() - 1) * page.getNumPerPage() && i <= page.getPageNum() * page.getNumPerPage()) {
				neList.add(reList.get(i));

			}
		}
		page.setResult(neList);
		page.setTotalCount(reList.size());
		model.addAttribute("pageList", page);
		return "web/modelShow/ceshiTable";
	}
}

package com.ahcd.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ahcd.pojo.Page;

/**
 * 展示前台数据模型
 * @author Administrator
 *
 */
@Controller
@RequestMapping("web/modelShow")
public class ModelShowController {



	/**
	 * 
	   * 功能说明    : 市直项目汇总
	   * @author   : feiyue yang 
	   * @version ：2017年3月30日 上午9:20:55 
	   * @return
	 */
	@RequestMapping("shiZhiProtect")
	public String toShiZhiProtectList() {
		
		return "web/modelShow/shiZhiProtectList";
	}
	
	/**
	 * 
	   * 功能说明    : 招商纳税情况明细
	   * @author   : feiyue yang 
	   * @version ：2017年3月30日 上午9:20:55 
	   * @return
	 */
	@RequestMapping("detailShiZhiProtect")
	public String toDetailShiZhiProtectList() {
		
		return "web/modelShow/detailShiZhiProtectList";
	}

	/**
	 * 
	   * 功能说明    : 2016年十月全省各市财政收入，增幅排名
	   * @author   : feiyue yang 
	   * @version ：2017年3月30日 上午9:20:55 
	   * @return
	 */
	@RequestMapping("shouRuZengFu")
	public String toShouRuZengFu() {
		
		return "web/modelShow/shouRuZengFuList";
	}
	
	
	/**
	 * 
	   * 功能说明    : 主要经济指标完成情况
	   * @author   : fei yang 
	   * @version ：2017年3月30日 上午9:20:55 
	   * @return
	 */
	@RequestMapping("economicList")
	public String economicList(Model model,Page<String> pageList) {
		model.addAttribute("pageList", pageList);
		return "web/modelShow/economicList";
	}
	

	
	/**
	 * 
	   * 功能说明    : 财政收入分级分部门完成情况
	   * @author   : fei yang 
	   * @version ：2017年3月30日 下午2:20:53 
	   * @param model
	   * @param pageList
	   * @return
	 */
	@RequestMapping("czzsrdiyubumenList")
	public String czzsrdiyubumenList(Model model,Page<String> pageList){
		model.addAttribute("pageList", pageList);
		return "web/modelShow/czzsrdiyubumenList";
	}
	
	/**
	 * 
	   * 功能说明    :财政支出分级分科目完成情况
	   * @author   : fei yang 
	   * @version ：2017年3月30日 上午10:27:27 
	   * @param model
	   * @param pageList
	   * @return
	 */
	@RequestMapping("czzcList")
	public String czzcList(Model model,Page<String> pageList) {
		model.addAttribute("pageList", pageList);
		return "web/modelShow/czzcList";
	}

	/**
	 * 
	   * 功能说明    :全省收入完成情况
	   * @author   : fei yang 
	   * @version ：2017年3月30日 上午10:27:27 
	   * @param model
	   * @param pageList
	   * @return
	 */
	@RequestMapping("czsrList")
	public String qssrList(Model model,Page<String> pageList) {
		model.addAttribute("pageList", pageList);
		return "web/modelShow/qssrList";
	}
	/**
	 * 
	   * 功能说明    :全省支出完成情况
	   * @author   : fei yang 
	   * @version ：2017年3月30日 上午10:27:27 
	   * @param model
	   * @param pageList
	   * @return
	 */
	@RequestMapping("qszcList")
	public String qszcList(Model model,Page<String> pageList) {
		model.addAttribute("pageList", pageList);
		return "web/modelShow/qszcList";
	}	

	/**
	 * 
	   * 功能说明    : 企业纳税信息（分国税、地税、海关）
	   * @author   : fei yang 
	   * @version ：2017年4月1日 上午10:39:25 
	   * @return
	 */
	@RequestMapping("qynsqkfqy")
	public String qynsqkfqy(Model model,Page<String> pageList){
		model.addAttribute("pageList", pageList);
		return "web/modelShow/qynsqkfqy";
	}
	
	
	
	/**
	 * 
	   * 功能说明    : 分行业比对
	   * @author   : fei yang 
	   * @version ：2017年4月1日 上午10:39:25 
	   * @return
	 */
	@RequestMapping("fhybd")
	public String fhybd(Model model,Page<String> pageList){
		model.addAttribute("pageList", pageList);
		return "web/modelShow/fhybd";
	}
	
	
	
	/**
	 * 
	   * 功能说明    : 分区域比对
	   * @author   : fei yang 
	   * @version ：2017年4月1日 上午10:39:25 
	   * @return
	 */
	@RequestMapping("fqybd")
	public String fqybd(Model model,Page<String> pageList){
		model.addAttribute("pageList", pageList);
		return "web/modelShow/fqybd";
	}
	
	/**
	 * 
	   * 功能说明    : 分征管部门比对
	   * @author   : fei yang 
	   * @version ：2017年4月1日 上午10:39:25 
	   * @return
	 */
	@RequestMapping("fzgbmbd")
	public String fzgbmbd(Model model,Page<String> pageList){
		model.addAttribute("pageList", pageList);
		return "web/modelShow/fzgbmbd";
	}
	
	
	
	/**
	 * 
	   * 功能说明    : 部门人员编制汇总
	   * @author   : fei yang 
	   * @version ：2017年4月1日 上午10:39:25 
	   * @return
	 */
	@RequestMapping("bmryhz")
	public String bmryhz(Model model,Page<String> pageList){
		model.addAttribute("pageList", pageList);
		return "web/modelShow/bmryhz";
	}
	
	/**
	 * 
	   * 功能说明    : 部门 行政在职信息明细
	   * @author   : fei yang 
	   * @version ：2017年4月1日 上午10:39:25 
	   * @return
	 */
	@RequestMapping("xzzzmc")
	public String xzzzmc(Model model,Page<String> pageList){
		model.addAttribute("pageList", pageList);
		return "web/modelShow/xzzzmc";
	}
	
	/**
	 * 
	   * 功能说明    : 部门支出汇总
	   * @author   : fei yang 
	   * @version ：2017年4月1日 上午10:39:25 
	   * @return
	 */
	@RequestMapping("bmzchz")
	public String bmzchz(Model model,Page<String> pageList){
		model.addAttribute("pageList", pageList);
		return "web/modelShow/bmzchz";
	}
	/**
	 * 
	   * 功能说明    : 部门支出汇总明细
	   * @author   : fei yang 
	   * @version ：2017年4月1日 上午10:39:25 
	   * @return
	 */
	@RequestMapping("bmzchzmx")
	public String bmzchzmx(Model model,Page<String> pageList){
		model.addAttribute("pageList", pageList);
		return "web/modelShow/bmzchzmx";
	}
	
	
	/**
	 * 
	   * 功能说明    :测试进度页面 
	   * @author   : fei yang 
	   * @version ：2017年4月1日 下午1:49:43 
	   * @param model
	   * @param pageList
	   * @return
	 */
	@RequestMapping("jinduTest")
	public String jinduTest(Model model,Page<String> pageList){
		model.addAttribute("pageList", pageList);
		return "web/modelShow/jinduTest";
	}
}

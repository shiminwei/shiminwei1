package com.ahcd.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ahcd.common.Constant;
import com.ahcd.common.FileUtils;
import com.ahcd.common.HttpServletResponseUtils;
import com.ahcd.common.OpreateResult;
import com.ahcd.pojo.LeadConfigBean;
import com.ahcd.service.LeadConfigService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("config")
public class LeadConfigController {

	@Resource
	private LeadConfigService leadConfigService;

	/**
	 * 
	 * 功能说明 : 获取当前前置机配置内容
	 * 
	 * @author : fei yang
	 * @version ：2017年2月8日 下午1:22:55
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String getConfig(HttpServletRequest request, Model model) {
		LeadConfigBean lead = leadConfigService.getConfigBean();
		Map<String, String> quartzMap = Constant.quartzMap();
		model.addAttribute("lead", lead);
		model.addAttribute("quartzMap", quartzMap);
		model.addAttribute("quartzMapJson", JSONObject.toJSONString(quartzMap));
		return "config/index";
	}

	/**
	 * 
	 * 功能说明 :修改配置
	 * 
	 * @author : fei yang
	 * @version ：2017年2月8日 下午2:05:48
	 * @param request
	 * @param model
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("toUpdateConfig")
	public OpreateResult toUpdateConfig(HttpServletRequest request, Model model, LeadConfigBean bean) {
		OpreateResult opreateResult = new OpreateResult("200", "操作成功", "main", "", "");
		leadConfigService.saveOrUpdate(bean);
		return opreateResult;
	}

	/**
	 * 
	   * 功能说明    : 判断文件路径是否合法
	   * @author   : fei yang 
	   * @version ：2017年2月9日 上午11:09:47 
	   * @param response
	   * @param filePath
	 */
	@RequestMapping("isFilePath")
	public static void isFilePath(HttpServletResponse response,String filePath){
		HttpServletResponseUtils.writer(response, String.valueOf(FileUtils.isDirectory(filePath)));
	}

	
}

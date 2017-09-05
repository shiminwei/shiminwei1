package com.ahcd.controller.web;

import java.math.BigDecimal;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ahcd.common.StringUtil;
//import com.ahcd.pojo.ChartsBean;
import com.ahcd.service.ChartsService;
import com.ahcd.service.impl.ZdConstantServiceImpl;
import com.alibaba.fastjson.JSON;

/**
 * @author : fei yang
 * @version ：2016年11月25日 下午2:22:10
 */
@Controller
@RequestMapping("web/showCharts")
public class ShowChartsController {

	@Resource
	private ChartsService chartsService;

	@Resource
	private ZdConstantServiceImpl zdConstantService;


	@RequestMapping("/chartList")
	public String chartList(HttpServletRequest request, Model model) {
		List<String[]> list = chartsService.getSeMap("1");
		int bennianSj = chartsService.getSum("1");
		Map<String, String> bennianSeMap = zdConstantService.getConstantByType("bennian_se");
		String bennianSe = "0";
		for (String key : bennianSeMap.keySet()) {
			if (key.equals("2016")) {
				bennianSe = bennianSeMap.get(key);
			}
		}
		model.addAttribute("bennianSe", bennianSe);
		model.addAttribute("bennianSj", bennianSj);
		String bl = getBl(bennianSe, bennianSj);
		model.addAttribute("bennianBl", bl);
		String listStr = JSON.toJSON(list).toString();
		model.addAttribute("listStr", listStr);
		return "web/showCharts/chartList";
	}

	public static String getBl(String bennianSe, int bennianSj) {
		if (StringUtil.isBlank(bennianSe) || StringUtil.isBlank(bennianSj)) {
			return new BigDecimal(0).toString();
		} else {
			BigDecimal bl = new BigDecimal(bennianSj).divide(new BigDecimal(bennianSe), 4, BigDecimal.ROUND_UP);
			double blBaiFen = bl.multiply(new BigDecimal(100)).doubleValue();
			return String.valueOf(blBaiFen);
		}
	}
}

package com.ahcd.controller.admin;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ahcd.common.Constant;
import com.ahcd.pojo.ChartsBean;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.ZdConstant;
import com.ahcd.service.ChartsService;
import com.ahcd.service.ZdConstantService;

/**
 * @author : fei yang
 * @version ：2016年12月22日 下午4:27:47
 */
@Controller
@RequestMapping("admin/charts")
public class ChartsController {

	@Resource
	private ChartsService chartsService;

	@Resource
	private ZdConstantService zdConstantService;

	@RequestMapping("/list")
	public String getList(HttpServletRequest request, Model model, Page<ChartsBean> page, ChartsBean bean) {
		Page<ChartsBean> pageList = chartsService.getList(page, bean);
		List<ZdConstant> list = zdConstantService.getConstantListByType(Constant.CHARTS_LAYOUT_TYPE);
		for (int i = 0; i < pageList.getResult().size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (pageList.getResult().get(i).getLayoutType().equals(list.get(j).getCode())) {
					pageList.getResult().get(i).setLayoutType(list.get(j).getValue());
				}
			}
		}
		model.addAttribute("pageList", pageList);
		model.addAttribute("bean", bean);
		return "admin/charts/list";
	}

}

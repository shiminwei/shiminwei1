package com.ahcd.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ahcd.common.DbUntil;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.DataTableBean;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.Page;
import com.ahzd.service.DataSourceMongoService;

@Controller
@RequestMapping("web/lookTable")
public class LookTableController {

	@Resource
	private DataSourceMongoService dataSourceMongoService;

	/**
	 * 
	 * 功能说明 :查询数据源的所有表以及注释
	 * 
	 * @author : fei yang
	 * @version ：2017年5月3日 下午3:53:30
	 * @param request
	 * @param model
	 * @param pageList
	 * @param datasourceId
	 *            数据源ID
	 * @param dataTableBean
	 * @return
	 */
	@RequestMapping("/list")
	public String toLookList(HttpServletRequest request, Model model, Page<DataTableBean> pageList, String datasourceId,
			DataTableBean dataTableBean) {
		pageList.setNumPerPage(10);
		if (StringUtil.isNotEmpty(datasourceId)) {
			DatasourceBean datasourceBean = dataSourceMongoService.findById(datasourceId);
			pageList = DbUntil.getAllTableByData(pageList, datasourceBean.getId(), datasourceBean.getType(),
					datasourceBean.getDatabase(), dataTableBean.getTableName());
			pageList.setPageNum(pageList.getPageNum());
			pageList.setNumPerPage(pageList.getNumPerPage());
		}
		model.addAttribute("pageList", pageList);
		model.addAttribute("bean", dataTableBean);
		model.addAttribute("datasourceId", datasourceId);
		return "index/lookList";
	}
}

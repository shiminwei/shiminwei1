package com.ahcd.controller.web;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ahcd.common.Constant;
import com.ahcd.common.StringUtil;
import com.ahcd.dbutil.WebDataQueryJdbc;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentInfo;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.impl.SysDepartmentInfoServiceImpl;
import com.ahzd.service.ExcelTemplateMongoService;

/**
 * @author : fei yang
 * @version ：2016年11月14日 下午5:03:30
 */
@Controller
@RequestMapping("web/dataQuery")
public class WebDataQueryController {
	//@Resource
	//private JsonConfigServiceImpl jsonConfigService;

	@Resource
	private SysDepartmentInfoServiceImpl sysDepartmentInfoService;

	@Resource  
	private ExcelTemplateMongoService excelTemplateMongoService; 
	/**
	 * 
	 * 功能说明 :当前用户模板列表
	 * 
	 * @author : fei yang
	 * @version ：2016年11月14日 下午5:24:06
	 * @param request
	 * @param model
	 * @param page
	 * @param bean
	 * @return
	 */
	@RequestMapping("list")
	public String getDataQueryList(HttpServletRequest request, Model model, Page<ExcelTemplate> page,
			ExcelTemplate bean) {
		SysReportUser user = (SysReportUser) request.getSession().getAttribute(Constant.SESSION_USER);
		//page = jsonConfigService.getPageListByUserId(page, user.getUserId());
		page = excelTemplateMongoService.getPageListByUserId(page, user.getUserId());
		model.addAttribute("pageList", page);
		model.addAttribute("bean", bean);
		return "web/dataQuery/list";
	}

	/**
	 * 
	 * 功能说明 : 查询历史数据列表
	 * 
	 * @author : fei yang
	 * @version ：2016年11月14日 下午5:24:20
	 * @param request
	 * @param model
	 * @param bean
	 * @return
	 */
	@RequestMapping("toQueryDate")
	public String toQueryDate(HttpServletRequest request, Model model, Page<ExcelTemplate> pageList, ExcelTemplate bean,
			String type) {
		Map<String, String> map = Constant.getMapByType(bean.getZq());
		//ExcelTemplate newBean = jsonConfigService.getExcelTemplateByName(bean.getName());
		if(StringUtil.isBlank(bean.getName())){
			return "web/dataQuery/queryData";
		}
//		try {
//			String tempalename = new String(bean.getName().getBytes("ISO-8859-1"),"UTF-8");
//			bean.setName(tempalename);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		ExcelTemplate newBean = excelTemplateMongoService.findByName(bean.getName());
		int nowYear = Calendar.getInstance().get(Calendar.YEAR);
		String nowMonth = Constant.getDefaultSelectedValue(bean.getZq());
		SysReportUser user = (SysReportUser) request.getSession().getAttribute(Constant.SESSION_USER);
		List<SysDepartmentInfo> departmentInfos = sysDepartmentInfoService
				.selectSysDepartmentInfoBySub(user.getDepartmentId());
		if (!StringUtil.isBlank(type)) {
			newBean.setYearColName(bean.getYearColName());
			newBean.setMonthColName(bean.getMonthColName());
			List<List<String>> resultList = WebDataQueryJdbc.getListByExcelBean(newBean, pageList, departmentInfos);
			int count = WebDataQueryJdbc.getCount(newBean, departmentInfos);
			pageList.setTotalCount(count);
			nowYear = Integer.valueOf(bean.getYearColName());
			nowMonth = bean.getMonthColName();
			model.addAttribute("resultList", resultList);
		}
		pageList.setQueryBean(newBean);
		model.addAttribute("bean", bean);
		model.addAttribute("year", Constant.getQueryYear());
		model.addAttribute("month", map);
		model.addAttribute("pageList", pageList);
		model.addAttribute("nowYear", nowYear);
		model.addAttribute("nowMonth", nowMonth);
		return "web/dataQuery/queryData";
	}
}

package com.ahcd.controller.admin;

import java.util.Calendar;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ahcd.common.Constant;
import com.ahcd.common.OpreateResult;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentTemplate;
import com.ahcd.pojo.SysReportChileBean;
import com.ahcd.pojo.SysReportLog;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.ISysDepartmentTemplateService;
import com.ahcd.service.impl.SysReportLogServiceImpl;
import com.ahzd.service.ExcelTemplateMongoService;

@Controller
@RequestMapping("/admin/data")
public class DataQueryController {

	@Resource
	private ISysDepartmentTemplateService departmentTemplateService;

	@Resource
	private SysReportLogServiceImpl sysReportLogService;
	
	@Resource  
	private ExcelTemplateMongoService excelTemplateMongoService; 

	/**
	 * 
	 * 功能说明 : 数据汇总
	 * 
	 * @author : fei yang
	 * @version ：2016年11月9日 下午3:37:59
	 * @param request
	 * @param model
	 * @param sysDepartmentTemplate
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	@RequestMapping("/summary")
	public String list(HttpServletRequest request, Model model, Page<SysReportLog> page, SysReportLog reportLog) {
		page = sysReportLogService.getPageList(page, reportLog);
		model.addAttribute("pageList", page);
		model.addAttribute("reportLog", reportLog);
		return "admin/dataquery/summary";
	}

	@RequestMapping("/toReportInfo")
	public String query(HttpServletRequest request, Model model, Page<SysDepartmentTemplate> page,
			SysDepartmentTemplate sysDepartmentTemplate) {
		page = departmentTemplateService.getDepartmentTemplatePage(page, sysDepartmentTemplate);
		model.addAttribute("pageList", page);
		model.addAttribute("bean", sysDepartmentTemplate);
		return "admin/dataquery/toReportInfo";
	}

	/**
	 * 
	 * 功能说明 : 详情查看及状态设置
	 * 
	 * @author : fei yang
	 * @version ：2017年2月27日 上午9:58:44
	 * @param request
	 * @param model
	 * @param chileBean
	 * @param page
	 * @param log
	 * @return
	 */
	@RequestMapping("/queryReportInfo")
	public String queryReportInfo(HttpServletRequest request, Model model, SysReportChileBean chileBean,
			Page<SysReportLog> page, SysReportLog log) {
		Map<String, String> map = Constant.getMapByType(chileBean.getZq());
		model.addAttribute("year", Constant.getQueryYear());
		model.addAttribute("month", map);
		if (chileBean.getBeginYear() == 0) {
			chileBean.setBeginYear(Calendar.getInstance().get(Calendar.YEAR));
			chileBean.setBeginMonth(Constant.getDefaultStarMonth(chileBean.getZq()));
			chileBean.setEndYear(Calendar.getInstance().get(Calendar.YEAR));
			chileBean.setEndMonth(12);
		}
		page = sysReportLogService.getDifferPageList(page, chileBean, log);
		model.addAttribute("pageList", page);
		model.addAttribute("chileBean", chileBean);
		model.addAttribute("log", log);
		return "admin/dataquery/queryReportInfo";
	}

	/**
	 * 设置成已报送
	 */
	@ResponseBody
	@RequestMapping("/isReport")
	public OpreateResult isReport(HttpServletRequest requsest, SysReportLog reportLog, String reportMonth) {
		OpreateResult result = new OpreateResult("300", "设置失败", "toQueryReport", "", "");
		SysReportUser user = (SysReportUser) requsest.getSession().getAttribute(Constant.SESSION_USER);
		ExcelTemplate newBean = excelTemplateMongoService.findByName(reportLog.getTemplateName());
		if (user != null && user.getUserId().intValue() == 1 && user.getUserCode().equals("admin")) {
			if (newBean == null) {
				result.setMessage("系统模板存在问题，请联系管理员");
				return result;
			}
			boolean flag = sysReportLogService.updateOrAddLog(reportLog, user);
			if (flag) {
				result.setStatusCode("200");
				result.setMessage("设置成功");
			}
		} else {
			result.setMessage("您不具有当前操作权限");
		}
		return result;
	}

}

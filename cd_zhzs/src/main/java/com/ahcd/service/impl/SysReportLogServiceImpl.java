package com.ahcd.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.ahcd.common.Constant;
import com.ahcd.common.StringUtil;
import com.ahcd.dao.SysReportLogMapper;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentTemplate;
import com.ahcd.pojo.SysReportChileBean;
import com.ahcd.pojo.SysReportLog;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.pojo.WebDataReportBean;
import com.ahcd.service.SysReportLogService;

/**
 * @author : fei yang
 * @version ：2016年11月7日 上午11:43:45
 */
@Service("sysReportLogService")
public class SysReportLogServiceImpl implements SysReportLogService {

	@Resource
	private SysReportLogMapper sysReportLogMapper;

	@Override
	public int insert(SysReportLog record) {
		return sysReportLogMapper.insert(record);
	}

	@Override
	public List<SysReportLog> getList(SysReportLog record) {
		return sysReportLogMapper.selectByBean(record);
	}

	/**
	 * 
	 * 功能说明 : 新增报送日志
	 * 
	 * @author : fei yang
	 * @version ：2016年11月7日 下午1:31:17
	 * @param user
	 * @param dataReportBean
	 * @param departmentTemplate
	 * @return
	 */
	public int addReportLog(SysReportUser user, WebDataReportBean dataReportBean,
			SysDepartmentTemplate departmentTemplate, ExcelTemplate template, int reportDataNum) {
		SysReportLog record = new SysReportLog();
		record.setTemplateName(template.getName());
		record.setTableName(template.getTableName());
		record.setUserId(String.valueOf(user.getUserId()));
		record.setUserName(user.getUserName());
		record.setAreaId(user.getAreaId());
		record.setAreaName(user.getAreaName());
		record.setDeparmentId(user.getDepartmentId());
		record.setDeparmentName(user.getDepartmentName());
		record.setReportYear(dataReportBean.getYear());
		record.setReportMonth(dataReportBean.getMonth());
		record.setReportType(new BigDecimal(dataReportBean.getReportType()));
		record.setReportZq(new BigDecimal(departmentTemplate.getReportPeroid()));
		record.setReportDate(new Date());
		record.setReportDataNum(new BigDecimal(reportDataNum + 1));
		record.setIsReport(1);
		return sysReportLogMapper.insert(record);
	}

	/**
	 * 
	 * 功能说明 :本期是否报送
	 * 
	 * @author : fei yang
	 * @version ：2016年11月7日 下午3:48:55
	 * @param page
	 * @return
	 */
	public Page<ExcelTemplate> getIsReportList(Page<ExcelTemplate> page, String deparmentId) {
		SysReportLog record = new SysReportLog();
		String nowYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		record.setReportYear(nowYear);
		record.setIsReport(1);
		record.setDeparmentId(deparmentId);
		List<SysReportLog> logs = sysReportLogMapper.selectByBean(record);
		for (int i = 0; i < page.getResult().size(); i++) {
			String reportMonth = Constant.getDefaultSelectedValue(page.getResult().get(i).getZq());
			for (int j = 0; j < logs.size(); j++) {
				if (page.getResult().get(i).getName().equals(logs.get(j).getTemplateName())
						&& reportMonth.equals(logs.get(j).getReportMonth())) {
					page.getResult().get(i).setNowIsReport(1);

				}
			}
		}
		return page;
	}

	@Override
	public List<SysReportLog> selectAllByDepartment(Page<SysReportLog> page, SysReportLog reportLog) {
		String nowYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		page.setBeginRow(beginRow);
		page.setEndRow(endRow);
		List<SysReportLog> list = sysReportLogMapper.selectAllByDepartment(page);
		for (int i = 0; i < list.size(); i++) {
			if (!StringUtil.isEmpty(list.get(i).getReportZq()) && !StringUtil.isEmpty(list.get(i).getReportYear())
					&& !StringUtil.isEmpty(list.get(i).getReportMonth())) {// 判断是否报送
				String nowMonth = Constant.getDefaultSelectedValue(list.get(i).getReportZq().intValue());
				if (list.get(i).getReportYear().equals(nowYear) && list.get(i).getReportMonth().equals(nowMonth)) {
					list.get(i).setPeriodIsReport(1);
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * 功能说明 :报送汇总
	 * 
	 * @author : fei yang
	 * @version ：2016年11月14日 下午2:38:59
	 * @param page
	 * @param reportLog
	 * @return
	 */
	public Page<SysReportLog> getPageList(Page<SysReportLog> page, SysReportLog reportLog) {
		page.setQueryBean(reportLog);
		page.setTotalCount(sysReportLogMapper.countPage(page));
		List<SysReportLog> list = selectAllByDepartment(page, reportLog);
		SysReportLog record = new SysReportLog();
		record.setReportYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		record.setIsReport(1);
		List<SysReportLog> nowYaerList = sysReportLogMapper.selectByBean(record);
		for (int i = 0; i < list.size(); i++) {
			int startMonth = list.get(i).getStartDate() - 1;
			int endMonth = list.get(i).getEndDate() - 1;
			for (int j = 0; j < nowYaerList.size(); j++) {
				String reportYear = nowYaerList.get(j).getReportYear();
				String reportMonth = nowYaerList.get(j).getReportMonth();
				if (nowYaerList.get(j).getTemplateName().equals(list.get(i).getTemplateName())
						&& StringUtil.isInteger(reportYear) && StringUtil.isInteger(reportMonth)) {
					if (String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).equals(reportYear)) {
						if (startMonth <= Integer.valueOf(reportMonth) && endMonth >= Integer.valueOf(reportMonth)) {
							if (StringUtil.isBlank(nowYaerList.get(j).getDeparmentId())) {
								if (nowYaerList.get(j).getUserId().equals("1")) {
									list.get(i).setPeriodIsReport(1);
								}
							} else if (nowYaerList.get(j).getDeparmentId().equals(list.get(i).getDeparmentId())) {
								list.get(i).setPeriodIsReport(1);
							}
						}
					}
				}
			}
		}
		page.setResult(list);
		return page;

	}

	/**
	 * 功能说明 :报送查询
	 */
	@Override
	public Page<SysReportLog> reportqueryPage(Page<SysReportLog> page, SysReportLog reportLog) {
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		page.setBeginRow(beginRow);
		page.setEndRow(endRow);
		page.setQueryBean(reportLog);
		page.setTotalCount(sysReportLogMapper.countReportQueryPage(page));
		List<SysReportLog> list = sysReportLogMapper.reportqueryPage(page);
		page.setResult(list);
		return page;
	}

	@Override
	public int updateIsReport(SysReportLog reportLoglogId) {
		return sysReportLogMapper.updateIsReport(reportLoglogId);
	}

	/**
	 * 
	 * 功能说明 : 获取相差月份的空集合
	 * 
	 * @author : fei yang
	 * @version ：2016年11月15日 下午1:39:05
	 * @param beginYear
	 * @param beginMonth
	 * @param endYear
	 * @param endMonth
	 * @param zq
	 * @return
	 */
	public static List<SysReportLog> getDifferMonthList(SysReportChileBean bean) {
		List<SysReportLog> list = new ArrayList<SysReportLog>();
		int beginYearInt = bean.getBeginYear();
		int endYearInt = bean.getEndYear();
		int beginMonthInt = bean.getBeginMonth();
		int endMonthInt = bean.getEndMonth();
		Map<String, String> map = Constant.getMapByType(bean.getZq());
		int falg = endYearInt - beginYearInt;
		for (int i = beginYearInt; i <= endYearInt; i++) {
			int differYear = endYearInt - i;
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if (i < endYearInt) {
					if (falg > 1) {
						if (differYear > 1) {
							if (Integer.valueOf(entry.getKey()) >= beginMonthInt)
								list.add(getNewLog(i, entry));
						} else {
							list.add(getNewLog(i, entry));
						}
					} else {
						if (Integer.valueOf(entry.getKey()) >= beginMonthInt)
							list.add(getNewLog(i, entry));
					}
				} else {
					if (falg == 0) {
						if (Integer.valueOf(entry.getKey()) >= beginMonthInt
								&& Integer.valueOf(entry.getKey()) <= endMonthInt)
							list.add(getNewLog(i, entry));
					} else {
						if (Integer.valueOf(entry.getKey()) <= endMonthInt)
							list.add(getNewLog(i, entry));
					}
				}
			}
		}
		return list;
	}

	public static SysReportLog getNewLog(int year, Map.Entry<String, String> entry) {
		SysReportLog log = new SysReportLog();
		log.setReportYear(String.valueOf(year));
		log.setReportMonth(entry.getKey() + "," + entry.getValue());
		return log;
	}

	public static List<SysReportLog> getNewList(Page<SysReportLog> page, List<SysReportLog> reList,
			List<SysReportLog> logs) {
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		List<SysReportLog> newList = new ArrayList<SysReportLog>();
		for (int i = 0; i < reList.size(); i++) {
			if (i >= beginRow && i < endRow) {
				String[] str = reList.get(i).getReportMonth().split(",");
				String monthCode = str[0];
				String monthName = str[1];
				for (int j = 0; j < logs.size(); j++) {
					if (reList.get(i).getReportYear().equals(logs.get(j).getReportYear())
							&& monthCode.equals(logs.get(j).getReportMonth())) {
						reList.get(i).setPeriodIsReport(1);
						reList.get(i).setReportType(logs.get(j).getReportType());
						reList.get(i).setLogId(logs.get(j).getLogId());
					}
				}
				reList.get(i).setDeparmentName(monthCode);
				reList.get(i).setReportMonth(monthName);
				newList.add(reList.get(i));
			}
		}
		return newList;

	}

	/**
	 * 
	 * 功能说明 : 获取查询和设置集合
	 * 
	 * @author : fei yang
	 * @version ：2016年11月15日 下午2:13:54
	 * @param page
	 * @param chileBean
	 * @return
	 */
	public Page<SysReportLog> getDifferPageList(Page<SysReportLog> page, SysReportChileBean chileBean,
			SysReportLog record) {
		List<SysReportLog> reList = getDifferMonthList(chileBean);

		int totalCount = reList.size();
		record.setIsReport(1);
		List<SysReportLog> logs = sysReportLogMapper.selectByBean(record);
		reList = getNewList(page, reList, logs);
		page.setResult(reList);
		page.setTotalCount(totalCount);
		return page;
	}

	public int addReportLogByAdmin(SysReportLog reportLog) {

		return sysReportLogMapper.insert(reportLog);
	}

	/**
	 * 
	 * 功能说明 : 设置报送未报送
	 * 
	 * @author : fei yang
	 * @version ：2016年11月15日 下午3:58:12
	 * @param reportLog
	 * @return
	 */
	public int updateOrAddLog(SysReportLog reportLog, ExcelTemplate template, SysReportUser user, String reportMonth) {
		// List<SysReportLog> sysReportLog =
		// sysReportLogMapper.selectByReportMonth(reportMonth);
		List<SysReportLog> sysReportLog = sysReportLogMapper.selectByBean(reportLog);

		BigDecimal logId1 = null;
		String reportmonth = "0";
		int isReport = 1;
		String templateName = null;
		if (sysReportLog != null) {
			for (int i = 0; i < sysReportLog.size(); i++) {
				reportmonth = sysReportLog.get(i).getReportMonth();
				logId1 = sysReportLog.get(i).getLogId();
				isReport = sysReportLog.get(i).getIsReport();
				templateName = sysReportLog.get(i).getTemplateName();
				if (template != null) {
					if (reportMonth.equals(reportmonth) && isReport != 1 && templateName.equals(template.getName())) {
						reportLog.setIsReport(1);
						reportLog.setLogId(logId1);
						return sysReportLogMapper.updateIsReport(reportLog);
					} else if (reportMonth.equals(reportmonth) && isReport != 0
							&& templateName.equals(template.getName())) {
						reportLog.setIsReport(0);
						reportLog.setLogId(logId1);
						return sysReportLogMapper.updateIsReport(reportLog);
					}
				}
			}
			if (template == null) {
				return 0;
			}
		}
		reportLog.setIsReport(1);
		reportLog.setTemplateName(template.getName());
		reportLog.setTableName(template.getTableName());
		reportLog.setUserId(String.valueOf(user.getUserId()));
		reportLog.setUserName(user.getUserName());
		reportLog.setAreaId(user.getAreaId());
		reportLog.setAreaName(user.getAreaName());
		reportLog.setDeparmentId(user.getDepartmentId());
		reportLog.setDeparmentName(user.getDepartmentName());
		reportLog.setReportType(new BigDecimal(4));
		reportLog.setReportDate(new Date());
		reportLog.setReportDataNum(new BigDecimal(0));
		reportLog.setIsReport(1);
		return addReportLogByAdmin(reportLog);
	}

	@Override
	public List<SysReportLog> selectByReportMonth(String reportMonth) {
		return sysReportLogMapper.selectByReportMonth(reportMonth);
	}

	/**
	 * 获取部门以及子部门的报送日志列表
	 */
	@Override
	public List<SysReportLog> selectByDepartmentId(SysReportLog sysReportLog) {

		return sysReportLogMapper.selectByDepartmentId(sysReportLog);
	}

	/**
	 * 
	 * 功能说明 : 设置报送和未报送
	 * 
	 * @author : fei yang
	 * @version ：2017年2月27日 上午10:44:46
	 * @param reportLog
	 * @param user
	 * @return
	 */
	public boolean updateOrAddLog(SysReportLog reportLog, SysReportUser user) {
		if (StringUtil.isBlank(reportLog.getLogId())) {
			return adminToReport(reportLog, user);
		} else {
			SysReportLog log = sysReportLogMapper.selectByPrimaryKey(String.valueOf(reportLog.getLogId()));
			if (log != null) {
				reportLog.setIsReport(0);
				int upSize = sysReportLogMapper.updateByPrimaryKey(reportLog);
				if (upSize > 0) {
					return	adminToReport(reportLog, user);
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * 功能说明 :系统管理员新增报送数据
	 * 
	 * @author : fei yang
	 * @version ：2017年2月27日 上午10:29:22
	 * @param reportLog
	 * @param user
	 * @return
	 */
	public boolean adminToReport(SysReportLog reportLog, SysReportUser user) {
		boolean flag = false;
		if (StringUtil.isNotEmpty(reportLog.getTemplateName())) {
			if (user != null && String.valueOf(user.getUserId()).equals("1") && "admin".equals(user.getUserName())) {
				reportLog.setReportType(new BigDecimal("4"));// 设置成系统报送
				reportLog.setUserId(String.valueOf(user.getUserId()));
				reportLog.setUserName(user.getUserName());
				reportLog.setReportDate(new Date());
				reportLog.setReportDataNum(new BigDecimal("0"));// 0条报送数量
				reportLog.setIsReport(1);
				if (sysReportLogMapper.insert(reportLog)==1) {
					flag = true;				
				} 
			}
		}
		return flag;
	}
}

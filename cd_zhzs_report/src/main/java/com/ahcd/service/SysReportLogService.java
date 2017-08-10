package com.ahcd.service;


import java.util.List;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysReportLog;

/**
 * @author : fei yang
 * @version ：2016年11月7日 上午11:42:40
 */

public interface SysReportLogService {
	int insert(SysReportLog record);
	
	List<SysReportLog>getList(SysReportLog record);
	public List<SysReportLog> selectAllByDepartment(Page<SysReportLog>page,SysReportLog reportLog);

	public Page<SysReportLog> reportqueryPage(Page<SysReportLog>page,SysReportLog reportLog);
	
	//TODO 设置成已报送
	int updateIsReport(SysReportLog reportLog);
	
	//TODO 根据月查
	List<SysReportLog> selectByReportMonth(String reportMonth);

	//获取部门以及子部门的报送日志
	List<SysReportLog>selectByDepartmentId(SysReportLog sysReportLog);
}

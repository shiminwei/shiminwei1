package com.ahcd.dao;

import java.util.List;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysReportLog;

public interface SysReportLogMapper {
	int deleteByPrimaryKey(String logId);

	int insert(SysReportLog record);

	int insertSelective(SysReportLog record);

	SysReportLog selectByPrimaryKey(String logId);

	int updateByPrimaryKeySelective(SysReportLog record);

	int updateByPrimaryKey(SysReportLog record);

	public List<SysReportLog> selectByBean(SysReportLog record);

	public List<SysReportLog> selectAllByDepartment(Page<SysReportLog> page);

	int countPage(Page<SysReportLog> page);	
	
	public List<SysReportLog> reportqueryPage(Page<SysReportLog>page);
	int countReportQueryPage(Page<SysReportLog> page);	
	
	//TODO 设置成已报送
	int updateIsReport(SysReportLog reportLog);
	
	//TODO 根据月查
	List<SysReportLog> selectByReportMonth(String reportMonth);
	
	List<SysReportLog>selectByDepartmentId(SysReportLog sysReportLog);
	
	
	List<SysReportLog> selectByBeanOrAdmin(SysReportLog sysReportLog);
}
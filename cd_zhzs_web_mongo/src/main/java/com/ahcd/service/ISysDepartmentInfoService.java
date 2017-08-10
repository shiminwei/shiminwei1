package com.ahcd.service;

import java.util.List;

import com.ahcd.pojo.SysDepartmentInfo;

public interface ISysDepartmentInfoService {
	public List<SysDepartmentInfo> getDepartment();
	
	public List<SysDepartmentInfo> getSysDepartmentInfoByAreaId(String departmentAreaId);
	
	public void insertDepartmentInfo(SysDepartmentInfo sysDepartmentinfo);
	
	public void updateDapartmentInfoByID(SysDepartmentInfo sysDepartmentInfo);
	
	public SysDepartmentInfo selectDapartmentInfoByID(String departmentId);
	
	public List<SysDepartmentInfo> selectSysDepartmentInfo();

	public List<SysDepartmentInfo> getDepartmentNames();
	
	public SysDepartmentInfo selectDapartmentInfoBydepartmentCode(String departmentCode);	
	
	//TODO 假删用户
	int isdelete(String departmentId);
}

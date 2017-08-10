package com.ahcd.service;

import java.util.List;

import com.ahcd.pojo.Page;
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

	List<SysDepartmentInfo> selectSysDepartmentInfoBySub(String departmentCode);

	Page<SysDepartmentInfo> selectPage(Page<SysDepartmentInfo> page);

	//TODO查找某个地区id查具体部门
	Page<SysDepartmentInfo> selectSysDepartmentInfoByID(Page<SysDepartmentInfo> page);
	
	//TODO根据name查找部门
	List<SysDepartmentInfo> selectSysDepartmentInfoByName(String name);
	
	//TODO根据area新增后查找部门
	List<SysDepartmentInfo> selectSysDepartmentInfoAreaID2(String id);
}

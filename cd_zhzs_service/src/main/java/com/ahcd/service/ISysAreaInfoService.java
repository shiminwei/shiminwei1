package com.ahcd.service;

import java.util.List;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysAreaInfo;

public interface ISysAreaInfoService {
	public  List<SysAreaInfo> getArea();
	
	public SysAreaInfo selectAreaCodeByAreaId(String departmentAreaId);
	
	public SysAreaInfo selectAreaNameByAreaCode(int areaCode);
	
	public int isdelete(String areaId);
	
	public int insertInfo(SysAreaInfo record);
	
	public int updateInfo(SysAreaInfo record);
	
	public Page<SysAreaInfo> selectAreasByPage(Page<SysAreaInfo> page);
}

package com.ahcd.service;

import java.util.List;

import com.ahcd.pojo.SysAreaInfo;

public interface ISysAreaInfoService {
	public  List<SysAreaInfo> getArea();
	
	public SysAreaInfo selectAreaCodeByAreaId(String departmentAreaId);
}

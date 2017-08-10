package com.ahcd.service;

import java.util.HashMap;
import java.util.List;

import com.ahcd.pojo.SysDepartmentAuth;

public interface ISysDepartmentAuthService {
	
	 int insertSubDepartments(SysDepartmentAuth record);
	 
	 List<SysDepartmentAuth> getNoticeDepartmentAuth(String departmentId);
	 
	 int deleteAllByDepartmentId(String departmentId);
	 
	 int deleteByDepartmentId(HashMap parameterMap);
}

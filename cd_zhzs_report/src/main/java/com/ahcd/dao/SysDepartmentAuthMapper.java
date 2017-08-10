package com.ahcd.dao;

import java.util.HashMap;
import java.util.List;

import com.ahcd.pojo.SysDepartmentAuth;

public interface SysDepartmentAuthMapper {
    int insertSubDepartments(SysDepartmentAuth sysDepartmentAuth);

    int insertSelective(SysDepartmentAuth sysDepartmentAuth);
    
    List<SysDepartmentAuth> getNoticeDepartmentAuth(String departmentId);
    
    int deleteAllByDepartmentId(String departmentId);
    
    int deleteByDepartmentId(HashMap parameterMap);
}
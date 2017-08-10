package com.ahcd.dao;

import java.math.BigDecimal;
import java.util.List;

import com.ahcd.pojo.SysDepartmentInfo;

public interface SysDepartmentInfoMapper {
	//TODO 假删用户
	int isdelete(String departmentId);

    int insertSelective(SysDepartmentInfo record);

    SysDepartmentInfo selectDapartmentInfoByID(String departmentId);

    int updateDapartmentInfoByID(SysDepartmentInfo sysDepartmentInfo);

    int updateByPrimaryKey(SysDepartmentInfo record);
    
    List<SysDepartmentInfo> selectSysDepartmentInfo();
    
    List<SysDepartmentInfo> selectSysDepartmentInfoByAreaId(String departmentAreaId);
    
    void insertDepartmentInfo(SysDepartmentInfo sysDepartmentInfo);
    
    String selectSysDepartmentInfo2(String departmentAreaId);
    
    List<SysDepartmentInfo> selectDapartmentInfo2();

    SysDepartmentInfo selectDapartmentInfoBydepartmentCode(String departmentCode);
}
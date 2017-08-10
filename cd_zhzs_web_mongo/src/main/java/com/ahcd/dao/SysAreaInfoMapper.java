package com.ahcd.dao;

import java.util.List;

import com.ahcd.pojo.SysAreaInfo;

public interface SysAreaInfoMapper {
    int deleteByPrimaryKey(String areaId);

    int insert(SysAreaInfo record);

    int insertSelective(SysAreaInfo record);

    SysAreaInfo selectByPrimaryKey(String areaId);

    int updateByPrimaryKeySelective(SysAreaInfo record);

    int updateByPrimaryKey(SysAreaInfo record);
    
    List<SysAreaInfo> selectAreaName();
    
    SysAreaInfo selectAreaCodeByAreaId(String departmentAreaId);
}
package com.ahcd.dao;

import java.util.List;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysAreaInfo;

public interface SysAreaInfoMapper {
    int deleteByPrimaryKey(String areaId);

    int insert(SysAreaInfo record);

    int insertSelective(SysAreaInfo record);

    SysAreaInfo selectAreaCodeByAreaId(String departmentAreaId);

    int updateByPrimaryKeySelective(SysAreaInfo record);

    int updateByPrimaryKey(SysAreaInfo record);
    
    List<SysAreaInfo> selectAreaName();
    
    SysAreaInfo selectAreaNameByAreaCode(int areaCode);
    
    int isdelete(String areaId);
    
    int countAreaPage(Page<SysAreaInfo> page);
    
    List<SysAreaInfo> selectAreasByPage(Page<SysAreaInfo> page);
}
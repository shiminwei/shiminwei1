package com.ahcd.dao;

import java.math.BigDecimal;
import java.util.List;

import com.ahcd.pojo.SysUserRole;

public interface SysUserRoleMapper {
	
    int deleteByPrimaryKey(BigDecimal userId);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);
    
    List<SysUserRole>getListByUerID(SysUserRole record);
    
    //TODO根据userId查找roleId
//    BigDecimal getRoleIDByUerID(BigDecimal userId);
}
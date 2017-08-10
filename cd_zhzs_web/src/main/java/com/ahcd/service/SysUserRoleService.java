package com.ahcd.service;

import java.math.BigDecimal;
import java.util.List;

import com.ahcd.pojo.SysUserRole;

/** 
 * @author   : fei yang 
 * @version ：2016年10月26日 下午2:39:03 
 */
public interface SysUserRoleService {
	
    int deleteByPrimaryKey(BigDecimal userId);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);
    
    List<SysUserRole>getListByUerID(SysUserRole record);
    
    
    int  saveOrUpdate (BigDecimal userId,String roleIds);
    
    //TODO根据userId查找roleId
//    BigDecimal getRoleIDByUerID(BigDecimal userId);
}

package com.ahcd.dao;

import com.ahcd.pojo.SysRole;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(BigDecimal roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(BigDecimal roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
    
	public List<SysRole> getPageList(HashMap<String, Object> map) ;
	
	Integer getCount(SysRole record);

	List<SysRole> getRoleList();
	/** 添加角色重名判断
	 * */
	Integer getSameRolenameCount(SysRole record);
}
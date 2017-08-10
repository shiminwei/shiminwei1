package com.ahcd.dao;

import java.math.BigDecimal;
import java.util.List;

import com.ahcd.pojo.SysWebRoleMenu;

public interface SysWebRoleMenuMapper {
	int insert(SysWebRoleMenu record);

	int insertSelective(SysWebRoleMenu record);

	public List<SysWebRoleMenu> getRoleList(SysWebRoleMenu record);
	
	int deleteByPrimaryKey(BigDecimal roleId);
}
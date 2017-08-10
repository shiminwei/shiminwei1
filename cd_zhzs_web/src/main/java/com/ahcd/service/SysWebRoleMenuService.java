package com.ahcd.service;


import java.math.BigDecimal;
import java.util.List;

import com.ahcd.pojo.SysWebRoleMenu;

/**
 * @author : fei yang
 * @version ：2016年10月25日 下午3:17:24
 */
public interface SysWebRoleMenuService {
	public int insert(SysWebRoleMenu record);

	public int insertSelective(SysWebRoleMenu record);
	
	public List<SysWebRoleMenu>getRoleList(SysWebRoleMenu record);
	
	int saveOrUpdate(String roleId ,String menuId);
	
	int deleteByPrimaryKey(BigDecimal roleId);
}

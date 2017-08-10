package com.ahcd.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.common.StringUtil;
import com.ahcd.dao.SysWebRoleMenuMapper;
import com.ahcd.pojo.SysWebRoleMenu;
import com.ahcd.service.SysWebRoleMenuService;

@Service("sysWebRoleMenuService")
public class SysWebRoleMenuServiceImpl implements SysWebRoleMenuService {

	@Resource
	private SysWebRoleMenuMapper sysWebRoleMenuMapper;

	@Override
	public int insert(SysWebRoleMenu record) {
		return sysWebRoleMenuMapper.insert(record);
	}

	@Override
	public int insertSelective(SysWebRoleMenu record) {
		return sysWebRoleMenuMapper.insertSelective(record);
	}

	@Override
	public List<SysWebRoleMenu> getRoleList(SysWebRoleMenu record) {
		return sysWebRoleMenuMapper.getRoleList(record);
	}

	@Override
	public int saveOrUpdate(String roleId, String menuIds) {
		sysWebRoleMenuMapper.deleteByPrimaryKey(new BigDecimal(roleId));
		if (StringUtil.isBlank(menuIds)) {
			return 1;
		} else {
			String[] menuId = menuIds.split(",");
			Set<String> set = new HashSet<String>(Arrays.asList(menuId));    
			set.toArray(menuId);  
			for (int i = 0; i < menuId.length; i++) {
				SysWebRoleMenu record = new SysWebRoleMenu();
				record.setRoleId(new BigDecimal(roleId));
				if(menuId[i] != null){
					record.setMenuId(new BigDecimal(menuId[i]));
					sysWebRoleMenuMapper.insert(record);
				}
			}
			return 1;
		}
	}

	@Override
	public int deleteByPrimaryKey(BigDecimal roleId) {
		return sysWebRoleMenuMapper.deleteByPrimaryKey(roleId);
	}

}

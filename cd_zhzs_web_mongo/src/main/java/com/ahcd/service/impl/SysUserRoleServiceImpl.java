package com.ahcd.service.impl;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.ahcd.common.StringUtil;
import com.ahcd.dao.SysUserRoleMapper;
import com.ahcd.pojo.SysUserRole;
import com.ahcd.service.SysUserRoleService;

@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {
	@Resource
	private SysUserRoleMapper sysUserRoleMapper;

	@Override
	public int deleteByPrimaryKey(BigDecimal userId) {

		return sysUserRoleMapper.deleteByPrimaryKey(userId);
	}

	@Override
	public int insert(SysUserRole record) {
		return sysUserRoleMapper.insert(record);
	}

	@Override
	public int insertSelective(SysUserRole record) {

		return sysUserRoleMapper.insertSelective(record);
	}

	@Override
	public List<SysUserRole> getListByUerID(SysUserRole record) {
		return sysUserRoleMapper.getListByUerID(record);
	}

	@Override
	public int saveOrUpdate(BigDecimal userId, String roleIds) {
		sysUserRoleMapper.deleteByPrimaryKey(userId);
		if (!StringUtil.isBlank(roleIds)) {
			String[] roleId=roleIds.split(",");
			for (int i = 0; i < roleId.length; i++) {
				
				SysUserRole record=new SysUserRole();
				record.setUserId(userId);
				record.setRoleId(new BigDecimal(roleId[i]));
				sysUserRoleMapper.insert(record);
			}
		}
		return 1;
	}

//	@Override
//	public BigDecimal getRoleIDByUerID(BigDecimal userId) {
//		return sysUserRoleMapper.getRoleIDByUerID(userId);
//	}
}

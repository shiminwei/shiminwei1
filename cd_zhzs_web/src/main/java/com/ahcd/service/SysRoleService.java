package com.ahcd.service;

import java.math.BigDecimal;
import java.util.List;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysRole;

/**
 * @author : fei yang
 * @version ：2016年10月25日 上午10:37:51
 */
public interface SysRoleService {
	int deleteByPrimaryKey(BigDecimal roleId);

	int insert(SysRole record);

	int insertSelective(SysRole record);

	SysRole selectByPrimaryKey(BigDecimal roleId);

	int updateByPrimaryKeySelective(SysRole record);

	int updateByPrimaryKey(SysRole record);

	public Page<SysRole> getPageList(Page<SysRole> page, SysRole bean);

	public Integer getCount(SysRole record);
	
	public SysRole getRoleById(int roleId);
	
	public  int saveOrUpdateBean(SysRole bean );
	
	public List<SysRole> getRoleList();
	
	public Integer getSameRolenameCount(SysRole record);
}

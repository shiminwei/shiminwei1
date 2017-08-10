package com.ahcd.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.ahcd.common.StringUtil;
import com.ahcd.dao.SysRoleMapper;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysRole;
import com.ahcd.service.SysRoleService;

@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	private SysRoleMapper sysRoleMapper;

	@Override
	public int deleteByPrimaryKey(BigDecimal roleId) {
		return sysRoleMapper.deleteByPrimaryKey(roleId);
	}

	@Override
	public int insert(SysRole record) {
		return sysRoleMapper.insert(record);
	}

	@Override
	public int insertSelective(SysRole record) {
		return sysRoleMapper.insertSelective(record);
	}

	@Override
	public SysRole selectByPrimaryKey(BigDecimal roleId) {
		return sysRoleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public int updateByPrimaryKeySelective(SysRole record) {
		return sysRoleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysRole record) {
		return sysRoleMapper.updateByPrimaryKey(record);
	}

	/**
	 * 获取分页信息
	 */
	@Override
	public Page<SysRole> getPageList(Page<SysRole> page, SysRole bean) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("roleName", bean.getRoleName());
		map.put("beginRow", (page.getPageNum() - 1) * page.getNumPerPage());
		map.put("endRow", page.getPageNum() * page.getNumPerPage() + 1);

		page.setResult(sysRoleMapper.getPageList(map));
		page.setTotalCount(sysRoleMapper.getCount(bean));
		return page;
	}

	@Override
	public Integer getCount(SysRole record) {
		return sysRoleMapper.getCount(record);
	}

	@Override
	public SysRole getRoleById(int roleId) {
		return sysRoleMapper.selectByPrimaryKey(new BigDecimal(roleId));
	}

	@Override
	public int saveOrUpdateBean(SysRole bean) {
		int fage = 0;
		if (StringUtil.isBlank(bean.getRoleId())) {
			fage = sysRoleMapper.insert(bean);
		} else {
			fage = sysRoleMapper.updateByPrimaryKey(bean);
		}
		return fage;
	}

	/**
	 * 
	   * 功能说明    :获取所有角色列表 
	   * @author   : fei yang 
	   * @version ：2016年10月25日 下午3:33:48 
	   * @param bean
	   * @return
	 */
	@Override
	public List<SysRole> getRoleList() {
		return sysRoleMapper.getRoleList();
	}
	
	/**
	 * 
	   * 功能说明      添加角色重名判断 
	   * @author   chenxt
	   * @version  2017-03-02 
	   * @param    record
	   * @return
	 */
	@Override
	public Integer getSameRolenameCount(SysRole record) {	
		return sysRoleMapper.getSameRolenameCount(record);
	}
}

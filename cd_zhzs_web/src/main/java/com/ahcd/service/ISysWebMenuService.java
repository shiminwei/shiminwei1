package com.ahcd.service;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysWebMenu;

/** 
* @author 作者 : fei yang
* @version 创建时间：2016年10月23日 下午12:11:32 
* 类说明 
*/

@Service
public interface ISysWebMenuService {
	public Page<SysWebMenu> getAllByBean(Page<SysWebMenu> page,SysWebMenu bean);
	public boolean deleteByPrimaryKey(String menuId);
	public SysWebMenu getBeanById(String menuId);
	public int saveOrUpdateBean(SysWebMenu bean);
	public List<SysWebMenu> getAllByType(SysWebMenu bean);
	public List<SysWebMenu> getWebMenuByUserID(String userId);
	public int getAMaxOrderNumber(BigDecimal menuLevel);
	public int getBCMaxOrderNumber(String parentName);
	public int countBCMaxOrderNumber(BigDecimal menuLevel);
	public int countAMaxOrderNumber(BigDecimal menuLevel);
	public SysWebMenu selectByOrderNumber(BigDecimal orderNumber);
	public int updateByPrimaryKeySelective(SysWebMenu record);
	public SysWebMenu selectByOrderNumberAndparentCode(HashMap map);
	public Page<SysWebMenu> getAllByBean1(Page<SysWebMenu> page,SysWebMenu bean);
	public Integer getACount(BigDecimal menuLevel);
	
	public List<SysWebMenu> getWebMenuByUserIDNew(BigDecimal userId);
	public SysWebMenu getDefaultIndexMenu();
}

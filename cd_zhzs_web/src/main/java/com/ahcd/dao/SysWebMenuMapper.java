package com.ahcd.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ahcd.pojo.SysWebMenu;

public interface SysWebMenuMapper {

	int deleteByPrimaryKey(String menuId);

	int insertSelective(SysWebMenu record);

	SysWebMenu selectByPrimaryKey(String menuId);

	int updateByPrimaryKeySelective(SysWebMenu record);

	int updateByPrimaryKey(SysWebMenu record);

	List<SysWebMenu> getAllByBean(HashMap<String, Object> map);
	
	List<SysWebMenu> getAllByBean1(HashMap<String, Object> map);

	String getMaxId();

	Integer getCount(HashMap map);
	
	Integer getACount(BigDecimal menuLevel);

	List<SysWebMenu> getAllByType(SysWebMenu bean);

	List<SysWebMenu> getWebMenuByUserID(String userId);
	
	int getAMaxOrderNumber(BigDecimal menuLevel);

	int getMaxOrderNumber(BigDecimal menuLevel);

	int getMaxAOrderNumber(BigDecimal menuLevel);
	
	int getBCMaxOrderNumber(String parentName);

	int countBCMaxOrderNumber(BigDecimal menuLevel);
	
	int countAMaxOrderNumber(BigDecimal menuLevel);
	
	SysWebMenu selectByOrderNumber(BigDecimal orderNumber);
	
	SysWebMenu selectByOrderNumberAndparentCode(HashMap map);

	List<SysWebMenu> getWebMenuByUserIDAndLevelAndParentCode(@Param("userId")BigDecimal userId, @Param("parentCode")String parentCode,@Param("menuLevel")int menuLevel);
	
	List<SysWebMenu> getWebMenuByUserID(@Param("userId")BigDecimal userId);

	SysWebMenu getDefaultIndexMenu();
}
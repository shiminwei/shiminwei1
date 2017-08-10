package com.ahcd.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.ahcd.pojo.CompanyAddressInfo;
import com.ahcd.pojo.Page;

public interface CompanyAddressInfoMapper {

	/**查询条数
     */
	int countPage(Page<CompanyAddressInfo> page);
	
	/**查询列表
     */
	List<CompanyAddressInfo> selectPage(Page<CompanyAddressInfo> page);

    /**新增
     */
    int insert(CompanyAddressInfo record);
    /**新增
     */
    int insertSelective(CompanyAddressInfo record);
    
    /**更新
     */
    int updateByPrimaryKeySelective(CompanyAddressInfo record);
    /**更新
     */
    int updateByPrimaryKey(CompanyAddressInfo record);
    
    /**查询实体
     */
    CompanyAddressInfo selectByPrimaryKey(String addressId);

    /**删除
     */
    int deleteByPrimaryKey(String addressId);
    /**删除
     */
    int deleteById(Map<String, BigDecimal> map);
    /**查询条数
     */
	int countPageById(Map<String, BigDecimal> map);
}
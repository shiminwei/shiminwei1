package com.ahcd.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.ahcd.pojo.CompanyNumberInfo;
import com.ahcd.pojo.Page;

public interface CompanyNumberInfoMapper {
	
	/**查询条数
     */
	int countPage(Page<CompanyNumberInfo> page);
	
	/**查询列表
     */
	List<CompanyNumberInfo> selectPage(Page<CompanyNumberInfo> page);

    /**新增
     */
    int insert(CompanyNumberInfo record);
    /**新增
     */
    int insertSelective(CompanyNumberInfo record);
    
    /**更新
     */
    int updateByPrimaryKeySelective(CompanyNumberInfo record);
    /**更新
     */
    int updateByPrimaryKey(CompanyNumberInfo record);

    /**查询实体
     */
    CompanyNumberInfo selectByPrimaryKey(String numberId);

    /**删除
     */
    int deleteByPrimaryKey(String numberId);
    /**删除
     */
    int deleteById(Map<String, BigDecimal> map);
    /**查询条数
     */
	int countPageById(Map<String, BigDecimal> map);

}
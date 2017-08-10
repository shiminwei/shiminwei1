package com.ahcd.dao;

import com.ahcd.pojo.CompanyInfo;
import com.ahcd.pojo.Page;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CompanyInfoMapper {

	/**查询条数
     */
	int countPage(Page<CompanyInfo> page);
	
	/**查询列表(分页)
     */
	List<CompanyInfo> selectPage(Page<CompanyInfo> page);
	
	/**删除
     */
    int deleteByPrimaryKey(BigDecimal id);

    /**新增
     */
    int insert(CompanyInfo record);

    /**新增
     */
    int insertSelective(CompanyInfo record);

    /**查询实体
     */
    CompanyInfo selectByPrimaryKey(BigDecimal id);

    /**更新
     */
    int updateByPrimaryKeySelective(CompanyInfo record);
    /**更新
     */
    int updateByPrimaryKey(CompanyInfo record);
    
    /**判断企业名称唯一性
     */
    int selectCount(Map<String, Object> map);
    
    /**
       * 根据企业ID获取企业的基本信息以及纳税信息
     */
    CompanyInfo  selectInfoTaxById(BigDecimal id);
}
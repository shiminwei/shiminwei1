package com.ahcd.dao;

import com.ahcd.pojo.AreaIndustryTaxInfo;
import com.ahcd.pojo.Page;
import java.math.BigDecimal;
import java.util.List;

public interface AreaIndustryTaxInfoMapper {
    
	/**删除
     */
    int deleteByPrimaryKey(BigDecimal aitiId);

    /**新增
     */
    int insert(AreaIndustryTaxInfo record);
    int insertSelective(AreaIndustryTaxInfo record);

    /**查询实体
     */
    AreaIndustryTaxInfo selectByPrimaryKey(BigDecimal aitiId);

    /**更新
     */
    int updateByPrimaryKey(AreaIndustryTaxInfo record);
    int updateByPrimaryKeySelective(AreaIndustryTaxInfo record);
    
    /**查询列表
	 * */
    List<AreaIndustryTaxInfo> selectAreaIndustryTaxInfoPage(Page<AreaIndustryTaxInfo> page);
    /**查询条数
	 * */
	int selectCountAreaIndustryTaxInfoPage(Page<AreaIndustryTaxInfo> page);

}
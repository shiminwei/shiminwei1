package com.ahcd.dao;

import java.math.BigDecimal;
import java.util.List;
import com.ahcd.pojo.IndustryInfo;
import com.ahcd.pojo.Page;
/**行业信息表
 * */
public interface IndustryInfoMapper {
	/**新增
	 * */
    int insert(IndustryInfo record);
    
    int insertSelective(IndustryInfo record);
    /**查询列表(分页)
	 * */
    List<IndustryInfo> selectIndustryInfoPage(Page<IndustryInfo> page);
    /**查询条数
	 * */
	int selectCountIndustryInfoPage(Page<IndustryInfo> page);
	/**查询实体
	 * */
	IndustryInfo selectByPrimaryKey(BigDecimal industryId);
	/**更新
	 * */
	int updateByPrimaryKeySelective(IndustryInfo record);
	/**删除
	 * */
	int deleteByPrimaryKey(BigDecimal industryId);
	
	/**查询列表(不分页)
	 * */
    List<IndustryInfo> selectIndustryInfoList(IndustryInfo bean);
}
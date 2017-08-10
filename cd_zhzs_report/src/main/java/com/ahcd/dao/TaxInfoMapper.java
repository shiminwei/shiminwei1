package com.ahcd.dao;

import java.math.BigDecimal;
import java.util.List;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.TaxInfo;
/**税种信息表
 * */
public interface TaxInfoMapper {
    /**新增
     */
    int insert(TaxInfo record);

    int insertSelective(TaxInfo record);
    /**查询列表(分页)
	 * */
    List<TaxInfo> selectTaxInfoPage(Page<TaxInfo> page);
    /**查询条数
	 * */
	int selectCountTaxInfoPage(Page<TaxInfo> page);
	/**查询实体
	 * */
	TaxInfo selectByPrimaryKey(BigDecimal taxId);
	/**更新
	 * */
	int updateByPrimaryKeySelective(TaxInfo record);
	/**删除
	 * */
	int deleteByPrimaryKey(BigDecimal taxId);
	 /**查询列表(不分页)
	  * */
	 List<TaxInfo> selectTaxInfoList(TaxInfo bean);
}
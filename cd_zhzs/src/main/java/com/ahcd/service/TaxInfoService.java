package com.ahcd.service;

import java.math.BigDecimal;
import java.util.List;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.TaxInfo;

public interface TaxInfoService {

	/**	查询列表(分页)
	 * */
	Page<TaxInfo> selectTaxInfoPage(Page<TaxInfo> page, TaxInfo bean);
	/**	查询实体
	 * */
	TaxInfo selectByPrimaryKey(BigDecimal taxId);
	/** 更新
	 * */
	int saveOrUpdate(TaxInfo bean);
	/**	删除
	 * */
	int deleteById(String taxId);
	/**	查询列表(不分页)
	 * */
	List<TaxInfo> selectTaxInfoList(TaxInfo bean);
}

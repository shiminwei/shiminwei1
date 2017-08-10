package com.ahcd.service;

import java.math.BigDecimal;
import com.ahcd.pojo.AreaIndustryTaxInfo;
import com.ahcd.pojo.Page;

public interface AreaIndustryTaxInfoService {

	/**	查询列表
	 * */
	Page<AreaIndustryTaxInfo> selectAreaIndustryTaxInfoPage(Page<AreaIndustryTaxInfo> page, AreaIndustryTaxInfo bean);
	/**	查询实体
	 * */
	AreaIndustryTaxInfo selectByPrimaryKey(BigDecimal aitiId);
	/** 更新
	 * */
	int saveOrUpdate(AreaIndustryTaxInfo bean);
	/**	删除
	 * */
	int deleteById(String aitiId);
}

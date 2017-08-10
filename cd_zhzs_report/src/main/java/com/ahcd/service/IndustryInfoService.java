package com.ahcd.service;

import java.math.BigDecimal;
import java.util.List;

import com.ahcd.pojo.IndustryInfo;
import com.ahcd.pojo.Page;

public interface IndustryInfoService {

	/**	查询列表(分页)
	 * */
	Page<IndustryInfo> selectIndustryInfoPage(Page<IndustryInfo> page, IndustryInfo bean);
	/**	查询实体
	 * */
	IndustryInfo selectByPrimaryKey(BigDecimal industryId);
	/** 更新
	 * */
	int saveOrUpdate(IndustryInfo bean);
	/**	删除
	 * */
	int deleteById(String industryId);
	/**	查询列表(不分页)
	 * */
	List<IndustryInfo> selectIndustryInfoList(IndustryInfo bean);
}

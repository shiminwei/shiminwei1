package com.ahcd.service;

import com.ahcd.pojo.CompanyNumberInfo;
import com.ahcd.pojo.Page;

public interface CompanyNumberInfoService {
		/**新增
		 * */
		int insertCompanyNumberInfo(CompanyNumberInfo companyNumberInfo);

		/**查询实体
		 * */
		CompanyNumberInfo getCompanyNumberInfoByNumberId(String numberId);

		/**更新
		 * */
	    int updateCompanyNumberInfo(CompanyNumberInfo companyNumberInfo);
	    
	    /**删除
		 * */
	    int deleteByNumberId(String numberId);
	    
	    /**查询列表
		 * */
	    Page<CompanyNumberInfo> getCompanyNumberInfoPage(Page<CompanyNumberInfo> page);
	    
}

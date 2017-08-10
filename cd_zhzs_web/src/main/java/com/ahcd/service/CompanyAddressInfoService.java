package com.ahcd.service;

import com.ahcd.pojo.CompanyAddressInfo;
import com.ahcd.pojo.Page;

public interface CompanyAddressInfoService {
		/**新增
		 * */
		int insertCompanyAddressInfo(CompanyAddressInfo addressInfo);

		/**查询实体
		 * */
		CompanyAddressInfo getCompanyAddressInfoById(String addressId);

		/**更新
		 * */
	    int updateCompanyAddressInfo(CompanyAddressInfo addressInfo);
	    
	    /**删除
		 * */
	    int deleteByAddressId(String addressId);
	    
	    /**查询列表
		 * */
	    Page<CompanyAddressInfo> getCompanyAddressInfoPage(Page<CompanyAddressInfo> page);
	    
}

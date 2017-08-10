package com.ahcd.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ahcd.pojo.CompanyInfo;
import com.ahcd.pojo.Page;

public interface CompanyInfoService {
		/**新增
		 * */
		int insertCompanyInfo(CompanyInfo companyInfo);

		/**查询实体
		 * */
		CompanyInfo getCompanyInfoById(BigDecimal id);

		/**更新
		 * */
	    int updateCompanyInfo(CompanyInfo companyInfo);
	    
	    /**删除
		 * */
	    int deleteById(String id);
	    
	    /**查询列表
		 * */
	    Page<CompanyInfo> getCompanyInfoPage(Page<CompanyInfo> page);
	    
	    /**置反[是否规模以上企业/是否招商企业]
		 * */
	    int reverse(BigDecimal id,String type);
	    
	    /**将Excel导入的数据保存到数据库中
		 * */
	    int saveCompanyInfoExcel(List<CompanyInfo> list);
	    
	    /**企业重名判断
		 * */
	    int getCount(Map<String,Object> map);
	    /**
	       * 根据企业ID获取企业的基本信息以及纳税信息
	     */
	    CompanyInfo  selectInfoTaxById(BigDecimal id);
}

package com.ahcd.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ahcd.pojo.Page;

/**
 * @author 作者 : fei yang
 * @version 创建时间：2016年9月29日 上午11:04:21 类说明
 */
@Service
public interface CompanyTaxService {
	public Page<Map<String,String>> getLocalPage(Page<Map<String,String>> page);
	public Page<Map<String,String>> getSZLocalPage(Page<Map<String,String>> page);
	public  Page<Map<String,String>>  selectTaxInGSJ(Page<Map<String, String>> pageList);
	public  Page<Map<String,String>>  selectTaxInHG(Page<Map<String, String>> pageList);
	public Page<Map<String,String>> getDetailPage(Page<Map<String,String>> page);
}

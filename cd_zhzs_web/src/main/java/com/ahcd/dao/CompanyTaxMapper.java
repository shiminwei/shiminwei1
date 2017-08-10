package com.ahcd.dao;

import java.util.List;
import java.util.Map;

import com.ahcd.pojo.Page;

public interface CompanyTaxMapper {
	/**查询地税条数
     */
	int countLocalPage(Page<Map<String,String>> page);
	
	/**查询地税列表
     */
	List<Map<String,String>> selectLocalPage(Page<Map<String,String>> page);
	
	/**查询地税总计
     */
	List<Map<String,String>> selectLocalSum(Page<Map<String,String>> page);
	/**查询省直地税条数
     */
	int countSZLocalPage(Page<Map<String,String>> page);
	
	/**查询省直地税列表
     */
	List<Map<String,String>> selectSZLocalPage(Page<Map<String,String>> page);
	
	/**查询省直地税总计
     */
	List<Map<String,String>> selectSZLocalSum(Page<Map<String,String>> page);
	
	/**查询地税条数
     */
	int countPage(Page<Map<String,String>> page);
	
	/**
	 * 国税列表
	 */
	List<Map<String,String>> selectTaxInGSJ(Page<Map<String,String>> page);
	/**查询地税条数
     */
	int countHGPage(Page<Map<String,String>> page);
	
	/**
	 * 国税列表总计
	 */
	List<Map<String,String>> selectTaxInGSJSum(Page<Map<String,String>> page);
	
	/**
	 * 海关列表
	 */
	List<Map<String,String>> selectTaxInHG(Page<Map<String,String>> page);
	
	/**
	 * 海关总计
	 */
	List<Map<String,String>> selectTaxInHGSum(Page<Map<String,String>> page);
	
	/**详情页面条数
     */
	int countDetailPage(Page<Map<String,String>> page);
	
	/**详情页面列表
     */
	List<Map<String,String>> selectDetailPage(Page<Map<String,String>> page);//
	
	/**详情页面总计
     */
	String selectDetailSum(Page<Map<String,String>> page);
	
}
package com.ahcd.dao;

import java.util.List;
import java.util.Map;

import com.ahcd.pojo.Page;


public interface ZyjjzbMapper {
	List<Map<String,Object>> selectAllResult(String k_date,String k_date2);
	
	/**
	 * 分区域主要经济指标完成情况表
	 */
/*	List<Map<String,Object>> selectAllJjzbFqy(String k_date);
*/	List<Map<String,Object>> selectAllJjzbFqy(String k_date,String endRow,String beginRow);
	/**
	 *全省主要经济指标完成情况表
	 */
	List<Map<String,Object>> selectAllJjzbQs(String k_date);	
	/**
	 *财政支出分级分科目执行情况
	 */
	List<Map<String,Object>> selectFjfkm(String k_date,String yskm);   
	/**
	 *全省收入情况表
	 */
	List<Map<String,Object>>selectQssrqk(String k_date,String k_date2,String k_date3);
	
	/**
	 *财政收入分区域分税种
	 */
	List<Map<String,Object>>selectFqyfsz(String k_date,String k_date2);
	
	int countDetailPage(Page<Map<String,String>> page);
	List<Map<String,String>> selectDetailPage(Page<Map<String,String>> page);
	
}
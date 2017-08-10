package com.ahcd.dao;

import java.util.List;
import java.util.Map;


public interface ZyjjzbMapper {
	List<Map<String,Object>> selectAllResult(String k_date,String k_date2);
	
	/**
	 * 分区域主要经济指标完成情况表
	 */
	List<Map<String,Object>> selectAllJjzbFqy(String k_date);
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
	List<Map<String,Object>>selectQssrqk(String k_date,String k_date2);
	
	/**
	 *财政收入分区域分税种
	 */
	List<Map<String,Object>>selectFqyfsz(String k_date,String k_date2);
}
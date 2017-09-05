package com.ahcd.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ahcd.dao.ZyjjzbMapper;
import com.ahcd.pojo.Page;
import com.ahcd.service.ZyjjzbService;
@Service("sysWebZyjjzbServiceImpl")
public class SysWebZyjjzbServiceImpl implements ZyjjzbService{
	@Resource
	private ZyjjzbMapper zyjjzbMapper;

	@Override
	public List<Map<String, Object>> selectAllResult(String k_date,String k_date2) {
		return zyjjzbMapper.selectAllResult(k_date,k_date2);
	}
	
/**
 * 分区域主要经济指标完成情况
 */
	@Override
	public List<Map<String, Object>> selectAllJjzbFqy(String k_date) {
		return zyjjzbMapper.selectAllJjzbFqy(k_date,"50","0");
	}
	/**
	 * 全省主要经济指标完成情况
	 */
@Override
public List<Map<String, Object>> selectAllJjzbQs(String k_date) {
	return zyjjzbMapper.selectAllJjzbQs(k_date);
}


	@Override
	public List<Map<String, Object>> selectQssrqk(String k_date,String k_date2,String k_date3) {
		return zyjjzbMapper.selectQssrqk(k_date,k_date2,k_date3);
	}
	/**
	 * *财政收入分区域分税种
	 */
	@Override
	public List<Map<String, Object>> selectFqyfsz(String k_date, String k_date2) {
		return zyjjzbMapper.selectFqyfsz(k_date, k_date2);
	}
	/**
	 * *财政收入分级分科目
	 */
	@Override
	public List<Map<String, Object>> selectFjfkm(String k_date, String yskm) {
		yskm="%"+yskm+"%";
		return zyjjzbMapper.selectFjfkm(k_date, yskm);
	}

	@Override
	public Page<Map<String, String>> getDetailPage(Page<Map<String, String>> page) {
		int totalCount=zyjjzbMapper.countDetailPage(page);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		List<Map<String,String>>resultList=zyjjzbMapper.selectDetailPage(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		List<String> list = new ArrayList<String>();
		page.setTotalaList(list);
		return page;
	}
}

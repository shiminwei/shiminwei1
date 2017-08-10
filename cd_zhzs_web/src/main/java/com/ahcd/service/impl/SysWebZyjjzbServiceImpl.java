package com.ahcd.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ahcd.dao.ZyjjzbMapper;
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
		return zyjjzbMapper.selectAllJjzbFqy(k_date);
	}
	/**
	 * 全省主要经济指标完成情况
	 */
@Override
public List<Map<String, Object>> selectAllJjzbQs(String k_date) {
	return zyjjzbMapper.selectAllJjzbQs(k_date);
}


	@Override
	public List<Map<String, Object>> selectQssrqk(String k_date,String k_date2) {
		return zyjjzbMapper.selectQssrqk(k_date,k_date2);
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
}

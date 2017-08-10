package com.ahcd.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.dao.CzsrMapper;
import com.ahcd.service.CzsrService;
@Service("sysWebCzsrServiceImpl")
public class SysWebCzsrServiceImpl implements CzsrService{
	@Resource
	private CzsrMapper czsrMapper;

	

	@Override
	public List<Map<String, Object>> selectAllResult(String k_date) {
		return czsrMapper.selectAllResult(k_date);
	}
}

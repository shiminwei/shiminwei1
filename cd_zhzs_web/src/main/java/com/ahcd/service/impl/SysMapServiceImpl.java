package com.ahcd.service.impl;

import java.util.List;
import java.util.Map;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.dao.SysMapMapper;
import com.ahcd.service.SysMapService;

@Service("sysMapService")
public class SysMapServiceImpl implements SysMapService {
	@Resource
	private SysMapMapper sysMapMapper;

	@Override
	public List<Map<String,Object>> getAllQyAddr() {
		
		List<Map<String,Object>> list = sysMapMapper.getAllQyAddr(); 
		return list;
	}

	
}

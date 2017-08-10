package com.ahcd.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ahcd.dao.SysAreaInfoMapper;
import com.ahcd.pojo.SysAreaInfo;
import com.ahcd.service.ISysAreaInfoService;

@Service("sysAreaInfoService")
public class SysAreaInfoServiceImpl implements ISysAreaInfoService{
	@Resource
	private SysAreaInfoMapper sysAreaInfoMapper;

	/** 
	 * @author 作者 : fei yang
	 * @version 创建时间：2016年10月23日 下午12:13:55 
	 * 类说明 
	 */ 
	@Override
	public List<SysAreaInfo> getArea() {
		// TODO Auto-generated method stub
		return sysAreaInfoMapper.selectAreaName();
		
	}

	@Override
	public SysAreaInfo selectAreaCodeByAreaId(String departmentAreaId) {
		return sysAreaInfoMapper.selectAreaCodeByAreaId(departmentAreaId);
	}
}


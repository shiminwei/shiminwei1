package com.ahcd.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.dao.SysDepartmentAuthMapper;
import com.ahcd.pojo.SysDepartmentAuth;
import com.ahcd.service.ISysDepartmentAuthService;

@Service("sysDepartmentAuthService")
public class SysDepartmentAuthImpl implements ISysDepartmentAuthService{

	@Resource
	private SysDepartmentAuthMapper sysDepartmentAuthMapper;
	
	//TODO 插入字部门
	@Override
	public int insertSubDepartments(SysDepartmentAuth sysDepartmentAuth) {
		return sysDepartmentAuthMapper.insertSubDepartments(sysDepartmentAuth);
	}

	//TODO 查询子部门
	@Override
	public List<SysDepartmentAuth> getNoticeDepartmentAuth(String departmentId) {
		return sysDepartmentAuthMapper.getNoticeDepartmentAuth(departmentId);
	}

	@Override
	public int deleteAllByDepartmentId(String departmentId) {
		return sysDepartmentAuthMapper.deleteAllByDepartmentId(departmentId);
	}

	@Override
	public int deleteByDepartmentId(HashMap parameterMap) {
		return sysDepartmentAuthMapper.deleteByDepartmentId(parameterMap);
	}
}

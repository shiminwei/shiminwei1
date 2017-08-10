package com.ahcd.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.dao.SysDepartmentInfoMapper;
import com.ahcd.pojo.SysDepartmentInfo;
import com.ahcd.service.ISysDepartmentInfoService;

@Service("departmentService")
public class SysDepartmentInfoServiceImpl implements ISysDepartmentInfoService {

	@Resource
	private SysDepartmentInfoMapper sysDepartmentInfoMapper;
	
	@Override
	public List<SysDepartmentInfo> getDepartment() {
		return sysDepartmentInfoMapper.selectSysDepartmentInfo();
	}

	@Override
	public List<SysDepartmentInfo> getSysDepartmentInfoByAreaId(String departmentAreaId) {
		return sysDepartmentInfoMapper.selectSysDepartmentInfoByAreaId(departmentAreaId);
	}

	@Override
	public void insertDepartmentInfo(SysDepartmentInfo sysDepartmentinfo) {
		sysDepartmentInfoMapper.insertDepartmentInfo(sysDepartmentinfo);
	}

	@Override
	public void updateDapartmentInfoByID(SysDepartmentInfo sysDepartmentInfo) {
		sysDepartmentInfoMapper.updateDapartmentInfoByID(sysDepartmentInfo);
	}

	@Override
	public SysDepartmentInfo selectDapartmentInfoByID(String departmentId) {
		return sysDepartmentInfoMapper.selectDapartmentInfoByID(departmentId);
	}

	@Override
	public List<SysDepartmentInfo> selectSysDepartmentInfo() {
		return sysDepartmentInfoMapper.selectSysDepartmentInfo();
	}

	@Override
	public List<SysDepartmentInfo> getDepartmentNames() {
		return sysDepartmentInfoMapper.selectDapartmentInfo2();
	}

	@Override
	public SysDepartmentInfo selectDapartmentInfoBydepartmentCode(String departmentCode) {
		return sysDepartmentInfoMapper.selectDapartmentInfoBydepartmentCode(departmentCode);
	}

	//TODO 假删
	@Override
	public int isdelete(String departmentId) {
		return sysDepartmentInfoMapper.isdelete(departmentId);
	}

	
}

package com.ahcd.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.dao.SysAreaInfoMapper;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysAreaInfo;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.ISysAreaInfoService;

@Service("areaService")
public class SysAreaInfoServiceImpl implements ISysAreaInfoService{
	@Resource
	private SysAreaInfoMapper sysAreaInfoMapper;

	@Override
	public List<SysAreaInfo> getArea() {
		return sysAreaInfoMapper.selectAreaName();
	}

	@Override
	public SysAreaInfo selectAreaCodeByAreaId(String departmentAreaId) {
		return sysAreaInfoMapper.selectAreaCodeByAreaId(departmentAreaId);
	}

	@Override
	public SysAreaInfo selectAreaNameByAreaCode(int areaCode) {
		return sysAreaInfoMapper.selectAreaNameByAreaCode(areaCode);
	}
	
	//删除所选择得区域   仅改变地区状态  假删
	@Override
	public int isdelete(String areaId) {
		return sysAreaInfoMapper.isdelete(areaId);
	}

	@Override
	public int insertInfo(SysAreaInfo record) {
		
		return sysAreaInfoMapper.insert(record);
	}

	@Override
	public int updateInfo(SysAreaInfo record) {
		// TODO Auto-generated method stub
		return sysAreaInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Page<SysAreaInfo> selectAreasByPage(Page<SysAreaInfo> page) {
		int totalCount=sysAreaInfoMapper.countAreaPage(page);
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		page.setBeginRow(beginRow);
		page.setEndRow(endRow);
		List<SysAreaInfo> resultList=sysAreaInfoMapper.selectAreasByPage(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}
}


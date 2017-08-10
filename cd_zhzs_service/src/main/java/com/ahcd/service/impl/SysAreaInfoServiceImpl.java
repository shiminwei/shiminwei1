package com.ahcd.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.dao.SysAreaInfoMapper;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysAreaInfo;
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<SysAreaInfo> selectAreasByPage(Page<SysAreaInfo> page) {
		int totalCount=sysAreaInfoMapper.countAreaPage(page);
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		page.setBeginRow(beginRow);
		page.setEndRow(endRow);
		List<SysAreaInfo> resultList=sysAreaInfoMapper.selectAreasByPage(page);
		Collections.sort(resultList, new Comparator(){
			  @Override
			  public int compare(Object o1, Object o2) {
				SysAreaInfo s1 = (SysAreaInfo)o1;
				SysAreaInfo s2 = (SysAreaInfo)o2;
				if(s1.getAreaCode().intValue()>s2.getAreaCode().intValue()){
					return 1;
				}else if(s1.getAreaCode().intValue()==s2.getAreaCode().intValue()){
					return 0;
				}else{
					return -1;
				}
			}
		});
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}
	
}


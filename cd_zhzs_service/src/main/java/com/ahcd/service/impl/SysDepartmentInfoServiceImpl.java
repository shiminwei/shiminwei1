package com.ahcd.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.dao.SysDepartmentInfoMapper;
import com.ahcd.pojo.Page;
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


	@Override
	public List<SysDepartmentInfo> selectSysDepartmentInfoBySub(String departmentId) {	
		return sysDepartmentInfoMapper.selectSysDepartmentInfoBySub(departmentId);
	}


	@Override
	public Page<SysDepartmentInfo> selectPage(Page<SysDepartmentInfo> page) {
		int totalCount = sysDepartmentInfoMapper.countPage(page);
		int beginRow = (page.getPageNum()-1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		page.setBeginRow(beginRow);
		page.setEndRow(endRow);
		List<SysDepartmentInfo> resultList = sysDepartmentInfoMapper.selectPage(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}

	//TODO查找某个地区id查具体部门
	@Override
	@SuppressWarnings({"unchecked","rawtypes"})
	public Page<SysDepartmentInfo> selectSysDepartmentInfoByID(Page<SysDepartmentInfo> page) {
		int totalCount = sysDepartmentInfoMapper.countPageAreaDepartment(page);
		int beginRow = (page.getPageNum()-1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		page.setBeginRow(beginRow);
		page.setEndRow(endRow);
		List<SysDepartmentInfo> resultList = sysDepartmentInfoMapper.selectSysDepartmentInfoByID(page);
		Collections.sort(resultList, new Comparator(){
			  @Override
			  public int compare(Object o1, Object o2) {
				SysDepartmentInfo s1 = (SysDepartmentInfo)o1;
				SysDepartmentInfo s2 = (SysDepartmentInfo)o2;
				if(s1.getDepartmentCode().intValue()>s2.getDepartmentCode().intValue()){
					return 1;
				}else if(s1.getDepartmentCode().intValue()==s2.getDepartmentCode().intValue()){
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

	@Override
	public List<SysDepartmentInfo> selectSysDepartmentInfoByName(String name) {
		return sysDepartmentInfoMapper.selectSysDepartmentInfoByName(name);
	}

	@Override
	public List<SysDepartmentInfo> selectSysDepartmentInfoAreaID2(String id) {
		return sysDepartmentInfoMapper.selectSysDepartmentInfoAreaID2(id);
	}
}
















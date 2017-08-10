package com.ahcd.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.dao.SysDepartmentLeadMapper;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentLead;
import com.ahcd.service.ISysDepartmentLeadService;
@Service("sysDepartmentLeadService")
public class SysDepartmentLeadServiceImpl implements ISysDepartmentLeadService {
	@Resource
	private SysDepartmentLeadMapper sysDepartmentLeadMapper;
	
	//TODO 增加
	@Override
	public int insert(SysDepartmentLead sysDepartmentLead) {
		return sysDepartmentLeadMapper.insert(sysDepartmentLead);
	}

	@Override
	public Page<SysDepartmentLead> selectSysDepartmentLeadPage(Page<SysDepartmentLead> page) {
		int totalCount = sysDepartmentLeadMapper.countSysDeparmentLeadPage(page);
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		page.setBeginRow(beginRow);
		page.setEndRow(endRow);
		List<SysDepartmentLead>resultList=sysDepartmentLeadMapper.selectSysDeparmentLeadPage(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}

	@Override
	public SysDepartmentLead selectByPrimaryKey(BigDecimal id) {
		return sysDepartmentLeadMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateIsDelete(BigDecimal id) {
		return sysDepartmentLeadMapper.updateIsDelete(id);
	}

	@Override
	public SysDepartmentLead checkSysdepartmentLeadById(BigDecimal id) {
		return sysDepartmentLeadMapper.selectSysdepartmentLeadById(id);
	}

	@Override
	public int updateByPrimaryKey(SysDepartmentLead sysDepartmentLead) {
		return sysDepartmentLeadMapper.updateByPrimaryKey(sysDepartmentLead);
	}

	@Override
	public List<SysDepartmentLead> selectSysdepartmentLeadInfo() {
		return sysDepartmentLeadMapper.selectSysdepartmentLeadInfo();
	}

	/**查询消息发送页面需要的前置机ID
     * */
	@Override
	public Page<SysDepartmentLead> selectSysDepartmentLeadList(
			Page<SysDepartmentLead> page) {
		int totalCount = sysDepartmentLeadMapper.countSysDeparmentLeadList(page);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		List<SysDepartmentLead>resultList=sysDepartmentLeadMapper.selectSysDeparmentLeadList(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}
}

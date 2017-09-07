package com.ahcd.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.ahcd.common.EmptyUtils;
import com.ahcd.common.PropertiesUntil;
import com.ahcd.dao.SysDepartmentLeadMapper;
import com.ahcd.pojo.SysDepartmentLead;
import com.ahcd.service.SysDepartmentLeadService;

@Service
public class SysDepartmentLeadServiceImpl implements SysDepartmentLeadService {
	@Resource
	private SysDepartmentLeadMapper sysDepartmentLeadMapper;

	@Override
	public int deleteByPrimaryKey(BigDecimal id) {
		return sysDepartmentLeadMapper.deleteByPrimaryKey(id);
	}

	@Override
	public SysDepartmentLead selectByPrimaryKey(BigDecimal id) {
		return sysDepartmentLeadMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysDepartmentLead record) {

		return sysDepartmentLeadMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKey(SysDepartmentLead record) {
		return sysDepartmentLeadMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<SysDepartmentLead> selectByLeadId(BigDecimal leadId) {
		return sysDepartmentLeadMapper.selectByLeadId(leadId);
	}

	/**
	 * 
	   * 功能说明    :根据前置机ID获取配置信息 
	   * @author   : fei yang 
	   * @version ：2017年2月8日 下午1:30:49 
	   * @param leadId
	   * @return
	 */
	public SysDepartmentLead getSysDepartmentLeadByLeadId(String leadId) {
		SysDepartmentLead departmentLead = new SysDepartmentLead();
		if (EmptyUtils.isBigDecimal(leadId)) {
			List<SysDepartmentLead> departmentLeads = sysDepartmentLeadMapper.selectByLeadId(new BigDecimal(leadId));
			if (departmentLeads.size() == 1) {
				departmentLead = departmentLeads.get(0);
			}
		}
		return departmentLead;
	}
}

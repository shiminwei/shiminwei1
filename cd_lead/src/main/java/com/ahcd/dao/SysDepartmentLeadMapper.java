package com.ahcd.dao;

import com.ahcd.pojo.SysDepartmentLead;
import java.math.BigDecimal;
import java.util.List;

public interface SysDepartmentLeadMapper {
	
	int deleteByPrimaryKey(BigDecimal id);

	SysDepartmentLead selectByPrimaryKey(BigDecimal id);

	List<SysDepartmentLead>selectByLeadId(BigDecimal leadId);
	
	int updateByPrimaryKey(SysDepartmentLead record);
}
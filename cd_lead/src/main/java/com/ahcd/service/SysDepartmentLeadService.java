package com.ahcd.service;

import java.math.BigDecimal;
import java.util.List;

import com.ahcd.pojo.SysDepartmentLead;

public interface SysDepartmentLeadService {

	/** 
	* @author   : fei yang 
	* @version ：2017年2月6日 下午3:35:03 
	*/
    int deleteByPrimaryKey(BigDecimal id);

    SysDepartmentLead selectByPrimaryKey(BigDecimal id);

	List<SysDepartmentLead>selectByLeadId(BigDecimal leadId);
	
    int updateByPrimaryKeySelective(SysDepartmentLead record);

    int updateByPrimaryKey(SysDepartmentLead record);
}

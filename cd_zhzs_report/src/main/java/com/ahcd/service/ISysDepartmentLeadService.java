package com.ahcd.service;

import java.math.BigDecimal;
import java.util.List;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentLead;

public interface ISysDepartmentLeadService {
	//TODO增加 
	int insert(SysDepartmentLead sysDepartmentLead);
	
	public Page<SysDepartmentLead> selectSysDepartmentLeadPage(Page<SysDepartmentLead> page);

	//TODO根据id查找
    SysDepartmentLead selectByPrimaryKey(BigDecimal id);
    
    //TODO假删
    int updateIsDelete(BigDecimal id);
    
    //TODO根据id查找
    SysDepartmentLead checkSysdepartmentLeadById(BigDecimal id);
    
    //更新
    int updateByPrimaryKey(SysDepartmentLead sysDepartmentLead);
    
    //查询所有用户
    List<SysDepartmentLead> selectSysdepartmentLeadInfo();
    /**查询消息发送页面需要的前置机ID
     * */
    public Page<SysDepartmentLead> selectSysDepartmentLeadList(Page<SysDepartmentLead> page);
}

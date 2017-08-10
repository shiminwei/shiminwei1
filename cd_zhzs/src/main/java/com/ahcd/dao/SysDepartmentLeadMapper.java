package com.ahcd.dao;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentLead;
import java.math.BigDecimal;
import java.util.List;

public interface SysDepartmentLeadMapper {
    int deleteByPrimaryKey(BigDecimal id);

    //TODO增加
    int insert(SysDepartmentLead sysDepartmentLead);

    int insertSelective(SysDepartmentLead record);

    //TODO根据id查找
    SysDepartmentLead selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(SysDepartmentLead record);

    //更新
    int updateByPrimaryKey(SysDepartmentLead sysDepartmentLead);
    
    //TODO 查询总条数
    int countSysDeparmentLeadPage(Page<SysDepartmentLead> page);
    
    //TODO
    List<SysDepartmentLead> selectSysDeparmentLeadPage(Page<SysDepartmentLead> page);
    
    //TODO假删
    int updateIsDelete(BigDecimal id);
    
    //TODO根据id查找
    SysDepartmentLead selectSysdepartmentLeadById(BigDecimal id);
    
    //查询所有用户
    List<SysDepartmentLead> selectSysdepartmentLeadInfo();
    
    /**查询消息发送页面需要的前置机ID
     * */
    int countSysDeparmentLeadList(Page<SysDepartmentLead> page);
    
    /**查询消息发送页面需要的前置机ID
     * */
    List<SysDepartmentLead> selectSysDeparmentLeadList(Page<SysDepartmentLead> page);
}
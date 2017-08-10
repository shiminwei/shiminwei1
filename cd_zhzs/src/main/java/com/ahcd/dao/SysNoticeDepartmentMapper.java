package com.ahcd.dao;

import java.util.List;

import com.ahcd.pojo.SysNoticeDepartment;

public interface SysNoticeDepartmentMapper {
    int deleteByPrimaryKey(String id);

    int insertSysNoticeDepartment(SysNoticeDepartment sysNoticeDepartment);

    int insertSelective(SysNoticeDepartment record);

    int updateByPrimaryKeySelective(SysNoticeDepartment record);

    //TODO 更新
    int updateSysNoticeDepartment(SysNoticeDepartment sysNoticeDepartment);
    
    //TODO 根据noticeId查询
    List<SysNoticeDepartment> selectByNoticeId(String noticeId);
    
    //TODO 根据noticeId删除
    int deleteByNoticeId(String noticeId);
}
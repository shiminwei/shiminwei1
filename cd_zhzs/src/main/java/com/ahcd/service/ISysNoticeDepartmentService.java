package com.ahcd.service;

import java.util.List;

import com.ahcd.pojo.SysNoticeDepartment;

public interface ISysNoticeDepartmentService {
	int insertSysNoticeDepartment(SysNoticeDepartment sysNoticeDepartment);
	
	//TODO 根据noticeId查询
    List<SysNoticeDepartment> selectByNoticeId(String noticeId);
    
    //TODO 更新
    int updateSysNoticeDepartment(SysNoticeDepartment sysNoticeDepartment);
    
    //TODO 根据noticeId删除
    int deleteByNoticeId(String noticeId);
}

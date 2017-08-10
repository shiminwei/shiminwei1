package com.ahcd.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.dao.SysNoticeDepartmentMapper;
import com.ahcd.pojo.SysNoticeDepartment;
import com.ahcd.service.ISysNoticeDepartmentService;

@Service("sysNoticeDepartmentService")
public class SysNoticeDepartmentServiceImpl implements ISysNoticeDepartmentService{
	@Resource
	private SysNoticeDepartmentMapper sysNoticeDepartmentMapper;
	
	@Override
	public int insertSysNoticeDepartment(SysNoticeDepartment sysNoticeDepartment) {
		return sysNoticeDepartmentMapper.insertSysNoticeDepartment(sysNoticeDepartment);
	}

	//TODO 根据noticeId查询
	@Override
	public List<SysNoticeDepartment> selectByNoticeId(String noticeId) {
		return sysNoticeDepartmentMapper.selectByNoticeId(noticeId);
	}

	@Override
	public int updateSysNoticeDepartment(SysNoticeDepartment sysNoticeDepartment) {
		return sysNoticeDepartmentMapper.updateSysNoticeDepartment(sysNoticeDepartment);
	}

	//TODO 根据noticeId删除
	@Override
	public int deleteByNoticeId(String noticeId) {
		return sysNoticeDepartmentMapper.deleteByNoticeId(noticeId);
	}
}

package com.ahcd.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.dao.SysNoticeFileMapper;
import com.ahcd.pojo.SysNoticeFile;
import com.ahcd.service.ISysNoticeFileService;

@Service("sysNoticeFileService")
public class SysNoticeFileService implements ISysNoticeFileService{
	@Resource
	private SysNoticeFileMapper sysNoticeFileMapper;

	//TODO 插入noticeFile
	@Override
	public int insertNoticeFile(SysNoticeFile sysNoticeFile) {
		return sysNoticeFileMapper.insertNoticeFile(sysNoticeFile);
	}

	
	//TODO 查询所有
	@Override
	public List<SysNoticeFile> selectSysNoticeFile() {
		return sysNoticeFileMapper.selectSysNoticeFile();
	}

	//TODo 根据noticeId查询
	@Override
	public SysNoticeFile selectByNoticeId(String noticeId) {
		return sysNoticeFileMapper.selectByNoticeId(noticeId);
	}

	//TODO 修改
	@Override
	public int updateSysNoticeFile(SysNoticeFile sysNoticeFile) {
		return sysNoticeFileMapper.updateSysNoticeFile(sysNoticeFile);
	}


	@Override
	public int deleteByNoticeId(String noticeId) {
		return sysNoticeFileMapper.deleteByNoticeId(noticeId);
	}
}

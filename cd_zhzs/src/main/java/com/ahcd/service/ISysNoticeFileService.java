package com.ahcd.service;

import java.util.List;

import com.ahcd.pojo.SysNoticeFile;

public interface ISysNoticeFileService {
	//TODO 插入信息
	int insertNoticeFile(SysNoticeFile sysNoticeFile);
	
	 //TODO 查询所有
    List<SysNoticeFile> selectSysNoticeFile();

    //TODO 根据noticeId查询
    SysNoticeFile selectByNoticeId(String noticeId);
    
    //TODO 修改
    int updateSysNoticeFile(SysNoticeFile sysNoticeFile);
    
    //TODO 删除
    int deleteByNoticeId(String noticeId);

}

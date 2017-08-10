package com.ahcd.dao;

import java.util.List;

import com.ahcd.pojo.SysNoticeFile;

public interface SysNoticeFileMapper {
	//TODO 删除
    int deleteByNoticeId(String noticeId);

    int insertNoticeFile(SysNoticeFile sysNoticeFile);

    int insertSelective(SysNoticeFile record);

    //TODO 查询所有
    List<SysNoticeFile> selectSysNoticeFile();

    //TODo 根据noticeId查询
    SysNoticeFile selectByNoticeId(String noticeId);
    
    //TODO 修改
    int updateSysNoticeFile(SysNoticeFile sysNoticeFile);

    int updateByPrimaryKey(SysNoticeFile record);
}
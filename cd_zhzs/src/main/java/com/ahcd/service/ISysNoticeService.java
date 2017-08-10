	package com.ahcd.service;

import java.math.BigDecimal;
import java.util.List;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysNotice;
import com.ahcd.pojo.SysNoticeDepartment;
import com.ahcd.pojo.SysNoticeFile;

public interface ISysNoticeService {
	//TODO 插入信息
	int insertSysNotice(SysNotice sysNotice);
	
	Page<SysNotice> getNoticePageByUser(Page<SysNotice> page);

	//TODO 根据noticeId查询信息
	SysNotice getNoticeById(String noticeId);

	//TODO 根据noticeId查询文件名
	SysNoticeFile getNoticeFile(String noticeId);
	
	//TODO 修改
    int updateSysNotice(SysNotice sysNotice);
    
    //TODO 删除
    int deleteByNoticeId(String noticeId);

	void readNotice(String noticeId, BigDecimal userId);

	Page<SysNotice> getNoticePage(Page<SysNotice> page);
	
	
	List<SysNoticeDepartment> getNoticeDepartment(String noticeId);
}

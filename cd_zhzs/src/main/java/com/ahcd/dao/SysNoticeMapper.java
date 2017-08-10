package com.ahcd.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysNotice;
import com.ahcd.pojo.SysNoticeDepartment;
import com.ahcd.pojo.SysNoticeFile;

public interface SysNoticeMapper {
	//TODO 删除
    int deleteByNoticeId(String noticeId);

    //TODO 插入
    int insertSysNotice(SysNotice sysNotice);

    //TODO 修改
    int updateSysNotice(SysNotice sysNotice);

    int updateByPrimaryKeyWithBLOBs(SysNotice record);

    int updateByPrimaryKey(SysNotice record);
    
    int countByUser(Page<SysNotice> page);

	List<SysNotice> selectByUser(Page<SysNotice> page);

	//TODO 根据noticeId查询信息
	SysNotice getNoticeById(String noticeId);
	
	//TODO 根据noticeId查询文件名
	SysNoticeFile getNoticeFile(String noticeId);

	//TODO 根据noticeId查询文件名
	//SysNoticeDepartment getNoticeDepartment(String noticeId);
	
	List<SysNoticeDepartment> getNoticeDepartment(String noticeId);

	int countByNoticeIdUserId(@Param("noticeId")String noticeId, @Param("userId")BigDecimal userId);

	void insertReaded(@Param("noticeId")String noticeId, @Param("userId")BigDecimal userId);

	int countPage(Page<SysNotice> page);

	List<SysNotice> selectPage(Page<SysNotice> page);
	
	SysNotice getNoticeInfoById(String noticeId);
}
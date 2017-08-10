package com.ahcd.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.dao.SysNoticeMapper;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysNotice;
import com.ahcd.pojo.SysNoticeDepartment;
import com.ahcd.pojo.SysNoticeFile;
import com.ahcd.service.ISysNoticeService;

@Service("sysNoticeService")
public class SysNoticeServiceImpl implements ISysNoticeService{
	@Resource
	private SysNoticeMapper sysNoticeMapper;
	
	//TODO 插入信息
	@Override
	public int insertSysNotice(SysNotice sysNotice) {
		return sysNoticeMapper.insertSysNotice(sysNotice);
	}

	@Override
	public Page<SysNotice> getNoticePageByUser(Page<SysNotice> page) {
		int totalCount=sysNoticeMapper.countByUser(page);
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		page.setBeginRow(beginRow);
		page.setEndRow(endRow);
		List<SysNotice>resultList=sysNoticeMapper.selectByUser(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}


	//TODO 根据noticeId查询信息
	@Override
	public SysNotice getNoticeById(String noticeId) {
		//return sysNoticeMapper.getNoticeInfoById(noticeId);
		return sysNoticeMapper.getNoticeById(noticeId);
	}


	@Override
	public void readNotice(String noticeId, BigDecimal userId) {
		if(sysNoticeMapper.countByNoticeIdUserId(noticeId,userId)==0){
			sysNoticeMapper.insertReaded(noticeId,userId);
		}
	}

	@Override
	public SysNoticeFile getNoticeFile(String noticeId) {
		return sysNoticeMapper.getNoticeFile(noticeId);
	}

	@Override
	public int updateSysNotice(SysNotice sysNotice) {
		return sysNoticeMapper.updateSysNotice(sysNotice);
	}
	
	//TODO 删除
	@Override
	public int deleteByNoticeId(String noticeId) {
		return sysNoticeMapper.deleteByNoticeId(noticeId);
	}

	@Override
	public Page<SysNotice> getNoticePage(Page<SysNotice> page) {
		int totalCount=sysNoticeMapper.countPage(page);
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		page.setBeginRow(beginRow);
		page.setEndRow(endRow);
		List<SysNotice>resultList=sysNoticeMapper.selectPage(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}

	@Override
	public List<SysNoticeDepartment> getNoticeDepartment(String noticeId) {
		
		return sysNoticeMapper.getNoticeDepartment(noticeId);
	}
}

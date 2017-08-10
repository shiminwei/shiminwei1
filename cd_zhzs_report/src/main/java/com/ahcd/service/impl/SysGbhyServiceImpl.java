package com.ahcd.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ahcd.dao.SysGbhyMapper;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysGbhy;
import com.ahcd.service.ISysGbhyService;

@Service("sysGbhyService")
public class SysGbhyServiceImpl implements ISysGbhyService{
	@Resource
	private SysGbhyMapper sysGbhyMapper;

	@Override
	public Page<SysGbhy> getNoticePage(Page<SysGbhy> page) {
		int totalCount = sysGbhyMapper.countPage(page);
		int beginRow = (page.getPageNum()-1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		page.setBeginRow(beginRow);
		page.setEndRow(endRow);
		List<SysGbhy> resultList = sysGbhyMapper.selectPage(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}
}

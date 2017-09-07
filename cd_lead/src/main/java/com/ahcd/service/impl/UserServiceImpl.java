package com.ahcd.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ahcd.dao.SysReportUserMapper;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private SysReportUserMapper sysReportUserMapper;

	@Override
	public SysReportUser selectUserInfoByUserCode(String userCode) {

		return sysReportUserMapper.selectUserInfoByUserCode(userCode);
	}

	@Override
	public SysReportUser selectUserInfoByUserId(BigDecimal userId) {
		return sysReportUserMapper.selectUserInfoByUserId(userId);
	}

	@Override
	public Page<SysReportUser> selectUserPage(Page<SysReportUser> page) {
		int totalCount = sysReportUserMapper.countUserPage(page);
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		page.setBeginRow(beginRow);
		page.setEndRow(endRow);
		List<SysReportUser> resultList = sysReportUserMapper.selectUserPage(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}

}

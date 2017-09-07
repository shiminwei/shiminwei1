package com.ahcd.dao;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysReportUser;

import java.math.BigDecimal;
import java.util.List;

public interface SysReportUserMapper {
	SysReportUser selectUserInfoByUserCode(String userCode);

	SysReportUser selectUserInfoByUserId(BigDecimal userId);

	List<SysReportUser> selectUserPage(Page<SysReportUser> page);

	int countUserPage(Page<SysReportUser> page);

}
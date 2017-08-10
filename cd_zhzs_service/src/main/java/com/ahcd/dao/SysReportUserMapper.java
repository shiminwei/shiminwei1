package com.ahcd.dao;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysReportUser;

import java.math.BigDecimal;
import java.util.List;

public interface SysReportUserMapper {

    //插入用户
    int insert(SysReportUser sysReportUser);

    //TODO根据id找用户
    SysReportUser selectByUserId(BigDecimal userId);


	SysReportUser selectByLoginCode(String loginCode);
	
	SysReportUser selectByLoginCodeForWeb(String loginCode);
	
	int selectUserNum();
	 
	List<SysReportUser> selectUserReportInfo();
	
	List<SysReportUser> selectUserAreaDepartment();
	
	SysReportUser selectUserAreaDepartmentByUserId(BigDecimal bigDecimal);
	
	//TODO 假删用户
	int isdelete(BigDecimal ids);
	
	SysReportUser selectUserInfoByUserCode(String userCode);
	
	//TODO 更新用户信息
	int updateInfo(SysReportUser sysReportUser);
	
	//TODO 根据userName查询userId
	SysReportUser selectUseridByUserName(String userName);

	//TODO 分页
	int countUserPage(Page<SysReportUser> page);

	List<SysReportUser> selectUserPage(Page<SysReportUser> page);

	//TODO 重置密码
	int updatePwdByUserCode(SysReportUser record);
	
	//TODO 根据用户名修改密码
	int updatePwdByUserName(SysReportUser sysReportUser);
	
	//TODO查询用户信息
	List<SysReportUser> selectUserInfo();
	
}
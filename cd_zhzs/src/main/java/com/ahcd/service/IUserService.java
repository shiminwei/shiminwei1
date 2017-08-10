package com.ahcd.service;

import java.math.BigDecimal;
import java.util.List;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysReportUser;

public interface  IUserService {
	public SysReportUser getUserById(BigDecimal userId);

	public SysReportUser getUserByLoginCode(String loginCode);
	
	public int insertUser(SysReportUser sysReportUser);
	
	public int insertSelective(SysReportUser sysReportUser);
	
	int selectUserNum();
	
	public List<SysReportUser> selectUserReportInfo();
	
	List<SysReportUser> selectUserAreaDepartment();
	
	SysReportUser selectUserAreaDepartmentByUserId(BigDecimal bigDecimal);
	
	//TODO 删除用户
	int isdelete(BigDecimal userId);
	
	SysReportUser selectByLoginCode(String userCode);
	
	SysReportUser selectUserInfoByUserCode(String userCode);
	
	//TODO 更新用户信息
	int updateInfo(SysReportUser sysReportUser);

	SysReportUser selectUseridByUserName(String userName);

	public Page<SysReportUser> selectUserPage(Page<SysReportUser> page);

	//TODO 重置密码
	public int resetPwd(String userCode);
	
	//TODO 根据用户名修改密码
	int updatePwdByUserName(String userName,String newPwd);
	
	//TODO查询用户信息
	List<SysReportUser> getUserInfo();
}

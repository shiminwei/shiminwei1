package com.ahcd.service;

import java.math.BigDecimal;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysReportUser;


public interface  IUserService {
	public SysReportUser getUserByLoginCode(String loginCode);
	public Page<SysReportUser> getAllByBean(Page<SysReportUser> page,SysReportUser bean);
	
	//TODO 查询用户数量
	int selectUserNum();
	
	//TODO 插入用户
    int insertUser(SysReportUser sysReportUser);
    
    //TODO 分页
    public Page<SysReportUser> selectUserPage(Page<SysReportUser> page);
    
    SysReportUser selectUserInfoByUserCode(String userCode);
    
    SysReportUser getUserById(BigDecimal userId);
    
    //TODO 更新用户信息
    int updateInfo(SysReportUser sysReportUser);
    
    //TODO 假删用户
  	int isdelete(BigDecimal ids);
  	
}

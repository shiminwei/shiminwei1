package com.ahcd.service;

import java.math.BigDecimal;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysReportUser;


public interface  IUserService {

  	SysReportUser selectUserInfoByUserCode(String userCode);
 	
  	SysReportUser selectUserInfoByUserId(BigDecimal userId);

    public Page<SysReportUser> selectUserPage(Page<SysReportUser> page);
    
}

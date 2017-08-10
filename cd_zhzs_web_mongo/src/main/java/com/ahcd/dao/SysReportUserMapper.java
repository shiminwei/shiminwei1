package com.ahcd.dao;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysReportUser;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface SysReportUserMapper {
    int deleteByPrimaryKey(BigDecimal userId);

    int insertSelective(SysReportUser record);

    SysReportUser selectByPrimaryKey(BigDecimal userId);

    int updateByPrimaryKeySelective(SysReportUser record);

    int updateByPrimaryKey(SysReportUser record);
    
    SysReportUser selectByLoginCode(String userCode);
    
	List<SysReportUser> getAllByBean(HashMap<String, Object> map);

	Integer getCount(SysReportUser bean);
	
	//TODO 查询用户数量
	int selectUserNum();
	
	//TODO 插入用户
    int insert(SysReportUser sysReportUser);
    
    //TODO 分页
  	int countUserPage(Page<SysReportUser> page);
  	
  	List<SysReportUser> selectUserPage(Page<SysReportUser> page);
  	
  	SysReportUser selectUserInfoByUserCode(String userCode);
  	
  	SysReportUser selectByUserId(BigDecimal userId);
  	
  	//TODO 更新用户信息
  	int updateInfo(SysReportUser sysReportUser);
  	
  	//TODO 假删用户
  	int isdelete(BigDecimal ids);
}
package com.ahcd.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.common.Constant;
import com.ahcd.common.PasswordUtil;
import com.ahcd.dao.SysReportUserMapper;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.IUserService;

@Service("userService")  
public class UserServiceImpl implements IUserService {
	@Resource  
    private SysReportUserMapper sysReportUserMapper;
	
	//TODO根据id找用户
	public SysReportUser getUserById(BigDecimal userId) {
		return sysReportUserMapper.selectByUserId(userId);
	}
	
	@Override
	public SysReportUser getUserByLoginCode(String loginCode) {
		return sysReportUserMapper.selectByLoginCode(loginCode);
	}

	@Override
	public int insertUser(SysReportUser sysReportUser) {
		sysReportUser.setCreateTime(new Date());
		if(sysReportUserMapper.insert(sysReportUser)>0){
			return 1;
		}
		return 0;
	}

	@Override
	public int insertSelective(SysReportUser sysReportUser) {
		return sysReportUserMapper.insertSelective(sysReportUser);
	}

	@Override
	public int selectUserNum() {
		return sysReportUserMapper.selectUserNum();
	}

	@Override
	public List<SysReportUser> selectUserReportInfo() {
		return sysReportUserMapper.selectUserReportInfo();
	}

	@Override
	public List<SysReportUser> selectUserAreaDepartment() {
		return sysReportUserMapper.selectUserAreaDepartment();
	}

	@Override
	public SysReportUser selectUserAreaDepartmentByUserId(BigDecimal bigDecimal) {
		return sysReportUserMapper.selectUserAreaDepartmentByUserId(bigDecimal);
	}
	
	
	@Override
	public SysReportUser selectByLoginCode(String userCode) {
		return sysReportUserMapper.selectByLoginCode(userCode);
	}
	

	@Override
	public SysReportUser selectUserInfoByUserCode(String userCode) {
		return sysReportUserMapper.selectUserInfoByUserCode(userCode);
	}

	//TODO 更新用户信息
	@Override
	public int updateInfo(SysReportUser sysReportUser) {
		return sysReportUserMapper.updateInfo(sysReportUser);
	}
	
	//TODO 假删
	@Override
	public int isdelete(BigDecimal ids) {
		return sysReportUserMapper.isdelete(ids);
	}


	@Override
	public SysReportUser selectUseridByUserName(String userName) {
		return sysReportUserMapper.selectUseridByUserName(userName);
	}

	@Override
	public Page<SysReportUser> selectUserPage(Page<SysReportUser> page) {
		int totalCount=sysReportUserMapper.countUserPage(page);
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		page.setBeginRow(beginRow);
		page.setEndRow(endRow);
		List<SysReportUser>resultList=sysReportUserMapper.selectUserPage(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}
	
	//TODO 重置密码
	@Override
	public int resetPwd(String userCode) {
		String pwd=PasswordUtil.generate(Constant.defaultPwd);
		SysReportUser record=new SysReportUser();
		record.setUserCode(userCode);
		record.setUserPwd(pwd);
		return	sysReportUserMapper.updatePwdByUserCode(record);
	}
	
	//TODO 修改密码
	@Override
	public int updatePwdByUserName(String userCode,String newPwd) {
		SysReportUser sysReportUser = new SysReportUser();
		sysReportUser.setUserPwd(PasswordUtil.generate(newPwd));
		sysReportUser.setUserCode(userCode);
		return sysReportUserMapper.updatePwdByUserName(sysReportUser);
	}

	//TODO查询用户信息
	@Override
	public List<SysReportUser> getUserInfo() {
		return sysReportUserMapper.selectUserInfo();
	}
}

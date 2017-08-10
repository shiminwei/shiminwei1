package com.ahcd.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.common.StringUtil;
import com.ahcd.dao.SysReportUserMapper;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.IUserService;

@Service("userService")  
public class UserServiceImpl implements IUserService {
	@Resource  
    private SysReportUserMapper sysReportUserMapper;
	
	//TODO
	public SysReportUser getUserById(BigDecimal userId) {
		return sysReportUserMapper.selectByUserId(userId);
	}
	
	@Override
	public SysReportUser getUserByLoginCode(String loginCode) {
		return sysReportUserMapper.selectByLoginCode(loginCode);
	}

	/** 
	 * 
	 * @author   : fei yang 
	 * @version ：2016年10月24日 下午3:43:04 
	 */ 
	@Override
	public Page<SysReportUser> getAllByBean(Page<SysReportUser> page, SysReportUser bean) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String userCode;
		if (StringUtil.isBlank(bean.getUserCode())) {
			userCode=null;
		}else {
			userCode=bean.getUserCode();
		}
		map.put("userCode",userCode);
		map.put("userName", bean.getUserName());
		map.put("beginRow", (page.getPageNum() - 1) * page.getNumPerPage());
		map.put("endRow", page.getPageNum() * page.getNumPerPage()+1);
		page.setResult(sysReportUserMapper.getAllByBean(map));
		page.setTotalCount(sysReportUserMapper.getCount(bean));
		return page;
	}

	@Override
	public int selectUserNum() {
		return sysReportUserMapper.selectUserNum();
	}

	@Override
	public int insertUser(SysReportUser sysReportUser) {
		return sysReportUserMapper.insert(sysReportUser);
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

	@Override
	public SysReportUser selectUserInfoByUserCode(String userCode) {
		return sysReportUserMapper.selectUserInfoByUserCode(userCode);
	}

	//TODO 更新用户信息
	@Override
	public int updateInfo(SysReportUser sysReportUser) {
		return sysReportUserMapper.updateInfo(sysReportUser);
	}

	@Override
	public int isdelete(BigDecimal ids) {
		return sysReportUserMapper.isdelete(ids);
	}

}

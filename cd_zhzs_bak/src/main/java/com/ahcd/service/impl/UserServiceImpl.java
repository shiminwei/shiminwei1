package com.ahcd.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.dao.IUserDao;
import com.ahcd.pojo.User;
import com.ahcd.service.IUserService;

@Service("userService")  
public class UserServiceImpl implements IUserService {
	@Resource  
    private IUserDao userDao;
	public User getUserById(int userId) {
		return userDao.selectByPrimaryKey(userId);
	}
	@Override
	public User getUserByLoginCode(String loginCode) {
		return userDao.selectByLoginCode(loginCode);
	}

}

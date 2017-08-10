package com.ahcd.service;

import com.ahcd.pojo.User;

public interface  IUserService {
	public User getUserById(int userId);

	public User getUserByLoginCode(String loginCode);
}

package com.ahcd.common;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;

@SuppressWarnings("all")
public class DecryptDruidSource extends DruidDataSource{
@Override
	public void setUsername(String username) {
		try {
			String privateKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALwayqGLJRIkYC/485DC6ylzHLhCBv/KVVXwLCxKc/SsZfjMbrxrw0b2Jy2ZwOt33H3uKV6d3Tn066xaMIY2h48CAwEAAQ==";
			username = ConfigTools.decrypt(privateKey, username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.setUsername(username);
	}

	@Override
	public void setPassword(String password) {
		try {
			String privateKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJYbKZU/W7QiL/Wtvp/f8aK4jGav8y189U/SB0YK3I0Zo5VvhlnZmuCnq7wlQF4RXHnDi1iaylKB5xM1YhS8yIsCAwEAAQ==";
			password = ConfigTools.decrypt(privateKey, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.setPassword(password);
	}
	
	@Override
	public void setUrl(String url) {
		try {
			String privateKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIJQBWD4fEjA6tqaMt48/dB+gUQmTbzrpG1tZi6inUVJrM0G0fvU4WYvXy15zvXcmxuyrbsn3ovCsHvt5Klvg20CAwEAAQ==";
			url = ConfigTools.decrypt(privateKey, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.setUrl(url);
	}
}

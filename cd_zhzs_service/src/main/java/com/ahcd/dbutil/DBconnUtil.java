package com.ahcd.dbutil;

import com.ahcd.log.Logger;
import com.ahcd.pojo.DatasourceBean;
import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBconnUtil {

	public static Connection getConnection(String dsId) {
		Connection c = null;
		try {
			c=JDBCUtils.getConnection(dsId);
			Logger.info("*** Open a connection from [" + dsId + "]***  ");
		} catch (Exception e) {
			Logger.error(e.toString());
		}
		if (c == null) {
			throw new BrokerException("数据库连接为空 ");
		}
		return c;
	}
	
	public static boolean  testConnection(DatasourceBean db) {
		boolean flag=false;
		DruidDataSource dataSource=null;
		Connection c = null;
		try {
    		dataSource=new DruidDataSource();
    		dataSource.setUrl(db.getUrl());
            dataSource.setUsername(db.getUser());//用户名
            dataSource.setPassword(db.getPwd());//密码
            dataSource.setInitialSize(2);
            dataSource.setMaxActive(20);
            dataSource.setMinIdle(0);
            dataSource.setMaxWait(60000);
            dataSource.setValidationQuery("SELECT 1");
            dataSource.setTestOnBorrow(false);
            dataSource.setTestWhileIdle(true);
            dataSource.setPoolPreparedStatements(false);
            c=dataSource.getConnection();
			if(c!=null)	flag=true;
		} catch (Exception e) {
			Logger.error(e.toString());
		}finally{
			try {
				if(c!=null){
					c.close();
					c = null;
				}
				if(dataSource!=null){
					dataSource.close();
					dataSource=null;
				}
				System.out.println(db.getUrl()+"连接关闭");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
}
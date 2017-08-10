package com.ahcd.common;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconn {
	private static String DB_URL = "";
	private static String DB_DRIVER = "";
	private static Connection conn = null;
	private static String user = "zhzs";
	private static String password = "123456";
	private static String host = "192.168.3.119";
	private static String port = "1521";
	private static String sid = "orcl";

	public static Connection getConnection() {

		DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
		DB_URL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;

		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, user, password);
			if (conn != null) {
				System.out.println("数据库链接成功");

			} else {
				System.out.println("数据库链接失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return conn;
	}

}

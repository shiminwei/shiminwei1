package com.ahcd.common;

import java.sql.Connection;
import java.sql.DriverManager;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.service.impl.JsonConfigServiceImpl;

public class DBconn {

	private static String DB_URL = "";
	private static String DB_DRIVER = "";
	private static Connection conn = null;
	private static String user = "czzhzs";
	private static String password = "hfcaida1557";
	private static String host = "10.4.136.12";
	private static String port = "1521";
	private static String sid = "yxnj";

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

	public static Connection getConnectionByBean(DatasourceBean db) {
		DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
		DB_URL = "jdbc:oracle:thin:@" + db.getHost() + ":" + db.getPort() + ":" + db.getSid();
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, db.getUser(), db.getPwd());
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

	public static Connection getConnectionById(String datasourceId) {
		JsonConfigServiceImpl configServiceImpl = new JsonConfigServiceImpl();
		DatasourceBean db = configServiceImpl.getDatasourceById(datasourceId);
		if (StringUtil.isBlank(db)) {
			return null;
		}

		DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
		DB_URL = "jdbc:oracle:thin:@" + db.getHost() + ":" + db.getPort() + ":" + db.getSid();
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, db.getUser(), db.getPwd());
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

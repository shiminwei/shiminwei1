package com.ahcd.common;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PropertiesUntil {

	private static String propertiesFileName="config.properties";
	public PropertiesUntil(String propertiesFileName){
		PropertiesUntil.propertiesFileName=propertiesFileName+".properties";
	}
	public String getPropertyValue(String propertyStr) {
		// 取得根目录路径
		String filePath = getClass().getResource("/").getFile().toString() + propertiesFileName;
		String str = "";
		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(filePath);
			try {
				prop.load(fis);
				fis.close();
				str = prop.getProperty(propertyStr).trim();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("无法获取资源文件");
		}
		return str;
	}
	public PropertiesUntil() {
	}

	public static void main(String args[]) {
		PropertiesUntil propertiesUntil = new PropertiesUntil("mongodb");
		String aa = propertiesUntil.getPropertyValue("mongo.db.databasename");
		System.out.println(aa);

	}
}
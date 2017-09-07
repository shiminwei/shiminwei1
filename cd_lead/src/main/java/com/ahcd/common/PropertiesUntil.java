package com.ahcd.common;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PropertiesUntil {

	public String getPropertyValue(String propertyStr) {
		// 取得根目录路径
		String filePath = getClass().getResource("/").getFile().toString() + "config.properties";
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
		PropertiesUntil propertiesUntil = new PropertiesUntil();
		String aa = propertiesUntil.getPropertyValue("xml_path");
		System.out.println(aa);

	}
}
package com.ahcd.service;

import java.io.File;
import java.io.IOException;

import com.ahcd.common.EmptyUtils;
import com.ahcd.common.FileUtils;
import com.ahcd.pojo.ExcelTemplate;
import com.alibaba.fastjson.JSONObject;

public class JsonServise {

	

	private final static String excelTemplateConfigPath ="D:/zhzs_resource/template/config/";

	
	
	public static ExcelTemplate getExcelTemplateByName(String name) {
		ExcelTemplate excelTemplate = new ExcelTemplate();
		boolean isExist = false;
		try {
			File file = new File(excelTemplateConfigPath);
			String[] filelist = file.list();
			if (filelist != null) {
				for (int i = 0; i < filelist.length; i++) {
					String fileName = excelTemplateConfigPath + "\\" + filelist[i];
 					String fileContent = FileUtils.getFileContent(fileName);
 					if (EmptyUtils.isNotEmpty(fileContent)) {
						ExcelTemplate excelTemplateTmp = JSONObject.parseObject(fileContent, ExcelTemplate.class);
						if (excelTemplateTmp == null)
							continue;
						else if (excelTemplateTmp.getName().equals(name)) {
							excelTemplate = excelTemplateTmp;
							isExist = true;
						}
					}
				}
			} else {
				System.out.println("文件夹不不存在");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (isExist) {
			return excelTemplate;
		} else {
			return null;
		}
	}
}

package com.ahcd.dbutil;

import com.ahcd.common.FileUtil;
import com.ahcd.common.PropertyManager;
import com.ahcd.common.StringUtil;
import com.ahcd.log.Logger;
import com.ahcd.pojo.DatasourceBean;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Config {
	private final static String datasourcePath = PropertyManager.getConfigProperty("config_path")
			+ PropertyManager.getConfigProperty("datasource");
	
	private Map<String,DatasourceBean> dsmap = new HashMap<String,DatasourceBean>();

	public Config() {
		reloadConfig();
	}
	public void reloadConfig(){
		try {
			File file = new File(datasourcePath);
			String[] filelist = file.list();
			if (filelist != null) {
				for (int i = 0; i < filelist.length; i++) {
					String fileName = datasourcePath + "\\" + filelist[i];
					String fileContent = FileUtil.getFileContent(fileName);
					if (!StringUtil.isBlank(fileContent)) {
						DatasourceBean db = JSONObject.parseObject(fileContent, DatasourceBean.class);
						if (db == null)
							continue;

						String dsId = db.getId();
						this.dsmap.put(dsId, db);
					}
				}
			} else {
				System.out.println("文件夹不不存在");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Iterator<String> it = this.dsmap.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			DatasourceBean ds = (DatasourceBean) this.dsmap.get(key);
			try {
				Class.forName(ds.getDriver()).newInstance();
				Logger.info("datasource-name -> " + ds.getName());
				Logger.info("dbdriver -> " + ds.getDriver());
				Logger.info("dburl -> " + ds.getHost());
			} catch (Exception e) {
				System.out.println("error------>" + e.toString());
			}
		}
	}
	public DatasourceBean getDS(String key) {
		return (DatasourceBean) this.dsmap.get(key);
	}
	public Map<String,DatasourceBean> getAllDS() {
		return dsmap;
	}
}
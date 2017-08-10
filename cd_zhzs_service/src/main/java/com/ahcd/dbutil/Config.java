package com.ahcd.dbutil;

import com.ahcd.common.MongoBeanUtil;
import com.ahcd.common.PropertiesUntil;
import com.ahcd.log.Logger;
import com.ahcd.pojo.DatasourceBean;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Config {
	private static PropertiesUntil propertiesUntil = new PropertiesUntil("mongodb");
	private static String host = propertiesUntil.getPropertyValue("mongo.db.host");
	private static String port = propertiesUntil.getPropertyValue("mongo.db.port");
	private static String databasename = propertiesUntil.getPropertyValue("mongo.db.databasename");
	private Map<String,DatasourceBean> dsmap = new HashMap<String,DatasourceBean>();

	public Config() {
		reloadConfig();
	}
	@SuppressWarnings({ "resource", "deprecation" })
	public void reloadConfig(){
		try {
			MongoClient        mongoClient = new MongoClient( host , Integer.valueOf(port) );//建立连接
	        DB get_db_credit = mongoClient.getDB(databasename);//数据库名
	        DBCollection collection = get_db_credit.getCollection("zhzs_query_datasource");
	        DBCursor cursor = collection.find();
			List<DBObject> list=cursor.toArray();
			if(list!=null && list.size() > 0) {
				for(DBObject dbObject:list){
					DatasourceBean datasourceBean= MongoBeanUtil.dbObject2Bean(dbObject, new DatasourceBean());
					datasourceBean.setId(dbObject.get("_id").toString());
					String dsId = datasourceBean.getId();
					this.dsmap.put(dsId, datasourceBean);
				}
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
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	public DatasourceBean getDS(String key) {
		return (DatasourceBean) this.dsmap.get(key);
	}
	public Map<String,DatasourceBean> getAllDS() {
		return dsmap;
	}
}
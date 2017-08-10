package com.ahcd.pojo;

public class DataTableBean {

	private String tableName;// 数据库表名
	private String tableDesc;// 数据库表名描述

	public DataTableBean() {

	}

	public DataTableBean(String tableName, String tableDesc) {
		super();
		this.tableName = tableName;
		this.tableDesc = tableDesc;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

}

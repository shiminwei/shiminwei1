package com.ahcd.pojo;

import com.ahcd.common.StringUtil;

public class ExcelTemplateCol {
	private String name; // exce列名
	private int colOrder;// 列顺序
	private String columnName;// 对应数据库表名
	private int colType = 1;// 字段类型
	private int isMain;// 是否主要字段
	private Integer colLength;// 长度

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getColOrder() {
		return colOrder;
	}

	public void setColOrder(int colOrder) {
		this.colOrder = colOrder;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getIsMain() {
		return isMain;
	}

	public void setIsMain(int isMain) {
		this.isMain = isMain;
	}

	public int getColType() {
		return colType;
	}

	public void setColType(int colType) {
		this.colType = colType;
	}

	public Integer getColLength() {
		if (colType == 1 && StringUtil.isBlank(colLength)) {// 1：字符串
			return 255;
		}
		return colLength;
	}

	public void setColLength(Integer colLength) {
		this.colLength = colLength;
	}
}

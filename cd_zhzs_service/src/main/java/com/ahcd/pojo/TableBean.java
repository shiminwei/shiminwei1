package com.ahcd.pojo;

import java.util.List;

public class TableBean {

	private String table; // 表名
	private List<TableRowBean> rows;// 行值
	private List<TableColumnBean> columns;// 列值

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public List<TableRowBean> getRows() {
		return rows;
	}

	public void setRows(List<TableRowBean> rows) {
		this.rows = rows;
	}

	public List<TableColumnBean> getColumns() {
		return columns;
	}

	public void setColumns(List<TableColumnBean> columns) {
		this.columns = columns;
	}

}

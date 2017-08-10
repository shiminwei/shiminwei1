package com.ahcd.pojo;

/**
 * Excel 列内容
 * 
 * @author : fei yang
 * @version ：2017年4月10日 上午11:50:15
 */
public class ExcelColumnBean {

	private Integer columnIndex;// 所属列
	private int columnType;// 列类型(当前是poi类包含的类型)
	private Object content;// 内容
	
	
	
	
	public ExcelColumnBean() {
		super();
	}
	public ExcelColumnBean(int columnType, Object content) {
		super();
		this.columnType = columnType;
		this.content = content;
	}
	public Integer getColumnIndex() {
		return columnIndex;
	}
	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
	}
	public int getColumnType() {
		return columnType;
	}
	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}




}

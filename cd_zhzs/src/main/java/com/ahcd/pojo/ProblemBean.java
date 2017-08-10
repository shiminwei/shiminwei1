package com.ahcd.pojo;

/**
 * @author : fei yang
 * @version ：2016年11月4日 下午4:10:51
 */
public class ProblemBean {
	private Integer row = 0;// 行数
	private Integer column = 0;// 列数
	private String content;// 问题内容

	
	
	
	public ProblemBean(Integer row, Integer column, String content) {
		super();
		this.row = row;
		this.column = column;
		this.content = content;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}

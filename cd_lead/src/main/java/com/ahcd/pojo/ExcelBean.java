package com.ahcd.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelBean {

	private String name;// 表名
	private List<String> colName = new ArrayList<String>();// excel 列名
	private String type;// xls获取xlsx
	private int rowsNum = 0;// 行数
	private List<Object> content = new ArrayList<Object>();// 内容
	private int checkResults=0;//检查结果

	private List<ProblemBean>problems=new ArrayList<ProblemBean>();//内容存在的问题
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getColName() {
		return colName;
	}

	public void setColName(List<String> colName) {
		this.colName = colName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRowsNum() {
		return rowsNum;
	}

	public void setRowsNum(int rowsNum) {
		this.rowsNum = rowsNum;
	}

	public List<Object> getContent() {
		return content;
	}

	public void setContent(List<Object> content) {
		this.content = content;
	}

	public int getCheckResults() {
		return checkResults;
	}

	public void setCheckResults(int checkResults) {
		this.checkResults = checkResults;
	}

	public List<ProblemBean> getProblems() {
		return problems;
	}

	public void setProblems(List<ProblemBean> problems) {
		this.problems = problems;
	}

}

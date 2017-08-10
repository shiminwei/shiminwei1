package com.ahzd.pojo;

public class DataJobStepDbToTxt extends DataJobStepBase{
	//文件存储路径
	private String filePath;
	//分隔符
	private String separator;
	//查询sql
	private String sql;
	//读取开始行
	private int beginRowIndex;	
	//保存文件时的后缀名
	private String fileNamePatterns;
	public String getFileNamePatterns() {
		return fileNamePatterns;
	}

	public void setFileNamePatterns(String fileNamePatterns) {
		this.fileNamePatterns = fileNamePatterns;
	}

	public int getBeginRowIndex() {
		return beginRowIndex;
	}

	public void setBeginRowIndex(int beginRowIndex) {
		this.beginRowIndex = beginRowIndex;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}

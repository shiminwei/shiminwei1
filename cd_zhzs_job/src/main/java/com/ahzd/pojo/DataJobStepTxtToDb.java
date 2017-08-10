package com.ahzd.pojo;

public class DataJobStepTxtToDb extends DataJobStepBase{
	//读取开始行
	private int beginRowIndex;
	//读取结束行
	private int endRowIndex;
	//读取路径
	private String filePath;
	//对应的tableName
	private String tableName;
	//分隔符
	private String separator;
	//保存文件时的后缀名
	private String fileNamePatterns;
	public String getFileNamePatterns() {
		return fileNamePatterns;
	}
	public void setFileNamePatterns(String fileNamePatterns) {
		this.fileNamePatterns = fileNamePatterns;
	}
	public String getSeparator() {
		return separator;
	}
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	public int getBeginRowIndex() {
		return beginRowIndex;
	}
	public void setBeginRowIndex(int beginRowIndex) {
		this.beginRowIndex = beginRowIndex;
	}
	public int getEndRowIndex() {
		return endRowIndex;
	}
	public void setEndRowIndex(int endRowIndex) {
		this.endRowIndex = endRowIndex;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}

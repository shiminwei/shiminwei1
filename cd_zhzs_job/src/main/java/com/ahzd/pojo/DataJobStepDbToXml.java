package com.ahzd.pojo;

/**
 * 数据源导出XML实体BEAN
 */
public class DataJobStepDbToXml extends DataJobStepBase {

	//读取开始行
	private Integer beginRowIndex;
	//读取结束行
	private Integer endRowIndex;
	//读取路径
	private String filePath;
	//对应的tableName
	private String sql;
	//保存文件时的后缀名
	private String fileNamePatterns;
	
	
	public Integer getBeginRowIndex() {
		return beginRowIndex;
	}
	public void setBeginRowIndex(Integer beginRowIndex) {
		this.beginRowIndex = beginRowIndex;
	}
	public Integer getEndRowIndex() {
		return endRowIndex;
	}
	public void setEndRowIndex(Integer endRowIndex) {
		this.endRowIndex = endRowIndex;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getFileNamePatterns() {
		return fileNamePatterns;
	}
	public void setFileNamePatterns(String fileNamePatterns) {
		this.fileNamePatterns = fileNamePatterns;
	}


}

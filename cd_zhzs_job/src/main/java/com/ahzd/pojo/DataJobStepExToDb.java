package com.ahzd.pojo;


/**
 * excel与db table的对应关系实体
 * @author admin
 *
 */
public class DataJobStepExToDb  extends DataJobStepBase{
	//读取的sheet页
	private Integer sheetIndex;
	//读取开始行
	private Integer beginRowIndex;
	//读取结束行
	private Integer endRowIndex;
	//读取路径
	private String filePath;
	//对应的tableName
	private String tableName;
	//保存文件时的后缀名
	private String fileNamePatterns;
	
	
	public String getFileNamePatterns() {
		return fileNamePatterns;
	}
	public void setFileNamePatterns(String fileNamePatterns) {
		this.fileNamePatterns = fileNamePatterns;
	}
	
	public Integer getSheetIndex() {
		return sheetIndex;
	}
	public void setSheetIndex(Integer sheetIndex) {
		this.sheetIndex = sheetIndex;
	}
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
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}

package com.ahzd.pojo;

public class DataJobStepXmlToDb extends DataJobStepBase {

	//读取开始行
	private Integer beginRowIndex;
	//读取结束行
	private Integer endRowIndex;
	//读取路径
	private String filePath;	
	//根节点名称
	private String rootName="ROOTS";
	//子节点名称
	private String rowName="ROOT";	
	//源数据导出类型属性名称
	private String dataTypeName="dataType";
	//源数据导出字段类型名称
	private String colTypeName="colType";
	//导入表名
	private String tableName;
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
	public String getRootName() {
		return rootName;
	}
	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public String getRowName() {
		return rowName;
	}
	public void setRowName(String rowName) {
		this.rowName = rowName;
	}
	public String getDataTypeName() {
		return dataTypeName;
	}
	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}
	public String getColTypeName() {
		return colTypeName;
	}
	public void setColTypeName(String colTypeName) {
		this.colTypeName = colTypeName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getFileNamePatterns() {
		return fileNamePatterns;
	}
	public void setFileNamePatterns(String fileNamePatterns) {
		this.fileNamePatterns = fileNamePatterns;
	}
	
	
	
	
	
}

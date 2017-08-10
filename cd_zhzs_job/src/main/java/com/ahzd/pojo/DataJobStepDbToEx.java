package com.ahzd.pojo;

public class DataJobStepDbToEx extends DataJobStepSqlScripts {
	private String filePath;
	// 保存文件时的后缀名
	private String fileNamePatterns;

	public String getFileNamePatterns() {
		return fileNamePatterns;
	}

	public void setFileNamePatterns(String fileNamePatterns) {
		this.fileNamePatterns = fileNamePatterns;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}

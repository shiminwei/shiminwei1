package com.ahzd.pojo;


/**
 * 发送文件到ftp相关实体
 * @author admin
 *
 */
public class DataJobStepFileToFtp  extends DataJobStepBase{
	//ip地址
	private String address;
	//端口号
	private int port;
	//用户名
	private String username;
	//密码
	private String password;
	//文件路径
	private String filePath;
	//保存文件时的后缀名
	private String fileNamePatterns;
	//是否删除原文件  1是,0否
	private String isDelete;
	
	public String getFileNamePatterns() {
		return fileNamePatterns;
	}
	public void setFileNamePatterns(String fileNamePatterns) {
		this.fileNamePatterns = fileNamePatterns;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}

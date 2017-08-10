package com.ahzd.pojo;


/**
 * 发送文件到ftp相关实体
 * @author admin
 *
 */
public class DataJobStepFtpToLocal  extends DataJobStepBase{
	//ip地址
	private String address;
	//端口号
	private int port;
	//用户名
	private String username;
	//密码
	private String password;
	//FTP文件路径
	private String filePath;
	//文件本地存放路径
	private String localPath;
	//保存文件时的后缀名
	private String fileNamePatterns;

	public String getFileNamePatterns() {
		return fileNamePatterns;
	}
	public void setFileNamePatterns(String fileNamePatterns) {
		this.fileNamePatterns = fileNamePatterns;
	}
	public String getLocalPath() {
		return localPath;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
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

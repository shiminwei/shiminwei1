package com.ahcd.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "function")
public class LeadConfigBean {

	private String userId;
	private String userName;
	private String leadId;
	private String dirPath;
	private String quartzStr;
	private String quartzType;
	private String departmentName;
	private int isMatch=0;//是否匹配到用户
	private int isStart=0;//启动状态
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public String getQuartzStr() {
		return quartzStr;
	}

	public void setQuartzStr(String quartzStr) {
		this.quartzStr = quartzStr;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getQuartzType() {
		return quartzType;
	}

	public void setQuartzType(String quartzType) {
		this.quartzType = quartzType;
	}

	public int getIsMatch() {
		return isMatch;
	}

	public void setIsMatch(int isMatch) {
		this.isMatch = isMatch;
	}

	public int getIsStart() {
		return isStart;
	}

	public void setIsStart(int isStart) {
		this.isStart = isStart;
	}

}

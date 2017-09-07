package com.ahcd.pojo;

import java.util.List;

public class ExcelTemplate {
	private String name;
	private String desc;
	private String updateTime;
	private String user;
	private int zq;//上报周期
	private int zqgqsj;//上报周期过期时间天数
	private String fileType;//上报文件类型
	private String templateFile;//上报文件名
	private String idcode;
	private String datasource;
	private String tableName;
	private int start=1;
	private int end=0;
	private String delColName="k_del";
	private String kDateColName="k_date";
	private String yearColName="k_year";
	private String monthColName="k_month";
	private String idColName="k_id";
	private String areaColName="k_area_id";
	private String departmentColName="k_department_id";
	private String userColName="k_user_id";
	private List<ExcelTemplateCol> cols;
	private int nowIsReport=0;//本期是否报送
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getZq() {
		return zq;
	}
	public void setZq(int zq) {
		this.zq = zq;
	}
	public int getZqgqsj() {
		return zqgqsj;
	}
	public void setZqgqsj(int zqgqsj) {
		this.zqgqsj = zqgqsj;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getTemplateFile() {
		return templateFile;
	}
	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}
	public String getIdcode() {
		return idcode;
	}
	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}
	public String getDatasource() {
		return datasource;
	}
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public String getDelColName() {
		return delColName;
	}
	public void setDelColName(String delColName) {
		this.delColName = delColName;
	}
	public String getIdColName() {
		return idColName;
	}
	public void setIdColName(String idColName) {
		this.idColName = idColName;
	}
	public List<ExcelTemplateCol> getCols() {
		return cols;
	}
	public void setCols(List<ExcelTemplateCol> cols) {
		this.cols = cols;
	}
	public String getAreaColName() {
		return areaColName;
	}
	public void setAreaColName(String areaColName) {
		this.areaColName = areaColName;
	}
	public String getDepartmentColName() {
		return departmentColName;
	}
	public void setDepartmentColName(String departmentColName) {
		this.departmentColName = departmentColName;
	}
	public String getUserColName() {
		return userColName;
	}
	public void setUserColName(String userColName) {
		this.userColName = userColName;
	}
	public String getYearColName() {
		return yearColName;
	}
	public void setYearColName(String yearColName) {
		this.yearColName = yearColName;
	}
	public String getMonthColName() {
		return monthColName;
	}
	public void setMonthColName(String monthColName) {
		this.monthColName = monthColName;
	}
	public String getkDateColName() {
		return kDateColName;
	}
	public void setkDateColName(String kDateColName) {
		this.kDateColName = kDateColName;
	}
	public int getNowIsReport() {
		return nowIsReport;
	}
	public void setNowIsReport(int nowIsReport) {
		this.nowIsReport = nowIsReport;
	}
	
	
	
}

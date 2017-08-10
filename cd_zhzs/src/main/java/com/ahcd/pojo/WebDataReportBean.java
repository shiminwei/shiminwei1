package com.ahcd.pojo;

/** 
 * @author   : fei yang 
 * @version ：2016年11月4日 上午10:41:35 
 */
public class WebDataReportBean {

	private String year;//上报年份
	private String month;//上报月份
	private String reportType;//上报类型 1 上报 2补报 3重报
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
}


package com.ahcd.pojo;

/**
 * @author : fei yang
 * @version ：2016年11月15日 下午1:59:03
 */
public class SysReportChileBean {

	private int beginYear;// 开始年
	private int beginMonth;// 开始月份 COde
	private int endYear;// 结束年
	private int endMonth;// 结束月份 COde
	private int zq;//报送周期类型
	public int getBeginYear() {
		return beginYear;
	}
	public void setBeginYear(int beginYear) {
		this.beginYear = beginYear;
	}
	public int getBeginMonth() {
		return beginMonth;
	}
	public void setBeginMonth(int beginMonth) {
		this.beginMonth = beginMonth;
	}
	public int getEndYear() {
		return endYear;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	public int getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}
	public int getZq() {
		return zq;
	}
	public void setZq(int zq) {
		this.zq = zq;
	}
	
}

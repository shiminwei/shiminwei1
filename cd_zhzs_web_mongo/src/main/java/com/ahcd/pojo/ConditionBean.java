package com.ahcd.pojo;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author 作者 : fei yang
 * @version 创建时间：2016年9月19日 上午11:19:55 类说明
 */

public class ConditionBean {
	private String conditionId;
	private String morenzhi;
	private String dispname;// 展现名称
	private String width;// 宽度
	private String disptype = "1";// 1。文本框 2.年下拉框 3.年月下拉框
	private String isByValue;// 是发传值到明细页面字段

	@XmlAttribute(name = "dispname")
	public String getDispname() {
		return dispname;
	}

	public void setDispname(String dispname) {
		this.dispname = dispname;
	}

	@XmlAttribute(name = "width")
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	@XmlAttribute(name = "disptype")
	public String getDisptype() {
		return disptype;
	}

	public void setDisptype(String disptype) {
		this.disptype = disptype;
	}

	@XmlAttribute(name = "morenzhi")
	public String getMorenzhi() {
		return morenzhi;
	}

	public void setMorenzhi(String morenzhi) {
		this.morenzhi = morenzhi;
	}

	@XmlAttribute(name = "id")
	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	@XmlAttribute(name = "isByValue")
	public String getIsByValue() {
		return isByValue;
	}

	public void setIsByValue(String isByValue) {
		this.isByValue = isByValue;
	}

}

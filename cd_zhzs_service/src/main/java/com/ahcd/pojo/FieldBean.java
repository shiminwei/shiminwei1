package com.ahcd.pojo;

import javax.xml.bind.annotation.XmlAttribute;

import com.ahcd.common.StringUtil;

/**
 * @author 作者 : fei yang
 * @version 创建时间：2016年9月19日 上午11:12:36 类说明
 */

public class FieldBean {
	private String sqlFielName;// 查询数据库列名
	private String jspFielName;// JSP展现列名
	private String hidden;// 是否隐藏
	private String totalaggr;// 合计
	private String subtotalaggr; // 小计
	private String link; // 链接地址
	
	private String target;//是否打开新页面
	
	private String targetType; // 打开方式 navTab 打开正常页面 dialog：弹出框
	
	
	private String isByValue;// 是否传值到明细页面字段
	private String name;// 原来替换Name
	private String warningStr;// 是否预警 为空代表不需要预警， 不为空代表需要当前列 需要比对预警的字符串
	private String mergeName;// 合并第二行列名称
	private String mergeNum;// 合并列数量
	private String firstMergeName;// 合并第一行列名称
	private String firstMergeNum;// 合并列数量
	private String rowNum;// 合并行数量
	private String width; // 列宽字符串
	private String color; // 颜色字符串
	public String getFirstMergeName() {
		return firstMergeName;
	}

	public void setFirstMergeName(String firstMergeName) {
		this.firstMergeName = firstMergeName;
	}

	public String getFirstMergeNum() {
		return firstMergeNum;
	}

	public void setFirstMergeNum(String firstMergeNum) {
		this.firstMergeNum = firstMergeNum;
	}

	private String linkTitle;// 链接标题

	
	public FieldBean() {
		super();
	}

	public FieldBean(String sqlFielName, String jspFielName) {
		super();
		this.sqlFielName = sqlFielName;
		this.jspFielName = jspFielName;
	}

	@XmlAttribute(name = "hidden")
	public String getHidden() {
		if (StringUtil.isBlank(hidden)) {
			return "0";
		}
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	@XmlAttribute(name = "totalaggr")
	public String getTotalaggr() {
		if (StringUtil.isBlank(totalaggr)) {
			return "0";
		}
		return totalaggr;
	}

	public void setTotalaggr(String totalaggr) {
		this.totalaggr = totalaggr;
	}

	@XmlAttribute(name = "subtotalaggr")
	public String getSubtotalaggr() {
		if (StringUtil.isBlank(subtotalaggr)) {
			return "0";
		}
		return subtotalaggr;
	}

	public void setSubtotalaggr(String subtotalaggr) {
		this.subtotalaggr = subtotalaggr;
	}

	@XmlAttribute(name = "target")
	public String getTarget() {
		if (StringUtil.isBlank(target)) {
			return "0";
		}
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@XmlAttribute(name = "link")
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@XmlAttribute(name = "sqlFielName")
	public String getSqlFielName() {
		return sqlFielName;
	}

	public void setSqlFielName(String sqlFielName) {
		this.sqlFielName = sqlFielName;
	}

	@XmlAttribute(name = "jspFielName")
	public String getJspFielName() {
		return jspFielName;
	}

	public void setJspFielName(String jspFielName) {
		this.jspFielName = jspFielName;
	}

	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name = "isByValue")
	public String getIsByValue() {
		return isByValue;
	}

	public void setIsByValue(String isByValue) {
		this.isByValue = isByValue;
	}

	@XmlAttribute(name = "warningStr")
	public String getWarningStr() {
		return warningStr;
	}

	public void setWarningStr(String warningStr) {
		this.warningStr = warningStr;
	}

	@XmlAttribute(name = "mergeName")
	public String getMergeName() {
		return mergeName;
	}

	public void setMergeName(String mergeName) {
		this.mergeName = mergeName;
	}

	@XmlAttribute(name = "mergeNum")
	public String getMergeNum() {
		return mergeNum;
	}

	public void setMergeNum(String mergeNum) {
		this.mergeNum = mergeNum;
	}

	@XmlAttribute(name = "width")
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	@XmlAttribute(name = "color")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@XmlAttribute(name = "linkTitle")
	public String getLinkTitle() {
		return linkTitle;
	}
	
	

	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}


}

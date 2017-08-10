package com.ahcd.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


/**
 * @author 作者 : fei yang
 * @version 创建时间：2016年9月19日 上午11:09:14 类说明
 */

public class ResultBean {

	private String sql;// 查询的SQL语句
	private List<FieldBean> fieldBeans=new ArrayList<FieldBean>();// 列集合
	private Integer showType = 1; // 展示类型 1：正常展示 2 ：合并列展示

	public String getSql() {
		return sql;
	}

	@XmlElement(name = "sqlstring")
	public void setSql(String sql) {
		this.sql = sql;
	}

	@XmlElement(name = "field")
	public List<FieldBean> getFieldBeans() {
		return fieldBeans;
	}

	public void setFieldBeans(List<FieldBean> fieldBeans) {
		this.fieldBeans = fieldBeans;
	}

	@Override
	public String toString() {
		return "ResultBean [sql=" + sql + ", fieldBeans=" + fieldBeans + "]";
	}

	public ResultBean(String sql, List<FieldBean> fieldBeans) {
		super();
		this.sql = sql;
		this.fieldBeans = fieldBeans;
	}

	public ResultBean() {
		super();
	}

	@XmlAttribute(name = "showType")
	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}

}

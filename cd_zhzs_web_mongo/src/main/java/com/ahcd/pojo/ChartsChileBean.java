package com.ahcd.pojo;

import java.util.List;
import java.util.Map;

/**
 * @author : fei yang
 * @version ：2016年12月23日 下午1:30:27
 */
public class ChartsChileBean {

	private String width;// 宽度
	private String heigh;// 长度
	private String fangwei;// 位置
	private String type;// 类型
	private String sql;//查询SQL
	
	private Map<String, List<String[]>> result;//结果
	
	List<FieldBean> fieldBeans;//查询结果集合
	
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeigh() {
		return heigh;
	}

	public void setHeigh(String heigh) {
		this.heigh = heigh;
	}

	public String getFangwei() {
		return fangwei;
	}

	public void setFangwei(String fangwei) {
		this.fangwei = fangwei;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<FieldBean> getFieldBeans() {
		return fieldBeans;
	}

	public void setFieldBeans(List<FieldBean> fieldBeans) {
		this.fieldBeans = fieldBeans;
	}

	public Map<String, List<String[]>> getResult() {
		return result;
	}

	public void setResult(Map<String, List<String[]>> result) {
		this.result = result;
	}

	
}

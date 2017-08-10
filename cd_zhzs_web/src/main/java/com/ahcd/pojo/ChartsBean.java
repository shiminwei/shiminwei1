package com.ahcd.pojo;

import java.util.List;

public class ChartsBean {

	private String id;// id标识
	private String name;// 名称
	private String datasource;// 数据源
	private String layoutType;//布局类型
	List<ChartsChileBean> chartsChileBeans;//子集合
	
	public List<ChartsChileBean> getChartsChileBeans() {
		return chartsChileBeans;
	}
	public void setChartsChileBeans(List<ChartsChileBean> chartsChileBeans) {
		this.chartsChileBeans = chartsChileBeans;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDatasource() {
		return datasource;
	}
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	public String getLayoutType() {
		return layoutType;
	}
	public void setLayoutType(String layoutType) {
		this.layoutType = layoutType;
	}
	
	
	
}

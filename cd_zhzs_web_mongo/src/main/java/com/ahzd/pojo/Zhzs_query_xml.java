package com.ahzd.pojo;

import java.util.List;

public class Zhzs_query_xml {
	
	private List<ConditionBeans> conditionBeans;
	private String conditioncolumn;
	private String datasource;
	private String dimfile;
	private String id;
	private String name;
	private String[] parameters;
	private String resultType;
	private Results results;
	public List<ConditionBeans> getConditionBeans() {
		return conditionBeans;
	}
	public void setConditionBeans(List<ConditionBeans> conditionBeans) {
		this.conditionBeans = conditionBeans;
	}
	public String getConditioncolumn() {
		return conditioncolumn;
	}
	public void setConditioncolumn(String conditioncolumn) {
		this.conditioncolumn = conditioncolumn;
	}
	public String getDatasource() {
		return datasource;
	}
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	public String getDimfile() {
		return dimfile;
	}
	public void setDimfile(String dimfile) {
		this.dimfile = dimfile;
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
	public String[] getParameters() {
		return parameters;
	}
	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public Results getResults() {
		return results;
	}
	public void setResults(Results results) {
		this.results = results;
	}
	
	
}

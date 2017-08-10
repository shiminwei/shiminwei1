package com.ahcd.pojo;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "function")
public class ConfigNewBean {

	private String id;// id标识
	private String name;// 名称
	private String datasource;// 数据源
	private String conditioncolumn;// 列数
	private String dimfile;// 子查询
	private String resultType;// 结果集类型
	private ResultBean results;// 结果集
	private List<ConditionBean> conditionBeans;// 查询条件
	
	
@XmlElement(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@XmlElement(name = "datasource")
	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	@XmlElement(name = "conditioncolumn")
	public String getConditioncolumn() {
		return conditioncolumn;
	}

	public void setConditioncolumn(String conditioncolumn) {
		this.conditioncolumn = conditioncolumn;
	}
	@XmlElement(name = "dimfile")
	public String getDimfile() {
		return dimfile;
	}

	public void setDimfile(String dimfile) {
		this.dimfile = dimfile;
	}
	@XmlElement(name = "resultType")
	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	@XmlElement(name = "condition")
	public List<ConditionBean> getConditionBeans() {
		return conditionBeans;
	}

	public void setConditionBeans(List<ConditionBean> conditionBeans) {
		this.conditionBeans = conditionBeans;
	}
	@XmlElement(name = "result")
	public ResultBean getResults() {
		return results;
	}

	public void setResults(ResultBean results) {
		this.results = results;
	}

}

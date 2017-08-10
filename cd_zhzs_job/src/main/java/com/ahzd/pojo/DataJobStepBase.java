package com.ahzd.pojo;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ahcd.common.StringUtil;
import com.ahcd.pojo.NewChileBean;



/**
 * 所有步骤的基类，提取了各种类型步骤所需的公共字段
 * @author admin
 *
 */
public class DataJobStepBase {
	public DataJobStepBase(){
		if(StringUtil.isBlank(stepId)){
			this.setStepId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
	}
	//步骤id
	private String stepId;
	//步骤名称
	private String name;
	//排序，即执行顺序
	private Integer sortNum;
	//类型 1：从oracle执行sql
	private Integer type;
	//数据源id
	private String datasourceId;
	//参数对应关系
	private Map<String,String> paramMap;

	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	
	
	
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDatasourceId() {
		return datasourceId;
	}
	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}

	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	public Map<String, String> getParamMap() {
		return paramMap;
	}
	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
}

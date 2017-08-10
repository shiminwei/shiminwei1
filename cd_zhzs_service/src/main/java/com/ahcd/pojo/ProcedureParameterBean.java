package com.ahcd.pojo;

import javax.xml.bind.annotation.XmlAttribute;

public class ProcedureParameterBean {

	private Integer id;// 参数ID
	private String type;// 参数类型
	private String conditionId;//查询ID
	private String value;// 参数值

	@XmlAttribute(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlAttribute(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@XmlAttribute(name = "conditionId")
	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}


	@XmlAttribute(name = "value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

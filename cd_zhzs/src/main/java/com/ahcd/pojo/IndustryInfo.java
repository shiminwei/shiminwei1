package com.ahcd.pojo;

import java.math.BigDecimal;

/**	行业信息表
 * */
public class IndustryInfo {
	/**	主键
	 */
	private BigDecimal industryId;
	/** 备注
	 */
	private String remark;
	/** 名称
	 */
	private String name;
	
	public BigDecimal getIndustryId() {
		return industryId;
	}
	public void setIndustryId(BigDecimal industryId) {
		this.industryId = industryId;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

package com.ahcd.pojo;

import java.math.BigDecimal;

/**分行业分税种分地区税率
 */
public class AreaIndustryTaxInfo {
    /**编号
     */
    private BigDecimal aitiId;

    /**名称
     */
    private String name;

    /**税率
     */
    private BigDecimal taxrate;

    /**备注
     */
    private String remark;

    /**地区ID
     */
    private BigDecimal areaId;
    
    /**行业信息ID
     */
    private BigDecimal industryId;
    
    /**行业信息名称
     */
    private String industryname;
    
    /**税种信息ID
     */
    private BigDecimal taxId;
    
    /**税种信息名称
     */
    private String taxname;

    public BigDecimal getAitiId() {
        return aitiId;
    }

    public void setAitiId(BigDecimal aitiId) {
        this.aitiId = aitiId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(BigDecimal taxrate) {
        this.taxrate = taxrate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getAreaId() {
        return areaId;
    }

    public void setAreaId(BigDecimal areaId) {
        this.areaId = areaId;
    }

    public BigDecimal getIndustryId() {
        return industryId;
    }

    public void setIndustryId(BigDecimal industryId) {
        this.industryId = industryId;
    }

    public BigDecimal getTaxId() {
        return taxId;
    }

    public void setTaxId(BigDecimal taxId) {
        this.taxId = taxId;
    }
    
	public String getIndustryname() {
		return industryname;
	}

	public void setIndustryname(String industryname) {
		this.industryname = industryname;
	}

	public String getTaxname() {
		return taxname;
	}

	public void setTaxname(String taxname) {
		this.taxname = taxname;
	}
}
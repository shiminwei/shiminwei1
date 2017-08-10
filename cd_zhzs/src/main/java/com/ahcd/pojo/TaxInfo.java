package com.ahcd.pojo;

import java.math.BigDecimal;
/**	税种信息表
 * */
public class TaxInfo {
    /**	主键
     */
    private BigDecimal taxId;

    /**	名称
     */
    private String name;

    /**	备注
     */
    private String remark;

    public BigDecimal getTaxId() {
        return taxId;
    }

    public void setTaxId(BigDecimal taxId) {
        this.taxId = taxId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
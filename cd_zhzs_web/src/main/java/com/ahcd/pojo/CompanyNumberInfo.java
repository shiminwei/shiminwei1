package com.ahcd.pojo;

import java.math.BigDecimal;

/**企业纳税识别和/社会信用代码信息表
 * */
public class CompanyNumberInfo {
    /**主键
     */
    private String numberId;

    /**企业ID
     */
    private BigDecimal id;

    /**识别号
     */
    private String identifyNumber;

    public String getNumberId() {
        return numberId;
    }

    public void setNumberId(String numberId) {
        this.numberId = numberId;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }
}
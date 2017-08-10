package com.ahcd.pojo;

import java.math.BigDecimal;

/**企业地址信息表
 * */
public class CompanyAddressInfo {
    /**主键
     */
    private String addressId;

    /**企业id
     */
    private BigDecimal id;

    /**企业地址
     */
    private String address;

    /**地址来源 1：国税 2：地税 3：财政
     */
    private String type;

    /**地址类型：1：经营地址 2：注册地址
     */
    private String addressType;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
}
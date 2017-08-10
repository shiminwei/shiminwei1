package com.ahcd.pojo;

import java.math.BigDecimal;

public class ZdConstant {
    private BigDecimal constantId;

    private String code;

    private String value;

    private BigDecimal state;

    private String constantDesc;

    private String type;

    private String name;

    public BigDecimal getConstantId() {
        return constantId;
    }

    public void setConstantId(BigDecimal constantId) {
        this.constantId = constantId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public BigDecimal getState() {
        return state;
    }

    public void setState(BigDecimal state) {
        this.state = state;
    }

    public String getConstantDesc() {
        return constantDesc;
    }

    public void setConstantDesc(String constantDesc) {
        this.constantDesc = constantDesc == null ? null : constantDesc.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}
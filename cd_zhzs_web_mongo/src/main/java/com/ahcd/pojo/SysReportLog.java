package com.ahcd.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class SysReportLog {
    private BigDecimal logId;

    private String templateName;

    private String tableName;

    private String userId;

    private String areaId;

    private String deparmentId;

    private BigDecimal reportType;

    private Date reportDate;

    private BigDecimal reportZq;

    private String userName;

    private String areaName;

    private String deparmentName;

    private String reportYear;

    private String reportMonth;

    public BigDecimal getLogId() {
        return logId;
    }

    public void setLogId(BigDecimal logId) {
        this.logId = logId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public String getDeparmentId() {
        return deparmentId;
    }

    public void setDeparmentId(String deparmentId) {
        this.deparmentId = deparmentId == null ? null : deparmentId.trim();
    }

    public BigDecimal getReportType() {
        return reportType;
    }

    public void setReportType(BigDecimal reportType) {
        this.reportType = reportType;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public BigDecimal getReportZq() {
        return reportZq;
    }

    public void setReportZq(BigDecimal reportZq) {
        this.reportZq = reportZq;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getDeparmentName() {
        return deparmentName;
    }

    public void setDeparmentName(String deparmentName) {
        this.deparmentName = deparmentName == null ? null : deparmentName.trim();
    }

    public String getReportYear() {
        return reportYear;
    }

    public void setReportYear(String reportYear) {
        this.reportYear = reportYear == null ? null : reportYear.trim();
    }

    public String getReportMonth() {
        return reportMonth;
    }

    public void setReportMonth(String reportMonth) {
        this.reportMonth = reportMonth == null ? null : reportMonth.trim();
    }
}
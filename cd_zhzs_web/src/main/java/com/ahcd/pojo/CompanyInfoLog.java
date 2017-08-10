package com.ahcd.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**企业信息历史表
 * */
public class CompanyInfoLog {
	/**企业信息表主键
	 * */
	private BigDecimal id;
	/**企业名称
	 * */
	private String name;
	/**法人代表
	 * */
	private String legalPerson;
	/**国标行业
	 * */
	private String idGbhy;
	/**国标行业名称
	 * */
	private String nameGbhy;
	/**是否规模以上企业 1是 0否
	 * */
	private String isAboveScale;
	/**是否招商企业 1是 0否
	 * */
	private String isCanvassBuisiness;
	/**状态 1正常 0不正常
	 * */
	private String status;
	/**企业属性
	 * */
	private String companyAttr;
	/**注册资本
	 * */
	private String registerCapital;
	/**企业类型
	 * */
	private String companyType;
	/**经营范围
	 * */
	private String dealArea;
	/**所属区域
	 * */
	private String areaId;
	/**是否删除 0未删 1已删
	 * */
	private String isDelete;
	/**登记日期
	 * */
	private String registerDate;
	/**行业类型
	 * */
	private String industryType;
	/**联络员
	 * */
	private String linkman;
	/**联络员电话
	 * */
	private String linkmanMobile;
	/**政治面貌
	 * */
	private String politicalOutlook;
	/**币种
	 * */
	private String currency;
	/**经营类别
	 * */
	private String managementCategory;
	/**管辖单位
	 * */
	private String jurisdictionUnit;
	/**主管管辖单位
	 * */
	private String mainJurisdictionUnit;
	/**(受委托)登记机关
	 * */
	private String registrationUint;
	/**主管登记机关
	 * */
	private String mainRegistrationUint;
	/**核准日期
	 * */
	private String approvalDate;
	/**联系电话
	 * */
	private String contactMobile;
	/**所属区域
	 * */
	private String areaName;
	/**关联工商表id
	 * */
	private String gszjKId;
	/**关联地税表id
	 * */
	private String dsjKId;
	/**关联国税表id
	 * */
	private String gsjKId;
	
	private String cyrs;
	/**
	 * 日期
	 * */
	private String k_date;
	/**
	 * 经营地址
	 * */
	private String jydz;
	/**
	 * 纳税人名称
	 * */
	private String nsrmc;
	/**
	 * 更新的时间
	 */
	private Date updateTime;
	/**
	 * 变更后的内容
	 */
	private String remark;
	
	
	public String getCyrs() {
		return cyrs;
	}
	public void setCyrs(String cyrs) {
		this.cyrs = cyrs;
	}
	public String getK_date() {
		return k_date;
	}
	public void setK_date(String k_date) {
		this.k_date = k_date;
	}
	public String getJydz() {
		return jydz;
	}
	public void setJydz(String jydz) {
		this.jydz = jydz;
	}
	public String getNsrmc() {
		return nsrmc;
	}
	public void setNsrmc(String nsrmc) {
		this.nsrmc = nsrmc;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLegalPerson() {
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	public String getIdGbhy() {
		return idGbhy;
	}
	public void setIdGbhy(String idGbhy) {
		this.idGbhy = idGbhy;
	}
	public String getNameGbhy() {
		return nameGbhy;
	}
	public void setNameGbhy(String nameGbhy) {
		this.nameGbhy = nameGbhy;
	}
	public String getIsAboveScale() {
		return isAboveScale;
	}
	public void setIsAboveScale(String isAboveScale) {
		this.isAboveScale = isAboveScale;
	}
	public String getIsCanvassBuisiness() {
		return isCanvassBuisiness;
	}
	public void setIsCanvassBuisiness(String isCanvassBuisiness) {
		this.isCanvassBuisiness = isCanvassBuisiness;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCompanyAttr() {
		return companyAttr;
	}
	public void setCompanyAttr(String companyAttr) {
		this.companyAttr = companyAttr;
	}
	public String getRegisterCapital() {
		return registerCapital;
	}
	public void setRegisterCapital(String registerCapital) {
		this.registerCapital = registerCapital;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getDealArea() {
		return dealArea;
	}
	public void setDealArea(String dealArea) {
		this.dealArea = dealArea;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getLinkmanMobile() {
		return linkmanMobile;
	}
	public void setLinkmanMobile(String linkmanMobile) {
		this.linkmanMobile = linkmanMobile;
	}
	public String getPoliticalOutlook() {
		return politicalOutlook;
	}
	public void setPoliticalOutlook(String politicalOutlook) {
		this.politicalOutlook = politicalOutlook;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getManagementCategory() {
		return managementCategory;
	}
	public void setManagementCategory(String managementCategory) {
		this.managementCategory = managementCategory;
	}
	public String getJurisdictionUnit() {
		return jurisdictionUnit;
	}
	public void setJurisdictionUnit(String jurisdictionUnit) {
		this.jurisdictionUnit = jurisdictionUnit;
	}
	public String getMainJurisdictionUnit() {
		return mainJurisdictionUnit;
	}
	public void setMainJurisdictionUnit(String mainJurisdictionUnit) {
		this.mainJurisdictionUnit = mainJurisdictionUnit;
	}
	public String getRegistrationUint() {
		return registrationUint;
	}
	public void setRegistrationUint(String registrationUint) {
		this.registrationUint = registrationUint;
	}
	public String getMainRegistrationUint() {
		return mainRegistrationUint;
	}
	public void setMainRegistrationUint(String mainRegistrationUint) {
		this.mainRegistrationUint = mainRegistrationUint;
	}
	public String getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getGszjKId() {
		return gszjKId;
	}
	public void setGszjKId(String gszjKId) {
		this.gszjKId = gszjKId;
	}
	public String getDsjKId() {
		return dsjKId;
	}
	public void setDsjKId(String dsjKId) {
		this.dsjKId = dsjKId;
	}
	public String getGsjKId() {
		return gsjKId;
	}
	public void setGsjKId(String gsjKId) {
		this.gsjKId = gsjKId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
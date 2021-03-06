package com.ahcd.pojo;

import java.math.BigDecimal;

import com.ahcd.common.StringUtil;

/**
 * 企业基础信息表
 */
public class CompanyInfo {
	/**
	 * 主键
	 */
	private BigDecimal id;
	/**
	 * 企业名称
	 */
	private String name;
	/**
	 * 法人代表
	 */
	private String legalPerson;
	/**
	 * 国标行业
	 */
	private String idGbhy;
	/**
	 * 国标行业名称
	 */
	private String nameGbhy;
	/**
	 * 是否规模以上企业 1是 0否
	 */
	private String isAboveScale;
	/**
	 * 是否招商企业 1是 0否
	 */
	private String isCanvassBuisiness;
	/**
	 * 企业状态
	 */
	private String status;
	/**
	 * 企业属性
	 */
	private String companyAttr;
	/**
	 * 注册资本
	 */
	private String registerCapital;
	/**
	 * 企业类型
	 */
	private String companyType;
	/**
	 * 经营范围
	 */
	private String dealArea;
	/**
	 * 所属区域
	 */
	private String areaId;
	/**
	 * 是否删除 0未删 1已删
	 */
	private String isDelete;
	/**
	 * 登记日期
	 */
	private String registerDate;
	/**
	 * 行业类型
	 */
	private String industryType;
	/**
	 * 联络员
	 */
	private String linkman;
	/**
	 * 联络员电话
	 */
	private String linkmanMobile;
	/**
	 * 政治面貌
	 */
	private String politicalOutlook;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 经营类别
	 */
	private String managementCategory;
	/**
	 * 管辖单位
	 */
	private String jurisdictionUnit;
	/**
	 * 主管管辖单位
	 */
	private String mainJurisdictionUnit;
	/**
	 * (受委托)登记机关
	 */
	private String registrationUint;
	/**
	 * 主管登记机关
	 */
	private String mainRegistrationUint;
	/**
	 * 核准日期
	 */
	private String approvalDate;
	/**
	 * 联系电话
	 */
	private String contactMobile;
	/**
	 * 所属区域
	 */
	private String areaName;
	/**
	 * 关联工商表id
	 */
	private String gszjKId;
	/**
	 * 关联地税表id
	 */
	private String dsjKId;
	/**
	 * 关联国税表id
	 */
	private String gsjKId;

	/**
	 * 数据的有效结果
	 */
	private String checkResult;
	
	/**
	 * 销售收入
	 */
	private String salesRevenue;

	/**
	 * 三年纳税总和
	 */
	private String taxAmount;

	/**
	 * 从业人员 数量
	 */
	private String practitionersNum;
	
	/**
	 * 今年税额
	 */
	private String thisYearTax;
	/**
	 * 去年税额
	 */
	private String lastYearTax;
	/**
	 *  前年税额
	 */
	private String previousYearTax;
	
	
	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
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
		if (StringUtil.isNotEmpty(registerDate)) {
			if (registerDate.length() >= 15) {
				return registerDate.substring(0, 10);
			}
		}
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

	public String getSalesRevenue() {
		return salesRevenue;
	}

	public void setSalesRevenue(String salesRevenue) {
		this.salesRevenue = salesRevenue;
	}

	public String getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getPractitionersNum() {
		return practitionersNum;
	}

	public void setPractitionersNum(String practitionersNum) {
		this.practitionersNum = practitionersNum;
	}

	public String getThisYearTax() {
		return thisYearTax;
	}

	public void setThisYearTax(String thisYearTax) {
		this.thisYearTax = thisYearTax;
	}

	public String getLastYearTax() {
		return lastYearTax;
	}

	public void setLastYearTax(String lastYearTax) {
		this.lastYearTax = lastYearTax;
	}

	public String getPreviousYearTax() {
		return previousYearTax;
	}

	public void setPreviousYearTax(String previousYearTax) {
		this.previousYearTax = previousYearTax;
	}

}
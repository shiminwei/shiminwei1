package com.ahcd.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SysReportUser {
    private BigDecimal userId;

    private String userName;

    private String userCode;

    private String userPwd;

    private Date createTime;

    private Date lastLoginTime;

    private BigDecimal hasReport=new BigDecimal(1);

    private BigDecimal hasWeb=new BigDecimal(0);

    private BigDecimal status=new BigDecimal(1);

    private String bussinessConcact;

    private String bussinessConcactPhone;

    private String techConcact;

    private String techConcactPhone;

    private String email;

    private String address;

    private String leader;
    
    private BigDecimal isDelete=new BigDecimal(0);
    
    private String departmentId;
    
    private String departmentName;
    
    private String departmentCode;
    
    private String areaId;
    
    private String areaCode;
    
    private String areaName;
    
    public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public BigDecimal getHasReport() {
        return hasReport;
    }

    public void setHasReport(BigDecimal hasReport) {
        this.hasReport = hasReport;
    }

    public BigDecimal getHasWeb() {
        return hasWeb;
    }

    public void setHasWeb(BigDecimal hasWeb) {
        this.hasWeb = hasWeb;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public String getBussinessConcact() {
        return bussinessConcact;
    }

    public void setBussinessConcact(String bussinessConcact) {
        this.bussinessConcact = bussinessConcact == null ? null : bussinessConcact.trim();
    }

    public String getBussinessConcactPhone() {
        return bussinessConcactPhone;
    }

    public void setBussinessConcactPhone(String bussinessConcactPhone) {
        this.bussinessConcactPhone = bussinessConcactPhone == null ? null : bussinessConcactPhone.trim();
    }

    public String getTechConcact() {
        return techConcact;
    }

    public void setTechConcact(String techConcact) {
        this.techConcact = techConcact == null ? null : techConcact.trim();
    }

    public String getTechConcactPhone() {
        return techConcactPhone;
    }

    public void setTechConcactPhone(String techConcactPhone) {
        this.techConcactPhone = techConcactPhone == null ? null : techConcactPhone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader == null ? null : leader.trim();
    }

	public BigDecimal getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(BigDecimal isDelete) {
		this.isDelete = isDelete;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
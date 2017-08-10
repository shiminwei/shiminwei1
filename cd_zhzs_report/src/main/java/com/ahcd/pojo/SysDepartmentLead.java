package com.ahcd.pojo;

import java.math.BigDecimal;

public class SysDepartmentLead {
    private BigDecimal id;

    private String userId;

    private String leadId;

    private BigDecimal status;

    private BigDecimal isDelete;
    
	private String userName;
	
	private String areaName;
	
	private String departmentName;
	
	private String departmentId;
    
    public BigDecimal getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(BigDecimal isDelete) {
		this.isDelete = isDelete;
		
	}
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId == null ? null : leadId.trim();
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
    
	
    
}
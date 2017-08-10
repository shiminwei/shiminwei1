package com.ahcd.pojo;

import java.math.BigDecimal;

public class SysDepartmentInfo {
    private String departmentId;

    private String departmentName;

    private String parentDepartmentId;
    
    private String parentAreaId;

    private BigDecimal departmentCode;

    private String departmentAreaId;
    
    public String getParentAreaId() {
		return parentAreaId;
	}

	public void setParentAreaId(String parentAreaId) {
		this.parentAreaId = parentAreaId;
	}

	//0不删除用户，1删除
    private BigDecimal isDelete=new BigDecimal(0);

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getParentDepartmentId() {
        return parentDepartmentId;
    }

    public void setParentDepartmentId(String parentDepartmentId) {
        this.parentDepartmentId = parentDepartmentId == null ? null : parentDepartmentId.trim();
    }

    public BigDecimal getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(BigDecimal departmentCode) {
        this.departmentCode = departmentCode == null ? null : departmentCode;
    }

    public String getDepartmentAreaId() {
        return departmentAreaId;
    }

    public void setDepartmentAreaId(String departmentAreaId) {
        this.departmentAreaId = departmentAreaId == null ? null : departmentAreaId.trim();
    }
    

	public BigDecimal getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(BigDecimal isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "SysDepartmentInfo [departmentId=" + departmentId + ", departmentName=" + departmentName
				+ ", parentDepartmentId=" + parentDepartmentId + ", departmentCode=" + departmentCode
				+ ", departmentAreaId=" + departmentAreaId + ", isDelete=" + isDelete + "]";
	}
}
package com.ahcd.pojo;

import java.math.BigDecimal;
import java.util.List;

public class SysAreaInfo {
    private String areaId;

    private String areaName;

    private BigDecimal areaCode;

    private String parentAreaId;
    
    private List<SysDepartmentInfo> departmentList; 
    private String idDelete;//是否删除
    
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public BigDecimal getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(BigDecimal areaCode) {
        this.areaCode = areaCode == null ? null : areaCode;
    }

    public String getParentAreaId() {
        return parentAreaId;
    }

    public void setParentAreaId(String parentAreaId) {
        this.parentAreaId = parentAreaId == null ? null : parentAreaId.trim();
    }

	public List<SysDepartmentInfo> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<SysDepartmentInfo> departmentList) {
		this.departmentList = departmentList;
	}


	public String getIdDelete() {
		return idDelete;
	}

	public void setIdDelete(String idDelete) {
		this.idDelete = idDelete;
	}

	@Override
	public String toString() {
		return areaName;
	}
    
    
}
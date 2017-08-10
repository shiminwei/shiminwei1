package com.ahcd.pojo;

import java.util.List;

public class SysAreaInfo {
    private String areaId;

    private String areaName;

    private String areaCode;

    private String parentAreaId;
    
    private List<SysDepartmentInfo> departmentList;
    
    
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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
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


	@Override
	public String toString() {
		return areaName;
	}
    
    
}
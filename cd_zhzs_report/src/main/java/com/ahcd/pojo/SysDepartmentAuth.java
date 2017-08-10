package com.ahcd.pojo;

public class SysDepartmentAuth {
    private String departmentId;

    private String subDepartmentId;
   
	public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    public String getSubDepartmentId() {
        return subDepartmentId;
    }

    public void setSubDepartmentId(String subDepartmentId) {
        this.subDepartmentId = subDepartmentId == null ? null : subDepartmentId.trim();
    }
}
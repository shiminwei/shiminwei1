package com.ahcd.pojo;


public class SysDepartmentTemplate {
    private String departmentId;

    private String templateName;

    private String departmentName;
    
    private int reportPeroid;

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

	public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

	public int getReportPeroid() {
		return reportPeroid;
	}

	public void setReportPeroid(int reportPeroid) {
		this.reportPeroid = reportPeroid;
	}
}
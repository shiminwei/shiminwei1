package com.ahcd.pojo;

import java.math.BigDecimal;

public class SysRole {
    private BigDecimal roleId;

    private String roleName;

    private Long status;

    private String roleDesc;

    private Integer isHaveRole=0;
    
    
    public BigDecimal getRoleId() {
        return roleId;
    }

    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }

	public Integer getIsHaveRole() {
		return isHaveRole;
	}

	public void setIsHaveRole(Integer isHaveRole) {
		this.isHaveRole = isHaveRole;
	}
    
    
}
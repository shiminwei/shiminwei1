package com.ahcd.pojo;

import java.math.BigDecimal;
import java.util.List;

/** 
* @author 作者 : fei yang
* @version 创建时间：2016年10月23日 上午11:47:14 
* 类说明 
*/

public class SysWebMenu {
	private String menuId;
	private String code;
	private String name;
	private BigDecimal menuLevel;// 1，一级菜单 2，二级菜单 3，三级菜单
	private BigDecimal menuType;// 1，无功能 2，普通菜单 3，url链接地址
	private String parentCode;
	private String parentName;
	private String functionId;
	private BigDecimal orderNumber;
	private BigDecimal roleId;
	private Integer isHave=0;//是否拥有权限
	private Integer isDefaultIndex=0;//是否首页
	
	public Integer getIsDefaultIndex() {
		return isDefaultIndex;
	}
	public void setIsDefaultIndex(Integer isDefaultIndex) {
		this.isDefaultIndex = isDefaultIndex;
	}
	public BigDecimal getRoleId() {
		return roleId;
	}
	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId == null ? null : menuId.trim();
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public BigDecimal getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(BigDecimal menuLevel) {
		this.menuLevel = menuLevel;
	}
	public BigDecimal getMenuType() {
		return menuType;
	}
	public void setMenuType(BigDecimal menuType) {
		this.menuType = menuType;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getFunctionId() {
		return functionId;
	}
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	public Integer getIsHave() {
		return isHave;
	}
	public void setIsHave(Integer isHave) {
		this.isHave = isHave;
	}
	
	private List<SysWebMenu> childrenMenuList;

	public List<SysWebMenu> getChildrenMenuList() {
		return childrenMenuList;
	}
	public void setChildrenMenuList(List<SysWebMenu> childrenMenuList) {
		this.childrenMenuList = childrenMenuList;
	}
	
	

}

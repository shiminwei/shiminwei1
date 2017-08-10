package com.ahcd.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ahcd.common.BigDecimalUtil;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysRole;
import com.ahcd.pojo.SysWebMenu;
import com.ahcd.pojo.SysWebRoleMenu;
import com.ahcd.service.ISysWebMenuService;
import com.ahcd.service.SysRoleService;
import com.ahcd.service.SysWebRoleMenuService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * @author : fei yang
 * @version ：2016年10月25日 下午3:21:49
 */
@Controller
@RequestMapping("/admin/menuRole")
public class SysWebMenuRoleController {

	@Resource
	private SysWebRoleMenuService sysWebRoleMenuService;
	@Resource
	private ISysWebMenuService sysWebMenuService;
	@Resource
	private SysRoleService sysRoleService;

	
	
	
	
	/**
	 * 
	 * 功能说明 : 角色管理列表
	 * 
	 * @author : fei yang
	 * @version ：2016年10月25日 下午1:27:14
	 * @param request
	 * @param model
	 * @param page
	 * @param bean
	 * @return
	 */
	@RequestMapping("/list")
	public String getList(HttpServletRequest request, Model model, Page<SysRole> page, SysRole bean) {
		page = sysRoleService.getPageList(page, bean);
		model.addAttribute("pageList", page);
		model.addAttribute("bean", bean);
		return "admin/menuRole/list";
	}

	
	/**
	 * 
	 * 功能说明 :部门对应展现菜单管理
	 * 
	 * @author : fei yang
	 * @version ：2016年10月24日 下午4:44:01
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/show")
	public String toWebMenu(HttpServletRequest request, Model model, String roleId) {
		SysWebMenu bean = new SysWebMenu();
		List<SysWebMenu> menuList = sysWebMenuService.getAllByType(bean);// 所有菜单列表
		List<SysWebRoleMenu> roleMenuList = new ArrayList<SysWebRoleMenu>();// 角色拥有的菜单列表
		SysWebRoleMenu record = new SysWebRoleMenu();
		if (!StringUtil.isBlank(roleId)) {
			record.setRoleId(new BigDecimal(roleId));
			roleMenuList = sysWebRoleMenuService.getRoleList(record);
		}
		
		StringBuffer oldMenuIds=new StringBuffer("");
		for (int i = 0; i < roleMenuList.size(); i++) {
			oldMenuIds.append(roleMenuList.get(i).getMenuId()+",");
		}
		
		List<SysWebMenu> oneList = new ArrayList<SysWebMenu>();
		List<SysWebMenu> twoList = new ArrayList<SysWebMenu>();
		List<SysWebMenu> threeList = new ArrayList<SysWebMenu>();
		for (int i = 0; i < menuList.size(); i++) {
			for (int j = 0; j < roleMenuList.size(); j++) {
				if (BigDecimalUtil.isSame(roleMenuList.get(j).getMenuId(), menuList.get(i).getMenuId())) {
					menuList.get(i).setIsHave(1);
				}
			}
			if (BigDecimalUtil.isSame(menuList.get(i).getMenuLevel(), 1)) {
				oneList.add(menuList.get(i));
			} else if (BigDecimalUtil.isSame(menuList.get(i).getMenuLevel(), 2)) {
				twoList.add(menuList.get(i));
			} else if (BigDecimalUtil.isSame(menuList.get(i).getMenuLevel(), 3)) {
				threeList.add(menuList.get(i));
			}
		}
		model.addAttribute("oneList", oneList);
		model.addAttribute("twoList", twoList);
		model.addAttribute("threeList", threeList);
		model.addAttribute("roleId", roleId);
		model.addAttribute("oldMenuIds", oldMenuIds);
		return "admin/menuRole/show";
	}

	@ResponseBody
	@RequestMapping("saveOrUpdate")
	public OpreateResult toSaveOrUpdate(HttpServletRequest request, HttpServletResponse response, String roleId,String menuId) {
		if(menuId==null || StringUtil.isBlank(menuId)){
			sysWebRoleMenuService.deleteByPrimaryKey(new BigDecimal(roleId));
		}else{
		sysWebRoleMenuService.saveOrUpdate(roleId, menuId);
		}
		OpreateResult opreateResult = new OpreateResult();
		opreateResult = new OpreateResult();
		opreateResult.setStatusCode("200");
		opreateResult.setNavTabId("menuRoleList");
		opreateResult.setMessage("操作成功");
		opreateResult.setCallbackType("closeCurrent");
		opreateResult.setForwardUrl("admin/menuRole/list");
		return opreateResult;
	}

}

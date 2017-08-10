package com.ahcd.controller.admin;

import java.io.IOException;
import java.math.BigDecimal;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ahcd.common.OpreateResult;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysRole;
import com.ahcd.service.SysRoleService;

/**
 * @author : fei yang
 * @version ：2016年10月25日 上午10:36:50
 */
@Controller
@RequestMapping("/admin/role")
public class SysRoleController {
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
		return "admin/role/list";
	}

	/**
	 * 
	 * 类说明:删除和批量删除
	 * 
	 * @author 作者 : fei yang
	 * @version 创建时间:2016年10月23日 下午1:38:58
	 * @param request
	 * @param model
	 * @param ids
	 * @return:
	 */
	@ResponseBody
	@RequestMapping("toDelete")
	public OpreateResult toDelete(HttpServletRequest request, Model model, BigDecimal roleId) {
		System.out.println(roleId);
		int flag = sysRoleService.deleteByPrimaryKey(roleId);
		OpreateResult result = new OpreateResult();
		if (flag > 0) {
			result = new OpreateResult("200", "删除成功", "", "", "");
		} else {
			result = new OpreateResult("300", "删除失败", "", "", "");
		}
		return result;
	}

	/**
	 * 
	 * 类说明:跳转到新增获取修改页面
	 * 
	 * @author 作者 : fei yang
	 * @version 创建时间:2016年10月23日 下午1:54:30
	 * @param request
	 * @param type
	 * @param model
	 * @param menuId
	 * @param menuType
	 * @return:
	 */
	@RequestMapping("toSaveOrUpdate")
	public String toSaveOrUpdate(HttpServletRequest request, Integer type, Model model, BigDecimal roleId) {

		if (type == 2) {
			SysRole bean = sysRoleService.selectByPrimaryKey(roleId);
			model.addAttribute("bean", bean);
		}
		model.addAttribute("type", type);
		return "admin/role/edit";
	}

	/**
	 * 
	 * 类说明: 新增或者修改
	 * 
	 * @author 作者 : fei yang
	 * @version 创建时间:2016年10月23日 下午2:06:04
	 * @param request
	 * @param model
	 * @param bean
	 * @return:
	 */
	@ResponseBody
	@RequestMapping("saveOrUpdate")
	public OpreateResult saveOrUpdate(HttpServletRequest request, SysRole bean) {
		int re = sysRoleService.saveOrUpdateBean(bean);
		OpreateResult opreateResult = new OpreateResult();
		if (re > 0) {
			opreateResult = new OpreateResult();
			opreateResult.setStatusCode("200");
			opreateResult.setNavTabId("roleList");
			opreateResult.setMessage("操作成功");
			opreateResult.setCallbackType("forward");
			opreateResult.setForwardUrl("role/list");
		} else {
			opreateResult = new OpreateResult();
			opreateResult.setStatusCode("300");
			opreateResult.setNavTabId("roleList");
			opreateResult.setMessage("操作失败");
			opreateResult.setCallbackType("forward");
		}
		return opreateResult;

	}
	/**
	 * 功能说明      添加角色重名判断 
	 */
	@RequestMapping("/checkSameRolename")
	public void checkSameRolename(HttpServletRequest request, HttpServletResponse response,BigDecimal roleId ,String roleName) {
		SysRole sysRole = new SysRole();
		sysRole.setRoleId(roleId);
		sysRole.setRoleName(roleName);
		int rel = sysRoleService.getSameRolenameCount(sysRole);
		try {
			String ret = "false";
			if (rel >= 1) {
				ret = "true";
			}
			response.getWriter().print(ret);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

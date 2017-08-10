package com.ahcd.controller.admin;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.common.XmlUtils;
import com.ahcd.pojo.ConfigBean;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysWebMenu;
import com.ahcd.service.ConfigService;
import com.ahcd.service.impl.SysWebMenuServiceImpl;

/**
 * @author 作者 : fei yang
 * @version 创建时间：2016年10月23日 下午12:26:21 类说明
 */

@Controller
@RequestMapping("/admin/webMenu")
public class WebMenuController {
	@Resource
	private SysWebMenuServiceImpl sysWebMenuService;
	@Resource
	private ConfigService configService;

	@RequestMapping("/list")
	public String getList(HttpServletRequest request, Model model, Page<SysWebMenu> page, SysWebMenu bean) {
		page = sysWebMenuService.getAllByBean1(page, bean);
		model.addAttribute("pageList", page);
		model.addAttribute("bean", bean);
		return "admin/menu/list";
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
	public OpreateResult toDelete(HttpServletRequest request, Model model, String ids) {
//		System.out.println(ids);
		boolean flag = sysWebMenuService.deleteByPrimaryKey(ids);
		OpreateResult result = new OpreateResult();
		if (flag) {
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
	 * @param MenuLevel
	 * @return:
	 */
	@RequestMapping("toSaveOrUpdate")
	public String toSaveOrUpdate(HttpServletRequest request, Integer type, Model model, SysWebMenu oldBean) {
		if (type == 2) {
			SysWebMenu bean = sysWebMenuService.getBeanById(oldBean.getMenuId());
			model.addAttribute("bean", bean);
			if (!StringUtil.isBlank(bean.getFunctionId() )) {
//				String xmlpPath = configService.getXmlPath();
//				String filePath = xmlpPath + "/con" + bean.getFunctionId() + ".xml";
			//	ConfigBean configBean= XmlUtils.getBean(filePath, ConfigBean.class);
				ConfigBean configBean = configService.getConfigBeanById(bean.getFunctionId());
				model.addAttribute("configBean", configBean);
			}
		}
		model.addAttribute("type", type);
		model.addAttribute("MenuLevel", oldBean.getMenuLevel());
		request.setAttribute("MenuLevel", oldBean.getMenuLevel());
		model.addAttribute("oldBean", oldBean);
		return "admin/menu/edit";
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
	public OpreateResult saveOrUpdate(HttpServletRequest request, SysWebMenu bean,BigDecimal menuLevel,Model model) {
		String url = request.getParameter("url");
		if(bean.getMenuType()==null){
			bean.setMenuType(new BigDecimal(1));
		}
		if(bean.getMenuType().intValue()==3){
			bean.setFunctionId(url);
		}
		bean.setMenuLevel(menuLevel);
		int re = sysWebMenuService.saveOrUpdateBean(bean);
		OpreateResult opreateResult = new OpreateResult();
		model.addAttribute("bean", bean);
		if (re > 0) {
			opreateResult = new OpreateResult("200", "操作成功!", "webMnuList", "forward", "webMenu/list?MenuLevel=" + bean.getMenuLevel() + "&parentCode="
					+ bean.getParentCode() + "&parentName=" + bean.getParentName());
			return opreateResult;
		} else {
			opreateResult = new OpreateResult();
			opreateResult.setStatusCode("300");
			opreateResult.setNavTabId("webMnuList");
			opreateResult.setMessage("操作失败");
			opreateResult.setCallbackType("closeCurrent");
		}
		return opreateResult;
	}

	public SysWebMenu reBean(SysWebMenu bean) {
		if (bean.getMenuLevel().compareTo(new BigDecimal(0)) == 0) {
			bean.setMenuLevel(new BigDecimal(1));
		} else if (bean.getMenuLevel().compareTo(new BigDecimal(1)) == 0) {
			bean.setMenuLevel(new BigDecimal(2));
		}else if (bean.getMenuLevel().compareTo(new BigDecimal(2)) == 0) {
			bean.setMenuLevel(new BigDecimal(3));
		}
		return bean;
	}

	/**
	 * 上移下移
	 * @param feiyue yang
	 * @param orderNumber
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("/upOrDown")
	public OpreateResult upOrDown(HttpServletRequest request,
			@RequestParam(value ="menuId", required = false) String menuId,
			@RequestParam(value ="needMobeId", required = false) String needMobeId,
			@RequestParam(value ="type", required = false) String type){
		OpreateResult opreateResult = new OpreateResult();
		if(type.equals("1")){
			opreateResult = new OpreateResult("300", "已经是第一个了!", "", "", "");
			return opreateResult;
		}else if(type.equals("2")){
			opreateResult = new OpreateResult("300", "已经是最后一个了!", "", "", "");
			return opreateResult;
		}else{
		SysWebMenu bean = sysWebMenuService.getBeanById(menuId);
		SysWebMenu needMobeBean = sysWebMenuService.getBeanById(needMobeId);
		
		BigDecimal menuIdOrderNumber = needMobeBean.getOrderNumber();
		BigDecimal needmenuIdOrderNumber = bean.getOrderNumber();
		if(menuIdOrderNumber == null || StringUtil.isBlank(menuIdOrderNumber) ||
				needmenuIdOrderNumber == null || StringUtil.isBlank(needmenuIdOrderNumber)){
			opreateResult = new OpreateResult("300", "缺少序号!", "", "", "");
			return opreateResult;
		}
		bean.setOrderNumber(menuIdOrderNumber);
		needMobeBean.setOrderNumber(needmenuIdOrderNumber);
		
		sysWebMenuService.updateByPrimaryKeySelective(bean);
		sysWebMenuService.updateByPrimaryKeySelective(needMobeBean);
		opreateResult = new OpreateResult("200", "操作成功!", "", "", "");
		return opreateResult;
		}
	}
	
	
	/**
	 * 
	 * 功能说明 :子菜单管理
	 * 
	 * @author : fei yang
	 * @version ：2016年10月24日 下午2:43:15
	 * @param request
	 * @param model
	 * @param page
	 * @param bean
	 * @return
	 */
	@RequestMapping("/chileList")
	public String getChileList(HttpServletRequest request, Model model, Page<SysWebMenu> page, SysWebMenu bean) {
		bean = reBean(bean);
		page = sysWebMenuService.getAllByBean(page, bean);
		model.addAttribute("pageList", page);
		model.addAttribute("bean", bean);
		return "admin/menu/list";
	}
}

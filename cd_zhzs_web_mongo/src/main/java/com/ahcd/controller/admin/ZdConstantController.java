package com.ahcd.controller.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.ZdConstant;
import com.ahcd.service.impl.ZdConstantServiceImpl;

/**
 * @author : fei yang
 * @version ：2016年11月24日 上午11:44:02
 */
@Controller
@RequestMapping("admin/zdConstant")
public class ZdConstantController {

	@Resource
	private ZdConstantServiceImpl zdConstantService;

	/**
	 * 
	 * 功能说明 :码表
	 * 
	 * @author : fei yang
	 * @version ：2016年11月24日 上午11:45:50
	 * @param request
	 * @param model
	 * @param page
	 * @param bean
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Page<ZdConstant> page, ZdConstant bean) {
		page = zdConstantService.selectTypePage(page, bean);
		request.setAttribute("pageList", page);
		request.setAttribute("bean", bean);
		return "admin/zdConstant/list";
	}

	/**
	 * 
	 * 功能说明 :子码表管理
	 * 
	 * @author : fei yang
	 * @version ：2016年11月24日 下午3:37:01
	 * @param request
	 * @param model
	 * @param page
	 * @param bean
	 * @return
	 */
	@RequestMapping("/chileList")
	public String chileList(HttpServletRequest request, Model model, Page<ZdConstant> page, ZdConstant bean) {
		page = zdConstantService.selectChileListPage(page, bean);
		request.setAttribute("pageList", page);
		request.setAttribute("bean", bean);
		return "admin/zdConstant/chileList";
	}

	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/toDelete")
	public OpreateResult toDeleteDepartment(HttpServletRequest request, String ids, String type) {
		OpreateResult opreateResult = new OpreateResult("200", "删除成功", "", "", "");
		int re = zdConstantService.deleteByIdsOrType(ids, type);
		if (re <= 0) {
			opreateResult = new OpreateResult("300", "删除失败", "", "", "");
		}
		return opreateResult;
	}

	/**
	 * 
	 * 功能说明 :新增或者修改跳转
	 * 
	 * @author : fei yang
	 * @version ：2016年11月24日 下午4:45:57
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("toSaveOrUpdate")
	public String toSaveOrUpdate(HttpServletRequest request, Model model, String constantId, Integer istype,
			ZdConstant bean) {
		if (istype == 3) {// 修改
			bean = zdConstantService.selectByPrimaryKey(new BigDecimal(constantId));
		}
		model.addAttribute("istype", istype);
		model.addAttribute("bean", bean);
		return "admin/zdConstant/edit";
	}

	/**
	 * 
	 * 功能说明 : 新增或者修改
	 * 
	 * @author : fei yang
	 * @version ：2016年11月24日 下午4:52:12
	 * @param request
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveOrUpdate")
	public OpreateResult SaveOrUpdate(HttpServletRequest request, ZdConstant bean, Integer isType) {
		OpreateResult opreateResult = new OpreateResult();
		if (isType == 1) {
			String navTabId = "zdConstantManage";
		int maxOrderNumber = 1;
		//maxOrderNumber = zdConstantService.selectMaxOrderNumbertByType(bean.getType())+1;
		
		bean.setOrderNumber(new BigDecimal(maxOrderNumber));
		int re = zdConstantService.saveOrUpdate(bean);
		if(re>0){
		opreateResult = new OpreateResult("200", "操作成功", navTabId, "closeCurrent", "");
		}
		if (re <= 0) {
			opreateResult = new OpreateResult("300", "操作失败", navTabId, "closeCurrent", "");
		}
		return opreateResult;
		}else{
			String navTabId = "chileList";
			int	maxOrderNumber = zdConstantService.selectMaxOrderNumbertByType(bean.getType())+1;
			bean.setOrderNumber(new BigDecimal(maxOrderNumber));
			int re = zdConstantService.saveOrUpdate(bean);
			if(re>0){
			opreateResult = new OpreateResult("200", "操作成功", navTabId, "closeCurrent", "");
			}
			if (re <= 0) {
				opreateResult = new OpreateResult("300", "操作失败", navTabId, "closeCurrent", "");
			}
			return opreateResult;
		}
	}
	
	/**
	 * 码表子页面上下移动
	 * @param feiyue yang
	 * @param type
	 * @param name
	 * @param chooseType
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
		ZdConstant bean = zdConstantService.selectByPrimaryKey(new BigDecimal(menuId));
		ZdConstant needMobeBean = zdConstantService.selectByPrimaryKey(new BigDecimal(needMobeId));
		
		BigDecimal menuIdOrderNumber = needMobeBean.getOrderNumber();
		BigDecimal needmenuIdOrderNumber = bean.getOrderNumber();
		bean.setOrderNumber(menuIdOrderNumber);
		needMobeBean.setOrderNumber(needmenuIdOrderNumber);
		
		zdConstantService.updateByPrimaryKeySelective(bean);
		zdConstantService.updateByPrimaryKeySelective(needMobeBean);
		opreateResult = new OpreateResult("200", "操作成功!", "", "", "");
		return opreateResult;
		}
	}
}















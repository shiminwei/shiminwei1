package com.ahcd.controller.admin;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ahcd.common.Constant;
import com.ahcd.common.PasswordUtil;
import com.ahcd.common.StringUtil;
import com.ahcd.dao.SysUserRoleMapper;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.pojo.SysWebMenu;
import com.ahcd.service.ISysWebMenuService;
import com.ahcd.service.IUserService;
import com.ahcd.service.SysUserRoleService;


@Controller  
@RequestMapping("/")  
public class IndexController {
	 @Resource  
	 private IUserService userService;  
	 
	 @Resource  
	 private ISysWebMenuService sysWebMenuService; 
	 
	 @Resource
	private SysUserRoleService sysUserRoleService;

	@RequestMapping("/")  
    public String toLogin(HttpServletRequest request,@ModelAttribute("msg") String msg,@ModelAttribute("loginCode") String loginCode){  
		request.setAttribute("msg",msg);
		request.setAttribute("loginCode",loginCode);
		return "../../login";
	}
	@RequestMapping("/login")  
    public String login(HttpServletRequest request,Model model,HttpSession session,RedirectAttributes attr){  
		String loginCode=request.getParameter("loginCode");
		String loginPwd=request.getParameter("loginPwd");
		if(StringUtil.isBlank(loginCode) || StringUtil.isBlank(loginPwd)){
			//帐号或者密码不能为空
			attr.addFlashAttribute("msg", "帐号或者密码不能为空");
			return "redirect:/";
		}
		SysReportUser u=userService.selectByLoginCodeForWeb(loginCode);
		if(u!=null && PasswordUtil.verify(loginPwd, u.getUserPwd())){
			//在Session里保存信息  
	        session.setAttribute(Constant.SESSION_USER, u);  
			return "redirect:/index";
		}else{
			//帐号或者密码不对
			attr.addFlashAttribute("msg", "帐号或密码错误，请重新输入");
			attr.addFlashAttribute("loginCode", loginCode);
			return "redirect:/";
		}
	}
	@RequestMapping("/index")  
    public String index(HttpServletRequest request,Model model){  
		SysReportUser user= (SysReportUser) request.getSession().getAttribute(Constant.SESSION_USER);
		 List<SysWebMenu> menuListNew=sysWebMenuService.getWebMenuByUserIDNew(user.getUserId());
		 //SysWebMenu indexMenu=sysWebMenuService.getDefaultIndexMenu();
		 model.addAttribute("menuListNew", menuListNew);
		 //model.addAttribute("indexMenu", indexMenu);
//		 List<SysWebMenu> menuList=sysWebMenuService.getWebMenuByUserID(String.valueOf(user.getUserId()));
//		 List<SysWebMenu> shouYeList=new ArrayList<SysWebMenu>();
//		 List<SysWebMenu> shouYeOneList=new ArrayList<SysWebMenu>();
//		 for (int i = 0; i < menuList.size(); i++) {
//			 if (BigDecimalUtil.isSame(menuList.get(i).getMenuLevel(), 1)&&menuList.get(i).getName().equals("主页")) {
//				 shouYeList.add(menuList.get(i));
//				 break;
//			}
//		 }
//		 for (int i = 0; i < menuList.size(); i++) {
//			 if (!StringUtil.isBlank(menuList.get(i).getParentCode())) {
//				 for (int j = 0; j < shouYeList.size(); j++) {
//					 if (menuList.get(i).getParentCode().equals(shouYeList.get(j).getCode())) {
//						 shouYeOneList.add(menuList.get(i));
//					 }
//				 }
//			}		 
//		 }
//		 List<SysWebMenu> oneList=new ArrayList<SysWebMenu>();
//		 List<SysWebMenu> twoList=new ArrayList<SysWebMenu>();
//		 List<SysWebMenu> threeList=new ArrayList<SysWebMenu>();
//		 List<SysWebMenu> zhuYeList=new ArrayList<SysWebMenu>();
//		 for (int i = 0; i < menuList.size(); i++) {
//			 if(menuList.get(i).getMenuLevel().compareTo(new BigDecimal(1))==0&&!menuList.get(i).getName().equals("主页")){
//				 oneList.add(menuList.get(i));
//			 }else if(menuList.get(i).getMenuLevel().compareTo(new BigDecimal(2))==0){
//				 twoList.add(menuList.get(i));
//			 }else if(menuList.get(i).getMenuLevel().compareTo(new BigDecimal(3))==0){
//				 threeList.add(menuList.get(i));
//			 }else if(menuList.get(i).getName().equals("主页")||menuList.get(i).getParentName().equals("主页")){
//				 zhuYeList.add(menuList.get(i));
//			 }
//		 }
//		 model.addAttribute("oneList", oneList);
//		 model.addAttribute("twoList", twoList);
//		 model.addAttribute("threeList", threeList);
//		 model.addAttribute("zhuYeList", zhuYeList);
//		 model.addAttribute("shouYeOneList", shouYeOneList);
		return "web/index/index";
	}
	@RequestMapping("/logout")  
    public String logout(HttpServletRequest request,Model model,HttpSession session,RedirectAttributes attr){  
		session.removeAttribute(Constant.SESSION_USER);
		return "redirect:/";
	}
	
	@RequestMapping("/toLogin")  
    public String toLogin(HttpServletRequest request,Model model,HttpSession session,RedirectAttributes attr){  
		return "../../toLogin";
	}
}

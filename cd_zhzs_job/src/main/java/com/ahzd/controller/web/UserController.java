package com.ahzd.controller.web;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.Page;
import com.ahzd.pojo.SysUser;
import com.ahzd.service.SysUserMongoService;

@Controller  
@RequestMapping("/web/user")
public class UserController {
	@Resource
	private SysUserMongoService sysUserMongoService;
	
	
	/**
	 * 跳转到用户列表
	 * @author feiyue yang
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String getUserList(HttpServletRequest request, Model model, Page<SysUser> page,
			@RequestParam(value ="pageNum", required = false) Integer pageNum ,@RequestParam(value ="numPerPage", required = false) Integer numPerPage,SysUser bean){
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		page = sysUserMongoService.getUserPage(page,bean);
		request.setAttribute("queryBean", bean);
		request.setAttribute("pageList", page);
		return "web/user/list";
	}
	
	/**
	 * 跳转到新增页面
	 * @author feiyue yang
	 * @param request
	 * @return
	 */
	@RequestMapping("/toaddOrEdit")
	public String toadd(HttpServletRequest request,String _id) {
		SysUser sysUser = sysUserMongoService.findById(_id);
		request.setAttribute("SysUser", sysUser);
		return "web/user/edit";
	}
	
	/**
	 * 新增或修改
	 * @author feiyue yang
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrEdit")
	public OpreateResult add(HttpServletRequest request,@ModelAttribute("sysUser")SysUser sysUser,String _id) {
		if(_id == null || StringUtil.isBlank(_id)){
			return sysUserMongoService.save(sysUser);
		}else{
			sysUser.set_id(_id);
			return sysUserMongoService.updateUserInfo(sysUser);
		}
	}
	
	/**
	 * 删除
	 * @author feiyue yang
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public OpreateResult delete(HttpServletRequest request,String _id) {
		SysUser sysUser = sysUserMongoService.findById(_id);
		return sysUserMongoService.delete(sysUser);
	}
}
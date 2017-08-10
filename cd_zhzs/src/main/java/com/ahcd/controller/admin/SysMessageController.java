package com.ahcd.controller.admin;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ahcd.common.Constant;
import com.ahcd.common.DateUtil;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysMessage;
import com.ahcd.pojo.SysMessageLead;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.impl.SysMessageServiceImpl;

/**
 * 说明 :前置机消息
 * @author : chenxt
 * @version ：2017年02月28日 
 */
@Controller
@RequestMapping("/admin/message")
public class SysMessageController {
	@Resource
	private SysMessageServiceImpl sysMessageService;
	/**
	 * 功能说明 :查询前置机消息列表
	 * @author : chenxt
	 * @version ：2017-02-28
	 * @param request
	 * @param model
	 * @param page
	 * @param bean
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Page<SysMessage> page, SysMessage bean) {
		page.setQueryBean(bean);
		Page<SysMessage> pageList=sysMessageService.getSysMessagePage(page);
		request.setAttribute("pageList", pageList);
		request.setAttribute("bean", bean);
		request.setAttribute("statecType", StatecType());
		return "admin/message/list";
	}

	/**
	 * 功能说明 :跳转新增/编辑消息
	 * @author : chenxt
	 * @version ：2017-02-28
	 * @param request
	 * @param messageId
	 * @param bean
	 * @return
	 */
	@RequestMapping("/toAddOrEdit")
	public String toAddOrEdit(HttpServletRequest request, String messageId,SysMessage bean) {
		if(!StringUtil.isBlank(messageId)){
			bean = sysMessageService.getSysMessageById(messageId);
		}else{
			SysReportUser user = (SysReportUser) request.getSession().getAttribute(Constant.SESSION_USER);
			bean.setUserId(user.getUserId());
			bean.setUserName(user.getUserName());
			bean.setCreateTime(new Date());
		}
		bean.setCreateTimeStr(DateUtil.date2Str("yyyy-MM-dd", bean.getCreateTime()));
		request.setAttribute("bean", bean);
		return "admin/message/edit";
	}

	/**
	 * 功能说明 :新增/修改
	 * @author : chenxt
	 * @version ：2017-02-28
	 * @param request
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrEdit")
	public OpreateResult addOrEdit(HttpServletRequest request, SysMessage bean) {
		String navTabId = "sysMessageManage";
		OpreateResult opreateResult = new OpreateResult("200", "操作成功", navTabId, "closeCurrent", "");
		int re = -1;
		if(StringUtil.isBlank(bean.getMessageId())){
			bean.setCreateTime(new Date());
			re = sysMessageService.insertSysMessage(bean);
		}else{
			re = sysMessageService.updateSysMessage(bean);
		}	
		if (re <= 0) {
			opreateResult = new OpreateResult("300", "操作失败", navTabId, "closeCurrent", "");
		}
		return opreateResult;
	}

	/**
	 * 功能说明 :删除
	 * @author : chenxt
	 * @version ：2017-02-28
	 * @param request
	 * @param messageId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/toDelete")
	public OpreateResult toDelete(HttpServletRequest request, String messageId) {
		OpreateResult opreateResult = new OpreateResult("200", "删除成功", "", "", "");
		int re = sysMessageService.deleteByMessageId(messageId);
		if (re <= 0) {
			opreateResult = new OpreateResult("300", "删除失败", "", "", "");
		}
		return opreateResult;
	}
	/**
	 * 功能说明 :跳转发送消息
	 * @author : chenxt
	 * @version ：2017-02-28
	 * @param request
	 * @param messageId
	 * @param bean
	 * @return
	 */
	@RequestMapping("/toSendMessage")
	public String toSendMessage(HttpServletRequest request, Model model,String messageId,SysMessage bean) {
		bean = sysMessageService.getSysMessageById(messageId);
		model.addAttribute("bean", bean);
		return "admin/message/sendmessage";
	}
	
	/**
	 * 功能说明 :发送
	 * @author : chenxt
	 * @version ：2017-02-28
	 * @param request
	 * @param model 
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendMessage")
	public OpreateResult sendMessage(HttpServletRequest request, Model model,SysMessage bean) {
		bean.setLeadId(request.getParameter("district.leadId"));
		int re = sysMessageService.insertSysMessageLead(bean.getLeadId(), bean.getMessageId());	
		OpreateResult opreateResult = new OpreateResult();
		if (re > 0) {
			opreateResult.setStatusCode("200");
			opreateResult.setNavTabId("sysMessageManage");
			opreateResult.setMessage("发送成功");
			opreateResult.setCallbackType("closeCurrent");
		} else {
			opreateResult.setStatusCode("300");
			opreateResult.setNavTabId("sysMessageManage");
			opreateResult.setMessage("发送失败");
			opreateResult.setCallbackType("closeCurrent");
		}
		model.addAttribute("bean", bean);
		return opreateResult;
	}
	
	/**
	 * 功能说明 :查询发送历史记录
	 * @author : chenxt
	 * @version ：2017-02-28
	 * @param request
	 * @param model 
	 * @param page
	 * @param bean
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("tohistory")
	public String tohistory(HttpServletRequest request, Model model, Page<SysMessageLead> page,SysMessageLead bean) throws ParseException {
		page.setQueryBean(bean);
		Page<SysMessageLead> pageList = sysMessageService.getSysDepartmentLeadPage(page);
		request.setAttribute("pageList", pageList);
		model.addAttribute("bean", bean);
		model.addAttribute("StatecType", StatecType());
		return "admin/message/detail";
	}
	
	/** 
	 * 功能说明 :状态枚举
	 * */
	@SuppressWarnings({ "serial" })
	private static final Map<String, String> StatecType() {
		Map<String, String> statesType = new HashMap<String, String>() {
			{
				put("0", "未发送");
				put("1", "已发送未读");
				put("2", "已发送已读");
			}
		};
		return statesType;
	}
}

package com.ahcd.controller.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ahcd.common.Constant;
import com.ahcd.common.FileUploadUtil;
import com.ahcd.common.PropertyManager;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysNotice;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.ISysNoticeService;

@Controller	
@RequestMapping("/web/notice")
public class WebNoticeController {
	@Resource
	private ISysNoticeService sysNoticeService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model,HttpSession session ,@RequestParam(value ="noticeTitle", required = false) String noticeTitle,
			@RequestParam(value ="pageNum", required = false) Integer pageNum ,@RequestParam(value ="numPerPage", required = false) Integer numPerPage){
		Map<String,Object> params=new HashMap<String,Object>();
		SysReportUser user = (SysReportUser)session.getAttribute(Constant.SESSION_USER);
		//获取最近十条公告
		Page<SysNotice> page=new Page<SysNotice>();
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		params.put("departmentId", user.getDepartmentId());
		params.put("userId", user.getUserId());
		params.put("noticeTitle", noticeTitle);
		page.setQueryBean(params);
		Page<SysNotice> pageList=sysNoticeService.getNoticePageByUser(page);
		request.setAttribute("pageList", pageList);
		return "web/notice/list";
	}
	@RequestMapping("/detail")
	public String detail(HttpServletRequest request,Model model,HttpSession session,@RequestParam(value ="noticeId", required = true) String noticeId ){
		SysReportUser user = (SysReportUser)session.getAttribute(Constant.SESSION_USER);
		SysNotice sysNotice=sysNoticeService.getNoticeById(noticeId);
		if(sysNotice!=null){
			sysNoticeService.readNotice(noticeId,user.getUserId());
		}
		request.setAttribute("sysNotice", sysNotice);
		return "web/notice/detail";
	}
	
	/**
	 * 
	 * 功能说明 :附件下载
	 * 
	 * @author : fei yang
	 * @version ：2016年11月2日 下午1:48:24
	 * @param response
	 * @param request
	 * @param model
	 * @param fileName
	 */
	@RequestMapping("/toDownload")
	public void toDownload(HttpServletResponse response, HttpServletRequest request, Model model, @RequestParam(value ="fileName", required = true) String fileName) {
		try {
			String fileType="";
			if(!StringUtil.isBlank(fileName) && fileName.contains(".")){
				fileType=fileName.substring(fileName.lastIndexOf("."), fileName.length());
				fileName=fileName.substring(0,fileName.lastIndexOf("."));
			}
			FileUploadUtil.downloadFile(response, FileUploadUtil.noticeFilePath,fileName, fileType);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * 附件下载:公告
	 * @throws IOException 
	 * 
	 */
	@RequestMapping("/downloadFile")
	public void downloadFile(HttpServletRequest requst,HttpServletResponse response,Model model,String fileName) throws IOException{
		String noticeFilePath = PropertyManager.getConfigProperty("config_path")+ Constant.noticeFileUpload;
		String filePath = noticeFilePath + fileName;
		File file = new File(filePath);
		try {
			InputStream is = new FileInputStream(file);
			String fileName1 = fileName = fileName.substring(0, fileName.lastIndexOf("."));
			response.encodeURL(new String(fileName1.getBytes(),"ISO8859_1"));//转码
		    response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
			byte[]b = new byte[1024];
			int len =0;
			while((len=is.read(b))!=-1){
				response.getOutputStream().write(b, 0, len);
			}
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}

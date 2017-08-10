package com.ahcd.controller.admin;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ahcd.common.Constant;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.PropertyManager;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysAreaInfo;
import com.ahcd.pojo.SysDepartmentInfo;
import com.ahcd.pojo.SysNotice;
import com.ahcd.pojo.SysNoticeDepartment;
import com.ahcd.pojo.SysNoticeFile;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.ISysAreaInfoService;
import com.ahcd.service.ISysDepartmentInfoService;
import com.ahcd.service.ISysNoticeDepartmentService;
import com.ahcd.service.ISysNoticeFileService;
import com.ahcd.service.ISysNoticeService;
import com.ahcd.service.IUserService;

@Controller
@RequestMapping("/admin/notice")
public class NoticeManageController {
	@Resource
	private ISysAreaInfoService areaService;
	@Resource
	private ISysDepartmentInfoService departmentService;
	@Resource
	private IUserService userService;
	@Resource
	private ISysNoticeService sysNoticeService;
	@Resource
	private ISysNoticeDepartmentService sysNoticeDepartmentService;
	@Resource
	private ISysNoticeFileService sysNoticeFileService;


	@RequestMapping("/list")
	public String list(HttpServletRequest request,@RequestParam(value ="noticeTitle1", required = false) String noticeTitle1,
			@RequestParam(value ="pageNum", required = false) Integer pageNum ,@RequestParam(value ="numPerPage", required = false) Integer numPerPage) {
		Map<String,Object> params=new HashMap<String,Object>();
		Page<SysNotice> page=new Page<SysNotice>();
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		String noticeTitle = "";
		try{
		noticeTitle = noticeTitle1.trim();
		}catch(Exception e){
			e.printStackTrace();
		}
		params.put("noticeTitle",noticeTitle);
		page.setQueryBean(params);
		Page<SysNotice> pageList=sysNoticeService.getNoticePage(page);
		request.setAttribute("pageList", pageList);
		request.setAttribute("noticeTitle1", noticeTitle);
		return "admin/notice/list";
	}

	/**
	 * 跳转新增公告
	 */
	@RequestMapping("/toAdd")
	public String toAddNotice(HttpServletRequest request, String StselectedDepartmentId) {
		List<SysAreaInfo> areaList = areaService.getArea();
		List<SysDepartmentInfo> departmentList = departmentService.getDepartment();
		request.setAttribute("areaList", areaList);
		request.setAttribute("departmentList", departmentList);
		request.setAttribute("actionMethod", "add");
		return "admin/notice/edit";
	}

	/**
	 * 新增公告
	 */
	@ResponseBody
	@RequestMapping("/add")
	public OpreateResult addNotice(HttpServletRequest request, HttpSession session,MultipartFile uploadExcel) {
		// 根据userName获取userId
		String userName = session.getAttribute("userName").toString();
		SysReportUser sysReportUser = userService.selectUseridByUserName(userName);
		BigDecimal userId = sysReportUser.getUserId();
		String noticeTitle = request.getParameter("noticetitle");
		String description = StringUtil.removeHtmlTag(request.getParameter("description"));
		String noticeSynopsis = request.getParameter("noticeSynopsis");
		if (StringUtil.isBlank(noticeSynopsis)) {
			noticeSynopsis = StringUtil.removeHtmlTag(description);
		}
		noticeSynopsis = noticeSynopsis.substring(0, noticeSynopsis.length() > 200 ? 200 : noticeSynopsis.length());

		SysNotice sysNotice = new SysNotice();
		sysNotice.setCreateTime(new Date());
		sysNotice.setUserId(userId);
		sysNotice.setNoticeTitle(noticeTitle);
		sysNotice.setNoticeSynopsis(noticeSynopsis);
		sysNotice.setNoticeContent(description);
		sysNoticeService.insertSysNotice(sysNotice);
		String noticeId = sysNotice.getNoticeId();
		
		//公告范围departmentId
		String[] departmentIds = request.getParameterValues("c1");
		if (departmentIds == null) {
			OpreateResult opreateResult = new OpreateResult();
			opreateResult.setStatusCode("300");
			opreateResult.setMessage("请选择公告范围!");
			opreateResult.setCallbackType("closeCurrent");
			return opreateResult;
		}
		for (int i = 0; i < departmentIds.length; i++) {
			if(!departmentIds[i].equals(",")){
				SysNoticeDepartment sysNoticeDepartment = new SysNoticeDepartment();
				sysNoticeDepartment.setDepartmentId(departmentIds[i]);
				sysNoticeDepartment.setNoticeId(noticeId);
				sysNoticeDepartmentService.insertSysNoticeDepartment(sysNoticeDepartment);
			}
		}
		//保存上传文件
		try{
		String fileName = uploadExcel.getOriginalFilename();
		if(!StringUtil.isBlank(fileName)){
			String fileType="";
			if(fileName.contains(".")){
				fileType=fileName.substring(fileName.lastIndexOf("."), fileName.length());
				fileName=fileName.substring(0, fileName.lastIndexOf("."));
			}
			fileName=fileName+new Date().getTime();
			File noticeFileFolder =new File("D:\\zhzs_resource\\notice_file");    
			//如果文件夹不存在则创建    
			if  (!noticeFileFolder .exists()  && !noticeFileFolder .isDirectory())      
			{       
			    System.out.println("//不存在");  
			    noticeFileFolder .mkdir();    
			} else   
			{  
			    System.out.println("//目录存在");  
			}  
			String noticeFileUploadPath = PropertyManager.getConfigProperty("config_path") + 
					Constant.noticeFileUpload+fileName+fileType;
			uploadExcel.transferTo(new File(noticeFileUploadPath));
			SysNoticeFile sysNoticeFile = new SysNoticeFile();
			sysNoticeFile.setNoticeId(noticeId);
			sysNoticeFile.setFilePath(fileName+fileType);
			sysNoticeFileService.insertNoticeFile(sysNoticeFile);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		OpreateResult opreateResult = new OpreateResult();
		opreateResult.setStatusCode("200");
		opreateResult.setNavTabId("noticeManage");
		opreateResult.setMessage("添加成功");
		opreateResult.setCallbackType("closeCurrent");
		return opreateResult;
	}
	
	/**
	 * 跳转到修改方法
	 *
	 */
	@RequestMapping("/toEdit")
	public String toEditNotice(HttpServletRequest request, String noticeId) {
		List<SysDepartmentInfo> departmentList = departmentService.getDepartment();
		request.setAttribute("departmentList", departmentList);
		List<SysNoticeDepartment> sysNoticeDepartmentList = sysNoticeService.getNoticeDepartment(noticeId);
		SysNotice sysNotice = sysNoticeService.getNoticeById(noticeId);
		sysNotice.setSysNoticeDepartmentList(sysNoticeDepartmentList);
		request.setAttribute("sysNotice", sysNotice);
		request.setAttribute("actionMethod", "edit");
		return "admin/notice/edit";
	}
	
	/**
	 * 修改方法
	 */
	@ResponseBody
	@RequestMapping("/edit")
	public OpreateResult editNotice(HttpServletRequest request,HttpSession session,MultipartFile uploadExcel2) {
		String noticeId = request.getParameter("noticeId");
		// 根据userName获取userId
		String userName = session.getAttribute("userName").toString();
		SysReportUser sysReportUser = userService.selectUseridByUserName(userName);
		BigDecimal userId = sysReportUser.getUserId();
		String noticeTitle = request.getParameter("noticetitle");
		String description = StringUtil.removeHtmlTag(request.getParameter("description"));
		String noticeSynopsis = request.getParameter("noticeSynopsis");
		if (StringUtil.isBlank(noticeSynopsis)) {
			noticeSynopsis = StringUtil.removeHtmlTag(description);
		}
		noticeSynopsis = noticeSynopsis.substring(0, noticeSynopsis.length() > 200 ? 200 : noticeSynopsis.length());

		SysNotice sysNotice = new SysNotice();
		sysNotice.setCreateTime(new Date());
		sysNotice.setUserId(userId);
		sysNotice.setNoticeTitle(noticeTitle);
		sysNotice.setNoticeSynopsis(noticeSynopsis);
		sysNotice.setNoticeId(noticeId);
		sysNotice.setNoticeContent(description);
		sysNoticeService.updateSysNotice(sysNotice);
		//保存修改文件
		try{
			SysNoticeFile oldsysNoticeFile = sysNoticeFileService.selectByNoticeId(noticeId);
			String oleFileName = "";
			try{
			oleFileName = oldsysNoticeFile.getFilePath();
			}catch(Exception e){
				e.printStackTrace();
			}
			String fileName = uploadExcel2.getOriginalFilename();
			if(!StringUtil.isBlank(fileName)){
				String fileType="";
				if(fileName.contains(".")){
					fileType=fileName.substring(fileName.lastIndexOf("."), fileName.length());
					fileName=fileName.substring(0, fileName.lastIndexOf("."));
				}
				fileName=fileName+new Date().getTime();
				
			if(fileName!=null){
				String oldNoticeFileUploadPath = PropertyManager.getConfigProperty("config_path")
						+ Constant.noticeFileUpload+oleFileName;
				File file = new File(oldNoticeFileUploadPath);
				if(file.exists()) {  
					file.delete();
					String noticeFileUploadPath = PropertyManager.getConfigProperty("config_path") + 
							Constant.noticeFileUpload+fileName+fileType;
					uploadExcel2.transferTo(new File(noticeFileUploadPath));
					SysNoticeFile sysNoticeFile = new SysNoticeFile();
					sysNoticeFile.setNoticeId(noticeId);
					sysNoticeFile.setFilePath(fileName+fileType);
					if(!StringUtil.isBlank(oleFileName)){
					sysNoticeFileService.updateSysNoticeFile(sysNoticeFile);
					}else{
						sysNoticeFileService.insertNoticeFile(sysNoticeFile);
					}
				}
			}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		//保存修改部门
		sysNoticeDepartmentService.deleteByNoticeId(noticeId);
		String[] departmentIds = request.getParameterValues("c1");
		if (departmentIds == null) {
			OpreateResult opreateResult = new OpreateResult();
			opreateResult.setStatusCode("300");
			opreateResult.setMessage("请选择公告范围!");
			opreateResult.setCallbackType("closeCurrent");
			return opreateResult;
		}
		for (int i = 0; i < departmentIds.length; i++) {
				SysNoticeDepartment sysNoticeDepartment = new SysNoticeDepartment();
				sysNoticeDepartment.setDepartmentId(departmentIds[i]);
				sysNoticeDepartment.setNoticeId(noticeId);
				sysNoticeDepartmentService.insertSysNoticeDepartment(sysNoticeDepartment);
		}
		
		OpreateResult result = new OpreateResult();
		result.setStatusCode("200");
		result.setNavTabId("noticeManage");
		result.setMessage("修改成功");
		result.setCallbackType("closeCurrent");
		return result;
	}
	
	/**
	 * 批量删除方法
	 */
	@ResponseBody
	@RequestMapping("/deletes")
	public OpreateResult deletes(HttpServletRequest request,@RequestParam(value ="notice_Ids", required = true) String notice_Ids) {
		String[] noticeIds=notice_Ids.split(",");
		for(int i=0;i<noticeIds.length;i++){
			//删信息
			sysNoticeService.deleteByNoticeId(noticeIds[i]);
			//删部门
			sysNoticeDepartmentService.deleteByNoticeId(noticeIds[i]);
			//删文件
			SysNoticeFile oldsysNoticeFile = sysNoticeFileService.selectByNoticeId(noticeIds[i]);
			if(oldsysNoticeFile!=null){
				//磁盘删除
				String oleFileName = oldsysNoticeFile.getFilePath();
				String oldNoticeFileUploadPath = PropertyManager.getConfigProperty("config_path") +Constant.noticeFileUpload+oleFileName;
				File file = new File(oldNoticeFileUploadPath);
				if(file.exists()) {  
					file.delete();
				}
			}
			//数据库文件记录删除
			sysNoticeFileService.deleteByNoticeId(noticeIds[i]);
		}
		OpreateResult result = new OpreateResult();
		result.setStatusCode("200");
		result.setNavTabId("noticeManage");
		result.setMessage("删除成功");
		return result;
	}
}

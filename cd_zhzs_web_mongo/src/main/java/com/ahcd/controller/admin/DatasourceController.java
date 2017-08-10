package com.ahcd.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ahcd.common.Constant;
import com.ahcd.common.OpreateResult;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.IJsonConfigService;

@Controller
@RequestMapping("/admin/datasource")
public class DatasourceController {

	@Resource
	private IJsonConfigService jsonConfigService;

	/**
	 * 
	 * 功能说明 : 列表
	 * 
	 * @author : fei yang
	 * @version ：2016年11月1日 下午5:14:54
	 * @param request
	 * @param model
	 * @param page
	 * @param bean
	 * @return
	 */
	@RequestMapping("list")
	public String getList(HttpServletRequest request, Model model, Page<DatasourceBean> page, DatasourceBean bean) {
		Page<DatasourceBean> pageList = jsonConfigService.getDatasourcePage(page, bean);
		model.addAttribute("pageList", pageList);
		model.addAttribute("bean", bean);
		return "admin/datasource/list";
	}

	/**
	 * 
	 * 类说明: 删除节点
	 * 
	 * @author 作者 : fei yang
	 * @version 创建时间:2016年9月21日 下午4:14:31
	 * @param request
	 * @param model
	 * @param id
	 * @return:
	 */
	@ResponseBody
	@RequestMapping("toDelete")
	public OpreateResult toDelete(HttpServletRequest request, Model model, String id) {
		return jsonConfigService.deleteDatasourceById(id);
	}

	/**
	 * 
	 * 功能说明 :跳转到新增或者修改页面
	 * 
	 * @author : fei yang
	 * @version ：2016年10月22日 上午10:09:34
	 * @param request
	 * @param type
	 *            1新增 2修改SQL 3 结果集 4条件区域
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("toSaveOrUpdate")
	public String toSaveOrUpdate(HttpServletRequest request, Integer isType, Model model, String id) {
		DatasourceBean oldBean = new DatasourceBean();
		if (isType == 1) {
		} else {
			oldBean = jsonConfigService.getDatasourceById(id);
		}
		model.addAttribute("isType", isType);
		model.addAttribute("datasourceBean", oldBean);
		return "admin/datasource/edit";
	}

	/**
	 * 
	 * 类说明: 新增或者修改
	 * 
	 * @author 作者 : fei yang
	 * @version 创建时间:2016年9月21日 下午4:14:51
	 * @param request
	 * @param model
	 * @param bean
	 * @return:
	 */
	@ResponseBody
	@RequestMapping("saveOrUpdate")
	public OpreateResult saveOrUpdate(HttpServletRequest request, Model model, DatasourceBean bean,Integer isType) {
	if (isType==1) {
		return jsonConfigService.saveDatasource(bean);
	}else {
		return jsonConfigService.updateDatasource(bean);
	}
		
	}

}

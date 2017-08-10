package com.ahcd.controller.admin;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.ConditionBean;
import com.ahcd.pojo.ConfigBean;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.ResultBean;
import com.ahcd.service.ConfigService;
import com.ahzd.service.ConfigBeanService;
import com.ahzd.service.DataSourceMongoService;
import com.alibaba.fastjson.JSON;

/**
 * 
 * @author : fei yang
 * @version ：2016年10月20日 下午4:11:12
 */
@Controller
@RequestMapping("/admin/config")
public class ConfigController {

@Resource
	private ConfigService configService;

	@Resource
	private ConfigBeanService configBeanService;

	@Resource
	private DataSourceMongoService dataSourceMongoService;

	/**
	 * 
	 * 功能说明 : 获取列表
	 * 
	 * @author : fei yang
	 * @version ：2016年10月21日 上午9:42:55
	 * @param request
	 * @param model
	 * @param page
	 * @param bean
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, ConfigBean bean, Page<ConfigBean> page) {
		String name = bean.getName();
		if(name!=null&&name.indexOf(",") != -1){
			name=name.replace(",","");
			bean.setName(name);
		}
		page = configBeanService.getList(page, bean);
		model.addAttribute("pageList", page);
		model.addAttribute("bean", bean);
		return "admin/config/list";
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
	public OpreateResult toDelete(HttpServletRequest request, Model model, String ids) {
		// boolean flag = configService.Delete(ids);
		boolean flag = false;// mogoDB根据ID删除相应的记录；
		ConfigBean configBean = null;
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			configBean = configBeanService.deleteRecord(id[i]);
		}
		if (configBean != null) {
			flag = true;
		}
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
	 * 功能说明 :跳转到新增或者修改页面
	 * 
	 * @author : fei yang
	 * @version ：2016年10月22日 上午10:09:34
	 * @param request
	 * @param type
	 *         type 1新增配置  3 结果集 4条件区域 4修改SQL
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("toSaveOrUpdate")
	public String toSaveOrUpdate(HttpServletRequest request, Integer type, Model model, String functionId) {
		String returnUrl = "admin/config/edit";
		ConfigBean bean = configBeanService.findOne(functionId);
		if (type == 1||type==4) {//新增或者修改SQL
			List<DatasourceBean> datasourceList = dataSourceMongoService.getAllDatasource();
			model.addAttribute("datasourceList", datasourceList);
		} else if (type == 2) {
			model.addAttribute("fieldBeans", bean.getResults().getFieldBeans());
			List<ConfigBean> configList = configBeanService.findAll();
			model.addAttribute("configList", configList);
			returnUrl = "admin/config/result";

		} else if (type == 3) {
			returnUrl = "admin/config/condition";
		}
		model.addAttribute("bean", bean);
		model.addAttribute("type", type);
		return returnUrl;
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
	public OpreateResult saveOrUpdate(HttpServletRequest request, Model model, ConfigBean bean, ResultBean resultBean,
			Integer type) {
		return configService.saveOrUpdate(bean, resultBean, type);
	}

	/**
	 * 
	 * 功能说明 : 查找带回集合
	 * 
	 * @author : fei yang
	 * @version ：2016年10月24日 下午1:58:09
	 * @param request
	 * @param model
	 * @param page
	 * @param bean
	 * @return
	 */
	@RequestMapping("/lookList")
	public String getLookList(HttpServletRequest request, Model model, Page<ConfigBean> page, ConfigBean bean,
			String index) {
		page.setNumPerPage(10);
		//Page<ConfigBean> pageList = configService.getList(page, bean);

		page=configBeanService.getList(page, bean);
		
		model.addAttribute("pageList", page);
		
		model.addAttribute("bean", bean);
		String reId = "functionId";
		String reName = "functionName";
		if (!StringUtil.isBlank(index)) {
			reId += index;
			reName += index;
		}
		model.addAttribute("index", index);
		model.addAttribute("reId", reId);
		model.addAttribute("reName", reName);
		return "admin/config/lookList";
	}

	/**
	 * 
	 * 功能说明 : 查找带回集合
	 * 
	 * @author : fei yang
	 * @version ：2016年10月24日 下午1:58:09
	 * @param request
	 * @param model
	 * @param page
	 * @param bean
	 * @return
	 */
	@RequestMapping("/lookListConfig")
	public String getLookListConfig(HttpServletRequest request, Model model, Page<ConfigBean> page, ConfigBean bean,
			String index, String functionId) {
		page.setNumPerPage(10);
		// Page<ConfigBean> pageList = configService.getList(page, bean);

		// 获取配置文件
		Page<ConfigBean> pageList = page = configBeanService.getList(page, bean);
		model.addAttribute("pageList", pageList);
		model.addAttribute("bean", bean);
		String reId = "functionId";
		String reName = "functionName";
		if (!StringUtil.isBlank(index)) {
			reId += index;
			reName += index;
		}
		model.addAttribute("index", index);
		model.addAttribute("reId", reId);
		model.addAttribute("reName", reName);
		return "admin/config/lookListConfig";
	}

	/**
	 * 跳转到新增条件页面
	 */
	@RequestMapping("toEditCondition")
	public String toEditCondition(Model model, String functionId) {
		model.addAttribute("functionId", functionId);
		return "admin/config/editCondition";
	}

	/**
	 * 
	 * 功能说明 :增加条件区域
	 * 
	 * @author : fei yang
	 * @version ：2017年3月10日 下午3:16:21
	 * @param request
	 * @param conditionBean
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addCondition")
	public OpreateResult addCondition(HttpServletRequest request, ConditionBean conditionBean, String id) {
		OpreateResult opreateResult = new OpreateResult("300", "增加条件失败!", "", "closeCurrent", "");
		if (configService.addCondition(conditionBean, id)) {
			opreateResult = new OpreateResult("200", "增加条件成功!", "configAddOrEdit", "closeCurrent", "");
		}
		return opreateResult;
	}

	/**
	 * 
	 * 功能说明 :校验SQL
	 * 
	 * @author : fei yang
	 * @version ：2017年3月3日 下午2:08:42
	 * @param response
	 * @param configBean
	 */
	@RequestMapping("toCheckSql")
	public void toCheckSql(HttpServletResponse response, HttpServletRequest request, String jsonStr) {
		try {
			ConfigBean bean = JSON.toJavaObject(JSON.parseObject(jsonStr), ConfigBean.class);
			String result = configService.toCheckSql(bean);
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功能说明 : 删除条件
	 * 
	 * @author : fei yang
	 * @version ：2017年3月10日 下午4:04:51
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("toDelCondition")
	public OpreateResult toDelCondition(String id, String conditionId) {
		OpreateResult opreateResult = new OpreateResult("300", "删除条件失败", "configAddOrEdit", "", "");
		if (configService.delCondition(id, conditionId)) {
			opreateResult = new OpreateResult("200", "删除成功", "configAddOrEdit", "", "");
		}
		return opreateResult;
	}

	/**
	 * 
	 * 功能说明 : 查询条件上下移动
	 * 
	 * @author : feiyue yang
	 * @version ：2017年3月10日 下午4:04:51
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("toUpOrDown")
	public OpreateResult toUpOrDown(String id, String conditionId, String type) {
		OpreateResult opreateResult = new OpreateResult();
		int a = configService.upOrDown(id, conditionId, type);
		if (a == 1) {
			opreateResult = new OpreateResult("200", "操作成功!", "configAddOrEdit", "", "");
		} else if (a == 2) {
			opreateResult = new OpreateResult("300", "已经是第一个了!", "configAddOrEdit", "", "");
		} else if (a == 3) {
			opreateResult = new OpreateResult("300", "已经是最后一个了!", "configAddOrEdit", "", "");
		} else {
			opreateResult = new OpreateResult("300", "操作失败,缺少标识ID!", "configAddOrEdit", "", "");
		}
		return opreateResult;
	}

}

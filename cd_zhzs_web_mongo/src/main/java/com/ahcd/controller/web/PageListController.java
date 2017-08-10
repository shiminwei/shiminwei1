package com.ahcd.controller.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ahcd.common.Constant;
import com.ahcd.common.DbUntil;
import com.ahcd.common.ExcelFileUtil;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.ConditionBean;
import com.ahcd.pojo.ConfigBean;
import com.ahcd.pojo.FieldBean;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.ZdConstant;
import com.ahcd.service.ConfigService;
import com.ahcd.service.SysRoleService;
import com.ahcd.service.impl.ZdConstantServiceImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @author 作者 : fei yang
 * @version 创建时间：2016年9月17日 下午8:37:13 类说明
 */
@Controller
@RequestMapping("pageList")
public class PageListController {

	@Resource
	private ConfigService configService;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private ZdConstantServiceImpl zdConstantService;

	/**
	 * 
	 * @Title: 配置文件展现
	 * @author: feiyang
	 * @date: 2016年10月28日 下午5:46:04
	 * @param request
	 * @param model
	 * @param functionid
	 *            XML页面配置文件ID
	 * @param page
	 *            分页对象
	 * @param detileType
	 *            1:子页面跳转 其他：正差跳转
	 * @return:
	 */
	@RequestMapping("public")
	public String toPublicList(HttpServletRequest request, Model model, String functionid, Page<String[]> page,
			Integer detileType) {
		ConfigBean bean = configService.getConfigBeanById(functionid);
		Map<String, String[]> queryMap = getNewMap(bean, request, detileType);
		if (bean.getResultType().equals("存储过程")) {
			page = DbUntil.toQueryByProcedure(bean, page, queryMap);
		} else {
			page = DbUntil.toQuery(bean, page, queryMap);
		}
		model.addAttribute("bean", bean);
		model.addAttribute("pageList", page);
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_YEAR_TYPE);
		List<ZdConstant> monthList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		List<ZdConstant> shuiZhongList = zdConstantService.getConstantListByType(Constant.WEB_SHOW_SHUIZHONG_TYPE);
		model.addAttribute("yearList", yearList);
		model.addAttribute("months", monthList);
		model.addAttribute("shuiZhongList", shuiZhongList);
		String jsonQueryMap = JSON.toJSON(queryMap).toString();
		model.addAttribute("jsonQueryMap", jsonQueryMap);
		model.addAttribute("queryMap", queryMap);
		model.addAttribute("queryMapStr", getChildPageQueryStr(queryMap, bean.getConditionBeans()));
		return "web/index/list";

	}

	/**
	 * 
	 * 功能说明 :处理查询条件MAP
	 * 
	 * @author : fei yang
	 * @version ：2017年3月28日 下午4:51:46
	 * @param bean
	 * @param request
	 * @param detileType
	 *            1:子页面跳转 其他：正差跳转
	 * @return
	 */
	public static Map<String, String[]> getNewMap(ConfigBean bean, HttpServletRequest request, Integer detileType) {
		Map<String, String[]> queryMap = new HashMap<String, String[]>();
		for (int i = 0; i < bean.getConditionBeans().size(); i++) {
			String[] value = { "" };
			if (!StringUtil.isBlank(bean.getConditionBeans().get(i).getMorenzhi())
					&& request.getParameterValues(bean.getConditionBeans().get(i).getConditionId()) == null) {
				String[] str = { bean.getConditionBeans().get(i).getMorenzhi() };
				queryMap.put(bean.getConditionBeans().get(i).getConditionId(), str);
			} else {
				if (!StringUtil.isBlank(request.getParameterValues(bean.getConditionBeans().get(i).getConditionId()))) {
					value = request.getParameterValues(bean.getConditionBeans().get(i).getConditionId());
					if (!StringUtil.isBlank(detileType) && detileType == 1 && value.length > 0) {// 子页面跳转条件ID重复判断
						value = new String[] { value[value.length - 1] };
					}
				}
				queryMap.put(bean.getConditionBeans().get(i).getConditionId(), value);
			}
		}
		return queryMap;
	}

	/**
	 * 
	 * 功能说明 :获取子页面的查询条件
	 * 
	 * @author : fei yang
	 * @version ：2017年3月28日 下午4:52:00
	 * @param bean
	 * @return
	 */
	public static Map<String, String[]> getChileQueryMap(ConfigBean bean) {
		Map<String, String[]> chileQueryMap = new HashMap<String, String[]>();
		for (int i = 0; i < bean.getResults().getFieldBeans().size(); i++) {
			if ("1".equals(bean.getResults().getFieldBeans().get(i).getIsByValue())) {
				String[] value = { "" };
				chileQueryMap.put(bean.getResults().getFieldBeans().get(i).getJspFielName(), value);
			}
		}
		return chileQueryMap;
	}

	/**
	 * 
	 * 功能说明 : 替换返回字符串
	 * 
	 * @author : fei yang
	 * @version ：2016年10月27日 上午10:28:39
	 * @param querValues
	 * @return
	 */
	public static List<String[]> getReturnQuerValues(String[] querValues) {
		List<String[]> reQuerValues = new ArrayList<String[]>();
		if (!StringUtil.isBlank(querValues)) {
			for (int i = 0; i < querValues.length; i++) {
				if (querValues[i].indexOf("^") >= 0) {
					String[] str = { querValues[i], querValues[i + 1] };
					reQuerValues.add(str);
					i++;
				} else if (querValues[i].equals(Constant.notChoseStr)) {
					reQuerValues.add(null);
				} else {
					String[] str = { querValues[i] };
					reQuerValues.add(str);
				}
			}
		}
		return reQuerValues;
	}

	/**
	 * 
	 * 功能说明 : 文件导出
	 * 
	 * @author : fei yang
	 * @version ：2016年10月28日 下午5:47:02
	 * @param response
	 * @param bean
	 * @param querValues
	 * @param functionid
	 * @throws Exception
	 */
	@RequestMapping("exportExcel")
	public void exportExcel(HttpServletResponse response, ConfigBean bean, String querValues, String functionid)
			throws Exception {
		ConfigBean oldbean = configService.getConfigBeanById(functionid);
		Map<String, String[]> newMap = JSON.parseObject(querValues, new TypeReference<Map<String, String[]>>() {
		});
		List<String[]> reStrings = DbUntil.toQueryExcel(oldbean, newMap);
		String fileName = oldbean.getName();
		List<FieldBean> headers = oldbean.getResults().getFieldBeans();
		ExcelFileUtil.exportExcel(response, fileName, reStrings, headers, "yyyy-YY-dd");
	}

	/**
	 * 
	 * @Title: 添加跳转子页面传值配置
	 * @author: feiyang
	 * @date: 2017年3月31日 下午12:44:43
	 * @param map
	 *            传参MAP
	 * @return:
	 */
	public static String getChildPageQueryStr(Map<String, String[]> map, List<ConditionBean> conditionBeans) {
		String str = "&detileType=1";// 标识子页面的跳转
		if (conditionBeans != null && map != null) {
			for (int i = 0; i < conditionBeans.size(); i++) {
				if (StringUtil.isNotEmpty(conditionBeans.get(i).getIsByValue())) {
					str += "&" + conditionBeans.get(i).getIsByValue() + "=";
					String[] value = map.get(conditionBeans.get(i).getConditionId());
					if (value != null) {
						for (int j = 0; j < value.length; j++) {
							str += value[j];
						}
					}
				}
			}
		}
		return str;
	}

	@RequestMapping("toTest")
	public void toTest(HttpServletResponse response) {
		try {
			for (int i = 0; i < 1000000; i++) {
				System.out.println(i);
			}
			response.getWriter().write("1111111111");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

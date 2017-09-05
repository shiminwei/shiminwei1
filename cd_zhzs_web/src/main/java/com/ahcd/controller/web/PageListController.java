package com.ahcd.controller.web;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ahcd.common.Constant;
import com.ahcd.common.DbUntil;
import com.ahcd.common.ExcelUtils;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.ConditionBean;
import com.ahcd.pojo.ConfigBean;
import com.ahcd.pojo.FieldBean;
import com.ahcd.pojo.Page;
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
	 *            1:子页面跳转 其他：正常跳转
	 * @param targetType
	 *            页面打开方式 navTab:打开新页面 dialog：打开新窗口
	 * @return:
	 */
	@RequestMapping("public")
	public String toPublicList(HttpServletRequest request, Model model, String functionid, Page<String[]> page,
			Integer detileType, String targetType) {
		ConfigBean bean = configService.getConfigBeanById(functionid);
		Map<String, String[]> queryMap = getNewMap(bean, request, detileType);
		if (bean.getResultType().equals("存储过程")) {
			page = DbUntil.toQueryByProcedure(bean, page, queryMap);
		} else {
			page = DbUntil.toQuery(bean, page, queryMap);
		}
		model.addAttribute("bean", bean);
		String type = "a";
		List<FieldBean> fieldBeansList = bean.getResults().getFieldBeans();
		FieldBean fieldBean = null;
		for (int i = 0; i < fieldBeansList.size(); i++) {
			fieldBean = fieldBeansList.get(i);
			if(fieldBean.getFirstMergeName()!=null &&fieldBean.getFirstMergeName().length()>0){
				type = "yes";
				break;
			}else{
				type = "no";
			}
		}
		model.addAttribute("type", type);
		model.addAttribute("pageList", page);
		List<ConditionBean> conditionBeans = configService.getDistinctList(bean.getConditionBeans());//去重查询展现类型
		zdConstantService.selectAllNeed(model, conditionBeans);
		model.addAttribute("jsonQueryMap", JSON.toJSON(queryMap).toString());
		model.addAttribute("queryMap", queryMap);
		if (StringUtil.isNotEmpty(targetType) && "dialog".equals(targetType)) {
			model.addAttribute("targetType", targetType);
		} else {
			model.addAttribute("targetType", "navTab");
		}
		model.addAttribute("showQueryName", getShowQueryName(queryMap, bean.getConditionBeans()));
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
	 *            1:子页面跳转 其他：正常跳转
	 * @return
	 */
	public static Map<String, String[]> getNewMap(ConfigBean bean, HttpServletRequest request, Integer detileType) {
		Map<String, String[]> queryMap = new HashMap<String, String[]>();
		ConditionBean conditionBean = null;
		for (int i = 0; i < bean.getConditionBeans().size(); i++) {
			String[] value = { "" };
			conditionBean = bean.getConditionBeans().get(i);
			if (!StringUtil.isBlank(conditionBean.getMorenzhi())
					&& request.getParameterValues(conditionBean.getConditionId()) == null) {
				String[] str = { conditionBean.getMorenzhi() };
				if ("3".equals(conditionBean.getDisptype())
						&& StringUtil.isNotEmpty(conditionBean.getMorenzhi())) {// 年月下拉框传参数
					// String[] yearAndMonthStr =
					// bean.getConditionBeans().get(i).getMorenzhi().split(",");
					str = getNewYearAndMonth(conditionBean.getMorenzhi());
				}
				queryMap.put(conditionBean.getConditionId(), str);
			} else {
				if (!StringUtil.isBlank(request.getParameterValues(conditionBean.getConditionId()))) {
					value = request.getParameterValues(conditionBean.getConditionId());
					if (!StringUtil.isBlank(detileType) && detileType == 1 && value.length > 0) {// 子页面跳转条件ID重复判断
						value = new String[] { value[value.length - 1] };
					}
					if ("3".equals(conditionBean.getDisptype()) && !StringUtil.isBlank(value)
							&& value.length == 1) {// 年月下拉框传参数
						value = getNewYearAndMonth(value[0]);
					}
				}

				queryMap.put(conditionBean.getConditionId(), value);
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
		List<FieldBean> fieldBeanslist = bean.getResults().getFieldBeans() ;
		for (int i = 0; i < fieldBeanslist.size(); i++) {
			if ("1".equals(fieldBeanslist.get(i).getIsByValue())) {
				String[] value = { "" };
				chileQueryMap.put(fieldBeanslist.get(i).getJspFielName(), value);
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
		// ExcelFileUtil.exportExcel(response, fileName, reStrings, headers,
		// "yyyy-YY-dd");
		Workbook workbook = ExcelUtils.exportExcel(fileName, reStrings, headers, "yyyy-YY-dd");
		if (workbook != null) {
			String ddate = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(fileName.getBytes("gb2312"), "iso8859-1") + "_" + ddate + ".xls");// 设定输出文件头
			OutputStream output = response.getOutputStream();
			workbook.write(output);
			output.flush();
			output.close();
		}
	}

	/**
	 * 
	 * 功能说明 :获取表名标题展示查询条件的字符串
	 * 
	 * @author : fei yang
	 * @version ：2017年4月24日 上午11:29:04
	 * @param queryMap
	 * @param conditionBeans
	 * @return
	 */
	public static String getShowQueryName(Map<String, String[]> queryMap, List<ConditionBean> conditionBeans) {
		String showQueryName = "";
		if (conditionBeans != null && conditionBeans.size() > 0) {
			ConditionBean bean = null;
			String disptype = null;
			for (int i = 0; i < conditionBeans.size(); i++) {
				bean = conditionBeans.get(i);
				if (StringUtil.isNotEmpty(bean.getIsShowTitile()) && "1".equals(bean.getIsShowTitile())) {// 需要在标题显示查询条件
					for (Map.Entry<String, String[]> entry : queryMap.entrySet()) {
						if (entry.getKey().equals(bean.getConditionId()) && !StringUtil.isBlank(entry.getValue())) {
							disptype = bean.getDisptype();
							if ("2".equals(disptype)) {// 年份下拉框
								showQueryName += entry.getValue()[0] + "年";
							} else if ("3".equals(disptype)) {// 年月下拉框
								if (entry.getValue().length == 1) {// 只有年
									if (StringUtil.isNotEmpty(entry.getValue()[0])) {
										showQueryName += entry.getValue()[0] + "年";
									}
								} else if (entry.getValue().length == 2) {// 存在年月
									if (StringUtil.isNotEmpty(entry.getValue()[0])) {
										showQueryName += entry.getValue()[0] + "年";
									}
									if (StringUtil.isNotEmpty(entry.getValue()[1])) {
										showQueryName += getNewMonthInt(entry.getValue()[1]) + "月";
									}
								}
							} else if ("5".equals(disptype)) {// 月份下拉框
								showQueryName += getNewMonthInt(entry.getValue()[0]) + "月";
							} else {
								for (int j = 0; j < entry.getValue().length; j++) {
									showQueryName += entry.getValue()[j];
								}
							}/*
							switch (disptype) {
							case "2":// 年份下拉框
								showQueryName += entry.getValue()[0] + "年";
							break;
							case "3":// 年月下拉框
								switch (entry.getValue().length) {
								case 1:// 只有年
									if (StringUtil.isNotEmpty(entry.getValue()[0])) {
										showQueryName += entry.getValue()[0] + "年";
									}
								break;
								case 2:// 存在年月
									if (StringUtil.isNotEmpty(entry.getValue()[0])) {
										showQueryName += entry.getValue()[0] + "年";
									}
									if (StringUtil.isNotEmpty(entry.getValue()[1])) {
										showQueryName += getNewMonthInt(entry.getValue()[1]) + "月";
									}
								break;
//							default:
//								break;
								}
							break;
							case "5":// 月份下拉框
								showQueryName += getNewMonthInt(entry.getValue()[0]) + "月";
							break;
							default:
								for (int j = 0; j < entry.getValue().length; j++) {
									showQueryName += entry.getValue()[j];
								}
							break;
							}*/
						}
						
					}
				}
			}
		}
		return showQueryName;
	}

	/**
	 * 
	 * 功能说明 :替换月份数据 （01==》1）
	 * @author : fei yang
	 * @version ：2017年4月24日 下午12:30:36
	 * @param monthStr
	 * @return
	 */
	public static String getNewMonthInt(String monthStr) {
		if (StringUtil.isNotEmpty(monthStr) && monthStr.length() == 2) {
			String firstStr = monthStr.substring(0, 1);
			if (firstStr.equals("0")) {
				return monthStr.substring(1, 2);
			}
		}
		return monthStr;
	}

	public static String[] getNewYearAndMonth(String value) {
		String[] str = new String[] { value };
		if (StringUtil.isNotEmpty(value) && value.length() > 4) {
			if (value.length() > 4) {
				String year = value.substring(0, 4);
				String month = value.substring(4, value.length());
				return new String[] { year, month };
			}
		}
		return str;
	}

	public static void main(String[] args) {
		String[] str = getNewYearAndMonth(null);
		for (int i = 0; i < str.length; i++) {
			System.out.println(str[i]);
		}
	}
}

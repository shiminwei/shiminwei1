package com.ahcd.common;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.ahcd.pojo.ConditionBean;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class Constant {

	public static final String SESSION_USER = "currentLogonUser";// session中user标识名
	public static final String ADMIN_USER_CODE = "admin";// 系统后台管理员用户名
	public static final String MENU_ONE_CODE = "A_";// 一级菜单首字母
	public static final String MENU_TWO_CODE = "B_";// 二级菜单首字母
	public static final String MENU_THTREE_CODE = "C_";// 三级菜单首字母
	public static final String CHARTS_LAYOUT_TYPE = "charts_layout_type";// 图表布局类型
	public static final String CHARTS_TYPE = "charts_type";// 图表类型

	public static final String WEB_SHOW_YEAR_TYPE = "web_show_year";// 监管前台查询年份配置
	public static final String WEB_SHOW_MONTH_TYPE = "web_show_month";// 监管前台查询月份配置
	public static final String WEB_SHOW_SHUIZHONG_TYPE = "shuiZhong";// 监管前台查询年份配置

	/**
	 * Properties xml配置路径名称
	 */
	public static final String CONFIG_PATH = "config_path";
	public static final String CONFIG_XML_PATH = "xml_config_path";
	public static final String CHARTS_PATH = "charts_xml";

	public static final String DATASOURCER_PATH = "datasource";

	public static final String SQL_KEY = "@";

	/**
	 * 
	 * 类说明:获取时间字符串
	 * 
	 * @author 作者 : fei yang
	 * @version 创建时间:2016年9月29日 下午2:34:07
	 * @return:
	 */
	public static final String getNowStr() {
		int random = (int) (Math.random() * 10000000);
		Calendar rightNow = Calendar.getInstance();
		Format dateFormat = new SimpleDateFormat("YYYYMMddHHmmssS");
		String str = dateFormat.format(rightNow.getTime());
		return str + String.valueOf(random);

	}

	/**
	 * 需要替换的SQL查询部分
	 */
	public static final String needReplaceStr = "@@";

	/**
	 * 没有选择查询全部的字符串
	 */
	public static final String notChoseStr = "请选择";

	/**
	 * 需要隐藏的字符串
	 */
	public static final String HIDDEN_STR = "##&&";

	/**
	 * 需要子页面字符串
	 */
	public static final String LINK_STR = "&&##";

	/**
	 * 
	 * 类说明: 获取项目的真实绝对根路径
	 * 
	 * @author 作者 : fei yang
	 * @version 创建时间:2016年9月17日 下午5:46:43
	 * @param request
	 * @return:
	 */
	public static String getRealPath(HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("");
		return realPath;
	}

	/**
	 * 
	 * 功能说明 : 获取先过程后查临时表
	 * 
	 * @author : fei yang
	 * @version ：2017年3月17日 下午1:46:00
	 * @param sql
	 * @return
	 */
	public static final String[] getProcedureSql(String sql) {
		if (StringUtil.isNotEmpty(sql)) {
			sql = sql.trim();
			if (";".equals(sql.substring(sql.length() - 1, sql.length()))) {
				sql = sql.substring(0, sql.length() - 1);
			}
			String[] newStr = sql.split(";");
			if (newStr.length == 2) {
				return newStr;
			}
		}
		return null;
	}

	public final static String projectTitle = PropertyManager.getConfigProperty("project_title");
	public final static String projectReportLoginUrl = PropertyManager.getConfigProperty("project_report_login_url");
	public final static String projectWebLoginUrl = PropertyManager.getConfigProperty("project_web_login_url");

}

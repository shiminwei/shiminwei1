package com.ahcd.common;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

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
	public static final String WEB_SHOW_SKGK_TYPE = "web_show_skgk";// 监管前台查询收款国库配置
	public static final String WEB_SHOW_RKJC_TYPE = "web_show_rkjc";// 监管前台查询入库级次配置
	public static final String WEB_SHOW_SSCY_TYPE = "web_show_sscy";// 监管前台查询所属产业配置
	public static final String WEB_SHOW_SSDL_TYPE = "web_show_ssdl";// 监管前台查询所属大类配置
	public static final String WEB_SHOW_SSQY_TYPE = "web_show_ssqy";// 监管前台查询所属区域配置
	public static final String WEB_SHOW_WHETHER_TYPE = "web_show_whether";// 监管前台查询是否配置
	public static final String WEB_SHOW_YSLY_TYPE = "web_show_ysly";// 监管前台查询预算来源配置

	

	
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

	public static final Map<Integer, String> colType() {
		Map<Integer,String>  map = new HashMap<Integer, String>();
	 	 for (FieldTypeEnum s : FieldTypeEnum.values()) {
	 		map.put(s.getIndex(), s.getName());
	 	 }
		return map;
	}

	public static final Map<Integer, String> periodType() {
		Map<Integer, String> periodType = new HashMap<Integer, String>() {
			{
				put(1, "月度");
				put(2, "季度");
				put(3, "半年");
				put(4, "整年");
			}
		};
		return periodType;
	}

	public static final Map<String, String> getMonth() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("1", "一月");
		map.put("2", "二月");
		map.put("3", "三月");
		map.put("4", "四月");
		map.put("5", "五月");
		map.put("6", "六月");
		map.put("7", "七月");
		map.put("8", "八月");
		map.put("9", "九月");
		map.put("10", "十月");
		map.put("11", "十一月");
		map.put("12", "十二月");
		return map;
	}

	public static final Map<String, String> getQuarter() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("3", "第一季度");
		map.put("6", "第二季度");
		map.put("9", "第三季度");
		map.put("12", "第四季度");
		return map;
	}

	public static final Map<String, String> getHalfAYear() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("6", "上半年");
		map.put("12", "下半年");
		return map;
	}

	/**
	 * 
	 * 功能说明 : 年月下拉框分类型
	 * 
	 * @author : fei yang
	 * @version ：2016年11月2日 下午6:01:03
	 * @param type
	 * @return
	 */
	public static final Map<String, String> getMapByType(int type) {
		Map<String, String> map = new HashMap<String, String>();
		if (type == 1) {// 月份
			map = Constant.getMonth();
		} else if (type == 2) {// 季度
			map = Constant.getQuarter();
		} else if (type == 3) {// 半年
			map = Constant.getHalfAYear();
		}
		return map;
	}

	public static final String getDefaultSelectedValue(int type) {
		String value = "";
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		if (type == 2) {// 季度
			if (month >= 1 && month <= 3) {
				value = "3";
			} else if (month >= 4 && month <= 6) {
				value = "3";
			} else if (month >= 7 && month <= 9) {
				value = "6";
			} else {
				value = "9";
			}
		} else if (type == 3) {// 半年
			if (month >= 1 && month <= 6) {
				value = "6";
			} else {
				value = "12";
			}
		} else {// 月份
			value = String.valueOf(month - 1);
		}
		return value;
	}

	/**
	 * 
	 * 功能说明 :默认开始时间
	 * 
	 * @author : fei yang
	 * @version ：2016年11月15日 下午2:23:11
	 * @param type
	 * @return
	 */
	public static final int getDefaultStarMonth(int type) {
		int value = 12;
		if (type == 1) {
			return 1;
		} else if (type == 2) {// 季度
			return 3;
		} else if (type == 3) {// 半年
			return 6;
		}
		return value;
	}

	public static final Map<String, String> getQueryYear() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		String queryYearStr = PropertyManager.getConfigProperty("year_str");
		String[] str = queryYearStr.split(",");
		for (int i = 0; i < str.length; i++) {
			map.put(str[i], str[i]);
		}
		return map;
	}

	public static final String templateTablePrefix = PropertyManager.getConfigProperty("template_table_prefix");

	public static final String noticeFileUpload = PropertyManager.getConfigProperty("notice_file_path");

	public static final String defaultPwd = PropertyManager.getConfigProperty("default_password");

	/**
	 * 读取EXCEL 内容
	 */
	public static final int READ_EXCEL_CONTENT = 1;
	/**
	 * 读取 EXCEL 头部
	 */
	public static final int READ_EXCEL_HEAD = 2;

	public static final String PROBLEM_TYPE_1 = "列数与模板不一致";
	public static final String PROBLEM_TYPE_2 = "字符串有误,长度过大";
	public static final String PROBLEM_TYPE_3 = "整数值有误,长度过大";
	public static final String PROBLEM_TYPE_4 = "整数值有误,请检查是否是整数";
	public static final String PROBLEM_TYPE_5 = "浮点数值有误,长度过大";
	public static final String PROBLEM_TYPE_6 = "浮点数值有误,,请检查是否是浮点数";
	public static final String PROBLEM_TYPE_7 = "日期格式有误，请输入以下格式任意一种：2009-09-09或209/09/09或2009年9月9日或20090909";
	public static final String PROBLEM_TYPE_8 = "上报的数据为空";

	/**
	 * 
	 * 功能说明 : 获取当前时间 返回类型timestamp 精确到毫秒
	 * 
	 * @author : fei yang
	 * @version ：2016年11月7日 下午1:53:49
	 * @return
	 */
	public static final Timestamp getNowTime() {
		Date time = new Date();
		Timestamp timestamp = new Timestamp(time.getTime());
		return timestamp;
	}

	/**
	 * 
	 * 功能说明 :excel 替换浮点型
	 * 
	 * @author : fei yang
	 * @version ：2016年11月8日 下午4:18:23
	 * @param str
	 * @return
	 */
	public static final String replaceNumber(String str) {
		String reStr = "0";
		if (StringUtil.isBlank(str)) {
			return reStr;
		} else {
			reStr = str.replace(" ", "");
			reStr = reStr.replace(",", "");
		}
		return reStr;
	}

	public static List<String[]> zhengZeList() {
		List<String[]> list = new ArrayList<String[]>();
		list.add(new String[] { "^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd" });
		list.add(new String[] { "^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd" });
		list.add(new String[] { "^\\d{4}年\\d{1,2}月\\d{1,2}日$", "yyyy年MM月dd日" });
		list.add(new String[] { "^\\d{4}\\d{1,2}\\d{1,2}$", "yyyyMMdd" });
		list.add(new String[] { "^\\d{4}.\\d{1,2}.\\d{1,2}$", "yyyy.MM.dd" });
		list.add(new String[] {
				"^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$",
				"yyyy-MM-dd hh:MM:ss" });
		return list;
	}

	public static String formatDate(String str) {
		String newStr = "";
		List<String[]> list = zhengZeList();
		try {
			for (int i = 0; i < list.size(); i++) {
				Pattern p = Pattern.compile(list.get(i)[0]);
				if (p.matcher(str).matches()) {
					SimpleDateFormat sdf = new SimpleDateFormat(list.get(i)[1]);
					Date date = sdf.parse(str);
					newStr = String.valueOf(new Timestamp(date.getTime()));
					break;
				}
			}
		} catch (ParseException e) {
		}
		return newStr;
	}

	/**
	 * 
	 * 功能说明 :判断是否是数字
	 * 
	 * @author : fei yang
	 * @version ：2016年11月15日 上午10:03:38
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 功能说明 :地区枚举
	 */
	@SuppressWarnings("serial")
	public static final Map<BigDecimal, String> AreaType() {
		Map<BigDecimal, String> areaType = new HashMap<BigDecimal, String>() {
			{
				put(new BigDecimal(0), "全部");
				put(new BigDecimal(1), "城区");
				put(new BigDecimal(2), "乡镇");
				put(new BigDecimal(3), "开发区");
			}
		};
		return areaType;
	}

	/**
	 * 
	 * 功能说明 : 更具报送周期获取当前时间的始止日期月份段
	 * 
	 * @author : fei yang
	 * @version ：2017年2月24日 下午4:01:16
	 * @param type
	 * @return
	 */
	public static int[] getDateStartAndEnd(BigDecimal reportZq) {
		int startMonth = 0;
		int endMonth = 0;
		if (!StringUtil.isEmpty(reportZq)) {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("MM");
			if (reportZq.intValue() == 1) {
				startMonth = endMonth = cal.get(Calendar.MONTH) + 1;
			} else if (reportZq.intValue() == 2) {
				startMonth = Integer.valueOf(sdf.format(DateUtil.getCurrentQuarterStartTime()));
				endMonth = Integer.valueOf(sdf.format(DateUtil.getCurrentQuarterEndTime()));
			} else if (reportZq.intValue() == 3) {
				startMonth = Integer.valueOf(sdf.format(DateUtil.getHalfYearStartTime()));
				endMonth = Integer.valueOf(sdf.format(DateUtil.getHalfYearEndTime()));
			} else {
				return new int[] { 1, 12 };
			}
		}
		return new int[] { startMonth, endMonth };

	}

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

	/** 任务调度的参数key */
	public final static String JOB_PARAM_KEY = "jobParam";
	/** 任务调度的任务组key */
	public final static String GROUP_PARAM_KEY = "groupParam";
	/** 任务调度的参数key(处理结果-是否处理成功) */
	public final static String JOB_PARAM_RESULT_KEY = "is_success";

	public final static String JOB_GENERATE_FILE_SAVE_PATH=PropertyManager.getConfigProperty("job_generate_file_save_path");
}

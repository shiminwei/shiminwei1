package com.ahcd.common;

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
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;

public class Constant {

	/**
	 * 根目录
	 */
	public static final String report_path = new PropertiesUntil().getPropertyValue("report_path");
	/**
	 * 配置目录
	 */
	public static final String report_path_config = report_path + "/" + "config/";

	/**
	 * 默认文件名称
	 */
	public static final String lead_config_fileName = "config.xml";

	/**
	 * 返回处理成功状态码
	 */
	public static final String RETURN_SUCCESS_CODE = "200";
	public static final String RETURN_SUCCESS_MES = "操作成功";
	/**
	 * 返回处理失败状态码
	 */
	public static final String RETURN_FAIL_CODE = "300";
	public static final String RETURN_FAIL_MES = "操作失败";

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
	 * 
	   * 功能说明    :系统自带配置定时内容 
	   * @author   : fei yang 
	   * @version ：2017年2月8日 下午5:16:43 
	   * @return
	 */
	public static final Map<String, String> quartzMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("每天凌晨", "0 0 0 * * ?");
		map.put("每周日凌晨", "0 0 0 ? * SUN");
		map.put("每月1号凌晨", "0 0 0 1 * ?");
		map.put("自定义频率", "");
		return map;
	}
	
	
	/**
	 *需要创建的报送文件夹 
	 */
	public static final  String[] needMkdirs={"上报","重报","补报"};
	
	public static final  String JOB_NAME="jobOne";
	public static final  String JOB_GROUP="jobOne";

	
	
	/**
	 * 检查表格内容
	 */
	
	public static final String PROBLEM_TYPE_1 = "列数与模板不一致";
	public static final String PROBLEM_TYPE_2 = "字符串有误,长度过大";
	public static final String PROBLEM_TYPE_3 = "整数值有误,长度过大";
	public static final String PROBLEM_TYPE_4 = "整数值有误,请检查是否是整数";
	public static final String PROBLEM_TYPE_5 = "浮点数值有误,长度过大";
	public static final String PROBLEM_TYPE_6 = "浮点数值有误,,请检查是否是浮点数";
	public static final String PROBLEM_TYPE_7 = "日期格式有误，请输入以下格式任意一种：2009-09-09或209/09/09或2009年9月9日或20090909";

	public static List<String[]> zhengZeList() {
		List<String[]> list = new ArrayList<String[]>();
		list.add(new String[] { "^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd" });
		list.add(new String[] { "^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd" });
		list.add(new String[] { "^\\d{4}年\\d{1,2}月\\d{1,2}日$", "yyyy年MM月dd日" });
		list.add(new String[] { "^\\d{4}\\d{1,2}\\d{1,2}$", "yyyyMMdd" });
		list.add(new String[] { "^\\d{4}.\\d{1,2}.\\d{1,2}$", "yyyy.MM.dd" });
		return list;
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
		if (EmptyUtils.isEmpty(str)) {
			return reStr;
		} else {
			reStr = str.replace(" ", "");
			reStr = reStr.replace(",", "");
		}
		return reStr;
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
	
	
	public static void main(String[] args) {
		Map<String, String> quartzMap =quartzMap();
	String str= 	JSONObject.toJSONString(quartzMap);
		System.out.println(str);
	}
	
	

}

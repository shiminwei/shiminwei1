package com.ahcd.common;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : fei yang
 * @version ：2017年1月16日 下午3:11:58
 */
public class EmptyUtils {

	/**
	 * 
	 * 功能说明 :str 等于空
	 * 
	 * @author : fei yang
	 * @version ：2017年1月16日 下午3:12:49
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null || "".equals(str));
	}

	/**
	 * 
	 * 功能说明 :str 不等于空
	 * 
	 * @author : fei yang
	 * @version ：2017年1月16日 下午3:13:09
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return (str != null && !str.equals("") && !str.equals("null"));
	}

	/**
	 * 
	 * 功能说明 : 判断字符串是否是BigDecimal类型
	 * 
	 * @author : fei yang
	 * @version ：2017年1月16日 下午3:24:41
	 * @param str
	 * @return
	 */
	public static boolean isBigDecimal(String str) {
		boolean flag = false;
		try {
			new BigDecimal(str);
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}
	
	
	/**
	 * 
	 * 功能说明 :str 等于空
	 * 
	 * @author : fei yang
	 * @version ：2017年1月16日 下午3:12:49
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(BigDecimal bd) {
		return (bd == null);
	}

/**
 * 
   * 功能说明    :判断集合是否为空 
   * @author   : fei yang 
   * @version ：2017年1月16日 下午6:08:03 
   * @param list
   * @return
 */
	public static boolean isEmpty(List<Object> list) {
		return (list == null || list.size() == 0);
	}
	
/**
 * 
   * 功能说明    : 判断字符串是否是数字
   * @author   : fei yang 
   * @version ：2017年2月6日 上午11:13:26 
   * @param str
   * @return
 */
	public static boolean isNumeric(String str){
		  for (int i = 0; i < str.length(); i++){
		   System.out.println(str.charAt(i));
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
		 }
	
	
/**
 * 
   * 功能说明    : 判断数组不为空
   * @author   : fei yang 
   * @version ：2017年2月6日 下午1:28:09 
   * @param str
   * @return
 */
	public static boolean isNotEmpty(String[] str) {
		return (str != null && str.length>0);
	}
	
/**
 * 
   * 功能说明    :判断数组不为空 
   * @author   : fei yang 
   * @version ：2017年2月6日 下午1:28:27 
   * @param str
   * @return
 */
	public static boolean isEmpty(String[] str) {
		return (str == null || str.length<=0);
	}
	

	public static boolean isNotEmpty(int num) {
		return !"null".equals(String.valueOf(num));
	}
}

package com.ahcd.common;

import java.math.BigDecimal;

/** 
 * @author   : fei yang 
 * @version ：2016年10月26日 上午10:50:58 
 */
public class BigDecimalUtil {

	
	/**
	 * 
	   * 功能说明    : 判断是否值一致
	   * @author   : fei yang 
	   * @version ：2016年10月26日 上午10:52:39 
	   * @param old
	   * @param need
	   * @return
	 */
	public static boolean isSame(BigDecimal old,BigDecimal need){
		return old.compareTo(need)==0;
	}
	
	public static boolean isSame(BigDecimal old,int need){
		return old.compareTo(new BigDecimal(need))==0;
	}
	public static boolean isSame(BigDecimal old,String need){
		return old.compareTo(new BigDecimal(need))==0;
	}
	
}

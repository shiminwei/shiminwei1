package com.ahcd.common;

import java.io.File;

public class CEVUtil {
		/**
		* 依据后缀名判断读取的是否为Excel文件
		* @param filePath
		* @return
		*/
	     public static boolean isExcel(String filePath){
	         return StringUtil.notBlank(filePath) && (filePath.trim().matches("^.+\\.(?i)(xls)$")||filePath.trim().matches("^.+\\.(?i)(xlsx)$"))?true:false;
	     } 
	     
	     /** 
	      * 检查文件是否存在 
	      */  
	     public static boolean fileExist(String filePath){
	         return StringUtil.notBlank(filePath) && new File(filePath.trim()).exists()?true:false;
	     }
	     /**
	      * 依据内容判断是否为excel2003及以下
	     */
	     public static boolean isExcel2003(String filePath){
			return StringUtil.notBlank(filePath) && filePath.trim().endsWith(".xls")?true:false;
	     }
	     /**
	      * 依据内容判断是否为excel2007及以上
	     */
	     public static boolean isExcel2007(String filePath){
	    	 return StringUtil.notBlank(filePath) && filePath.trim().endsWith(".xlsx")?true:false;
	     }
	
	
}

package com.ahcd.common;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JobConstant {
	public static Map<Integer,String>  getAllStepTypes(){
		Map<Integer,String>  map = new HashMap<Integer, String>();
	 	 for (StepTypeEnum s : StepTypeEnum.values()) {
	 		map.put(s.getIndex(), s.getName());
	 	 }
	 	 return map;
	}
	
	/**
	 * 用于返回调度任务中的步骤生成文件保存路径
	 * 本方法会将文件路径定义为：系统配置的job生成文件根目录+jobID+前台页面手输的文件路径
	 * 本方法的目的：防止同名路径同名文件的冲突，所有步骤产生的文件都应该放到其所属调度任务下所有
	 * @param jobId
	 * @param filePath  页面中输入的保存路径
	 * @return 
	 */
	public static String getFileSavePath(String jobId,String filePath){
		if(StringUtil.isBlank(jobId)) return null;
		if(StringUtil.isBlank(filePath)) return null;
		else if(!filePath.contains("."))	return null;
		filePath=filePath.replace("\\", "/");
		String fileName=filePath;
		String jobFilePath="";
		if(filePath.contains("/")){
			String[] pathArray=filePath.split("/");
			filePath=filePath.substring(0, filePath.lastIndexOf("/"));
			fileName=pathArray[pathArray.length-1];
			jobFilePath =Constant.JOB_GENERATE_FILE_SAVE_PATH+File.separator+jobId+File.separator+filePath+File.separator+fileName;
		}else{
			jobFilePath =Constant.JOB_GENERATE_FILE_SAVE_PATH+File.separator+jobId+File.separator+fileName;
		}
		jobFilePath=jobFilePath.replace("\\", "/");
		FileUtil.makeDirectory(new File(jobFilePath));
		return jobFilePath ;
	}
	
	/**
	 * 用于返回调度任务中的步骤生成文件保存路径
	 * 本方法会将文件路径定义为：系统配置的job生成文件根目录+jobID+前台页面手输的文件路径+占位符
	 * 本方法的目的：防止同名路径同名文件的冲突，所有步骤产生的文件都应该放到其所属调度任务下所有
	 * @param jobId
	 * @param filePath
	 * @param pattern  文件名占位属性 从fileNamePatterns()方法中获取
	 * @return 真实的文件存储路径
	 */
	public static String getFileSavePath(String jobId,String filePath,String pattern){
		if(StringUtil.isBlank(jobId)) return null;
		if(StringUtil.isBlank(filePath)) return null;
		else if(!filePath.contains("."))	return null;
		//if(StringUtil.isBlank(pattern)) return null;
		filePath=filePath.replace("\\", "/");
		String fileName=filePath;
		String jobFilePath="";
		if(filePath.contains("/")){
			String[] pathArray=filePath.split("/");
			filePath=filePath.substring(0, filePath.lastIndexOf("/"));
			fileName=pathArray[pathArray.length-1];
			fileName=fileName.substring(0, fileName.lastIndexOf("."))+getPatternStr(pattern)+fileName.substring(fileName.lastIndexOf("."),fileName.length());
			jobFilePath =Constant.JOB_GENERATE_FILE_SAVE_PATH+File.separator+jobId+File.separator+filePath+File.separator+fileName;
		}else{
			fileName=fileName.substring(0, fileName.lastIndexOf("."))+getPatternStr(pattern)+fileName.substring(fileName.lastIndexOf("."),fileName.length());
			jobFilePath =Constant.JOB_GENERATE_FILE_SAVE_PATH+File.separator+jobId+File.separator+fileName;
		}
		jobFilePath=jobFilePath.replace("\\", "/");
		FileUtil.makeDirectory(new File(jobFilePath));
		return jobFilePath ;
	}
	
	
	/**
	 * 用于生成文件时后缀占位符
	 * @return
	 */
	public static LinkedHashMap<String,String> fileNamePatterns(){
		@SuppressWarnings("serial")
		LinkedHashMap<String,String> map=new LinkedHashMap<String,String>(){{
			put( "@yyyyMM@", "当前年月" ); 
			put( "@yyyyMMdd@", "当前年月日" ); 
			put( "@yyyyMMddHH@", "当前年月日时" ); 
			put( "@yyyyMMddHHmm@", "当前年月日时分" ); 
			put( "@yyyyMMddHHmmss@", "当前年月日时分秒" ); 
			put( "@yyyy:MM@", "当前年:月" ); 
			put( "@yyyy:MM:dd@", "当前年:月:日" ); 
			put( "@yyyy:MM:dd:HH@", "当前年:月:日 时" ); 
			put( "@yyyy:MM:dd HH:mm@", "当前年:月:日 时:分" ); 
			put( "@yyyy:MM:dd HH:mm:ss@", "当前年:月:日 时:分:秒" ); 
		}};
		return map;
	}
	
	/**
	 * 根据占位符返回有效的时间
	 * @param pattern
	 * @return
	 */
	public static String getPatternStr(String pattern){
		if(StringUtil.isBlank(pattern))  return "";
		pattern=pattern.trim();
		String str="";
		 String format_string="";
		Date now =new Date();
		if(pattern.equals("@yyyyMM@")){
			format_string="yyyyMM";
		}else if(pattern.equals("@yyyyMMdd@")){
			format_string="yyyyMMdd";
		}else if(pattern.equals("@yyyyMMddHH@")){
			format_string="yyyyMMddHH";
		}else if(pattern.equals("@yyyyMMddHHmm@")){
			format_string="yyyyMMddHHmm";
		}else if(pattern.equals("@yyyyMMddHHmmss@")){
			format_string="yyyyMMddHHmmss";
		}else if(pattern.equals("@yyyy:MM@")){
			format_string="yyyy:MM";
		}else if(pattern.equals("@yyyy:MM:dd@")){
			format_string="yyyy:MM:dd";
		}else if(pattern.equals("@yyyy:MM:dd HH@")){
			format_string="yyyy:MM:dd HH";
		}else if(pattern.equals("@yyyy:MM:dd HH:mm@")){
			format_string="yyyy:MM:dd HH:mm";
		}else if(pattern.equals("@yyyy:MM:dd HH:mm:ss@")){
			format_string="yyyy:MM:dd HH:mm:ss";
		}else{
			 return "";
		}
		str=DateUtil.formateDate(now, format_string);
		return str;
	}
	
	public static void main(String[] args){
		System.out.println(getFileSavePath("123","xxx.xls","xx"));
	}
}

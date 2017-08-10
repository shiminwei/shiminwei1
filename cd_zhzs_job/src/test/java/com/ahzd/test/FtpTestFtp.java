package com.ahzd.test;

import com.ahcd.common.FtpUtils;

public class FtpTestFtp {
	//下载
	public static void main(String[] args) {
		        boolean flag = FtpUtils.downFile("192.168.3.120", 21, "ftp_ahzd", "123456", "", "新建文本文档.txt", "D:/ftp");
		        System.out.println(flag);  
	}
	
	
	
	//上传
//	public static void main(String[] args) throws FileNotFoundException {
//		   String localPath = "D:/新建文本文档.txt";
//		   String isDelete = "1";
//		   boolean is= FtpUtils.uploadFile("192.168.3.120", 21, "ftp_ahzd", "123456", "",FileUtil.getFileName(localPath),localPath,isDelete);  
//		   System.out.println("操作结果为"+is);
//	}
}









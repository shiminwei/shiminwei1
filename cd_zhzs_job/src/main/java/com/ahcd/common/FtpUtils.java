package com.ahcd.common;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.ahcd.common.FileUtil;

public class FtpUtils {
	/** 
	 * Description: 向FTP服务器上传文件 
	 * @Version1.0 Jul 27, 2008 4:31:09 PM by 崔红保（cuihongbao@d-heaven.com）创建 
	 * @param url FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param path FTP服务器保存目录 
	 * @param filename 上传到FTP服务器上的文件名 
	 * @param input 输入流 
	 * @return 成功返回true，否则返回false 
	 * @throws IOException 
	 */ 
	public static boolean uploadFile(String url,int port,String username, String password, String Ftppath, String fileName,String localPath,String isDelete) throws IOException {  
		boolean success = false;  
	    FTPClient ftp = new FTPClient();  
	 //   try {  
	        int reply;  
	        ftp.connect(url, port);//连接FTP服务器  
	        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
	        ftp.login(username, password);//登录  
	        reply = ftp.getReplyCode();
	        if (!FTPReply.isPositiveCompletion(reply)) {  
	            ftp.disconnect();  
	            return success;  
	        }  
	        System.out.println("链接成功!");
	        boolean f = ftp.changeWorkingDirectory(Ftppath);  
	        System.out.println("跳转至ftp目录"+f);
	        ftp.setControlEncoding("iso-8859-1");
	        FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_VMS);
	        conf.setServerLanguageCode("zh");
	        FileInputStream input = new FileInputStream(new File(localPath)); 
	        Boolean is= ftp.storeFile(new String(fileName.getBytes("GBK"),"iso-8859-1"), input); 
	        System.out.println("上传"+is);
	        input.close();
	        if(!is){
	        	success = false;
	        }
	        if(isDelete.equals("1")){
	        	System.out.println("复制原文件");
	        	String newPath = "D:/bak/"+fileName;
	        	File file = new File("D:/bak/");
	        	if(!file.exists()){
	        		file.mkdir(); 
	        		System.out.println("新建bak文件夹成功!");
	        	}
	        	try {
				boolean  copyResult= FileUtil.CopyFile(localPath, newPath);
				if(copyResult){
					 boolean deleteRsult = FileUtil.deleteFile(localPath);
					 if(deleteRsult)
						 System.out.println("删除原文件成功");
					 else
						 System.out.println("删除原文件失败");
					    
				}else{
					System.out.println("文件复制失败");
				}
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
	        ftp.logout(); 
	        success = true;  
//	    } catch (IOException e) {  
//	        e.printStackTrace();  
//	    } finally {  
	        if (ftp.isConnected()) {  
	            try {  
	                ftp.disconnect();  
	            } catch (IOException ioe) {  
	            }  
	        }  
//	    }  
	    return success;  
	}  
	
	
	
	
	/** 
	 * Description: 从FTP服务器下载文件 
	 * @param url FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param remotePath FTP服务器上的相对路径 
	 * @param fileName 要下载的文件名 
	 * @param localPath 下载后保存到本地的路径 
	 * @return */
	public static boolean downFile(String url, int port,String username, String password, String remotePath,String fileName,String localPath) {  
	    boolean success = false;  
	    FTPClient ftp = new FTPClient();  
	    try {  
	        int reply;  
	        ftp.connect(url, port);  
	        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
	        ftp.login(username, password);//登录  
	        reply = ftp.getReplyCode();  
	        if (!FTPReply.isPositiveCompletion(reply)) {  
	            ftp.disconnect();  
	            return success;  
	        }  
	        System.out.println("登录成功!");
	        ftp.setRemoteVerificationEnabled(false);
            ftp.enterLocalPassiveMode();
	        boolean f = ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录  
	        System.out.println(f);
			ftp.setControlEncoding("iso-8859-1");// 注意编码格式
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
			conf.setServerLanguageCode("zh");//中文 
	        if(!new File(localPath).exists()){
	        	new File(localPath).mkdir(); 
        		System.out.println("新建目标文件夹成功!");
        	}
			File[] files = new File(localPath).listFiles();
			for (File ff : files) {
				String ftpFileName = ff.getName();
				if (fileName.equals(ftpFileName)) {
					String a = localPath + "/" + ftpFileName;
					new File(a).renameTo(new File(a + "-旧文件"));
					System.out.println("已将文件夹中重复文件名重名");
				}
        	}
	        if(f){
		        FTPFile[] fs = ftp.listFiles();  
		        for(FTPFile ff:fs){  
		        	String ftpFileName = new String(ff.getName().getBytes("iso-8859-1"), "GBK");
		            if(fileName.equals(ftpFileName)){  
		                File localFile = new File(localPath+"/"+ftpFileName);  
		                OutputStream is = new FileOutputStream(localFile);   
		                ftp.retrieveFile(ff.getName(), is);  
		                is.close();  
		            }  
		        }  
		        ftp.logout();  
		        success = true;  
		        System.out.println("下载成功");
	        }else{
	        	System.out.println("访问文件失败!");
	        }
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (ftp.isConnected()) {  
	            try {  
	                ftp.disconnect();  
	            } catch (IOException ioe) {  
	            }  
	        }  
	    }  
	    return success;  
	}
	
	
  
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

package com.ahcd.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class FileUtils {

	/**
	 * 
	 * 功能说明 : 判断文件夹是否存在 若不存在则创建
	 * 
	 * @author : fei yang
	 * @version ：2017年2月7日 下午2:15:30
	 * @param filePath
	 *            文件路径
	 */
	public static void mkdirs(String filePath) {
		if (EmptyUtils.isNotEmpty(filePath)) {
			File file = new File(filePath);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
		}
	}

	/**
	 * 
	 * 功能说明 : 判断当前路径是否是文件夹
	 * 
	 * @author : fei yang
	 * @version ：2017年2月9日 上午11:01:59
	 * @param filePath
	 * @return
	 */
	public static boolean isDirectory(String filePath) {
		File file = new File(filePath);
		if (file.exists() && file.isDirectory()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	   * 功能说明    : 创建新的报送目录
	   * @author   : fei yang 
	   * @version ：2017年2月10日 下午1:32:06 
	   * @param filePath
	   * @param dirNames
	 */
	public static void createSubdirectory(String filePath, String[] dirNames) {
		for (int i = 0; i < dirNames.length; i++) {
			String path = filePath + "/" + dirNames[i];
			mkdirs(path);
		}
	}

	/**
	 * 
	 * 功能说明 : 验证文件格式 true 不合格 false 合格
	 * 
	 * @author : fei yang
	 * @version ：2016年11月3日 上午11:36:00
	 * @param fileName
	 * @param fileType
	 * @return
	 */

	public static boolean vidateFileType(String fileName, String fileType) {

		if (EmptyUtils.isNotEmpty(fileName)&& fileName.lastIndexOf(".") >= 0) {
			String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (EmptyUtils.isEmpty(fileSuffix)) {
				return false;
			}
			String[] suffixs = fileType.split(",");
			for (String suffix : suffixs) {
				if (fileSuffix.equals(suffix))
					return true;
			}
		}
		return false;
	}
	public static void main(String[] args) {
		String filePath = "D:/123";
		createSubdirectory(filePath, Constant.needMkdirs);
	}
	

	/**
	 * 读取文件的内容 读取指定文件的内容
	 * 
	 * @param path
	 *            为要读取文件的绝对路径
	 * @return 以行读取文件后的内容。
	 * @since 1.0
	 */
	public static final String getFileContent(String path) throws IOException {
		String filecontent = "";
		try {
			File f = new File(path);
			if (f.exists()) {
				InputStream is=new FileInputStream(path);
				
				BufferedReader br = new BufferedReader(new InputStreamReader( is,"GBK")); // 建立BufferedReader对象，并实例化为br
				String line = br.readLine(); // 从文件读取一行字符串
				// 判断读取到的字符串是否不为空
				while (line != null) {
					filecontent += line + "\n";
					line = br.readLine(); // 从文件中继续读取一行数据
				}
				br.close(); // 关闭BufferedReader对象
				 is.close();
			}

		} catch (IOException e) {
			throw e;
		}
		return filecontent;
	}
}

package com.ahcd.common;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ahzd.pojo.DataJobStepTxtToDb;
public class ReadTxt {

	/**
	 * 功能说明 : 根据开始行结束行读文件
	 * 
	 * @param txtBean
	 * @param newMap
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static List<String[]> readText(DataJobStepTxtToDb txtBean) throws IOException {
		BufferedReader in = null;
		List<String[]> list = new ArrayList<String[]>();
		String encoding = "GBK";
		File f = new File(txtBean.getFilePath());
		if (f.isFile() && f.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(f), encoding);
			in = new BufferedReader(read);
			String line;
			int i = 1;
			while ((line = in.readLine()) != null) {
				if (i >= Integer.valueOf(txtBean.getBeginRowIndex())
						&& i <= Integer.valueOf(txtBean.getEndRowIndex())) {
					list.add(line.split(txtBean.getSeparator()));
				}
				i++;
			}
		}
		return list;
	}

	/**
	 * 
	 * 功能说明 : 判断页面传来的分隔符
	 * 
	 * @param sep
	 * @return
	 */
	public static String checkSep(String sep) {
		if ("\\t".equals(sep) || sep == "\\t") {
			sep = "\t";
		} else if ("|".equals(sep) || "^".equals(sep) || "*".equals(sep)) {
			sep = "\\" + sep;
		}
		return sep;
	}

	/**
	 * 
	 * 功能说明 : 获取表头
	 * 
	 * @author : feiyue yang
	 * @version ：2016年8月2日 上午11:34:03
	 * @param txtBean
	 * @throws IOException
	 */
	public static String[] getHead(InputStream ins, String separator) throws IOException {
		BufferedReader in = null;
		String[] str = null;
		String encoding = "GBK";
		InputStreamReader read = new InputStreamReader(ins, encoding);
		in = new BufferedReader(read);
		String line;
		int i = 0;
		while ((line = in.readLine()) != null) {
			i++;
			str = line.split(separator);
			if (i == 1) {
				break;
			}
		}
		read.close();
		return str;
	}

	/**
	 * 
	 * 功能说明 : 获取对应关系的序列对应关系
	 * 
	 * @param map
	 * @param str
	 * @return
	 */
	public static Map<String, String> getNewMap(Map<String, String> map, String[] str) {
		Set<String> set = map.keySet();
		for (String s : set) {
			for (int i = 0; i < str.length; i++) {
				if (map.get(s).equals(str[i])) {
					map.put(s, String.valueOf(i));
				}
			}
		}
		return map;
	}

	/**
	 * 
	 * 功能说明 :获取角标
	 * 
	 * @param newMap
	 * @param j
	 * @return
	 */
	public static int getCorner(Map<String, String> newMap, int j) {
		Collection<String> values = newMap.values();
		int i = 0;
		int re = 0;
		for (Object object : values) {
			if (i == j) {
				re = Integer.valueOf(object.toString());
			}
			i++;
		}
		return re;
	}

	/**
	 * 
	 * 功能说明 :获取动态 InsertSql
	 * 
	 * @param newMap
	 * @param tableName
	 * @return
	 */
	public static StringBuffer getNewInsertSql(Map<String, String> newMap, String tableName) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO " + tableName + " (");
		String columnName = "";
		String values = "";
		Set<String> set = newMap.keySet();
		for (String s : set) {
			columnName += s + ",";
			values += "?,";
		}
		columnName = columnName.substring(0, columnName.length() - 1);
		values = values.substring(0, values.length() - 1);
		sql.append(columnName + ") VALUES ( " + values + " )");
		return sql;
	}
	
	
	/**
	 * 
	 * 功能说明 : 动态获取 SelectSQL
	 * 
	 * @author : fei yang
	 * @version ：2016年8月3日 下午5:09:35
	 * @param map
	 * @param tableName
	 * @return
	 */
	public static String getSelectSql(Map<String, String> map, String oldSql) {
		oldSql = oldSql.substring(ignoreCaseIndexOf(oldSql, "FROM", 0), oldSql.length());
		String sql = "SELECT  ";
		String columnName = "";
		Set<String> set = map.keySet();
		for (String s : set) {
			columnName += s + ",";
		}
		columnName = columnName.substring(0, columnName.length() - 1);
		String newSql = sql + columnName + " " + oldSql;
		System.out.println(newSql);
		return newSql;
	}
	
	/**
	 * 
	 * 功能说明 : 返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始，不区分大小。
	 * 
	 * @author : fei yang
	 * @version ：2016年8月5日 下午5:21:48
	 * @param subject
	 * @param search
	 * @param fromIndex
	 * @return
	 */
	public static int ignoreCaseIndexOf(String subject, String search, int fromIndex) {
		if (subject == null || search == null) {
			throw new NullPointerException("输入的参数为空");
		}
		fromIndex = fromIndex < 0 ? 0 : fromIndex;
		if (search.equals("")) {
			return fromIndex >= subject.length() ? subject.length() : fromIndex;
		}
		int index1 = fromIndex;
		int index2 = 0;
		char c1;
		char c2;
		loop1: while (true) {
			if (index1 < subject.length()) {
				c1 = subject.charAt(index1);
				c2 = search.charAt(index2);
			} else {
				break loop1;
			}
			while (true) {
				if (isEqual(c1, c2)) {
					if (index1 < subject.length() - 1 && index2 < search.length() - 1) {
						c1 = subject.charAt(++index1);
						c2 = search.charAt(++index2);
					} else if (index2 == search.length() - 1) {
						return fromIndex;
					} else {
						break loop1;
					}
				} else {
					index2 = 0;
					break;
				}
			}
			index1 = ++fromIndex;
		}
		return -1;
	}
	
	/**
	 * 
	 * 功能说明 : 获取表头字符串
	 * 
	 * @author : fei yang
	 * @version ：2016年8月3日 下午5:20:52
	 * @param map
	 * @return
	 */
	public static String getHeadStr(Map<String, String> map, String sep) {
		String str = "";
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			str += map.get(key) + sep;
		}
		str = str.substring(0, str.length() - sep.length()) + "\r\n";
		return str;
	}
	
	/**
	 * 
	 * 功能说明 :判断两个字符是否相等。
	 * @param c1
	 * @param c2
	 * @return
	 */
	private static boolean isEqual(char c1, char c2) {
		// 字母小写 字母大写
		if (((97 <= c1 && c1 <= 122) || (65 <= c1 && c1 <= 90)) && ((97 <= c2 && c2 <= 122) || (65 <= c2 && c2 <= 90))
				&& ((c1 - c2 == 32) || (c2 - c1 == 32))) {
			return true;
		} else if (c1 == c2) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * 功能说明 : 判断文件是否存在不存在创建新文件
	 * @param path
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public static void isexitsPath(String path) throws InterruptedException, IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
	}
	
	/**
	 * 
	 * 功能说明 : TXT 内容写入
	 * @param fileName
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static boolean writeTxtFile(File fileName, List<String> list) throws Exception {

		boolean flag = false;
		FileOutputStream o = null;
			o = new FileOutputStream(fileName);
			for (int i = 0; i < list.size(); i++) {
				o.write(list.get(i).getBytes("GBK"));
			}
			o.close();
			flag = true;
		return flag;
	}
}

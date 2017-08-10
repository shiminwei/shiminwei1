package com.ahcd.common;

import java.util.ArrayList;
import java.util.List;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PingYinUtil {
	/**
	 * 
	 * 功能说明 : 将字符串中的中文转化为拼音,其他字符不变
	 * 
	 * @author : fei yang
	 * @version ：2016年11月2日 上午9:40:20
	 * @param inputString
	 * @return
	 */
	public static String getPingYin(String inputString) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);

		char[] input = inputString.trim().toCharArray();
		String output = "";

		try {
			for (int i = 0; i < input.length; i++) {
				if (java.lang.Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
					output += temp[0];
				} else
					output += java.lang.Character.toString(input[i]);
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return output;
	}

	/**
	 * 
	 * 功能说明 : 获取汉字串拼音首字母，英文字符不变
	 * 
	 * @author : fei yang
	 * @version ：2016年11月2日 上午9:39:46
	 * @param chinese
	 * @return
	 */
	public static String getFirstSpell(String chinese) {
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 128) {
				try {
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
					if (temp != null) {
						pybf.append(temp[0].charAt(0));
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pybf.append(arr[i]);
			}
		}
		return pybf.toString().replaceAll("\\W", "").trim();
	}

	/**
	 * 
	 * 功能说明 : 获取汉字串拼音，英文字符不变
	 * 
	 * @author : fei yang
	 * @version ：2016年11月2日 上午9:40:02
	 * @param chinese
	 * @return
	 */
	public static String getFullSpell(String chinese) {
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 128) {
				try {
					pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pybf.append(arr[i]);
			}
		}
		return pybf.toString();
	}

	/**
	 * 
	   * 功能说明    : 中文列转成首字母
	   * @author   : fei yang 
	   * @version ：2016年11月2日 上午10:02:50 
	   * @param cols
	   * @return
	 */
	public static List<String> getFirstSpellByList(List<Object> cols) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < cols.size(); i++) {
			list.add(getFirstSpell(cols.get(i).toString()));
		}
		return getNotRepeat(list);
	}

	
	/**
	 * 
	   * 功能说明    :  重复列加数值
	   * @author   : fei yang 
	   * @version ：2016年11月2日 上午10:02:19 
	   * @param list
	   * @return
	 */
	public static List<String> getNotRepeat(List<String> list) {
		List<String> tempList = new ArrayList<String>();
		int size = 1;
		for (String i : list) {
			if (!tempList.contains(i)) {
				tempList.add(i);
			} else {
				tempList.add(i + String.valueOf(size));
				size++;
			}
		}
		return tempList;
	}
}
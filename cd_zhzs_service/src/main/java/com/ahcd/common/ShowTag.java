package com.ahcd.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import com.ahcd.common.Constant;
import com.ahcd.common.StringUtil;

/**
 * 功能说明 : 自定义标签
 * 
 * @author fei yang
 * @version ：2016年11月2日 下午1:00:44
 *
 */
public class ShowTag extends SimpleTagSupport {

	private String value;

	private String type;// zq :返回周期的名称

	private String formatType;

	private Date dateValue;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFormatType() {
		return formatType;
	}

	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}

	public Date getDateValue() {
		return dateValue;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}

	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		if ("zq".equals(type)) {// 周期
			out.println(getZqName(value));
		} else if ("dateFormat".equals(type)) {
			out.println(getDateStr(dateValue, formatType));
		} else if ("reportType".equals(type)) {
			out.println(getReportTypeName(value));
		}

	}

	/**
	 * 
	 * 功能说明 : 获取周期的NAME
	 * 
	 * @author : fei yang
	 * @version ：2016年11月2日 下午1:00:44
	 * @param value
	 * @return
	 */
	public static String getZqName(String value) {
		if (StringUtil.isBlank(value)) {
			return value;
		}

		String str = "";
		Map<Integer, String> map = Constant.periodType();
		for (Integer key : map.keySet()) {
			if (key == Integer.valueOf(value)) {
				str = map.get(key);
			}
		}
		if (StringUtil.isBlank(str)) {
			str = value;
		}
		return str;
	}

	public static String getReportTypeName(String value) {
		String str = "";
		if (StringUtil.isBlank(value)) {
			return value;
		}
		if (value.equals("1")) {
			str="上报";
		}else if (value.equals("2")) {
			str="重报";
		}else if (value.equals("3")) {
			str="补报";
		}else if (value.equals("4")) {
			str="系统设置报送";
		}
		return str;
	}

	/**
	 * 
	 * 功能说明 :转换日期格式
	 * 
	 * @author : fei yang
	 * @version ：2016年11月14日 上午11:39:44
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String getDateStr(Date dateValue, String formatType) {
		if (dateValue == null) {
			return "";
		}
		if (StringUtil.isBlank(formatType)) {
			formatType = "yyyy年MM月dd日";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatType);
		return sdf.format(dateValue);
	}

	public static void main(String[] args) {
		// String str = getZqName("5");
		// System.out.println(str);

		Date date = new Date();
		System.out.println(date);

		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy年MM日dd日 HH:mm:ss ");
		String str = sdf.format(date);
		System.out.println(str);
	}

}

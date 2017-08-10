package com.ahcd.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.stereotype.Service;

import com.ahcd.common.StringUtil;
import com.ahcd.service.ZdConstantService;

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
		if ("dateFormat".equals(type)) {// 周期
			out.println(getDateStr(dateValue, formatType));
		} 

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

	}

}

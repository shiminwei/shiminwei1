package com.ahcd.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel 行内容
 * 
 * @author : fei yang
 * @version ：2017年4月10日 上午11:40:19
 */
public class ExcelRowBean {


	private Integer rowsIndex;// 所属行
	private List<ExcelColumnBean> excelColumnBeans;// 包含列的值
	public Integer getRowsIndex() {
		return rowsIndex;
	}
	public void setRowsIndex(Integer rowsIndex) {
		this.rowsIndex = rowsIndex;
	}
	public List<ExcelColumnBean> getExcelColumnBeans() {
		return excelColumnBeans;
	}
	public void setExcelColumnBeans(List<ExcelColumnBean> excelColumnBeans) {
		this.excelColumnBeans = excelColumnBeans;
	}

	
}

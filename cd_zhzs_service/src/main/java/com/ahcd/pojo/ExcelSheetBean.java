package com.ahcd.pojo;

import java.util.List;

/** 
 * Excel Sheet (表)内容
 * @author   : fei yang 
 * @version ：2017年4月10日 下午1:05:05 
 */
public class ExcelSheetBean {
	
	private Integer sheetIndex;// 所属表序列
	private ExcelRowBean tableHeader;// 表头
	private List<ExcelRowBean> rows;// Excel内容


	public Integer getSheetIndex() {
		return sheetIndex;
	}
	public void setSheetIndex(Integer sheetIndex) {
		this.sheetIndex = sheetIndex;
	}
	public ExcelRowBean getTableHeader() {
		return tableHeader;
	}
	public void setTableHeader(ExcelRowBean tableHeader) {
		this.tableHeader = tableHeader;
	}
	public List<ExcelRowBean> getRows() {
		return rows;
	}
	public void setRows(List<ExcelRowBean> rows) {
		this.rows = rows;
	}
	
	
}

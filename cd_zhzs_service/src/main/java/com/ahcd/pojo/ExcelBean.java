package com.ahcd.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel 实体
 * 
 * @author : fei yang
 * @version ：2017年4月10日 上午11:36:40
 */
public class ExcelBean {

	private String fileName;// 文件名称
	private List<ExcelSheetBean> excelSheetBeans;// 多个表内容
	private String type;// excle 的类型 1997 和2003
	private Integer contentSize;// 内容条数（不包括表头）

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<ExcelSheetBean> getExcelSheetBeans() {
		return excelSheetBeans;
	}

	public void setExcelSheetBeans(List<ExcelSheetBean> excelSheetBeans) {
		this.excelSheetBeans = excelSheetBeans;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getContentSize() {
		int size = 0;
		for (int i = 0; i < excelSheetBeans.size(); i++) {
			ExcelSheetBean excelSheetBean = excelSheetBeans.get(i);
			if (excelSheetBean != null && excelSheetBean.getRows() != null && excelSheetBean.getRows().size() > 0) {
				size += excelSheetBean.getRows().size();
			}
		}
		return size;
	}

	public void setContentSize(Integer contentSize) {
		this.contentSize = contentSize;
	}

}

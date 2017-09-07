package com.ahcd.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ahcd.pojo.ExcelBean;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.ExcelTemplateCol;
import com.ahcd.pojo.ProblemBean;
import com.ahcd.service.JsonServise;

public class ExcelUtils {


	/**
	 * 
	   * 功能说明    :根据流获取excel内容 
	   * @author   : fei yang 
	   * @version ：2017年2月17日 上午9:10:17 
	   * @param is
	   * @return
	 */
	@SuppressWarnings("resource")
	public static ExcelBean getExcelBean(InputStream is) {
		ExcelBean excelBean = new ExcelBean();
		try {
			HSSFWorkbook wbs = new HSSFWorkbook(is);
			HSSFSheet childSheet = wbs.getSheetAt(0);
			excelBean.setRowsNum(childSheet.getPhysicalNumberOfRows());
			int beginRow = 0;// 开始行
			int endRow = childSheet.getLastRowNum();// 结束行
			for (int j = beginRow; j <= endRow; j++) {
				HSSFRow row = childSheet.getRow(j);
				List<String> list = new ArrayList<String>();
				if (null != row) {
					for (int k = 0; k < row.getLastCellNum(); k++) {
						HSSFCell cell = row.getCell(k);
						list = getCellType(cell, list);
					}
				}
				if (j == 0) {
					excelBean.setColName(list);
				} else {
					excelBean.getContent().add(list);
				}
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return excelBean;
	}
	
	
	/**
	 * 
	 * 功能说明 :校验HSSFCell 内容 返回想要的值
	 * 
	 * @author : fei yang
	 * @version ：2016年11月3日 下午3:22:54
	 * @param cell
	 * @param list
	 * @return
	 */
	public static List<String> getCellType(HSSFCell cell, List<String> list) {
		if (null != cell) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数字
				list.add(getExcelDateString(cell).trim());
				break;
			case HSSFCell.CELL_TYPE_STRING: // 字符串
				list.add(cell.getStringCellValue().trim());
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				list.add(cell.getStringCellValue().trim());
				break;
			case HSSFCell.CELL_TYPE_FORMULA: // 公式
				String value = "";
				try {
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					value = String.valueOf(cell.getNumericCellValue());
				} catch (IllegalStateException e) {
					value = String.valueOf(cell.getRichStringCellValue());
				}
				list.add(value);
				break;
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				list.add(" ");
				break;
			default:
				break;
			}
		} else {
			list.add(" ");
		}
		return list;
	}

	/**
	 * 
	   * 功能说明    :获取EXCELcell的时间字符串 
	   * @author   : fei yang 
	   * @version ：2017年2月17日 上午9:09:49 
	   * @param cell
	   * @return
	 */
	public static String getExcelDateString(HSSFCell cell) {

		if (HSSFDateUtil.isCellDateFormatted(cell)) {// 日期
			Date d = cell.getDateCellValue();
			String str = String.valueOf(new Timestamp(d.getTime()));
			str = str.replace(" ", "");
			str = str.substring(0, 10);
			return str;
		} else if (cell.getCellStyle().getDataFormat() == 31) {
			double value = cell.getNumericCellValue();
			Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
			String str = String.valueOf(new Timestamp(date.getTime()));
			str = str.replace(" ", "");
			str = str.substring(0, 10);
			return str;
		} else {
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			return cell.getStringCellValue();
		}

	}

	/**
	 * 
	 * 功能说明 :校验表头是否一致
	 * 
	 * @author : fei yang
	 * @version ：2017年2月16日 下午5:15:02
	 * @param excelCols
	 * @param templateColNames
	 * @return
	 */
	public static boolean checkHeadIsSame(List<String> excelCols, List<ExcelTemplateCol> templateColNames) {
		System.out.println("111");
		boolean flag = true;
		if (excelCols.size() > 0 && templateColNames.size() >= 0 && excelCols.size() == templateColNames.size()) {
			for (int i = 0; i < excelCols.size(); i++) {
				if (!excelCols.get(i).trim().equals(templateColNames.get(i).getName().trim())) {
					return false;
				}
			}
		} else {
			return false;
		}
		return flag;

	}

	/**
	 * 
	 * 功能说明 :
	 * 
	 * @author : fei yang
	 * @version ：2016年11月4日 下午3:56:59
	 * @param objList
	 * @param template
	 * @return -1 //列值与模板不一致 -2 //字符串数字过大
	 */
	public static List<ProblemBean> checkExeclContent(List<Object> objList, ExcelTemplate template) {
		Pattern intPattern = Pattern.compile("[0-9]*");
		Pattern flaPattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
		List<ProblemBean> problems = new ArrayList<ProblemBean>();
		int colSize = template.getCols().size();
		for (int i = 1; i < objList.size(); i++) {
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) objList.get(i);
			if (list.size() != colSize) {// 列值与模板不一致

				problems.add(new ProblemBean(i, 0, Constant.PROBLEM_TYPE_1));
				return problems;
			}
			for (int j = 0; j < template.getCols().size(); j++) {
				ExcelTemplateCol col = template.getCols().get(j);
				// if (!StringUtil.isBlank(list.get(j))) {
				if (EmptyUtils.isNotEmpty(list.get(j))) {
					if (col.getColType() == 1) {// 字符串类型
						if (list.get(j).length() >= col.getColLength()) {
							problems.add(new ProblemBean(i, j, Constant.PROBLEM_TYPE_2));
						}
					} else if (col.getColType() == 2) {// 数字类型
						String newNumber = Constant.replaceNumber(list.get(j));
						if (newNumber.length() >= col.getColLength()) {
							problems.add(new ProblemBean(i, j, Constant.PROBLEM_TYPE_3));
						} else {
							if (!intPattern.matcher(newNumber).matches()) {
								problems.add(new ProblemBean(i, j, Constant.PROBLEM_TYPE_4));
							}
						}
					} else if (col.getColType() == 3) {// 浮点类型
						String newNumber = Constant.replaceNumber(list.get(j));
						if (newNumber.length() >= col.getColLength()) {
							problems.add(new ProblemBean(i, j, Constant.PROBLEM_TYPE_5));
						} else {
							if (!flaPattern.matcher(newNumber).matches()) {
								problems.add(new ProblemBean(i, j, Constant.PROBLEM_TYPE_6));
							}
						}
					} else if (col.getColType() == 4) {// 时间类型

						if (EmptyUtils.isEmpty(Constant.formatDate(list.get(j)))) {
							problems.add(new ProblemBean(i, j, Constant.PROBLEM_TYPE_7 + "当前时间格式为:" + list.get(j)));
						}
					}
				}
			}
		}
		return problems;
	}

	
	/**
	 * 
	   * 功能说明    : 检验EXCEL
	   * @author   : fei yang 
	   * @version ：2017年2月17日 上午9:06:49 
	   * @param is  excle的文件流
	   * @param template  系统模板
	   * @param isCheckHead     true 检查表头 ， flase 检查内容
	   * @return
	 */
	public static ExcelBean checkExcel(InputStream is, ExcelTemplate template, Boolean isCheckHead) {
		ExcelBean excelBean = new ExcelBean();
		try {
			excelBean = getExcelBean(is);
			List<String> colNameList = excelBean.getColName();
			List<Object> contentList = excelBean.getContent();
			if (isCheckHead) {
				if (excelBean.getRowsNum() <= 0) {
					excelBean.setCheckResults(-5);// 文件内容为空
					return excelBean;
				} else {
					excelBean.setCheckResults(1);
					return excelBean;
				}
			} else {
				if (excelBean.getRowsNum() <= 1) {
					excelBean.setCheckResults(-5);// 文件内容为空
					return excelBean;
				} else {
					if (checkHeadIsSame(colNameList, template.getCols())) {
						excelBean.setProblems(checkExeclContent(contentList, template));
						if (excelBean.getProblems().size() <= 0) {
							excelBean.setCheckResults(1);// 可以上传
							return excelBean;
						} else {
							excelBean.setCheckResults(-6);// 文件内容存在问题
							return excelBean;
						}
					} else {
						System.out.println("表头验证未通过");
						excelBean.setCheckResults(-4); // 上传文件与模板不一致
						return excelBean;
					}
				}
			}
		} catch (Exception e) {
			return new ExcelBean();
		}
	}
	

	public static void main(String[] args) {
		try {
			// String fileName = "D:/国税局入库信息模板_201605.xlsx";
			String fileName = "D:/ce1.xls";
			File file = new File(fileName);
			ExcelTemplate template = JsonServise.getExcelTemplateByName("测试数据");
			System.out.println(template.getName());
			ExcelBean excelBean = checkExcel(new FileInputStream(file), template, false);
			System.out.println("hangshu==>" + excelBean.getRowsNum());
			System.out.println("状态===》" + excelBean.getCheckResults());
			for (int i = 0; i < excelBean.getProblems().size(); i++) {
				System.out.println(excelBean.getProblems().get(i).getContent());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
 
}

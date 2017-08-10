package com.ahcd.common;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;

import com.ahcd.dbutil.JDBCUtils;
import com.ahcd.pojo.ExcelBean;
import com.ahcd.pojo.ExcelColumnBean;
import com.ahcd.pojo.ExcelRowBean;
import com.ahcd.pojo.ExcelSheetBean;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.ExcelTemplateCol;
import com.ahcd.pojo.ProblemBean;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.pojo.WebDataReportBean;

public class ExcelBase {

	/**
	 * 
	 * 功能说明 : 读取Excel 返回ExcelBean(1997 后缀xls文件)
	 * 
	 * @author : fei yang
	 * @version ：2017年4月10日 下午5:04:39
	 * @param is
	 *            文件流
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	@SuppressWarnings("resource")
	public static ExcelBean readExcel1997(InputStream is, String fileName) {
		if (is == null || StringUtil.isBlank(fileName)) {// 文件流 或者文件名称为空
			return null;
		}
		if (!FileUtil.vidateFileType(fileName, "xls")) {// 文件格式不匹配
			return null;
		}
		ExcelBean excelBean = new ExcelBean();
		excelBean.setFileName(fileName);
		excelBean.setType(fileName.substring(fileName.lastIndexOf(".") + 1));
		List<ExcelSheetBean> excelSheetBeans = new ArrayList<ExcelSheetBean>();
		try {
			HSSFWorkbook wbs = new HSSFWorkbook(is);
			for (int i = 0; i < wbs.getNumberOfSheets(); i++) {
				ExcelSheetBean excelSheetBean = new ExcelSheetBean();
				excelSheetBean.setSheetIndex(i);
				List<ExcelRowBean> excelRowBeans = new ArrayList<ExcelRowBean>();
				HSSFSheet childSheet = wbs.getSheetAt(i);
				for (int j = 0; j <= childSheet.getLastRowNum(); j++) {
					ExcelRowBean excelRowBean = new ExcelRowBean();
					excelRowBean.setRowsIndex(j);
					List<ExcelColumnBean> excelColumnBeans = new ArrayList<ExcelColumnBean>();
					HSSFRow row = childSheet.getRow(j);
					if (row != null) {
						for (int k = 0; k < row.getLastCellNum(); k++) {
							HSSFCell cell = row.getCell(k);
							ExcelColumnBean excelColumnBean = getContentByCellType(cell);
							excelColumnBean.setColumnIndex(k);
							excelColumnBeans.add(excelColumnBean);
						}
						excelRowBean.setExcelColumnBeans(excelColumnBeans);
					}
					excelRowBean.setExcelColumnBeans(excelColumnBeans);
					if (j == 0) {// 读取表头
						excelSheetBean.setTableHeader(excelRowBean);
					} else {// 读取表内容
						excelRowBeans.add(excelRowBean);
					}
				}
				excelSheetBean.setRows(excelRowBeans);
				/**
				 * 下面判断空的Sheet
				 */
				if (excelSheetBean != null) {
					if (excelSheetBean.getTableHeader() == null
							|| excelSheetBean.getTableHeader().getExcelColumnBeans() == null
							|| excelSheetBean.getTableHeader().getExcelColumnBeans().size() <= 0) {
						excelSheetBean.setTableHeader(null);
					}
					if (excelSheetBean.getRows() == null || excelSheetBean.getRows().size() <= 0) {
						excelSheetBean.setRows(null);
					}
				}
				if (StringUtil.isBlank(excelSheetBean.getTableHeader())
						&& StringUtil.isBlank(excelSheetBean.getRows())) {
					excelSheetBean = null;
				}
				excelSheetBeans.add(excelSheetBean);
			}
			excelBean.setExcelSheetBeans(excelSheetBeans);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return excelBean;
	}

	/**
	 * 
	 * 功能说明 : 读取Excel 返回ExcelBean（ 2007 后缀xlsx文件）
	 * 
	 * @author : fei yang
	 * @version ：2017年4月10日 下午5:04:39
	 * @param is
	 *            文件流
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	@SuppressWarnings("resource")
	public static ExcelBean readExcel2003(InputStream is, String fileName) {
		if (is == null || StringUtil.isBlank(fileName)) {// 文件流 或者文件名称为空
			return null;
		}
		if (!FileUtil.vidateFileType(fileName, "xlsx")) {// 文件格式不匹配
			return null;
		}
		ExcelBean excelBean = new ExcelBean();
		excelBean.setFileName(fileName);
		excelBean.setType(fileName.substring(fileName.lastIndexOf(".") + 1));
		List<ExcelSheetBean> excelSheetBeans = new ArrayList<ExcelSheetBean>();
		try {
			XSSFWorkbook wbs = new XSSFWorkbook(is);
			for (int i = 0; i < wbs.getNumberOfSheets(); i++) {
				ExcelSheetBean excelSheetBean = new ExcelSheetBean();
				excelSheetBean.setSheetIndex(i);
				List<ExcelRowBean> excelRowBeans = new ArrayList<ExcelRowBean>();
				XSSFSheet childSheet = wbs.getSheetAt(i);
				for (int j = 0; j <= childSheet.getLastRowNum(); j++) {
					ExcelRowBean excelRowBean = new ExcelRowBean();
					excelRowBean.setRowsIndex(j);
					List<ExcelColumnBean> excelColumnBeans = new ArrayList<ExcelColumnBean>();
					XSSFRow row = childSheet.getRow(j);
					if (row != null) {
						for (int k = 0; k < row.getLastCellNum(); k++) {
							XSSFCell cell = row.getCell(k);
							ExcelColumnBean excelColumnBean = getContentByCellType(cell);
							excelColumnBean.setColumnIndex(k);
							excelColumnBeans.add(excelColumnBean);
						}
						excelRowBean.setExcelColumnBeans(excelColumnBeans);
					}
					excelRowBean.setExcelColumnBeans(excelColumnBeans);
					if (j == 0) {// 读取表头
						excelSheetBean.setTableHeader(excelRowBean);
					} else {// 读取表内容
						excelRowBeans.add(excelRowBean);
					}
				}
				excelSheetBean.setRows(excelRowBeans);
				/**
				 * 下面判断空的Sheet
				 */
				if (excelSheetBean != null) {
					if (excelSheetBean.getTableHeader() == null
							|| excelSheetBean.getTableHeader().getExcelColumnBeans() == null
							|| excelSheetBean.getTableHeader().getExcelColumnBeans().size() <= 0) {
						excelSheetBean.setTableHeader(null);
					}
					if (excelSheetBean.getRows() == null || excelSheetBean.getRows().size() <= 0) {
						excelSheetBean.setRows(null);
					}
				}
				if (StringUtil.isBlank(excelSheetBean.getTableHeader())
						&& StringUtil.isBlank(excelSheetBean.getRows())) {
					excelSheetBean = null;
				}
				excelSheetBeans.add(excelSheetBean);
			}
			excelBean.setExcelSheetBeans(excelSheetBeans);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return excelBean;
	}

	/**
	 * 
	 * 功能说明 :校验HSSFCell内容 返回想要的值(1997)
	 * 
	 * @author : fei yang
	 * @version ：2016年11月3日 下午3:22:54
	 * @param cell
	 * @return
	 */
	public static ExcelColumnBean getContentByCellType(HSSFCell cell) {
		if (null != cell) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数字
				String dateString = getExcelDateString(cell.getNumericCellValue(), cell.getCellStyle().getDataFormat());
				if (StringUtil.isBlank(dateString)) {
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					return new ExcelColumnBean(HSSFCell.CELL_TYPE_NUMERIC, cell.getStringCellValue());
				} else {
					return new ExcelColumnBean(HSSFCell.CELL_TYPE_NUMERIC, dateString);
				}
			case HSSFCell.CELL_TYPE_STRING: // 字符串
				new ExcelColumnBean(HSSFCell.CELL_TYPE_STRING, cell.getStringCellValue().trim());
			case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				new ExcelColumnBean(HSSFCell.CELL_TYPE_BOOLEAN, cell.getStringCellValue().trim());
			case HSSFCell.CELL_TYPE_FORMULA: // 公式
				Object value = "";
				try {
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					value = cell.getNumericCellValue();
				} catch (IllegalStateException e) {
					value = cell.getRichStringCellValue();
				}
				return new ExcelColumnBean(HSSFCell.CELL_TYPE_FORMULA, value);
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				return new ExcelColumnBean(HSSFCell.CELL_TYPE_ERROR, " ");
			default:
				return new ExcelColumnBean(0, " ");
			}
		} else {
			return new ExcelColumnBean(0, " ");
		}
	}

	/**
	 * 
	 * 功能说明 :校验XSSFCell内容 返回想要的值(2003)
	 * 
	 * @author : fei yang
	 * @version ：2016年11月3日 下午3:22:54
	 * @param cell
	 * @return
	 */
	public static ExcelColumnBean getContentByCellType(XSSFCell cell) {
		if (null != cell) {
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_NUMERIC: // 数字
				String dateString = getExcelDateString(cell.getNumericCellValue(), cell.getCellStyle().getDataFormat());
				if (StringUtil.isBlank(dateString)) {
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					return new ExcelColumnBean(XSSFCell.CELL_TYPE_NUMERIC, cell.getStringCellValue());// 4是时间格式
				} else {
					return new ExcelColumnBean(XSSFCell.CELL_TYPE_NUMERIC, dateString);
				}
			case XSSFCell.CELL_TYPE_STRING: // 字符串
				new ExcelColumnBean(XSSFCell.CELL_TYPE_STRING, cell.getStringCellValue().trim());
			case XSSFCell.CELL_TYPE_BOOLEAN: // Boolean
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				new ExcelColumnBean(XSSFCell.CELL_TYPE_BOOLEAN, cell.getStringCellValue().trim());
			case XSSFCell.CELL_TYPE_FORMULA: // 公式
				Object value = "";
				try {
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					value = cell.getNumericCellValue();
				} catch (IllegalStateException e) {
					value = cell.getRichStringCellValue();
				}
				return new ExcelColumnBean(XSSFCell.CELL_TYPE_FORMULA, value);
			case XSSFCell.CELL_TYPE_ERROR: // 故障
				return new ExcelColumnBean(XSSFCell.CELL_TYPE_ERROR, " ");
			default:
				return new ExcelColumnBean(0, " ");
			}
		} else {
			return new ExcelColumnBean(0, " ");
		}
	}

	/**
	 * 
	 * 功能说明 :读取excel表中时间和日期的字符串
	 * 
	 * @author : fei yang
	 * @version ：2017年4月10日 下午5:27:59
	 * @param value
	 *            读取EXCEL 的NumericCellValue值 如果是日期或者时间的话可能读出来是数字
	 * @param format
	 *            Cell DataFormat() 类型
	 * @return
	 */
	public static String getExcelDateString(double value, short format) {
		String result = "";
		try {
			SimpleDateFormat sdf = null;
			
			if (format == 14 || format == 31 || format == 57 || format == 58 || (format >= 176 && format <= 189)) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
				if (sdf != null) {
					result = sdf.format(date);
				}
			}
		} catch (Exception e) {
			return result;
		}
		return result;
	}

	/**
	 * 
	 * 功能说明 : 根据文件流以及文件名称读取Excel
	 * 
	 * @author : fei yang
	 * @version ：2017年4月10日 下午5:10:10
	 * @param is
	 *            文件流
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public static ExcelBean readExcel(InputStream is, String fileName) {
		if (is == null || StringUtil.isBlank(fileName)) {// 文件流 或者文件名称为空
			return null;
		}
		if (!FileUtil.vidateFileType(fileName, "xls,xlsx")) {// 文件格式不匹配
			return null;
		}
		ExcelBean bean = new ExcelBean();
		if (FileUtil.vidateFileType(fileName, "xls")) {// 1997exlce 文件读取
			bean = readExcel1997(is, fileName);
		} else if (FileUtil.vidateFileType(fileName, "xlsx")) {// 2003exlce 文件读取
			bean = readExcel2003(is, fileName);
		} else {
			bean = null;
		}
		return bean;
	}

	/**
	 * 
	 * 功能说明 : 根据 ExcelTemplate 对象写Excel 1997（目前只写列名）
	 * 
	 * @author : fei yang
	 * @version ：2017年4月11日 上午9:38:53
	 * @param template
	 * @return 返回工作簿 HSSFWorkbook
	 */
	public static HSSFWorkbook toWriteExcel(ExcelTemplate template) {
		if (template == null || StringUtil.isBlank(template.getName()) || StringUtil.isBlank(template.getCols())) {
			return null;
		}
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Sheet1");
			HSSFRow row1 = sheet.createRow(0);
			for (int i = 0; i < template.getCols().size(); i++) {
				String cellValue = template.getCols().get(i).getName();
				if (StringUtil.isBlank(cellValue)) {
					cellValue = "";
				}
				row1.createCell(i).setCellValue(cellValue);
				sheet.setColumnWidth(i, cellValue.getBytes().length * 256);// 列自适应宽度的设置
			}
			return wb;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * 功能说明 :校验模板内容合法性 返回存在的问题内容
	 * 
	 * @author : fei yang
	 * @version ：2017年4月13日 下午2:50:24
	 * @param bean
	 * @param template
	 * @return
	 */
	public static List<ProblemBean> checkExeclContent(ExcelBean bean, ExcelTemplate template) {
		List<ProblemBean> problems = new ArrayList<ProblemBean>();
		if (template != null && bean != null && bean.getExcelSheetBeans() != null
				&& bean.getExcelSheetBeans().size() > 0) {
			Pattern intPattern = Pattern.compile("[0-9]*");
			Pattern flaPattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
			for (int i = 0; i < bean.getExcelSheetBeans().size(); i++) {
				ExcelSheetBean excelSheetBean = bean.getExcelSheetBeans().get(i);
				if (excelSheetBean != null) {
					// 以下部分是判断表格 头部是否匹配
					ExcelRowBean tableHeader = excelSheetBean.getTableHeader();// 表头
					if (tableHeader != null && template.getCols() != null && tableHeader.getExcelColumnBeans() != null
							&& tableHeader.getExcelColumnBeans().size() == template.getCols().size()) {
						for (int j = 0; j < template.getCols().size(); j++) {
							if (!template.getCols().get(j).getName()
									.equals(tableHeader.getExcelColumnBeans().get(j).getContent().toString())) {
								problems.add(new ProblemBean(i, 0, j, Constant.PROBLEM_TYPE_1));// 列值与模板不一致
							}
						}
					} else {
						problems.add(new ProblemBean(i, 0, 0, Constant.PROBLEM_TYPE_1));// 列值与模板不一致
					}
					// 以下部分是判断表格内容格式
					List<ExcelRowBean> rowBeans = excelSheetBean.getRows();// 表头
					if (rowBeans != null && rowBeans.size() > 0) {
						for (int j = 0; j < rowBeans.size(); j++) {
							List<ExcelColumnBean> columnBean = rowBeans.get(j).getExcelColumnBeans();
							if (columnBean != null && columnBean.size() > 0
									&& columnBean.size() == template.getCols().size()) {

								for (int k = 0; k < template.getCols().size(); k++) {
									ExcelTemplateCol col = template.getCols().get(k);
									if (col.getColType() == 1) {// 字符串类型
										if (columnBean.get(k).getContent().toString().getBytes().length > col
												.getColLength()) {
											problems.add(new ProblemBean(i, j, k, Constant.PROBLEM_TYPE_2));// 字符串长度过长
										}
									} else if (col.getColType() == 2) {// 数字类型
										String newNumber = Constant
												.replaceNumber(columnBean.get(k).getContent().toString());
										if (!StringUtil.isInteger(newNumber)) {
											problems.add(new ProblemBean(i, j, k, Constant.PROBLEM_TYPE_4));// 整数值有误,请检查是否是整数

										}
										/*
										 * if (newNumber.length() >=
										 * col.getColLength()) {
										 * problems.add(new ProblemBean(i, j, k,
										 * Constant.PROBLEM_TYPE_3));//
										 * 整数值有误,长度过大 } else { if
										 * (!intPattern.matcher(newNumber).
										 * matches()) { problems.add(new
										 * ProblemBean(i, j, k,
										 * Constant.PROBLEM_TYPE_4));//
										 * 整数值有误,请检查是否是整数 } }
										 */
									} else if (col.getColType() == 3) {// 浮点类型
										String newNumber = Constant
												.replaceNumber(columnBean.get(k).getContent().toString());
										if (newNumber.length() >= col.getColLength()) {
											problems.add(new ProblemBean(i, j, k, Constant.PROBLEM_TYPE_5));// 浮点数值有误,长度过大
										} else {
											if (!flaPattern.matcher(newNumber).matches()) {
												problems.add(new ProblemBean(i, j, k, Constant.PROBLEM_TYPE_6));// 浮点数值有误,,请检查是否是浮点数
											}
										}
									} else if (col.getColType() == 4) {// 时间类型
										if (StringUtil.isBlank(
												Constant.formatDate(columnBean.get(k).getContent().toString()))) {
											problems.add(new ProblemBean(i, j, k, Constant.PROBLEM_TYPE_7 + "当前时间格式为:"
													+ columnBean.get(k).getContent().toString()));
										}
									}
								}

							} else {
								problems.add(new ProblemBean(i, j, 0, Constant.PROBLEM_TYPE_1));// 列值与模板不一致
							}
						}
					} else {
						ProblemBean problemBean = new ProblemBean(i, 1, 0, Constant.PROBLEM_TYPE_8);
						problems.add(problemBean);// 上报的数据为空
					}

				}
			}

		}
		return problems;
	}

	/**
	 * 
	 * 功能说明 : 获取INSERT SQL模型
	 * 
	 * @author : fei yang
	 * @version ：2016年11月4日 上午11:25:34
	 * @param template
	 * @param user
	 * @param webDataReportBean
	 * @return
	 */
	public static StringBuffer getNewInsertSql(ExcelTemplate template, SysReportUser user,
			WebDataReportBean webDataReportBean) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO " + template.getTableName() + " ( ");
		String columnName = getFixedCols(template, webDataReportBean, user, 1);// 获取固定的列名
		String values = getFixedCols(template, webDataReportBean, user, 2);// 获取固定的值
		for (int i = 0; i < template.getCols().size(); i++) {
			columnName += template.getCols().get(i).getColumnName() + ",";
			values += "?,";
		}
		columnName = columnName.substring(0, columnName.length() - 1);
		values = values.substring(0, values.length() - 1);
		sql.append(columnName + ") VALUES ( " + values + " )");
		System.out.println("INSERT SQL》" + sql);
		return sql;
	}

	/**
	 * 
	 * 功能说明 : 重新报送更改K_DEL 值
	 * 
	 * @author : fei yang
	 * @version ：2017年4月13日 下午3:02:00
	 * @param webDataReportBean
	 * @param tableName
	 * @return
	 */
	public static String getUpdateSql(WebDataReportBean webDataReportBean, String tableName) {
		String sql = "  update " + tableName + "  set k_del = 1 ";
		sql += "where k_year= '" + webDataReportBean.getYear() + "'";
		sql += "and k_month= '" + webDataReportBean.getMonth() + "'";
		return sql;
	}

	/**
	 * 
	 * 功能说明 : 固定列名和值
	 * 
	 * @author : fei yang
	 * @version ：2016年11月4日 上午11:25:07
	 * @param template
	 * @param webDataReportBean
	 * @param user
	 * @param type
	 * @return
	 */
	public static String getFixedCols(ExcelTemplate template, WebDataReportBean webDataReportBean, SysReportUser user,
			int type) {
		String str = "";
		if (type == 1) {
			str += template.getIdColName() + ",";// 主键ID
			str += template.getkDateColName() + ",";// 报送时间
			str += template.getYearColName() + ",";// 所属年份
			str += template.getMonthColName() + ",";// 所属月份
			str += template.getAreaColName() + ",";// 地区ID
			str += template.getDepartmentColName() + ",";// 部门ID
			str += template.getUserColName() + ",";// 用户ID
			str += template.getDelColName() + ",";// 数据是否有效
		} else {
			str += "?,";
			str += "SYSDATE ,";
			str += "'" + webDataReportBean.getYear() + "',";
			str += "'" + webDataReportBean.getMonth() + "',";
			str += "'" + user.getAreaId() + "',";
			str += "'" + user.getDepartmentId() + "',";
			str += "'" + user.getUserId() + "',";
			str += "'0',";
		}
		return str;
	}

	/**
	 * 
	 * 功能说明 : excle导入ORCLE
	 * 
	 * @author : fei yang
	 * @version ：2016年11月4日 上午11:26:43
	 * @param objList
	 * @param template
	 * @param user
	 * @param webDataReportBean
	 * @return
	 * @throws ClassNotFoundException
	 */
	@Transactional
	public static boolean execlImpToDb(ExcelBean excelBean, ExcelTemplate template, SysReportUser user,
			WebDataReportBean webDataReportBean) throws ClassNotFoundException {
		Connection con = null;
		PreparedStatement pst = null;
		Statement sm = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean falg = false;
		try {
			StringBuffer sql = getNewInsertSql(template, user, webDataReportBean);
			con = JDBCUtils.getConnection(template.getDatasource());
			con.setAutoCommit(false);
			if (webDataReportBean.getReportType().equals("2")) {// 重报
				String updateSql = getUpdateSql(webDataReportBean, template.getTableName());
				sm = con.createStatement();
				sm.executeUpdate(updateSql);
			}
			JDBCUtils.startTransaction(template.getDatasource());
			pst = (PreparedStatement) con.prepareStatement(sql.toString());
			for (int i = 0; i < excelBean.getExcelSheetBeans().size(); i++) {
				if (excelBean.getExcelSheetBeans().get(i) != null) {
					List<ExcelRowBean> rowBeans = excelBean.getExcelSheetBeans().get(i).getRows();
					if (rowBeans != null && rowBeans.size() > 0) {
						for (int j = 0; j < rowBeans.size(); j++) {
							pst.setString(1, UUID.randomUUID().toString().replace("-", ""));			
							List<ExcelColumnBean> columnBeans = rowBeans.get(j).getExcelColumnBeans();	
							for (int k = 0; k < columnBeans.size(); k++) {
								try {
									if (template.getCols().get(k).getColType() == 1) {// 字符串
										pst.setString(k + 2, columnBeans.get(k).getContent().toString());
									} else if (template.getCols().get(k).getColType() == 2) {// 整数
										pst.setInt(k + 2, Integer.valueOf(columnBeans.get(k).getContent().toString()));
									} else if (template.getCols().get(k).getColType() == 3) {// 浮点
										pst.setDouble(k + 2, Double.valueOf(columnBeans.get(k).getContent().toString()));
									}else if (template.getCols().get(k).getColType() == 4) {// 时间
										java.sql.Date date = new java.sql.Date(
												sdf.parse(columnBeans.get(k).getContent().toString()).getTime());
										pst.setDate(k + 2, date);
									}else {
										System.out.println("插入失败，已经回滚！");								
										con.setAutoCommit(true);
									}
								} catch (Exception e) {
									System.out.println("插入失败，已经回滚！");
									e.printStackTrace();
									con.setAutoCommit(true);
								}
							}
							pst.addBatch();
						}
					}
				}
			}
			pst.executeBatch();
			con.commit();
			pst.close();
			JDBCUtils.close(template.getDatasource());
			falg = true;
		} catch (SQLException e) {
			System.out.println(e.toString());
			try {
				if (!con.isClosed()) {
					con.rollback();
					System.out.println("插入失败，已经回滚！");
					con.setAutoCommit(true);

				}
			} catch (SQLException e1) {
				System.out.println(e1.toString());
				e1.printStackTrace();
			}
		}
		return falg;
	}

}

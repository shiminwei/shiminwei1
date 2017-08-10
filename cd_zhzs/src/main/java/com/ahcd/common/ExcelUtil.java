package com.ahcd.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.ahcd.dbutil.JDBCUtils;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.ExcelTemplateCol;
import com.ahcd.pojo.ProblemBean;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.pojo.WebDataReportBean;

public class ExcelUtil {
	private final static String uploadExcelPath = PropertyManager.getConfigProperty("config_path")
			+ PropertyManager.getConfigProperty("upload_excel_path") + "excel/";

	public static List<ExcelTemplateCol> importExcelTemplate(MultipartFile mFile) {
		List<ExcelTemplateCol> cols = new ArrayList<ExcelTemplateCol>();
		String fileName = mFile.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		String ym = new SimpleDateFormat("yyyy-MM").format(new Date());
		String filePath = uploadExcelPath + ym + fileName;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
				file.mkdirs();
			} else {
				file.mkdirs();
			}
			mFile.transferTo(file);

			if ("xls".equals(suffix) || "XLS".equals(suffix)) {
				cols = importXls(file);
			} else if ("xlsx".equals(suffix) || "XLSX".equals(suffix)) {
				cols = importXlsx(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cols;
	}

	private static List<ExcelTemplateCol> importXls(File file) {
		List<ExcelTemplateCol> cols = new ArrayList<ExcelTemplateCol>();

		InputStream is = null;
		HSSFWorkbook hWorkbook = null;
		try {
			is = new FileInputStream(file);
			hWorkbook = new HSSFWorkbook(is);
			HSSFSheet hSheet = hWorkbook.getSheetAt(0);

			if (null != hSheet) {
				for (int i = 1; i < hSheet.getPhysicalNumberOfRows(); i++) {
					ExcelTemplateCol su = new ExcelTemplateCol();
					HSSFRow hRow = hSheet.getRow(i);
					for (int j = 0; i < hRow.getLastCellNum(); j++) {
						su.setName(hRow.getCell(j).toString());
					}
					cols.add(su);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (null != hWorkbook) {
				try {
					hWorkbook.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return cols;
	}

	private static List<ExcelTemplateCol> importXlsx(File file) {
		List<ExcelTemplateCol> cols = new ArrayList<ExcelTemplateCol>();

		InputStream is = null;
		XSSFWorkbook xWorkbook = null;
		try {
			is = new FileInputStream(file);
			xWorkbook = new XSSFWorkbook(is);
			XSSFSheet xSheet = xWorkbook.getSheetAt(0);

			if (null != xSheet) {
				for (int i = 1; i < xSheet.getPhysicalNumberOfRows(); i++) {
					ExcelTemplateCol su = new ExcelTemplateCol();
					XSSFRow xRow = xSheet.getRow(i);
					for (int j = 0; i < xRow.getLastCellNum(); j++) {
						su.setName(xRow.getCell(j).toString());
					}
					cols.add(su);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (null != xWorkbook) {
				try {
					xWorkbook.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return cols;
	}

	@SuppressWarnings("resource")
	public static void writeExcel(ExcelTemplate excelTemplate) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("sheet");
			HSSFRow row = sheet.createRow(0);
			if (excelTemplate != null && excelTemplate.getCols() != null && excelTemplate.getCols().size() > 0) {
				for (int i = 0; i < excelTemplate.getCols().size(); i++) {
					HSSFCell cell = row.createCell(i);
					cell.setCellValue(excelTemplate.getCols().get(i).getName());
				}
			}
			FileOutputStream out = new FileOutputStream(uploadExcelPath + excelTemplate.getName() + ".xls");
			workbook.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功能说明 : 读取EXCEL文件 返回LIST
	 * 
	 * @author : fei yang
	 * @version ：2016年11月3日 下午3:37:11
	 * @param is
	 * @param type
	 *            1 内容 2 头部
	 * @return
	 */
	@SuppressWarnings("resource")
	public static List<Object> readExcel(InputStream is) {
		List<Object> objList = new ArrayList<Object>();
		try {
			HSSFWorkbook wbs = new HSSFWorkbook(is);
			HSSFSheet childSheet = wbs.getSheetAt(0);
			System.out.println("有行数" + childSheet.getLastRowNum());
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
				objList.add(list);
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objList;
	}

	/**
	 * 
	 * 功能说明 : 校验EXEL表头
	 * 
	 * @author : fei yang
	 * @version ：2016年11月3日 下午2:17:26
	 * @param is
	 * @param template
	 * @return -1： 文件格式不正确 -2：表头为空 -3 原始模板存在问题 -4 上传文件与模板不一致 0 未知错误 1 可以上传了
	 *         -5文件内容为空
	 */
	public static int checkExcelHead(MultipartFile file, String templateName) {
		int flag = 0;
		String fileName = file.getOriginalFilename();
		if (!FileUtil.vidateFileType(fileName, "xls,xlsx")) {
			return -1;
		}
		try {
			InputStream is = file.getInputStream();
			List<Object> excleList = readExcel(is);
			if (excleList.size() <= 0) {
				return -2;
			} else if (excleList.size() == 1) {
				return -5;
			}
			List<Object> uploadHead = new ArrayList<Object>();
			uploadHead.add(excleList.get(0));
			String excelTemplateFilePath = PropertyManager.getConfigProperty("config_path")
					+ PropertyManager.getConfigProperty("upload_excel_path")
					+ PropertyManager.getConfigProperty("upload_excel_chile_path");
			String filePath = excelTemplateFilePath + templateName + ".xls";
			List<Object> mobanHead = readExcel(new FileInputStream(new File(filePath)));
			if (StringUtil.isBlank(uploadHead)) {
				return -3;
			}
			if (checkList(uploadHead, mobanHead)) {
				return 1;
			} else {
				return -4;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
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
	 * 功能说明 : 校验表头是否一致
	 * 
	 * @author : fei yang
	 * @version ：2016年11月3日 下午4:32:13
	 * @param oldList
	 * @param newList
	 * @return
	 */
	public static boolean checkList(List<Object> oldList, List<Object> newList) {
		if (oldList.size() <= 0 || newList.size() <= 0 || oldList.size() != newList.size() || oldList.size() != 1
				|| newList.size() != 1) {
			return false;
		} else {
			@SuppressWarnings("unchecked")
			List<String> list1 = (List<String>) oldList.get(0);
			@SuppressWarnings("unchecked")
			List<String> list2 = (List<String>) newList.get(0);
			if (list1.size() <= 0 || list2.size() <= 0 || list1.size() != list2.size()) {
				return false;
			} else {
				for (int i = 0; i < list1.size(); i++) {
					if (!list1.get(i).trim().equals(list2.get(i).trim())) {
						return false;
					}
				}
			}
		}
		return true;
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
			str += "'1',";
		}
		return str;
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
	public static boolean execlImpToDb(List<Object> objList, ExcelTemplate template, SysReportUser user,
			WebDataReportBean webDataReportBean) throws ClassNotFoundException {
		Connection con = null;
		PreparedStatement pst = null;
		Statement sm = null;
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
			for (int i = 1; i < objList.size(); i++) {
				@SuppressWarnings("unchecked")
				List<String> indexList = (List<String>) objList.get(i);
				UUID uuid = UUID.randomUUID();
				pst.setString(1, uuid.toString().replace("-", ""));
				for (int j = 0; j < indexList.size(); j++) {
					pst.setString(j + 2, indexList.get(j));
				}
				pst.addBatch();
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
			}
			for (int j = 0; j < template.getCols().size(); j++) {
				ExcelTemplateCol col = template.getCols().get(j);
				if (!StringUtil.isBlank(list.get(j))) {
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
						if (StringUtil.isBlank(Constant.formatDate(list.get(j)))) {
							problems.add(new ProblemBean(i, j, Constant.PROBLEM_TYPE_7 + "当前时间格式为:" + list.get(j)));
						}
					}
				}
			}
		}
		return problems;
	}

	public static String getUpdateSql(WebDataReportBean webDataReportBean, String tableName) {
		String sql = "  update " + tableName + "  set k_del = 0 ";
		sql += "where k_year= '" + webDataReportBean.getYear() + "'";
		sql += "and k_month= '" + webDataReportBean.getMonth() + "'";
		return sql;
	}

	public static String getExcelDateString(HSSFCell cell) {
		
		if (HSSFDateUtil.isCellDateFormatted(cell)) {// 日期
			Date d = cell.getDateCellValue();
			String str=String.valueOf(new Timestamp(d.getTime()));
			str=str.replace(" ", "");
			str=str.substring(0, 10);
			return str;
		}else if (cell.getCellStyle().getDataFormat() == 31) {
              double value = cell.getNumericCellValue();  
              Date date = org.apache.poi.ss.usermodel.DateUtil  
                      .getJavaDate(value);  
              String str=String.valueOf(new Timestamp(date.getTime()));
  			str=str.replace(" ", "");
  			str=str.substring(0, 10);
  			return str;
		}else {
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			return cell.getStringCellValue();
		}

	}

	/**
	 * 
	 * 功能说明 :替换EXCEL内容
	 * 
	 * @author : fei yang
	 * @version ：2016年11月8日 下午5:16:37
	 * @param objList
	 * @param template
	 * @return
	 */
	public static List<Object> replaceExeclContent(List<Object> objList, ExcelTemplate template) {

		for (int i = 1; i < objList.size(); i++) {
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) objList.get(i);
			for (int j = 0; j < template.getCols().size(); j++) {
				ExcelTemplateCol col = template.getCols().get(j);
				if (col.getColType() == 2 || col.getColType() == 3) {// 数字类型
					String newNumber = Constant.replaceNumber(list.get(j));
					list.set(j, newNumber);
				} else if (col.getColType() == 4) {// 时间类型
					String newDate = Constant.formatDate(list.get(j));
					list.set(j, newDate);
				}
			}
			objList.set(i, list);
		}
		return objList;
	}
	public static void main(String[] args) {
		File file=new File("E:/11/测试数据 1.xls");
		try {
			List<Object>list=readExcel(new FileInputStream(file));
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

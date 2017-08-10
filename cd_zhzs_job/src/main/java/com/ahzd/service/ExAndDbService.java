package com.ahzd.service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.ahcd.common.CEVUtil;
import com.ahcd.common.DBconn;
import com.ahcd.common.ReadExcel;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.OperateResult;
import com.ahzd.pojo.DataJobStepDbToEx;
import com.ahzd.pojo.DataJobStepExToDb;
@SuppressWarnings("rawtypes")
@Service("exAndDbService")
public class ExAndDbService {
	/**
	 * 功能说明:将Excel文件导入到Db中
	 * @param dataJobStepExToDb
	 * @return
	 */
	public OperateResult  excuteJobStepExToDb(DataJobStepExToDb dataJobStepExToDb){
		OperateResult operateResult = new OperateResult();
		try {
			String path = dataJobStepExToDb.getFilePath();
			//读取excel文件并且将其插入到数据中
			int result =read(dataJobStepExToDb);
			String newPath = path.substring(0, path.lastIndexOf("/"))+"//bak";
			if(result>0){
				ReadExcel.moveTheFile(path, newPath);
				operateResult.setMsg("操作成功");
			}else{
				operateResult.setMsg("文件不存在！");
			}
		} catch (Exception e) {
			operateResult.setMsg("操作失败");
		}
		return operateResult;
	}
	
	/**
	 *功能说明:将DB文件导入到excel中
	 */
	public OperateResult excuteJobStepDbToEx(DataJobStepDbToEx dataJobStepDbToEx){
		OperateResult operateResult = new OperateResult();
		try {
			String name = dataJobStepDbToEx.getName();
			String filePath = dataJobStepDbToEx.getFilePath();
			//循环value值，即页面输入的导出对应的列名
	    	List<String> headers = new ArrayList<String>();
			Set set = dataJobStepDbToEx.getParamMap().entrySet();
	    	  for(Iterator iter = set.iterator(); iter.hasNext();)
	    	  {
	    	   Map.Entry entry = (Map.Entry)iter.next();
	    	   String value = (String)entry.getValue();
	    	   headers.add(value);
	    	  }
			List<String[]> reStrings = toQueryExcel(dataJobStepDbToEx);
			if(dataJobStepDbToEx.getType() == 4){
				dbToExcel1997(name, reStrings, headers, "yyyy-YY-dd",filePath);
			}else{
				dbToExcel2003(name, reStrings, headers, "yyyy-YY-dd",filePath);
			}
			operateResult.setResult(true);
			operateResult.setMsg("操作成功");
			//String newPath = "D:workwork";
			//ReadExcel.moveTheFile(filePath, newPath);
		} catch (Exception e) {
			operateResult.setResult(false);
			operateResult.setMsg("操作失败");
		}
		
		return operateResult;
	}
	
	/**
	 * @描述：根据文件名读取excel文件
	 */
	public int read(DataJobStepExToDb dataJobStepExToDb) {
		InputStream is = null;
		Workbook wb = null;
		int result=0;
		try {
			/** 验证文件是否合法 */
			if (!ReadExcel.validateExcel(dataJobStepExToDb.getFilePath())) {
				return 0;
			}
			/** 判断文件的类型，是2003还是2007 */
			boolean isExcel2003 = true;
			if (CEVUtil.isExcel2007(dataJobStepExToDb.getFilePath())) {
				isExcel2003 = false;
			}
			/** 调用本类提供的根据流读取的方法 */
			is = new FileInputStream(new File(dataJobStepExToDb.getFilePath()));

			if (isExcel2003) {
				wb = new HSSFWorkbook(is);
				result = ex97toDb(wb,dataJobStepExToDb);
			} else {
				wb = new XSSFWorkbook(is);
				result=ex03toDb(wb,dataJobStepExToDb);
				}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
					e.printStackTrace();
				}
			}
		}
		System.out.println(result+"********************************");
		return result;
	}
	/**
	 * @描述：读取97版本数据进入数据库 .xls文件
	 */
	private int  ex97toDb(Workbook wb, DataJobStepExToDb bean) {
		int result =0;
		try {
			String tabName = bean.getTableName();
			Integer beginRow = bean.getBeginRowIndex();
			Integer endRow = bean.getEndRowIndex();
			Integer sheetNow = bean.getSheetIndex();
			StringBuffer filedStr = new StringBuffer();// 存放数据库字段//F1,F2,F3
			StringBuffer excelStr = new StringBuffer();// 存放excel列标题//
			Map<String,String> exlMap= bean.getParamMap();
			//获取要插入的数据库字段
	    	if(exlMap!=null)
	    	{
	    		Iterator<Map.Entry<String,String>> entries=exlMap.entrySet().iterator();
	    		while(entries.hasNext())
	    		{
	    			Map.Entry<String,String> entry=entries.next();
	    			filedStr.append("\"");
	    			filedStr.append(entry.getKey());
	    			filedStr.append("\"");
	    			filedStr.append(",");
	    			excelStr.append(entry.getValue());
	    			excelStr.append(",");
	    		}
	    	}
			filedStr.deleteCharAt(filedStr.length() - 1);// 删除最后一个,
			excelStr.deleteCharAt(excelStr.length() - 1);
			System.out.println("  filedStr is " + filedStr.toString()+ " excelStr is " + excelStr);
			 /**得到总的sheet */
	        int sheetAccount = wb.getNumberOfSheets();
			String[] tempStr = excelStr.toString().split(",");
			String tempVal = "";
			String cellValue = "";// 每个单元格中数据
			String dataSourceId = bean.getDatasourceId();
			Connection conn = DBconn.getConnectionById(dataSourceId);
			Statement stmt = conn.createStatement();
			System.out.println("-----------beginRow is " + beginRow + "-----endRow is " + endRow);
			//判断需要导入的Excel工作表  为空则表示需要导入所有的工作表
			if(sheetNow!=null){
				/** 得到第x个shell */
				Sheet sheet = wb.getSheetAt(sheetNow); // 读取页面传入的sheet坐标
				/** 得到Excel的行数 */
				int rowCount = sheet.getPhysicalNumberOfRows();
				int firstRowNum = sheet.getFirstRowNum();// 取数据excel列标题
				// 开始行结束行不输默认导入全表
				if (beginRow == null) {
					beginRow = firstRowNum + 1;
				}
				if (endRow == null) {
					endRow = rowCount + firstRowNum - 1;
				}
				for (int r = beginRow; r <= endRow; r++) {
					Row row = sheet.getRow(r);
					if (row == null) {
						continue;
					}
					StringBuffer sqlStr = new StringBuffer();
					sqlStr.append(" insert into " + tabName + "("+ filedStr.toString() + ") values(");
					for (int i = 0; i < tempStr.length; i++) {
						tempVal = tempStr[i];// 该向数据库导入的列
						System.out.println("。。。。。。该读取的列是 " + tempVal);
						// 循环Excel的列
						for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
							Row rowStr = sheet.getRow(firstRowNum);// 取数据excel列标题
							Cell cellStr = rowStr.getCell(c);// 列标题所在单元格
							System.out.println("列表名称  is "+ cellStr.getStringCellValue());
							if (excelStr.toString().contains(cellStr.getStringCellValue()))// 判断要读取的列是否和页面传来的列一致
							{
								if (cellStr.getStringCellValue().equals(tempVal)) {
									Cell cell = row.getCell(c); // 每行数据所在单元格
									if (null != cell) {
										// 以下是判断数据的类型
										switch (cell.getCellType()) {
										case HSSFCell.CELL_TYPE_NUMERIC: // 数字
											String dateString = getExcelDateString(cell.getNumericCellValue(),cell.getCellStyle().getDataFormat());
											if (StringUtil.isBlank(dateString)) {
												cell.setCellType(HSSFCell.CELL_TYPE_STRING);
												cellValue = cell.getStringCellValue();
											} else {
												cellValue = dateString;
											}
											break;
										case HSSFCell.CELL_TYPE_STRING: // 字符串
											cellValue = cell.getStringCellValue();
											break;
										case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
											cellValue = cell.getBooleanCellValue()
													+ "";
											break;
										case HSSFCell.CELL_TYPE_FORMULA: // 公式
											cellValue = cell.getCellFormula() + "";
											break;
										case HSSFCell.CELL_TYPE_BLANK: // 空值
											cellValue = "";
											break;
										case HSSFCell.CELL_TYPE_ERROR: // 故障
											cellValue = "非法字符";
											break;
										default:
											cellValue = "未知类型";
											break;
										}
										System.out.println("  cellStr.getStringCellValue() is "+ cellStr.getStringCellValue()+ " excelStr.toString() is "+ excelStr.toString()+ "  cell is" + cellValue);
									}
									System.out.println(" the content is " + cellValue);
									sqlStr.append("'");
									sqlStr.append(cellValue);
									sqlStr.append("',");
								}
							}
						}
					}
					sqlStr.deleteCharAt(sqlStr.length() - 1);
					sqlStr.append(")");
					System.out.println(sqlStr);
					System.out.println(" the sqlStr is " + sqlStr.toString());
					result =stmt.executeUpdate(sqlStr.toString());
				}
			}else{
				//循环Excel的sheet表
				for (int m = 0; m < sheetAccount; m++) {
					// 循环Excel的行
					Sheet sheet  = wb.getSheetAt(m);
					/** 得到Excel的行数 */
					int firstRowNum = sheet.getFirstRowNum();// 取数据excel列标题
					/** 得到Excel的行数 */
					int rowCount = sheet.getPhysicalNumberOfRows();
					// 开始行结束行不输默认导入全表
					if (beginRow == null) {
						beginRow = firstRowNum + 1;
					}
					if (endRow == null) {
						endRow = rowCount + firstRowNum - 1;
					}
					for (int r = beginRow; r <= endRow; r++) {
						Row row = sheet.getRow(r);
						if (row == null) {
							continue;
						}
						StringBuffer sqlStr = new StringBuffer();
						sqlStr.append(" insert into " + tabName + "("+ filedStr.toString() + ") values(");
						for (int i = 0; i < tempStr.length; i++) {
							tempVal = tempStr[i];// 该向数据库导入的列
							System.out.println("。。。。。。该读取的列是 " + tempVal);
							// 循环Excel的列
							for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
								Row rowStr = sheet.getRow(firstRowNum);// 取数据excel列标题
								Cell cellStr = rowStr.getCell(c);// 列标题所在单元格
								System.out.println("列表名称  is "+ cellStr.getStringCellValue());
								if (excelStr.toString().contains(cellStr.getStringCellValue()))// 判断要读取的列是否和页面传来的列一致
								{
									if (cellStr.getStringCellValue().equals(tempVal)) {
										Cell cell = row.getCell(c); // 每行数据所在单元格
										if (null != cell) {
											// 以下是判断数据的类型
											switch (cell.getCellType()) {
											case HSSFCell.CELL_TYPE_NUMERIC: // 数字
												String dateString = getExcelDateString(cell.getNumericCellValue(),cell.getCellStyle().getDataFormat());
												if (StringUtil.isBlank(dateString)) {
													cell.setCellType(HSSFCell.CELL_TYPE_STRING);
													cellValue = cell.getStringCellValue();
												} else {
													cellValue = dateString;
												}
												break;
											case HSSFCell.CELL_TYPE_STRING: // 字符串
												cellValue = cell.getStringCellValue();
												break;
											case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
												cellValue = cell.getBooleanCellValue()
														+ "";
												break;
											case HSSFCell.CELL_TYPE_FORMULA: // 公式
												cellValue = cell.getCellFormula() + "";
												break;
											case HSSFCell.CELL_TYPE_BLANK: // 空值
												cellValue = "";
												break;
											case HSSFCell.CELL_TYPE_ERROR: // 故障
												cellValue = "非法字符";
												break;
											default:
												cellValue = "未知类型";
												break;
											}
											System.out.println("  cellStr.getStringCellValue() is "+ cellStr.getStringCellValue()+ " excelStr.toString() is "+ excelStr.toString()+ "  cell is" + cellValue);
										}
										System.out.println(" the content is " + cellValue);
										sqlStr.append("'");
										sqlStr.append(cellValue);
										sqlStr.append("',");
									}
								}
							}
						}
						sqlStr.deleteCharAt(sqlStr.length() - 1);
						sqlStr.append(")");
						System.out.println(sqlStr);
						System.out.println(" the sqlStr is " + sqlStr.toString());
						result =stmt.executeUpdate(sqlStr.toString());
					}
				}
			}
			stmt.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * @描述：读取97版本数据进入数据库 
	 * 
	 */
	private int ex03toDb(Workbook book, DataJobStepExToDb bean) {
		int result =0;
		try {
			String dataSourceId = bean.getDatasourceId();
			Connection conn = DBconn.getConnectionById(dataSourceId);
			Statement stmt = conn.createStatement();
			String tabname = bean.getTableName();
			Integer sheetNow  = bean.getSheetIndex();
			StringBuffer filedStr = new StringBuffer();// 存放数据库字段//F1,F2,F3
			StringBuffer excelStr = new StringBuffer();// 存放excel列标题//
			Map<String,String> exlMap= bean.getParamMap();
			//获取要插入的数据库字段
	    	if(exlMap!=null)
	    	{
	    		Iterator<Map.Entry<String,String>> entries=exlMap.entrySet().iterator();
	    		while(entries.hasNext())
	    		{
	    			Map.Entry<String,String> entry=entries.next();
	    			filedStr.append("\"");
	    			filedStr.append(entry.getKey());
	    			filedStr.append("\"");
	    			filedStr.append(",");
	    			excelStr.append(entry.getValue());
	    			excelStr.append(",");
	    		}
	    	}
			filedStr.deleteCharAt(filedStr.length() - 1);// 删除最后一个,
			excelStr.deleteCharAt(excelStr.length() - 1);
			System.out.println("  filedStr is " + filedStr.toString()+ " excelStr is " + excelStr);
			String[] sbbs = excelStr.toString().split(",");
			System.out.println("excel的列名为" + excelStr);
			/*得到总的sheet*/
			int sheetAccount = book.getNumberOfSheets();
			if(sheetNow!=null){
				XSSFSheet firstsheet = (XSSFSheet) book.getSheetAt(sheetNow);
				System.out.println("获取工作表");
				int fristrow = firstsheet.getFirstRowNum();//获取Excel列标题
				Object tempvalue = null;
				Object value = null;
				// 将指定行数和列数插入数据库
				Integer beginrows = bean.getBeginRowIndex();
				Integer endrows = bean.getEndRowIndex();
				int rowCount = firstsheet.getPhysicalNumberOfRows();
				// 开始行结束行不输默认导入全表
				if (beginrows == null) {
					beginrows = fristrow + 1;
				}
				if (endrows == null) {
					endrows = rowCount + fristrow - 1;
				}
				// 数据全部导入
				for (int i = beginrows; i <= endrows; i++)// ============》》》》》开始行和结束行
				{
					XSSFRow row = firstsheet.getRow(i);
					if (row == null) {
						continue;
					}
					System.out.println("循环行数了" + row + i);
					StringBuffer sqlStr = new StringBuffer();
					sqlStr.append(" insert into " + tabname + "("+ filedStr.toString() + ") values(");
					for (int k = 0; k < sbbs.length; k++)// =============>>循环页面得到的列数
					{
						tempvalue = sbbs[k];
						System.out.println("该向数据库导入的列" + tempvalue + k);
						for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
							XSSFRow rows = firstsheet.getRow(fristrow);// 取数据excel列标题
							XSSFCell cell = rows.getCell(j);// 取数据excel列标题
							System.out.println("循环列数" + cell + j);
							if (excelStr.toString().contains(cell.getStringCellValue()))// 判断要读取的列是否和页面传来的列一致
							{
								if (cell.getStringCellValue().equals(tempvalue)) {
									XSSFCell cell1 = row.getCell(j);
									if (cell1 != null) {
										DecimalFormat df = new DecimalFormat("0");
										SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");// 格式话日期年月日
										DecimalFormat nf = new DecimalFormat("0");
										switch (cell1.getCellType()) {
										case XSSFCell.CELL_TYPE_STRING:// 字符串
											value = cell1.getStringCellValue();
											break;
										case XSSFCell.CELL_TYPE_NUMERIC:// 数字
											if ("@".equals(cell1.getCellStyle().getDataFormatString())) {
												value = df.format(cell1.getNumericCellValue());
											} else if ("General".equals(cell1.getCellStyle().getDataFormatString())) {
												value = nf.format(cell1.getNumericCellValue());
											} else {
												value = date.format(HSSFDateUtil.getJavaDate(cell1.getNumericCellValue()));
											}
											break;
										case XSSFCell.CELL_TYPE_BOOLEAN:
											value = cell1.getBooleanCellValue();
											break;
										case XSSFCell.CELL_TYPE_BLANK:
											value = "";
											break;
										case XSSFCell.CELL_TYPE_FORMULA:
											value = cell1.getCellFormula();
											break;
										case XSSFCell.CELL_TYPE_ERROR:
											value = "空值";
											break;
										default:
											value = cell1.toString();
											break;
										}
									}
									sqlStr.append("'");
									sqlStr.append(value);
									sqlStr.append("',");
									System.out.println(sqlStr);
								}
							}
						}
					}
						sqlStr.deleteCharAt(sqlStr.length() - 1);
						sqlStr.append(")");
						System.out.println(sqlStr);
						System.out.println(" the sqlStr is " + sqlStr.toString());
						result=stmt.executeUpdate(sqlStr.toString());
					}
			}else{
				//循环Excel的sheet表
				for (int m = 0; m < sheetAccount; m++) {
					XSSFSheet firstsheet = (XSSFSheet) book.getSheetAt(m);
					System.out.println("获取工作表");
					int fristrow = firstsheet.getFirstRowNum();//获取Excel列标题
					Object tempvalue = null;
					Object value = null;
					// 将指定行数和列数插入数据库
					Integer beginrows = bean.getBeginRowIndex();
					Integer endrows = bean.getEndRowIndex();
					int rowCount = firstsheet.getPhysicalNumberOfRows();
					// 开始行结束行不输默认导入全表
					if (beginrows == null) {
						beginrows = fristrow + 1;
					}
					if (endrows == null) {
						endrows = rowCount + fristrow - 1;
					}
					// 数据全部导入
					for (int i = beginrows; i <= endrows; i++)// ============》》》》》开始行和结束行
					{
						XSSFRow row = firstsheet.getRow(i);
						if (row == null) {
							continue;
						}
						System.out.println("循环行数了" + row + i);
						StringBuffer sqlStr = new StringBuffer();
						sqlStr.append(" insert into " + tabname + "("+ filedStr.toString() + ") values(");
						for (int k = 0; k < sbbs.length; k++)// =============>>循环页面得到的列数
						{
							tempvalue = sbbs[k];
							System.out.println("该向数据库导入的列" + tempvalue + k);
							for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
								XSSFRow rows = firstsheet.getRow(fristrow);// 取数据excel列标题
								XSSFCell cell = rows.getCell(j);// 取数据excel列标题
								System.out.println("循环列数" + cell + j);
								if (excelStr.toString().contains(cell.getStringCellValue()))// 判断要读取的列是否和页面传来的列一致
								{
									if (cell.getStringCellValue().equals(tempvalue)) {
										XSSFCell cell1 = row.getCell(j);
										if (cell1 != null) {
											DecimalFormat df = new DecimalFormat("0");
											SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");// 格式话日期年月日
											DecimalFormat nf = new DecimalFormat("0");
											switch (cell1.getCellType()) {
											case XSSFCell.CELL_TYPE_STRING:// 字符串
												value = cell1.getStringCellValue();
												break;
											case XSSFCell.CELL_TYPE_NUMERIC:// 数字
												if ("@".equals(cell1.getCellStyle().getDataFormatString())) {
													value = df.format(cell1.getNumericCellValue());
												} else if ("General".equals(cell1.getCellStyle().getDataFormatString())) {
													value = nf.format(cell1.getNumericCellValue());
												} else {
													value = date.format(HSSFDateUtil.getJavaDate(cell1.getNumericCellValue()));
												}
												break;
											case XSSFCell.CELL_TYPE_BOOLEAN:
												value = cell1.getBooleanCellValue();
												break;
											case XSSFCell.CELL_TYPE_BLANK:
												value = "";
												break;
											case XSSFCell.CELL_TYPE_FORMULA:
												value = cell1.getCellFormula();
												break;
											case XSSFCell.CELL_TYPE_ERROR:
												value = "空值";
												break;
											default:
												value = cell1.toString();
												break;
											}
										}
										sqlStr.append("'");
										sqlStr.append(value);
										sqlStr.append("',");
										System.out.println(sqlStr);
									}
								}
							}
						}
						sqlStr.deleteCharAt(sqlStr.length() - 1);
						sqlStr.append(")");
						System.out.println(sqlStr);
						System.out.println(" the sqlStr is " + sqlStr.toString());
						result=stmt.executeUpdate(sqlStr.toString());
					}
				}
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
	private static String getExcelDateString(double value, short format) {
		String result = "";
		try {
			SimpleDateFormat sdf = null;
			if (format == 14 || format == 31 || format == 57 || format == 58|| (format >= 176 && format <= 189)) {
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
	 * 功能说明：去数据库查询Excel数据
	 * 
	 * 
	 * 
	 */
	private static List<String[]> toQueryExcel(DataJobStepDbToEx bean){
		Connection conn = DBconn.getConnectionById(bean.getDatasourceId());
		List<String[]> list = new ArrayList<String[]>();
		Statement stmt = null;
		ResultSet rs = null;
		String  sql = bean.getSql();
		StringBuffer newSql = new StringBuffer("");
		if (conn != null) {
			try {
				newSql.append(sql);
				System.out.println("查询SQL" + newSql.toString());
				stmt = conn.createStatement();
				rs = stmt.executeQuery(newSql.toString());
				List<String> getSizeList = new ArrayList<String>();
				Set set = bean.getParamMap().entrySet();
				  
				  for(Iterator iter = set.iterator(); iter.hasNext();)
				  {
				   Map.Entry entry = (Map.Entry)iter.next();
				   String key = (String)entry.getKey();
				   getSizeList.add(key);
				  }
				while (rs.next()) {
					StringBuffer strb = new StringBuffer();
					
					for (int i = 1; i <= getSizeList.size(); i++) {
						
						if (rs.getString(i) == null) {
							strb.append(" " + "@");
						} else {
							strb.append(rs.getString(i) + "@");
						}
					}
					String str = strb.toString().substring(0, strb.length() - 1);
					String[] newstr = str.split("@");
					list.add(newstr);
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	 * 功能说明：将数据库数据导入到Excel97中
	 * 
	 * @param response
	 * @param fileName
	 * @param list
	 * @param headers
	 * @param dateFormat
	 * @throws Exception
	 */
	private static void dbToExcel1997(String fileName, List<String[]> list,List<String> headers, String dateFormat,String fielpath) throws Exception {
		@SuppressWarnings("resource")
		Workbook workbook = new HSSFWorkbook(); // 创建一个工作簿
		double bigSize=list.size();
		double nextSiz=65530;
		int sheetSize=(int) Math.ceil(bigSize/nextSiz);
		System.out.println("一共几页=="+sheetSize);
		for (int i = 0; i < sheetSize; i++) {
			int rowIndex = 0;
			Sheet sheet = workbook.createSheet(); // 创建一个Sheet页
			sheet.autoSizeColumn((short) 0); // 单元格宽度自适应
			Row row = sheet.createRow(rowIndex++); // 创建第一行(头部)
			CreationHelper helper = workbook.getCreationHelper();
			CellStyle style = workbook.createCellStyle(); // 设置单元格样式
			style.setDataFormat(helper.createDataFormat().getFormat(dateFormat)); // 格式化日期类型
			for (int j = 0; j < headers.size(); j++) { // 输出头部
				row.createCell(j).setCellValue(headers.get(j));
			}
			for (int j = 0; j <65530; j++) {
				if ((i*65530+j)<bigSize) {
					row = sheet.createRow(rowIndex++);
					for (int x = 0; x < list.get(i).length; x++) {
						row.createCell(x).setCellValue(list.get(i*65530+j)[x]);
					}
				}
			}
		}
		OutputStream output = new FileOutputStream(fielpath);
		workbook.write(output);
		output.flush();
		output.close();
	}
	
	/**
	 * 
	 * 功能说明：将数据库数据导入到Excel2003中
	 * 
	 * @param response
	 * @param fileName
	 * @param list
	 * @param headers
	 * @param dateFormat
	 * @throws Exception
	 */
	private static void dbToExcel2003(String fileName, List<String[]> list,List<String> headers, String dateFormat,String fielpath) throws Exception {
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(); // 创建一个工作簿
		double bigSize=list.size();
		double nextSiz=65530;
		int sheetSize=(int) Math.ceil(bigSize/nextSiz);
		System.out.println("一共几页=="+sheetSize);
		for (int i = 0; i < sheetSize; i++) {
			int rowIndex = 0;
			//Sheet sheet = workbook.createSheet(); // 创建一个Sheet页
			XSSFSheet sheet = workbook.createSheet();
			sheet.autoSizeColumn((short) 0); // 单元格宽度自适应
			//Row row = sheet.createRow(rowIndex++); // 创建第一行(头部)
			XSSFRow row = sheet.createRow(rowIndex++);
			XSSFCreationHelper helper = workbook.getCreationHelper();
			XSSFCellStyle style = workbook.createCellStyle(); // 设置单元格样式
			style.setDataFormat(helper.createDataFormat().getFormat(dateFormat)); // 格式化日期类型
			for (int j = 0; j < headers.size(); j++) { // 输出头部
				row.createCell(j).setCellValue(headers.get(j));
			}
			for (int j = 0; j <65530; j++) {
				if ((i*65530+j)<bigSize) {
					row = sheet.createRow(rowIndex++);
					for (int x = 0; x < list.get(i).length; x++) {
						row.createCell(x).setCellValue(list.get(i*65530+j)[x]);
					}
				}
			}
		}
		OutputStream output = new FileOutputStream(fielpath);
		workbook.write(output);
		output.flush();
		output.close();
	}

	public static void main(String[] args) {
		Connection con = DBconn.getConnectionById("1");
		System.out.println(con);
	}
}

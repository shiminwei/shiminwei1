package com.ahcd.common;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.ahcd.pojo.FieldBean;

/**
 * @author : fei yang
 * @version ：2016年10月27日 上午10:33:18
 */
public class ExcelFileUtil {

	public static void exportExcel(HttpServletResponse response, String fileName, List<String[]> list,
			List<FieldBean> headers, String dateFormat) throws Exception {
		
		Workbook workbook = new HSSFWorkbook(); // 创建一个工作簿
		
		double bigSize=list.size();
		double nextSiz=65530;
		int sheetSize=(int) Math.ceil(bigSize/nextSiz)+1;
System.out.println("一共几页=="+sheetSize);

		for (int i = 0; i < sheetSize; i++) {
			int rowIndex = 0;
			Sheet sheet = workbook.createSheet(); // 创建一个Sheet页
	CellRangeAddress address=	new	CellRangeAddress(0, 1, 0, headers.size());
			//参数1：行号 参数2：起始列号 参数3：行号 参数4：终止列号
			sheet.addMergedRegion(address);
			sheet.autoSizeColumn((short) 0); // 单元格宽度自适应
			
		/**
		 * 	以下是列名
		 */
			Row row = sheet.createRow(rowIndex++); // 创建第一行(头部)
			CreationHelper helper = workbook.getCreationHelper();
			CellStyle style = workbook.createCellStyle(); // 设置单元格样式
			style.setDataFormat(helper.createDataFormat().getFormat(dateFormat)); // 格式化日期类型
			for (int j = 0; j < headers.size(); j++) { // 输出头部
				row.createCell(j).setCellValue(headers.get(j).getJspFielName());
			}
			for (int j = 0; j < headers.size(); j++) {//合并列
				row.createCell(j).setCellValue(headers.get(j).getMergeName());
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
		String ddate = new SimpleDateFormat("yyyyMMddhhmmss").format(Calendar.getInstance().getTime());
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition",
				"attachment; filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1") + "_" + ddate + ".xls");// 设定输出文件头
		OutputStream output = response.getOutputStream();
		workbook.write(output);
		output.flush();
		output.close();
	}
	public static String getValue(Object obj) {
		if (!StringUtil.isBlank(obj)) {
			return obj.toString();
		} else {
			return "";
		}
	}

	public static void main(String[] args) {

	}
}

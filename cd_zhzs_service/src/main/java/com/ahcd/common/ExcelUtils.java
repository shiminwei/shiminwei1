package com.ahcd.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import com.ahcd.pojo.ExcelBean;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.FieldBean;
import com.ahcd.pojo.ProblemBean;

public class ExcelUtils {

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
	public static ExcelBean readExcelByIs(InputStream is, String fileName) {
		if (is == null || StringUtil.isBlank(fileName)) {// 文件流 或者文件名称为空
			return null;
		}
		if (!FileUtil.vidateFileType(fileName, "xls,xlsx")) {// 文件格式不匹配
			return null;
		}
		ExcelBean bean = new ExcelBean();
		if (FileUtil.vidateFileType(fileName, "xls")) {// 1997exlce 文件读取
			bean = ExcelBase.readExcel1997(is, fileName);
		} else if (FileUtil.vidateFileType(fileName, "xlsx")) {// 2003exlce 文件读取
			bean = ExcelBase.readExcel2003(is, fileName);
		} else {
			return null;
		}
		return bean;
	}

	/**
	 * 
	 * 功能说明 : 根据File文件读取Excel表
	 * 
	 * @author : fei yang
	 * @version ：2017年4月11日 上午8:48:21
	 * @param File
	 *            file 文件
	 * @return
	 */
	public static ExcelBean readExcelByFile(File file) {
		try {
			if (file != null) {
				return readExcelByIs(new FileInputStream(file), file.getName());
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	/**
	 * 
	 * 功能说明 : 根据MultipartFile文件读取Excel表
	 * 
	 * @author : fei yang
	 * @version ：2017年4月11日 上午8:48:21
	 * @param MultipartFile
	 *            multipartFile 文件
	 * @return
	 */
	public static ExcelBean readExcelByMultipartFile(MultipartFile multipartFile) {
		try {
			if (multipartFile != null) {
				return readExcelByIs(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	/**
	 * 
	 * 功能说明 : 根据ExcelTemplate 前台模板下载（xls格式）
	 * 
	 * @author : fei yang
	 * @version ：2017年4月11日 上午10:11:05
	 * @param response
	 * @param template
	 *            ExcelTemplate 模板对象
	 */
	public static void toWriteExcelFileByTemplate(HttpServletResponse response, ExcelTemplate template) {
		if (template != null && StringUtil.isNotEmpty(template.getName()) && !StringUtil.isBlank(template.getCols())) {
			try {
				HSSFWorkbook hwb = ExcelBase.toWriteExcel(template);
				if (hwb != null) {
					response.reset();
					response.setContentType("application/vnd.ms-excel;charset=UTF-8");
					response.setHeader("Content-Disposition", "attachment; filename="
							+ new String(template.getName().getBytes("gbk"), "iso-8859-1") + ".xls");// 设定输出文件头
					// 循环取出流中的数据
					OutputStream out = response.getOutputStream();
					hwb.write(out);
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	public static List<ProblemBean> checkExeclContent(MultipartFile multipartFile, ExcelTemplate excelTemplate) {
		ExcelBean excelBean = readExcelByMultipartFile(multipartFile);
		return ExcelBase.checkExeclContent(excelBean, excelTemplate);
	}
	
	public static Workbook exportExcel(String fileName, List<String[]> list,
			List<FieldBean> headers, String dateFormat) throws Exception {		
		Workbook workbook = new HSSFWorkbook(); // 创建一个工作簿		
		double bigSize=list.size();
		double nextSiz=65530;
		int sheetSize=(int) Math.ceil(bigSize/nextSiz)+1;

		for (int i = 0; i < sheetSize; i++) {
			int rowIndex = 2;
			Sheet sheet = workbook.createSheet(); // 创建一个Sheet页
			
			
			Row titleRow = sheet.createRow(0); // 标题		
			//titleRow.createCell(0).setCellValue(fileName);
			CellRangeAddress address=	new	CellRangeAddress(0, 1, 0, headers.size()-1);			
			   CellStyle titlestyle = workbook.createCellStyle();
			   titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
               Font font = workbook.createFont(); 
               titlestyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
               titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
               font.setFontHeightInPoints((short) 20);// 设置字体大小
               titlestyle.setFont(font);
	           Cell titleCell=    titleRow.createCell(0);
	           titleCell.setCellStyle(titlestyle);
	           titleCell.setCellValue(fileName);
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
				row.createCell(j).setCellValue(headers.get(j).getMergeName());
			}

			Row hbrow = sheet.createRow(rowIndex++); // 
			for (int j = 0; j < headers.size(); j++) {//合并列
				hbrow.createCell(j).setCellValue(headers.get(j).getJspFielName());
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
		return workbook;
	}
	
	
}

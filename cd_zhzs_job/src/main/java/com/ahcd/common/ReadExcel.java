package com.ahcd.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.ahcd.pojo.ExcelBean;
import com.ahcd.pojo.ExcelColumnBean;
import com.ahcd.pojo.ExcelRowBean;
import com.ahcd.pojo.ExcelSheetBean;

public class ReadExcel {

	/**
	 * 验证EXCEL文件是否合法
	 */
	public static boolean validateExcel(String filePath) {

		/** 判断文件名是否为空或者文件是否存在 */
		if (!CEVUtil.fileExist(filePath)) {
			System.out.println("文件不存在");
			return false;
		}

		/** 检查文件是否是Excel格式的文件 */
		if (!CEVUtil.isExcel(filePath)) {
			System.out.println("文件名不是excel格式");
			return false;
		}
		return true;
	}
	/**
	 * 
	 * 功能说明，将文件移动到新的文件夹里面
	 * @author
	 * @param oldPath
	 * @param newPath
	 */
	public static void moveTheFile(String oldPath,String newPath){
    	try { 
    		String filename = oldPath.substring(oldPath.lastIndexOf("/")+1,oldPath.length());
            File afile = new File(oldPath); 
            //String filePath ="C:\\Users\\Administrator\\Desktop\\bak";
            File myFile = new File(newPath);
            if(!myFile.exists()){
            	myFile.mkdir();;
            	System.out.println("文件夹不存在创建了一个新的文件夹");
            }else{
            	System.out.println("该文件夹已经存在,开始移文件");
            }
            if (afile.renameTo(new File(newPath +"\\"+ filename))) {  
                System.out.println("File is moved successful!");  
            } else {  
                System.out.println("File is failed to move!");  
            } 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    	
    }
	public static List<ExcelColumnBean> getExcelCols(String filePath,String filename,int sheet){
		File file = new File(filePath);
		InputStream in;
		List<ExcelColumnBean>  columns = null;
		try {
			in = new FileInputStream(file);
			ExcelBean bean = ExcelUtils.readExcelByIs(in,filename);
	        List<ExcelSheetBean> listob = null;  
	        listob = bean.getExcelSheetBeans();
	    	ExcelRowBean excelbean = listob.get(sheet).getTableHeader();
	    	columns = excelbean.getExcelColumnBeans();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			
       
        return columns;
	}
	public static HSSFWorkbook toWriteWorkbook(List<String> headers, String dateFormat){
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个工作簿
		int rowIndex = 0;
		Sheet sheet = workbook.createSheet(); // 创建一个Sheet页
		sheet.autoSizeColumn((short) 0); // 单元格宽度自适应
		Row row = sheet.createRow(rowIndex); // 创建第一行(头部)
//		CreationHelper helper = workbook.getCreationHelper();
//		CellStyle style = workbook.createCellStyle(); // 设置单元格样式
//		style.setDataFormat(helper.createDataFormat().getFormat(dateFormat)); // 格式化日期类型
		for (int j = 0; j < headers.size(); j++) { // 输出头部
			String cellValue=headers.get(j);
			row.createCell(j).setCellValue(cellValue);
			sheet.setColumnWidth(j, cellValue.getBytes().length * 256);// 列自适应宽度的设置
		}
		return workbook;
	}
}

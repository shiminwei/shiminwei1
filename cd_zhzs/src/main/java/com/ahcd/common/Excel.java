package com.ahcd.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
	public static void main(String[] args) {
		String xls="D:\\我是XLS.xls";
		String xlsx="D:\\我是XLSX.xlsx";
		Excel e=new Excel();
		System.out.println("读取xls格式excel结果：");
		e.getFromExcel(xls);
		System.out.println("读取xlsx格式excel结果：");
		e.getFromExcel(xlsx);
	}
	
	public void getFromExcel(String filename){
		InputStream is=null;
		Workbook wb=null;
		String type=filename.substring(filename.lastIndexOf(".")+1);//获取文件类型
		File file=new File(filename);
		try {
			is=new FileInputStream(file);
			if(type.equals("xls")){
				wb=new HSSFWorkbook(is);
				readXls(wb);
			}else if(type.equals("xlsx")){
				wb=new XSSFWorkbook(is);
				readXlsx(wb);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			try {
				is.close();
				wb.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param wb:excel文件对象
	 */
	public void readXls(Workbook wb){
		Sheet sheet=wb.getSheetAt(0);//对应excel正文对象
		for(int i=0;i<=sheet.getLastRowNum();i++){
			HSSFRow hssfrow=(HSSFRow) sheet.getRow(i);//行
			for (int j = 0; j <=hssfrow.getLastCellNum(); j++) {
				HSSFCell hssfcell=hssfrow.getCell(j);
				if(hssfcell!=null){
					hssfcell.setCellType(Cell.CELL_TYPE_STRING);//设置单元格类型为String类型，以便读取时候以string类型，也可其它
					String cellValue=hssfcell.getStringCellValue();
					System.out.print(cellValue);
				}
				System.out.print("\t");
			}
			System.out.println();
		}
	}
	
	/**
	 * 
	 * @param wb:excel文件对象
	 */
	public void readXlsx(Workbook wb){
		Sheet sheet=wb.getSheetAt(0);
		for (int i = 0; i <=sheet.getLastRowNum(); i++) {
			XSSFRow xssfrow=(XSSFRow) sheet.getRow(i);
			for (int j = 0; j <xssfrow.getLastCellNum(); j++) {
				XSSFCell xssfcell=xssfrow.getCell(j);
				if(xssfcell!=null){
					xssfcell.setCellType(Cell.CELL_TYPE_STRING);
					String cellValue=xssfcell.getStringCellValue();
					System.out.print(cellValue);
				}
				System.out.print("\t");
			}
			System.out.println();
		}
	}
	
}

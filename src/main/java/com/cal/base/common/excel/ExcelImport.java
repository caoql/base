package com.cal.base.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel导入  Xlsx 和 Xls
 * @author zsh
 * date:2017年12月18日
 */
public class ExcelImport {
	/**
	 * 获取文件名后缀类型
	 * @param filename
	 * @return
	 */
	public static String getFileName(String filename){
		return filename.substring(filename.lastIndexOf("."),filename.length());
	}
	/**
	 * 读取Xlsx文件
	 * @param file  excel文件
	 * @param startRow 从第几行开始,0为起始值
	 * @return
	 * @throws Exception
	 */
	public static List<Object[]> ExcelImportXlsx(File file,int startRow) throws Exception{
		List<Object[]> list = new ArrayList<Object[]>();
		InputStream is = new FileInputStream(file);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		// 获取每一个工作薄
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// 获取当前工作薄的每一行
			for (int rowNum = startRow; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				//行
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					Object [] obj = null;
					for (int i = 0; i <= xssfRow.getLastCellNum(); i++) {
						//obj的长度和列数相同
						obj = new Object[xssfRow.getLastCellNum()];
						obj[i] = xssfRow.getCell(i);
					}
					list.add(obj);
				}
			}
		}
		return list;
	}
	/**
	 * 读取Xls文件
	 * @param file
	 * @param startRow 开始行 默认为0
	 * @return
	 * @throws Exception
	 */
	public static List<Object[]> ExcelImportXls(File file,int startRow) throws Exception{
		List<Object[]> list = new ArrayList<Object[]>();
		InputStream is = new FileInputStream(file);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        // 获取每一个工作薄
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 获取当前工作薄的每一行
            for (int rowNum = startRow; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                	Object [] obj = null;
					for (int i = 0; i <= hssfRow.getLastCellNum(); i++) {
						//obj的长度和列数相同
						obj = new Object[hssfRow.getLastCellNum()];
						obj[i] = hssfRow.getCell(i);
					}
					list.add(obj);
                }
            }
        }
		return list;
	}
}

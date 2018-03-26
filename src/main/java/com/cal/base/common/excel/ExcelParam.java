package com.cal.base.common.excel;

import java.util.List;

/**
 * 
 * @author andyc
 *
 */
public class ExcelParam {
	
	//sheet名
	private String title;
	//列名
	private String[] rowName;
	//表数据
	private List<Object[]>  dataList;
	//合并的列索引
	private int colSum;
	//合并列数据
	private String[] cellValue;
		
	public ExcelParam(String title, String[] rowName, List<Object[]> dataList, int colSum, String[] cellValue) {
		super();
		this.title = title;
		this.rowName = rowName;
		this.dataList = dataList;
		this.colSum = colSum;
		this.cellValue = cellValue;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Object[]> getDataList() {
		return dataList;
	}
	public void setDataList(List<Object[]> dataList) {
		this.dataList = dataList;
	}
	public String[] getRowName() {
		return rowName;
	}
	public void setRowName(String[] rowName) {
		this.rowName = rowName;
	}
	public int getColSum() {
		return colSum;
	}
	public void setColSum(int colSum) {
		this.colSum = colSum;
	}
	public String[] getCellValue() {
		return cellValue;
	}
	public void setCellValue(String[] cellValue) {
		this.cellValue = cellValue;
	}
}

package com.cal.base.common.excel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

public class ExportExcel {

	// 构造方法，传入要导出的数据
	public ExportExcel(List<ExcelParam> params) {

	}

	public ExportExcel() {

	}

	/**
	 * 导出Excel
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static void export(String excelName, List<ExcelParam> params,
			HttpServletResponse response) throws Exception {
		HSSFWorkbook workbook = null;
		try {
			if (CollectionUtils.isNotEmpty(params)) {
				// 创建工作簿对象
				workbook = new HSSFWorkbook();
				for (ExcelParam param : params) {
					String title = param.getTitle();
					String[] rowName = param.getRowName();
					List<Object[]> dataList = param.getDataList();
					// 创建工作表
					HSSFSheet sheet = workbook.createSheet(param.getTitle());
					// 产生表格标题行
					HSSFRow rowm = sheet.createRow(0);
					HSSFCell cellTiltle = rowm.createCell(0);
					// sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 -
					// 可扩展】
					HSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);// 获取列头样式对象
					HSSFCellStyle style = getStyle(workbook);// 单元格样式对象
					sheet.addMergedRegion(new CellRangeAddress(0, 1, 0,
							(rowName.length - 1)));
					cellTiltle.setCellStyle(columnTopStyle);
					cellTiltle.setCellValue(title);
					// 定义所需列数
					int columnNum = rowName.length;
					// 在索引2的位置创建行(最顶端的行开始的第二行)
					HSSFRow rowRowName = sheet.createRow(2);
					// 将列头设置到sheet的单元格中
					for (int n = 0; n < columnNum; n++) {
						HSSFCell cellRowName = rowRowName.createCell(n);// 创建列头对应个数的单元格
						cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置列头单元格的数据类型
						HSSFRichTextString text = new HSSFRichTextString(
								rowName[n]);
						cellRowName.setCellValue(text);// 设置列头单元格的值
						cellRowName.setCellStyle(columnTopStyle);// 设置列头单元格样式
					}
					// 将查询出的数据设置到sheet对应的单元格中
					for (int i = 0; i < dataList.size(); i++) {
						Object[] obj = dataList.get(i);// 遍历每个对象
						HSSFRow row = sheet.createRow(i + 3);// 创建所需的行数
						for (int j = 0; j < obj.length; j++) {
							HSSFCell cell = null; // 设置单元格的数据类型
							cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
							if (!"".equals(obj[j]) && obj[j] != null) {
								cell.setCellValue(obj[j].toString());// 设置单元格的值
							} else {
								cell.setCellValue("");
							}
							cell.setCellStyle(style);// 设置单元格样式
						}
					}
					// 让列宽随着导出的列长自动适应
					for (int colNum = 0; colNum < columnNum; colNum++) {
						int columnWidth = sheet.getColumnWidth(colNum) / 256;
						for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
							HSSFRow currentRow;
							// 当前行未被使用过
							if (sheet.getRow(rowNum) == null) {
								currentRow = sheet.createRow(rowNum);
							} else {
								currentRow = sheet.getRow(rowNum);
							}
							if (currentRow.getCell(colNum) != null) {
								HSSFCell currentCell = currentRow
										.getCell(colNum);
								if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
									int length = currentCell
											.getStringCellValue().getBytes().length;
									if (columnWidth < length) {
										columnWidth = length;
									}
								}
							}
						}
						if (colNum == 0) {
							sheet.setColumnWidth(colNum,
									(columnWidth - 2) * 256);
						} else {
							sheet.setColumnWidth(colNum,
									(columnWidth + 4) * 256);
						}
					}
					if (param.getCellValue() != null
							&& param.getCellValue().length > 0) {
						String[] cellValue = param.getCellValue();
						int colSum = param.getColSum();
						HSSFCellStyle cellStyle = workbook.createCellStyle();
						cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
						cellStyle
								.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
						cellStyle.setWrapText(true);// 指定单元格自动换行
						// 单元格字体
						HSSFFont font = workbook.createFont();
						font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
						font.setFontName("宋体");
						font.setFontHeight((short) 250);
						cellStyle.setFont(font);
						HSSFRow lastRow = sheet.createRow((short) (sheet
								.getLastRowNum() + 1));
						HSSFCell sumCell = lastRow.createCell(0);
						sumCell.setCellValue(new HSSFRichTextString("合计"));
						sumCell.setCellStyle(cellStyle);
						sheet.addMergedRegion(new Region(sheet.getLastRowNum(),
								(short) 0, sheet.getLastRowNum(),
								(short) colSum));// 指定合并区域
						for (int i = 2; i < (cellValue.length + 2); i++) {
							sumCell = lastRow.createCell(i);
							sumCell.setCellStyle(cellStyle);
							sumCell.setCellValue(new HSSFRichTextString(
									cellValue[i - 2]));
						}
					}

				}
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (workbook != null) {
			try {
				String fileName = excelName + ".xls";
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader(
						"Content-Disposition",
						"attachment; filename="
								+ new String(fileName.getBytes("gb2312"),
										"ISO-8859-1"));
				OutputStream out = response.getOutputStream();
				workbook.write(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void outExcel(HttpServletResponse response, HSSFWorkbook workbook,
			String excelName) {
		if (workbook != null) {
			try {
				String fileName = "Excel-" + excelName + ".xls";
				String headStr = "attachment; filename=\"" + fileName + "\"";
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Disposition", headStr);
				OutputStream out = response.getOutputStream();
				workbook.write(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * 列头单元格样式
	 */
	public static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

	/*
	 * 列数据信息单元格样式
	 */
	public static HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

	private void download(String path, HttpServletResponse response) {
		try {
			//  path是指欲下载的文件的路径。  
			File file = new File(path);
			//  取得文件名。  
			String filename = file.getName();
			//  以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			response.reset();
			// response.addHeader("Content-Disposition","attachment;filename="+new
			// String(filename.getBytes()));
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(
							filename.replaceAll(" ", "").getBytes("utf-8"),
							"iso8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			// response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/octet-stream");
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void downloadFile(String remoteFilePath, HttpServletResponse response)
			throws IOException {
		File file = new File(remoteFilePath);
		if (file.exists()) {
			System.out.println("文件存在");
		}
		String filename = file.getName();
		// 读取文件数据
		InputStream fis = null;
		fis = new BufferedInputStream(new FileInputStream(file.getPath()));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		// 设置响应头
		response.reset();
		// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(filename.replaceAll(" ", "").getBytes("utf-8"),
						"iso8859-1"));
		response.addHeader("Content-Length", "" + file.length());
		// response.setContentType("application/octet-stream");
		response.setContentType("multipart/form-data");
		// 把数据写给浏览器,输出文件
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		/*
		 * String systemType = System.getProperties().getProperty("os.name");//
		 * 判断是哪个操作系统 if(!xTemplate){ if(!(systemType.startsWith("win") ||
		 * systemType.startsWith("Win"))){ os.write(new byte[] { (byte) 0xEF,
		 * (byte) 0xBB,(byte) 0xBF }); } }
		 */
		os.write(buffer);
		os.flush();
		os.close();
	}

	public void download2(String remoteFilePath, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		String downLoadPath = remoteFilePath;

		File file = new File(remoteFilePath);
		String filename = file.getName();
		long fileLength = file.length();

		response.setContentType("");
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(filename.getBytes("gb2312"), "ISO-8859-1"));
		response.setHeader("Content-Length", String.valueOf(fileLength));

		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bis.close();
		bos.close();

	}
}

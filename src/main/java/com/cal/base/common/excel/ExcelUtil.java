package com.cal.base.common.excel;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public final class ExcelUtil {
	
	public static Map<String, Object> get(String key) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 文件名-.filename
		String filename = ReadConfigUtil.getProperty(key+".filename");
		//  报表头
		String title = ReadConfigUtil.getProperty(key+".title");
		// 列头
		String fieldname = ReadConfigUtil.getProperty(key+".fieldname");
		String[] rowsName = fieldname.split(",");
		// 列数据封装
		
		map.put("filename", filename);
		map.put("title", title);
		map.put("rowsName", rowsName);
		
		return map;
	}
	
}

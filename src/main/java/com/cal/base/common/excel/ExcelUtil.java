package com.cal.base.common.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.cal.base.common.exception.CommonException;
import com.cal.base.common.reflect.ObjReflect;

public final class ExcelUtil {
	// 文件名后缀
	public static final String SUFFIX_FILE_NAME = ".filename";
	// 标题后缀
	public static final String SUFFIX_TITLE = ".title";
	// 字段名后缀
	public static final String SUFFIX_FIELD_NAME = ".fieldname";
	// 字段值后缀
	public static final String SUFFIX_FIELD_VALUE = ".fieldvalue";
	
	// 根据key解析配置文件
	public static Map<String, Object> analysisExportProp(String key) {
		if (StringUtils.isBlank(key)) {
			throw new CommonException("配置文件解析出错了(传递的key不能空)");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 文件名
		String filename = ReadConfigUtil.getProperty(key + SUFFIX_FILE_NAME);
		if (StringUtils.isBlank(filename)) {
			throw new CommonException("配置文件解析出错了(配置的filename不能为空)");
		}
		// 报表头
		String title = ReadConfigUtil.getProperty(key + SUFFIX_TITLE);
		if (StringUtils.isBlank(title)) {
			throw new CommonException("配置文件解析出错了(配置的title不能为空)");
		}
		// 列头
		String fieldname = ReadConfigUtil.getProperty(key + SUFFIX_FIELD_NAME);
		if (StringUtils.isBlank(fieldname)) {
			throw new CommonException("配置文件解析出错了(配置的fieldname不能为空)");
		}
		String[] rowsName = fieldname.split(",");
		// 列值
		String fieldvalue = ReadConfigUtil.getProperty(key + SUFFIX_FIELD_VALUE);
		if (StringUtils.isBlank(fieldname)) {
			throw new CommonException("配置文件解析出错了(配置的fieldvalue不能为空)");
		}
		String[] rowsValue = fieldvalue.split(",");
		
		// 逻辑判断
		if (rowsName.length != rowsValue.length) {
			throw new CommonException("配置文件解析出错了(配置的fieldname和fieldvalue数目不相等)");
		}
		
		// 列数据封装
		map.put("filename", filename);
		map.put("title", title);
		map.put("rowsName", rowsName);
		map.put("rowsValue", rowsValue);
		
		return map;
	}
	
	// 导出帮助方法
	public static <T> void export(List<T> list, String exportName, HttpServletResponse response) throws Exception {
		Map<String, Object> map = analysisExportProp(exportName);
		// 报表导出头
		// 文件名
		String filename = (String) map.get("filename");
		// 报表头
		String title = (String) map.get("title");
		// 列头
		String[] rowsName = (String[]) map.get("rowsName");
		// 
		String[] rowsValue = (String[]) map.get("rowsValue");
		// 列数据封装
		List<Object[]>  rowList = new ArrayList<Object[]>();
		if (list != null) {
			for (T temp : list) {
				int len = rowsName.length;
				Object[] row = new Object[len] ;
				for(int i = 0; i < len; i++) {
					row[i] = ObjReflect.getFieldValue(temp, rowsValue[i]);
				}
				rowList.add(row);
			}
		}
		// Excel导出公共调用
        ExcelParam excelParam = new ExcelParam(title, rowsName, rowList , 1, null);
        List<ExcelParam> params = new ArrayList<>();
        params.add(excelParam);
        ExportExcel.export(filename, params, response);
	}
	
}

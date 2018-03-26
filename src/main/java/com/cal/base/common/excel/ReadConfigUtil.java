package com.cal.base.common.excel;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

/**
 * 读取导出配置文件帮助类
 * @author andyc
 *
 */
public final class ReadConfigUtil {
	
	private static ResourceBundle myResources;
	
	static {
		load();
	}
	
	// 加载配置文件
	private static void load() {
		// 配置文件
		String basename = "export.export";
		// 读取配置文件
		myResources = ResourceBundle.getBundle(basename);
		
	}
	
	// 获取值
	public static String getProperty(String key) throws UnsupportedEncodingException{
		if (myResources == null) {
			load();
		}
		if (StringUtils.isBlank(key)) {
			return null;
		}
		String value = myResources.getString(key);
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return new String(value.getBytes("ISO8859-1"), "UTF-8");
	}
}

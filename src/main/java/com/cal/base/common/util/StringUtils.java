package com.cal.base.common.util;

import java.util.regex.Pattern;

public class StringUtils {
	public static final String PHONE ="^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0,5-8])|(18[0-9]))\\d{8}$";
	
	public static boolean isPhoneNumber(String phone) {
        if (isBlank(phone)) {
            return false;
        }
        return Pattern.matches(PHONE, phone);
    }
	
	public static boolean isBlank(String str) {
		int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
	}
	
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
}

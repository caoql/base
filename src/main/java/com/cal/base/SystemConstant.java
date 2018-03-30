package com.cal.base;

/**
 * 系统级别的常量
 * @author andyc
 *
 */
public class SystemConstant {

	// redis存用户信息的前缀
	public static final String REDIS_USER_PRE = "USER-INFO-ID:";
	
	// 用户登录信息前缀
	public static final String LOGIN_USER_PRE = "REDIS_LOGIN_USER_PRE:";

	public static String getSessionKey(String requestedSessionId) {
		return "tms_sesn_" + requestedSessionId;
	}
}

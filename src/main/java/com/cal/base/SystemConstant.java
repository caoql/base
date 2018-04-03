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
	
	// 系统资源根节点
	public static final String RESOURCE_ROOT_DEFAULT = "default";
	
	// 是
	public static final String IS_YES_STRING = "1";
	// 否
	public static final String IS_NOT_STRING = "0";
	
	// 是
	public static final int IS_YES_INT = 1;
	// 否
	public static final int IS_NOT_INT = 0;
	
	// 是
	public static final short IS_YES_SHORT = 1;
	// 否
	public static final short IS_NOT_SHORT = 0;
	
}

package com.cal.base.common.exception;

import com.cal.base.common.enums.ErrorCodeEnum;

/**
 * 通用异常
 * @author andyc
 * @since 2018-1-1
 */
public class CommonException extends RuntimeException {
	
	private static final long serialVersionUID = 5089877029820554730L;
	
	public static final int COMMON_ERROR_CODE = 600;
	public static final String COMMON_ERROR_MSG = "通用的未知错误";
    
    private int code;
    private String msg;
    
    public CommonException(ErrorCodeEnum errorCodeEnum) {
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }
    
    public CommonException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public CommonException(String msg) {
        this.code = CommonException.COMMON_ERROR_CODE;
        this.msg = msg;
    }
    
    public CommonException() {
        this.code = CommonException.COMMON_ERROR_CODE;
        this.msg = CommonException.COMMON_ERROR_MSG;
    }

	public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}


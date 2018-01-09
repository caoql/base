package com.cal.base.common.exception;

import com.cal.base.common.enums.ErrorCodeEnum;

/**
 * Service层抛出的异常
 * @author andyc
 * @since 2018-1-1
 */
public class ServiceException extends RuntimeException {
	
	private static final long serialVersionUID = -8257955820385768047L;
	
	public static final int COMMON_ERROR_CODE = 800;
	public static final String COMMON_ERROR_MSG = "Service层的未知错误";
    
    private int code;
    private String msg;
    
    public ServiceException(ErrorCodeEnum errorCodeEnum) {
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }
    
    public ServiceException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public ServiceException(String msg) {
        this.code = CommonException.COMMON_ERROR_CODE;
        this.msg = msg;
    }
    
    public ServiceException() {
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



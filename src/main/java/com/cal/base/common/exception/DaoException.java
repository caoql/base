package com.cal.base.common.exception;

import com.cal.base.common.enums.ErrorCodeEnum;

/**
 * Dao层抛出的异常
 * @author andyc
 * @since 2018-1-1
 */
public class DaoException extends RuntimeException {
    
	private static final long serialVersionUID = -981649361641666505L;
	
	public static final int COMMON_ERROR_CODE = 700;
	public static final String COMMON_ERROR_MSG = "Dao层的未知错误";
    
    private int code;
    private String msg;
    
    public DaoException(ErrorCodeEnum errorCodeEnum) {
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }
    
    public DaoException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public DaoException(String msg) {
        this.code = CommonException.COMMON_ERROR_CODE;
        this.msg = msg;
    }
    
    public DaoException() {
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

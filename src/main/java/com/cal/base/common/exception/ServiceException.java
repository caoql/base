package com.cal.base.common.exception;

import com.cal.base.common.enums.ErrorCodeEnum;

/**
 * Service层抛出的异常
 * @author andyc
 * @since 2018-2-26
 */
public class ServiceException extends CommonException {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8257955820385768047L;
	
	public ServiceException() {
		this.code = ErrorCodeEnum.SERVICE_ERROR.getCode();
		this.msg = ErrorCodeEnum.SERVICE_ERROR.getMsg();
	}
    
    public ServiceException(ErrorCodeEnum errorCodeEnum) {
       super(errorCodeEnum);
    }
    
    public ServiceException(int code, String msg) {
        super(code, msg);
    }
    
    public ServiceException(String msg) {
        this.code = ErrorCodeEnum.SERVICE_ERROR.getCode();
        this.msg = msg;
    }
}



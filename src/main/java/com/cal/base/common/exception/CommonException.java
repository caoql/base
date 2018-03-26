package com.cal.base.common.exception;

import com.cal.base.common.enums.ErrorCodeEnum;


/**
 * 通用异常
 * @author andyc
 * @since 2018-2-26
 */
public class CommonException extends RuntimeException {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1180059982502682421L;
	/**
     * 错误码
     */
    protected Integer code;
    /**
     * 描述信息
     */
    protected String msg;
    
	public CommonException() {
		this.code = ErrorCodeEnum.COMMON_ERROR.getCode();
		this.msg = ErrorCodeEnum.COMMON_ERROR.getMsg();
	}
    
    public CommonException(ErrorCodeEnum errorCodeEnum) {
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }
    
    public CommonException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public CommonException(String msg) {
        this.code = ErrorCodeEnum.COMMON_ERROR.getCode();
        this.msg = msg;
    }

	public Integer getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}


package com.cal.base.common.exception;

import com.cal.base.common.enums.ErrorCodeEnum;

/**
 * Dao层抛出的异常
 * @author andyc
 * @since 2018-2-26
 */
public class DaoException extends CommonException {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8376281035177970222L;
	
	public DaoException() {
		this.code = ErrorCodeEnum.DAO_ERROR.getCode();
		this.msg = ErrorCodeEnum.DAO_ERROR.getMsg();
	}
    
    public DaoException(ErrorCodeEnum errorCodeEnum) {
       super(errorCodeEnum);
    }
    
    public DaoException(int code, String msg) {
        super(code, msg);
    }
    
    public DaoException(String msg) {
        this.code = ErrorCodeEnum.DAO_ERROR.getCode();
        this.msg = msg;
    }
}

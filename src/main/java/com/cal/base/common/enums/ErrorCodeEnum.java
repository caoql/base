package com.cal.base.common.enums;

/**
 * 通用的错误定义
 * @author andyc
 * @since 2018-1-1
 */
public enum ErrorCodeEnum {
	CALL_SUCCESS(0, "操作成功"), 
    CALL_ERROR(999, "系统异常，请稍后重试...");
    
    /**
     * 错误码
     */
    private int code;
	
    /**
     * 描述信息
     */
    private String msg;
    
    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

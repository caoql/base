package com.cal.base.common.enums;

/**
 * 通用的错误定义
 * @author andyc
 * @since 2018-2-26
 */
public enum ErrorCodeEnum {
	CALL_SUCCESS(0, "操作成功"), 
    CALL_ERROR(9999, "系统异常，请稍后重试..."),
    CALL_VALIDATE_ERROR(1111, "参数校验失败"),
    
    //通用异常定义
    COMMON_ERROR(9000, "通用的未知错误"),
    
    // Dao层的通用错误定义
    DAO_ERROR(8000, "Dao层的未知错误"),
	
    // Service层的通用错误定义
    SERVICE_ERROR(7000, "Service层的未知错误"),
    
    // 数据库相关错误定义以8开头
	DB_INSERT_ERROR(8010, "数据库插入数据失败"),
	DB_UPDATE_ERROR(8020, "数据库更新数据失败"),
	DB_DELETE_ERROR(8030, "数据库删除数据失败"),
	
	PARAM_IS_NULL(1000,"参数为空");
	
    /**
     * 错误码
     */
    private Integer code;
	
    /**
     * 描述信息
     */
    private String msg;
    
    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
    
    
}

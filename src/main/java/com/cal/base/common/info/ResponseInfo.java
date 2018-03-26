package com.cal.base.common.info;

import com.cal.base.common.enums.ErrorCodeEnum;


/**
 * controller统一的返回数据
 * 相当于一个工具类，所有属性设置为public,简化操作
 * @author andyc
 * @since 2018-1-3
 */
public class ResponseInfo {
    /**
     * 0-success,other-fail
     */
    public Integer code = 0;
    
    /**
     * 状态码的描述信息
     */
    public String msg = "操作成功";
    
    /**
     * 数据
     */
    public Object data;
    

	public void setErrorInfo(ErrorCodeEnum errorCode) {
		this.code = errorCode.getCode();
		this.msg = errorCode.getMsg();
	} 
}


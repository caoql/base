package com.cal.base.system.controller;

import com.cal.base.common.enums.ErrorCodeEnum;
import com.cal.base.common.info.ResponseInfo;

public abstract class BaseController {
	/**
	 * ajax成功
	 * @param obj 成功时的数据对象
	 * @return {Object}
	 */
	public Object renderSuccess(Object obj) {
		ResponseInfo info = new ResponseInfo();
		info.data = obj;
		return info;
	}
	
	/**
	 * ajax成功
	 * @return {Object}
	 */
	public Object renderSuccess() {
		ResponseInfo info = new ResponseInfo();
		return info;
	}
	
	/**
	 * ajax成功
	 * @param msg
	 * @return {Object}
	 */
	public Object renderSuccess(String msg) {
		ResponseInfo info = new ResponseInfo();
		info.msg = msg;
		return info;
	}
	
	
	/**
     * ajax失败
     * @param msg 失败的消息
     * @return {Object}
     */
    public Object renderError(String msg) {
    	ResponseInfo info = new ResponseInfo();
    	info.code = ErrorCodeEnum.COMMON_ERROR.getCode();
    	info.msg = msg;
		return info;
    }
}

package com.cal.base.common.util.web;

import java.text.DecimalFormat;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@SuppressWarnings("serial")
public class LoginCookieDto implements java.io.Serializable{
	private static Logger logger = Logger.getLogger(LoginCookieDto.class);
	
	/** 0 未登录;1 cookie 快速登录(弱登录); 2 服务窗/微信 快速登录(免登录 /弱登录) ; 3 强登录 */
	public static String loginLevel0="0";
	/** 0 未登录;1 cookie 快速登录(弱登录); 2 服务窗/微信 快速登录(免登录 /弱登录) ; 3 强登录 */
	public static String loginLevel1="1";
	/** 0 未登录;1 cookie 快速登录(弱登录); 2 服务窗/微信 快速登录(免登录 /弱登录) ; 3 强登录 */
	public static String loginLevel2="2";
	/** 0 未登录;1 cookie 快速登录(弱登录); 2 服务窗/微信 快速登录(免登录 /弱登录) ; 3 强登录 */
	public static String loginLevel3="3";
	
	/**  注册渠道:0 微信;1支付宝;2 ios app;3 android app; 4 其他 app;5 网站  l**/
	private String enterType;
	/** 1 代表当前第一次登录 **/
	private Integer enterTimes =1;
	/**版本**/
	private Long version = 1l;

	private String uuid;
	
	public String userId;
	/** 0 未登录;1 cookie 快速登录(弱登录); 2 服务窗/微信 快速登录(免登录 /弱登录) ; 3 强登录 */
	private String loginLevel = "0";
	
    private String syscode;
    
    private String systemdef;
	
	public LoginCookieDto(){
		enterTimes = 0;
		this.uuid = UUID.randomUUID().toString().replace("_", "");
	}
	
	public LoginCookieDto(String enterType , Integer enterTimes){
		this.enterType =  StringUtils.isEmpty(enterType)? Constant.USER_CHANNEL_5: enterType;
		this.enterTimes = (enterTimes==null ? enterTimes = 0 : enterTimes);
		this.uuid = UUID.randomUUID().toString().replace("_", "");
	}
	
	
	public String toString() {
		return JSON.toJSONString(this,
				SerializerFeature.WriteDateUseDateFormat,
				SerializerFeature.WriteMapNullValue);
	}
	
	public String buildCookieValue(){
		if(StringUtils.isEmpty(uuid)){
			this.uuid = UUID.randomUUID().toString().replace("_", "");
		}
		DecimalFormat d2 = new DecimalFormat("00");
		StringBuffer sb = new StringBuffer();
		sb.append((StringUtils.isEmpty(enterType)? Constant.USER_CHANNEL_5: enterType) )  //登录 渠道 0
		.append(","+d2.format( (enterTimes==null ? enterTimes = 0 : enterTimes)))  // 当前登录次数 1
		.append(","+uuid)   //2
		.append(","+(userId!=null ? userId : "")) //用户ID 3
		.append(","+(version!=null ? version : "")) //version 4
		.append(","+(loginLevel!=null ? loginLevel : "")) //loginLevel5
		.append(","+(syscode!=null ? syscode : "")) //syscode
		.append(","+(systemdef!=null ? systemdef : "")) //systemdef
		;
		String value = new String(org.apache.commons.codec.binary.Base64.encodeBase64(sb.toString().getBytes()));
		//随机的前四位
		return "MDEs"+value;
	}
	/**
	 * @param cookieValue
	 * @return 不会返回空的对象
	 */
	public static LoginCookieDto buildDto(String cookieValue){
		try {
			if(!StringUtils.isEmpty(cookieValue)){
				String  planiVlaue = new String(org.apache.commons.codec.binary.Base64.decodeBase64( cookieValue.substring(4).getBytes()));
				String [] strArray = planiVlaue.split(",");
				String enterType =strArray[0];
				Integer enterTimes = Integer.parseInt(strArray[1]);
				String uuid  = strArray[2] ;
				String userIdStr =  strArray.length>3?strArray[3]:null;
				String versionStr = strArray.length>4?strArray[4]:null;
				String loginLevel = strArray.length>5?strArray[5]:null;
				String syscode = strArray.length>6?strArray[6]:null;
				String systemdef = strArray.length>7?strArray[7]:null;
				String userId = null;
				Long version = null;
				if(StringUtils.isNotEmpty(userIdStr)){
					userId = String.valueOf(userIdStr);
				}
				if(StringUtils.isNotEmpty(versionStr)){
					version = Long.valueOf(versionStr);
				}
				return new LoginCookieDto(enterType, enterTimes).setUuid(uuid).setUserId(userId).setVersion(version)
						.setLoginLevel(loginLevel).setSyscode(syscode).setSystemdef(systemdef);
			}else{
				return new LoginCookieDto();
			}
		} catch (Exception e) {
			logger.error("cookieValue="+cookieValue,e);
			return new LoginCookieDto();
		}
	}
	
	public Boolean getIsLogin() {
		/** 0 未登录;1 cookie 快速登录(弱登录); 2 服务窗/微信 快速登录(免登录 /弱登录) ; 3 强登录 */
		if( StringUtils.isNotEmpty(loginLevel) && !"0".equals(loginLevel) ){
			return true;
		}else{
			return false;
		}
	}
	/**  注册/登录渠道:0 微信;1支付宝;2 ios app;3 android app; 4 其他 app;5 网站   ysh_yjf_user.create_channel **/
	public String getEnterType() {
		return enterType;
	}
	public LoginCookieDto setEnterType(String enterType) {
		this.enterType = enterType;
		return this;
	}
	public Integer getEnterTimes() {
		return enterTimes;
	}
	public LoginCookieDto setEnterTimes(Integer enterTimes) {
		this.enterTimes = enterTimes;
		return this;
	}
	public String getUuid() {
		return uuid;
	}
	public LoginCookieDto setUuid(String uuid) {
		this.uuid = uuid;
		return this;
	}


	public static void main(String[] args) {
		String coolieValue = "01,";
		String value = new String(org.apache.commons.codec.binary.Base64.encodeBase64(coolieValue.getBytes()));
		LoginCookieDto lcd = buildDto(value);
	}

	public Long getVersion() {
		return version;
	}

	public LoginCookieDto setVersion(Long version) {
		this.version = version;
		return this;
	}

	public String getUserId() {
		return userId;
	}

	public LoginCookieDto setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public String getLoginLevel() {
		return loginLevel;
	}

	public LoginCookieDto setLoginLevel(String loginLevel) {
		this.loginLevel = loginLevel;
		return this;
	}

	public String getSyscode() {
		return syscode;
	}

	public LoginCookieDto setSyscode(String syscode) {
		this.syscode = syscode;
		return this;
	}

	public String getSystemdef() {
		return systemdef;
	}

	public LoginCookieDto setSystemdef(String systemdef) {
		this.systemdef = systemdef;
		return this;
	}
}

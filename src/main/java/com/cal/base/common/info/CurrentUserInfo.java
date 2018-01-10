package com.cal.base.common.info;

import java.io.Serializable;
import java.util.Date;

import com.cal.base.common.reflect.ObjReflect;



public class CurrentUserInfo implements Serializable {
	
	private static final long serialVersionUID = -3116161940595611422L;

	public CurrentUserInfo() {}
	
	@Override
	public String toString() {
		return ObjReflect.toString(this);
	}
	
	/**
	 * 系统编码
	 */
	private String syscode;
	/**
	 * 登录账号
	 */
	private String account;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 当前时间
	 */
	private Date nowTime;
	
	/**
	 * 用户所在组织编号
	 */
	private String organizationId;
	
	private String userId;
	
	 /**
     * 性别
     */
    private Byte sex;

    /**
     * 年龄
     */
    private Byte age;

    /**
     * 电话
     */
    private String phone;
    

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getNowTime() {
		return nowTime;
	}

	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public Byte getAge() {
		return age;
	}

	public void setAge(Byte age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}

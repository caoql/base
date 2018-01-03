package com.cal.base.system.entity.query;

import java.io.Serializable;

import com.cal.base.common.info.RequestInfo;
import com.cal.base.common.reflect.ObjReflect;

public class UserParam extends RequestInfo implements Serializable {

	private static final long serialVersionUID = 6723603051553532737L;

	private String account;
	
	private String name;
	
	private String phone;
	
	private String sex;
	
	private String isEnabled;
	
	@Override
	public String toString() {
		return ObjReflect.toString(this);
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}
}

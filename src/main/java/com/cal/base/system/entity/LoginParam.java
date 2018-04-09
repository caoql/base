package com.cal.base.system.entity;

import java.io.Serializable;

/**
 * 
 * 登录参数封装
 * @author andyc 2018-4-4
 *
 */
public class LoginParam implements Serializable {
	private static final long serialVersionUID = 9153464506667490189L;

	private String username;

	private String password;

	@Override
	public String toString() {
		return "LoginParam [username=" + username + ", password=" + password
				+ "]";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim(); 
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim(); 
	}
}

package com.cal.base.system.entity.vo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.cal.base.common.exception.CommonException;
import com.cal.base.system.entity.po.UserPO;

/**
 * 添加用户和编辑用户对应的VO
 * 
 * @author andyc 2018-3-16
 *
 */
public class UserVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2388723975808996221L;

	private Long userId;

	private String account;

	private String name;

	private String password;

	/** 密码加密盐 */
	private String salt;

	private String phone;

	private String sex;

	private String isEnabled;

	private String remark;

	/**
	 * userVO转userPO
	 * 
	 * @return
	 */
	public UserPO toUserPO() {
		UserPO po = new UserPO();
		try {
			BeanUtils.copyProperties(po, this);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			throw new CommonException("UserVO转成UserPO的过程出错了");
		}
		return po;
	}

	@Override
	public String toString() {
		return "UserVO [userId=" + userId + ", account=" + account + ", name="
				+ name + ", password=" + password + ", salt=" + salt
				+ ", phone=" + phone + ", sex=" + sex + ", isEnabled="
				+ isEnabled + ", remark=" + remark + "]";
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}

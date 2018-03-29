package com.cal.base.system.entity.dto;

import java.io.Serializable;
import java.util.List;

import com.cal.base.common.reflect.ObjReflect;
import com.cal.base.system.entity.po.RolePO;
import com.cal.base.system.entity.po.UserOrganizationPO;

/**
 * 用户管理界面对应的DTO
 * 
 * @author andyc 2018-3-29
 *
 */
public class UserListDTO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7131870576745042683L;

	private String userId;

	private String account;

	private String name;

	private String phone;

	private String sex;

	private String isEnabled;

	private String remark;

	private String createTime;

	private String creator;

	private String updateTime;

	private String updator;
	
	// 用户组织信息
    private UserOrganizationPO userOrganization;
    
    // 角色信息
    private List<RolePO> role;
    
	@Override
	public String toString() {
		return ObjReflect.toString(this);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		switch (sex) {
		case "m":
			return "男";
		case "w":
			return "女";
		default:
			return sex;
		}
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIsEnabled() {
		 switch (isEnabled) {
         case "1":
             return "正常";
         case "0":
             return "停用";
         default :	
         	return isEnabled;
         }
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public UserOrganizationPO getUserOrganization() {
		return userOrganization;
	}

	public void setUserOrganization(UserOrganizationPO userOrganization) {
		this.userOrganization = userOrganization;
	}

	public List<RolePO> getRole() {
		return role;
	}

	public void setRole(List<RolePO> role) {
		this.role = role;
	}
}
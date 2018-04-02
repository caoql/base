package com.cal.base.system.entity.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * 角色绑定界面VO
 * @author andyc 2018-4-2
 *
 */
public class UserRoleVO implements Serializable {
	private static final long serialVersionUID = -8036003957340838506L;

	@NotNull(message="用户的ID不能为空")
	private String userId;
	
	@NotNull(message="角色绑定信息不能为空")
	private List<String> roleIds;

	@Override
	public String toString() {
		return "UserRoleVO [userId=" + userId + ", roleIds=" + roleIds + "]";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
}

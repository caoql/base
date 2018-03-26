package com.cal.base.system.entity.po;

import java.io.Serializable;

/**
 * system_user_role表对应的PO
 * @author andyc 2018-3-15
 *
 */
public class UserRolePO implements Serializable {
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = -6354569699818363968L;

	private String id;

    private String userId;

    private String roleId;

    @Override
	public String toString() {
		return "UserRole [id=" + id + ", userId=" + userId + ", roleId="
				+ roleId + "]";
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
}
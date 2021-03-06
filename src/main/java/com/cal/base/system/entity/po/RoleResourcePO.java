package com.cal.base.system.entity.po;

import java.io.Serializable;

/**
 * system_role_resource表对应的PO
 * @author andyc 2018-3-15
 *
 */
public class RoleResourcePO implements Serializable {
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 5460770784638762792L;

	private String id;

    private String roleId;

    private String resourceId;

    @Override
	public String toString() {
		return "RoleResource [id=" + id + ", roleId=" + roleId
				+ ", resourceId=" + resourceId + "]";
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId == null ? null : resourceId.trim();
    }
}
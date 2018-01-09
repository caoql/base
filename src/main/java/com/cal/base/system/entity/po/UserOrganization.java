package com.cal.base.system.entity.po;

import java.io.Serializable;

public class UserOrganization implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5579856289982268264L;

	private String id;

    private String userId;

    private String organizationId;

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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId == null ? null : organizationId.trim();
    }
}
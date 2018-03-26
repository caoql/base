package com.cal.base.system.entity.po;

import java.io.Serializable;

/**
 * system_user_organization表对应的PO
 * 
 * @author andyc 2018-3-15
 *
 */
public class UserOrganizationPO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5579856289982268264L;

	private String id;

	private String userId;

	private String organizationId;

	@Override
	public String toString() {
		return "UserOrganization [id=" + id + ", userId=" + userId
				+ ", organizationId=" + organizationId + "]";
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

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId == null ? null : organizationId
				.trim();
	}
}
package com.cal.base.system.entity.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户数据权限system_user_data_auth表对应的PO
 * 
 * @author andyc 2018-3-15
 */
public class UserDataAuthPO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7738722754489137913L;

	private String id;

	private String userId;

	private String businessCode;

	private String remark;

	private Date createTime;

	private String creator;

	private Date updateTime;

	private String updator;

	@Override
	public String toString() {
		return "UserDataAuthPO [id=" + id + ", userId=" + userId
				+ ", businessCode=" + businessCode + ", remark=" + remark
				+ ", createTime=" + createTime + ", creator=" + creator
				+ ", updateTime=" + updateTime + ", updator=" + updator + "]";
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

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode == null ? null : businessCode.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator == null ? null : creator.trim();
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator == null ? null : updator.trim();
	}
}
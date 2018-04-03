package com.cal.base.system.entity.po;

import java.io.Serializable;
import java.util.Date;

/**
 * system_resource表对应的PO
 * @author andyc 2018-3-15
 *
 */
public class ResourcePO implements Serializable {
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 2630857491860546156L;

	private String resourceId;

    private String name;

    private String url;

    private String type;

    private String description;

    private Byte nodeOrder;

    private String pid;

    private Short isEnabled;

    private String remark;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String updator;

    @Override
	public String toString() {
		return "Resource [resourceId=" + resourceId + ", name=" + name
				+ ", url=" + url + ", type=" + type + ", description="
				+ description + ", nodeOrder=" + nodeOrder + ", pid=" + pid
				+ ", isEnabled=" + isEnabled + ", remark=" + remark
				+ ", createTime=" + createTime + ", creator=" + creator
				+ ", updateTime=" + updateTime + ", updator=" + updator + "]";
	}

	public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId == null ? null : resourceId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Byte getNodeOrder() {
        return nodeOrder;
    }

    public void setNodeOrder(Byte nodeOrder) {
        this.nodeOrder = nodeOrder;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public Short getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Short isEnabled) {
        this.isEnabled = isEnabled;
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
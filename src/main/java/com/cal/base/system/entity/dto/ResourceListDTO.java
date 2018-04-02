package com.cal.base.system.entity.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.cal.base.common.reflect.ObjReflect;

/**
 * 资源界面数据展示DTO
 * @author andyc 2018-3-30
 *
 */
public class ResourceListDTO implements Serializable {
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = -3161337638650887933L;

	private String resourceId;

    private String name;

    private String url;

    private String type;

    private String description;

    private String nodeOrder;

    private String pid;

    private String isEnabled;

    private String remark;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String updator;

    private List<ResourceListDTO> children;
    
    @Override
	public String toString() {
		return ObjReflect.toString(this);
	}

	public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
    	if (Objects.equals("F", type)) {
			return "菜单组";
		} else if (Objects.equals("A", type)) {
			return "菜单";
		} else if (Objects.equals("D", type)) {
			return "动作";
		} else {
			return type;
		}
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNodeOrder() {
        return nodeOrder;
    }

    public void setNodeOrder(String nodeOrder) {
        this.nodeOrder = nodeOrder;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getIsEnabled() {
    	if (Objects.equals("1", isEnabled)) {
			return "是";
		} else if (Objects.equals("0", isEnabled)) {
			return "否";
		} else {
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
        this.creator = creator;
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
        this.updator = updator;
    }

	public List<ResourceListDTO> getChildren() {
		return children;
	}

	public void setChildren(List<ResourceListDTO> children) {
		this.children = children;
	}
}
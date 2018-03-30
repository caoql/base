package com.cal.base.system.entity.dto;

import java.io.Serializable;
import java.util.Objects;

import com.cal.base.common.reflect.ObjReflect;

/**
 * 角色界面数据展示对应的dto
 * @author andyc 2018-3-30
 *
 */
public class RoleListDTO implements Serializable {
	
	private static final long serialVersionUID = 2276899070436404771L;

	private String roleId;

    private String name;

    private String description;

    private String isEnabled;

    private String remark;

    private String createTime;

    private String creator;

    private String updateTime;

    private String updator;

    
    @Override
	public String toString() {
		return ObjReflect.toString(this);
	}

	public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
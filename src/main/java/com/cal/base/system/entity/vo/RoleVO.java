package com.cal.base.system.entity.vo;

import java.io.Serializable;

import com.cal.base.common.reflect.ObjReflect;
import com.cal.base.system.entity.po.RolePO;

/**
 * 角色新增编辑对应的VO
 * 
 * @author andyc 2018-3-30
 *
 */
public class RoleVO implements Serializable {
	private static final long serialVersionUID = 2259896124839393540L;

	private String roleId;

	private String name;

	private String description;

	private String isEnabled;

	public RolePO toRolePO() {
		RolePO po = new RolePO();
		po.setDescription(description);
		po.setRoleId(roleId);
		po.setIsEnabled(isEnabled);
		po.setName(name);
		return po;
	}
	
	@Override
	public String toString() {
		return ObjReflect.toString(this);
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId == null ? null : roleId.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled == null ? null : isEnabled.trim();
	}
	
}
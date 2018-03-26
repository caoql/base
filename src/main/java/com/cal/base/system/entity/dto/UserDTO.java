package com.cal.base.system.entity.dto;

import java.util.List;

import com.cal.base.common.reflect.ObjReflect;
import com.cal.base.system.entity.po.RolePO;
import com.cal.base.system.entity.po.UserPO;
import com.cal.base.system.entity.po.UserOrganizationPO;

/**
 * 
 * @author andyc
 *
 */
public class UserDTO extends UserPO {
	
    // 用户组织信息
    private UserOrganizationPO userOrganization;
    
    // 角色信息
    private List<RolePO> role;
    
    @Override
	public String toString() {
		return ObjReflect.toString(this);
	}

	public UserOrganizationPO getUserOrganization() {
		return userOrganization;
	}

	public void setUserOrganization(UserOrganizationPO userOrganization) {
		this.userOrganization = userOrganization;
	}

	public List<RolePO> getRole() {
		return role;
	}

	public void setRole(List<RolePO> role) {
		this.role = role;
	}
}
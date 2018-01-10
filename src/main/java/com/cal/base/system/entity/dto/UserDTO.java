package com.cal.base.system.entity.dto;

import java.util.List;

import com.cal.base.common.reflect.ObjReflect;
import com.cal.base.system.entity.po.Role;
import com.cal.base.system.entity.po.User;
import com.cal.base.system.entity.po.UserOrganization;

/**
 * 
 * @author andyc
 *
 */
public class UserDTO extends User {
	
    // 用户组织信息
    private UserOrganization userOrganization;
    
    // 角色信息
    private List<Role> role;
    
    @Override
	public String toString() {
		return ObjReflect.toString(this);
	}

	public UserOrganization getUserOrganization() {
		return userOrganization;
	}

	public void setUserOrganization(UserOrganization userOrganization) {
		this.userOrganization = userOrganization;
	}

	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}
}
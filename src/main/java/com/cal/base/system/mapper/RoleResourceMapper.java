package com.cal.base.system.mapper;

import com.cal.base.system.entity.po.RoleResourcePO;

/**
 * 角色Mapper,主要操作的是system_role_resource表
 * @author andyc 2018-3-15
 *
 */
public interface RoleResourceMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(RoleResourcePO record);

    RoleResourcePO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RoleResourcePO record);

    // 通过角色ID删除资源
	int deleteByRoleId(String roleId);
}
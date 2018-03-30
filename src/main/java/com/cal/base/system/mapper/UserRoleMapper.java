package com.cal.base.system.mapper;

import java.util.List;

import com.cal.base.system.entity.po.UserRolePO;

/**
 * 用户角色关系Mapper,主要操作的是system_user_role表
 * @author andyc 2018-3-15
 *
 */
public interface UserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(UserRolePO record);

    UserRolePO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserRolePO record);

    // 根据用户ID获取角色ID列表
	List<String> selectRoleIdListByUserId(String userId);
}
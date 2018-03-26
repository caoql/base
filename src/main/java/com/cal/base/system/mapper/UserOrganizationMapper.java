package com.cal.base.system.mapper;

import com.cal.base.system.entity.po.UserOrganizationPO;

/**
 * 用户组织关系Mapper，主要操作的是system_user_organization表
 * 
 * @author andyc 2018-3-15
 *
 */
public interface UserOrganizationMapper {
	int deleteByPrimaryKey(String id);

	int insertSelective(UserOrganizationPO record);

	UserOrganizationPO selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(UserOrganizationPO record);
}